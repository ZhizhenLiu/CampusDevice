package service.impl;

import bean.Admin;
import com.alibaba.fastjson.JSONObject;
import dao.AdminDao;
import dao.ReservationDao;
import dao.impl.AdminDaoImpl;
import dao.impl.ReservationDaoImpl;
import service.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();
    private ReservationDao reservationDao = new ReservationDaoImpl();

    /*
     * @Description: 通过标识获取管理员管辖范围内的有人预约的设备
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getReservation(String wechatId)
    {
        return  reservationDao.getReservation(wechatId);
    }

    public JSONObject getReservationDetail(String deviceNo)
    {
        return  reservationDao.getReservationDetail(deviceNo);
    }
}
