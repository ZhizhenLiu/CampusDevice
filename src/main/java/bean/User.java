package bean;

/*
 * @Description: 用户信息
 */
public class User {
    private String m_Uno;
    private String m_Uname;
    private String m_UwechatID;
    private String m_Uemail;
    private String m_Uphone;
    private int m_UcreditGrade;
    private String m_Utype;
    private String m_UmentorName;
    private String m_UmentorPhone;
    private String m_UmajorClass;


    public User(String m_Uno, String m_Uname, String m_UwechatID, String m_Uemail, String m_Uphone, int m_UcreditGrade, String m_Utype, String m_UmentorName, String m_UmentorPhone, String m_UmajorClass) {
        this.m_Uno = m_Uno;
        this.m_Uname = m_Uname;
        this.m_UwechatID = m_UwechatID;
        this.m_Uemail = m_Uemail;
        this.m_Uphone = m_Uphone;
        this.m_UcreditGrade = m_UcreditGrade;
        this.m_Utype = m_Utype;
        this.m_UmentorName = m_UmentorName;
        this.m_UmentorPhone = m_UmentorPhone;
        this.m_UmajorClass = m_UmajorClass;
    }

    public User() {
    }

    public String getM_Uno() {
        return m_Uno;
    }

    public void setM_Uno(String m_Uno) {
        this.m_Uno = m_Uno;
    }

    public String getM_Uname() {
        return m_Uname;
    }

    public void setM_Uname(String m_Uname) {
        this.m_Uname = m_Uname;
    }

    public String getM_UwechatID() {
        return m_UwechatID;
    }

    public void setM_UwechatID(String m_UwechatID) {
        this.m_UwechatID = m_UwechatID;
    }

    public String getM_Uemail() {
        return m_Uemail;
    }

    public void setM_Uemail(String m_Uemail) {
        this.m_Uemail = m_Uemail;
    }

    public String getM_Uphone() {
        return m_Uphone;
    }

    public void setM_Uphone(String m_Uphone) {
        this.m_Uphone = m_Uphone;
    }

    public int getM_UcreditGrade() {
        return m_UcreditGrade;
    }

    public void setM_UcreditGrade(int m_UcreditGrade) {
        this.m_UcreditGrade = m_UcreditGrade;
    }

    public String getM_Utype() {
        return m_Utype;
    }

    public void setM_Utype(String m_Utype) {
        this.m_Utype = m_Utype;
    }

    public String getM_UmentorName() {
        return m_UmentorName;
    }

    public void setM_UmentorName(String m_UmentorName) {
        this.m_UmentorName = m_UmentorName;
    }

    public String getM_UmentorPhone() {
        return m_UmentorPhone;
    }

    public void setM_UmentorPhone(String m_UmentorPhone) {
        this.m_UmentorPhone = m_UmentorPhone;
    }

    public String getM_UmajorClass() {
        return m_UmajorClass;
    }

    public void setM_UmajorClass(String m_UmajorClass) {
        this.m_UmajorClass = m_UmajorClass;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_no='" + m_Uno + '\'' +
                ", u_name='" + m_Uname + '\'' +
                ", u_wechatId='" + m_UwechatID + '\'' +
                ", u_email='" + m_Uemail + '\'' +
                ", u_phone='" + m_Uphone + '\'' +
                ", u_credit_grade=" + m_UcreditGrade +
                ", u_type='" + m_Utype + '\'' +
                ", u_mentor_name='" + m_UmentorName + '\'' +
                ", u_mentor_phone='" + m_UmentorPhone + '\'' +
                ", u_major_class='" + m_UmajorClass + '\'' +
                '}';
    }
}
