package dao.impl;

import bean.User;
import dao.UserDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: select:通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    public User getUserByWechatID(String wechatID)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        User user = null;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM user u, specialty s WHERE u_wechatid = ? AND u.u_major_class = s.s_no";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, wechatID);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                user = new User(rs.getString("u_no"), rs.getString("u_name"), rs.getString("u_wechatid"), rs.getString("u_email"),
                        rs.getString("u_phone"), rs.getInt("u_credit_grade"), rs.getString("u_type"), rs.getString("u_mentor_name"),
                        rs.getString("u_mentor_phone"), rs.getString("s_name"), rs.getInt("s_no"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return user;
    }


    /*
     * @Description: 用户首次登陆添加到user表中
     * @Param user
     * @Return: int
     */
    public int registerUser(User user)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO user VALUES (?,?,?,?,?,?,?,?,?,?)";
            pStmt = con.prepareStatement(sql);

            pStmt.setString(1, user.getU_no());
            pStmt.setString(2, user.getU_name());
            pStmt.setString(3, user.getU_wechatID());
            pStmt.setString(4, user.getU_email());
            pStmt.setString(5, user.getU_phone());
            pStmt.setInt(6, user.getU_creditGrade());
            pStmt.setString(7, user.getU_type());
            pStmt.setString(8, user.getU_mentorName());
            pStmt.setString(9, user.getU_mentorPhone());
            pStmt.setInt(10, user.getS_no());

            flag = pStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 用户修改个人信息
     * @Param user
     * @Return: void
     */
    public int changeUserInfo(User user)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET u_name=?, u_email=?, u_phone=?, u_credit_grade=?, u_mentor_name=?, u_mentor_phone=?, u_major_class=? " +
                  "WHERE u_no = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, user.getU_name());
            pStmt.setString(2, user.getU_email());
            pStmt.setString(3, user.getU_phone());
            pStmt.setInt(4, user.getU_creditGrade());
            pStmt.setString(5, user.getU_mentorName());
            pStmt.setString(6, user.getU_phone());
            pStmt.setInt(7, user.getS_no());
            pStmt.setString(8, user.getU_no());

            flag = pStmt.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 通过用户学工号获取用户对象
     * @Param u_no
     * @Return: bean.User
     */
    public User getUserByNo(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        User user = new User();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM user u, specialty s WHERE u_no = ? AND u.u_major_class = s.s_no";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                user = new User(rs.getString("u_no"), rs.getString("u_name"), rs.getString("u_wechatid"), rs.getString("u_email"),
                        rs.getString("u_phone"), rs.getInt("u_credit_grade"), rs.getString("u_type"), rs.getString("u_mentor_name"),
                        rs.getString("u_mentor_phone"), rs.getString("s_name"), rs.getInt("s_no"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return user;

    }

    /*
     * @Description: 更新增加或减少信誉分数
     * @Param score
     * @Return: int
     */
    public int updateCreditGrade(String u_no, int score)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET u_credit_grade = u_credit_grade + ? WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, score);
            pStmt.setString(2, u_no);
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 通过用户学号获取用户当前信用积分
     * @Param u_no
     * @Return: int
     */
    @Override
    public int getCreditScore(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "select u_credit_grade from user WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            int score = pStmt.executeUpdate();
            return score;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return 0;
    }

    /*
     * @Description: 修改用户名称
     * @Param u_no  u_name
     * @Return: int
     */
    public int setUserName(String u_no, String u_name)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET u_name = ? WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_name);
            pStmt.setString(2, u_no);
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 设置用户手机号码
     * @Param u_no  u_phone
     * @Return: int
     */
    public int setUserPhone(String u_no, String u_phone)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET u_phone = ? WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_phone);
            pStmt.setString(2, u_no);
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 修改用户邮箱
     * @Param u_no  u_email
     * @Return: int
     */
    public int setUserEmail(String u_no, String u_email)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET u_email = ? WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_email);
            pStmt.setString(2, u_no);
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 修改用户导师姓名
     * @Param u_no  u_mentorName
     * @Return: int
     */
    public int setUserMentorName(String u_no, String u_mentorName)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET u_mentor_name = ? WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_mentorName);
            pStmt.setString(2, u_no);
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 修改用户导师联系方式
     * @Param u_no  u_mentorPhone
     * @Return: int
     */
    public int setUserMentorPhone(String u_no, String u_mentorPhone)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET u_mentor_phone = ? WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_mentorPhone);
            pStmt.setString(2, u_no);
            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }
}
