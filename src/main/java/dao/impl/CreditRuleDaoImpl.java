package dao.impl;

import bean.CreditRule;
import dao.CreditRuleDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CreditRuleDaoImpl implements CreditRuleDao {
    private Connection m_con;
    private PreparedStatement m_pStmt;
    private ResultSet m_rs;
    private String m_sql;

    public void addCreditRule(CreditRule cr)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        try
        {
            Connection conn = JDBCUtils.getM_connection();
            String sql = "insert into credit_rule(cr_no, ce_content, cr_score) values(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            //执行操作
            ps.setInt(1,cr.getM_CrNo());
            ps.setString(2,cr.getM_CrContent());
            ps.setInt(3,cr.getM_CrScore());
            ps.executeUpdate();

            conn.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<CreditRule> getAllCreditRules()
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        List<CreditRule> creditRuleList = new ArrayList<>();

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT * FROM credit_rule ";
            m_pStmt = m_con.prepareStatement(m_sql);

            //执行操作
            m_rs = m_pStmt.executeQuery();

            while(m_rs.next())
            {
                creditRuleList.add(new CreditRule(m_rs.getInt(1), m_rs.getString(2), m_rs.getInt(3)));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return creditRuleList;
    }

    @Override
    public CreditRule getCreditRule(int cr_no)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        CreditRule creditRule = new CreditRule();

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "select * from credit_rule where cr_no = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //执行操作
            m_pStmt.setInt(1,cr_no);
            ResultSet rs = m_pStmt.executeQuery();
            if(rs.next())
            {
                creditRule = new CreditRule(rs.getInt(1),rs.getString(2),rs.getInt(3));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return creditRule;
    }

    @Override
    public int getCreditNum()
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;
        int num = 0;

        try{
            m_con = JDBCUtils.getM_connection();
            m_sql = "SELECT count(cr_no) FROM credit_rule ";
            m_pStmt = m_con.prepareStatement(m_sql);

            m_rs = m_pStmt.executeQuery();
            if(m_rs.next())
            {
                num = m_rs.getInt(1);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(m_rs, m_pStmt, m_con);
        }
        return num;
    }

    @Override
    public void changeCreditRule(CreditRule cr)
    {
        //初始化
        m_con = null;
        m_pStmt = null;
        m_rs = null;

        try
        {
            m_con = JDBCUtils.getM_connection();
            m_sql = "update credit_rule set ce_content = ?, cr_score = ? where cr_no = ?";
            m_pStmt = m_con.prepareStatement(m_sql);

            //执行操作
            m_pStmt.setString(1,cr.getM_CrContent());
            m_pStmt.setInt(2,cr.getM_CrScore());
            m_pStmt.setInt(3,cr.getM_CrNo());
            m_pStmt.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, m_pStmt, m_con);
        }
    }
}
