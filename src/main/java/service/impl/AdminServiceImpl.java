package service.impl;

import bean.Borrow;
import bean.Device;
import bean.Reservation;
import bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.*;
import dao.impl.*;
import service.AdminService;

import java.util.List;

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
    public JSONObject getReservedDevice(String wechatId)
    {
        //获取主键，通过主键查询
        int a_no = adminDao.getAdminByWechatId(wechatId).getA_no();
        JSONObject info = new JSONObject();
        List<Device> deviceList = reservationDao.getReservedDevice(a_no);
        info.put("flag", 1);
        info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        return info;
    }

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservationDetail(String deviceNo)
    {
        JSONObject info = new JSONObject();
        List<Reservation> reservationList = reservationDao.getReservationDetail(deviceNo);
        info.put("flag", 1);
        info.put("reservation", JSONArray.parseArray(JSON.toJSONString(reservationList)));
        return info;
    }

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmBorrow(String u_no, int d_no)
    {
        JSONObject info = new JSONObject();
        String borrowDate = reservationDao.getBorrowDate(u_no, d_no);
        String returnDate = reservationDao.getReturnDate(u_no, d_no);
        System.out.println(d_no+"设备状态更改"+u_no+": "+deviceDao.setDeviceState("外借", d_no));
        System.out.println(reservationDao.reserveSucceed(u_no, d_no));
        int flag = borrowDao.confirmBorrow(u_no, d_no, borrowDate, returnDate);
        info.put("flag", flag);
        if (flag == 0)
        {
            info.put("errmsg", "确认设备归还失败");
        }
        return info;
    }

    /*
     * @Description: 通过微信唯一标识获得对应管理范围设备外借预期信息
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getOverDue(String wechatId)
    {
        //获取主键，通过主键查询
        int a_no = adminDao.getAdminByWechatId(wechatId).getA_no();

        //设置所有逾期设备状态为 -1 表示逾期未还
        borrowDao.setAllStateOverDue();

        JSONObject info = new JSONObject();
        List<Borrow> borrowList = borrowDao.getOverDueList(a_no);
        if (borrowList.isEmpty())
        {
            info.put("flag", 0 );
            info.put("errmsg","没有逾期未还设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("borrow", borrowList);
        }
        return info;
    }

    /*
     * @Description: 管理员确认用户归还设备
     * @Param wechatId  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmReturn(String wechatId, int d_no)
    {
        JSONObject info = new JSONObject();
        String u_no = userDao.getUserByWechatID(wechatId).getU_no();

        //获取用户借用记录的编号，唯一标识一条记录
        int b_no = borrowDao.getBorrowNo(u_no, d_no);

        //归还设备
        int flag= returnDeviceDao.ReturnDevice(u_no, d_no, b_no);
        info.put("flag", flag);
        if (flag == 0)
        {
            info.put("errmsg","确认归还设备失败");
        }
        return info;
    }
}
