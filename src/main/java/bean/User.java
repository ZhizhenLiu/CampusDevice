package bean;

/*
 * @Description: 用户信息
 */
public class User
{
    private String u_no;
    private String u_name;
    private String u_wechatID;
    private String u_email;
    private String u_phone;
    private int u_creditGrade;
    private String u_type;
    private String u_mentorName;
    private String u_mentorPhone;
    private int s_no;

    //专业学院附加信息
    private String s_name;
    private int am_no;
    private String am_name;

    public User(String u_no, String u_name, String u_wechatID, String u_email, String u_phone, int u_creditGrade, String u_type, String u_mentorName, String u_mentorPhone, int s_no, String s_name, int am_no, String am_name)
    {
        this.u_no = u_no;
        this.u_name = u_name;
        this.u_wechatID = u_wechatID;
        this.u_email = u_email;
        this.u_phone = u_phone;
        this.u_creditGrade = u_creditGrade;
        this.u_type = u_type;
        this.u_mentorName = u_mentorName;
        this.u_mentorPhone = u_mentorPhone;
        this.s_no = s_no;
        this.s_name = s_name;
        this.am_no = am_no;
        this.am_name = am_name;
    }

    public User()
    {
    }

    public String getU_no()
    {
        return u_no;
    }

    public void setU_no(String u_no)
    {
        this.u_no = u_no;
    }

    public String getU_name()
    {
        return u_name;
    }

    public void setU_name(String u_name)
    {
        this.u_name = u_name;
    }

    public String getU_wechatID()
    {
        return u_wechatID;
    }

    public void setU_wechatID(String u_wechatID)
    {
        this.u_wechatID = u_wechatID;
    }

    public String getU_email()
    {
        return u_email;
    }

    public void setU_email(String u_email)
    {
        this.u_email = u_email;
    }

    public String getU_phone()
    {
        return u_phone;
    }

    public void setU_phone(String u_phone)
    {
        this.u_phone = u_phone;
    }

    public int getU_creditGrade()
    {
        return u_creditGrade;
    }

    public void setU_creditGrade(int u_creditGrade)
    {
        this.u_creditGrade = u_creditGrade;
    }

    public String getU_type()
    {
        return u_type;
    }

    public void setU_type(String u_type)
    {
        this.u_type = u_type;
    }

    public String getU_mentorName()
    {
        return u_mentorName;
    }

    public void setU_mentorName(String u_mentorName)
    {
        this.u_mentorName = u_mentorName;
    }

    public String getU_mentorPhone()
    {
        return u_mentorPhone;
    }

    public void setU_mentorPhone(String u_mentorPhone)
    {
        this.u_mentorPhone = u_mentorPhone;
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
        return "User{" +
                "u_no='" + u_no + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_wechatID='" + u_wechatID + '\'' +
                ", u_email='" + u_email + '\'' +
                ", u_phone='" + u_phone + '\'' +
                ", u_creditGrade=" + u_creditGrade +
                ", u_type='" + u_type + '\'' +
                ", u_mentorName='" + u_mentorName + '\'' +
                ", u_mentorPhone='" + u_mentorPhone + '\'' +
                ", s_no=" + s_no +
                ", s_name='" + s_name + '\'' +
                ", am_no=" + am_no +
                ", am_name='" + am_name + '\'' +
                '}';
    }
}

