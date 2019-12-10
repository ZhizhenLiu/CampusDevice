package service.impl;

import bean.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.*;
import dao.impl.*;
import service.AdminService;
import utils.MessageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private CreditRecordDao creditRecordDao = new CreditRecordDaoImpl();
    private CreditRuleDao creditRuleDao = new CreditRuleDaoImpl();

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
    public JSONObject getReservedDevice(String wechatID)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        //获取主键，通过主键查询
        String a_no = adminDao.getAdminByWechatID(wechatID).getA_no();
        List<Reservation> reservationList = reservationDao.handleReservation(a_no);

        if (reservationList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("暂时没有预约中设备");
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
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getBorrowedDevice(String wechatID)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        //获取主键，通过主键查询
        String a_no = adminDao.getAdminByWechatID(wechatID).getA_no();
        List<Borrow> borrowList = borrowDao.getBorrowList(a_no);
        if (borrowList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("暂时没有外借中设备");
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
        JSONArray errMsg = new JSONArray();

        List<Reservation> reservationList = reservationDao.handleReservationDetail(d_no);
        if (reservationList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("该设备没有预约队列");        }
        else
        {
            info.put("flag", 1);
            info.put("reservation", JSONArray.parseArray(JSON.toJSONString(reservationList)));
        }
        return info;
    }

    /*
     * @Description: 管理员编辑修改用户预约，开始协商
     * @Param r_no  startDate  endDate  feedBack
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject editReservation(int r_no, String startDate, String endDate, String feedBack)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        Reservation reservation = reservationDao.getReservation(r_no);
        String u_no = reservation.getU_no();
        String u_name = reservation.getU_name() + reservation.getU_type();
        String d_name = reservation.getD_name();
        info.put("flag", 1);
        int flag = reservationDao.editReservation(r_no, startDate, endDate, feedBack);
        if (flag == 0)
        {
            info.put("flag", 0);
            errMsg.add("编辑修改预约失败");
        }
        flag = messageDao.sendMessage(u_no, u_name + "，你有一条来自管理员的预约协商：" + d_name + "，请在我的预约中查看详情");
        if (flag == 0)
        {
            info.put("flag", 0);
            errMsg.add("发送提示消息失败");
        }
        info.put("errMsg", errMsg);

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
        String u_name = reservation.getU_name() + (reservation.getU_type().equals("学生")?"同学":"老师");
        String d_name = reservation.getD_name();
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
            flag = messageDao.sendMessage(u_no, u_name + "，你的预约：" + d_name + "被拒绝。请在我的预约中查看详情");
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

        List<Borrow> borrowList = borrowDao.getOverDueList(a_no, page, count);
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
     * @Param b_no  rd_state
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject confirmReturn(int b_no, String rd_state, String comment)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        Borrow borrow = borrowDao.getBorrowByNo(b_no);
        String u_no = borrow.getU_no();
        User user = userDao.getUserByNo(u_no);
        String u_name = user.getU_name() + (user.getU_type().equals("学生")?"同学":"老师");
        String d_no = borrow.getD_no();
        String d_name = deviceDao.getDeviceByNo(d_no).getD_name();
        Date now = new Date();
        Date returnDate = new Date();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            returnDate = simpleDateFormat.parse(borrow.getB_returnDate());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        int flag = 1;

        //修改借用表状态
        flag = borrowDao.returnOnTime(b_no);
        if (flag == 0)
        {
            errMsg.add("修改借用记录状态为归还失败");
        }

        //添加到归还表
        flag = returnDeviceDao.returnDevice(u_no, d_no, b_no, rd_state, comment);
        if (flag == 0)
        {
            errMsg.add("确认设备归还失败");
        }
        else
        {
            if (rd_state.equals("正常") || rd_state.equals("其他"))
            {
                deviceDao.setDeviceState("在库", d_no);
                List<String> trackingUserNoList = trackDao.getTrackingUserNoList(d_no);
                for (String userNo : trackingUserNoList)
                {
                    flag = messageDao.sendMessage(userNo,  "你跟踪的设备："+ d_name+ "已经归还。如需借用，请及时预约");
                    if(flag == 0)
                    {
                        errMsg.add("发送提示信息失败");
                    }
                }
            }
            else  deviceDao.setDeviceState(rd_state, d_no);

            //修改信誉分
            if (returnDate.getTime() <= now.getTime() && rd_state.equals("正常"))
            {
                CreditRule creditRule = creditRuleDao.getCreditRule(1);
                creditRecordDao.updateCredit(u_no, creditRule.getCr_content(), user.getU_creditGrade(), creditRule.getCr_score());
                userDao.updateCreditGrade(u_no, creditRule.getCr_score());
            }

            flag = messageDao.sendMessage(u_no, u_name +"，管理员已确认你归还设备："+ d_name+"，感谢你的合作");
            if(flag == 0)
            {
                errMsg.add("发送提示信息失败");
            }

            flag = deviceDao.addBorrowedTimes(d_no);
            if (flag == 0)
            {
                errMsg.add("设备借用次数增长失败");
            }
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
        JSONArray errMsg = new JSONArray();

        //根据借用记录查找借用记录
        Borrow borrow = borrowDao.getBorrowByNo(b_no);
        String u_no = borrow.getU_no() + borrow.getU_type();
        String u_name = borrow.getU_name() + (borrow.getU_type().equals("学生")?"同学":"老师");
        String d_name = borrow.getD_name();
        String d_saveSite = borrow.getD_saveSite();

        MessageUtils.sendRemindMessage(borrow);
        int flag = messageDao.sendMessage(u_no, u_name + "，您好！您借用的设备:" + d_name + "，已经逾期未还，请在近日内归还到" + d_saveSite);
        if (flag == 0)
        {
            errMsg.add("发送提醒信息出错");
            info.put("flag", 0);
        }
        else info.put("flag", 1);
        info.put("errMsg", errMsg);

        return info;
    }
    /*
     * @Description: 设置设备图片url
     * @Param d_no  url
     * @Return: int
     */
    public JSONObject setDeviceImgUrl(String d_no, String url)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        int flag = deviceDao.setDeviceImgUrl(d_no, url);
        if (flag == 0)
        {
            errMsg.add("设置设备url错误");
            info.put("flag", 0);
        }
        else  info.put("flag", 1);
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 管理员获取反馈信息
     * @Param page,count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject getFeedbackByPage(int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        FeedbackDao feedbackDao = new FeedbackDaoImpl();
        List<Feedback> feedbackList = feedbackDao.getFeedbackByPage(page,count);
        if (feedbackList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("当前页数没有反馈信息了");
        }
        else
        {
            info.put("flag", 1);
            info.put("feedback", JSONArray.parseArray(JSON.toJSONString(feedbackList)));
        }
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 管理员回复用户的意见
     * @Param u_no,m_content,f_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject respondToUserFeedback(String m_content, int f_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        FeedbackDao feedbackDao = new FeedbackDaoImpl();
        int flag = feedbackDao.respondToUserFeedback(m_content,f_no);
        if (flag == -1 || flag == 0)
        {
            info.put("flag", 0);
            errMsg.add("对用户反馈信息进行回复错误");
        }
        else
        {
            info.put("flag", 1);
        }
        info.put("errMsg", errMsg);

        return info;
    }
}
