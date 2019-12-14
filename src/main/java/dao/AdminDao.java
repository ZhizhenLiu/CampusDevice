package dao;

import bean.Admin;
import bean.User;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AdminDao
{
    /*
     * @Description: 通过微信唯一标识获取管理员信息
     * @Param wechatID
     * @Return: bean.Admin
     */
    Admin getAdminByWechatID(String wechatID);

    /*
     * @Description: 通过a_no获取管理员
     * @Param a_no
     * @Return: bean.Admin
     */
    Admin getAdminByNo(String a_no);

    /*
     * @Description: 获取普通管理员列表
     * @Param
     * @Return: java.util.List<bean.Admin>
     */
    List<Admin> getNormalAdminList();

   /*
    * @Description: 设置用户为管理员
    * @Param user
    * @Return: int
    */
    int setUserAsAdmin(User user);

    /*
     * @Description: 删除管理员
     * @Param a_no
     * @Return: int
     */
    int deleteAdmin(String a_no);

}
