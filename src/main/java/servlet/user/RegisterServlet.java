package servlet.user;

import bean.User;
import com.alibaba.fastjson.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.HttpUtils;
import utils.MessageUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet",urlPatterns = "/user/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //获取参数
        String u_no = request.getParameter("u_no");
        String u_name = request.getParameter("u_name");
        String u_email = request.getParameter("u_email");
        String u_phone = request.getParameter("u_phone");
        String u_type = request.getParameter("u_type");
        String u_class_major = request.getParameter("u_class_major");
        String u_mentor_name = request.getParameter("u_mentor_name");
        String u_mentor_phone = request.getParameter("u_mentor_phone");
        String code = request.getParameter("code");

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        System.out.println( "code: " +  code);
        System.out.println( "u_email: " +  u_email);
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        System.out.println(result);
        JSONObject info = null;
        PrintWriter printWriter = response.getWriter();
        UserService userService = new UserServiceImpl();
        String wechatId = "";
        //请求成功
        if (result.containsKey("openid"))
        {
            wechatId = (String) result.get("openid");
            System.out.println(result);
            User user = new User(u_no, u_name, wechatId, u_email, u_phone, 100, u_type, u_mentor_name, u_mentor_phone,u_class_major);
            System.out.println(user);
            info = userService.registerUser(user);
            printWriter.write(info.toJSONString());
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errms",result.get("errmsg"));
            info.put("flag","0");
            printWriter.write(info.toJSONString());
        }
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
