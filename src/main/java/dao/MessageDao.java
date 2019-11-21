package dao;

import bean.Message;

import java.util.List;

public interface MessageDao {

    /*
     * @Description: 用户查看消息栏信息 分页查询
     * @Param u_no  page  count
     * @Return: java.util.List<bean.Message>
     */
    List<Message> getMessageByPage(String u_no, int page, int count);

    /*
     * @Description: 获取所有信息的数量
     * @Param u_no
     * @Return: int
     */
    int getAllMessageNum(String u_no);
}
