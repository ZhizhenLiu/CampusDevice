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
        sql = "SELECT * FROM device ORDER BY d_borrowed_times DESC LIMIT 0,10";
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
                device.setD_photo(rs.getString("d_photo"));
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

    /*
     * @Description: 查询首页所有设备
     * @Param page  count
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getAllDeviceByPage(int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Device> deviceList = new ArrayList<>();
        con = JDBCUtils.getConnection();
        sql = "SELECT * FROM device LIMIT ?, ?";
        try
        {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, (page - 1) * count);
            pStmt.setInt(2, count);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_model(rs.getString("d_model"));
                device.setD_name(rs.getString("d_name"));
                device.setD_state(rs.getString("d_state"));
                device.setD_photo(rs.getString("d_photo"));
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
            String sql = "SELECT d_no, d_name, d_model, d_save_site, d_state, d_photo, " +
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
                device.setD_model(rs.getString("d_model"));
                device.setD_saveSite(rs.getString("d_save_site"));
                device.setD_state(rs.getString("d_state"));
                device.setA_name(rs.getString("a_name"));
                device.setA_phone(rs.getString("a_phone"));
                device.setD_photo(rs.getString("d_photo"));
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
    public int setDeviceState(String d_no, String d_state)
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
            pStmt.setString(1, d_state);
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
        Device device = null;

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
                device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_model(rs.getString("d_model"));
                device.setD_saveSite(rs.getString("d_save_site"));
                device.setA_no(rs.getString("a_no"));
                device.setD_factoryNo(rs.getString("d_factory_no"));
                device.setD_state(rs.getString("d_state"));
                device.setD_storeDate(rs.getString("d_store_date"));
                device.setD_borrowedTimes(rs.getInt("d_borrowed_times"));
                device.setD_photo(rs.getString("d_photo"));
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
     * @Param keyword  page  count
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getDeviceByPageWithKeyword(String keyword, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Device> deviceList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql =   "SELECT * FROM device WHERE d_name LIKE ? OR d_no LIKE ? OR d_save_site LIKE ? " +
                    "LIMIT ?, ? ";
            pStmt = con.prepareStatement(sql);

            pStmt.setString(1, keyword);
            pStmt.setString(2, keyword);
            pStmt.setString(3, keyword);
            pStmt.setInt(4, (page - 1) * count);
            pStmt.setInt(5, count);

            //替换参数，从1开始
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_model(rs.getString("d_model"));
                device.setD_saveSite(rs.getString("d_save_site"));
                device.setD_state(rs.getString("d_state"));
                device.setD_storeDate(rs.getString("d_store_date"));
                device.setD_borrowedTimes(rs.getInt("d_borrowed_times"));
                device.setD_photo(rs.getString("d_photo"));
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

    /*
     * @Description: 管理员通过关键词获取管辖范围内设备信息
     * @Param a_no  keyword  page  count
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getDeviceOfAdminByPageWithKeyword(String a_no, String keyword, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Device> deviceList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql =   "SELECT * FROM device WHERE (d_name LIKE ? OR d_no LIKE ? OR d_save_site LIKE ?) " +
                    "AND a_no = ? " +
                    "LIMIT ?, ? ";
            pStmt = con.prepareStatement(sql);

            pStmt.setString(1, keyword);
            pStmt.setString(2, keyword);
            pStmt.setString(3, keyword);
            pStmt.setString(4, a_no);
            pStmt.setInt(5, (page - 1) * count);
            pStmt.setInt(6, count);

            //替换参数，从1开始
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_name(rs.getString("d_name"));
                device.setD_model(rs.getString("d_model"));
                device.setD_saveSite(rs.getString("d_save_site"));
                device.setD_state(rs.getString("d_state"));
                device.setD_storeDate(rs.getString("d_store_date"));
                device.setD_borrowedTimes(rs.getInt("d_borrowed_times"));
                device.setD_photo(rs.getString("d_photo"));
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

    /*
     * @Description: 设置设备图片url
     * @Param url
     * @Return: int
     */
    public int setDeviceImgUrl(String d_no, String url)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE device SET d_photo = ? WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, url);
            pStmt.setString(2, d_no);

            System.out.println(url + " " + d_no);
            //替换参数，从1开始
            flag = pStmt.executeUpdate();
            System.out.println(flag);
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
     * @Description: 修改设备名称
     * @Param d_no  d_name
     * @Return: int
     */
    public int setDeviceName(String d_no, String d_name)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            String sql = "UPDATE device SET d_name = ? WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_name);
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
     * @Description: 修改设备型号
     * @Param d_no  d_model
     * @Return: int
     */
    public int setDeviceModel(String d_no, String d_model)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            String sql = "UPDATE device SET d_model = ? WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_model);
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
     * @Description: 添加设备
     * @Param device
     * @Return: int
     */
    public int addDevice(Device device)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            String sql = "INSERT INTO device(d_no, d_name, d_model, d_save_site, a_no, d_factory_no, d_state, d_store_date, d_borrowed_times) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, device.getD_no());
            pStmt.setString(2, device.getD_name());
            pStmt.setString(3, device.getD_model());
            pStmt.setString(4, device.getD_saveSite());
            pStmt.setString(5, device.getA_no());
            pStmt.setString(6, device.getD_factoryNo());
            pStmt.setString(7, device.getD_state());
            pStmt.setString(8, device.getD_storeDate());
            pStmt.setInt(9, 0);

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
     * @Description: 删除设备
     * @Param d_no
     * @Return: int
     */
    public int deleteDevice(String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            String sql = "DELETE FROM device WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1,  d_no);

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
     * @Description: 管理员获取管辖范围内管理设备列表: 分页
     * @Param a_no  page  count
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getDeviceOfAdminByPage(String a_no, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Device> deviceList = new ArrayList<>();
        con = JDBCUtils.getConnection();
        sql = "SELECT * FROM device WHERE a_no = ? LIMIT ?, ?";
        try
        {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, a_no);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_model(rs.getString("d_model"));
                device.setD_name(rs.getString("d_name"));
                device.setD_state(rs.getString("d_state"));
                device.setD_photo(rs.getString("d_photo"));
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


    /*
     * @Description: 管理员获取管辖范围内管理设备列表：所有
     * @Param a_no
     * @Return: java.util.List<bean.Device>
     */
    public List<Device> getAllDeviceOfAdmin(String a_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Device> deviceList = new ArrayList<>();
        con = JDBCUtils.getConnection();
        sql = "SELECT * FROM device WHERE a_no = ? ";
        try
        {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, a_no);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            while (rs.next())
            {
                Device device = new Device();
                device.setD_no(rs.getString("d_no"));
                device.setD_model(rs.getString("d_model"));
                device.setD_name(rs.getString("d_name"));
                device.setD_state(rs.getString("d_state"));
                device.setD_photo(rs.getString("d_photo"));
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
