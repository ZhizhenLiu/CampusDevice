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
     * @Param state  d_no
     * @Return: int
     */
    int setDeviceState(String state, String d_no);

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

    //修改自增编号的信息：alter table device auto_increment = 11;

    /*
     * @Description: 通过关键词获取设备信息
     * @Param keyword
     * @Return: List<Device>
     */
    List<Device> getDeviceByKeyword(String keyword);
}
