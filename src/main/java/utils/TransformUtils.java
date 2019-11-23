package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransformUtils
{

    //将字符串变成数据库SQL的时间
    public static Date StringTransSQLDate(String time)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = null;
        try
        {
            dateTime = simpleDateFormat.parse(time);
            Date SQLDateTime = new java.sql.Date(dateTime.getTime());
            return SQLDateTime;
        }
        catch (ParseException e)
        {
            System.out.println("字符串转时间错误！");
            e.printStackTrace();
        }
        return null;
    }

    //将字符串变成整数Int
    public static int StringTransInt(String str)
    {
        if (!str.equals(""))
        {
            int num = Integer.parseInt(str);
            return num;
        }
        else
        {
            System.out.println("字符串为空！");
            return -1;
        }
    }
}
