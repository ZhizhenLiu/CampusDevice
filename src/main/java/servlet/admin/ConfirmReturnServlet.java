package servlet.admin;

import bean.User;
import com.alibaba.fastjson.JSONObject;
import service.AdminService;
import service.UserService;
import service.impl.AdminServiceImpl;
import service.impl.UserServiceImpl;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ConfirmReturnServlet", urlPatterns = "/admin/confirmReturn")
public class ConfirmReturnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String code = request.getParameter("code");
        int d_no = Integer.parseInt(request.getParameter("d_no"));

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        System.out.println(result);
        JSONObject returnData = null;
        PrintWriter printWriter = response.getWriter();
        AdminService adminService = new AdminServiceImpl();
        String wechatId = "";
        //请求成功
        if (result.containsKey("openid"))
        {
            wechatId = (String) result.get("openid");
            adminService.confirmReturn(wechatId, d_no);
            printWriter.write(returnData.toJSONString());
        }
        //请求失败，返回错误信息
        else
        {
            returnData.put("errms",result.get("errmsg"));
            returnData.put("flag","0");
            printWriter.write(returnData.toJSONString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
