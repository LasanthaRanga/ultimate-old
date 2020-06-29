/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.asses;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class TableAsses {

    final private int idAsses;
    final private SimpleStringProperty bookNo;
    final private SimpleStringProperty assesNo;
    final private SimpleStringProperty cus;
    final private SimpleStringProperty alocation;
    final private SimpleStringProperty status;

    public TableAsses(int idAsses, String bookNo, String assesNo, String cus, String alocation, String status) {
        this.idAsses = idAsses;
        this.bookNo = new SimpleStringProperty(bookNo);
        this.assesNo = new SimpleStringProperty(assesNo);
        this.cus = new SimpleStringProperty(cus);
        this.alocation = new SimpleStringProperty(alocation);
        this.status = new SimpleStringProperty(status);
    }

    /**
     * @return the idAsses
     */
    public int getIdAsses() {
        return idAsses;
    }

    /**
     * @return the bookNo
     */
    public String getBookNo() {
        return bookNo.get();
    }

    /**
     * @return the assesNo
     */
    public String getAssesNo() {
        return assesNo.get();
    }

    /**
     * @return the cus
     */
    public String getCus() {
        return cus.get();
    }

    /**
     * @return the alocation
     */
    public String getAlocation() {
        return alocation.get();
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status.get();
    }

}
