
package modle.asses;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class HolderAssess {

    /**
     * @return the idAssess
     */
    public int getIdAssess() {
        return idAssess;
    }

    /**
     * @return the order
     */
    public double getOrder() {
        return order;
    }

    /**
     * @return the Natrue
     */
    public String getNatrue() {
        return Natrue.get();
    }

    /**
     * @return the ward
     */
    public String getWard() {
        return ward.get();
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street.get();
    }

    /**
     * @return the assessmentNo
     */
    public String getAssessmentNo() {
        return assessmentNo.get();
    }

    /**
     * @return the osaleteNo
     */
    public String getOsaleteNo() {
        return osaleteNo.get();
    }

    /**
     * @return the alocation
     */
    public Double getAlocation() {
        return alocation;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner.get();
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status.get();
    }

    private final int idAssess;
    private final double order;
    private final SimpleStringProperty Natrue;
    private final SimpleStringProperty ward;
    private final SimpleStringProperty street;
    private final SimpleStringProperty assessmentNo;
    private final SimpleStringProperty osaleteNo;
    private final Double alocation;
    private final SimpleStringProperty owner;
    private final SimpleStringProperty status;

    public HolderAssess(int idAssess, double order, String Natrue, String ward, String street, String assessmentNo, String osaleteNo, double alocation, String owner, String status) {
        this.idAssess = idAssess;
        this.order = order;
        this.Natrue = new SimpleStringProperty(Natrue);
        this.ward = new SimpleStringProperty(ward);
        this.street = new SimpleStringProperty(street);
        this.assessmentNo = new SimpleStringProperty(assessmentNo);
        this.osaleteNo = new SimpleStringProperty(osaleteNo);
        this.alocation = alocation;
        this.owner = new SimpleStringProperty(owner);
        this.status = new SimpleStringProperty(status);
        this.ch = new JFXCheckBox();


    }


    private JFXCheckBox ch;

    public void setCh(JFXCheckBox ch) {
        this.ch = ch;
    }

    public JFXCheckBox getCh() {
        return ch;
    }


    public void setSelected(boolean b) {
        this.ch.setSelected(b);
    }

    public boolean isSelected() {
        return this.ch.isSelected();
    }


}
