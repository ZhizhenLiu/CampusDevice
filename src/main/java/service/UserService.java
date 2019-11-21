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
    User getUserByWechatID(String wechatID);

    /*
     * @Description: 通过微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getJSONUserByWechatID(String wechatID);

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
     * @Param d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getDeviceDetails(int d_no);

    /*
     * @Description: 用户预约设备
     * @Param d_no  wechatId  startDate  returnDate
     * @Return: int : 1为成功、0为失败
     */
    JSONObject reserveDevice(int d_no, String wechatId, Date startDate, Date returnDate);

    /*
     * @Description: 查询用户借用的记录(借用中b_state=0，归还b_state=1,逾期未还b_state= -1)
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getBorrowRecord(String wechatID);

    /*
     * @Description: 获取用户的信用记录:分页查询
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getCreditRecordByPage(String wechatID, int page, int count);

    /*
     * @Description: 获取用户的接收到的消息:分页查询
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getMessageByPage(String wechatID, int page, int count);
}
