package bean;


public class Track
{

    private int t_no;
    private int t_state;
    private String u_no;
    private String d_no;

    public Track(int t_no, int t_state, String u_no, String d_no)
    {
        this.t_no = t_no;
        this.t_state = t_state;
        this.u_no = u_no;
        this.d_no = d_no;
    }

    public int getT_no()
    {
        return t_no;
    }

    public void setT_no(int t_no)
    {
        this.t_no = t_no;
    }

    public int getT_state()
    {
        return t_state;
    }

    public void setT_state(int t_state)
    {
        this.t_state = t_state;
    }

    public String getU_no()
    {
        return u_no;
    }

    public void setU_no(String u_no)
    {
        this.u_no = u_no;
    }

    public String getD_no()
    {
        return d_no;
    }

    public void setD_no(String d_no)
    {
        this.d_no = d_no;
    }

    @Override
    public String toString()
    {
        return "Track{" +
                "t_no=" + t_no +
                ", t_state=" + t_state +
                ", u_no='" + u_no + '\'' +
                ", d_no='" + d_no + '\'' +
                '}';
    }
}
