package service;

import bean.User;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface UserService {

    /*
     * @Description: 通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    User getUserByWechatId(String wechatId);

    /*
     * @Description: 通过微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getUserBywechatId(String wechatId);

    /*
     * @Description: 用户首次登陆添加到user表中
     * @Param user
     * @Return: JSONObject
     */
    JSONObject registerUser(User user);

    /*
     * @Description: 分页查询首页热门设备
     * @Param page:页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    JSONObject getHotDeviceByPage(int page, int count);


    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getDeviceDetails(int deviceNo);

    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: int : 1为成功、0为失败
     */
    JSONObject reserveDevice(int deviceNo, String wechatId, Date startDate, Date endDate);



}
