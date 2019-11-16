package dao;

import com.alibaba.fastjson.JSONObject;

public interface DeviceDao {
    /*
     * @Description: 分页查询首页热门设备
     * @Param page  count
     * @Return: java.util.List<bean.Device>
     */
    JSONObject getHotDeviceByPage(int page, int count);

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getDeviceDetails(int deviceNo);
}
