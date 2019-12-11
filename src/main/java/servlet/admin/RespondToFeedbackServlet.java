package servlet.admin;

import com.alibaba.fastjson.JSONObject;
import service.AdminService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.UserServiceImpl;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RespondToFeedbackServlet", urlPatterns = "/admin/respondToFeedback")
public class RespondToFeedbackServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        int f_no = Integer.parseInt(request.getParameter("f_no"));
        String content = request.getParameter("content");

        //返回参数
        AdminService adminService = new AdminServiceImpl();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(adminService.respondToUserFeedback(content, f_no).toJSONString());

        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
