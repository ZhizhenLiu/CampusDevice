import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.DeviceDao;
import dao.ReservationDao;
import dao.UserDao;
import dao.impl.DeviceDaoImpl;
import dao.impl.ReservationDaoImpl;
import dao.impl.UserDaoImpl;
import service.AdminService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.UserServiceImpl;
import utils.MessageUtils;

public class Test {
    @org.junit.jupiter.api.Test

    public void test() {

        ReservationDao reservationDao = new ReservationDaoImpl();
        System.out.println(reservationDao.getStartDate("201797689552",2));
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
       AdminService adminService = new AdminServiceImpl();
       UserDao userDao = new UserDaoImpl();
       DeviceDao deviceDao = new DeviceDaoImpl();
       /*
       System.out.println(adminService.confirmBorrow("201726010310",5));*/
//       System.out.println(adminService.refuseBorrow());
//       System.out.println(adminService.getOverDue("o0ug241yqbsjM0N5xR5qhLxi8gH0"));
       System.out.println(userService.getJSONUserByWechatID("o0ug241yqbsjM0N5xR5qhLxi8gH0"));
   }
}
