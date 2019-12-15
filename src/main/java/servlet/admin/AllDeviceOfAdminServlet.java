package servlet.admin;

import com.alibaba.fastjson.JSONObject;
import service.AdminService;
import service.SystemService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.SystemServiceImpl;
import service.impl.UserServiceImpl;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AllDeviceOfAdminServlet", urlPatterns = "/admin/all")
public class AllDeviceOfAdminServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String code = request.getParameter("code");
        int page = Integer.parseInt(request.getParameter("page"));

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));

        JSONObject info = new JSONObject();
        PrintWriter printWriter = response.getWriter();
        AdminService adminService = new AdminServiceImpl();

        //请求成功,获取用户openid
        if (result.containsKey("openid"))
        {
            String wechatID = (String) result.get("openid");
            info = adminService.getAllDeviceOfAdmin(wechatID, page, 10);
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errMsg", result.get("errmsg"));
            info.put("flag", "0");
        }
        printWriter.write(info.toJSONString());
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
