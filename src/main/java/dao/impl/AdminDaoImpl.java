package dao.impl;

import bean.Admin;
import dao.AdminDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    /*
     * @Description: 通过微信唯一标识获取管理员信息
     * @Param wechatId
     * @Return: bean.Admin
     */
    public Admin getAdminByWechatID(String wechatID)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "select * from administrator where a_wechatid = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1, wechatID);
            m_rs = m_pStmt.executeQuery();

            if (m_rs.next())
            {
                return new Admin(m_rs.getInt("a_no"), m_rs.getString("a_name"), m_rs.getString("a_wechatid"),
                    m_rs.getString("a_type"), m_rs.getString("a_phone"), m_rs.getString("a_emial"));
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
        return  null;
    }


}
