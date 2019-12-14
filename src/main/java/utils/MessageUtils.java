package utils;

import bean.Borrow;
import bean.User;
import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import dao.TextRecordDao;
import dao.UserDao;
import dao.impl.TextRecordDaoImpl;
import dao.impl.UserDaoImpl;

import java.util.Random;

public class MessageUtils
{
    private static String c_apiUrl = "https://sms_developer.zhenzikj.com";
    private static String c_appId = "102764";
    private static String c_appSecret = "NWE1YTg3MTctMWM3ZC00ZjJmLWI5ZWYtYjdhYmMyYTUyY2Q2";

    /*
     * @Description: 返回验证码工具类
     * @Param number  name
     * @Return: java.lang.String
     */
    public static String sendVerifyCode(String phone)
    {
        ZhenziSmsClient client = new ZhenziSmsClient(c_apiUrl, c_appId, c_appSecret);
        //验证码
        String verifyCode = null;
        try
        {
            verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            String message = "您好！您的验证码为:" + verifyCode + "，该码有效期为5分钟，该码只能使用一次!";
            System.out.println(message);
            JSONObject result = JSONObject.parseObject(client.send(phone, message));

            //返回结果是json格式的字符串, code: 发送状态，0为成功。非0为发送失败，可从data中查看错误信息
            if (result.getInteger("code") != 0) verifyCode = null;
            System.out.println(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return verifyCode;
    }

    /*
     * @Description: 管理员提醒归还设备
     * @Param number  u_name  d_name
     * @Return: void
     */
    public static void sendRemindMessage(Borrow borrow)
    {
        String u_name = borrow.getU_name() + (borrow.getU_type().equals("学生") ? "同学" : "老师");
        String u_phone = borrow.getU_phone();
        String d_name = borrow.getD_name();
        String d_saveSite = borrow.getD_saveSite();
        ZhenziSmsClient client = new ZhenziSmsClient(c_apiUrl, c_appId, c_appSecret);
        try
        {
            String message = u_name + "，您好！您借用的设备:" + d_name + "，已经逾期未还，请在近日内归还到" + d_saveSite;
            System.out.println(message);
            String result = client.send(u_phone, message);
            TextRecordDao textRecordDao = new TextRecordDaoImpl();
            UserDao userDao = new UserDaoImpl();
            User user = userDao.getUserByPhone(u_phone);
            textRecordDao.addTextRecord(user.getU_no(),message);
            System.out.println(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
