import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.BorrowDao;
import dao.ReservationDao;
import dao.impl.BorrowDaoImpl;
import dao.impl.ReservationDaoImpl;
import service.UserService;
import service.impl.UserServiceImpl;

import java.util.Date;

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
        ReservationDao reservationDao = new ReservationDaoImpl();
        System.out.println(reservationDao.getBorrowDate("201797689552",2));
        System.out.println(reservationDao.getReturnDate("201797689552",2));
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
       System.out.println(userService.reserveDevice(1,"finethankyou001",new Date(2019-11-16),new Date(2019-11-30)));
   }
}
