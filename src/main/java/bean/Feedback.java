package bean;

import java.util.Date;

/**
 * 反馈表
 */
public class Feedback {

    //反馈_编号
    private int f_no;
    //用户_学工号
    private String u_no;
    //反馈_内容
    private String f_content;
    //反馈_时间
    private Date f_date;

    public Feedback(int f_no, String u_no, String f_content, Date f_date) {
        this.f_no = f_no;
        this.u_no = u_no;
        this.f_content = f_content;
        this.f_date = f_date;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "f_no=" + f_no +
                ", u_no='" + u_no + '\'' +
                ", f_content='" + f_content + '\'' +
                ", f_date=" + f_date +
                '}';
    }

    public int getF_no() {
        return f_no;
    }

    public void setF_no(int f_no) {
        this.f_no = f_no;
    }

    public String getU_no() {
        return u_no;
    }

    public void setU_no(String u_no) {
        this.u_no = u_no;
    }

    public String getF_content() {
        return f_content;
    }

    public void setF_content(String f_content) {
        this.f_content = f_content;
    }

    public Date getF_date() {
        return f_date;
    }

    public void setF_date(Date f_date) {
        this.f_date = f_date;
    }
}
