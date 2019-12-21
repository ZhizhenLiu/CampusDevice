package servlet.admin;

import bean.Device;
import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EditDeviceServlet", urlPatterns = "/admin/editDevice")
public class EditDeviceServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String d_no = request.getParameter("d_no");
        String d_name = request.getParameter("d_name");
        String d_state = request.getParameter("d_state");
        String d_model = request.getParameter("d_model");
        Device device = new Device();
        device.setD_no(d_no);
        device.setD_name(d_name);
        device.setD_state(d_state);
        device.setD_model(d_model);

        //返回结果
        PrintWriter printWriter = response.getWriter();
        AdminService adminService = new AdminServiceImpl();
        printWriter.write(adminService.editDevice(device).toJSONString());

        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
