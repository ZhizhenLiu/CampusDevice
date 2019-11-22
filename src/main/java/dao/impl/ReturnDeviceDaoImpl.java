package dao.impl;

import dao.ReturnDeviceDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnDeviceDaoImpl implements ReturnDeviceDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    /*
     * @Description: 归还设备
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public int returnDevice(String u_no, int d_no, int b_bo)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        int flag = 0;

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "INSERT INTO return_device(u_no, d_no, b_no, rd_date) " +
                    "VALUES (?, ?, ?, CURRENT_DATE )";
            m_pStmt = m_con.prepareStatement(m_sql);
            m_pStmt.setString(1, u_no);
            m_pStmt.setInt(2, d_no);
            m_pStmt.setInt(3, b_bo);

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
}
