package dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.ReservationDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ReservationDaoImpl implements ReservationDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject reserveDevice(int deviceNo, String wechatId, Date startDate, Date endDate)
    {
        JDBCUtils.init(rs, pStmt, con);
        String u_no = "user0";
        JSONObject result = new JSONObject();
        con = JDBCUtils.getConnection();
        try {
            String sql = "select u_no from user where u_wechatid = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1,wechatId);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                u_no = rs.getString("u_no");
            }
            else
            {
                //没有找到对应的用户,添加错误信息
                result.put("msg","未找到对应的用户");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }

        JDBCUtils.init(rs, pStmt, con);
        try {
            con = JDBCUtils.getConnection();
            sql = "insert into reservation(d_no,u_no,r_reservation_date,r_start_date,r_return_date) values(?,?,CURRENT_DATE,?,?)";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, deviceNo);
            pStmt.setString(2, u_no);
            pStmt.setDate(3, new java.sql.Date(startDate.getTime()));
            pStmt.setDate(4, new java.sql.Date(endDate.getTime()));
            int flag = pStmt.executeUpdate();

            //找到对应的用户,记录执行状态
            result.put("flag",flag);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null,pStmt,con);
        }

        return result;
    }

    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservation(String wechatId)
    {
        //初始化
        JDBCUtils.init(rs, pStmt, con);
        JSONObject result = new JSONObject();
        JSONArray reservations = new JSONArray();
        try {
            con = JDBCUtils.getConnection();
            sql = "SELECT d.d_no, d.d_name, d.d_main_use, COUNT(*) r_sum " +
                    "FROM reservation r, device d " +
                    "WHERE " +
                    "r.d_no = d.d_no " +
                    "AND d.a_no = ? " +
                    "GROUP BY d.d_no, d.d_name, d.d_main_use " +
                    "ORDER BY r_sum DESC";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, wechatId);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            if (rs.next())
            {
                //有的话记录查询状态为1：成功
                result.put("state",1);
                do {
                    reservations.add(
                            JSON.parse("{\"d_no\":"+rs.getInt("d_no")+",\"d_name\":\""+rs.getString("d_name")+
                                    "\",\"d_main_use\":\""+rs.getString("d_main_use")+"\",\"r_sum\":"+rs.getInt("r_sum")+"}")
                    );
                }
                while (rs.next());
                result.put("reservations",reservations);
            }
            else
            {
                //不存在记录，查询状态为0：失败
                result.put("flag", 0);
                result.put("errMsg","未能获取到设备信息");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return result;
    }

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservationDetail(String deviceNo)
    {
        //初始化
        JDBCUtils.init(rs, pStmt, con);
        JSONObject result = new JSONObject();
        JSONArray reservations = new JSONArray();
        try {
            con = JDBCUtils.getConnection();
            sql = "SELECT u_name, u_type, r_reservation_date, r_borrow_date, r_return_date, u_credit_grade " +
                  "FROM user, reservation " +
                  "WHERE user.u_no = reservation.u_no " +
                  "AND d_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, deviceNo);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            if (rs.next())
            {
                //有的话记录查询状态为1：成功
                result.put("state",1);
                do {
                    reservations.add(
                            JSON.parse("{\"u_name\":\""+rs.getString("u_name")+"\",\"u_type\":\""+rs.getString("u_type")+
                                    "\",\"r_reservation_date\":\""+rs.getString("r_reservation_date")+"\",\"r_borrow_date\":\""+rs.getString("r_borrow_date")+
                                    "\",\"r_return_date\":\""+rs.getString("r_return_date")+"\",\"r_return_date\":\""+rs.getString("r_return_date")+
                                    "\",\"u_credit_grade\":\""+rs.getString("u_credit_grade")+"\"}")
                    );
                }
                while (rs.next());
                result.put("reservations",reservations);
            }
            else
            {
                //不存在记录，查询状态为0：失败
                result.put("flag", 0);
                result.put("errMsg","未能获取到预约信息");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return result;
    }
}
