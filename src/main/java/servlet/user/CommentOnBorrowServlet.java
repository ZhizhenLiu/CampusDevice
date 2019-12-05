package servlet.user;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CommentOnBorrowServlet", urlPatterns = "/user/comment")
public class CommentOnBorrowServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        int b_no = Integer.parseInt(request.getParameter("b_no"));
        String comment = request.getParameter("comment");

        //返回参数
        UserService userService = new UserServiceImpl();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(userService.commentOnDevice(b_no, comment).toJSONString());

        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
