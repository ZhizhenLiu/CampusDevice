package bean;

import java.util.Date;

public class TextRecord {

    //短信编号
    private int tr_no;
    //发给谁的
    private String u_no;
    //短信内容
    private String tr_content;
    //发送短信时间
    private Date tr_date;

    public TextRecord(int tr_no, String u_no, String tr_content, Date tr_date) {
        this.tr_no = tr_no;
        this.u_no = u_no;
        this.tr_content = tr_content;
        this.tr_date = tr_date;
    }

    public int getTr_no() {
        return tr_no;
    }

    public void setTr_no(int tr_no) {
        this.tr_no = tr_no;
    }

    public String getU_no() {
        return u_no;
    }

    public void setU_no(String u_no) {
        this.u_no = u_no;
    }

    public String getTr_content() {
        return tr_content;
    }

    public void setTr_content(String tr_content) {
        this.tr_content = tr_content;
    }

    public Date getTr_date() {
        return tr_date;
    }

    public void setTr_date(Date tr_date) {
        this.tr_date = tr_date;
    }

    @Override
    public String toString() {
        return "TextRecord{" +
                "tr_no=" + tr_no +
                ", u_no='" + u_no + '\'' +
                ", tr_content='" + tr_content + '\'' +
                ", tr_date=" + tr_date +
                '}';
    }
}
