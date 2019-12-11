package bean;

/*
 * @Description: 设备信息
 */
public class Device
{
    private String d_no;
    private String d_name;
    private String d_model;
    private String d_saveSite;
    private String a_no;
    private String d_factoryNo;
    private String d_state;
    private String d_storeDate;
    private int d_borrowedTimes;
    private String d_photo;

    //扩展属性
    private String a_name;
    private String a_phone;

    public Device(String d_no, String d_name, String d_model, String d_saveSite, String a_no, String d_factoryNo, String d_state, String d_storeDate, int d_borrowedTimes, String d_photo, String a_name, String a_phone)
    {
        this.d_no = d_no;
        this.d_name = d_name;
        this.d_model = d_model;
        this.d_saveSite = d_saveSite;
        this.a_no = a_no;
        this.d_factoryNo = d_factoryNo;
        this.d_state = d_state;
        this.d_storeDate = d_storeDate;
        this.d_borrowedTimes = d_borrowedTimes;
        this.d_photo = d_photo;
        this.a_name = a_name;
        this.a_phone = a_phone;
    }

    public Device()
    {
    }

    public String getD_no()
    {
        return d_no;
    }

    public void setD_no(String d_no)
    {
        this.d_no = d_no;
    }

    public String getD_name()
    {
        return d_name;
    }

    public void setD_name(String d_name)
    {
        this.d_name = d_name;
    }

    public String getD_model()
    {
        return d_model;
    }

    public void setD_model(String d_model)
    {
        this.d_model = d_model;
    }

    public String getD_saveSite()
    {
        return d_saveSite;
    }

    public void setD_saveSite(String d_saveSite)
    {
        this.d_saveSite = d_saveSite;
    }

    public String getA_no()
    {
        return a_no;
    }

    public void setA_no(String a_no)
    {
        this.a_no = a_no;
    }

    public String getD_factoryNo()
    {
        return d_factoryNo;
    }

    public void setD_factoryNo(String d_factoryNo)
    {
        this.d_factoryNo = d_factoryNo;
    }

    public String getD_state()
    {
        return d_state;
    }

    public void setD_state(String d_state)
    {
        this.d_state = d_state;
    }

    public String getD_storeDate()
    {
        return d_storeDate;
    }

    public void setD_storeDate(String d_storeDate)
    {
        this.d_storeDate = d_storeDate;
    }

    public int getD_borrowedTimes()
    {
        return d_borrowedTimes;
    }

    public void setD_borrowedTimes(int d_borrowedTimes)
    {
        this.d_borrowedTimes = d_borrowedTimes;
    }

    public String getD_photo()
    {
        return d_photo;
    }

    public void setD_photo(String d_photo)
    {
        this.d_photo = d_photo;
    }

    public String getA_name()
    {
        return a_name;
    }

    public void setA_name(String a_name)
    {
        this.a_name = a_name;
    }

    public String getA_phone()
    {
        return a_phone;
    }

    public void setA_phone(String a_phone)
    {
        this.a_phone = a_phone;
    }

    @Override
    public String toString()
    {
        return "Device{" +
                "d_no='" + d_no + '\'' +
                ", d_name='" + d_name + '\'' +
                ", d_model='" + d_model + '\'' +
                ", d_saveSite='" + d_saveSite + '\'' +
                ", a_no='" + a_no + '\'' +
                ", d_factoryNo='" + d_factoryNo + '\'' +
                ", d_state='" + d_state + '\'' +
                ", d_storeDate='" + d_storeDate + '\'' +
                ", d_borrowedTimes=" + d_borrowedTimes +
                ", d_photo='" + d_photo + '\'' +
                ", a_name='" + a_name + '\'' +
                ", a_phone='" + a_phone + '\'' +
                '}';
    }
}
