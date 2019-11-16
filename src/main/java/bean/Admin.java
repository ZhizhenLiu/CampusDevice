package bean;
/*
 * @Description: 管理员信息
 */
public class Admin {
    String a_no;
    String a_name;
    String a_wechatId;
    String a_type;
    String a_phone;
    String a_emial;

    /*
     * @Description: 管理员构造函数
     * @Param a_no  a_name  a_wechatId  a_type  a_phone  a_emial
     * @Return: null
     */
    public Admin(String a_no, String a_name, String a_wechatId, String a_type, String a_phone, String a_emial) {
        this.a_no = a_no;
        this.a_name = a_name;
        this.a_wechatId = a_wechatId;
        this.a_type = a_type;
        this.a_phone = a_phone;
        this.a_emial = a_emial;
    }

    public String getA_no() {
        return a_no;
    }

    public void setA_no(String a_no) {
        this.a_no = a_no;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_wechatId() {
        return a_wechatId;
    }

    public void setA_wechatId(String a_wechatId) {
        this.a_wechatId = a_wechatId;
    }

    public String getA_type() {
        return a_type;
    }

    public void setA_type(String a_type) {
        this.a_type = a_type;
    }

    public String getA_phone() {
        return a_phone;
    }

    public void setA_phone(String a_phone) {
        this.a_phone = a_phone;
    }

    public String getA_emial() {
        return a_emial;
    }

    public void setA_emial(String a_emial) {
        this.a_emial = a_emial;
    }

    @Override
    public String toString() {
        return "Admin[" +
                "a_no='" + a_no + '\"' +
                ", a_name=\"" + a_name + '\"' +
                ", a_wechatId=\"" + a_wechatId + '\"' +
                ", a_type=\"" + a_type + '\"' +
                ", a_phone=\"" + a_phone + '\"' +
                ", a_emial=\"" + a_emial + '\"' +
                ']';
    }

}
