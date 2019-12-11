package bean;

/**
 * 消息表
 */
public class Message
{

    //消息_编号
    private int m_no;
    //用户_学工号
    private String u_no;
    //消息_内容
    private String m_content;
    //消息_时间
    private String m_date;
    //消息_状态(m_state=1则表示消息已读，m_state=0则表示消息未读)
    private int m_state;

    public Message(int m_no, String u_no, String m_content, String m_date, int m_state)
    {
        this.m_no = m_no;
        this.u_no = u_no;
        this.m_content = m_content;
        this.m_date = m_date;
        this.m_state = m_state;
    }

    public Message()
    {
    }

    public int getM_no()
    {
        return m_no;
    }

    public void setM_no(int m_no)
    {
        this.m_no = m_no;
    }

    public String getU_no()
    {
        return u_no;
    }

    public void setU_no(String u_no)
    {
        this.u_no = u_no;
    }

    public String getM_content()
    {
        return m_content;
    }

    public void setM_content(String m_content)
    {
        this.m_content = m_content;
    }

    public String getM_date()
    {
        return m_date;
    }

    public void setM_date(String m_date)
    {
        this.m_date = m_date;
    }

    public int getM_state()
    {
        return m_state;
    }

    public void setM_state(int m_state)
    {
        this.m_state = m_state;
    }

    @Override
    public String toString()
    {
        return "Message{" +
                "m_no=" + m_no +
                ", u_no='" + u_no + '\'' +
                ", m_content='" + m_content + '\'' +
                ", m_date='" + m_date + '\'' +
                ", m_state=" + m_state +
                '}';
    }
}
