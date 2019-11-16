package service.impl;

import bean.Device;
import bean.Reservation;
import bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.ReservationDao;
import dao.UserDao;
import dao.impl.ReservationDaoImpl;
import dao.impl.UserDaoImpl;
import service.UserService;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    private ReservationDao reservationDao = new ReservationDaoImpl();

    /*
     * @Description: 通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    public User getUserByUserNo(String userNo)
    {
        return userDao.getUserByUserNo(userNo);
    }

    /*
     * @Description: 通过用户微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getUserBywechatId(String wechatId)
    {
        return userDao.getUserBywechatId(wechatId);
    }

    /*
     * @Description: 用户首次登陆添加到user表中
     * @Param user
     * @Return: void
     */
    public void registerUser(User user)
    {
        userDao.registerUser(user);
    }

    /*
     * @Description: 分页查询首页热门设备
     * @Param page  count
     * @Return: com.alibaba.fastjson.JSONArray
     */
    public JSONObject getHotDeviceByPage(int page, int count)
    {
        return  userDao.getHotDeviceByPage(page, count);
    }

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getDeviceDetails(int deviceNo)
    {
        return userDao.getDeviceDetails(deviceNo);
    }

    /*
     * @Description: 用户预约设备
     * @Param deviceNo  wechatId  startDate  endDate
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject reserveDevice(int deviceNo, String wechatId, Date startDate, Date endDate)
    {
        return reservationDao.reserveDevice(deviceNo, wechatId, startDate, endDate);
    }
}
