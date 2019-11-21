package bean;

/*
 * 信用规则表
 */
public class CreditRule {

    //信用规则_编号
    private int m_CrNo;
    //信用规则_内容
    private String m_CrContent;
    //信用规则_加减分
    private int m_CrScore;


    public CreditRule(int m_CrNo, String m_CrContent, int m_CrScore) {
        this.m_CrNo = m_CrNo;
        this.m_CrContent = m_CrContent;
        this.m_CrScore = m_CrScore;
    }

    @Override
    public String toString() {
        return "CreditRule{" +
                "cr_no=" + m_CrNo +
                ", ce_content='" + m_CrContent + '\'' +
                ", cr_score=" + m_CrScore +
                '}';
    }

    public CreditRule() {
    }

    public int getM_CrNo() {
        return m_CrNo;
    }

    public void setM_CrNo(int m_CrNo) {
        this.m_CrNo = m_CrNo;
    }

    public String getM_CrContent() {
        return m_CrContent;
    }

    public void setM_CrContent(String m_CrContent) {
        this.m_CrContent = m_CrContent;
    }

    public int getM_CrScore() {
        return m_CrScore;
    }

    public void setM_CrScore(int m_CrScore) {
        this.m_CrScore = m_CrScore;
    }
}
