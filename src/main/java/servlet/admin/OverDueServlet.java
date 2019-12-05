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

@WebServlet(name = "OverDueServlet", urlPatterns = "/admin/overDue")
public class OverDueServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String code = request.getParameter("code");
        int page = Integer.parseInt(request.getParameter("page"));

        //向微信服务器接口发送code，获取管理员唯一标识openid, 返回参数
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        JSONObject info = null;
        PrintWriter printWriter = response.getWriter();
        AdminService adminService = new AdminServiceImpl();
        String wechatID = "";

        //请求成功,获取管理员openid
        if (result.containsKey("openid"))
        {
            wechatID = (String) result.get("openid");
            printWriter.write(adminService.getOverDueByPage(wechatID, page, 10).toJSONString());
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errMsg", result.get("errMsg"));
            info.put("flag", "0");
            printWriter.write(result.get("errMsg").toString());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
