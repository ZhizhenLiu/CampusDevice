package service.impl;

import bean.Borrow;
import bean.Device;
import bean.Reservation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.*;
import dao.impl.*;
import service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao m_adminDao = new AdminDaoImpl();
    private UserDao m_userDao = new UserDaoImpl();
    private DeviceDao m_deviceDao = new DeviceDaoImpl();
    private ReservationDao m_reservationDao = new ReservationDaoImpl();
    private BorrowDao m_borrowDao = new BorrowDaoImpl();
    private ReturnDeviceDao m_returnDeviceDao = new ReturnDeviceDaoImpl();
    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservedDevice(String wechatID)
    {
        //获取主键，通过主键查询
        int a_no = m_adminDao.getAdminByWechatID(wechatID).getM_Ano();
        JSONObject info = new JSONObject();
        List<Device> deviceList = m_reservationDao.getReservedDevice(a_no);
        info.put("flag", 1);
        info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        return info;
    }

    /*
     * @Description: 获取某一个设备的预约队列
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservationDetail(String d_no)
    {
        JSONObject info = new JSONObject();
        List<Reservation> reservationList = m_reservationDao.getReservationDetail(d_no);
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
        String borrowDate = m_reservationDao.getBorrowDate(u_no, d_no);
        String returnDate = m_reservationDao.getReturnDate(u_no, d_no);
        System.out.println(d_no+"设备状态更改"+u_no+": "+ m_deviceDao.setDeviceState("外借", d_no));
        System.out.println(m_reservationDao.reserveSucceed(u_no, d_no));
        int flag = m_borrowDao.confirmBorrow(u_no, d_no, borrowDate, returnDate);
        info.put("flag", flag);
        if (flag == 0)
        {
            info.put("errmsg", "确认设备归还失败");
        }
        return info;
    }

    /*
     * @Description: 通过微信唯一标识获得对应管理范围设备外借预期信息
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getOverDue(String wechatID)
    {
        //获取主键，通过主键查询
        int a_no = m_adminDao.getAdminByWechatID(wechatID).getM_Ano();

        //设置所有逾期设备状态为 -1 表示逾期未还
        m_borrowDao.setAllStateOverDue();

        JSONObject info = new JSONObject();
        List<Borrow> borrowList = m_borrowDao.getOverDueList(a_no);
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
     * @Param wechatID  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmReturn(String wechatID, int d_no)
    {
        JSONObject info = new JSONObject();
        String u_no = m_userDao.getUserByWechatID(wechatID).getM_Uno();

        //获取用户借用记录的编号，唯一标识一条记录
        int b_no = m_borrowDao.getBorrowNo(u_no, d_no);

        //归还设备
        int flag= m_returnDeviceDao.ReturnDevice(u_no, d_no, b_no);
        info.put("flag", flag);
        if (flag == 0)
        {
            info.put("errmsg","确认归还设备失败");
        }
        return info;
    }
}
