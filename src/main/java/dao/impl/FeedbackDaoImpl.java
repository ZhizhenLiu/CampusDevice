package dao.impl;

import bean.Feedback;
import bean.Message;
import dao.FeedbackDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDaoImpl implements FeedbackDao
{

    //将用户的反馈信息添加到feedback表中并返回f_no
    @Override
    public int addUserFeedbackToDb(String u_no, String f_content)
    {
        try
        {
            Connection con = JDBCUtils.getConnection();
            String firstSQL = "insert into feedback(u_no,f_content,f_date) values(?,?,CURRENT_DATE)";
            PreparedStatement pStmt = con.prepareStatement(firstSQL);
            pStmt.setString(1, u_no);
            pStmt.setString(2, f_content);
            //执行第一条
            pStmt.executeUpdate();

            //立即获取最近的一条学号为u_no的反馈
            String secondSQL = "select f_no from feedback where u_no= ? order by f_no desc";
            pStmt = con.prepareStatement(secondSQL);
            pStmt.setString(1, u_no);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                return rs.getInt(1);
            }
            con.close();

        }
        catch (Exception e)
        {
            System.out.println("连接错误");
            e.printStackTrace();
        }
        return -1;
    }

    //通过用户的f_no查找到用户的反馈信息
    @Override
    public Feedback getUserByFeedback(int f_no)
    {
        try
        {
            Connection con = JDBCUtils.getConnection();
            String sql = "select * from feedback where f_no = ?";
            PreparedStatement pStmt = con.prepareStatement(sql);

            //获取
            pStmt.setInt(1, f_no);

            ResultSet rs = pStmt.executeQuery();
            Feedback feedback = new Feedback();
            if (rs.next())
            {
                feedback = new Feedback(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4));
            }
            con.close();
            return feedback;

        }
        catch (Exception e)
        {
            System.out.println("连接错误");
            e.printStackTrace();
        }
        return null;
    }

    //分页查询按倒序获取全部反馈（最近）
    @Override
    public List<Feedback> getFeedbackByPage(int page, int count)
    {
        try
        {
            Connection con = JDBCUtils.getConnection();
            String sql = "select * from feedback order by f_no desc limit ?,?";
            PreparedStatement pStmt = con.prepareStatement(sql);

            //获取
            pStmt.setInt(1, (page - 1) * count);
            pStmt.setInt(2, count);

            ResultSet rs = pStmt.executeQuery();
            List<Feedback> list = new ArrayList<>();
            while (rs.next())
            {
                list.add(new Feedback(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4)));
            }
            con.close();
            return list;

        }
        catch (Exception e)
        {
            System.out.println("连接错误");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //一键统计当前反馈（设置关键词，看反馈语句是否包含这些关键词来统计反馈，没有关键词的表明原因为其他）
    @Override
    public List<String> getFeedbackByStatistics()
    {
        return null;
    }

    //管理员回复用户的反馈（提供模板，方便操作）
    @Override
    public int respondToUserFeedback(String m_content, int f_no)
    {
        try
        {
            Connection con = JDBCUtils.getConnection();
            String sql = "INSERT INTO message(u_no, m_content, m_date) " +
                    "VALUES (?, ?, CURRENT_DATE)";
            PreparedStatement pStmt = con.prepareStatement(sql);

            FeedbackDao feedbackDao = new FeedbackDaoImpl();
            Feedback feedback = feedbackDao.getUserByFeedback(f_no);
            SimpleDateFormat sdf = new SimpleDateFormat("y年M月d日");
            String stringDate = sdf.format(feedback.getFb_date());
            String message = "根据您在" + stringDate + "的时候发表的关于“" + feedback.getFb_content() + "”的反馈内容,管理员做出了以下回答：" +
                    "\r\n" + m_content;
            //替换参数，从1开始
            pStmt.setString(1, feedback.getU_no());
            pStmt.setString(2, message);

            int flag = pStmt.executeUpdate();
            con.close();
            return flag;
        }
        catch (SQLException e)
        {
            System.out.println("连接错误");
            e.printStackTrace();
        }
        return -1;
    }
}
