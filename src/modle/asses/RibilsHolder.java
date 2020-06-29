package modle.asses;

public class RibilsHolder {

    int id;
    String riciptNo;
    double total;
    String paymethod;
    int payby;
    String chequeNo;
    String assess;



    public RibilsHolder(int id, String riciptNo, double total, String paymethod, int payby, String chequeNo, String assess) {
        this.id = id;
        this.riciptNo = riciptNo;
        this.total = total;
        this.paymethod = paymethod;
        this.payby = payby;
        this.chequeNo = chequeNo;
        this.assess = assess;
    }

    public int getId() {
        return id;
    }

    public String getRiciptNo() {
        return riciptNo;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public int getPayby() {
        return payby;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRiciptNo(String riciptNo) {
        this.riciptNo = riciptNo;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public void setPayby(int payby) {
        this.payby = payby;
    }

    public void setChequeNo(String chequeNo) { this.chequeNo = chequeNo; }

    public String getChequeNo() {return chequeNo;}

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getAssess() {
        return assess;
    }
}
