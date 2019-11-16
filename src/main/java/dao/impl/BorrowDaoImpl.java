package dao.impl;

import bean.Device;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BorrowDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowDaoImpl implements BorrowDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmBorrow(String u_no, int d_no, String borrowDate, String returnDate)
    {
        JSONObject result = new JSONObject();
        JDBCUtils.init(rs, pStmt, con);

        con = JDBCUtils.getConnection();
        sql = "INSERT INTO borrow(d_no, u_no, b_borrow_date, b_return_date) " +
              "VALUES (?, ?, ?, ?)";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, u_no);
            pStmt.setInt(2, d_no);
            pStmt.setString(3, borrowDate);
            pStmt.setString(4, returnDate);

            //返回执行状态
            int flag = pStmt.executeUpdate();
            result.put("flag",flag);

            if (flag == 0)
            {
                result.put("errmsg", "确认租借失败");
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
