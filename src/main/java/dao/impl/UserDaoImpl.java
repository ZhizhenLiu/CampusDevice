package dao.impl;

import bean.User;
import com.alibaba.fastjson.JSONObject;
import dao.UserDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;
    /*
     * @Description: select:通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    public User getUserByUserNo(String userNo)
    {
        //初始化
        JDBCUtils.init(rs, pStmt, con);

        try {
            con = JDBCUtils.getConnection();
            sql = "select * from user where u_userno = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, userNo);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
               return new User(rs.getString("u_userno"),rs.getString("u_name"),rs.getString("u_wechatid"),rs.getString("u_email"),
                       rs.getString("u_phone"),rs.getInt("u_credit_grade"),rs.getString("u_type"),rs.getString("u_mentor_name"),
                       rs.getString("u_mentor_phone"),rs.getString("u_major_class"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return  null;
    }

    /*
     * @Description: 通过微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getUserBywechatId(String wechatId)
    {
        //初始化
        JDBCUtils.init(rs, pStmt, con);
        JSONObject result = null;
        try {
            con = JDBCUtils.getConnection();
            sql = "select * from user where u_wechatid = ?";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, wechatId);
            rs = pStmt.executeQuery();

            if (rs.next())
            {
                result.put("flag","1");
                result.put("user",new User(rs.getString("u_userno"),rs.getString("u_name"),rs.getString("u_wechatid"),rs.getString("u_email"),
                        rs.getString("u_phone"),rs.getInt("u_credit_grade"),rs.getString("u_type"),rs.getString("u_mentor_name"),
                        rs.getString("u_mentor_phone"),rs.getString("u_major_class")));
            }
            else
            {
                result.put("flag","0");
                result.put("errmsg","未查询到对应的用户");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return result;
    }

    /*
     * @Description: 用户首次登陆添加到user表中
     * @Param user
     * @Return: void
     */
    public void registerUser(User user)
    {
        //初始化
        JDBCUtils.init(rs, pStmt, con);

        try {
            con = JDBCUtils.getConnection();
            sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";
            pStmt = con.prepareStatement(sql);

            pStmt.setString(1,user.getU_no());
            pStmt.setString(2,user.getU_name());
            pStmt.setString(3,user.getU_wechatId());
            pStmt.setString(4,user.getU_email());
            pStmt.setString(5,user.getU_phone());
            pStmt.setInt(6,user.getU_credit_grade());
            pStmt.setString(7,user.getU_type());
            pStmt.setString(8,user.getU_mentor_name());
            pStmt.setString(9,user.getU_mentor_phone());
            pStmt.setString(10,user.getU_major_class());

            pStmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null, pStmt, con);
        }
    }




}
