package dao;

import bean.Borrow;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface BorrowDao
{

    /*
     * @Description: 用户查询借用完成的记录(逾期归还:-2 归还未评价:1 归还评价:2)
     * @Param u_no  page  count
     * @Return: java.util.List<bean.Borrow>
     */
    List<Borrow> getFinishedBorrowRecordByPage(String u_no, int page, int count);

    /*
     * @Description: 用户查询借用中的记录( 逾期借用: -1 借用中：0 )
     * @Param u_no  page  count
     * @Return: java.util.List<bean.Borrow>
     */
    List<Borrow> getUnfinishedBorrowRecordByPage(String u_no, int page, int count);

    /*
     * @Description: 管理员查看管辖范围内外借中记录
     * @Param a_no
     * @Return: java.util.List<bean.Borrow>
     */
    List<Borrow> getBorrowList(String a_no);

    /*
     * @Description: 管理员确认设备租借给某个用户
     * @Param u_no  d_no  borrowDate  returnDate
     * @Return: int
     */
    int confirmBorrow(String u_no, String d_no, String borrowDate, String returnDate);

    /*
     * @Description: 设置所有逾期设备状态为 -1 表示逾期未还
     * @Param
     * @Return: int
     */
    int setAllOverDueState();

    /*
     * @Description: 管理员获取管辖范围内预期未还用户
     * @Param a_no  page  count
     * @Return: java.util.List<bean.Borrow>
     */
    List<Borrow> getOverDueList(String a_no, int page, int count);

    /*
     * @Description: 借用中设备按时归还 （逾期归还:-2  逾期借用: -1  借用中:0  归还:1 归还评价:2）
     * @Param b_no
     * @Return: int
     */
    int returnOnTime(int b_no);

    /*
     * @Description: 借用中设备逾期归还 （逾期归还:-2  逾期借用: -1  借用中:0  归还:1 归还评价:2）
     * @Param b_no
     * @Return: int
     */
    int returnOutOfTime(int b_no);

    /*
     * @Description: 完成评价（逾期归还:-2  逾期借用: -1  借用中:0  归还:1 归还评价:2）
     * @Param b_no  b_state
     * @Return: int
     */
    int finishComment(int b_no);

    /*
     * @Description: 根据借用编号查询借用记录
     * @Param b_no
     * @Return: bean.Borrow
     */
    Borrow getBorrowByNo(int b_no);

    /*
     * @Description: 以String的形式返回逾期人学工号、逾期人名、设备编号、设备名、借用日期、应当归还日期、实际归还日期、借用人信用分、管理仪器的管理员的名字
     * @Param b_no
     * @Return: List<String>
     */
    List<String> getOverDueList();

    /*
     * @Description: 获得逾期的用户借用信息以及他未还的天数
     * @Param b_no
     * @Return: int
     */
    List<Integer> getOverDueBorrowAndDays();

    /*
     * @Description: 以String的形式返回逾期人学工号、逾期人名、设备编号、设备名、借用日期、应当归还日期、实际归还日期、借用人信用分、管理仪器的管理员的名字
     * @Param b_no
     * @Return: List<String>
     */
    //修改：只能导出自己管理的逾期未还人员名单
    List<String> getOverDueList(String a_no);

}
