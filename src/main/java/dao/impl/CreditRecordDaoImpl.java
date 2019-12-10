package dao.impl;

import bean.CreditRecord;
import bean.CreditRule;
import bean.Message;
import dao.CreditRecordDao;
import dao.CreditRuleDao;
import dao.MessageDao;
import dao.UserDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//信用积分变化
public class CreditRecordDaoImpl implements CreditRecordDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    public int initCredit(String u_no, int score)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        int flag = 0;
        try
        {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO credit_record(u_no, cr_change_score, cr_change_reason, cr_date, cr_after_grade) " +
                    "VALUES (?,?,?, CURRENT_DATE ,?)";
            pStmt = con.prepareStatement(sql);

            pStmt.setString(1, u_no);
            pStmt.setInt(2, 0);
            pStmt.setString(3, "湖大微设备欢迎你。期待能为你带来愉快的体验");
            pStmt.setInt(4, score);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 信用积分变动
     * @Param userNo  reason  score:原来分数  changeScore:浮动分数
     * @Return: int
     */
    public int updateCredit(String u_no, String reason, int score, int changeScore)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int flag = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO credit_record(u_no, cr_change_score, cr_change_reason, cr_date, cr_after_grade) " +
                    "VALUES (?,?,?, CURRENT_DATE ,?)";
            PreparedStatement pStmt = con.prepareStatement(sql);
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
            JDBCUtils.closeAll(null, pStmt, con);
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
        con = null;
        pStmt = null;
        rs = null;
        List<CreditRecord> creditRecordList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM credit_record WHERE u_no = ?  " +
                    "ORDER BY cr_no desc" +
                    "LIMIT ? ,?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, u_no);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);
            rs = pStmt.executeQuery();

            //返回CreditRecords
            while (rs.next())
            {
                CreditRecord creditRecord = new CreditRecord(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getDate(5),rs.getInt(6));
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
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return creditRecordList;
    }


    /*
     * @Description: 查询用户所有的信用分数记录
     * @Param userNo
     * @Return: java.util.List<bean.CreditRecord>
     */
    public List<CreditRecord> getAllCreditRecord(String u_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<CreditRecord> creditRecordList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "select * from credit_record";
            pStmt = con.prepareStatement(sql);
            rs = pStmt.executeQuery();

            //返回所有的CreditRecords
            while (rs.next())
            {
                CreditRecord creditRecord = new CreditRecord(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getDate(5),rs.getInt(6));
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
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return creditRecordList;
    }

    /*
     * @Description: 添加信用积分变动，逾期一个星期扣1分，超过一个星期小于等于一个月再扣2分，大于一个月再扣2分，之后每个月扣5分
     * @Param u_no
     * @Return: java.util.List<bean.CreditRecord>
     */
    @Override
    public int addRecord(String u_no,int days) {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "insert into credit_record(u_no, cr_change_score, cr_change_reason, cr_date, cr_after_grade) values(?,?,?,CURRENT_DATE ,?)";
            pStmt = con.prepareStatement(sql);
            int breakRuleNo = 0;
            if(days == 0)
            {
                breakRuleNo = 1;
            }
            else if(days <= 7)
            {
                breakRuleNo = 2;
            }
            else if(days > 7 && days <= 30)
            {
                breakRuleNo = 3;
            }
            else
            {
                breakRuleNo = 4;
            }

            CreditRuleDao creditRuleDao = new CreditRuleDaoImpl();
            UserDao userDao = new UserDaoImpl();
            MessageDao messageDao = new MessageDaoImpl();

            pStmt.setString(1,u_no);
            pStmt.setInt(2,creditRuleDao.getCreditRule(breakRuleNo).getCr_score());
            pStmt.setString(3,creditRuleDao.getCreditRule(breakRuleNo).getCr_content());
            pStmt.setInt(4,creditRuleDao.getCreditRule(breakRuleNo).getCr_score()+userDao.getCreditScore(u_no));

            pStmt.executeUpdate();
            //立即更改当前学生的信用分
            int flag = userDao.updateCreditGrade(u_no,creditRuleDao.getCreditRule(breakRuleNo).getCr_score()+userDao.getCreditScore(u_no));
            if(flag == 0)
            {
                System.out.println("更改当前学生的信用分失败");
            }
            //给用户发消息告诉他他的信用分被扣了
            String str = userDao.getUserByNo(u_no).getU_name();
            char name[] = str.toCharArray();
            String message = "尊敬的"+name[0]+"同学，由于您"+creditRuleDao.getCreditRule(breakRuleNo)+"，所以导致您当前的信用分发生了变化\r\n" +
                    "您当前的信用分为"+userDao.getCreditScore(u_no)+"\r\n"+"具体的细节请您查看历史信用分变动查看具体情况！";
            flag = messageDao.sendMessage(u_no,message);
            if(flag == 0 || flag == -1)
            {
                System.out.println("发送短信失败");
            }
            else
            {
                return flag;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return 0;
    }
}
