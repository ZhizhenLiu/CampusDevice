package dao;

import bean.Admin;
import com.alibaba.fastjson.JSONObject;

public interface AdminDao {

    Admin getAdminByWechatId(String wechatId);

}
