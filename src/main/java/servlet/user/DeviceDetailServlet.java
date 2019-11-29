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

@WebServlet(name = "DeviceDetailServlet", urlPatterns = "/user/deviceDetail")
public class DeviceDetailServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String d_no = request.getParameter("d_no");

        //返回参数
        UserService userService = new UserServiceImpl();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(userService.getDeviceDetails(d_no).toJSONString());

        printWriter.flush();
        printWriter.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
