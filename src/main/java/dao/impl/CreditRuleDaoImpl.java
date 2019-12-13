package dao.impl;

import bean.CreditRule;
import dao.CreditRuleDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CreditRuleDaoImpl implements CreditRuleDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    //添加规则
    public void addCreditRule(CreditRule cr)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        try
        {
            Connection conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO credit_rule(ce_content, cr_score) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            //执行操作
            ps.setString(1, cr.getCr_content());
            ps.setInt(2, cr.getCr_score());
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
    }

    //分页查询所有的规则
    public List<CreditRule> getAllCreditRules()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<CreditRule> creditRuleList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM credit_rule ORDER BY cr_no  ";
            pStmt = con.prepareStatement(sql);

            //执行操作
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                creditRuleList.add(new CreditRule(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return creditRuleList;
    }

    //查询某一条规则
    @Override
    public CreditRule getCreditRule(int cr_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        CreditRule creditRule = new CreditRule();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "select * from credit_rule where cr_no = ?";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setInt(1, cr_no);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                creditRule = new CreditRule(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return creditRule;
    }

    //查询规则的数量
    @Override
    public int getCreditNum()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int num = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT count(cr_no) FROM credit_rule ";
            pStmt = con.prepareStatement(sql);

            rs = pStmt.executeQuery();
            if (rs.next())
            {
                num = rs.getInt(1);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return num;
    }

    //更改某一条规则
    @Override
    public void changeCreditRule(CreditRule cr)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "update credit_rule set ce_content = ?, cr_score = ? where cr_no = ?";
            pStmt = con.prepareStatement(sql);

            //执行操作
            pStmt.setString(1, cr.getCr_content());
            pStmt.setInt(2, cr.getCr_score());
            pStmt.setInt(3, cr.getCr_no());
            pStmt.executeUpdate();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
    }
}
