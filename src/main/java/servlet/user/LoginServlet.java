package servlet.user;

import com.alibaba.fastjson.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet",urlPatterns = "/user/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        UserService userService1 = new UserServiceImpl();
        String code = request.getParameter("code");

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        JSONObject returnData = null;
        PrintWriter printWriter = response.getWriter();
        UserService userService = new UserServiceImpl();
        String wechatId = "";

        //请求成功,获取用户openiid
        if (result.get("errcode").equals("0"))
        {
            wechatId = (String) result.get("openid");

            //查询对应openid的用户是否存在
            //返回查询：flag为0：不存在用户。
            // flag为1：存在该用户
            printWriter.write(userService.getUserBywechatId(wechatId).toJSONString());
        }
        //请求失败，返回错误信息
        else
        {
            returnData.put("errmsg",result.get("errmsg"));
            returnData.put("flag","0");
            printWriter.write(result.get("errmsg").toString());
        }






        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}