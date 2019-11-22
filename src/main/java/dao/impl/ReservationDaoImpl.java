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

public class ReservationDaoImpl implements ReservationDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public int reserveDevice(int d_no, String u_no, Date startDate, Date returnDate)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "INSERT INTO reservation(d_no,u_no,r_reservation_date,r_start_date,r_return_date) " +
                    "VALUES(?, ?, CURRENT_DATE, ?, ?)";
            m_pStmt = m_con.prepareStatement(m_sql);
            m_pStmt.setInt(1, d_no);
            m_pStmt.setString(2, u_no);
            m_pStmt.setDate(3, new java.sql.Date(startDate.getTime()));
            m_pStmt.setDate(4, new java.sql.Date(returnDate.getTime()));

            //记录执行状态
            flag = m_pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, m_pStmt, m_con);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        List<Device> deviceList = new ArrayList<>();
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT d.d_no, d.d_name, d.d_main_use, COUNT(*) r_sum " +
                  "FROM reservation r, device d " +
                  "WHERE " +
                  "r.d_no = d.d_no " +
                  "AND d.a_no = ? " +
                  "GROUP BY d.d_no, d.d_name, d.d_main_use " +
                  "ORDER BY r_sum DESC";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setInt(1, a_no);
            m_rs = m_pStmt.executeQuery();

            //判断是否存在记录
            while (m_rs.next())
            {
                Device device = new Device();
                device.setM_Dno(m_rs.getInt("d_no"));
                device.setM_Dname(m_rs.getString("d_name"));
                device.setM_DmainUse(m_rs.getString("d_main_use"));
                device.setM_Rsum(m_rs.getInt("r_sum"));
                deviceList.add(device);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return deviceList;
    }

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: java.util.List<bean.Reservation>
     */
    public List<Reservation> getReservationDetail(String d_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        List<Reservation> reservationList = new ArrayList<>();
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT u_name, u_type, r_reservation_date, r_start_date, r_return_date, r_reservation_date, u_credit_grade " +
                    "FROM user, reservation " +
                    "WHERE user.u_no = reservation.u_no " +
                    "AND d_no = ? " +
                    "AND r_state = 0 " +
                    "ORDER BY r_reservation_date";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, d_no);
            m_rs = m_pStmt.executeQuery();

            while (m_rs.next())
            {
                Reservation reservation = new Reservation();
                reservation.setM_Uname(m_rs.getString("u_name"));
                reservation.setM_Utype(m_rs.getString("u_type"));
                reservation.setM_RstartDate(m_rs.getString("r_start_date"));
                reservation.setM_RreturnDate(m_rs.getString("r_return_date"));
                reservation.setM_RreservationDate(m_rs.getString("r_reservation_date"));
                reservation.setM_UcreditGrade(m_rs.getInt("u_credit_grade"));
                reservationList.add(reservation);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return reservationList;
    }

    public String getStartDate(String u_no, int d_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        String startDate = "";
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT r_start_date " +
                    "FROM reservation " +
                    "WHERE u_no = ? " +
                    "AND d_no = ? ";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, u_no);
            m_pStmt.setInt(2, d_no);
            m_rs = m_pStmt.executeQuery();

            //判断是否存在记录
            if (m_rs.next())
            {
                startDate = m_rs.getString("r_start_date");
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
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return startDate;
    }

    public String getReturnDate(String u_no, int d_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        String returnDate = "";
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT r_return_date " +
                    "FROM reservation " +
                    "WHERE u_no = ? " +
                    "AND d_no = ? ";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, u_no);
            m_pStmt.setInt(2, d_no);
            m_rs = m_pStmt.executeQuery();

            //判断是否存在记录
            if (m_rs.next())
            {
                returnDate = m_rs.getString("r_return_date");
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
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "UPDATE reservation SET r_state = 1  " +
                    "WHERE u_no = ? AND d_no = ? " +
                    "AND r_state = 0";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, u_no);
            m_pStmt.setInt(2, d_no);

            flag = m_pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "UPDATE reservation SET r_state = -1, r_feedback = ?  " +
                    "WHERE u_no = ? AND d_no = ? " +
                    "AND r_state = 0";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1,r_feedBack);
            m_pStmt.setString(2, u_no);
            m_pStmt.setInt(3, d_no);

            flag = m_pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        List<Reservation> reservationList = new ArrayList<>();

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT r_reservation_date, r_start_date, r_return_date, d.d_no, d_name, d_main_use, r_state " +
                    "FROM reservation r,device d " +
                    "WHERE r.d_no = d.d_no " +
                    "AND u_no = ? ";
            m_pStmt = m_con.prepareStatement(m_sql);

            //执行操作
            m_pStmt.setString(1, u_no);
            m_rs = m_pStmt.executeQuery();
            while(m_rs.next())
            {
                Reservation reservation = new Reservation();
                reservation.setM_RreservationDate(m_rs.getString("r_reservation_date"));
                reservation.setM_RstartDate(m_rs.getString("r_start_date"));
                reservation.setM_RreturnDate(m_rs.getString("r_return_date"));
                reservation.setM_Dno(m_rs.getInt("d_no"));
                reservation.setM_Dname(m_rs.getString("d_name"));
                reservation.setM_Rstate(m_rs.getInt("r_state"));
                reservationList.add(reservation);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return reservationList;
    }


    public int getReservationNum(String u_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        int num = 0;

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "select count(*) from reservation where u_no = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //执行操作
            m_pStmt.setString(1, u_no);
            ResultSet m_rs = m_pStmt.executeQuery();
            if(m_rs.next())
            {
                num = m_rs.getInt(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return num;
    }
}
