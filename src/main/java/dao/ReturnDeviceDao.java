package dao;

import com.alibaba.fastjson.JSONObject;

public interface ReturnDeviceDao
{

    /*
     * @Description: 管理员确认设备归还，修改状态
     * @Param u_no  d_no  b_no  rd_state  comment
     * @Return: int
     */
    int returnDevice(String u_no, String d_no, int b_no, String rd_state, String comment);
}
