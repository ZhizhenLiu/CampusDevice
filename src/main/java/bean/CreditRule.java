package bean;

/*
 * 信用规则表
 */
public class CreditRule
{

    //信用规则_编号
    private int cr_no;
    //信用规则_内容
    private String cr_content;
    //信用规则_加减分
    private int cr_score;


    public CreditRule(int cr_no, String cr_content, int cr_score)
    {
        this.cr_no = cr_no;
        this.cr_content = cr_content;
        this.cr_score = cr_score;
    }

    @Override
    public String toString()
    {
        return "CreditRule{" +
                "cr_no=" + cr_no +
                ", ce_content='" + cr_content + '\'' +
                ", cr_score=" + cr_score +
                '}';
    }

    public CreditRule()
    {
    }

    public int getCr_no()
    {
        return cr_no;
    }

    public void setCr_no(int cr_no)
    {
        this.cr_no = cr_no;
    }

    public String getCr_content()
    {
        return cr_content;
    }

    public void setCr_content(String cr_content)
    {
        this.cr_content = cr_content;
    }

    public int getCr_score()
    {
        return cr_score;
    }

    public void setCr_score(int cr_score)
    {
        this.cr_score = cr_score;
    }
}
