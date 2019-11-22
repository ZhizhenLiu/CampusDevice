package bean;

public class Reservation {
    private int m_Rno;
    private int m_Dno;
    private String m_Uno;
    private String m_RreservationDate;
    private String m_RstartDate;
    private String m_RreturnDate;
    private String m_RfeedBack;
    private int m_Rstate;

    //附加用户属性
    private String m_Uname;
    private String m_Utype;
    private int m_UcreditGrade;
    //附加设备属性
    private String m_Dname;
    private String m_DsaveSite;
    private String m_DmainUse;

    public Reservation(int m_Rno, int m_Dno, String m_Uno, String m_RreservationDate, String m_RstartDate, String m_RreturnDate, String m_RfeedBack, int m_Rstate, String m_Uname, String m_Utype, int m_UcreditGrade, String m_Dname, String m_DsaveSite, String m_DmainUse) {
        this.m_Rno = m_Rno;
        this.m_Dno = m_Dno;
        this.m_Uno = m_Uno;
        this.m_RreservationDate = m_RreservationDate;
        this.m_RstartDate = m_RstartDate;
        this.m_RreturnDate = m_RreturnDate;
        this.m_RfeedBack = m_RfeedBack;
        this.m_Rstate = m_Rstate;
        this.m_Uname = m_Uname;
        this.m_Utype = m_Utype;
        this.m_UcreditGrade = m_UcreditGrade;
        this.m_Dname = m_Dname;
        this.m_DsaveSite = m_DsaveSite;
        this.m_DmainUse = m_DmainUse;
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

    public String getM_RstartDate() {
        return m_RstartDate;
    }

    public void setM_RstartDate(String m_RstartDate) {
        this.m_RstartDate = m_RstartDate;
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

    public String getM_Dname() {
        return m_Dname;
    }

    public void setM_Dname(String m_Dname) {
        this.m_Dname = m_Dname;
    }

    public String getM_DsaveSite() {
        return m_DsaveSite;
    }

    public void setM_DsaveSite(String m_DsaveSite) {
        this.m_DsaveSite = m_DsaveSite;
    }

    public String getM_DmainUse() {
        return m_DmainUse;
    }

    public void setM_DmainUse(String m_DmainUse) {
        this.m_DmainUse = m_DmainUse;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "m_Rno=" + m_Rno +
                ", m_Dno=" + m_Dno +
                ", m_Uno='" + m_Uno + '\'' +
                ", m_RreservationDate='" + m_RreservationDate + '\'' +
                ", m_RstartDate='" + m_RstartDate + '\'' +
                ", m_RreturnDate='" + m_RreturnDate + '\'' +
                ", m_RfeedBack='" + m_RfeedBack + '\'' +
                ", m_Rstate=" + m_Rstate +
                ", m_Uname='" + m_Uname + '\'' +
                ", m_Utype='" + m_Utype + '\'' +
                ", m_UcreditGrade=" + m_UcreditGrade +
                ", m_Dname='" + m_Dname + '\'' +
                ", m_DsaveSite='" + m_DsaveSite + '\'' +
                ", m_DmainUse='" + m_DmainUse + '\'' +
                '}';
    }
}

