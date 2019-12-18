package servlet.admin;

import bean.Device;
import com.alibaba.fastjson.JSONObject;
import dao.AdminDao;
import dao.impl.AdminDaoImpl;
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

@WebServlet(name = "SearchDeviceOfAdminServlet", urlPatterns = "/admin/searchDevice")
public class SearchDeviceOfAdminServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String code = request.getParameter("code");
        String keyword = request.getParameter("keyword");
        int page = Integer.parseInt(request.getParameter("page"));

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        AdminService adminService = new AdminServiceImpl();
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        JSONObject info = null;
        PrintWriter printWriter = response.getWriter();
        String wechatID = "";

        //请求成功,获取管理员openid
        if (result.containsKey("openid"))
        {
            wechatID = (String) result.get("openid");
            info = adminService.getDeviceByPageWithKeyword(wechatID, keyword, page, 10);
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errMsg", result.get("errMsg"));
            info.put("flag", "0");
        }

        printWriter.write(info.toJSONString());
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
