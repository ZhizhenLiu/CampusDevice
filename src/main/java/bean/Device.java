package bean;

/*
 * @Description: 设备信息
 */
public class Device
{
    private int d_no;
    private String a_no;
    private String d_state;
    private int d_borrowedTimes;
    private String d_name;
    private String d_importantParam;
    private String d_mainUse;
    private String d_saveSite;

    //扩展属性
    private int r_sum; //预约人数
    private String a_name;
    private String a_phone;

    public Device(int d_no, String a_no, String d_state, int d_borrowedTimes, String d_name, String d_importantParam, String d_mainUse, String d_saveSite)
    {
        this.d_no = d_no;
        this.a_no = a_no;
        this.d_state = d_state;
        this.d_borrowedTimes = d_borrowedTimes;
        this.d_name = d_name;
        this.d_importantParam = d_importantParam;
        this.d_mainUse = d_mainUse;
        this.d_saveSite = d_saveSite;
    }

    public Device()
    {
    }

    public int getD_no()
    {
        return d_no;
    }

    public void setD_no(int d_no)
    {
        this.d_no = d_no;
    }

    public String getA_no()
    {
        return a_no;
    }

    public void setA_no(String a_no)
    {
        this.a_no = a_no;
    }

    public String getD_state()
    {
        return d_state;
    }

    public void setD_state(String d_state)
    {
        this.d_state = d_state;
    }

    public int getD_borrowedTimes()
    {
        return d_borrowedTimes;
    }

    public void setD_borrowedTimes(int d_borrowedTimes)
    {
        this.d_borrowedTimes = d_borrowedTimes;
    }

    public String getD_name()
    {
        return d_name;
    }

    public void setD_name(String d_name)
    {
        this.d_name = d_name;
    }

    public String getD_importantParam()
    {
        return d_importantParam;
    }

    public void setD_importantParam(String d_importantParam)
    {
        this.d_importantParam = d_importantParam;
    }

    public String getD_mainUse()
    {
        return d_mainUse;
    }

    public void setD_mainUse(String d_mainUse)
    {
        this.d_mainUse = d_mainUse;
    }

    public String getD_saveSite()
    {
        return d_saveSite;
    }

    public void setD_saveSite(String d_saveSite)
    {
        this.d_saveSite = d_saveSite;
    }

    public int getR_sum()
    {
        return r_sum;
    }

    public void setR_sum(int r_sum)
    {
        this.r_sum = r_sum;
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
                "d_no=" + d_no +
                ", a_no='" + a_no + '\'' +
                ", d_state='" + d_state + '\'' +
                ", d_borrowed_times=" + d_borrowedTimes +
                ", d_name='" + d_name + '\'' +
                ", d_important_param='" + d_importantParam + '\'' +
                ", d_main_use='" + d_mainUse + '\'' +
                ", d_save_site='" + d_saveSite + '\'' +
                ", r_sum=" + r_sum +
                ", a_name='" + a_name + '\'' +
                ", a_phone='" + a_phone + '\'' +
                '}';
    }
}
