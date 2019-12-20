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
            sql = "SELECT * FROM reservation r, user u, device d " +
                    "WHERE r.u_no = u.u_no " +
                    "AND r.d_no = d.d_no " +
                    "AND r_no = ? ";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, r_no);

            //记录执行状态
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                reservation.setR_no(rs.getInt("r_no"));
                reservation.setD_no(rs.getString("d_no"));
                reservation.setU_no(rs.getString("u_no"));
                reservation.setR_reservationDate(rs.getString("r_reservation_date"));
                reservation.setR_startDate(rs.getString("r_start_date"));
                reservation.setR_returnDate(rs.getString("r_return_date"));
                reservation.setR_feedBack(rs.getString("r_feedback"));
                reservation.setR_state(rs.getInt("r_state"));

                //用户附加属性
                reservation.setU_name(rs.getString("u_name"));
                reservation.setU_type(rs.getString("u_type"));
                reservation.setU_creditGrade(rs.getInt("u_credit_grade"));

                //设备附加属性
                reservation.setD_name(rs.getString("d_name"));
                reservation.setD_saveSite(rs.getString("d_save_site"));
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
     * @Description: 判断用户是否正在预约该设备的队列中
     * @Param u_no  d_no
     * @Return: boolean
     */
    public boolean isReserving(String u_no, String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        boolean flag = false;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM reservation WHERE u_no = ? AND d_no = ? AND r_state IN (0, 2, 3) ";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, u_no);
            pStmt.setString(2, d_no);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                flag = true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return flag;
    }

    /*
     * @Description: 用户预约设备
     * @Param d_no  u_no  startDate  returnDate
     * @Return: int
     */
    public int reserveDevice(String d_no, String u_no, String startDate, String returnDate)
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
            pStmt.setString(1, d_no);
            pStmt.setString(2, u_no);
            pStmt.setString(3, startDate);
            pStmt.setString(4, returnDate);

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
            sql = "UPDATE reservation SET r_state = -2, r_feedback = ?  " +
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
     * @Description: 通过管理员编号获取管理员管辖范围内的有人预约的设备
     * @Param a_no
     * @Return: java.util.List<bean.Reservation>
     */
    public List<Reservation> handleReservation(String a_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Reservation> reservationList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT d.d_no, d.d_name, d.d_model, d.d_photo, COUNT(*) r_sum " +
                  "FROM reservation r, device d " +
                  "WHERE r.d_no = d.d_no " +
                  "AND d.a_no = ? AND r_state IN(0, 3) " +
                  "GROUP BY d.d_no, d.d_name, d.d_model, d.d_photo " +
                  "ORDER BY r_sum DESC ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, a_no);

            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Reservation reservatioin = new Reservation();
                reservatioin.setD_no(rs.getString("d_no"));
                reservatioin.setD_name(rs.getString("d_name"));
                reservatioin.setD_photo(rs.getString("d_photo"));
                reservatioin.setR_sum(rs.getInt("r_sum"));
                reservationList.add(reservatioin);
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

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: java.util.List<bean.Reservation>
     */
    public List<Reservation> handleReservationDetail(String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Reservation> reservationList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT r_state, u.u_no, u_name, u_type, r_no, r_start_date, r_return_date, r_reservation_date, u_credit_grade " +
                    "FROM user u, reservation r " +
                    "WHERE u.u_no = r.u_no " +
                    "AND d_no = ? " +
                    "AND r_state IN(0, 3) " +
                    "ORDER BY r_reservation_date DESC,u_credit_grade DESC";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, d_no);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Reservation reservation = new Reservation();
                reservation.setR_state(rs.getInt("r_state"));
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

    /*
     * @Description: 管理员编辑修改用户预约，开始协商
     * @Param r_no  startDate  endDate  feedBack
     * @Return: int
     */
    public int editReservation(int r_no, String startDate, String endDate, String feedBack)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE reservation SET r_start_date = ? , r_return_date = ?, r_state = 2 , r_feedback = ? " +
                  "WHERE r_no = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, startDate);
            pStmt.setString(2, endDate);
            pStmt.setString(3, feedBack);
            pStmt.setInt(4, r_no);
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

    public String getStartDate(String u_no, String d_no)
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
            pStmt.setString(2, d_no);
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

    public String getReturnDate(String u_no, String d_no)
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
            pStmt.setString(2, d_no);
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
    public int confirmReserve(int r_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql =   "UPDATE reservation SET r_state = 1  " +
                    "WHERE r_no = ? " +
                    "AND r_state IN (0, 3)";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, r_no);

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
     * @Description: 预约中设备预约被拒绝：（预约取消:-2  预约拒绝:-1  预约中:0   预约成功:1 协商预约:2  协商成功:3）
     * @Param u_no  d_no  r_feedBack
     * @Return: int
     */
    public int refuseReserve(int r_no, String r_feedBack)
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
                    "WHERE r_no = ? " +
                    "AND r_state IN(0, 3)";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, r_feedBack);
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
     * @Description: 用户查看申请中预约：预约中0、协商中2、协商成功3
     * @Param u_no  page  count
     * @Return: java.util.List<bean.Reservation>
     */
    public List<Reservation> getUnfinishedReservationByPage(String u_no, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Reservation> reservationList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT r_no,r_reservation_date, r_start_date, r_return_date, d.d_no, d_name, r_state, r_feedback " +
                    "FROM reservation r,device d " +
                    "WHERE r.d_no = d.d_no " +
                    "AND u_no = ? AND r_state IN(0, 2, 3) " +
                    "ORDER BY r_reservation_date DESC " +
                    "LIMIT ?, ? ";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, u_no);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                Reservation reservation = new Reservation();
                reservation.setR_no(rs.getInt("r_no"));
                reservation.setR_reservationDate(rs.getString("r_reservation_date"));
                reservation.setR_startDate(rs.getString("r_start_date"));
                reservation.setR_returnDate(rs.getString("r_return_date"));
                reservation.setD_no(rs.getString("d_no"));
                reservation.setD_name(rs.getString("d_name"));
                reservation.setR_state(rs.getInt("r_state"));
                reservation.setR_feedBack(rs.getString("r_feedback"));
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

    /*
     * @Description: 用户查看已完成预约：成功1，拒绝-1、取消-2
     * @Param u_no  page  count
     * @Return: java.util.List<bean.Reservation>
     */
    public List<Reservation> getFinishedReservationByPage(String u_no, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Reservation> reservationList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT r_no,r_reservation_date, r_start_date, r_return_date, d.d_no, d_name, r_state, r_feedback " +
                    "FROM reservation r,device d " +
                    "WHERE r.d_no = d.d_no " +
                    "AND u_no = ? AND r_state IN(-2, -1, 1) " +
                    "ORDER BY r_reservation_date DESC " +
                    "LIMIT ?, ? ";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, u_no);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                Reservation reservation = new Reservation();
                reservation.setR_no(rs.getInt("r_no"));
                reservation.setR_reservationDate(rs.getString("r_reservation_date"));
                reservation.setR_startDate(rs.getString("r_start_date"));
                reservation.setR_returnDate(rs.getString("r_return_date"));
                reservation.setD_no(rs.getString("d_no"));
                reservation.setD_name(rs.getString("d_name"));
                reservation.setR_state(rs.getInt("r_state"));
                reservation.setR_feedBack(rs.getString("r_feedback"));
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

    /*
     * @Description: 设置预约状态
     * @Param r_no  r_state
     * @Return: int
     */
    public int setReservationState(int r_no, int r_state)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int flag = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE reservation SET r_state = ? WHERE r_no = ?";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setInt(1, r_state);
            pStmt.setInt(2, r_no);
            flag = pStmt.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }
}
