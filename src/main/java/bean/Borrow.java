package bean;

public class Borrow {
    int b_no;
    int d_no;
    String u_no;
    String b_borrow_date;
    String b_return_date;
    int b_state;

    //附加属性
    String u_name;
    String u_type;
    int u_credit_grade;
    String u_phone;
    String u_email;

    public Borrow(int b_no, int d_no, String u_no, String b_borrow_date, String b_return_date, int b_state, String u_name, String u_type, int u_credit_grade, String u_phone, String u_email) {
        this.b_no = b_no;
        this.d_no = d_no;
        this.u_no = u_no;
        this.b_borrow_date = b_borrow_date;
        this.b_return_date = b_return_date;
        this.b_state = b_state;
        this.u_name = u_name;
        this.u_type = u_type;
        this.u_credit_grade = u_credit_grade;
        this.u_phone = u_phone;
        this.u_email = u_email;
    }

    public Borrow() {
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

    public String getB_borrow_date() {
        return b_borrow_date;
    }

    public void setB_borrow_date(String b_borrow_date) {
        this.b_borrow_date = b_borrow_date;
    }

    public String getB_return_date() {
        return b_return_date;
    }

    public void setB_return_date(String b_return_date) {
        this.b_return_date = b_return_date;
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

    public int getU_credit_grade() {
        return u_credit_grade;
    }

    public void setU_credit_grade(int u_credit_grade) {
        this.u_credit_grade = u_credit_grade;
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

    @Override
    public String toString() {
        return "Borrow{" +
                "b_no=" + b_no +
                ", d_no=" + d_no +
                ", u_no='" + u_no + '\'' +
                ", b_borrow_date='" + b_borrow_date + '\'' +
                ", b_return_date='" + b_return_date + '\'' +
                ", b_state=" + b_state +
                ", u_name='" + u_name + '\'' +
                ", u_type='" + u_type + '\'' +
                ", u_credit_grade=" + u_credit_grade +
                ", u_phone='" + u_phone + '\'' +
                ", u_email='" + u_email + '\'' +
                '}';
    }
}
