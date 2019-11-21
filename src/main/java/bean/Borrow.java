package bean;

public class Borrow {
    private int m_Bno;
    private int m_Dno;
    private String m_Uno;
    private String m_BborrowDate;
    private String m_BreturnDate;
    private int m_Bstate;

    //附加用户属性
    private String m_Uname;
    private String m_Utype;
    private int m_UcreditGrade;
    private String m_Uphone;
    private String m_Uemail;

    //附加设备属性
    private String m_DsaveSite;
    private String m_Dname;
    private String m_DmainUse;

    public Borrow() {
    }

    public Borrow(int m_Bno, int m_Dno, String m_Uno, String m_BborrowDate, String m_BreturnDate, int m_Bstate, String m_Uname, String m_Utype, int m_UcreditGrade, String m_Uphone, String m_Uemail, String m_DsaveSite, String m_Dname, String m_DmainUse) {
        this.m_Bno = m_Bno;
        this.m_Dno = m_Dno;
        this.m_Uno = m_Uno;
        this.m_BborrowDate = m_BborrowDate;
        this.m_BreturnDate = m_BreturnDate;
        this.m_Bstate = m_Bstate;
        this.m_Uname = m_Uname;
        this.m_Utype = m_Utype;
        this.m_UcreditGrade = m_UcreditGrade;
        this.m_Uphone = m_Uphone;
        this.m_Uemail = m_Uemail;
        this.m_DsaveSite = m_DsaveSite;
        this.m_Dname = m_Dname;
        this.m_DmainUse = m_DmainUse;
    }

    public int getM_Bno() {
        return m_Bno;
    }

    public void setM_Bno(int m_Bno) {
        this.m_Bno = m_Bno;
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

    public String getM_BborrowDate() {
        return m_BborrowDate;
    }

    public void setM_BborrowDate(String m_BborrowDate) {
        this.m_BborrowDate = m_BborrowDate;
    }

    public String getM_BreturnDate() {
        return m_BreturnDate;
    }

    public void setM_BreturnDate(String m_BreturnDate) {
        this.m_BreturnDate = m_BreturnDate;
    }

    public int getM_Bstate() {
        return m_Bstate;
    }

    public void setM_Bstate(int m_Bstate) {
        this.m_Bstate = m_Bstate;
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

    public String getM_Uphone() {
        return m_Uphone;
    }

    public void setM_Uphone(String m_Uphone) {
        this.m_Uphone = m_Uphone;
    }

    public String getM_Uemail() {
        return m_Uemail;
    }

    public void setM_Uemail(String m_Uemail) {
        this.m_Uemail = m_Uemail;
    }

    public String getM_DsaveSite() {
        return m_DsaveSite;
    }

    public void setM_DsaveSite(String m_DsaveSite) {
        this.m_DsaveSite = m_DsaveSite;
    }

    public String getM_Dname() {
        return m_Dname;
    }

    public void setM_Dname(String m_Dname) {
        this.m_Dname = m_Dname;
    }

    public String getM_DmainUse() {
        return m_DmainUse;
    }

    public void setM_DmainUse(String m_DmainUse) {
        this.m_DmainUse = m_DmainUse;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "b_no=" + m_Bno +
                ", d_no=" + m_Dno +
                ", u_no='" + m_Uno + '\'' +
                ", b_borrow_date='" + m_BborrowDate + '\'' +
                ", b_return_date='" + m_BreturnDate + '\'' +
                ", b_state=" + m_Bstate +
                ", u_name='" + m_Uname + '\'' +
                ", u_type='" + m_Utype + '\'' +
                ", u_credit_grade=" + m_UcreditGrade +
                ", u_phone='" + m_Uphone + '\'' +
                ", u_email='" + m_Uemail + '\'' +
                ", return_place='" + m_DsaveSite + '\'' +
                ", d_name='" + m_Dname + '\'' +
                ", d_main_use='" + m_DmainUse + '\'' +
                '}';
    }
}
