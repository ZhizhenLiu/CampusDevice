package bean;

public class Reservation {
    String r_no;
    String d_no;
    String u_no;
    String u_name;
    String u_type;
    int u_credit_grade;
    String r_reservation_date;
    String r_borrow_date;
    String r_return_date;
    String r_feedback;

    public Reservation(String r_no, String d_no, String u_no, String u_name, String u_type, int u_credit_grade, String r_reservation_date, String r_borrow_date, String r_return_date, String r_feedback) {
        this.r_no = r_no;
        this.d_no = d_no;
        this.u_no = u_no;
        this.u_name = u_name;
        this.u_type = u_type;
        this.u_credit_grade = u_credit_grade;
        this.r_reservation_date = r_reservation_date;
        this.r_borrow_date = r_borrow_date;
        this.r_return_date = r_return_date;
        this.r_feedback = r_feedback;
    }

    public Reservation() {
    }

    public String getR_no() {
        return r_no;
    }

    public void setR_no(String r_no) {
        this.r_no = r_no;
    }

    public String getD_no() {
        return d_no;
    }

    public void setD_no(String d_no) {
        this.d_no = d_no;
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

    public String getR_reservation_date() {
        return r_reservation_date;
    }

    public void setR_reservation_date(String r_reservation_date) {
        this.r_reservation_date = r_reservation_date;
    }

    public String getR_borrow_date() {
        return r_borrow_date;
    }

    public void setR_borrow_date(String r_borrow_date) {
        this.r_borrow_date = r_borrow_date;
    }

    public String getR_return_date() {
        return r_return_date;
    }

    public void setR_return_date(String r_return_date) {
        this.r_return_date = r_return_date;
    }

    public String getR_feedback() {
        return r_feedback;
    }

    public void setR_feedback(String r_feedback) {
        this.r_feedback = r_feedback;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "r_no='" + r_no + '\'' +
                ", d_no='" + d_no + '\'' +
                ", u_no='" + u_no + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_type='" + u_type + '\'' +
                ", u_credit_grade=" + u_credit_grade +
                ", r_reservation_date='" + r_reservation_date + '\'' +
                ", r_borrow_date='" + r_borrow_date + '\'' +
                ", r_return_date='" + r_return_date + '\'' +
                ", r_feedback='" + r_feedback + '\'' +
                '}';
    }
}

