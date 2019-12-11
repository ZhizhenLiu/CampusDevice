package dao;

import bean.Device;
import bean.Reservation;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;

public interface ReservationDao
{
    /*
     * @Description: 根据借用编号获取借用对象
     * @Param r_no
     * @Return: bean.Reservation
     */
    Reservation getReservation(int r_no);

    /*
     * @Description: 判断用户是否正在预约该设备的队列中
     * @Param u_no  d_no
     * @Return: boolean
     */
    boolean isReserving(String u_no, String d_no);

    /*
     * @Description: 用户预约设备
     * @Param d_no  u_no  startDate  returnDate
     * @Return: int
     */
    int reserveDevice(String d_no, String u_no, String startDate, String returnDate);

    /*
     * @Description: 用户取消预约
     * @Param r_no
     * @Return: int
     */
    int cancelReservation(int r_no);

    /*
     * @Description: 通过管理员编号获取管理员管辖范围内的有人预约的设备
     * @Param a_no
     * @Return: java.util.List<bean.Reservation>
     */
    List<Reservation> handleReservation(String a_no);

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param d_no
     * @Return: java.util.List<bean.Reservation>
     */
    List<Reservation> handleReservationDetail(String d_no);

    /*
     * @Description: 管理员编辑修改用户预约，开始协商
     * @Param r_no  startDate  endDate
     * @Return: int
     */
    int editReservation(int r_no, String startDate, String endDate, String feedBack);

    /*
     * @Description: 获取某个用户预约设备的开始日期
     * @Param u_no  d_no
     * @Return: java.lang.String
     */
    String getStartDate(String u_no, String d_no);

    /*
     * @Description: 获取某个用户预约设备的归还日期
     * @Param u_no  d_no
     * @Return: java.lang.String
     */
    String getReturnDate(String u_no, String d_no);

    /*
     * @Description: 预约中设备预约成功：（0:预约中 -1：预约被拒绝 1：预约成功）
     * @Param u_no  d_no
     * @Return: int
     */
    int confirmReserve(int r_no);

    /*
     * @Description: 预约中设备预约被拒绝：（0:预约中 -1：预约被拒绝 1：预约成功）
     * @Param u_no  d_no  r_feedBack
     * @Return: int
     */
    int refuseReserve(int r_no, String r_feedBack);

    /*
     * @Description: 用户查看申请中预约：预约中、协商中
     * @Param u_no
     * @Return: java.util.List<bean.Reservation>
     */
    List<Reservation> getUnfinishedReservationByPage(String u_no, int page, int count);

    /*
     * @Description: 用户查看已完成预约：成功，拒绝、取消
     * @Param u_no  page  count
     * @Return: java.util.List<bean.Reservation>
     */
    List<Reservation> getFinishedReservationByPage(String u_no, int page, int count);

    /*
     * @Description: 获取用户预约设备的数量
     * @Param u_no
     * @Return: int
     */
    int getReservationNum(String u_no);

    /*
     * @Description: 设置预约状态
     * @Param r_no  r_state
     * @Return: int
     */
    int setReservationState(int r_no, int r_state);

}
