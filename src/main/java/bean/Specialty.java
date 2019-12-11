package bean;

public class Specialty
{

    private int s_no;
    private String s_name;
    private int am_no;

    //附加学院属性
    private String am_name;

    public Specialty(int s_no, String s_name, int am_no, String am_name)
    {
        this.s_no = s_no;
        this.s_name = s_name;
        this.am_no = am_no;
        this.am_name = am_name;
    }

    public Specialty()
    {
    }

    public int getS_no()
    {
        return s_no;
    }

    public void setS_no(int s_no)
    {
        this.s_no = s_no;
    }

    public String getS_name()
    {
        return s_name;
    }

    public void setS_name(String s_name)
    {
        this.s_name = s_name;
    }

    public int getAm_no()
    {
        return am_no;
    }

    public void setAm_no(int am_no)
    {
        this.am_no = am_no;
    }

    public String getAm_name()
    {
        return am_name;
    }

    public void setAm_name(String am_name)
    {
        this.am_name = am_name;
    }

    @Override
    public String toString()
    {
        return "Specialty{" +
                "s_no=" + s_no +
                ", s_name='" + s_name + '\'' +
                ", am_no=" + am_no +
                ", am_name='" + am_name + '\'' +
                '}';
    }
}
