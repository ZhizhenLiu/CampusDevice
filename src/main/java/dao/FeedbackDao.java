package dao;

import bean.Feedback;
import bean.Message;

import java.util.List;

public interface FeedbackDao
{

    //将用户的反馈信息添加到feedback表中并返回f_no
    int addUserFeedbackToDb(String u_no, String f_content);

    //通过用户的f_no查找到用户的反馈信息
    Feedback getUserByFeedback(int f_no);

    //分页查询按倒序获取全部反馈（最近）
    List<Feedback> getFeedbackByPage(int page, int count);

    //一键统计当前反馈（设置关键词，看反馈语句是否包含这些关键词来统计反馈，没有关键词的表明原因为其他）
    List<String> getFeedbackByStatistics();

    //管理员回复用户的反馈（提供模板，方便操作）
    int respondToUserFeedback(String m_content, int f_no);
}
