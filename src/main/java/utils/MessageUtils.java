package utils;

import com.zhenzi.sms.ZhenziSmsClient;

import java.util.Random;

public class MessageUtils {

    //返回验证码
    public static String SendTextMessage(String number,String name)
    {
        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appId = "102764";
        String appSecret = "9b94983e-1e86-4bab-ae82-a83e484eb7ea";
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        //验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        try{
            String message = "尊敬的"+name+"先生，您的验证码为:"+ verifyCode+"，该码有效期为5分钟，该码只能使用一次!";
            String result = client.send(number, message);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return verifyCode;
    }
}
