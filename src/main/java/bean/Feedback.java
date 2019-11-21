package bean;

import java.util.Date;

/**
 * 反馈表
 */
public class Feedback {

    //反馈_编号
    private int m_FbNo;
    //用户_学工号
    private String m_Uno;
    //反馈_内容
    private String m_FbContent;
    //反馈_时间
    private Date m_FbDate;

    public Feedback(int m_FbNo, String m_Uno, String m_FbContent, Date m_FbDate) {
        this.m_FbNo = m_FbNo;
        this.m_Uno = m_Uno;
        this.m_FbContent = m_FbContent;
        this.m_FbDate = m_FbDate;
    }

    public Feedback() {
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "f_no=" + m_FbNo +
                ", u_no='" + m_Uno + '\'' +
                ", f_content='" + m_FbContent + '\'' +
                ", f_date=" + m_FbDate +
                '}';
    }

    public int getM_FbNo() {
        return m_FbNo;
    }

    public void setM_FbNo(int m_FbNo) {
        this.m_FbNo = m_FbNo;
    }

    public String getM_Uno() {
        return m_Uno;
    }

    public void setM_Uno(String m_Uno) {
        this.m_Uno = m_Uno;
    }

    public String getM_FbContent() {
        return m_FbContent;
    }

    public void setM_FbContent(String m_FbContent) {
        this.m_FbContent = m_FbContent;
    }

    public Date getM_FbDate() {
        return m_FbDate;
    }

    public void setM_FbDate(Date m_FbDate) {
        this.m_FbDate = m_FbDate;
    }
}
