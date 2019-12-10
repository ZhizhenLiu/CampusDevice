package servlet.admin;

import com.alibaba.fastjson.JSONObject;
import service.AdminService;
import service.impl.AdminServiceImpl;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ConfirmReturnServlet", urlPatterns = "/admin/confirmReturn")
public class ConfirmReturnServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        int b_no = Integer.parseInt(request.getParameter("b_no"));
        String rd_state = request.getParameter("rd_state");
        String comment = request.getParameter("comment");

        //向微信服务器接口发送code，获取管理员唯一标识openid, 返回参数
        PrintWriter printWriter = response.getWriter();
        AdminService adminService = new AdminServiceImpl();
        printWriter.write(adminService.confirmReturn(b_no, rd_state, comment).toJSONString());
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
