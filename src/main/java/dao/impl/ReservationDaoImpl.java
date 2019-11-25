package dao.impl;

import bean.Device;
import bean.Reservation;
import dao.ReservationDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 根据借用编号获取借用对象
     * @Param r_no
     * @Return: bean.Reservation
     */
    public Reservation getReservation(int r_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        Reservation reservation = new Reservation();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM reservation WHERE r_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, r_no);

            //记录执行状态
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                reservation.setR_no(rs.getInt("r_no"));
                reservation.setD_no(rs.getInt("d_no"));
                reservation.setU_no(rs.getString("u_no"));
                reservation.setR_reservationDate(rs.getString("r_reservation_date"));
                reservation.setR_startDate(rs.getString("r_start_date"));
                reservation.setR_returnDate(rs.getString("r_return_date"));
                reservation.setR_feedBack(rs.getString("r_feedback"));
                reservation.setR_state(rs.getInt("r_state"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }

        return reservation;
    }
    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public int reserveDevice(int d_no, String u_no, Date startDate, Date returnDate)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO reservation(d_no,u_no,r_reservation_date,r_start_date,r_return_date) " +
                    "VALUES(?, ?, CURRENT_DATE, ?, ?)";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, d_no);
            pStmt.setString(2, u_no);
            pStmt.setDate(3, new java.sql.Date(startDate.getTime()));
            pStmt.setDate(4, new java.sql.Date(returnDate.getTime()));

            //记录执行状态
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }

        return flag;
    }

    /*
     * @Description: 用户取消预约
     * @Param r_no
     * @Return: int
     */
    public int cancelReservation(int r_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE reservation SET r_state = -1, r_feedback = ?  " +
                    "WHERE r_no = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, "用户取消预约");
            pStmt.setInt(2, r_no);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }
    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public List<Device> getReservedDevice(int a_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Device> deviceList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT d.d_no, d.d_name, d.d_main_use, COUNT(*) r_sum " +
                    "FROM reservation r, device d " +
                    "WHERE " +
                    "r.d_no = d.d_no " +
                    "AND d.a_no = ? AND r_state = 0 " +
                    "GROUP BY d.d_no, d.d_name, d.d_main_use " +
                    "ORDER BY r_sum DESC";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, a_no);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getInt("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_mainUse(rs.getString("d_main_use"));
                device.setR_sum(rs.getInt("r_sum"));
                deviceList.add(device);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return deviceList;
    }

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: java.util.List<bean.Reservation>
     */
    public List<Reservation> getReservationDetail(int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Reservation> reservationList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT u.u_no, u_name, u_type, r_no, r_start_date, r_return_date, r_reservation_date, u_credit_grade " +
                    "FROM user u, reservation r " +
                    "WHERE " +
                    "u.u_no = r.u_no " +
                    "AND d_no = ? " +
                    "AND r_state = 0 " +
                    "ORDER BY r_reservation_date DESC,u_credit_grade DESC";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, d_no);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Reservation reservation = new Reservation();
                reservation.setU_no(rs.getString("u_no"));
                reservation.setU_name(rs.getString("u_name"));
                reservation.setU_type(rs.getString("u_type"));
                reservation.setR_no(rs.getInt("r_no"));
                reservation.setR_startDate(rs.getString("r_start_date"));
                reservation.setR_returnDate(rs.getString("r_return_date"));
                reservation.setR_reservationDate(rs.getString("r_reservation_date"));
                reservation.setU_creditGrade(rs.getInt("u_credit_grade"));
                reservationList.add(reservation);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return reservationList;
    }

    public String getStartDate(String u_no, int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        String startDate = "";
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT r_start_date " +
                    "FROM reservation " +
                    "WHERE u_no = ? " +
                    "AND d_no = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            pStmt.setInt(2, d_no);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            if (rs.next())
            {
                startDate = rs.getString("r_start_date");
            }
            else
            {
                startDate = "not found";
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return startDate;
    }

    public String getReturnDate(String u_no, int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        String returnDate = "";
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT r_return_date " +
                    "FROM reservation " +
                    "WHERE u_no = ? " +
                    "AND d_no = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            pStmt.setInt(2, d_no);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            if (rs.next())
            {
                returnDate = rs.getString("r_return_date");
            }
            else
            {
                returnDate = "not found";
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return returnDate;
    }

    /*
     * @Description: 预约中设备预约成功：（0:预约中 -1：预约被拒绝 1：预约成功）
     * @Param u_no  d_no
     * @Return: int
     */
    public int confirmReserve(String u_no, int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE reservation SET r_state = 1  " +
                    "WHERE u_no = ? AND d_no = ? " +
                    "AND r_state = 0";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            pStmt.setInt(2, d_no);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 预约中设备预约被拒绝：（0:预约中 -1：预约被拒绝 1：预约成功）
     * @Param u_no  d_no  r_feedBack
     * @Return: int
     */
    public int refuseReserve(String u_no, int d_no, String r_feedBack)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE reservation SET r_state = -1, r_feedback = ?  " +
                    "WHERE u_no = ? AND d_no = ? " +
                    "AND r_state = 0";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, r_feedBack);
            pStmt.setString(2, u_no);
            pStmt.setInt(3, d_no);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 用户查看我的预约
     * @Param userNo
     * @Return: java.util.List<bean.Reservation>
     */
    public List<Reservation> getReservation(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Reservation> reservationList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT r_no,r_reservation_date, r_start_date, r_return_date, d.d_no, d_name, d_main_use, r_state " +
                    "FROM reservation r,device d " +
                    "WHERE r.d_no = d.d_no " +
                    "AND u_no = ? ";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                Reservation reservation = new Reservation();
                reservation.setR_no(rs.getInt("r_no"));
                reservation.setR_reservationDate(rs.getString("r_reservation_date"));
                reservation.setR_startDate(rs.getString("r_start_date"));
                reservation.setR_returnDate(rs.getString("r_return_date"));
                reservation.setD_no(rs.getInt("d_no"));
                reservation.setD_name(rs.getString("d_name"));
                reservation.setR_state(rs.getInt("r_state"));
                reservationList.add(reservation);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return reservationList;
    }


    public int getReservationNum(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int num = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "select count(*) from reservation where u_no = ?";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                num = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return num;
    }
}
