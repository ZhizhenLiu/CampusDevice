package dao;

import com.alibaba.fastjson.JSONObject;

public interface ReturnDeviceDao {

    /*
     * @Description: 归还设备
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    int ReturnDevice(String u_no, int d_no, int b_no);
}
