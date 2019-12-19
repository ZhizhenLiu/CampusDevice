package servlet.admin;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.AdminService;
import service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = "/admin/upload")
public class UploadServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        //获取文件需要上传到的路径
        String path = getServletContext().getRealPath("/") + "img/device";

        System.out.println(path);
        File dir = new File(path);
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();

        /*
        如果没以下两行设置的话,上传大的文件会占用很多内存，
        设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
        原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
              按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
              然后再将其真正写到对应目录的硬盘上
         */

        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
        factory.setRepository(dir);
        //高水平的API文件上传处理
        factory.setSizeThreshold(1024 * 1024);
        String d_no = null;
        String destPath = null;
        String deviceUrl = "https://www.tozsy.com/CampusDevice/img/device";
        ServletFileUpload upload = new ServletFileUpload(factory);
        try
        {
            List<FileItem> list = upload.parseRequest(request);
            FileItem picture = null;
            for (FileItem item : list)
            {
                //如果获取的表单信息是普通的文本信息 而且表单的属性名字为d_no
                if (item.isFormField() && item.getFieldName().equals("d_no"))
                {
                    //获取具体输入的字符串
                    d_no = item.getString();

                    //定义路径: 设置图片的名称为：d_no-yyyy-MM-dd-HH-mm-ss，便于小程序渲染
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("-HH-mm-ss");
                    String time =  simpleDateFormat.format(date);
                    destPath = path + "/" + d_no + time +".png";
                    deviceUrl = deviceUrl + "/" + d_no + time + ".png";
                }
                else
                {
                    picture = item;
                }
            }

            //自定义上传图片的名字
            System.out.println(destPath);
            //真正写到磁盘上
            File file = new File(destPath);
            OutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = picture.getInputStream();
            int data = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((data = inputStream.read(buf)) != -1)
            {
                //在buf数组中取出数据写到（输出流）磁盘上
                outputStream.write(buf, 0, data);
            }
            inputStream.close();
            outputStream.close();
        }
        catch (FileUploadException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        PrintWriter printWriter = response.getWriter();
        AdminService adminService = new AdminServiceImpl();
        System.out.println(d_no +" "+ deviceUrl);
        printWriter.write(adminService.setDeviceImgUrl(d_no, deviceUrl).toJSONString());

        printWriter.flush();
        printWriter.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
