package modle.asses;

public class SearchHolder {
    public boolean isW() {
        return w;
    }

    public boolean isS() {
        return s;
    }

    public boolean isN() {
        return n;
    }

    public boolean isA() {
        return a;
    }

    public boolean isO() {
        return o;
    }

    public boolean isC() {
        return c;
    }

    public String getWard() {
        return ward;
    }

    public String getStreet() {
        return street;
    }

    public String getNature() {
        return nature;
    }

    public String getAssessment() {
        return assessment;
    }

    public String getObsolete() {
        return obsolete;
    }

    public String getCustomer() {
        return customer;
    }

    public void setW(boolean w) {
        this.w = w;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public void setN(boolean n) {
        this.n = n;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public void setO(boolean o) {
        this.o = o;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public void setObsolete(String obsolete) {
        this.obsolete = obsolete;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public boolean w,s,n,a,o,c;
    public String ward,street,nature,assessment,obsolete,customer;

    public SearchHolder(boolean w, boolean s, boolean n, boolean a, boolean o, boolean c, String ward, String street, String nature, String assessment, String obsolete, String customer) {
        this.w = w;
        this.s = s;
        this.n = n;
        this.a = a;
        this.o = o;
        this.c = c;
        this.ward = ward;
        this.street = street;
        this.nature = nature;
        this.assessment = assessment;
        this.obsolete = obsolete;
        this.customer = customer;
    }

    public SearchHolder() {
        super();
    }
}
