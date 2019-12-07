package servlet.admin;

import service.AdminService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EditReservationServlet", urlPatterns = "/admin/editReservation")
public class EditReservationServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        int r_no = Integer.parseInt(request.getParameter("r_no"));
        String startDate = request.getParameter("startDate");
        String returnDate = request.getParameter("returnDate");
        String feedBack = request.getParameter("feedBack");

        //返回操作结果
        PrintWriter printWriter = response.getWriter();
        AdminService adminService = new AdminServiceImpl();
        printWriter.write(adminService.editReservation(r_no, startDate, returnDate, feedBack).toJSONString());
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
