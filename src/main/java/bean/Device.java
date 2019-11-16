package bean;
/*
 * @Description: 设备信息
 */
public class Device {

    int d_no;
    String a_no;
    String d_state;
    int d_borrowed_times;
    String d_name;
    String d_important_param;
    String d_main_use;
    String d_save_site;
    int r_sum; //预约人数

    public Device(int d_no, String a_no, String d_state, int d_borrowed_times, String d_name, String d_important_param, String d_main_use, String d_save_site) {
        this.d_no = d_no;
        this.a_no = a_no;
        this.d_state = d_state;
        this.d_borrowed_times = d_borrowed_times;
        this.d_name = d_name;
        this.d_important_param = d_important_param;
        this.d_main_use = d_main_use;
        this.d_save_site = d_save_site;
    }

    public Device() {
    }

    public int getD_no() {
        return d_no;
    }

    public void setD_no(int d_no) {
        this.d_no = d_no;
    }

    public String getA_no() {
        return a_no;
    }

    public void setA_no(String a_no) {
        this.a_no = a_no;
    }

    public String getD_state() {
        return d_state;
    }

    public void setD_state(String d_state) {
        this.d_state = d_state;
    }

    public int getD_borrowed_times() {
        return d_borrowed_times;
    }

    public void setD_borrowed_times(int d_borrowed_times) {
        this.d_borrowed_times = d_borrowed_times;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_important_param() {
        return d_important_param;
    }

    public void setD_important_param(String d_important_param) {
        this.d_important_param = d_important_param;
    }

    public String getD_main_use() {
        return d_main_use;
    }

    public void setD_main_use(String d_main_use) {
        this.d_main_use = d_main_use;
    }

    public String getD_save_site() {
        return d_save_site;
    }

    public void setD_save_site(String d_save_site) {
        this.d_save_site = d_save_site;
    }

    public int getR_sum() {
        return r_sum;
    }

    public void setR_sum(int r_sum) {
        this.r_sum = r_sum;
    }

    @Override
    public String toString() {
        return "Device{" +
                "d_no=" + d_no +
                ", a_no='" + a_no + '\'' +
                ", d_state='" + d_state + '\'' +
                ", d_borrowed_times=" + d_borrowed_times +
                ", d_name='" + d_name + '\'' +
                ", d_important_param='" + d_important_param + '\'' +
                ", d_main_use='" + d_main_use + '\'' +
                ", d_save_site='" + d_save_site + '\'' +
                '}';
    }
}
