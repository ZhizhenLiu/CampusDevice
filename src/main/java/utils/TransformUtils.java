package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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

    //将数据库SQL变成字符串
    public static String SQLDateTransString(java.sql.Date dateTime)
    {
        try
        {
            Date date = new java.util.Date(dateTime.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String stringDate = sdf.format(date);
            return stringDate;

        }
        catch (Exception e)
        {
            System.out.println("数据库时间转字符串错误！");
            e.printStackTrace();
        }
        return null;
    }

    //将字符串变成整数Int
    public static int StringTransInt(String str)
    {
        if (!str.matches("^[0-9]*$"))  //如果字符串不是数字型
        {
            System.out.println("字符串不是数字型，含其他字符");
            return -1;
        }
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
