package utils;

import bean.Borrow;
import bean.User;
import com.zhenzi.sms.ZhenziSmsClient;

import java.util.Random;

public class MessageUtils
{
    private static String c_apiUrl = "https://sms_developer.zhenzikj.com";
    private static String c_appId = "102764";
    private static String c_appSecret = "9b94983e-1e86-4bab-ae82-a83e484eb7ea";

    /*
     * @Description: 返回验证码工具类
     * @Param number  name
     * @Return: java.lang.String
     */
    public static String sendVerifyCode(User user)
    {
        String name = user.getU_name();
        String type = user.getU_type();
        String phone = user.getU_phone();
        ZhenziSmsClient client = new ZhenziSmsClient(c_apiUrl, c_appId, c_appSecret);

        //验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        try
        {
            String message = name + type + "，您好！您的验证码为:" + verifyCode + "，该码有效期为5分钟，该码只能使用一次!";
            System.out.println(message);
            String result = client.send(phone, message);
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
        String u_name = borrow.getU_name();
        String u_type = borrow.getU_type();
        String u_phone = borrow.getU_phone();
        String d_name = borrow.getD_name();
        String d_saveSite = borrow.getD_saveSite();
        ZhenziSmsClient client = new ZhenziSmsClient(c_apiUrl, c_appId, c_appSecret);
        try
        {
            String message = u_name + u_type + "，您好！您借用的设备:" + d_name + "，已经逾期未还，请在近日内归还到" + d_saveSite;
            System.out.println(message);
            String result = client.send(u_phone, message);
            System.out.println(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
