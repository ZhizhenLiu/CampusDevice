package dao;

import bean.Message;

import java.util.List;

public interface MessageDao {

    /*
     * @Description: 用户查看消息栏信息 分页查询
     * @Param userNo  page  count
     * @Return: java.util.List<bean.Message>
     */
    List<Message> getMessageByPage(String userNo, int page, int count);

    /*
     * @Description: 获取所有信息的数量
     * @Param userNo
     * @Return: int
     */
    int getAllMessageNum(String userNo);
}
