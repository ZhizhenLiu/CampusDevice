package dao;

import com.alibaba.fastjson.JSONObject;

public interface BorrowDao {

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject confirmBorrow(String u_no, int d_no, String borrowDate, String returnDate);

    /*
     * @Description: 管理员获取预期未还用户
     * @Param a_no
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject getOverDue(int a_no);

    /*
     * @Description: 借用中设备归还 （0：借用中，1：归还）
     * @Param u_no  d_no
     * @Return: int
     */
    int returnBorrow(String u_no, int d_no);
}
