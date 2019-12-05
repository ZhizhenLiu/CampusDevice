package service.impl;

import bean.Borrow;
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
    private TrackDao trackDao = new TrackDaoImpl();

    /*
     * @Description: 登陆校验。判断管理员是否存在
     * @Param wechatID
     * @Return: boolean
     */
    public boolean isAdminExist(String wechatID)
    {
        if (adminDao.getAdminByWechatID(wechatID) == null)
        {
            return false;
        }
        return true;
    }

    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservedDeviceByPage(String wechatID, int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        //获取主键，通过主键查询
        String a_no = adminDao.getAdminByWechatID(wechatID).getA_no();
        List<Reservation> reservationList = reservationDao.handleReservationByPage(a_no, page, count);

        if (reservationList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("当前页数暂时没有预约中设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("device", JSONArray.parseArray(JSON.toJSONString(reservationList)));
        }
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 通过标识获取管理员管辖范围内外借设备
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getBorrowedDeviceByPage(String wechatID, int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        //获取主键，通过主键查询
        String a_no = adminDao.getAdminByWechatID(wechatID).getA_no();
        List<Borrow> borrowList = borrowDao.getBorrowListByPage(a_no, page, count);
        if (borrowList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("当前页数暂时没有外借中设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("device", JSONArray.parseArray(JSON.toJSONString(borrowList)));
        }
        info.put("errMsg", errMsg);

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
        List<Reservation> reservationList = reservationDao.handleReservationDetail(d_no);
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
        String u_name = reservation.getU_name() + reservation.getU_type();
        String d_no = reservation.getD_no();
        String d_name = reservation.getD_name();
        String d_saveSite = reservation.getD_saveSite();
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
            flag = messageDao.sendMessage(u_no, u_name+"，管理员已批准你的预约，设备："+d_name+"。请在今日内到"+d_saveSite+"领取设备");
            if (flag == 0)
            {
                info.put("flag", 0);
                errMsg.add("发送成功借用提示失败");
            }
            List<String> userNoListList = trackDao.getTrackingUserNoList(d_no);
            for (String userNo : userNoListList)
            {
                User user = userDao.getUserByNo(userNo);
                messageDao.sendMessage(userNo, u_name+"，你跟踪的设备："+d_name+"。已经归还，如有需要，请尽快预约");
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
        info.put("flag", 1);

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
            info.put("flag", flag);
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
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getOverDueByPage(String wechatID, int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        //获取主键，通过主键查询
        String a_no = adminDao.getAdminByWechatID(wechatID).getA_no();

        //设置所有逾期设备状态为 -1 表示逾期未还
        borrowDao.setAllOverDueState();

        List<Borrow> borrowList = borrowDao.getOverDueListByPage(a_no, page, count);
        if (borrowList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("当前页数没有逾期未还设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("borrow", JSONArray.parseArray(JSON.toJSONString(borrowList)));
        }
        info.put("errMsg", errMsg);

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
        String u_name = userDao.getUserByNo(u_no).getU_name() + userDao.getUserByNo(u_no).getU_type();
        String d_no = borrow.getD_no();
        String d_name = deviceDao.getDeviceByNo(d_no).getD_name();

        int flag = borrowDao.returnOnTime(b_no);
        if (flag == 0)
        {
            errMsg.add("修改借用记录状态为归还失败");
        }
        flag = messageDao.sendMessage(u_no, u_name +"，管理员已确认你归还设备："+ d_name+"，感谢你的合作");
        if(flag == 0)
        {
            errMsg.add("发送提示信息失败");
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
