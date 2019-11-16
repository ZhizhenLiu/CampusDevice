package dao.impl;

import bean.Device;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.DeviceDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceDaoImpl implements DeviceDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;
    /*
     * @Description: 分页查询首页热门设备
     * @Param page:页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    public JSONObject getHotDeviceByPage(int page, int count)
    {
        JSONObject result = new JSONObject();
        JSONArray deviceList = new JSONArray();
        JDBCUtils.init(rs, pStmt, con);

        con = JDBCUtils.getConnection();
        sql = "select * from device limit ?,?";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, (page-1)*count);
            pStmt.setInt(2, count);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            if (rs.next())
            {
                //有的话记录查询状态为1：成功
                result.put("state",1);
                do {
                    deviceList.add(
                            new Device(rs.getInt("d_no"), rs.getString("a_no"), rs.getString("d_state"), rs.getInt("d_borrowed_times"),
                                    rs.getString("d_name"), rs.getString("d_important_param"), rs.getString("d_main_use"), rs.getString("d_save_site"))
                    );
                }
                while (rs.next());
                result.put("device",deviceList);
            }
            else
            {
                //不存在记录，查询状态为0：失败
                result.put("flag", 0);
                result.put("errMsg","未能获取到设备信息");
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

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getDeviceDetails(int deviceNo)
    {
        JDBCUtils.init(rs, pStmt, con);
        JSONObject result = new JSONObject();
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT" +
                    "d_no," +
                    "d_name," +
                    "d_main_use," +
                    "d_important_param," +
                    "d_save_site," +
                    "( SELECT a_name FROM administrator a1 WHERE a1.a_no = d.a_no ) a_name," +
                    "( SELECT a_phone FROM administrator a2 WHERE a2.a_no = d.a_no ) a_phone " +
                    "FROM" +
                    "device d " +
                    "WHERE" +
                    "d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, deviceNo);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                result.put("d_no",rs.getString("d_no"));
                result.put("d_name",rs.getString("d_name"));
                result.put("d_main_use",rs.getString("d_main_use"));
                result.put("d_important_param",rs.getString("d_important_param"));
                result.put("d_save_site",rs.getString("d_save_site"));
                result.put("a_name",rs.getString("a_name"));
                result.put("a_phone",rs.getString("a_phone"));
                result.put("flag",1);
            }
            else
            {
                result.put("flag",0);
                result.put("errmsg","查询不到对应的设备");
            }
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
     * @Description: 改变设备状态
     * @Param status  d_no
     * @Return: int
     */
    public int changeDeviceStatus(String status, int d_no)
    {
        JDBCUtils.init(rs, pStmt, con);
        int result = 0;
        try {
            con = JDBCUtils.getConnection();
            String sql = "UPDATE device SET d_state = ? WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, status);
            pStmt.setInt(2, d_no);

            //更新状态
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
}
