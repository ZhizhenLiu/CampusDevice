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

@WebServlet(name = "HandleBorrowServlet", urlPatterns = "/admin/handleBorrow")
public class HandleBorrowServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String code = request.getParameter("code");

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        AdminService adminService = new AdminServiceImpl();
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        JSONObject info = null;
        PrintWriter printWriter = response.getWriter();
        String wechatId = "";

        //请求成功,获取管理员openid
        if (result.containsKey("openid"))
        {
            wechatId = (String) result.get("openid");
            printWriter.write(adminService.getBorrowedDevice(wechatId).toJSONString());
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errMsg", result.get("errMsg"));
            info.put("flag", "0");
            printWriter.write(result.get("errMsg").toString());
        }
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
