package dao.impl;

import bean.Borrow;
import bean.Device;
import bean.Reservation;
import bean.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BorrowDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowDaoImpl implements BorrowDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: int
     */
    public int confirmBorrow(String u_no, int d_no, String borrowDate, String returnDate)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        con = JDBCUtils.getConnection();
        sql = "INSERT INTO borrow(d_no, u_no, b_borrow_date, b_return_date) " +
              "VALUES (?, ?, ?, ?)";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, d_no);
            pStmt.setString(2, u_no);
            pStmt.setString(3, borrowDate);
            pStmt.setString(4, returnDate);

            //返回执行状态
            flag = pStmt.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return flag;
    }
    /*
     * @Description: 设置所有逾期设备状态为 -1 表示逾期未还
     * @Param
     * @Return: int
     */
    public int setAllStateOverDue()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int result = 0;
        try {
            con = JDBCUtils.getConnection();
            sql = "UPDATE borrow SET b_state = -1 " +
                    "WHERE b_return_date < CURRENT_DATE "+
                    "AND b_state = 0";
            pStmt = con.prepareStatement(sql);

            result = pStmt.executeUpdate();
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
     * @Description: 管理员获取管辖范围内预期未还用户
     * @Param a_no
     * @Return: java.util.List<bean.Borrow>
     */
    public List<Borrow> getOverDueList(int a_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Borrow> borrowList = new ArrayList<>();
        try {
            con = JDBCUtils.getConnection();
            sql = "SELECT u.u_name,u.u_type, u.u_credit_grade, b.b_borrow_date, b.b_return_date,u.u_phone, u.u_email FROM borrow b, device d, `user` u " +
                  "WHERE b.d_no = d.d_no " +
                  "AND u.u_no = b.u_no " +
                  "AND d.a_no = ? " +
                  "AND b_state = -1 ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, a_no);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Borrow borrow = new Borrow();
                borrow.setU_name(rs.getString("u_name"));
                borrow.setU_type(rs.getString("u_type"));
                borrow.setU_credit_grade(rs.getInt("u_credit_grade"));
                borrow.setB_borrow_date(rs.getString("b_borrow_date"));
                borrow.setB_return_date(rs.getString("b_return_date"));
                borrowList.add(borrow);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return borrowList;
    }

    /*
     * @Description: 借用中设备归还 （0：借用中，1：归还）
     * @Param u_no  d_no
     * @Return: int
     */
    public int returnBorrow(String u_no, int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try {
            con = JDBCUtils.getConnection();
            sql = "UPDATE borrow SET b_state = 1 " +
                    "WHERE u_no = ? "+
                    "AND d_no = ? " +
                    "AND b_state = 0";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            pStmt.setInt(2, d_no);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 获取借用记录编号
     * @Param u_no  d_no
     * @Return: int
     */
    public int getBorrowNo(String u_no, int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int b_no = 0;
        try {
            con = JDBCUtils.getConnection();
            sql = "SELECT b_no FROM borrow " +
                    "WHERE u_no = ? " +
                    "AND d_no = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            pStmt.setInt(2, d_no);

            rs = pStmt.executeQuery();
            if (rs.next())
            {
                b_no = rs.getInt("b_no");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return b_no;
    }
}
