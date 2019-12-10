package bean;

import java.util.Date;

/**
 * 信用记录
 */
public class CreditRecord
{

    //信用记录_编号
    private int cr_no;
    //用户_学工号
    private String u_no;
    //信用记录_分数浮动
    private int cr_changeScore;
    //信用记录_变化原因
    private String cr_changeReason;
    //信用记录_日期(自动填写)
    private Date cr_date;
    //信用记录_变化后分数
    private int cr_afterGrade;

    public CreditRecord(int cr_no, String u_no, int cr_changeScore, String cr_changeReason, Date cr_date, int cr_afterGrade)
    {
        this.cr_no = cr_no;
        this.u_no = u_no;
        this.cr_changeScore = cr_changeScore;
        this.cr_changeReason = cr_changeReason;
        this.cr_date = cr_date;
        this.cr_afterGrade = cr_afterGrade;
    }

    public CreditRecord()
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

    public String getU_no()
    {
        return u_no;
    }

    public void setU_no(String u_no)
    {
        this.u_no = u_no;
    }

    public int getCr_changeScore()
    {
        return cr_changeScore;
    }

    public void setCr_changeScore(int cr_changeScore)
    {
        this.cr_changeScore = cr_changeScore;
    }

    public String getCr_changeReason()
    {
        return cr_changeReason;
    }

    public void setCr_changeReason(String cr_changeReason)
    {
        this.cr_changeReason = cr_changeReason;
    }

    public Date getCr_date()
    {
        return cr_date;
    }

    public void setCr_date(Date cr_date)
    {
        this.cr_date = cr_date;
    }

    public int getCr_afterGrade()
    {
        return cr_afterGrade;
    }

    public void setCr_afterGrade(int cr_afterGrade)
    {
        this.cr_afterGrade = cr_afterGrade;
    }

    @Override
    public String toString()
    {
        return "CreditRecord{" +
                "cr_no=" + cr_no +
                ", u_no='" + u_no + '\'' +
                ", cr_change_score=" + cr_changeScore +
                ", cr_change_reason='" + cr_changeReason + '\'' +
                ", cr_date='" + cr_date + '\'' +
                ", cr_after_grade=" + cr_afterGrade +
                '}';
    }
}
