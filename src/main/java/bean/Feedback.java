package bean;

import java.util.Date;

/**
 * 反馈表
 */
public class Feedback
{

    //反馈_编号
    private int fb_no;
    //用户_学工号
    private String u_no;
    //反馈_内容
    private String fb_content;
    //反馈_时间
    private Date fb_date;

    public Feedback(int fb_no, String u_no, String fb_content, Date fb_date)
    {
        this.fb_no = fb_no;
        this.u_no = u_no;
        this.fb_content = fb_content;
        this.fb_date = fb_date;
    }

    public Feedback()
    {
    }

    public int getFb_no()
    {
        return fb_no;
    }

    public void setFb_no(int fb_no)
    {
        this.fb_no = fb_no;
    }

    public String getU_no()
    {
        return u_no;
    }

    public void setU_no(String u_no)
    {
        this.u_no = u_no;
    }

    public String getFb_content()
    {
        return fb_content;
    }

    public void setFb_content(String fb_content)
    {
        this.fb_content = fb_content;
    }

    public Date getFb_date()
    {
        return fb_date;
    }

    public void setFb_date(Date fb_date)
    {
        this.fb_date = fb_date;
    }

    @Override
    public String toString()
    {
        return "Feedback{" +
                "f_no=" + fb_no +
                ", u_no='" + u_no + '\'' +
                ", f_content='" + fb_content + '\'' +
                ", f_date=" + fb_date +
                '}';
    }
}
