import bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.*;
import dao.impl.*;
import service.AdminService;
import service.SystemService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.SystemServiceImpl;
import service.impl.UserServiceImpl;
import utils.MessageUtils;

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
       SystemService systemService = new SystemServiceImpl();
       UserDao userDao = new UserDaoImpl();
       DeviceDao deviceDao = new DeviceDaoImpl();
       BorrowDao borrowDao = new BorrowDaoImpl();
       CommentDao  commentDao = new CommentDaoImpl();
       ReservationDao reservationDao = new ReservationDaoImpl();
       SpecialtyDao specialtyDao = new SpecialtyDaoImpl();
//      System.out.println(userService.reserveDevice("1713714S","o0ug241yqbsjM0N5xR5qhLxi8gH0","2019-10-01","2019-10-07"));
//       System.out.println(adminService.getReservedDevice(""));

       System.out.println();
       System.out.println();

       String wechatID = "o0ug241yqbsjM0N5xR5qhLxi8gH0";

     User user = new User("u_no", "u_name", "wechatID", "u_email", "u_phone", "teacher".equals("student")?100:200, "teacher".equals("student")?"学生":"老师", "u_mentorName", "u_mentorPhone", -1, "sname",-1, "amname");


   }
}
