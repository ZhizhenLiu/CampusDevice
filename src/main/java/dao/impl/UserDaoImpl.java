package dao.impl;

import bean.Device;
import bean.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.UserDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
    /*
     * @Description: 分页查询首页热门设备
     * @Param page:页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.Device>
     */
    public JSONObject getHotDeviceByPage(int page, int count)
    {
        JSONObject result = new JSONObject();
        JSONArray deviceList = new JSONArray();
        JDBCUtils.init(rs, pStmt, con);

        con = JDBCUtils.getConnection();
        sql = "select * from device limit ?,?";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, (page-1)*count);
            pStmt.setInt(2, count);
            rs = pStmt.executeQuery();

            //判断是否存在记录
            if (rs.next())
            {
                //有的话记录查询状态为1：成功
                result.put("state",1);
                do {
                    deviceList.add(new Device(rs.getInt("d_no"), rs.getString("a_no"),rs.getString("d_state"),rs.getInt("d_borrowed_times"),
                            rs.getString("d_name"),rs.getString("d_important_param"),rs.getString("d_main_use"),rs.getString("d_save_site")));
                }
                while (rs.next());
                result.put("device",deviceList);
            }
            else
            {
                //不存在记录，查询状态为0：失败
                result.put("flag", 0);
                result.put("errMsg","未能获取到设备信息");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return result;
    }

    /*
     * @Description: 用户浏览获取设备具体信息
     * @Param deviceNo
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject getDeviceDetails(int deviceNo)
    {
        JDBCUtils.init(rs, pStmt, con);
        JSONObject result = new JSONObject();
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT" +
                    "d_no," +
                    "d_name," +
                    "d_main_use," +
                    "d_important_param," +
                    "d_save_site," +
                    "( SELECT a_name FROM administrator a1 WHERE a1.a_no = d.a_no ) a_name," +
                    "( SELECT a_phone FROM administrator a2 WHERE a2.a_no = d.a_no ) a_phone " +
                    "FROM" +
                    "device d " +
                    "WHERE" +
                    "d_no = ?;";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, deviceNo);
            rs = pStmt.executeQuery();
            if (rs.next())
            {
                result.put("d_no",rs.getString("d_no"));
                result.put("d_name",rs.getString("d_name"));
                result.put("d_main_use",rs.getString("d_main_use"));
                result.put("d_important_param",rs.getString("d_important_param"));
                result.put("d_save_site",rs.getString("d_save_site"));
                result.put("a_name",rs.getString("a_name"));
                result.put("a_phone",rs.getString("a_phone"));
                result.put("flag",1);
            }
            else
            {
                result.put("flag",0);
                result.put("errmsg","查询不到对应的设备");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return result;
    }



}
