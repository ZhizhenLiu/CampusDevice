package service;

import bean.Admin;
import com.alibaba.fastjson.JSONObject;

public interface AdminService {

    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservation(String wechatId);

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservationDetail(String deviceNo);

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject confirmBorrow(String u_no, int d_no);

}
