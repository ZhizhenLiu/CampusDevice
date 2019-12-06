package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpUtils
{
    private static String c_url = "https://api.weixin.qq.com/sns/jscode2session";
    private static String c_appid = "wxd761f924887cb306";
    private static String c_secret = "14d621bffc3a04069b198a2651c61d78";
    private static String c_grantType = "authorization_code";

    /*
     * @Description: 根据微信前台发送的code获取用户唯一标识openid
     * @Param code
     * @Return: java.lang.String
     */
    public static String sendGet(String code)
    {
        String param = "appid=" + c_appid + "&secret=" + c_secret + "&js_code=" + code + "&grant_type=" + c_grantType;
        String result = "";
        BufferedReader in = null;
        try
        {
            String urlNameString = c_url + "?" + param;
            System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);

            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 遍历所有的响应头字段
            for (String key : map.keySet())
            {
                System.out.println(key + "--->" + map.get(key));
            }

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
