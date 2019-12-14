package bean;

public class Reservation
{
    private int r_no;
    private String d_no;
    private String u_no;
    private String r_reservationDate;
    private String r_startDate;
    private String r_returnDate;
    private String r_feedBack;
    private int r_state;
    private int r_sum; //预约人数

    //附加用户属性
    private String u_name;
    private String u_type;
    private int u_creditGrade;

    //附加设备属性
    private String d_name;
    private String d_saveSite;
    private String d_photo;

    public Reservation(int r_no, String d_no, String u_no, String r_reservationDate, String r_startDate, String r_returnDate, String r_feedBack, int r_state, int r_sum, String u_name, String u_type, int u_creditGrade, String d_name, String d_saveSite, String d_photo)
    {
        this.r_no = r_no;
        this.d_no = d_no;
        this.u_no = u_no;
        this.r_reservationDate = r_reservationDate;
        this.r_startDate = r_startDate;
        this.r_returnDate = r_returnDate;
        this.r_feedBack = r_feedBack;
        this.r_state = r_state;
        this.r_sum = r_sum;
        this.u_name = u_name;
        this.u_type = u_type;
        this.u_creditGrade = u_creditGrade;
        this.d_name = d_name;
        this.d_saveSite = d_saveSite;
        this.d_photo = d_photo;
    }

    public Reservation()
    {
    }

    public int getR_no()
    {
        return r_no;
    }

    public void setR_no(int r_no)
    {
        this.r_no = r_no;
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

    public String getR_reservationDate()
    {
        return r_reservationDate;
    }

    public void setR_reservationDate(String r_reservationDate)
    {
        this.r_reservationDate = r_reservationDate;
    }

    public String getR_startDate()
    {
        return r_startDate;
    }

    public void setR_startDate(String r_startDate)
    {
        this.r_startDate = r_startDate;
    }

    public String getR_returnDate()
    {
        return r_returnDate;
    }

    public void setR_returnDate(String r_returnDate)
    {
        this.r_returnDate = r_returnDate;
    }

    public String getR_feedBack()
    {
        return r_feedBack;
    }

    public void setR_feedBack(String r_feedBack)
    {
        this.r_feedBack = r_feedBack;
    }

    public int getR_state()
    {
        return r_state;
    }

    public void setR_state(int r_state)
    {
        this.r_state = r_state;
    }

    public int getR_sum()
    {
        return r_sum;
    }

    public void setR_sum(int r_sum)
    {
        this.r_sum = r_sum;
    }

    public String getU_name()
    {
        return u_name;
    }

    public void setU_name(String u_name)
    {
        this.u_name = u_name;
    }

    public String getU_type()
    {
        return u_type;
    }

    public void setU_type(String u_type)
    {
        this.u_type = u_type;
    }

    public int getU_creditGrade()
    {
        return u_creditGrade;
    }

    public void setU_creditGrade(int u_creditGrade)
    {
        this.u_creditGrade = u_creditGrade;
    }

    public String getD_name()
    {
        return d_name;
    }

    public void setD_name(String d_name)
    {
        this.d_name = d_name;
    }

    public String getD_saveSite()
    {
        return d_saveSite;
    }

    public void setD_saveSite(String d_saveSite)
    {
        this.d_saveSite = d_saveSite;
    }

    public String getD_photo()
    {
        return d_photo;
    }

    public void setD_photo(String d_photo)
    {
        this.d_photo = d_photo;
    }

    @Override
    public String toString()
    {
        return "Reservation{" +
                "r_no=" + r_no +
                ", d_no='" + d_no + '\'' +
                ", u_no='" + u_no + '\'' +
                ", r_reservationDate='" + r_reservationDate + '\'' +
                ", r_startDate='" + r_startDate + '\'' +
                ", r_returnDate='" + r_returnDate + '\'' +
                ", r_feedBack='" + r_feedBack + '\'' +
                ", r_state=" + r_state +
                ", r_sum=" + r_sum +
                ", u_name='" + u_name + '\'' +
                ", u_type='" + u_type + '\'' +
                ", u_creditGrade=" + u_creditGrade +
                ", d_name='" + d_name + '\'' +
                ", d_saveSite='" + d_saveSite + '\'' +
                ", d_photo='" + d_photo + '\'' +
                '}';
    }
}

