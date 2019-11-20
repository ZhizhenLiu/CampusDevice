package bean;

/*
 * @Description: 用户信息
 */
public class User {
    private String u_no;
    private String u_name;
    private String u_wechatId;
    private String u_email;
    private String u_phone;
    private int u_credit_grade;
    private String u_type;
    private String u_mentor_name;
    private String u_mentor_phone;
    private String u_major_class;


    public User(String u_no, String u_name, String u_wechatId, String u_email, String u_phone, int u_credit_grade, String u_type, String u_mentor_name, String u_mentor_phone, String u_major_class) {
        this.u_no = u_no;
        this.u_name = u_name;
        this.u_wechatId = u_wechatId;
        this.u_email = u_email;
        this.u_phone = u_phone;
        this.u_credit_grade = u_credit_grade;
        this.u_type = u_type;
        this.u_mentor_name = u_mentor_name;
        this.u_mentor_phone = u_mentor_phone;
        this.u_major_class = u_major_class;
    }

    public User() {
    }

    public String getU_no() {
        return u_no;
    }

    public void setU_no(String u_no) {
        this.u_no = u_no;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_wechatId() {
        return u_wechatId;
    }

    public void setU_wechatId(String u_wechatId) {
        this.u_wechatId = u_wechatId;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public int getU_credit_grade() {
        return u_credit_grade;
    }

    public void setU_credit_grade(int u_credit_grade) {
        this.u_credit_grade = u_credit_grade;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }

    public String getU_mentor_name() {
        return u_mentor_name;
    }

    public void setU_mentor_name(String u_mentor_name) {
        this.u_mentor_name = u_mentor_name;
    }

    public String getU_mentor_phone() {
        return u_mentor_phone;
    }

    public void setU_mentor_phone(String u_mentor_phone) {
        this.u_mentor_phone = u_mentor_phone;
    }

    public String getU_major_class() {
        return u_major_class;
    }

    public void setU_major_class(String u_major_class) {
        this.u_major_class = u_major_class;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_no='" + u_no + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_wechatId='" + u_wechatId + '\'' +
                ", u_email='" + u_email + '\'' +
                ", u_phone='" + u_phone + '\'' +
                ", u_credit_grade=" + u_credit_grade +
                ", u_type='" + u_type + '\'' +
                ", u_mentor_name='" + u_mentor_name + '\'' +
                ", u_mentor_phone='" + u_mentor_phone + '\'' +
                ", u_major_class='" + u_major_class + '\'' +
                '}';
    }
}
