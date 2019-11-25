package utils;

import java.sql.*;

/*
   JDBC工具类：将常用的函数/重复调用代码量大 封装到此，方便调用
 */
public class JDBCUtils
{

    private static String c_url = "jdbc:mysql://49.235.73.29:3306/testdb?serverTimezone=Asia/Shanghai";
    private static String c_user = "root";
    private static String c_password = "skplroot";
    private static Connection connection;

    /*
     * @Description: 创建连接：连接mytest数据库
     * @Param
     * @Return: java.sql.Connection
     */
    public static Connection getConnection()
    {
        connection = null;
        try
        {
            //选择加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立连接
            connection = DriverManager.getConnection(c_url, c_user, c_password);
            return connection;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //如果发生异常，则返回一个空的连接
        return connection;
    }

    /*
     * @Description:  关闭资源函数：由于按照Connection、Statement、ResultSet的顺序创建，所以要从后面开始关闭
     * @Param resultSet  statement  connection
     * @Return: void
     */
    public static void closeAll(ResultSet resultSet, Statement statement, Connection connection)
    {
        /*
        不能放到同一个try、catch语句中：
        如果某一个在close过程中出现异常抛出，后面的将无法关闭资源
         */
        try
        {
            if (resultSet != null) resultSet.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (statement != null) statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (connection != null) connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
