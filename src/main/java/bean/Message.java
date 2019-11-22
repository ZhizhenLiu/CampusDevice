package bean;

/**
 * 消息表
 */
public class Message {

    //消息_编号
    private int m_no;
    //用户_学工号
    private String u_no;
    //消息_内容
    private String m_content;
    //消息_时间
    private String m_date;

    public Message(int m_no, String u_no, String m_content, String m_date) {
        this.m_no = m_no;
        this.u_no = u_no;
        this.m_content = m_content;
        this.m_date = m_date;
    }

    public Message() {
    }

    public int getM_no() {
        return m_no;
    }

    public void setM_no(int m_no) {
        this.m_no = m_no;
    }

    public String getU_no() {
        return u_no;
    }

    public void setU_no(String u_no) {
        this.u_no = u_no;
    }

    public String getM_content() {
        return m_content;
    }

    public void setM_content(String m_content) {
        this.m_content = m_content;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "m_Mno=" + m_no +
                ", m_Uno='" + u_no + '\'' +
                ", m_Mcontent='" + m_content + '\'' +
                ", m_Mdate='" + m_date + '\'' +
                '}';
    }
}
