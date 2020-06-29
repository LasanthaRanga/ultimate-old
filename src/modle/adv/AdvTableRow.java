/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modle.adv;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class AdvTableRow {

    private int rowId;
    private int side;
    private SimpleStringProperty location;
    private SimpleStringProperty boardType;
    private double unitPrice;
    private double width;
    private double height;
    private double qty;
    private double sqfeet;
    private double totSq;
    private double totPrice;
    private double groundRent;

    /**
     * @return the rowId
     */
    public int getRowId() {
        return rowId;
    }

    /**
     * @return the side
     */
    public int getSide() {
        return side;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location.get();
    }

    /**
     * @return the boardType
     */
    public String getBoardType() {
        return boardType.get();
    }

    /**
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @return the sqfeet
     */
    public double getSqfeet() {
        return sqfeet;
    }

    /**
     * @return the totSq
     */
    public double getTotSq() {
        return totSq;
    }

    /**
     * @return the totPrice
     */
    public double getTotPrice() {
        return totPrice;
    }

    public AdvTableRow(int rowId, int side, String location, String boardType, double unitPrice, double width, double height, double qty, double sqfeet, double totSq, double totPrice, double  groundRent) {
        this.rowId = rowId;
        this.side = side;
        this.location = new SimpleStringProperty(location);
        this.boardType = new SimpleStringProperty(boardType);
        this.unitPrice = unitPrice;
        this.width = width;
        this.height = height;
        this.qty = qty;
        this.sqfeet = sqfeet;
        this.totSq = totSq;
        this.totPrice = totPrice;
        this.groundRent = groundRent;
    }

    /**
     * @return the groundRent
     */
    public double getGroundRent() {
        return groundRent;
    }

}
