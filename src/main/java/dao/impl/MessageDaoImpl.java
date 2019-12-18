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

public class MessageDaoImpl implements MessageDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 用户查看消息栏信息 分页查询
     * @Param userNo  page  count
     * @Return: java.util.List<bean.Message>
     */
    public List<Message> getMessageByPage(String u_no, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Message> messageList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            //按照最近的未读消息先展示的顺序
            sql = "SELECT * FROM message WHERE u_no = ? ORDER BY m_state asc, m_no DESC LIMIT ?,?";
            pStmt = con.prepareStatement(sql);
            //执行操作
            pStmt.setString(1, u_no);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                messageList.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
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
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            //刚发的message的状态为未读
            sql = "INSERT INTO message(u_no, m_content, m_date, m_state) " +
                    "VALUES (?, ?, CURRENT_DATE,0)";
            pStmt = con.prepareStatement(sql);

            //替换参数，从1开始
            pStmt.setString(1, u_no);
            pStmt.setString(2, m_content);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 获取所有信息的数量
     * @Param userNo
     * @Return: int
     */
    public List<Message> getAllUnReadMessage(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Message> messageList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM message WHERE u_no = ? AND m_state = 0 ORDER BY m_no DESC ";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, u_no);
            rs = pStmt.executeQuery();

           while (rs.next())
           {
               messageList.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
           }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return messageList;
    }

    /*
     * @Description: 将所有消息的状态设为已读
     * @Param u_no
     * @Return: int
     */
    @Override
    public int setAllMessageHaveRead(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int flag = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "UPDATE message SET m_state = 1 WHERE u_no = ? AND m_state = 0";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, u_no);
            flag = pStmt.executeUpdate();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }
}
