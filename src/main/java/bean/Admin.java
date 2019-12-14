package bean;

/*
 * @Description: 管理员信息
 */
public class Admin
{
    private String a_no;
    private String a_name;
    private String a_wechatID;
    private String a_type;
    private String a_phone;
    private String a_email;


    public Admin(String a_no, String a_name, String a_wechatID, String a_type, String a_phone, String a_email)
    {
        this.a_no = a_no;
        this.a_name = a_name;
        this.a_wechatID = a_wechatID;
        this.a_type = a_type;
        this.a_phone = a_phone;
        this.a_email = a_email;
    }

    public Admin()
    {
    }

    public Admin(String a_no)
    {
        this.a_no = a_no;
    }

    public String getA_no()
    {
        return a_no;
    }

    public void setA_no(String a_no)
    {
        this.a_no = a_no;
    }

    public String getA_name()
    {
        return a_name;
    }

    public void setA_name(String a_name)
    {
        this.a_name = a_name;
    }

    public String getA_wechatID()
    {
        return a_wechatID;
    }

    public void setA_wechatID(String a_wechatID)
    {
        this.a_wechatID = a_wechatID;
    }

    public String getA_type()
    {
        return a_type;
    }

    public void setA_type(String a_type)
    {
        this.a_type = a_type;
    }

    public String getA_phone()
    {
        return a_phone;
    }

    public void setA_phone(String a_phone)
    {
        this.a_phone = a_phone;
    }

    public String getA_email()
    {
        return a_email;
    }

    public void setA_email(String a_email)
    {
        this.a_email = a_email;
    }

    @Override
    public String toString()
    {
        return "Admin{" +
                "a_no='" + a_no + '\'' +
                ", a_name='" + a_name + '\'' +
                ", a_wechatID='" + a_wechatID + '\'' +
                ", a_type='" + a_type + '\'' +
                ", a_phone='" + a_phone + '\'' +
                ", a_email='" + a_email + '\'' +
                '}';
    }
}
