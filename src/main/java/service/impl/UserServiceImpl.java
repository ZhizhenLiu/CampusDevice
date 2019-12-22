package service.impl;

import bean.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.*;
import dao.impl.*;
import service.UserService;
import utils.FormatCheckUtils;
import utils.TransformUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService
{
    private UserDao userDao = new UserDaoImpl();
    private ReservationDao reservationDao = new ReservationDaoImpl();
    private DeviceDao deviceDao = new DeviceDaoImpl();
    private BorrowDao borrowDao = new BorrowDaoImpl();
    private CreditRecordDao creditRecordDao = new CreditRecordDaoImpl();
    private MessageDao messageDao = new MessageDaoImpl();
    private CommentDao commentDao = new CommentDaoImpl();
    private TrackDao trackDao = new TrackDaoImpl();
    private CreditRuleDao creditRuleDao = new CreditRuleDaoImpl();
    private AcademyDao academyDao = new AcademyDaoImpl();
    private SpecialtyDao specialtyDao = new SpecialtyDaoImpl();

    /*
     * @Description: 登陆校验，判断用户是否存在
     * @Param wechatID
     * @Return: boolean
     */
    public boolean isUserExist(String wechatID)
    {
        if (userDao.getUserByWechatID(wechatID) == null)
        {
            return false;
        }
        return true;
    }

    /*
     * @Description: 通过用户微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getJSONUserByWechatID(String wechatID)
    {
        JSONObject info = new JSONObject();

        User user = userDao.getUserByWechatID(wechatID);
        int flag = 1;
        if (user == null)
        {
            flag = 0;
            info.put("errMsg", "通过wechatID获取用户失败");
        }
        else
        {
            String u_no = user.getU_no();
            if (user.getU_type().equals("学生"))
            {
                user.setS_name(userDao.getUserSpecialityName(u_no));
                user.setAm_name(userDao.getUserAcademyName(u_no));
            }
            flag = 1;
            info.put("user", user);
        }
        info.put("flag", flag);
        return info;
    }

    /*
     * @Description: 用户首次登陆注册添加到user表中
     * @Param user
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject registerUser(User user)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        int flag = 1;
        System.out.println(user);

        //判断用户是否已经注册过该学工号
        if (userDao.getUserByNo(user.getU_no()) != null)
        {
            errMsg.add("该学工号已经注册");
            flag = 0;
        }
        else if (user.getU_name().length() >= 20)
        {
            flag = 0;
            errMsg.add("用户名长度超过限制");
        }
        //判断手机号码格式是否正确
        else if (!FormatCheckUtils.isChinaPhoneLegal(user.getU_phone()))
        {
            errMsg.add("手机号码格式错误");
            flag = 0;
        }
        else if (user.getU_email() != null && !FormatCheckUtils.isEmail(user.getU_email()))
        {
            errMsg.add("邮箱格式错误");
            flag = 0;
        }
        //判断导师手机号码格式是否正确
        else if (user.getU_mentorPhone() != null && !FormatCheckUtils.isChinaPhoneLegal(user.getU_mentorPhone()))
        {
            errMsg.add("导师手机号码格式错误");
            flag = 0;
        }
        else
        {
            //注册用户表
            flag = userDao.registerUser(user);
            if (flag == 0) errMsg.add("注册用户（不含班级）失败");
            if (user.getU_type().equals("学生"))
            {
                //学生班级修改
                flag = userDao.setUserSpecialtyNo(user.getU_no(), user.getS_no());
                if (flag == 0) errMsg.add("注册用户班级修改失败");
            }

            //发送欢迎消息
            flag = messageDao.sendMessage(user.getU_no(), "微设备系统欢迎你。期待能为你带来愉快的体验");
            if (flag == 0) errMsg.add("发送欢迎消息失败");

            //信誉积分初始记录
            flag = creditRecordDao.initCredit(user.getU_no(), user.getU_type().equals("学生") ? 100 : 200);
            if (flag == 0) errMsg.add("用户信用分初始化失败");

        }

        info.put("flag", flag);
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 分页查询首页热门设备
     * @Param page  count
     * @Return: com.alibaba.fastjson.JSONArray
     */
    public JSONObject getHotDevice()
    {
        List<Device> deviceList = deviceDao.getHotDevice();
        JSONObject info = new JSONObject();
        if (deviceList.isEmpty())
        {
            info.put("flag", 0);
            info.put("errMsg", "没有查询到热门设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        }
        return info;
    }

    /*
     * @Description: 分页查询所有设备
     * @Param page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getAllDeviceByPage(int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        List<Device> deviceList = deviceDao.getAllDeviceByPage(page, count);
        if (deviceList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("当前页数没有设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        }
        info.put("errMsg", errMsg);
        return info;
    }

   /*
    * @Description: 用户浏览获取设备具体信息
    * @Param wechatID  d_no
    * @Return: com.alibaba.fastjson.JSONObject
    */
    public JSONObject getDeviceDetails(String wechatID, String d_no)
    {
        JSONObject info = new JSONObject();
        Device device = deviceDao.getDeviceDetails(d_no);

        if (device == null)
        {
            info.put("flag", 0);
            info.put("errMsg", "查询不到对应编号的设备");
        }
        else
        {
            //添加用户是否跟踪设备中状态
            String u_no = userDao.getUserByWechatID(wechatID).getU_no();
            if (trackDao.isTracking(u_no, d_no)) info.put("track", 1);
            else info.put("track", 0);

            info.put("flag", 1);
            info.put("device", device);
        }
        return info;
    }

    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject reserveDevice(String d_no, String wechatId, String startDate, String returnDate)
    {
        String u_no = userDao.getUserByWechatID(wechatId).getU_no();
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        int flag = 1;
        //获取设备但前状态
        String state = deviceDao.getDeviceState(d_no);
        if (state.equals("在库"))
        {
            //判断是否已经正在预约,如果没有正在预约该设备
            if (!reservationDao.isReserving(u_no, d_no))
            {
                //判断借用日期是否小于归还日期
                Date nowTime = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date now = TransformUtils.StringTransSQLDate(simpleDateFormat.format(nowTime));
                Date start = TransformUtils.StringTransSQLDate(startDate);
                Date end = TransformUtils.StringTransSQLDate(returnDate);
                if (!start.before(now) && start.before(end))
                {
                    flag = reservationDao.reserveDevice(d_no, u_no, startDate, returnDate);
                    if (flag == 0) errMsg.add("用户预约设备失败");
                }
                else
                {
                    flag = 0;
                    errMsg.add("起始日期应小于归还日期，大于等于当前日期");
                }
            }
            else
            {
                flag = 0;
                errMsg.add("已正在预约该设备！");
            }
        }
        else
        {
            //设备当前不在库，返回错误信息
            flag = 0;
            errMsg.add("设备当前不可借用");
        }
        info.put("flag", flag);
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 用户取消预约
     * @Param r_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject cancelReservation(int r_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        info.put("flag", 1);
        int flag = 1;
        //预约取消:-2  预约拒绝:-1  预约中:0   预约成功:1 协商预约:2  协商成功:3
        int r_state = reservationDao.getReservation(r_no).getR_state();
        if (r_state == 0 || r_state == 2 || r_state == 3)
        {
            flag = reservationDao.cancelReservation(r_no);
            if (flag == 0) errMsg.add("用户取消预约失败");
            String u_no = reservationDao.getReservation(r_no).getU_no();
            String d_name = reservationDao.getReservation(r_no).getD_name();
            flag = messageDao.sendMessage(u_no, "你已经成功取消预约设备：" + d_name);
            if (flag == 0) errMsg.add("发送提示消息失败");
        }
        else
        {
            flag = 0;
           errMsg.add("预约已取消、通过或者被拒绝");
        }
        info.put("flag", flag);
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 用户同意 修改预约
     * @Param r_no isAgreed
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject agreeEditReservation(int r_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        int flag = 1;
        info.put("flag", flag);
        Reservation reservation = reservationDao.getReservation(r_no);
        String u_no = reservation.getU_no();
        String d_name = reservation.getD_name();
        String startDate = reservation.getR_startDate();
        String returnDate = reservation.getR_returnDate();

        flag = reservationDao.setReservationState(r_no, 3);
        if (flag == 0)
        {
            info.put("flag", 0);
            errMsg.add("用户同意协商申请失败");
        }
        flag = messageDao.sendMessage(u_no, "你已同意预约协商：" + d_name + "。预约时间修改为：" + startDate + " ~ " + returnDate);
        if (flag == 0)
        {
            info.put("flag", 0);
            errMsg.add("发送提示消息失败");
        }
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 逾期归还：-2  逾期借用: -1 借用中：0 归还：1
     * @Param wechatID  page  count  isFinished
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getBorrowRecordByPage(String wechatID, int page, int count, boolean isFinished)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        String u_no = userDao.getUserByWechatID(wechatID).getU_no();
        borrowDao.setAllOverDueState();
        List<Borrow> borrowList;
        if (isFinished)
        {
            borrowList = borrowDao.getFinishedBorrowRecordByPage(u_no, page, count);
            if (borrowList.isEmpty())
            {
                info.put("flag", 0);
                errMsg.add("当前页数没有已完成借用记录");
            }
            else
            {
                info.put("flag", 1);
                info.put("borrowed_item", JSONArray.parseArray(JSON.toJSONString(borrowList)));
            }
        }
        else
        {
            borrowList = borrowDao.getUnfinishedBorrowRecordByPage(u_no, page, count);
            if (borrowList.isEmpty())
            {
                info.put("flag", 0);
                errMsg.add("当前页数没有借用中记录");
            }
            else
            {
                info.put("flag", 1);
                info.put("borrowed_item", JSONArray.parseArray(JSON.toJSONString(borrowList)));
            }
        }
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 获取用户的信用记录
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getCreditRecordByPage(String wechatID, int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        User user = userDao.getUserByWechatID(wechatID);
        String u_no = user.getU_no();
        int credit_score = user.getU_creditGrade();

        List<CreditRecord> creditRecordList = creditRecordDao.getRecordByPage(u_no, page, count);
        if (creditRecordList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("当前页数没有信用记录");
        }
        else
        {
            info.put("flag", 1);
            info.put("record", JSONArray.parseArray(JSON.toJSONString(creditRecordList)));
        }
        info.put("score", credit_score);
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 获取用户的接收到的消息:分页查询
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getMessageByPage(String wechatID, int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        User user = userDao.getUserByWechatID(wechatID);
        System.out.println(user);
        String u_no = user.getU_no();

        int flag = 1;
        messageDao.setAllMessageHaveRead(u_no);
        List<Message> messageList = messageDao.getMessageByPage(u_no, page, count);
        System.out.println(messageList);
        if (messageList.isEmpty())
        {
            flag = 0;
            errMsg.add("当前页数没有消息");
        }
        else
        {
            info.put("messages", JSONArray.parseArray(JSON.toJSONString(messageList)));
        }
        info.put("flag", flag);
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 用户查看申请中预约 或 用户查看已完成预约
     * @Param wechatID  page  count  isFinished
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservationByPage(String wechatID, int page, int count, boolean isFinished)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        User user = userDao.getUserByWechatID(wechatID);
        String u_no = user.getU_no();
        List<Reservation> reservationList;
        if (isFinished)
        {
            reservationList = reservationDao.getFinishedReservationByPage(u_no, page, count);
            if (reservationList.isEmpty())
            {
                info.put("flag", 0);
                errMsg.add("当前页数没有已完成预约记录");
            }
            else
            {
                info.put("flag", 1);
                info.put("reservation", JSONArray.parseArray(JSON.toJSONString(reservationList)));
            }
        }
        else
        {
            reservationList = reservationDao.getUnfinishedReservationByPage(u_no, page, count);
            if (reservationList.isEmpty())
            {
                info.put("flag", 0);
                errMsg.add("当前页数没有申请中预约记录");
            }
            else
            {
                info.put("flag", 1);
                info.put("reservation", JSONArray.parseArray(JSON.toJSONString(reservationList)));
            }
        }
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 通过关键字检索查找设备
     * @Param keyword  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getDeviceByPageWithKeyword(String keyword, int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        keyword = "%"+keyword+"%";
        List<Device> deviceList = deviceDao.getDeviceByPageWithKeyword(keyword, page, count);
        if (deviceList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("没有查询到对应关键字的设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        }
        info.put("errMsg", errMsg);
        return info;
    }


    /*
     * @Description: 用户评价借用设备
     * @Param b_no  comment
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject commentOnDevice(int b_no, String comment)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();
        Borrow borrow = borrowDao.getBorrowByNo(b_no);

        int flag = 1;
        if (borrow == null)
        {
            flag = 0;
            errMsg.add("不存在该借用记录");
        }
        else
        {
            info.put("flag", 1);
            String u_no = borrow.getU_no();
            String d_no = borrow.getD_no();

            //添加评论
            flag = commentDao.addComment(u_no, d_no, comment);
            if (flag == 0) errMsg.add("插入评论记录失败");

            //修改借用状态为归还评价
            flag = borrowDao.finishComment(b_no);
            if (flag == 0) errMsg.add("修改已评价状态失败");
        }

        info.put("flag", flag);
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 分页查询 获得设备评论
     * @Param d_no  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getCommentByPage(String d_no, int page, int count)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        List<Comment> commentList = commentDao.getCommentListByPage(d_no, page, count);
        info.put("flag", 1);
        if (commentList.isEmpty())
        {
            info.put("flag", 0);
            errMsg.add("当前页数暂时没有评论");
        }
        else
        {
            info.put("flag", 1);
            info.put("comment", JSONArray.parseArray(JSON.toJSONString(commentList)));
        }
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 用户跟踪设备
     * @Param wechatID  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject trackDevice(String wechatID, String d_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        String u_no = userDao.getUserByWechatID(wechatID).getU_no();
        if (trackDao.isTracking(u_no, d_no))
        {
            info.put("flag", 0);
            errMsg.add("已在跟踪设备中");
        }
        else
        {
            int flag = trackDao.trackDevice(u_no, d_no);
            info.put("flag", flag);
            if (flag == 0) errMsg.add("跟踪设备失败异常");
        }
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 用户取消跟踪设备
     * @Param wechatID  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject cancelTrack(String wechatID, String d_no)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        String u_no = userDao.getUserByWechatID(wechatID).getU_no();
        if (trackDao.isTracking(u_no, d_no))
        {
            int flag = trackDao.cancelTrackDevice(u_no, d_no);
            info.put("flag", flag);
            if (flag == 0)
            {
                errMsg.add("取消失败");
            }
            info.put("errMsg", errMsg);
        }
        else
        {
//            info.put("errMsg", errMsg
        }

        return info;
    }

    /*
     * @Description: 用户反馈意见给管理员
     * @Param wecharId,f_content
     * @Return: com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject feedbackToAdmin(String wechatId, String f_content)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();
        FeedbackDao feedbackDao = new FeedbackDaoImpl();
        UserDao userDao = new UserDaoImpl();

        User user = userDao.getUserByWechatID(wechatId);
        String u_no = user.getU_no();
        int f_no = feedbackDao.addUserFeedbackToDb(u_no, f_content);
        int flag = 0;
        if (f_no > 0)
        {
            flag = 1;
        }
        info.put("flag", flag);
        if (flag == 0)
        {
            errMsg.add("添加用户反馈到反馈表错误");
        }
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 用户首页获取未读信息
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getAllUnReadMessage(String wechatID)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        User user = userDao.getUserByWechatID(wechatID);
        List<Message> messageList = messageDao.getAllUnReadMessage(user.getU_no());
        if (messageList.isEmpty()) errMsg.add("当前无未读消息");
        else
        {
            info.put("messageList", JSONArray.parseArray(JSON.toJSONString(messageList)));
        }
        info.put("errMsg", errMsg);
        info.put("flag", 1);

        return info;
    }

    /*
     * @Description: 用户修改个人信息
     * @Param user
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject editUserInfo(User user)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        int flag = 1;
        String u_no = user.getU_no();
        if (user.getU_name() != null)
        {
            if (user.getU_name().length() >= 20)
            {
                flag = 0;
                errMsg.add("用户名长度超过限制");
            }
            else
            {
                flag = userDao.setUserName(u_no, user.getU_name());
                if (flag == 0) errMsg.add("修改用户姓名失败");
            }
        }
        if (user.getU_phone() != null)
        {
            if (FormatCheckUtils.isChinaPhoneLegal(user.getU_phone()))
            {
                flag = userDao.setUserPhone(u_no, user.getU_phone());
                if (flag == 0) errMsg.add("修改用户手机号码失败");
            }
            else
            {
                flag = 0;
                errMsg.add("手机号码格式错误");
            }
        }
        if (user.getU_email() != null)
        {
            if (FormatCheckUtils.isEmail(user.getU_email()) || user.getU_email().length() >= 40)
            {
                flag = userDao.setUserEmail(u_no, user.getU_email());
                if (flag == 0) errMsg.add("修改用户邮箱失败失败");
            }
            else
            {
                flag = 0;
                errMsg.add("邮箱格式错误或长度超过限制");
            }
        }
        if (user.getU_mentorName() != null)
        {
            if (user.getU_mentorName().length() >= 20)
            {
                flag = 0;
                errMsg.add("用户名长度超过限制");
            }
            else
            {
                flag = userDao.setUserMentorName(u_no, user.getU_mentorName());
                if (flag == 0) errMsg.add("修改用户导师姓名失败");
            }
        }
        if (user.getU_mentorPhone() != null)
        {
            if (FormatCheckUtils.isChinaPhoneLegal(user.getU_mentorPhone()))
            {
                flag = userDao.setUserMentorPhone(u_no, user.getU_mentorPhone());
                if (flag == 0) errMsg.add("修改用户导师手机号码失败");
            }
            else
            {
                flag = 0;
                errMsg.add("导师手机号码格式错误");
            }
        }

        info.put("flag", flag);
        info.put("errMsg", errMsg);

        return info;
    }

    /*
     * @Description: 用户查看所有信誉分信息
     * @Param
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getAllCreditRules()
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        int flag = 1;
        List<CreditRule> creditRuleList =  creditRuleDao.getAllCreditRules();
        if(creditRuleList.isEmpty())
        {
            flag = 0;
            errMsg.add("查询不到信用规则");
        }
        else info.put("creditRuleList", JSONArray.parseArray(JSON.toJSONString(creditRuleList)));

        info.put("flag", flag);
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 注册前获取院系专业信息
     * @Param
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getAcademyList()
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        //用户不存在，获取注册所需信息：学院、专业
        int flag = 1;
        List<Academy> academyList = academyDao.getAllAcademy();
        if (!academyList.isEmpty())
        {
            for (Academy academy : academyList)
            {
                List<Specialty> specialtyList = specialtyDao.getSpecialtyByAcademyNo(academy.getAm_no());
                if (!specialtyList.isEmpty())
                {
                    for (Specialty specialty: specialtyList)
                    {
                        academy.getSpecialtyList().add(specialty);
                    }
                }
            }
            info.put("academyList", JSONArray.parseArray(JSON.toJSONString(academyList)));
        }
        else
        {
            flag = 0;
            errMsg.add("获取学院信息失败");
        }

        info.put("flag", flag);
        info.put("errMsg",errMsg);
        return info;
    }


}
