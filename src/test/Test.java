import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import dao.ReservationDao;
import dao.impl.ReservationDaoImpl;
import service.UserService;
import service.impl.UserServiceImpl;

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

       System.out.println(userService.getMessageByPage("finethankyou001",1,10));
   }
}
