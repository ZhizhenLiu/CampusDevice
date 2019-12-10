package dao;

import bean.Admin;
import com.alibaba.fastjson.JSONObject;

public interface AdminDao
{
    //通过微信号获取管理员
    Admin getAdminByWechatID(String wechatID);

}
