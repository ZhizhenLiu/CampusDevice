package servlet.user;

import bean.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet",urlPatterns = "/user/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String u_no = request.getParameter("u_no");
        String u_name = request.getParameter("u_name");
        String u_email = request.getParameter("u_email");
        String u_phone = request.getParameter("u_phone");
        String u_type = request.getParameter("u_type");
        String u_class_major = request.getParameter("u_class_major");
        String u_mentor_name = request.getParameter("u_mentor_name");
        String u_mentor_phone = request.getParameter("u_mentor_phone");
        String code = request.getParameter("code");
        String u_wechatid = code;
        User user = new User(u_no, u_name, u_wechatid, u_email, u_phone, 100, u_type,
                u_mentor_name, u_mentor_phone, u_class_major);

        System.out.println(user);

        userService = new UserServiceImpl();
        userService.registerUser(user);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
