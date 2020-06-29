package modle.asses;

public class RibHolder {

    private int id;
    private String billno;
    private double total;
    private int status;
    private String complete;
    private int count;


    public RibHolder(int id, String billno, double total, int status, String complete, int count) {
        this.id = id;
        this.billno = billno;
        this.total = total;
        this.status = status;
        this.complete = complete;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getBillno() {
        return billno;
    }

    public double getTotal() {
        return total;
    }

    public int getStatus() {
        return status;
    }

    public String getComplete() {
        return complete;
    }

    public int getCount() {
        return count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
