package dao.impl;

import bean.*;
import dao.*;
import utils.JDBCUtils;
import utils.MessageUtils;

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
                    "ORDER BY cr_no DESC " +
                    "LIMIT ? ,?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, u_no);
            pStmt.setInt(2, (page - 1) * count);
            pStmt.setInt(3, count);
            rs = pStmt.executeQuery();

            //返回CreditRecords
            while (rs.next())
            {
                CreditRecord creditRecord = new CreditRecord(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getInt(6));
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
                CreditRecord creditRecord = new CreditRecord(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getInt(6));
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
     * @Description: 添加信用积分变动
     * @Param u_no, days
     * @Return: int
     */
    public int addRecord(int b_no, int days)
    {
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
            if(days == 1)
            {
                breakRuleNo = 2;
            }
            else if(days == 8)
            {
                breakRuleNo = 3;
            }
            else if((days-1)%30 == 0)
            {
                breakRuleNo = 4;
            }

            //提示
            if(breakRuleNo == 0)
            {
                System.out.print("没有触发扣分条件");
            }
            else
            {
                System.out.print("触发了第"+breakRuleNo+"条规则");
            }

            CreditRuleDao creditRuleDao = new CreditRuleDaoImpl();
            UserDao userDao = new UserDaoImpl();
            MessageDao messageDao = new MessageDaoImpl();
            BorrowDao borrowDao = new BorrowDaoImpl();
            CreditRule creditRule = creditRuleDao.getCreditRule(breakRuleNo);
            Borrow borrow = borrowDao.getBorrowByNo(b_no);
            User user = userDao.getUserByNo(borrow.getU_no());

            pStmt.setString(1,borrow.getU_no());
            pStmt.setInt(2,creditRule.getCr_score());
            pStmt.setString(3,creditRule.getCr_content());
            pStmt.setInt(4,creditRule.getCr_score()+user.getU_creditGrade());

            pStmt.executeUpdate();
            if(days == 0)
            {
                //发短信
                MessageUtils.sendRemindMessage(borrow);
                System.out.println("已经向手机号为"+user.getU_phone()+"的用户发送短信提醒");
            }
            else if(days == 1 || days == 8 || (days-1)%30 == 0)
            {
                System.out.println("手机号为"+user.getU_phone()+"的用户因为未归还达到"+days+"天，信用积分"+creditRule.getCr_score()+"，已经向其发送系统信息提醒。");
            }
            else
            {
                //System.out.println("手机号为"+user.getU_phone()+"的用户因为未归还达到"+days+"天，已经被惩罚");
                //因为只有触发了才会惩罚，所以不需要提醒，也不会有更改信用分发短信提醒操作
                return 0;
            }

            //立即更改当前学生的信用分
            int flag = userDao.updateCreditGrade(borrow.getU_no(),creditRule.getCr_score());
            if(flag <= 0)
            {
                System.out.println("更改当前学生的信用分失败");
                return -1;
            }
            //给用户发消息告诉他他的信用分被扣了
            user = userDao.getUserByNo(borrow.getU_no());
            String str = user.getU_name();
            char name[] = str.toCharArray();
            String message = "尊敬的"+name[0]+"同学，由于您“"+creditRule.getCr_content()+"”，导致您当前的信用分发生了变化\r\n" +
                    "您当前的信用分为"+user.getU_creditGrade()+"\r\n"+"具体的细节请您通过信用分的变动查看！";
            flag = messageDao.sendMessage(borrow.getU_no(),message);
            if(flag <= 0)
            {
                System.out.println("发送短信失败");
                return -1;
            }
            return flag;

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
