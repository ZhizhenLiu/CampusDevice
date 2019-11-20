package dao;

import bean.CreditRecord;

import java.util.List;

public interface CreditRecordDao {

    //初始化信用积分
    void initCredit(String userNo);

    /*
     * @Description: 信用积分变动
     * @Param userNo  reason  score:原来分数  changeScore:浮动分数
     * @Return: int
     */
    int updateCredit(String userNo, String reason, int score, int changeScore);

    /*
     * @Description: 分页查询获取最近积分记录
     * @Param userNo   page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.CreditRecord>
     */
    List<CreditRecord> getRecordByPage(String userNo, int page, int count);

    /*
     * @Description: 查询用户所有的信用分数记录
     * @Param userNo
     * @Return: java.util.List<bean.CreditRecord>
     */
    List<CreditRecord> getAllCreditRecord(String userNo);
}
