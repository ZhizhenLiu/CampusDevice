package dao.impl;

import bean.CreditRecord;
import dao.CreditRecordDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//信用积分变化
public class CreditRecordDaoImpl implements CreditRecordDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    public void initCredit(String u_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "INSERT INTO credit_record(cr_no, u_no, cr_change_score, cr_change_reason, cr_date, cr_after_grade)" +
                  " VALUES (?,?,?,?, CURRENT_DATE ,?)";
            m_pStmt = m_con.prepareStatement(m_sql);

            m_pStmt.setInt(1, 1);
            m_pStmt.setString(2, u_no);
            m_pStmt.setInt(3, 0);
            m_pStmt.setString(4, "刚刚注册");
            m_pStmt.setInt(5, 100);

            m_pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, m_pStmt, m_con);
        }
    }

    /*
     * @Description: 信用积分变动
     * @Param userNo  reason  score:原来分数  changeScore:浮动分数
     * @Return: int
     */
    public int updateCredit(String u_no, String reason, int score, int changeScore)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        int flag = 0;

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "INSERT INTO credit_record(u_no, cr_change_score, cr_change_reason, cr_date, cr_after_grade) " +
                  "VALUES (?,?,?, CURRENT_DATE ,?)";
            PreparedStatement pStmt = m_con.prepareStatement(m_sql);
            pStmt.setString(1, u_no);
            pStmt.setInt(2, changeScore);
            pStmt.setString(3, reason);

            //最终分数=变动前分数+变动分数
            pStmt.setInt(4, score + changeScore);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null , m_pStmt, m_con);
        }
        return flag;
    }

    /*
     * @Description: 分页查询获取最近积分记录
     * @Param userNo   page: 页数，第几页  count：每页设备数量
     * @Return: java.util.List<bean.CreditRecord>
     */
    public List<CreditRecord> getRecordByPage(String u_no, int page, int count)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        List<CreditRecord> creditRecordList = new ArrayList<>();

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT * FROM credit_record WHERE u_no = ?  " +
                  "ORDER BY cr_date " +
                  "LIMIT ? ,?";
            m_pStmt = m_con.prepareStatement(m_sql);
            m_pStmt.setString(1, u_no);
            m_pStmt.setInt(2, (page - 1) * count);
            m_pStmt.setInt(3, count);
            m_rs = m_pStmt.executeQuery();

            //返回CreditRecords
            while (m_rs.next())
            {
                CreditRecord creditRecord = new CreditRecord();
                creditRecord.setM_CrDate(m_rs.getString("cr_date"));
                creditRecord.setM_CrChangeScore(m_rs.getInt("cr_change_score"));
                creditRecord.setM_CrChangeReason(m_rs.getString("cr_change_reason"));
                creditRecordList.add(creditRecord);
            }
            System.out.println("查找信用积分变动信息成功！");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return creditRecordList;
    }


    /*
     * @Description: 查询用户所有的信用分数记录
     * @Param userNo
     * @Return: java.util.List<bean.CreditRecord>
     */
    public List<CreditRecord> getAllCreditRecord(String u_no) {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        List<CreditRecord> creditRecordList = new ArrayList<>();

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "select * from credit_record";
            m_pStmt = m_con.prepareStatement(m_sql);
            m_rs = m_pStmt.executeQuery();

            //返回所有的CreditRecords
            while (m_rs.next())
            {
                CreditRecord creditRecord = new CreditRecord();
                creditRecord.setM_CrDate(m_rs.getString("cr_date"));
                creditRecord.setM_CrChangeScore(m_rs.getInt("cr_change_score"));
                creditRecord.setM_CrChangeReason(m_rs.getString("cr_change_reason"));
                creditRecordList.add(creditRecord);
            }
            System.out.println("查找所有的信用积分变动信息成功！");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return creditRecordList;
    }
}
