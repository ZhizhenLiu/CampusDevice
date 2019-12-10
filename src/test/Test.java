import bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.*;
import dao.impl.*;
import service.AdminService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Test {
    @org.junit.jupiter.api.Test

    public void test() {

        ReservationDao reservationDao = new ReservationDaoImpl();

    }

    @org.junit.jupiter.api.Test
   void Test2()
   {
       JSONArray reservations = new JSONArray();
       String menuList = "[,"
               + ",]";
       reservations.add(JSON.parse("{'id':2,'locked':true,'loginedTime':'2015-03-22'}"));
       reservations.add(JSON.parse("{'id':2,'locked':true,'loginedTime':'2015-03-22'}"));
       reservations.add(JSON.parse("{'id':2,'locked':true,'loginedTime':'2015-03-22'}"));
       reservations.add(JSON.parse("{'id':2,'locked':true,'loginedTime':'2015-03-22'}"));

       System.out.println(reservations);
   }

   @org.junit.jupiter.api.Test
   void Test3()
   {

       UserService userService = new UserServiceImpl();
       AdminService adminService = new AdminServiceImpl();
       UserDao userDao = new UserDaoImpl();
       DeviceDao deviceDao = new DeviceDaoImpl();
       BorrowDao borrowDao = new BorrowDaoImpl();
       CommentDao  commentDao = new CommentDaoImpl();
       ReservationDao reservationDao = new ReservationDaoImpl();
//      System.out.println(userService.reserveDevice("1713714S","o0ug241yqbsjM0N5xR5qhLxi8gH0","2019-10-01","2019-10-07"));
//       System.out.println(adminService.getReservedDevice(""));

       System.out.println();
       System.out.println();

       String wechatID = "o0ug242ge55sufbQW0xHk7KTmq60";


//       System.out.println(userService.getReservationByPage("o0ug241yqbsjM0N5xR5qhLxi8gH0",1 ,10, true));
//       System.out.println(reservationDao.getFinishedReservationByPage("201726010310", 1, 10));
//       System.out.println();
//       System.out.println(userService.getReservationByPage("",1 ,10, false));
//       System.out.println(reservationDao.getUnfinishedReservationByPage("201726010310", 1, 10));
//       System.out.println(userService.reserveDevice("1905399S", "o0ug241yqbsjM0N5xR5qhLxi8gH0", "2019-12-06", "2019-12-12"));
//       System.out.println(adminService.editReservation(39, "2019-12-15", "2019-12-30", "原借用时间段有人外借"));
//       System.out.println(userService.getReservationByPage(wechatID,1,10,true));
//       System.out.println();
//       System.out.println(userService.getReservationByPage(wechatID,1,10,false));
       String returnDate = "2017-12-11";
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date now = new Date();
       Date re = new Date();
       try
       {
            re = simpleDateFormat.parse(returnDate);
       }
       catch (ParseException e)
       {
           e.printStackTrace();
       }
       System.out.println(now + " " + re);
       System.out.println(re.getTime() > now.getTime());
   }
}
