package service;

import bean.Device;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public interface AdminService
{

    /*
     * @Description: 登陆校验。判断管理员是否存在
     * @Param wechatID
     * @Return: boolean
     */
    boolean isAdminExist(String wechatID);

    /*
     * @Description: 获取管理员管辖范围内所有设备
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getAllDeviceOfAdmin(String wechatID, int page, int count);

    /*
     * @Description: 管理员通过关键字检索查找管辖范围内设备
     * @Param wechatID  keyword  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getDeviceByPageWithKeyword(String wechatID, String keyword, int page, int count);


    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservedDevice(String wechatID);

    /*
     * @Description: 通过标识获取管理员管辖范围内外借设备
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getBorrowedDevice(String wechatID);

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservationDetail(String d_no);

    /*
     * @Description: 管理员编辑修改用户预约，开始协商
     * @Param r_no  startDate  endDate  feedBack
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject editReservation(int r_no, String startDate, String endDate, String feedBack);

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param r_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject confirmBorrow(int r_no);

    /*
     * @Description: 管理员拒绝租借给某个用户
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject refuseBorrow(int r_no, String r_feedBack);

    /*
     * @Description: 通过微信唯一标识获得对应管理范围设备外借预期信息
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getOverDueByPage(String wechatID, int page, int count);

    /*
     * @Description: 管理员确认用户归还设备
     * @Param b_no  rd_state
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject confirmReturn(int b_no, String rd_state, String comment);

    /*
     * @Description: 管理员主动提醒逾期未还用户
     * @Param b_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject remindOverDue(int b_no);

    /*
     * @Description: 设置设备图片url
     * @Param d_no  url
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject setDeviceImgUrl(String d_no, String url);

    /*
     * @Description: 管理员获取反馈信息
     * @Param page,count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getFeedbackByPage(int page, int count);

    /*
     * @Description: 管理员回复用户的意见
     * @Param u_no,m_content,f_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject respondToUserFeedback(String m_content, int f_no);

    /*
     * @Description: 添加设备
     * @Param device
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject addDevice(Device device);

    /*
     * @Description: 管理员修改设备
     * @Param device
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject editDevice(Device device);
    
    /*
     * @Description: 管理员删除设备
     * @Param d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject deleteDevice(String d_no);

    /*
     * @Description: 校统管查看所有的用户和非管理员
     * @Param
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getUserAndNormalAdminList();

    /*
     * @Description: 设置用户为管理员
     * @Param u_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject setUserAsAdmin(String u_no);

    /*
     * @Description: 删除管理员
     * @Param a_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject deleteAdmin(String a_no);
}
