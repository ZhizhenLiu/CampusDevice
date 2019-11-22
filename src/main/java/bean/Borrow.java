package bean;

public class Borrow {
    private int b_no;
    private int d_no;
    private String u_no;
    private String b_borrowDate;
    private String b_returnDate;
    private int b_state;

    //附加用户属性
    private String u_name;
    private String u_type;
    private int u_creditGrade;
    private String u_phone;
    private String u_email;

    //附加设备属性
    private String d_saveSite;
    private String d_name;
    private String d_mainUse;

    public Borrow() {
    }

    public Borrow(int b_no, int d_no, String u_no, String b_borrowDate, String b_returnDate, int b_state, String u_name, String u_type, int u_creditGrade, String u_phone, String u_email, String d_saveSite, String d_name, String d_mainUse) {
        this.b_no = b_no;
        this.d_no = d_no;
        this.u_no = u_no;
        this.b_borrowDate = b_borrowDate;
        this.b_returnDate = b_returnDate;
        this.b_state = b_state;
        this.u_name = u_name;
        this.u_type = u_type;
        this.u_creditGrade = u_creditGrade;
        this.u_phone = u_phone;
        this.u_email = u_email;
        this.d_saveSite = d_saveSite;
        this.d_name = d_name;
        this.d_mainUse = d_mainUse;
    }

    public int getB_no() {
        return b_no;
    }

    public void setB_no(int b_no) {
        this.b_no = b_no;
    }

    public int getD_no() {
        return d_no;
    }

    public void setD_no(int d_no) {
        this.d_no = d_no;
    }

    public String getU_no() {
        return u_no;
    }

    public void setU_no(String u_no) {
        this.u_no = u_no;
    }

    public String getB_borrowDate() {
        return b_borrowDate;
    }

    public void setB_borrowDate(String b_borrowDate) {
        this.b_borrowDate = b_borrowDate;
    }

    public String getB_returnDate() {
        return b_returnDate;
    }

    public void setB_returnDate(String b_returnDate) {
        this.b_returnDate = b_returnDate;
    }

    public int getB_state() {
        return b_state;
    }

    public void setB_state(int b_state) {
        this.b_state = b_state;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }

    public int getU_creditGrade() {
        return u_creditGrade;
    }

    public void setU_creditGrade(int u_creditGrade) {
        this.u_creditGrade = u_creditGrade;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getD_saveSite() {
        return d_saveSite;
    }

    public void setD_saveSite(String d_saveSite) {
        this.d_saveSite = d_saveSite;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_mainUse() {
        return d_mainUse;
    }

    public void setD_mainUse(String d_mainUse) {
        this.d_mainUse = d_mainUse;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "b_no=" + b_no +
                ", d_no=" + d_no +
                ", u_no='" + u_no + '\'' +
                ", b_borrow_date='" + b_borrowDate + '\'' +
                ", b_return_date='" + b_returnDate + '\'' +
                ", b_state=" + b_state +
                ", u_name='" + u_name + '\'' +
                ", u_type='" + u_type + '\'' +
                ", u_credit_grade=" + u_creditGrade +
                ", u_phone='" + u_phone + '\'' +
                ", u_email='" + u_email + '\'' +
                ", return_place='" + d_saveSite + '\'' +
                ", d_name='" + d_name + '\'' +
                ", d_main_use='" + d_mainUse + '\'' +
                '}';
    }
}
