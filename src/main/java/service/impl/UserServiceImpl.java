package service.impl;

import bean.Device;
import bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.DeviceDao;
import dao.ReservationDao;
import dao.UserDao;
import dao.impl.DeviceDaoImpl;
import dao.impl.ReservationDaoImpl;
import dao.impl.UserDaoImpl;
import service.UserService;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    private ReservationDao reservationDao = new ReservationDaoImpl();
    private DeviceDao deviceDao = new DeviceDaoImpl();

    /*
     * @Description: 通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    public User getUserByWechatId(String wechatId)
    {
        return userDao.getUserByWechatID(wechatId);
    }

    /*
     * @Description: 通过用户微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getUserBywechatId(String wechatID)
    {
        User user = userDao.getUserByWechatID(wechatID);
        JSONObject info = new JSONObject();
        if (user == null)
        {
            info.put("flag",0);
            info.put("errmsg","通过wechatID获取用户失败");
        }
        else
        {
            info.put("flag",1);
            info.put("user",user);
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
            info.put("errmsg","注册用户失败");
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
            info.put("errmsg", "当前页数没有设备");
        }
        else
        {
            info.put("flag", 1 );
            info.put("device", JSONArray.parseArray(JSON.toJSONString(deviceList)));
        }
        return info;
    }

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getDeviceDetails(int deviceNo)
    {
        JSONObject info = new JSONObject();
        Device device = deviceDao.getDeviceDetails(deviceNo);
        if (device == null)
        {
            info.put("flag", 0);
            info.put("errmsg", "查询不到对应编号的设备");
        }
        else info.put("flag", 1);
        return info;
    }

    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject reserveDevice(int deviceNo, String wechatId, Date startDate, Date endDate)
    {
        String u_no = userDao.getUserByWechatID(wechatId).getU_no();
        JSONObject info = new JSONObject();
        JSONArray errmsg = new JSONArray();

        //获取设备但前状态
        String state = deviceDao.getDeviceState(deviceNo);
        if (state.equals("在库"))
        {
            int flag = reservationDao.reserveDevice(deviceNo, u_no, startDate, endDate);
            info.put("flag", flag);
            if (flag == 0 )
            {
                errmsg.add("用户预约设备失败");
            }
        }
        else
        {
            //设备当前不在库，返回错误信息
            info.put("flag",0);
            errmsg.add("设备当前不可借用");
        }
        info.put("errmsg",errmsg);
        return info;
    }
}
