package bean;

import java.util.Date;

/**
 * 消息表
 */
public class Message {

    //消息_编号
    private int m_Mno;
    //用户_学工号
    private String m_Uno;
    //消息_内容
    private String m_Mcontent;
    //消息_时间
    private Date m_Mdate;

    public Message(int m_Mno, String m_Uno, String m_Mcontent, Date m_Mdate) {
        this.m_Mno = m_Mno;
        this.m_Uno = m_Uno;
        this.m_Mcontent = m_Mcontent;
        this.m_Mdate = m_Mdate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "m_no=" + m_Mno +
                ", u_no='" + m_Uno + '\'' +
                ", m_content='" + m_Mcontent + '\'' +
                ", m_date=" + m_Mdate +
                '}';
    }

    public int getM_Mno() {
        return m_Mno;
    }

    public void setM_Mno(int m_Mno) {
        this.m_Mno = m_Mno;
    }

    public String getM_Uno() {
        return m_Uno;
    }

    public void setM_Uno(String m_Uno) {
        this.m_Uno = m_Uno;
    }

    public String getM_Mcontent() {
        return m_Mcontent;
    }

    public void setM_Mcontent(String m_Mcontent) {
        this.m_Mcontent = m_Mcontent;
    }

    public Date getM_Mdate() {
        return m_Mdate;
    }

    public void setM_Mdate(Date m_Mdate) {
        this.m_Mdate = m_Mdate;
    }
}
