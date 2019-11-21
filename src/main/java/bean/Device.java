package bean;
/*
 * @Description: 设备信息
 */
public class Device {
    private int m_Dno;
    private String m_Ano;
    private String m_Dstate;
    private int m_DborrowedTimes;
    private String m_Dname;
    private String m_DimportantParam;
    private String m_DmainUse;
    private String m_DsaveSite;

    //扩展属性
    private int m_Rsum; //预约人数
    private String m_Aname;
    private String m_Aphone;

    public Device(int m_Dno, String m_Ano, String m_Dstate, int m_DborrowedTimes, String m_Dname, String m_DimportantParam, String m_DmainUse, String m_DsaveSite) {
        this.m_Dno = m_Dno;
        this.m_Ano = m_Ano;
        this.m_Dstate = m_Dstate;
        this.m_DborrowedTimes = m_DborrowedTimes;
        this.m_Dname = m_Dname;
        this.m_DimportantParam = m_DimportantParam;
        this.m_DmainUse = m_DmainUse;
        this.m_DsaveSite = m_DsaveSite;
    }

    public Device() {
    }

    public int getM_Dno() {
        return m_Dno;
    }

    public void setM_Dno(int m_Dno) {
        this.m_Dno = m_Dno;
    }

    public String getM_Ano() {
        return m_Ano;
    }

    public void setM_Ano(String m_Ano) {
        this.m_Ano = m_Ano;
    }

    public String getM_Dstate() {
        return m_Dstate;
    }

    public void setM_Dstate(String m_Dstate) {
        this.m_Dstate = m_Dstate;
    }

    public int getM_DborrowedTimes() {
        return m_DborrowedTimes;
    }

    public void setM_DborrowedTimes(int m_DborrowedTimes) {
        this.m_DborrowedTimes = m_DborrowedTimes;
    }

    public String getM_Dname() {
        return m_Dname;
    }

    public void setM_Dname(String m_Dname) {
        this.m_Dname = m_Dname;
    }

    public String getM_DimportantParam() {
        return m_DimportantParam;
    }

    public void setM_DimportantParam(String m_DimportantParam) {
        this.m_DimportantParam = m_DimportantParam;
    }

    public String getM_DmainUse() {
        return m_DmainUse;
    }

    public void setM_DmainUse(String m_DmainUse) {
        this.m_DmainUse = m_DmainUse;
    }

    public String getM_DsaveSite() {
        return m_DsaveSite;
    }

    public void setM_DsaveSite(String m_DsaveSite) {
        this.m_DsaveSite = m_DsaveSite;
    }

    public int getM_Rsum() {
        return m_Rsum;
    }

    public void setM_Rsum(int m_Rsum) {
        this.m_Rsum = m_Rsum;
    }

    public String getM_Aname() {
        return m_Aname;
    }

    public void setM_Aname(String m_Aname) {
        this.m_Aname = m_Aname;
    }

    public String getM_Aphone() {
        return m_Aphone;
    }

    public void setM_Aphone(String m_Aphone) {
        this.m_Aphone = m_Aphone;
    }

    @Override
    public String toString() {
        return "Device{" +
                "d_no=" + m_Dno +
                ", a_no='" + m_Ano + '\'' +
                ", d_state='" + m_Dstate + '\'' +
                ", d_borrowed_times=" + m_DborrowedTimes +
                ", d_name='" + m_Dname + '\'' +
                ", d_important_param='" + m_DimportantParam + '\'' +
                ", d_main_use='" + m_DmainUse + '\'' +
                ", d_save_site='" + m_DsaveSite + '\'' +
                ", r_sum=" + m_Rsum +
                ", a_name='" + m_Aname + '\'' +
                ", a_phone='" + m_Aphone + '\'' +
                '}';
    }
}
