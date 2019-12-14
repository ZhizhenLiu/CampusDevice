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

@WebServlet(name = "AddDeviceServlet", urlPatterns = "/admin/addDevice")
public class AddDeviceServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String d_no = request.getParameter("d_no");
        String d_name = request.getParameter("d_name");
        String d_model = request.getParameter("d_model");
        String d_saveSite = request.getParameter("d_saveSite");
        String a_no = request.getParameter("a_no");
        String d_factoryNo = request.getParameter("d_factoryNo");
        String d_state = request.getParameter("d_state");
        String d_storeDate = request.getParameter("d_storeDate");
        Device device = new Device();
        device.setD_no(d_no);
        device.setD_name(d_name);
        device.setD_model(d_model);
        device.setD_saveSite(d_saveSite);
        device.setA_no(a_no);
        device.setD_factoryNo(d_factoryNo);
        device.setD_state(d_state);
        device.setD_storeDate(d_storeDate);

        //返回参数
        AdminService adminService = new AdminServiceImpl();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(adminService.addDevice(device).toJSONString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
