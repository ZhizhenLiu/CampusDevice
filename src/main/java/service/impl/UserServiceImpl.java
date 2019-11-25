package service.impl;

import bean.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.*;
import dao.impl.*;
import service.UserService;

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

    /*
     * @Description: 通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    public User getUserByWechatID(String wechatID)
    {
        return userDao.getUserByWechatID(wechatID);
    }

    /*
     * @Description: 通过用户微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getJSONUserByWechatID(String wechatID)
    {
        User user = userDao.getUserByWechatID(wechatID);
        JSONObject info = new JSONObject();
        if (user == null)
        {
            info.put("flag", 0);
            info.put("errMsg", "通过wechatID获取用户失败");
        }
        else
        {
            info.put("flag", 1);
            info.put("user", user);
        }
        return info;
    }

    /*
     * @Description: 用户首次登陆注册添加到user表中
     * @Param user
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject registerUser(User user)
    {
        int flag = userDao.registerUser(user);
        JSONObject info = new JSONObject();
        info.put("flag", flag);
        if (flag == 0)
        {
            info.put("errMsg", "注册用户失败");
        }
        return info;
    }

    /*
     * @Description: 分页查询首页热门设备
     * @Param page  count
     * @Return: com.alibaba.fastjson.JSONArray
     */
    public JSONObject getHotDeviceByPage(int page, int count)
    {
        List<Device> deviceList = deviceDao.getHotDeviceByPage(page, count);
        JSONObject info = new JSONObject();
        if (deviceList.isEmpty())
        {
            info.put("flag", 0);
            info.put("errMsg", "当前页数没有设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        }
        return info;
    }

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getDeviceDetails(int d_no)
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
    public JSONObject reserveDevice(int d_no, String wechatId, Date startDate, Date returnDate)
    {
        String u_no = userDao.getUserByWechatID(wechatId).getU_no();
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        //获取设备但前状态
        String state = deviceDao.getDeviceState(d_no);
        if (state.equals("在库"))
        {
            int flag = reservationDao.reserveDevice(d_no, u_no, startDate, returnDate);
            info.put("flag", flag);
            if (flag == 0)
            {
                errMsg.add("用户预约设备失败");
            }
        }
        else
        {
            //设备当前不在库，返回错误信息
            info.put("flag", 0);
            errMsg.add("设备当前不可借用");
        }
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
        int flag = reservationDao.cancelReservation(r_no);
        if (flag == 0)
        {
            info.put("flag", 0);
            errMsg.add("用户取消预约失败");
        }
        String u_no = reservationDao.getReservation(r_no).getU_no();
        String d_name = reservationDao.getReservation(r_no).getD_name();
        flag = messageDao.sendMessage(u_no, "你已经成功取消预约设备："+d_name);
        if (flag == 0)
        {
            info.put("flag", 0);
            errMsg.add("发送提示消息失败");
        }
        info.put("errMsg", errMsg);
        return info;
    }

    /*
     * @Description: 查询用户借用的记录(借用中b_state=0，归还b_state=1,逾期未还b_state= -1)
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getBorrowRecord(String wechatID)
    {
        String u_no = userDao.getUserByWechatID(wechatID).getU_no();
        JSONObject info = new JSONObject();
        List<Borrow> borrowList = borrowDao.getBorrowRecord(u_no);
        info.put("borrowed_item", JSONArray.parseArray(JSON.toJSONString(borrowList)));
        return info;
    }

    /*
     * @Description: 获取用户的信用记录
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getCreditRecordByPage(String wechatID, int page, int count)
    {
        User user = userDao.getUserByWechatID(wechatID);
        String u_no = user.getU_no();
        int credit_score = user.getU_creditGrade();
        JSONObject info = new JSONObject();
        List<CreditRecord> creditRecordList = creditRecordDao.getRecordByPage(u_no, page, count);
        info.put("score", credit_score);
        info.put("record", JSONArray.parseArray(JSON.toJSONString(creditRecordList)));
        return info;
    }

    /*
     * @Description: 获取用户的接收到的消息:分页查询
     * @Param wechatID  page  count
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getMessageByPage(String wechatID, int page, int count)
    {
        User user = userDao.getUserByWechatID(wechatID);
        String u_no = user.getU_no();
        JSONObject info = new JSONObject();
        List<Message> messageList = messageDao.getMessageByPage(u_no, page, count);
        info.put("messages", JSONArray.parseArray(JSON.toJSONString(messageList)));
        return info;
    }

    /*
     * @Description: 用户查询已预约设备信息
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservation(String wechatID)
    {
        User user = userDao.getUserByWechatID(wechatID);
        JSONObject info = new JSONObject();
        String u_no = user.getU_no();
        List<Reservation> reservationList = reservationDao.getReservation(u_no);
        info.put("reservation", JSONArray.parseArray(JSON.toJSONString(reservationList)));
        return info;
    }

    /*
     * @Description: 通过关键字检索查找设备
     * @Param keyword
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getDeviceByKeyword(String keyword)
    {
        JSONObject info = new JSONObject();
        List<Device> deviceList = deviceDao.getDeviceByKeyword(keyword);
        if (deviceList.isEmpty())
        {
            info.put("flag", 0);
            info.put("errMsg", "没有查询到对应关键字的设备");
        }
        else
        {
            info.put("flag", 1);
            info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        }
        return info;
    }
}
