package dao;

import com.alibaba.fastjson.JSONObject;

public interface ReturnDeviceDao
{

    /*
     * @Description: 归还设备
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    int returnDevice(String u_no, String d_no, int b_no);
}
