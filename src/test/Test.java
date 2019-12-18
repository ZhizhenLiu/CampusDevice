import bean.Borrow;
import bean.Device;
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

import java.awt.*;
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

         User user = new User("u_no", "u_name", "wechatID", "u_email", "u_phone", "teacher".equals("student")?100:200, "teacher".equals("student")?"学生":"老师", "u_mentorName", "u_mentorPhone", -1, "sname",-1, "amname");
//        String wechatID = "wechatID";
//        String d_no = "1905399S";
//
//        User user = new User();
//        user.setU_no("u_no");
//        user.setU_name("戏子");
//        user.setU_mentorPhone("123456789");
//
//       System.out.println(userService.getAllCreditRules());

       Device device = new Device();
       device.setD_no("d_no");
       device.setD_name("d_name");
       device.setD_model("d_model");
       device.setD_saveSite("d_saveSite");
       device.setA_no("3");
       device.setD_factoryNo("d_factoryNo");
       device.setD_state("damaged");
       device.setD_storeDate("2019-12-11");
//       System.out.println(adminService.addDevice(device));
//       System.out.println(adminService.addDevice(device));
//       System.out.println(userService.getDeviceDetails("o0ug242ge55sufbQW0xHk7KTmq60", "1713715S"));

//        .out.println(userService.reserveDevice("1713715S","o0ug242ge55sufbQW0xHk7KTmq60", "2019-12-18", "2019-12-19"));
       System.out.println(adminService.confirmBorrow(91));
   }
}
