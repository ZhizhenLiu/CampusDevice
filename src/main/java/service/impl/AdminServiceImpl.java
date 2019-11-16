package service.impl;

import bean.Admin;
import com.alibaba.fastjson.JSONObject;
import dao.AdminDao;
import dao.BorrowDao;
import dao.ReservationDao;
import dao.impl.AdminDaoImpl;
import dao.impl.BorrowDaoImpl;
import dao.impl.ReservationDaoImpl;
import service.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();
    private ReservationDao reservationDao = new ReservationDaoImpl();
    private BorrowDao borrowDao = new BorrowDaoImpl();
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

    public JSONObject confirmBorrow(String u_no, int d_no)
    {
        String borrowDate = reservationDao.getBorrowDate(u_no, d_no);
        String returnDate = reservationDao.getReturnDate(u_no, d_no);
        return borrowDao.confirmBorrow(u_no, d_no, borrowDate, returnDate);
    }
}
