package dao.impl;

import bean.Admin;
import bean.User;
import dao.AdminDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 通过微信唯一标识获取管理员信息
     * @Param wechatId
     * @Return: bean.Admin
     */
    public Admin getAdminByWechatID(String wechatID)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        Admin admin = null;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM administrator WHERE a_wechatid = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, wechatID);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                admin =  new Admin(rs.getString("a_no"), rs.getString("a_name"), rs.getString("a_wechatid"),
                        rs.getString("a_type"), rs.getString("a_phone"), rs.getString("a_email"));
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
        return admin;
    }

    /*
     * @Description: 通过a_no获取管理员
     * @Param a_no
     * @Return: bean.Admin
     */
    public Admin getAdminByNo(String a_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        Admin admin = null;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM administrator WHERE a_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, a_no);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                admin =  new Admin(rs.getString("a_no"), rs.getString("a_name"), rs.getString("a_wechatid"),
                        rs.getString("a_type"), rs.getString("a_phone"), rs.getString("a_emial"));
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
        return admin;
    }

    /*
     * @Description: 获取普通管理员列表
     * @Param
     * @Return: java.util.List<bean.Admin>
     */
    public List<Admin> getNormalAdminList()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Admin> adminList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM administrator WHERE a_type =  ? ";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, "院统管");
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Admin admin = new Admin();
                admin.setA_no(rs.getString("a_no"));
                admin.setA_name(rs.getString("a_name"));
                admin.setA_type(rs.getString("a_type"));
                admin.setA_phone(rs.getString("a_phone"));
                adminList.add(admin);
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
        return adminList;
    }

    /*
     * @Description: 设置用户为管理员: 初始密码设置为123456789
     * @Param user
     * @Return: int
     */
    public int setUserAsAdmin(User user)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO administrator(A_NO, A_PASSWORD, A_NAME, A_WECHATID, A_TYPE, A_PHONE, A_EMAIL) VALUES (?, MD5(?) , ?, ?, ?, ?, ?)";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, user.getU_no());
            pStmt.setString(2, user.getU_no());
            pStmt.setString(3, user.getU_name());
            pStmt.setString(4, user.getU_wechatID());
            pStmt.setString(5, "院统管");
            pStmt.setString(6, user.getU_phone());
            pStmt.setString(7, user.getU_email());

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
     * @Description: 删除管理员
     * @Param a_no
     * @Return: int
     */
    public int deleteAdmin(String a_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "DELETE FROM administrator WHERE a_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, a_no);

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

}
