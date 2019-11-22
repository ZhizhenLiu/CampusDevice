package dao.impl;

import bean.User;
import dao.UserDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;
    /*
     * @Description: select:通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    public User getUserByWechatID(String wechatID)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        User user = new User();
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT * FROM user WHERE u_wechatid = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, wechatID);
            m_rs = m_pStmt.executeQuery();

            if (m_rs.next())
            {
                user = new User(m_rs.getString("u_no"), m_rs.getString("u_name"), m_rs.getString("u_wechatid"), m_rs.getString("u_email"),
                       m_rs.getString("u_phone"), m_rs.getInt("u_credit_grade"), m_rs.getString("u_type"), m_rs.getString("u_mentor_name"),
                       m_rs.getString("u_mentor_phone"), m_rs.getString("u_major_class"));
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
        return  user;
    }


    /*
     * @Description: 用户首次登陆添加到user表中
     * @Param user
     * @Return: int
     */
    public int registerUser(User user)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "INSERT INT user VALUES (?,?,?,?,?,?,?,?,?,?)";
            m_pStmt = m_con.prepareStatement(m_sql);

            m_pStmt.setString(1,user.getM_Uno());
            m_pStmt.setString(2,user.getM_Uname());
            m_pStmt.setString(3,user.getM_UwechatID());
            m_pStmt.setString(4,user.getM_Uemail());
            m_pStmt.setString(5,user.getM_Uphone());
            m_pStmt.setInt(6,user.getM_UcreditGrade());
            m_pStmt.setString(7,user.getM_Utype());
            m_pStmt.setString(8,user.getM_UmentorName());
            m_pStmt.setString(9,user.getM_UmentorPhone());
            m_pStmt.setString(10,user.getM_UmajorClass());

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
     * @Description: 用户修改个人信息
     * @Param user
     * @Return: void
     */
    public int changeUserInfo( User user)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        try {
            m_con = JDBCUtils.getM_connection();
            String sql = "UPDATE user SET u_name=?, u_email=?, u_phone=?, u_credit_grade=?, u_mentor_name=?, u_mentor_phone=?, u_major_class=? " +
                         "WHERE u_no = ?";
            m_pStmt = m_con.prepareStatement(sql);

            //替换参数，从1开始
            m_pStmt.setString(1,user.getM_Uname());
            m_pStmt.setString(2,user.getM_Uemail());
            m_pStmt.setString(3,user.getM_Uphone());
            m_pStmt.setInt(4,user.getM_UcreditGrade());
            m_pStmt.setString(5,user.getM_UmentorName());
            m_pStmt.setString(6,user.getM_Uphone());
            m_pStmt.setString(7,user.getM_UmajorClass());
            m_pStmt.setString(8,user.getM_Uno());

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
     * @Description: 通过用户学工号获取用户对象
     * @Param u_no
     * @Return: bean.User
     */
    public User getUserByNo(String u_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        User user = new User();
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT * FROM user WHERE u_no = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, u_no);
            m_rs = m_pStmt.executeQuery();

            if (m_rs.next())
            {
                user = new User(m_rs.getString("u_no"), m_rs.getString("u_name"), m_rs.getString("u_wechatid"), m_rs.getString("u_email"),
                        m_rs.getString("u_phone"), m_rs.getInt("u_credit_grade"), m_rs.getString("u_type"), m_rs.getString("u_mentor_name"),
                        m_rs.getString("u_mentor_phone"), m_rs.getString("u_major_class"));
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
        return  user;

    }
}
