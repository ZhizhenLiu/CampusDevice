package dao.impl;

import bean.Admin;
import dao.AdminDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 通过微信唯一标识获取管理员信息
     * @Param wechatId
     * @Return: bean.Admin
     */
    public Admin getAdminByWechatId(String wechatId)
    {
        //初始化
        JDBCUtils.init(rs, pStmt, con);

        try {
            con = JDBCUtils.getConnection();
            sql = "select * from administrator where a_wechatid = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, wechatId);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                return new Admin(rs.getString("a_no"),rs.getString("a_name"),rs.getString("a_wechatid"),
                    rs.getString("a_type"),rs.getString("a_phone"),rs.getString("a_emial"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return  null;
    }


}
