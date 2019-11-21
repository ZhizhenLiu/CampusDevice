package bean;
/*
 * @Description: 管理员信息
 */
public class Admin {
    private int m_Ano;
    private String m_Aname;
    private String m_AwechatID;
    private String m_Atype;
    private String m_Aphone;
    private String m_Aemail;

    /*
     * @Description: 管理员构造函数
     * @Param a_no  a_name  a_wechatId  a_type  a_phone  a_emial
     * @Return: null
     */
    public Admin(int m_Ano, String m_Aname, String m_AwechatID, String m_Atype, String m_Aphone, String m_Aemail) {
        this.m_Ano = m_Ano;
        this.m_Aname = m_Aname;
        this.m_AwechatID = m_AwechatID;
        this.m_Atype = m_Atype;
        this.m_Aphone = m_Aphone;
        this.m_Aemail = m_Aemail;
    }

    public Admin() {
    }

    public int getM_Ano() {
        return m_Ano;
    }

    public void setM_Ano(int m_Ano) {
        this.m_Ano = m_Ano;
    }

    public String getM_Aname() {
        return m_Aname;
    }

    public void setM_Aname(String m_Aname) {
        this.m_Aname = m_Aname;
    }

    public String getM_AwechatID() {
        return m_AwechatID;
    }

    public void setM_AwechatID(String m_AwechatID) {
        this.m_AwechatID = m_AwechatID;
    }

    public String getM_Atype() {
        return m_Atype;
    }

    public void setM_Atype(String m_Atype) {
        this.m_Atype = m_Atype;
    }

    public String getM_Aphone() {
        return m_Aphone;
    }

    public void setM_Aphone(String m_Aphone) {
        this.m_Aphone = m_Aphone;
    }

    public String getM_Aemail() {
        return m_Aemail;
    }

    public void setM_Aemail(String m_Aemail) {
        this.m_Aemail = m_Aemail;
    }

    @Override
    public String toString() {
        return "Admin[" +
                "a_no='" + m_Ano + '\"' +
                ", a_name=\"" + m_Aname + '\"' +
                ", a_wechatId=\"" + m_AwechatID + '\"' +
                ", a_type=\"" + m_Atype + '\"' +
                ", a_phone=\"" + m_Aphone + '\"' +
                ", a_emial=\"" + m_Aemail + '\"' +
                ']';
    }

}
