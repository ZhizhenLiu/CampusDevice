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
import utils.MessageUtils;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao m_adminDao = new AdminDaoImpl();
    private UserDao m_userDao = new UserDaoImpl();
    private DeviceDao m_deviceDao = new DeviceDaoImpl();
    private ReservationDao m_reservationDao = new ReservationDaoImpl();
    private BorrowDao m_borrowDao = new BorrowDaoImpl();
    private ReturnDeviceDao m_returnDeviceDao = new ReturnDeviceDaoImpl();
    private MessageDao m_messageDao = new MessageDaoImpl();
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
    public JSONObject getReservationDetail(int d_no)
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
        JSONArray errmsg = new JSONArray();
        String state = m_deviceDao.getDeviceState(d_no);
        if (state.equals("在库"))
        {
            String borrowDate = m_reservationDao.getStartDate(u_no, d_no);
            String returnDate = m_reservationDao.getReturnDate(u_no, d_no);

            int flag = m_borrowDao.confirmBorrow(u_no, d_no, borrowDate, returnDate);
            flag += m_reservationDao.confirmReserve(u_no, d_no);
            info.put("flag", flag == 2? 1: 0);

            if (flag == 0)
            {
                errmsg.add("确认设备归还失败");
            }
        }
        else
        {
            info.put("flag", 0);
            errmsg.add("设备当前状态为"+state+", 暂不可借用");
        }
        info.put("errmsg",errmsg);
        return info;
    }

    /*
     * @Description: 管理员拒绝租借给某个用户
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject refuseBorrow(String u_no, int d_no, String r_feedBack)
    {
        JSONObject info = new JSONObject();
        JSONArray errmsg = new JSONArray();
        int flag = m_reservationDao.refuseReserve(u_no, d_no, r_feedBack);
        info.put("flag", flag);
        if (flag == 0)
        {
            errmsg.add("拒绝预约失败");
        }
        
        //反馈不为空
        if (r_feedBack != null)
        {
            flag = m_messageDao.sendMessage(u_no, r_feedBack);
            if (flag == 0)
            {
                errmsg.add("发送消息给用户失败");
            }
        }
        info.put("errmsg", errmsg);
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
    public JSONObject confirmReturn(String u_no, int d_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errmsg = new JSONArray();

        //获取用户借用记录的编号，唯一标识一条记录
        int b_no = m_borrowDao.getBorrowNo(u_no, d_no);
        if (b_no == 0)
        {
            info.put("flag", 0);
            errmsg.add("没有获取到设备借用记录");
        }
        else
        {
            int flag = m_borrowDao.returnBorrow(b_no);
            if (flag == 0)
            {
                errmsg.add("修改借用记录状态为归还失败");
            }
            //归还设备
            flag = m_returnDeviceDao.returnDevice(u_no, d_no, b_no);
            if (flag == 0)
            {
                errmsg.add("添加到已归还设备失败");
            }
            flag = m_deviceDao.setDeviceState("在库", d_no);
            if (flag == 0)
            {
                errmsg.add("修改设备状态失败");
            }
            flag = m_deviceDao.addBorrowedTimes(d_no);
            if (flag == 0)
            {
                errmsg.add("设备借用次数增长");
            }
            info.put("flag", flag);
        }
        info.put("errmsg", errmsg);
        return info;
    }

    /*
     * @Description: 管理员主动提醒逾期未还用户
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject remindOverDue(String u_no, int d_no)
    {
        JSONObject info = new JSONObject();
        User user = m_userDao.getUserByNo(u_no);
        Device device = m_deviceDao.getDeviceByNo(d_no);
        MessageUtils.sendRemindMessage(user.getM_Uphone(),user.getM_Uname(),device.getM_Dname());
        info.put("flag", 1);
        return info;
    }
}
