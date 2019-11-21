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

public class BorrowDaoImpl implements BorrowDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    /*
     * @Description: 查询用户借用中的记录(借用中b_state=0，归还b_state=1,逾期未还b_state= -1)
     * @Param userNo
     * @Return: java.util.List<bean.Borrow>
     */
    public List<Borrow> getBorrowRecord(String u_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        List<Borrow> borrowList = new ArrayList<>();

        try {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT " +
                  "b_borrow_date," +
                  "b_return_date," +
                  "d_save_site," +
                  "device.d_no," +
                  "d_name," +
                  "d_main_use," +
                  "b_state " +
                  "FROM borrow, device " +
                  "WHERE u_no = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //执行操作
            m_pStmt.setString(1, u_no);
            m_rs = m_pStmt.executeQuery();
            while(m_rs.next())
            {
                Borrow borrow = new Borrow();
                borrow.setM_BborrowDate(m_rs.getString("b_borrow_date"));
                borrow.setM_BreturnDate(m_rs.getString("b_return_date"));
                borrow.setM_DdeviceSite(m_rs.getString("d_save_site"));
                borrow.setM_Dno(m_rs.getInt("d_no"));
                borrow.setM_Dname(m_rs.getString("d_name"));
                borrow.setM_DmainUse(m_rs.getString("d_main_use"));
                borrow.setM_Bstate(m_rs.getInt("b_state"));
                borrowList.add(borrow);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return borrowList;
    }

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: int
     */
    public int confirmBorrow(String u_no, int d_no, String borrowDate, String returnDate)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        m_con = JDBCUtils.getM_connection();
        m_sql = "INSERT INTO borrow(d_no, u_no, b_borrow_date, b_return_date) " +
              "VALUES (?, ?, ?, ?)";
        try
        {
            m_pStmt = m_con.prepareStatement(m_sql);
            m_pStmt.setInt(1, d_no);
            m_pStmt.setString(2, u_no);
            m_pStmt.setString(3, borrowDate);
            m_pStmt.setString(4, returnDate);

            //返回执行状态
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
     * @Description: 设置所有逾期设备状态为 -1 表示逾期未还
     * @Param
     * @Return: int
     */
    public int setAllStateOverDue()
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int result = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "UPDATE borrow SET b_state = -1 " +
                    "WHERE b_return_date < CURRENT_DATE "+
                    "AND b_state = 0";
            m_pStmt = m_con.prepareStatement(m_sql);

            result = m_pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        List<Borrow> borrowList = new ArrayList<>();
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT u.u_name,u.u_type, u.u_credit_grade, b.b_borrow_date, b.b_return_date,u.u_phone, u.u_email FROM borrow b, device d, `user` u " +
                  "WHERE b.d_no = d.d_no " +
                  "AND u.u_no = b.u_no " +
                  "AND d.a_no = ? " +
                  "AND b_state = -1 ";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setInt(1, a_no);
            m_rs = m_pStmt.executeQuery();

            //判断是否存在记录
            while (m_rs.next())
            {
                Borrow borrow = new Borrow();
                borrow.setM_Uname(m_rs.getString("u_name"));
                borrow.setM_Utype(m_rs.getString("u_type"));
                borrow.setM_UcreditGrade(m_rs.getInt("u_credit_grade"));
                borrow.setM_BborrowDate(m_rs.getString("b_borrow_date"));
                borrow.setM_BreturnDate(m_rs.getString("b_return_date"));
                borrowList.add(borrow);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "UPDATE borrow SET b_state = 1 " +
                    "WHERE u_no = ? "+
                    "AND d_no = ? " +
                    "AND b_state = 0";
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
        finally {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int b_no = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT b_no FROM borrow " +
                    "WHERE u_no = ? " +
                    "AND d_no = ? ";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, u_no);
            m_pStmt.setInt(2, d_no);

            m_rs = m_pStmt.executeQuery();
            if (m_rs.next())
            {
                b_no = m_rs.getInt("b_no");
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
        return b_no;
    }
}
