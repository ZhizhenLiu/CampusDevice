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
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    /*
     * @Description: 分页查询首页热门设备
     * @Param  page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getHotDeviceByPage(int page, int count)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        List<Device> deviceList = new ArrayList<>();
        m_con = JDBCUtils.getM_connection();
        m_sql = "SELECT * FROM device LIMIT ?,?";
        try
        {
            m_pStmt = m_con.prepareStatement(m_sql);
            m_pStmt.setInt(1, (page-1)*count);
            m_pStmt.setInt(2, count);
            m_rs = m_pStmt.executeQuery();

            //判断是否存在记录
            while (m_rs.next())
            {
                Device device = new Device(m_rs.getInt("d_no"), m_rs.getString("a_no"), m_rs.getString("d_state"), m_rs.getInt("d_borrowed_times"),
                        m_rs.getString("d_name"), m_rs.getString("d_important_param"), m_rs.getString("d_main_use"), m_rs.getString("d_save_site"));
                deviceList.add(device);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, m_pStmt, m_con);
        }
        return deviceList;
    }

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: bean.Device
     */
    public Device getDeviceDetails(int d_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        Device device = new Device();
        try
        {
            m_con = JDBCUtils.getM_connection();
            String sql = "SELECT d_no,d_name,d_main_use,d_important_param,d_save_site, " +
                         "(SELECT a_name FROM administrator a1 WHERE a1.a_no = d.a_no ) a_name, " +
                         "(SELECT a_phone FROM administrator a2 WHERE a2.a_no = d.a_no ) a_phone  " +
                         "FROM device d WHERE d_no = ? ";
            m_pStmt = m_con.prepareStatement(sql);
            m_pStmt.setInt(1, d_no);
            m_rs = m_pStmt.executeQuery();
            if (m_rs.next())
            {
                device.setM_Dno(m_rs.getInt("d_no"));
                device.setM_Dname(m_rs.getString("d_name"));
                device.setM_DmainUse(m_rs.getString("d_main_use"));
                device.setM_DimportantParam(m_rs.getString("d_important_param"));
                device.setM_DdeviceSite(m_rs.getString("d_save_site"));
                device.setM_Aname(m_rs.getString("a_name"));
                device.setM_Aphone(m_rs.getString("a_phone"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
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
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int result = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            String sql = "UPDATE device SET d_state = ? WHERE d_no = ?";
            m_pStmt = m_con.prepareStatement(sql);
            m_pStmt.setString(1, status);
            m_pStmt.setInt(2, d_no);

            //更新状态
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
     * @Description: 获取设备状态
     * @Param d_no
     * @Return: java.lang.String
     */
    public String getDeviceState(int d_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        String result = "";
        try
        {
            m_con = JDBCUtils.getM_connection();
            String sql = "SELECT d_state FROM device WHERE d_no= ?";
            m_pStmt = m_con.prepareStatement(sql);
            m_pStmt.setInt(1, d_no);

            //查询
            m_rs = m_pStmt.executeQuery();
            if (m_rs.next())
            {
                result = m_rs.getString("d_state");
            }
            else
            {
                result = "不存在该设备";
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
        return result;
    }
}
