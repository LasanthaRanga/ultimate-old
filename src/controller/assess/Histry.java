package controller.assess;

import conn.DB;

/**
 * Created by Ranga Rathnayake on 2020-12-31.
 */
public class Histry {

    int idAssessment;
    double lya;
    double lyw;
    double over;
    double debit;
    double credit;
    double lqa;
    double lqw;
    double quaterAmount;
    double allocation;
    double yearRate;
    double warantRate;
    int year;
    int quater;
    int date;
    String cus_name;
    String cus_id;
    String wardName;
    String streetName;
    String assessmentNo;

    public Histry() {
    }

    public Histry(int idAssessment, double lya, double lyw, double over, double debit, double credit, double lqa, double lqw, double quaterAmount, double allocation, double yearRate, double warantRate, int year, int quater, int date, String cus_name, String cus_id, String wardName, String streetName, String assessmentNo) {
        this.idAssessment = idAssessment;
        this.lya = lya;
        this.lyw = lyw;
        this.over = over;
        this.debit = debit;
        this.credit = credit;
        this.lqa = lqa;
        this.lqw = lqw;
        this.quaterAmount = quaterAmount;
        this.allocation = allocation;
        this.yearRate = yearRate;
        this.warantRate = warantRate;
        this.year = year;
        this.quater = quater;
        this.date = date;
        this.cus_name = cus_name;
        this.cus_id = cus_id;
        this.wardName = wardName;
        this.streetName = streetName;
        this.assessmentNo = assessmentNo;
    }


    public int getIdAssessment() {
        return idAssessment;
    }

    public double getLya() {
        return lya;
    }

    public double getLyw() {
        return lyw;
    }

    public double getOver() {
        return over;
    }

    public double getDebit() {
        return debit;
    }

    public double getCredit() {
        return credit;
    }

    public double getLqa() {
        return lqa;
    }

    public double getLqw() {
        return lqw;
    }

    public double getQuaterAmount() {
        return quaterAmount;
    }

    public double getAllocation() {
        return allocation;
    }

    public double getYearRate() {
        return yearRate;
    }

    public double getWarantRate() {
        return warantRate;
    }

    public int getYear() {
        return year;
    }

    public int getQuater() {
        return quater;
    }

    public int getDate() {
        return date;
    }

    public String getCus_name() {
        return cus_name;
    }

    public String getCus_id() {
        return cus_id;
    }

    public String getWardName() {
        return wardName;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getAssessmentNo() {
        return assessmentNo;
    }


    public void setIdAssessment(int idAssessment) {
        this.idAssessment = idAssessment;
    }

    public void setLya(double lya) {
        this.lya = lya;
    }

    public void setLyw(double lyw) {
        this.lyw = lyw;
    }

    public void setOver(double over) {
        this.over = over;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setLqa(double lqa) {
        this.lqa = lqa;
    }

    public void setLqw(double lqw) {
        this.lqw = lqw;
    }

    public void setQuaterAmount(double quaterAmount) {
        this.quaterAmount = quaterAmount;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public void setWarantRate(double warantRate) {
        this.warantRate = warantRate;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setQuater(int quater) {
        this.quater = quater;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setAssessmentNo(String assessmentNo) {
        this.assessmentNo = assessmentNo;
    }


    public void save() {
        try {
            int i = DB.setData("INSERT INTO `assessmenthistry` (`idAssessment`,`lya`,`lyw`,`over`,`debit`,`credit`,`lqa`,`lqw`,`quaterAmount`,`allocation`,`yearRate`,`warantRate`,`year`,`quater`,`date`,`cus_name`,`cus_id`,`wardName`,`streetName`,`assessmentNo`)" +
                    " VALUES (" + this.idAssessment + "," + this.lya + "," + this.lyw + "," + this.over + "," + this.debit + "," + this.credit + "," + this.lqa + "," + this.lqw + "," + this.quaterAmount + "," + this.allocation + "," + this.yearRate + "," + this.warantRate + "," + this.year + "," + this.quater + "," + this.date + ",'" + this.cus_name + "','" + this.cus_id + "','" + this.wardName + "','" + this.streetName + "','" + this.assessmentNo + "')");
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
