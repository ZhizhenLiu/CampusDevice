package dao.impl;

import bean.Comment;
import dao.CommentDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 添加评论
     * @Param u_no  d_no  comment
     * @Return: int
     */
    public int addComment(String u_no, String d_no, String comment)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int flag = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO comment (d_no, u_no, c_date, c_content) VALUES (?, ?, CURRENT_DATE , ?)";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_no);
            pStmt.setString(2, u_no);
            pStmt.setString(3, comment);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 分页查询查看评论
     * @Param d_no  page  count
     * @Return: java.util.List<bean.Comment>
     */
    public List<Comment> getCommentListByPage(String d_no, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Comment> commentList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM comment WHERE d_no = ? ORDER BY c_date DESC " +
                  "LIMIT ?, ? ";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_no);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);

            rs = pStmt.executeQuery();

            while (rs.next())
            {
                commentList.add(new Comment(rs.getInt("c_no"), rs.getString("d_no"), rs.getString("u_no"),
                        rs.getString("c_date"), rs.getString("c_content")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }

        return commentList;
    }
}
