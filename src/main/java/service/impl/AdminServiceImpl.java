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

public class AdminServiceImpl implements AdminService
{
    private AdminDao adminDao = new AdminDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private DeviceDao deviceDao = new DeviceDaoImpl();
    private ReservationDao reservationDao = new ReservationDaoImpl();
    private BorrowDao borrowDao = new BorrowDaoImpl();
    private ReturnDeviceDao returnDeviceDao = new ReturnDeviceDaoImpl();
    private MessageDao messageDao = new MessageDaoImpl();

    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservedDevice(String wechatID)
    {
        //获取主键，通过主键查询
        int a_no = adminDao.getAdminByWechatID(wechatID).getA_no();
        JSONObject info = new JSONObject();
        List<Device> deviceList = reservationDao.getReservedDevice(a_no);
        info.put("flag", 1);
        info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        return info;
    }

    /*
     * @Description: 通过标识获取管理员管辖范围内外借设备
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getBorrowedDevice(String wechatID)
    {
        //获取主键，通过主键查询
        int a_no = adminDao.getAdminByWechatID(wechatID).getA_no();
        JSONObject info = new JSONObject();
        List<Borrow> borrowList = borrowDao.getBorrowList(a_no);
        info.put("flag", 1);
        info.put("borrow", borrowList);
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
        List<Reservation> reservationList = reservationDao.getReservationDetail(d_no);
        info.put("flag", 1);
        info.put("reservation", JSONArray.parseArray(JSON.toJSONString(reservationList)));
        return info;
    }

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmBorrow(int r_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();
        Reservation reservation = reservationDao.getReservation(r_no);
        String u_no = reservation.getU_no();
        int d_no = reservation.getD_no();
        String state = deviceDao.getDeviceState(d_no);

        info.put("flag", 1);
        if (state.equals("在库"))
        {
            String borrowDate = reservation.getR_startDate();
            String returnDate = reservation.getR_returnDate();

            // 预约状态置为1（成功）-> 设备状态修改外借 -> 借用表中插入记录
            int flag = reservationDao.confirmReserve(r_no);
            if (flag == 0)
            {
                info.put("flag", 0);
                errMsg.add("修改预约状态失败");
            }
            flag = deviceDao.setDeviceState("外借", d_no);
            if (flag == 0)
            {
                info.put("flag", 0);
                errMsg.add("设备状态修改外借失败");
            }
            flag = borrowDao.confirmBorrow(u_no, d_no, borrowDate, returnDate);
            if (flag == 0)
            {
                info.put("flag", 0);
                errMsg.add("借用表中插入记录失败");
            }
        }
        else
        {
            info.put("flag", 0);
            errMsg.add("设备当前状态为" + state + ", 暂不可借用");
        }
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 管理员拒绝租借给某个用户
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject refuseBorrow(int r_no, String r_feedBack)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();
        Reservation reservation = reservationDao.getReservation(r_no);
        String u_no = reservation.getU_no();

        int flag = reservationDao.refuseReserve(r_no, r_feedBack);
        info.put("flag", flag);
        if (flag == 0)
        {
            errMsg.add("拒绝预约失败");
        }

        //反馈不为空
        if (r_feedBack != null)
        {
            flag = messageDao.sendMessage(u_no, r_feedBack);
            if (flag == 0)
            {
                errMsg.add("发送消息给用户失败");
            }
        }
        info.put("errMsg", errMsg);
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
        int a_no = adminDao.getAdminByWechatID(wechatID).getA_no();

        //设置所有逾期设备状态为 -1 表示逾期未还
        borrowDao.setAllStateOverDue();

        JSONObject info = new JSONObject();
        List<Borrow> borrowList = borrowDao.getOverDueList(a_no);
        if (borrowList.isEmpty())
        {
            info.put("flag", 0);
            info.put("errMsg", "没有逾期未还设备");
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
    public JSONObject confirmReturn(int b_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();
        Borrow borrow = borrowDao.getBorrowByNo(b_no);
        String u_no = borrow.getU_no();
        int d_no = borrow.getD_no();

        int flag = borrowDao.returnBorrow(b_no);
        if (flag == 0)
        {
            errMsg.add("修改借用记录状态为归还失败");
        }
        //归还设备
        flag = returnDeviceDao.returnDevice(u_no, d_no, b_no);
        if (flag == 0)
        {
            errMsg.add("添加到已归还设备失败");
        }
        flag = deviceDao.setDeviceState("在库", d_no);
        if (flag == 0)
        {
            errMsg.add("修改设备状态失败");
        }
        flag = deviceDao.addBorrowedTimes(d_no);
        if (flag == 0)
        {
            errMsg.add("设备借用次数增长失败");
        }

        info.put("flag", flag);
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 管理员主动提醒逾期未还用户
     * @Param b_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject remindOverDue(int b_no)
    {
        JSONObject info = new JSONObject();

        //根据借用记录查找借用记录
        Borrow borrow = borrowDao.getBorrowByNo(b_no);
        MessageUtils.sendRemindMessage(borrow);
        info.put("flag", 1);
        return info;
    }
}
