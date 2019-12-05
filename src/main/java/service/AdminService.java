package service;

import bean.Admin;
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
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservedDeviceByPage(String wechatID, int page, int count);

    /*
     * @Description: 通过标识获取管理员管辖范围内外借设备
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getBorrowedDeviceByPage(String wechatID, int page, int count);

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservationDetail(String d_no);

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
     * @Param b_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject confirmReturn(int b_no);

    /*
     * @Description: 管理员主动提醒逾期未还用户
     * @Param b_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject remindOverDue(int b_no);
}
