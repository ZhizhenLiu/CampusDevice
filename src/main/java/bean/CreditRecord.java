package bean;

/**
 * 信用记录
 */
public class CreditRecord {

    //信用记录_编号
    private int m_CrNo;
    //用户_学工号
    private String m_Uno;
    //信用记录_分数浮动
    private int m_CrChangeScore;
    //信用记录_变化原因
    private String m_CrChangeReason;
    //信用记录_日期(自动填写)
    private String m_CrDate;
    //信用记录_变化后分数
    private int m_CrAfterGrade;

    public CreditRecord(int m_CrNo, String m_Uno, int m_CrChangeScore, String m_CrChangeReason, String m_CrDate, int m_CrAfterGrade) {
        this.m_CrNo = m_CrNo;
        this.m_Uno = m_Uno;
        this.m_CrChangeScore = m_CrChangeScore;
        this.m_CrChangeReason = m_CrChangeReason;
        this.m_CrDate = m_CrDate;
        this.m_CrAfterGrade = m_CrAfterGrade;
    }

    public CreditRecord() {
    }

    public int getM_CrNo() {
        return m_CrNo;
    }

    public void setM_CrNo(int m_CrNo) {
        this.m_CrNo = m_CrNo;
    }

    public String getM_Uno() {
        return m_Uno;
    }

    public void setM_Uno(String m_Uno) {
        this.m_Uno = m_Uno;
    }

    public int getM_CrChangeScore() {
        return m_CrChangeScore;
    }

    public void setM_CrChangeScore(int m_CrChangeScore) {
        this.m_CrChangeScore = m_CrChangeScore;
    }

    public String getM_CrChangeReason() {
        return m_CrChangeReason;
    }

    public void setM_CrChangeReason(String m_CrChangeReason) {
        this.m_CrChangeReason = m_CrChangeReason;
    }

    public String getM_CrDate() {
        return m_CrDate;
    }

    public void setM_CrDate(String m_CrDate) {
        this.m_CrDate = m_CrDate;
    }

    public int getM_CrAfterGrade() {
        return m_CrAfterGrade;
    }

    public void setM_CrAfterGrade(int m_CrAfterGrade) {
        this.m_CrAfterGrade = m_CrAfterGrade;
    }

    @Override
    public String toString() {
        return "CreditRecord{" +
                "cr_no=" + m_CrNo +
                ", u_no='" + m_Uno + '\'' +
                ", cr_change_score=" + m_CrChangeScore +
                ", cr_change_reason='" + m_CrChangeReason + '\'' +
                ", cr_date='" + m_CrDate + '\'' +
                ", cr_after_grade=" + m_CrAfterGrade +
                '}';
    }
}
