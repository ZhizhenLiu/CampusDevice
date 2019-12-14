package dao;

import bean.User;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.math3.optim.nonlinear.scalar.LineSearch;

import java.util.Date;
import java.util.List;

public interface UserDao
{

    /*
     * @Description: 通过用户wechatId获取用户对象
     * @Param wechatID
     * @Return: bean.User
     */
    User getUserByWechatID(String wechatID);

    /*
     * @Description: 通过用户手机号获取用户对象
     * @Param phone
     * @Return: bean.User
     */
    User getUserByPhone(String phone);

    /*
     * @Description: 用户信息需要获取专业名称
     * @Param u_no
     * @Return: java.lang.String
     */
    String getUserSpecialityName(String u_no);

    /*
     * @Description: 用户信息需要获取院系名称
     * @Param u_no
     * @Return: int
     */
    String getUserAcademyName(String u_no);


    /*
     * @Description: 用户首次登陆进行注册添加到user表中
     * @Param user
     * @Return: JSONObject
     */
    int registerUser(User user);

    /*
     * @Description: 通过用户学工号获取用户对象
     * @Param u_no
     * @Return: bean.User
     */
    User getUserByNo(String u_no);

    /*
     * @Description: 更新增加或减少信誉分数
     * @Param score
     * @Return: int
     */
    int updateCreditGrade(String u_no, int score);

    /*
     * @Description: 通过用户学号获取用户当前信用积分
     * @Param u_no
     * @Return: int
     */
    int getCreditScore(String u_no);

    /*
     * @Description: 修改用户名称
     * @Param u_no  u_name
     * @Return: int
     */
    int setUserName(String u_no, String u_name);

    /*
     * @Description: 设置用户手机号码
     * @Param u_no  u_phone
     * @Return: int
     */
    int setUserPhone(String u_no, String u_phone);

    /*
     * @Description: 修改用户邮箱
     * @Param u_no  u_email
     * @Return: int
     */
    int setUserEmail(String u_no, String u_email);

    /*
     * @Description: 修改用户导师姓名
     * @Param u_no  u_mentorName
     * @Return: int
     */
    int setUserMentorName(String u_no, String u_mentorName);

    /*
     * @Description: 修改用户导师联系方式
     * @Param u_no  u_mentorPhone
     * @Return: int
     */
    int setUserMentorPhone(String u_no, String u_mentorPhone);

    /*
     * @Description: 设置用户专业班级
     * @Param u_no  s_no
     * @Return: int
     */
    int setUserSpecialtyNo(String u_no, int s_no);

    /*
     * @Description: 获取所有的非管理员、用户列表
     * @Param
     * @Return: java.util.List<bean.User>
     */
    List<User> getAllUserList();
}
