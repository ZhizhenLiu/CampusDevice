package dao.impl;

import bean.CreditRecord;
import dao.CreditRecordDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//信用积分变化
public class CreditRecordDaoImpl implements CreditRecordDao {
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    public void initCredit(String userNo) {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        try {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO credit_record(cr_no, u_no, cr_change_score, cr_change_reason, cr_date, cr_after_grade)" +
                  " VALUES (?,?,?,?, CURRENT_DATE ,?)";
            pStmt = con.prepareStatement(sql);

            pStmt.setInt(1, 1);
            pStmt.setString(2, userNo);
            pStmt.setInt(3, 0);
            pStmt.setString(4, "刚刚注册");
            pStmt.setInt(5, 100);

            pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null, pStmt, con);
        }
    }

    /*
     * @Description: 信用积分变动
     * @Param userNo  reason  score:原来分数  changeScore:浮动分数
     * @Return: int
     */
    public int updateCredit(String userNo, String reason, int score, int changeScore)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int flag = 0;

        try {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO credit_record(u_no, cr_change_score, cr_change_reason, cr_date, cr_after_grade) " +
                  "VALUES (?,?,?, CURRENT_DATE ,?)";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setString(1, userNo);
            pStmt.setInt(2, changeScore);
            pStmt.setString(3, reason);

            //最终分数=变动前分数+变动分数
            pStmt.setInt(4, score + changeScore);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(null ,pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 分页查询获取最近积分记录
     * @Param userNo   page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.CreditRecord>
     */
    public List<CreditRecord> getRecordByPage(String userNo, int page, int count)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<CreditRecord> creditRecordList = new ArrayList<>();

        try {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM credit_record WHERE u_no = ?  " +
                  "ORDER BY cr_date " +
                  "LIMIT ? ,?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, userNo);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);
            rs = pStmt.executeQuery();

            //返回CreditRecords
            while (rs.next())
            {
                CreditRecord creditRecord = new CreditRecord();
                creditRecord.setCr_date(rs.getString("cr_date"));
                creditRecord.setCr_change_score(rs.getInt("cr_change_score"));
                creditRecord.setCr_change_reason(rs.getString("cr_change_reason"));
                creditRecordList.add(creditRecord);
            }
            System.out.println("查找信用积分变动信息成功！");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return creditRecordList;
    }


    /*
     * @Description: 查询用户所有的信用分数记录
     * @Param userNo
     * @Return: java.util.List<bean.CreditRecord>
     */
    public List<CreditRecord> getAllCreditRecord(String userNo) {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<CreditRecord> creditRecordList = new ArrayList<>();

        try {
            con = JDBCUtils.getConnection();
            sql = "select * from credit_record";
            pStmt = con.prepareStatement(sql);
            rs = pStmt.executeQuery();

            //返回所有的CreditRecords
            while (rs.next())
            {
                CreditRecord creditRecord = new CreditRecord();
                creditRecord.setCr_date(rs.getString("cr_date"));
                creditRecord.setCr_change_score(rs.getInt("cr_change_score"));
                creditRecord.setCr_change_reason(rs.getString("cr_change_reason"));
                creditRecordList.add(creditRecord);
            }
            System.out.println("查找所有的信用积分变动信息成功！");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return creditRecordList;
    }
}
