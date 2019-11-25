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

@WebServlet(name = "RefuseReservationServlet", urlPatterns = "/admin/rejectReservation")
public class RefuseReservationServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        int r_no = Integer.parseInt(request.getParameter("r_no"));
        String r_feedBack = request.getParameter("r_feedBack");

        //返回参数
        AdminService adminService = new AdminServiceImpl();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(adminService.refuseBorrow(r_no, r_feedBack).toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
