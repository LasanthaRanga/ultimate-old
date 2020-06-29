package modle.asses;

public class CDholder {

    int idcd;
    int idAssessment;
    String day;
    String user;
    String ward;
    String street;
    String assessment;
    double credit;
    double debit;
    String description;
    String other;
    int xx;

    public CDholder(int idcd, int idAssessment, String day, String user, String ward, String street, String assessment, double credit, double debit, String description, String other, int xx) {
        this.idcd = idcd;
        this.idAssessment = idAssessment;
        this.day = day;
        this.user = user;
        this.ward = ward;
        this.street = street;
        this.assessment = assessment;
        this.credit = credit;
        this.debit = debit;
        this.description = description;
        this.other = other;
        this.xx = xx;
    }

    public int getIdcd() {
        return idcd;
    }

    public int getIdAssessment() {
        return idAssessment;
    }

    public String getDay() {
        return day;
    }

    public String getUser() {
        return user;
    }

    public String getWard() {
        return ward;
    }

    public String getStreet() {
        return street;
    }

    public String getAssessment() {
        return assessment;
    }

    public double getCredit() {
        return credit;
    }

    public double getDebit() {
        return debit;
    }

    public String getDescription() {
        return description;
    }

    public String getOther() {
        return other;
    }

    public int getXx() {
        return xx;
    }
}
