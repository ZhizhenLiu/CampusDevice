package dao;

import bean.Borrow;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface BorrowDao
{

    /*
     * @Description: 查询用户借用的记录(借用中b_state=0，归还b_state=1,逾期未还b_state= -1)
     * @Param u_no
     * @Return: java.util.List<bean.Borrow>
     */
    List<Borrow> getBorrowRecord(String u_no);

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: int
     */
    int confirmBorrow(String u_no, int d_no, String borrowDate, String returnDate);

    /*
     * @Description: 设置所有逾期设备状态为 -1 表示逾期未还
     * @Param
     * @Return: int
     */
    int setAllStateOverDue();

    /*
     * @Description: 管理员获取管辖范围内预期未还用户
     * @Param a_no
     * @Return: java.util.List<bean.Borrow>
     */
    List<Borrow> getOverDueList(int a_no);

    /*
     * @Description: 借用中设备归还 （0：借用中，1：归还 -1:逾期）
     * @Param b_no
     * @Return: int
     */
    int returnBorrow(int b_no);

    /*
     * @Description: 获取借用记录编号
     * @Param u_no  d_no
     * @Return: int
     */
    int getBorrowNo(String u_no, int d_no);

}
