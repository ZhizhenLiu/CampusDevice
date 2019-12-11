package bean;


public class Comment
{

    private int c_no;
    private String d_no;
    private String u_no;
    private String c_date;
    private String c_content;

    public Comment(int c_no, String d_no, String u_no, String c_date, String c_content)
    {
        this.c_no = c_no;
        this.d_no = d_no;
        this.u_no = u_no;
        this.c_date = c_date;
        this.c_content = c_content;
    }

    public Comment()
    {
    }

    public int getC_no()
    {
        return c_no;
    }

    public void setC_no(int c_no)
    {
        this.c_no = c_no;
    }

    public String getD_no()
    {
        return d_no;
    }

    public void setD_no(String d_no)
    {
        this.d_no = d_no;
    }

    public String getU_no()
    {
        return u_no;
    }

    public void setU_no(String u_no)
    {
        this.u_no = u_no;
    }

    public String getC_date()
    {
        return c_date;
    }

    public void setC_date(String c_date)
    {
        this.c_date = c_date;
    }

    public String getC_content()
    {
        return c_content;
    }

    public void setC_content(String c_content)
    {
        this.c_content = c_content;
    }

    @Override
    public String toString()
    {
        return "Comment{" +
                "c_no=" + c_no +
                ", d_no='" + d_no + '\'' +
                ", u_no='" + u_no + '\'' +
                ", c_date='" + c_date + '\'' +
                ", c_content='" + c_content + '\'' +
                '}';
    }
}
