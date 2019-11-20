package dao;

import bean.Device;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface DeviceDao {
    /*
     * @Description: 分页查询首页热门设备
     * @Param  page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getHotDeviceByPage(int page, int count);

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    Device getDeviceDetails(int deviceNo);

    /*
     * @Description: 改变设备状态
     * @Param status  d_no
     * @Return: int
     */
    int setDeviceState(String status, int d_no);

    /*
     * @Description: 获取设备状态
     * @Param d_no
     * @Return: java.lang.String
     */
    String getDeviceState(int d_no);

}
