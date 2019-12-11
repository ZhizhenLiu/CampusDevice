package dao;

import bean.CreditRecord;

import java.util.List;

public interface CreditRecordDao
{
    //初始化信用积分
    int initCredit(String u_no, int score);

    /*
     * @Description: 信用积分变动
     * @Param u_no  reason  score:原来分数  changeScore:浮动分数
     * @Return: int
     */
    int updateCredit(String u_no, String reason, int score, int changeScore);

    /*
     * @Description: 分页查询获取最近积分记录
     * @Param u_no   page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.CreditRecord>
     */
    List<CreditRecord> getRecordByPage(String u_no, int page, int count);

    /*
     * @Description: 查询用户所有的信用分数记录
     * @Param u_no
     * @Return: java.util.List<bean.CreditRecord>
     */
    List<CreditRecord> getAllCreditRecord(String u_no);

    /*
     * @Description: 添加信用积分变动
     * @Param u_no, days
     * @Return: int
     */
    int addRecord(int b_no, int days);
}
