package bean;

public class Reservation {
    private int m_Rno;
    private int m_Dno;
    private String m_Uno;
    private String m_RreservationDate;
    private String m_RborrowDate;
    private String m_RreturnDate;
    private String m_RfeedBack;
    private int m_Rstate;

    //附加属性
    private String m_Uname;
    private String m_Utype;
    private int m_UcreditGrade;

    public Reservation(int m_Rno, int m_Dno, String m_Uno, String m_RreservationDate, String m_RborrowDate, String m_RreturnDate, String m_RfeedBack, int m_Rstate, String m_Uname, String m_Utype, int m_UcreditGrade) {
        this.m_Rno = m_Rno;
        this.m_Dno = m_Dno;
        this.m_Uno = m_Uno;
        this.m_RreservationDate = m_RreservationDate;
        this.m_RborrowDate = m_RborrowDate;
        this.m_RreturnDate = m_RreturnDate;
        this.m_RfeedBack = m_RfeedBack;
        this.m_Rstate = m_Rstate;
        this.m_Uname = m_Uname;
        this.m_Utype = m_Utype;
        this.m_UcreditGrade = m_UcreditGrade;
    }

    public Reservation() {
    }

    public int getM_Rno() {
        return m_Rno;
    }

    public void setM_Rno(int m_Rno) {
        this.m_Rno = m_Rno;
    }

    public int getM_Dno() {
        return m_Dno;
    }

    public void setM_Dno(int m_Dno) {
        this.m_Dno = m_Dno;
    }

    public String getM_Uno() {
        return m_Uno;
    }

    public void setM_Uno(String m_Uno) {
        this.m_Uno = m_Uno;
    }

    public String getM_RreservationDate() {
        return m_RreservationDate;
    }

    public void setM_RreservationDate(String m_RreservationDate) {
        this.m_RreservationDate = m_RreservationDate;
    }

    public String getM_RborrowDate() {
        return m_RborrowDate;
    }

    public void setM_RborrowDate(String m_RborrowDate) {
        this.m_RborrowDate = m_RborrowDate;
    }

    public String getM_RreturnDate() {
        return m_RreturnDate;
    }

    public void setM_RreturnDate(String m_RreturnDate) {
        this.m_RreturnDate = m_RreturnDate;
    }

    public String getM_RfeedBack() {
        return m_RfeedBack;
    }

    public void setM_RfeedBack(String m_RfeedBack) {
        this.m_RfeedBack = m_RfeedBack;
    }

    public int getM_Rstate() {
        return m_Rstate;
    }

    public void setM_Rstate(int m_Rstate) {
        this.m_Rstate = m_Rstate;
    }

    public String getM_Uname() {
        return m_Uname;
    }

    public void setM_Uname(String m_Uname) {
        this.m_Uname = m_Uname;
    }

    public String getM_Utype() {
        return m_Utype;
    }

    public void setM_Utype(String m_Utype) {
        this.m_Utype = m_Utype;
    }

    public int getM_UcreditGrade() {
        return m_UcreditGrade;
    }

    public void setM_UcreditGrade(int m_UcreditGrade) {
        this.m_UcreditGrade = m_UcreditGrade;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "r_no=" + m_Rno +
                ", d_no=" + m_Dno +
                ", u_no='" + m_Uno + '\'' +
                ", r_reservation_date='" + m_RreservationDate + '\'' +
                ", r_borrow_date='" + m_RborrowDate + '\'' +
                ", r_return_date='" + m_RreturnDate + '\'' +
                ", r_feedback='" + m_RfeedBack + '\'' +
                ", r_state=" + m_Rstate +
                ", u_name='" + m_Uname + '\'' +
                ", u_type='" + m_Utype + '\'' +
                ", u_credit_grade=" + m_UcreditGrade +
                '}';
    }
}

