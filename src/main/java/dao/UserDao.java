package dao;

import bean.User;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface UserDao {

    /*
     * @Description: 通过用户wechatId获取用户对象
     * @Param wechatID
     * @Return: bean.User
     */
    User getUserByWechatID(String wechatID);


    /*
     * @Description: 用户首次登陆进行注册添加到user表中
     * @Param user
     * @Return: JSONObject
     */
    int registerUser(User user);

    /*
     * @Description: 用户修改个人信息
     * @Param user
     * @Return: int
     */
    int changeUserInfo(User user);

    /*
     * @Description: 通过用户学工号获取用户对象
     * @Param u_no
     * @Return: bean.User
     */
    User getUserByNo(String u_no);
}
