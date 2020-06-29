package modle.asses;


import javafx.beans.property.SimpleStringProperty;

public class TablePay {

    /**
     * @return the amounts
     */
    public String getAmounts() {
        return amounts.get();
    }

    /**
     * @return the disconts
     */
    public String getDisconts() {
        return disconts.get();
    }

    /**
     * @return the values
     */
    public String getValues() {
        return values.get();
    }

    /**
     * @return the discription
     */
    public String getDiscription() {
        return discription.get();
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return the discont
     */
    public double getDiscont() {
        return discont;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }
    final private SimpleStringProperty discription;
    final private double amount;
    final private double discont;
    final private double value;

    final private SimpleStringProperty amounts;
    final private SimpleStringProperty disconts;
    final private SimpleStringProperty values;

    public TablePay(String discription, double amount, double discont, double value) {
        this.discription = new SimpleStringProperty(discription);
        this.amount = amount;
        this.discont = discont;
        this.value = value;

        this.amounts = new SimpleStringProperty(modle.Maths.rondAnd2String(amount));
        this.disconts = new SimpleStringProperty(modle.Maths.rondAnd2String(discont));
        this.values = new SimpleStringProperty(modle.Maths.rondAnd2String(value));

    }

}
