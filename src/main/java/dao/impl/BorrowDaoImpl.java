package dao.impl;

import bean.Borrow;
import dao.BorrowDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowDaoImpl implements BorrowDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 查询用户借用中的记录(借用中b_state=0，归还b_state=1,逾期未还b_state= -1)
     * @Param userNo
     * @Return: java.util.List<bean.Borrow>
     */
    public List<Borrow> getBorrowRecord(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Borrow> borrowList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT b_borrow_date, b_return_date, d_save_site, device.d_no, d_name, d_main_use " +
                  "FROM borrow, device " +
                  "WHERE u_no = ? AND b_state = 0 " +
                  "AND borrow.d_no = device.d_no";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                Borrow borrow = new Borrow();
                borrow.setB_borrowDate(rs.getString("b_borrow_date"));
                borrow.setB_returnDate(rs.getString("b_return_date"));
                borrow.setD_saveSite(rs.getString("d_save_site"));
                borrow.setD_no(rs.getString("d_no"));
                borrow.setD_name(rs.getString("d_name"));
                borrow.setD_mainUse(rs.getString("d_main_use"));
                borrowList.add(borrow);
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
        return borrowList;
    }

    /*
     * @Description: 管理员查看管辖范围内外借中记录
     * @Param a_no
     * @Return: java.util.List<bean.Borrow>
     */
    public List<Borrow> getBorrowList(int a_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Borrow> borrowList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT b_no, b.b_state,b.b_no, d.d_name, d.d_no, u.u_no, u.u_name, u_credit_grade, b_borrow_date, b_return_date " +
                    "FROM borrow b, administrator a, user u ,device d  " +
                    "WHERE a.a_no = ? " +
                    "AND b.u_no = u.u_no " +
                    "AND b.d_no = d.d_no " +
                    "AND b_state = 0 " +
                    "ORDER BY b_return_date";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setInt(1, a_no);
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                Borrow borrow = new Borrow();
                borrow.setB_no(rs.getInt("b_no"));
                borrow.setD_name(rs.getString("d_name"));
                borrow.setD_no(rs.getString("d_no"));
                borrow.setU_no(rs.getString("u_no"));
                borrow.setU_name(rs.getString("u_name"));
                borrow.setU_creditGrade(rs.getInt("u_credit_grade"));
                borrow.setB_borrowDate(rs.getString("b_borrow_date"));
                borrow.setB_returnDate(rs.getString("b_return_date"));
                borrowList.add(borrow);
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
        return borrowList;
    }

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: int
     */
    public int confirmBorrow(String u_no, String d_no, String borrowDate, String returnDate)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        con = JDBCUtils.getConnection();
        sql = "INSERT INTO borrow(d_no, u_no, b_borrow_date, b_return_date) " +
                "VALUES (?, ?, ?, ?)";
        try
        {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_no);
            pStmt.setString(2, u_no);
            pStmt.setString(3, borrowDate);
            pStmt.setString(4, returnDate);

            //返回执行状态
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
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE borrow SET b_state = -1 " +
                    "WHERE b_return_date < CURRENT_DATE " +
                    "AND b_state = 0";
            pStmt = con.prepareStatement(sql);

            result = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
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
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT u.u_name, u.u_no, u.u_type, u.u_credit_grade, b.b_borrow_date, b.b_return_date,u.u_phone, u.u_email FROM borrow b, device d, `user` u " +
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
                borrow.setU_no(rs.getString("u_no"));
                borrow.setU_type(rs.getString("u_type"));
                borrow.setU_creditGrade(rs.getInt("u_credit_grade"));
                borrow.setB_borrowDate(rs.getString("b_borrow_date"));
                borrow.setB_returnDate(rs.getString("b_return_date"));
                borrowList.add(borrow);
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
        return borrowList;
    }

    /*
     * @Description: 借用中设备归还 （0：借用中，1：归还 -1:逾期）
     * @Param b_no
     * @Return: int
     */
    public int returnBorrow(int b_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE borrow SET b_state = 1 " +
                    "WHERE b_no = ? " +
                    "AND b_state <> 1";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, b_no);

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
     * @Description: 根据借用编号查询借用记录
     * @Param b_no
     * @Return: bean.Borrow
     */
    public Borrow getBorrowByNo(int b_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        Borrow borrow = new Borrow();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM borrow b, device d, user u " +
                    "WHERE b.d_no = d.d_no " +
                    "AND b.u_no = u.u_no " +
                    "AND b_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, b_no);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                borrow.setB_no(rs.getInt("b_no"));
                borrow.setD_no(rs.getString("d_no"));
                borrow.setU_no(rs.getString("u_no"));
                borrow.setB_borrowDate(rs.getString("b_borrow_date"));
                borrow.setB_returnDate(rs.getString("b_return_date"));
                borrow.setB_state(rs.getInt("b_state"));
                borrow.setU_name(rs.getString("u_name"));
                borrow.setU_type(rs.getString("u_type"));
                borrow.setU_phone(rs.getString("u_phone"));
                borrow.setD_name(rs.getString("d_name"));
                borrow.setD_saveSite(rs.getString("d_save_site"));
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
        return borrow;
    }
}
