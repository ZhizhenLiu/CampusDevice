package service.impl;

import com.alibaba.fastjson.JSONObject;
import dao.*;
import dao.impl.*;
import service.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private DeviceDao deviceDao = new DeviceDaoImpl();
    private ReservationDao reservationDao = new ReservationDaoImpl();
    private BorrowDao borrowDao = new BorrowDaoImpl();
    private ReturnDeviceDao returnDeviceDao = new ReturnDeviceDaoImpl();
    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservation(String wechatId)
    {
        return  reservationDao.getReservation(wechatId);
    }

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservationDetail(String deviceNo)
    {
        return  reservationDao.getReservationDetail(deviceNo);
    }

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmBorrow(String u_no, int d_no)
    {
        String borrowDate = reservationDao.getBorrowDate(u_no, d_no);
        String returnDate = reservationDao.getReturnDate(u_no, d_no);
        System.out.println(d_no+"设备状态更改"+u_no+": "+deviceDao.setDeviceState("外借", d_no));
        System.out.println(reservationDao.reserveSucceed(u_no, d_no));
        return borrowDao.confirmBorrow(u_no, d_no, borrowDate, returnDate);
    }

    /*
     * @Description: 通过微信唯一标识获得对应管理范围设备外借预期信息
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getOverDue(String wechatId)
    {
        int a_no = adminDao.getAdminByWechatId(wechatId).getA_no();

        //设置所有逾期设备状态为 -1 表示逾期未还
        borrowDao.setAllStateOverDue();
        return borrowDao.getOverDue(a_no);
    }

    /*
     * @Description: 管理员确认用户归还设备
     * @Param wechatId  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmReturn(String wechatId, int d_no)
    {
        String u_no = userDao.getUserByWechatId(wechatId).getU_no();

        //获取用户借用记录的编号，唯一标识一条记录
        int b_no = borrowDao.getBorrowNo(u_no, d_no);

        //归还设备
        return returnDeviceDao.ReturnDevice(u_no, d_no , b_no);
    }
}
