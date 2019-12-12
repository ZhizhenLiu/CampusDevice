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

@WebServlet(name = "RegisterServlet", urlPatterns = "/user/register")
public class RegisterServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String u_no = request.getParameter("u_no");
        String u_name = request.getParameter("u_name");
        String u_email = request.getParameter("u_email");
        String u_phone = request.getParameter("u_phone");
        String u_type = request.getParameter("u_type");

        //老师暂无专业班级
        int s_no = -1;
        if (u_type.equals("student")) s_no = Integer.parseInt(request.getParameter("s_no"));

        String u_mentorName = request.getParameter("u_mentorName");
        String u_mentorPhone = request.getParameter("u_mentorPhone");
        String code = request.getParameter("code");

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        JSONObject info = null;
        PrintWriter printWriter = response.getWriter();
        UserService userService = new UserServiceImpl();
        String wechatID = "";

        //请求成功
        if (result.containsKey("openid"))
        {
            wechatID = (String) result.get("openid");
            User user = new User(u_no, u_name, wechatID, u_email, u_phone, u_type.equals("student")?100:200, u_type.equals("student")?"学生":"老师", u_mentorName, u_mentorPhone, s_no, null, -1, null);
            System.out.println(user);
            info = userService.registerUser(user);
            printWriter.write(info.toJSONString());
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errms", result.get("errMsg"));
            info.put("flag", 0);
            printWriter.write(info.toJSONString());
        }

        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
