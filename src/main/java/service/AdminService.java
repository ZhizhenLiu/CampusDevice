package service;

import bean.Admin;
import com.alibaba.fastjson.JSONObject;

public interface AdminService {

    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservedDevice(String wechatID);

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservationDetail(String d_no);

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject confirmBorrow(String u_no, int d_no);

    /*
     * @Description: 通过微信唯一标识获得对应管理范围设备外借预期信息
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getOverDue(String wechatID);

    /*
     * @Description: 管理员确认用户归还设备
     * @Param wechatID  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject confirmReturn(String wechatID, int d_no);

}
