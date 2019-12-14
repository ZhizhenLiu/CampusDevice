package servlet.admin;

import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SetUserAsAdminServlet", urlPatterns = "/admin/setUserAsAdmin")
public class SetUserAsAdminServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String u_no = request.getParameter("u_no");

        //返回参数
        AdminService adminService = new AdminServiceImpl();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(adminService.setUserAsAdmin(u_no).toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
