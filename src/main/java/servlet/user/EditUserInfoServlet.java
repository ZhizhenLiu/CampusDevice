package servlet.user;

import bean.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.io.PrintWriter;

@javax.servlet.annotation.WebServlet(name = "EditUserInfoServlet", urlPatterns = "/user/editUserInfo")
public class EditUserInfoServlet extends javax.servlet.http.HttpServlet
{
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException
    {

        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String u_no = request.getParameter("u_no");
        String u_name = request.getParameter("u_name");
        String u_phone = request.getParameter("u_phone");
        String u_email = request.getParameter("u_email");
        String u_mentorName = request.getParameter("u_mentorName");
        String u_mentorPhone = request.getParameter("u_mentorPhone");

        //返回参数
        PrintWriter printWriter = response.getWriter();
        User user = new User();
        user.setU_no(u_no);
        user.setU_name(u_name);
        user.setU_phone(u_phone);
        user.setU_email(u_email);
        user.setU_mentorName(u_mentorName);
        user.setU_mentorPhone(u_mentorPhone);

        UserService userService = new UserServiceImpl();
        printWriter.write(userService.editUserInfo(user).toJSONString());

        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException
    {
        doPost(request, response);
    }
}
