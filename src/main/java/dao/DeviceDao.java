package dao;

import bean.Device;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface DeviceDao
{
    /*
     * @Description: 查询首页热门设备
     * @Param
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getHotDevice();

    /*
     * @Description: 查询首页所有设备
     * @Param page  count
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getAllDeviceByPage(int page, int count);

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    Device getDeviceDetails(String d_no);

    /*
     * @Description: 改变设备状态
     * @Param d_no  d_state
     * @Return: int
     */
    int setDeviceState(String d_no, String d_state);

    /*
     * @Description: 获取设备状态
     * @Param d_no
     * @Return: java.lang.String
     */
    String getDeviceState(String d_no);

    /*
     * @Description: 设备借用次数增长
     * @Param d_no
     * @Return: int
     */
    int addBorrowedTimes(String d_no);

    /*
     * @Description: 通过设备编号获取设备信息
     * @Param d_no
     * @Return: bean.Device
     */
    Device getDeviceByNo(String d_no);

    /*
     * @Description: 获取所有的设备信息
     * @Param
     * @Return: List<String>
     */
    List<String> getDevice();

    /*
     * @Description: 通过关键词获取设备信息
     * @Param keyword  page  count
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getDeviceByPageWithKeyword(String keyword, int page, int count);

    /*
     * @Description: 管理员通过关键词获取管辖范围内设备信息
     * @Param a_no  keyword  page  count
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getDeviceOfAdminByPageWithKeyword(String a_no, String keyword, int page, int count);

    /*
     * @Description: 设置设备图片url
     * @Param url
     * @Return: int
     */
    int setDeviceImgUrl(String d_no, String url);

    /*
     * @Description: 修改设备名称
     * @Param d_no  d_name
     * @Return: int
     */
    int setDeviceName(String d_no, String d_name);

    /*
     * @Description: 修改设备型号
     * @Param d_no  d_model
     * @Return: int
     */
    int setDeviceModel(String d_no, String d_model);

    /*
     * @Description: 添加设备
     * @Param device
     * @Return: int
     */
    int addDevice(Device device);

    /*
     * @Description: 删除设备
     * @Param d_no
     * @Return: int
     */
    int deleteDevice(String d_no);

    /*
     * @Description: 管理员获取管辖范围内管理设备列表: 分页
     * @Param a_no  page  count
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getDeviceOfAdminByPage(String a_no, int page, int count);


    /*
     * @Description: 管理员获取管辖范围内管理设备列表：所有
     * @Param a_no
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getAllDeviceOfAdmin(String a_no);

}
