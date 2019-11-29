package dao;

import bean.Comment;

import java.util.List;

public interface CommentDao
{
    /*
     * @Description: 添加评论
     * @Param u_no  d_no  comment
     * @Return: int
     */
    int addComment(String u_no, String d_no, String comment);


    /*
     * @Description: 分页查询查看评论
     * @Param d_no  page  count
     * @Return: java.util.List<bean.Comment>
     */
    List<Comment> getCommentListByPage(String d_no, int page, int count);

}
