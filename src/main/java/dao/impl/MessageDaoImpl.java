package dao.impl;

import bean.Message;
import dao.MessageDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    /*
     * @Description: 用户查看消息栏信息 分页查询
     * @Param userNo  page  count
     * @Return: java.util.List<bean.Message>
     */
    public List<Message> getMessageByPage(String u_no, int page, int count)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        List<Message> messageList = new ArrayList<>();

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT * FROM message WHERE u_no = ? LIMIT ?,?";
            m_pStmt = m_con.prepareStatement(m_sql);
            //执行操作
            m_pStmt.setString(1, u_no);
            m_pStmt.setInt(2, (page-1)*count);
            m_pStmt.setInt(3, count);
            m_rs = m_pStmt.executeQuery();
            while(m_rs.next())
            {
                messageList.add(new Message(m_rs.getInt(1), m_rs.getString(2), m_rs.getString(3), m_rs.getDate(4)));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return messageList;
    }

    /*
     * @Description: 系统向用户发送消息
     * @Param u_no  m_content
     * @Return: int
     */
    public int sendMessage(String u_no, String m_content)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        int flag = 0;
        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "INSERT INTO message(u_no, m_content, m_date) " +
                    "VALUES (?, ?, CURRENT_DATE)";
            m_pStmt = m_con.prepareStatement(m_sql);

            //替换参数，从1开始
            m_pStmt.setString(1,u_no);
            m_pStmt.setString(2, m_content);

            flag = m_pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return flag;
    }

    /*
     * @Description: 获取所有信息的数量
     * @Param userNo
     * @Return: int
     */
    public int getAllMessageNum(String u_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        int num = 0;

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "select count(*) from message where u_no = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //执行操作
            m_pStmt.setString(1, u_no);
            m_rs = m_pStmt.executeQuery();

            if(m_rs.next())
            {
                num = m_rs.getInt(1);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return num;
    }
}
