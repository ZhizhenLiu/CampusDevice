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
import utils.FormatCheckUtils;
import utils.TransformUtils;

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
       AdminDao adminDao = new AdminDaoImpl();
       DeviceDao deviceDao = new DeviceDaoImpl();
       BorrowDao borrowDao = new BorrowDaoImpl();
       CommentDao  commentDao = new CommentDaoImpl();
       ReservationDao reservationDao = new ReservationDaoImpl();
       SpecialtyDao specialtyDao = new SpecialtyDaoImpl();

         User user = new User("2017260103", "u_name", "wechatID", "u_email", "13549511517", "teacher".equals("student")?100:200, "teacher".equals("student")?"学生":"老师", "u_mentorName", "u_mentorPhone", -1, "sname",-1, "amname");
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
       device.setD_no("666");
       device.setD_name("11");
       device.setD_model("3132");
       device.setD_saveSite("d_saveSite");
       device.setA_no("3");
       device.setD_factoryNo("d_factoryNo");
       device.setD_state("damaged");
       device.setD_storeDate("2019-12-11");
//       System.out.println(adminService.addDevice(device));
//       System.out.println(adminService.addDevice(device));
//       System.out.println(userService.getDeviceDetails("o0ug242ge55sufbQW0xHk7KTmq60", "1713715S"));

//        .out.println(userService.reserveDevice("1713715S","o0ug242ge55sufbQW0xHk7KTmq60", "2019-12-18", "2019-12-19"));

//       Date now = new Date();
//       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//       Date nowTime = TransformUtils.StringTransSQLDate(simpleDateFormat.format(now));
//       Date beg = TransformUtils.StringTransSQLDate("2019-12-20");
//       Date end = TransformUtils.StringTransSQLDate("2019-12-23");
//       System.out.println(beg + " " +nowTime);
//       //起始时间 >= 现在时间
//       System.out.println(!beg.before(nowTime)); //beg <now
//       System.out.println(beg.before(end));
//       System.out.println(!beg.before(nowTime) && beg.before(end));


       System.out.println("你好");
   }
}
