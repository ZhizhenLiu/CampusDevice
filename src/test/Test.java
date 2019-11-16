import bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonString;
import dao.AdminDao;
import dao.ReservationDao;
import dao.UserDao;
import dao.impl.AdminDaoImpl;
import dao.impl.ReservationDaoImpl;
import dao.impl.UserDaoImpl;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.HttpUtils;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Test {
    @org.junit.jupiter.api.Test

    public void test() {
//        JSONObject json = new JSONObject();
//        User user2 = new User("201726010313","卡莎","k12950339855","ks@163.com",
//                "13568115132",90,"学生","沙雕网友","16120769200",
//                "删库工程");
//        User user1 = new User("201726010313","2","k12950339855","ks@163.com",
//                "13568115132",90,"学生","沙雕网友","16120769200",
//                "删库工程");
//
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(user1);
//        jsonArray.add(user2);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("user",jsonArray);
//        System.out.println(jsonObject);
        AdminDao adminDao = new AdminDaoImpl();
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
       ReservationDao reservationDao = new ReservationDaoImpl();
       System.out.println(reservationDao.getReservationDetail("2"));
   }
}
