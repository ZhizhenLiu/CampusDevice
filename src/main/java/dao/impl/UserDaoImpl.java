package dao.impl;

import bean.User;
import dao.UserDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            sql = "SELECT * FROM user u WHERE u_wechatid = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, wechatID);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setU_no(rs.getString("u_no"));
                user.setU_name(rs.getString("u_name"));
                user.setU_wechatID(rs.getString("u_wechatid"));
                user.setU_email(rs.getString("u_email"));
                user.setU_phone(rs.getString("u_phone"));
                user.setU_creditGrade(rs.getInt("u_credit_grade"));
                user.setU_type(rs.getString("u_type"));
                user.setU_mentorName(rs.getString("u_mentor_name"));
                user.setU_mentorPhone(rs.getString("u_mentor_phone"));
                user.setS_no(rs.getInt("s_no"));
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
     * @Description: 通过用户手机号获取用户对象
     * @Param phone
     * @Return: bean.User
     */
    @Override
    public User getUserByPhone(String phone) {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        User user = null;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM user WHERE u_phone = ? ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, phone);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setU_no(rs.getString("u_no"));
                user.setU_name(rs.getString("u_name"));
                user.setU_wechatID(rs.getString("u_wechatid"));
                user.setU_email(rs.getString("u_email"));
                user.setU_phone(rs.getString("u_phone"));
                user.setU_creditGrade(rs.getInt("u_credit_grade"));
                user.setU_type(rs.getString("u_type"));
                user.setU_mentorName(rs.getString("u_mentor_name"));
                user.setU_mentorPhone(rs.getString("u_mentor_phone"));
                user.setS_no(rs.getInt("s_no"));
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
     * @Description: 用户信息需要获取专业名称
     * @Param u_no
     * @Return: java.lang.String
     */
    public String getUserSpecialityName(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        String majorClass = null;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT s.s_name FROM user u, specialty s WHERE u_no = ? AND u.s_no = s.s_no";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                majorClass = rs.getString("s_name");
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
        return majorClass;
    }

    /*
     * @Description: 用户信息需要获取院系名称
     * @Param u_no
     * @Return: java.lang.String
     */
    public String getUserAcademyName(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        String academyName = null;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT am_name FROM user u, specialty s, academy am WHERE u_no = ? AND u.s_no = s.s_no AND s.am_no= am.am_no";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                academyName = rs.getString("am_name");
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
        return academyName;
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
            sql = "INSERT INTO user(u_no, u_name, u_wechatid, u_email, u_phone, u_credit_grade, u_type, u_mentor_name, u_mentor_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

        User user = null;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM user u, specialty s, academy am WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setU_no(rs.getString("u_no"));
                user.setU_name(rs.getString("u_name"));
                user.setU_wechatID(rs.getString("u_wechatid"));
                user.setU_email(rs.getString("u_email"));
                user.setU_phone(rs.getString("u_phone"));
                user.setU_creditGrade(rs.getInt("u_credit_grade"));
                user.setU_type(rs.getString("u_type"));
                user.setU_mentorName(rs.getString("u_mentor_name"));
                user.setU_mentorPhone(rs.getString("u_mentor_phone"));
                user.setS_no(rs.getInt("s_no"));
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

    /*
     * @Description: 设置用户专业名称
     * @Param u_no  s_no
     * @Return: int
     */
    public int setUserSpecialtyNo(String u_no, int s_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE user SET s_no = ? WHERE u_no = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setInt(1, s_no);
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
     * @Description: 获取所有的非管理员、用户列表
     * @Param
     * @Return: java.util.List<bean.User>
     */
    public List<User> getAllUserList()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<User> userList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM `user` " +
                  "WHERE u_wechatid NOT IN " +
                  "( " +
                  "SELECT a_wechatid FROM administrator " +
                  ") ";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                User user = new User();
                user.setU_no(rs.getString("u_no"));
                user.setU_name(rs.getString("u_name"));
                user.setU_type(rs.getString("u_type"));
                user.setU_phone(rs.getString("u_phone"));
                userList.add(user);
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
        return userList;
    }
}
