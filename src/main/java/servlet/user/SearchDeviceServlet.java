package servlet.user;

import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SearchDeviceServlet", urlPatterns = "/user/searchDevice")
public class SearchDeviceServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取检索关键字参数
        String keyword = request.getParameter("keyword");
        int page = Integer.parseInt(request.getParameter("page"));

        //返回信息
        UserService userService = new UserServiceImpl();
        PrintWriter printWriter = response.getWriter();
        printWriter.write(userService.getDeviceByPageWithKeyword(keyword, page, 10).toJSONString());

        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        doPost(request, response);
    }
}
