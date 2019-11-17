package dao.impl;

import com.alibaba.fastjson.JSONObject;
import dao.ReturnDeviceDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnDeviceDaoImpl implements ReturnDeviceDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 归还设备
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject ReturnDevice(String u_no, int d_no, int b_bo)
    {
        JSONObject result = new JSONObject();
        JDBCUtils.init(rs, pStmt, con);

        con = JDBCUtils.getConnection();
        sql = "INSERT INTO return_device(u_no, d_no, b_no, rd_date) " +
                "VALUES (?, ?, ?, CURRENT_DATE )";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, d_no);
            pStmt.setString(2, u_no);
            pStmt.setInt(3, b_bo);

            //返回执行状态
            int flag = pStmt.executeUpdate();
            result.put("flag",flag);

            if (flag == 0)
            {
                result.put("errmsg", "确认归还失败");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return result;
    }
}
