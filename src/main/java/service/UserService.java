package service;

import bean.User;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface UserService
{

    /*
     * @Description: 登陆校验，判断用户是否存在
     * @Param wechatID
     * @Return: boolean
     */
    boolean isUserExist(String wechatID);

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
     * @Description: 查询首页热门设备
     * @Param page:页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    JSONObject getHotDevice();

    /*
     * @Description: 分页查询所有设备
     * @Param page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getAllDeviceByPage(int page, int count);


    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getDeviceDetails(String d_no);

    /*
     * @Description: 用户预约设备
     * @Param d_no  wechatId  startDate  returnDate
     * @Return: int : 1为成功、0为失败
     */
    JSONObject reserveDevice(String d_no, String wechatId, String startDate, String returnDate);

    /*
     * @Description: 用户取消预约
     * @Param r_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject cancelReservation(int r_no);

    /*
     * @Description: 用户同意 修改预约
     * @Param r_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject agreeEditReservation(int r_no);

    /*
     * @Description: 逾期归还：-2  逾期借用: -1 借用中：0 归还：1
     * @Param wechatID  page  count  isFinished
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getBorrowRecordByPage(String wechatID, int page, int count, boolean isFinished);

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

    /*
     * @Description: 用户查看申请中预约 或 用户查看已完成预约
     * @Param wechatID  page  count  isFinished
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservationByPage(String wechatID, int page, int count, boolean isFinished);

    /*
     * @Description: 通过关键字检索查找设备
     * @Param keyword  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getDeviceByPageWithKeyword(String keyword, int page, int count);

    /*
     * @Description: 用户评价借用设备
     * @Param b_no  comment
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject commentOnDevice(int b_no, String comment);

    /*
     * @Description: 分页查询 获得设备评论
     * @Param d_no  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getCommentByPage(String d_no, int page, int count);

    /*
     * @Description: 用户跟踪设备
     * @Param wechatID  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject trackDevice(String wechatID, String d_no);
    
    /*
     * @Description: 用户取消跟踪设备
     * @Param t_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject cancelTrack(int t_no);
}
