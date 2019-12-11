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
    private String u_majorClass;
    private int s_no;

    public User(String u_no, String u_name, String u_wechatID, String u_email, String u_phone, int u_creditGrade, String u_type, String u_mentorName, String u_mentorPhone, String u_majorClass, int s_no)
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
        this.u_majorClass = u_majorClass;
        this.s_no = s_no;
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

    public String getU_majorClass()
    {
        return u_majorClass;
    }

    public void setU_majorClass(String u_majorClass)
    {
        this.u_majorClass = u_majorClass;
    }

    public int getS_no()
    {
        return s_no;
    }

    public void setS_no(int s_no)
    {
        this.s_no = s_no;
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
                ", u_majorClass='" + u_majorClass + '\'' +
                ", s_no=" + s_no +
                '}';
    }
}

