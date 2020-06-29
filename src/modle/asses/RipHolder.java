package modle.asses;

public class RipHolder {

    private int idAssessment;
    private int idWard;

    private String streetName;
    private String assessNo;
    private String customerName;

    private Double LYA = 0.0;
    private Double LYW = 0.0;
    private Double LYAP = 0.0;
    private Double LYCA = 0.0;
    private Double LYCW = 0.0;
    private Double LYWP = 0.0;

    private Double Q1A = 0.0;
    private Double Q1W = 0.0;
    private Double Q1CA = 0.0;
    private Double Q1CW = 0.0;
    private Double Q1DR = 0.0;
    private Double Q1DP = 0.0;
    private Double Q1P = 0.0;

    private Double Q2A = 0.0;
    private Double Q2W = 0.0;
    private Double Q2CA = 0.0;
    private Double Q2CW = 0.0;
    private Double Q2DR = 0.0;
    private Double Q2DP = 0.0;
    private Double Q2P = 0.0;

    private Double Q3A = 0.0;
    private Double Q3W = 0.0;
    private Double Q3CA = 0.0;
    private Double Q3CW = 0.0;
    private Double Q3DR = 0.0;
    private Double Q3DP = 0.0;
    private Double Q3P = 0.0;

    private Double Q4A = 0.0;
    private Double Q4W = 0.0;
    private Double Q4CA = 0.0;
    private Double Q4CW = 0.0;
    private Double Q4DR = 0.0;
    private Double Q4DP = 0.0;
    private Double Q4P = 0.0;

    private Double debit = 0.0; // +
    private Double credit = 0.0; // -

    private Double cd = 0.0; // credit debit total + -


    private int idPayment;
    private int idRecipt;
    private String reciptNo;

    private Double overPay = 0.0;
    private Double tyFullPay = 0.0;
    private Double fullTotalPay = 0.0;

    private String chequeNo;


    private Double cash = 0.0;
    private Double cheque = 0.0;

    private Double quaterVal = 0.0;

    private int cq = 0;
    private int cy = 0;

    public void setCy(int cy) {
        this.cy = cy;
    }

    public int getCy() {
        return cy;
    }

    public int getCq() {
        return cq;
    }

    public void setCq(int cq) {
        this.cq = cq;
    }

    public void setQuaterVal(Double quaterVal) {
        this.quaterVal = quaterVal;
    }

    public Double getQuaterVal() {
        return quaterVal;
    }

    public Double getThisYearCurrentArrias() {
        return thisYearCurrentArrias;
    }

    public Double getThisYearCurrentWarrant() {
        return thisYearCurrentWarrant;
    }

    public void setThisYearCurrentArrias(Double thisYearCurrentArrias) {
        this.thisYearCurrentArrias = thisYearCurrentArrias;
    }

    public void setThisYearCurrentWarrant(Double thisYearCurrentWarrant) {
        this.thisYearCurrentWarrant = thisYearCurrentWarrant;
    }

    private Double thisYearCurrentArrias = 0.0;
    private Double thisYearCurrentWarrant = 0.0;


    public void setCashCheque(double cash, double cheque) {
        this.cash = cash;
        this.cheque = cheque;
    }

    public double getCash() {
        return cash;
    }

    public double getCheque() {
        return cheque;
    }


    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeNo() {
        return chequeNo;
    }


    public void setAssessData(int idAssessment, int idWard, String streetName, String customerName) {
        this.idAssessment = idAssessment;
        this.idWard = idWard;
        this.streetName = streetName;
        this.customerName = customerName;
    }

    public void setLasatYearArriars(double LYA, double LYCA, double LYAP) {
        this.LYA = LYA;
        this.LYCA = LYCA;
        this.LYAP = LYAP;
    }

    public void setLastYearWarrant(double LYW, double LYCA, double LYWP) {
        this.LYW = LYW;
        this.LYCW = LYCA;
        this.LYWP = LYWP;
    }

    public void setQ1(double q1A, double q1W, double q1CA, double q1CW, double q1DR, double q1DP, double q1P) {
        this.Q1A = q1A;
        this.Q1W = q1W;
        this.Q1CA = q1CA;
        this.Q1CW = q1CW;
        this.Q1DR = q1DR;
        this.Q1DP = q1DP;
        this.Q1P = q1P;
    }

    public void setQ2(double q2A, double q2W, double q2CA, double q2CW, double q2DR, double q2DP, double q2P) {
        this.Q2A = q2A;
        this.Q2W = q2W;
        this.Q2CA = q2CA;
        this.Q2CW = q2CW;
        this.Q2DR = q2DR;
        this.Q2DP = q2DP;
        this.Q2P = q2P;
    }

    public void setQ3(double q3A, double q3W, double q3CA, double q3CW, double q3DR, double q3DP, double q3P) {
        this.Q3A = q3A;
        this.Q3W = q3W;
        this.Q3CA = q3CA;
        this.Q3CW = q3CW;
        this.Q3DR = q3DR;
        this.Q3DP = q3DP;
        this.Q3P = q3P;
    }


    public void setQ4(double q4A, double q4W, double q4CA, double q4CW, double q4DR, double q4DP, double q4P) {
        this.Q4A = q4A;
        this.Q4W = q4W;
        this.Q4CA = q4CA;
        this.Q4CW = q4CW;
        this.Q4DR = q4DR;
        this.Q4DP = q4DP;
        this.Q4P = q4P;
    }


    public int getIdAssessment() {
        return idAssessment;
    }

    public void setIdAssessment(int idAssessment) {
        this.idAssessment = idAssessment;
    }

    public int getIdWard() {
        return idWard;
    }

    public void setIdWard(int idWard) {
        this.idWard = idWard;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAssessNo() {
        return assessNo;
    }

    public void setAssessNo(String assessNo) {
        this.assessNo = assessNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getLYA() {
        return LYA;
    }

    public void setLYA(Double LYA) {
        this.LYA = LYA;
    }

    public Double getLYW() {
        return LYW;
    }

    public void setLYW(Double LYW) {
        this.LYW = LYW;
    }

    public Double getLYAP() {
        return LYAP;
    }

    public void setLYAP(Double LYAP) {
        this.LYAP = LYAP;
    }

    public Double getLYCA() {
        return LYCA;
    }

    public void setLYCA(Double LYCA) {
        this.LYCA = LYCA;
    }

    public Double getLYCW() {
        return LYCW;
    }

    public void setLYCW(Double LYCW) {
        this.LYCW = LYCW;
    }

    public Double getLYWP() {
        return LYWP;
    }

    public void setLYWP(Double LYWP) {
        this.LYWP = LYWP;
    }

    public Double getQ1A() {
        return Q1A;
    }

    public void setQ1A(Double q1A) {
        Q1A = q1A;
    }

    public Double getQ1W() {
        return Q1W;
    }

    public void setQ1W(Double q1W) {
        Q1W = q1W;
    }

    public Double getQ1CA() {
        return Q1CA;
    }

    public void setQ1CA(Double q1CA) {
        Q1CA = q1CA;
    }

    public Double getQ1CW() {
        return Q1CW;
    }

    public void setQ1CW(Double q1CW) {
        Q1CW = q1CW;
    }

    public Double getQ1DR() {
        return Q1DR;
    }

    public void setQ1DR(Double q1DR) {
        Q1DR = q1DR;
    }

    public Double getQ1DP() {
        return Q1DP;
    }

    public void setQ1DP(Double q1DP) {
        Q1DP = q1DP;
    }

    public Double getQ1P() {
        return Q1P;
    }

    public void setQ1P(Double q1P) {
        Q1P = q1P;
    }

    public Double getQ2A() {
        return Q2A;
    }

    public void setQ2A(Double q2A) {
        Q2A = q2A;
    }

    public Double getQ2W() {
        return Q2W;
    }

    public void setQ2W(Double q2W) {
        Q2W = q2W;
    }

    public Double getQ2CA() {
        return Q2CA;
    }

    public void setQ2CA(Double q2CA) {
        Q2CA = q2CA;
    }

    public Double getQ2CW() {
        return Q2CW;
    }

    public void setQ2CW(Double q2CW) {
        Q2CW = q2CW;
    }

    public Double getQ2DR() {
        return Q2DR;
    }

    public void setQ2DR(Double q2DR) {
        Q2DR = q2DR;
    }

    public Double getQ2DP() {
        return Q2DP;
    }

    public void setQ2DP(Double q2DP) {
        Q2DP = q2DP;
    }

    public Double getQ2P() {
        return Q2P;
    }

    public void setQ2P(Double q2P) {
        Q2P = q2P;
    }

    public Double getQ3A() {
        return Q3A;
    }

    public void setQ3A(Double q3A) {
        Q3A = q3A;
    }

    public Double getQ3W() {
        return Q3W;
    }

    public void setQ3W(Double q3W) {
        Q3W = q3W;
    }

    public Double getQ3CA() {
        return Q3CA;
    }

    public void setQ3CA(Double q3CA) {
        Q3CA = q3CA;
    }

    public Double getQ3CW() {
        return Q3CW;
    }

    public void setQ3CW(Double q3CW) {
        Q3CW = q3CW;
    }

    public Double getQ3DR() {
        return Q3DR;
    }

    public void setQ3DR(Double q3DR) {
        Q3DR = q3DR;
    }

    public Double getQ3DP() {
        return Q3DP;
    }

    public void setQ3DP(Double q3DP) {
        Q3DP = q3DP;
    }

    public Double getQ3P() {
        return Q3P;
    }

    public void setQ3P(Double q3P) {
        Q3P = q3P;
    }

    public Double getQ4A() {
        return Q4A;
    }

    public void setQ4A(Double q4A) {
        Q4A = q4A;
    }

    public Double getQ4W() {
        return Q4W;
    }

    public void setQ4W(Double q4W) {
        Q4W = q4W;
    }

    public Double getQ4CA() {
        return Q4CA;
    }

    public void setQ4CA(Double q4CA) {
        Q4CA = q4CA;
    }

    public Double getQ4CW() {
        return Q4CW;
    }

    public void setQ4CW(Double q4CW) {
        Q4CW = q4CW;
    }

    public Double getQ4DR() {
        return Q4DR;
    }

    public void setQ4DR(Double q4DR) {
        Q4DR = q4DR;
    }

    public Double getQ4DP() {
        return Q4DP;
    }

    public void setQ4DP(Double q4DP) {
        Q4DP = q4DP;
    }

    public Double getQ4P() {
        return Q4P;
    }

    public void setQ4P(Double q4P) {
        Q4P = q4P;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        credit = credit;
    }

    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public int getIdRecipt() {
        return idRecipt;
    }

    public void setIdRecipt(int idRecipt) {
        this.idRecipt = idRecipt;
    }

    public String getReciptNo() {
        return reciptNo;
    }

    public void setReciptNo(String reciptNo) {
        this.reciptNo = reciptNo;
    }

    public Double getOverPay() {
        return overPay;
    }

    public void setOverPay(Double overPay) {
        this.overPay = overPay;
    }

    public Double getTyFullPay() {
        return tyFullPay;
    }

    public void setTyFullPay(Double tyFullPay) {
        this.tyFullPay = tyFullPay;
    }

    public Double getFullTotalPay() {
        return fullTotalPay;
    }

    public void setFullTotalPay(Double fullTotalPay) {
        this.fullTotalPay = fullTotalPay;
    }

    public void setCd(Double cd) {
        this.cd = cd;
    }

    public Double getCd() {
        return cd;
    }


}
