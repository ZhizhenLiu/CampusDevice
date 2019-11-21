package utils;

import com.zhenzi.sms.ZhenziSmsClient;

import java.util.Random;

public class MessageUtils {
    private static String c_apiUrl = "https://sms_developer.zhenzikj.com";
    private static String c_appId = "102764";
    private static String c_appSecret = "9b94983e-1e86-4bab-ae82-a83e484eb7ea";

    /*
     * @Description: 返回验证码工具类
     * @Param number  name
     * @Return: java.lang.String
     */
    public static String SendTextMessage(String number,String name)
    {

        ZhenziSmsClient client = new ZhenziSmsClient(c_apiUrl, c_appId, c_appSecret);

        //验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        try
        {
            String message = "尊敬的"+name+"，您的验证码为:"+ verifyCode+"，该码有效期为5分钟，该码只能使用一次!";
            String result = client.send(number, message);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return verifyCode;
    }
}
