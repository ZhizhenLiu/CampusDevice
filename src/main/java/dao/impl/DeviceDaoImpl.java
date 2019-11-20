package dao.impl;

import bean.Device;
import dao.DeviceDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceDaoImpl implements DeviceDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 分页查询首页热门设备
     * @Param  page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getHotDeviceByPage(int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Device> deviceList = new ArrayList<>();
        con = JDBCUtils.getConnection();
        sql = "SELECT * FROM device LIMIT ?,?";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, (page-1)*count);
            pStmt.setInt(2, count);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Device device = new Device(rs.getInt("d_no"), rs.getString("a_no"), rs.getString("d_state"), rs.getInt("d_borrowed_times"),
                        rs.getString("d_name"), rs.getString("d_important_param"), rs.getString("d_main_use"), rs.getString("d_save_site"));
                deviceList.add(device);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return deviceList;
    }

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: bean.Device
     */
    public Device getDeviceDetails(int deviceNo)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        Device device = new Device();
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT d_no,d_name,d_main_use,d_important_param,d_save_site, " +
                         "(SELECT a_name FROM administrator a1 WHERE a1.a_no = d.a_no ) a_name, " +
                         "(SELECT a_phone FROM administrator a2 WHERE a2.a_no = d.a_no ) a_phone  " +
                         "FROM device d WHERE d_no = ? ";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, deviceNo);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                device.setD_no(rs.getInt("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_main_use(rs.getString("d_main_use"));
                device.setD_important_param(rs.getString("d_important_param"));
                device.setD_save_site(rs.getString("d_save_site"));
                device.setA_name(rs.getString("a_name"));
                device.setA_phone(rs.getString("a_phone"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return device;
    }

    /*
     * @Description: 改变设备状态
     * @Param status  d_no
     * @Return: int
     */
    public int setDeviceState(String status, int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

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

    /*
     * @Description: 获取设备状态
     * @Param d_no
     * @Return: java.lang.String
     */
    public String getDeviceState(int d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        String result = "";
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT d_state FROM device WHERE d_no= ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, d_no);

            //查询
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                result = rs.getString("d_state");
            }
            else
            {
                result = "不存在该设备";
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
}
