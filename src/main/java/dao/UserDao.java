package dao;

import bean.User;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface UserDao {

    /*
     * @Description: 通过用户编号获取用户对象
     * @Param userNo
     * @Return: bean.User
     */
    User getUserByUserNo(String userNo);


    /*
     * @Description: 通过微信唯一标识获取用户
     * @Param wechatId
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getUserBywechatId(String wechatId);

    /*
     * @Description: 用户首次登陆添加到user表中
     * @Param user
     * @Return: void
     */
    void registerUser(User user);

}
