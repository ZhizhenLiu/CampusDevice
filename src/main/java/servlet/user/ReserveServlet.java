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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "ReserveServlet", urlPatterns = "/user/reserve")
public class ReserveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        int d_no = Integer.parseInt(request.getParameter("d_no"));
        String code = request.getParameter("code");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null, endDate = null;
        try {
             startDate = format.parse(request.getParameter("startDate"));
             endDate = format.parse(request.getParameter("endDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        JSONObject info = null;
        PrintWriter printWriter = response.getWriter();
        UserService userService = new UserServiceImpl();
        String wechatId = "";
        //请求成功
        if (result.get("errcode").equals("0"))
        {
            wechatId = (String) result.get("openid");
            info = userService.reserveDevice(d_no, wechatId, startDate, endDate);
            printWriter.write(info.toJSONString());
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errms",result.get("errmsg"));
            info.put("flag","0");
            printWriter.write(result.get("errmsg").toString());
        }
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
