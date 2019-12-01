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

public class DeviceDaoImpl implements DeviceDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 分页查询首页热门设备
     * @Param  page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getHotDevice()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Device> deviceList = new ArrayList<>();
        con = JDBCUtils.getConnection();
        sql = "SELECT * FROM device ORDER BY d_borrowed_times LIMIT 0,10";
        try
        {
            pStmt = con.prepareStatement(sql);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_model(rs.getString("d_model"));
                device.setD_name(rs.getString("d_name"));
                device.setD_state(rs.getString("d_state"));
                deviceList.add(device);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return deviceList;
    }

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: bean.Device
     */
    public Device getDeviceDetails(String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        Device device = null;
        try
        {
            con = JDBCUtils.getConnection();
            String sql = "SELECT d_no,d_name,d_main_use,d_important_param,d_save_site,d_state," +
                    "(SELECT a_name FROM administrator a1 WHERE a1.a_no = d.a_no ) a_name, " +
                    "(SELECT a_phone FROM administrator a2 WHERE a2.a_no = d.a_no ) a_phone  " +
                    "FROM device d WHERE d_no = ? ";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_no);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_mainUse(rs.getString("d_main_use"));
                device.setD_importantParam(rs.getString("d_important_param"));
                device.setD_saveSite(rs.getString("d_save_site"));
                device.setD_state(rs.getString("d_state"));
                device.setA_name(rs.getString("a_name"));
                device.setA_phone(rs.getString("a_phone"));
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
        return device;
    }

    /*
     * @Description: 改变设备状态
     * @Param state  d_no
     * @Return: int
     */
    public int setDeviceState(String state, String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            String sql = "UPDATE device SET d_state = ? WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, state);
            pStmt.setString(2, d_no);

            //更新状态
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 获取设备状态
     * @Param d_no
     * @Return: java.lang.String
     */
    public String getDeviceState(String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        String result = "";
        try
        {
            con = JDBCUtils.getConnection();
            String sql = "SELECT d_state FROM device WHERE d_no= ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_no);

            //查询
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                result = rs.getString("d_state");
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
        return result;
    }

    /*
     * @Description: 设备借用次数增长
     * @Param d_no
     * @Return: int
     */
    public int addBorrowedTimes(String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE device SET d_borrowed_times = d_borrowed_times+1 WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_no);

            //更新状态
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 通过设备编号获取设备信息
     * @Param d_no
     * @Return: bean.Device
     */
    public Device getDeviceByNo(String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        Device device = new Device();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM device WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, d_no);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                device.setD_no(rs.getString("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_model(rs.getString("d_model"));
                device.setD_saveSite(rs.getString("d_save_site"));
                device.setA_no(rs.getString("a_no"));
                device.setD_factoryNo(rs.getString("d_factory_no"));
                device.setD_state(rs.getString("d_state"));
                device.setD_storeDate(rs.getString("d_store_date"));
                device.setD_borrowedTimes(rs.getInt("d_borrowed_times"));
                device.setD_importantParam(rs.getString("d_important_param"));
                device.setD_mainUse(rs.getString("d_main_use"));

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
        return device;
    }

    /*
     * @Description: 获取所有的设备信息
     * @Param
     * @Return: List<String>
     */
    @Override
    public List<String> getDevice()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<String> list = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM device";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                list.add(String.valueOf(rs.getInt("d_no")));
                list.add(rs.getString("a_no"));
                list.add(rs.getString("d_state"));
                list.add(String.valueOf(rs.getInt("d_borrowed_times")));
                list.add(rs.getString("d_name"));
                list.add(rs.getString("d_important_param"));
                list.add(rs.getString("d_main_use"));
                list.add(rs.getString("d_save_site"));
            }

            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return null;
    }

    /*
     * @Description: 通过关键词获取设备信息
     * @Param keyword
     * @Return: List<Device>
     */
    @Override
    public List<Device> getDeviceByKeyword(String keyword)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Device> deviceList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            //参数不太好用setString替换，直接字符串代替也没错
            sql = "select * from device where d_name like '%" + keyword + "%' or d_important_param like '%" + keyword + "%' or d_main_use like '%" + keyword + "%'";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_model(rs.getString("d_model"));
                device.setD_saveSite(rs.getString("d_save_site"));
                device.setA_no(rs.getString("a_no"));
                device.setD_factoryNo(rs.getString("d_factory_no"));
                device.setD_state(rs.getString("d_state"));
                device.setD_storeDate(rs.getString("d_store_date"));
                device.setD_borrowedTimes(rs.getInt("d_borrowed_times"));
                device.setD_importantParam(rs.getString("d_important_param"));
                device.setD_mainUse(rs.getString("d_main_use"));
                deviceList.add(device);
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
        return deviceList;
    }
}
