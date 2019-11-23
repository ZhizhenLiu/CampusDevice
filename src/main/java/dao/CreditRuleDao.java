package dao;

import bean.CreditRule;

import java.util.List;

public interface CreditRuleDao
{

    //添加规则
    void addCreditRule(CreditRule cr);

    /*
     * @Description: 显示所有的规则
     * @Param
     * @Return: java.util.List<bean.CreditRule>
     */
    List<CreditRule> getAllCreditRules();

    //查询某一条规则
    CreditRule getCreditRule(int cr_no);

    //查询规则的数量
    int getCreditNum();

    //更改某一条规则
    void changeCreditRule(CreditRule cr);
}
