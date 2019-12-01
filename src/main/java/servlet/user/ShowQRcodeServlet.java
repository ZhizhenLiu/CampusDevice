package servlet.user;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "ShowQRcodeServlet", urlPatterns = "/user/showQRcode")
public class ShowQRcodeServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            FileInputStream hFile = new FileInputStream("E:/code.png"); // 以byte流的方式打开文件 d:\1.gif
            int i = hFile.available(); //得到文件大小
            byte data[] = new byte[i];
            hFile.read(data); //读数据
            hFile.close();
            response.setContentType("image/png"); //设置返回的文件类型
            OutputStream toClient = response.getOutputStream(); //得到向客户端输出二进制数据的对象
            toClient.write(data); //输出数据
            toClient.close();
        }
        catch (IOException e) //错误处理
        {
            PrintWriter toClient = response.getWriter(); //得到向客户端输出文本的对象
            response.setContentType("text/html;charset=UTF-8");
            toClient.write("无法打开图片!");
            toClient.close();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
