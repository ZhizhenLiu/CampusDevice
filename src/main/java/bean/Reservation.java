package bean;

public class Reservation
{
    private int r_no;
    private int d_no;
    private String u_no;
    private String r_reservationDate;
    private String r_startDate;
    private String r_returnDate;
    private String r_feedBack;
    private int r_state;

    //附加用户属性
    private String u_name;
    private String u_type;
    private int u_creditGrade;
    //附加设备属性
    private String d_name;
    private String d_saveSite;
    private String d_mainUse;

    public Reservation(int r_no, int d_no, String u_no, String r_reservationDate, String r_startDate, String r_returnDate, String r_feedBack, int r_state, String u_name, String u_type, int u_creditGrade, String d_name, String d_saveSite, String d_mainUse)
    {
        this.r_no = r_no;
        this.d_no = d_no;
        this.u_no = u_no;
        this.r_reservationDate = r_reservationDate;
        this.r_startDate = r_startDate;
        this.r_returnDate = r_returnDate;
        this.r_feedBack = r_feedBack;
        this.r_state = r_state;
        this.u_name = u_name;
        this.u_type = u_type;
        this.u_creditGrade = u_creditGrade;
        this.d_name = d_name;
        this.d_saveSite = d_saveSite;
        this.d_mainUse = d_mainUse;
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

    public int getD_no()
    {
        return d_no;
    }

    public void setD_no(int d_no)
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

    public String getD_mainUse()
    {
        return d_mainUse;
    }

    public void setD_mainUse(String d_mainUse)
    {
        this.d_mainUse = d_mainUse;
    }

    @Override
    public String toString()
    {
        return "Reservation{" +
                "m_Rno=" + r_no +
                ", m_Dno=" + d_no +
                ", m_Uno='" + u_no + '\'' +
                ", m_RreservationDate='" + r_reservationDate + '\'' +
                ", m_RstartDate='" + r_startDate + '\'' +
                ", m_RreturnDate='" + r_returnDate + '\'' +
                ", m_RfeedBack='" + r_feedBack + '\'' +
                ", m_Rstate=" + r_state +
                ", m_Uname='" + u_name + '\'' +
                ", m_Utype='" + u_type + '\'' +
                ", m_UcreditGrade=" + u_creditGrade +
                ", m_Dname='" + d_name + '\'' +
                ", m_DsaveSite='" + d_saveSite + '\'' +
                ", m_DmainUse='" + d_mainUse + '\'' +
                '}';
    }
}

