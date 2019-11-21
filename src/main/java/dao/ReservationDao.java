package dao;

import bean.Device;
import bean.Reservation;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

public interface ReservationDao {


    /*
     * @Description: 用户预约设备
     * @Param d_no  wechatId  startDate  returnDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    int reserveDevice(int d_no, String wechatId, Date startDate, Date returnDate);

    /*
     * @Description: 通过管理员编号获取管理员管辖范围内的有人预约的设备
     * @Param a_no
     * @Return: java.util.List<bean.Device>
     */
    List<Device> getReservedDevice(int a_no);

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param d_no
     * @Return: java.util.List<bean.Reservation>
     */
    List<Reservation> getReservationDetail(String d_no);

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
     * @Description: 预约中设备预约成功：（0:预约中 -1：预约被拒绝 1：预约成功）
     * @Param u_no  d_no
     * @Return: int
     */
    int reserveSucceed(String u_no, int d_no);

    /*
     * @Description: 用户查看我的预约
     * @Param u_no
     * @Return: java.util.List<bean.Reservation>
     */
    List<Reservation> getReservation(String u_no);

    //获取用户预约设备的数量
    int getReservationNum(String u_no);

}
