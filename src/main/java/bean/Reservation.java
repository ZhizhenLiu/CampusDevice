package bean;

public class Reservation {
    String r_no;
    String d_no;
    String u_no;
    String r_reservation_date;
    String r_borrow_date;
    String r_return_date;
    String r_feedback;

    public Reservation(String d_no, String u_no, String r_reservation_date, String r_return_date, String r_feedback) {
        this.d_no = d_no;
        this.u_no = u_no;
        this.r_reservation_date = r_reservation_date;
        this.r_return_date = r_return_date;
        this.r_feedback = r_feedback;
    }

    public Reservation(String r_no, String d_no, String u_no, String r_reservation_date, String r_borrow_date, String r_return_date, String r_feedback) {
        this.r_no = r_no;
        this.d_no = d_no;
        this.u_no = u_no;
        this.r_reservation_date = r_reservation_date;
        this.r_borrow_date = r_borrow_date;
        this.r_return_date = r_return_date;
        this.r_feedback = r_feedback;
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
}

