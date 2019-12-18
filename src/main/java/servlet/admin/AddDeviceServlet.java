package servlet.admin;

import bean.Device;
import com.alibaba.fastjson.JSONObject;
import dao.AdminDao;
import dao.DeviceDao;
import dao.impl.AdminDaoImpl;
import dao.impl.DeviceDaoImpl;
import service.AdminService;
import service.impl.AdminServiceImpl;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddDeviceServlet", urlPatterns = "/admin/addDevice")
public class AddDeviceServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //获取参数
        String code = request.getParameter("code");
        String d_no = request.getParameter("d_no");
        String d_name = request.getParameter("d_name");
        String d_model = request.getParameter("d_model");
        String d_saveSite = request.getParameter("d_saveSite");
        String d_factoryNo = request.getParameter("d_factoryNo");
        String d_state = request.getParameter("d_state");
        String d_storeDate = request.getParameter("d_storeDate");
        Device device = new Device();
        device.setD_no(d_no);
        device.setD_name(d_name);
        device.setD_model(d_model);
        device.setD_saveSite(d_saveSite);
        device.setD_factoryNo(d_factoryNo);
        device.setD_state(d_state);
        device.setD_storeDate(d_storeDate);


        //向微信服务器接口发送code，获取用户唯一标识openid, 返回参数
        AdminService adminService = new AdminServiceImpl();
        JSONObject result = JSONObject.parseObject(HttpUtils.sendGet(code));
        JSONObject info = null;
        PrintWriter printWriter = response.getWriter();
        String wechatID = "";

        //请求成功,获取管理员openid
        if (result.containsKey("openid"))
        {
            wechatID = (String) result.get("openid");
            AdminDao adminDao = new AdminDaoImpl();
            device.setA_no(adminDao.getAdminByWechatID(wechatID).getA_no());
            info = adminService.addDevice(device);
        }
        //请求失败，返回错误信息
        else
        {
            info.put("errMsg", result.get("errMsg"));
            info.put("flag", "0");
        }

        printWriter.write(info.toJSONString());
        printWriter.flush();
        printWriter.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
