package bean;

import java.util.Date;

/**
 * 信用记录
 */
public class CreditRecord {

    //信用记录_编号
    private int cr_no;
    //用户_学工号
    private String u_no;
    //信用记录_分数浮动
    private int cr_change_score;
    //信用记录_变化原因
    private String cr_change_reason;
    //信用记录_日期(自动填写)
    private String cr_date;
    //信用记录_变化后分数
    private int cr_after_grade;

    public CreditRecord(int cr_no, String u_no, int cr_change_score, String cr_change_reason, String cr_date, int cr_after_grade) {
        this.cr_no = cr_no;
        this.u_no = u_no;
        this.cr_change_score = cr_change_score;
        this.cr_change_reason = cr_change_reason;
        this.cr_date = cr_date;
        this.cr_after_grade = cr_after_grade;
    }

    public CreditRecord() {
    }

    public int getCr_no() {
        return cr_no;
    }

    public void setCr_no(int cr_no) {
        this.cr_no = cr_no;
    }

    public String getU_no() {
        return u_no;
    }

    public void setU_no(String u_no) {
        this.u_no = u_no;
    }

    public int getCr_change_score() {
        return cr_change_score;
    }

    public void setCr_change_score(int cr_change_score) {
        this.cr_change_score = cr_change_score;
    }

    public String getCr_change_reason() {
        return cr_change_reason;
    }

    public void setCr_change_reason(String cr_change_reason) {
        this.cr_change_reason = cr_change_reason;
    }

    public String getCr_date() {
        return cr_date;
    }

    public void setCr_date(String cr_date) {
        this.cr_date = cr_date;
    }

    public int getCr_after_grade() {
        return cr_after_grade;
    }

    public void setCr_after_grade(int cr_after_grade) {
        this.cr_after_grade = cr_after_grade;
    }

    @Override
    public String toString() {
        return "CreditRecord{" +
                "cr_no=" + cr_no +
                ", u_no='" + u_no + '\'' +
                ", cr_change_score=" + cr_change_score +
                ", cr_change_reason='" + cr_change_reason + '\'' +
                ", cr_date='" + cr_date + '\'' +
                ", cr_after_grade=" + cr_after_grade +
                '}';
    }
}
