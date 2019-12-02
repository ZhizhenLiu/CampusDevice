package servlet.user;

import utils.QRCodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ShowQRCodeServlet", urlPatterns = "/user/showQRCode")
public class ShowQRCodeServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取二维码填入内容参数
        String b_no = request.getParameter("b_no");
        String path = getServletContext().getRealPath("/");
        String codeUrl = QRCodeUtils.createQRCode(b_no, path);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(codeUrl);
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
