package dao;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface ReservationDao {


    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject reserveDevice(int deviceNo, String wechatId, Date startDate, Date endDate);

    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservation(String wechatId);

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getReservationDetail(String deviceNo);

    /*
     * @Description: 获取某个用户预约设备的开始日期
     * @Param u_no  d_no
     * @Return: java.lang.String
     */
    String getBorrowDate(String u_no, int d_no);

    /*
     * @Description: 获取某个用户预约设备的归还日期
     * @Param u_no  d_no
     * @Return: java.lang.String
     */
    String getReturnDate(String u_no, int d_no);

    /*
     * @Description: 移除预约队列：1、预约成功 2、预约被管理员拒绝
     * @Param u_no  d_no
     * @Return: int
     */
    int removeReservation(String u_no, int d_no);

}
