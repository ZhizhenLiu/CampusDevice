package servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.MessageUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "VerifyCodeServlet", urlPatterns = "/verifyCode")
public class VerifyCodeServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String phone = request.getParameter("phone");

        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();

        String verifyCode = MessageUtils.sendVerifyCode(phone);
        if (verifyCode == null)
        {
            info.put("flag", 0);
            errMsg.add("发送验证码失败");
        }
        else
        {
            info.put("flag", 1);
            info.put("verifyCode", verifyCode);
        }

        PrintWriter printWriter = response.getWriter();
        printWriter.write(info.toJSONString());
        printWriter.flush();
        printWriter.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
