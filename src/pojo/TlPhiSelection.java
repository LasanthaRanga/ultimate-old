package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * TlPhiSelection generated by hbm2java
 */
public class TlPhiSelection  implements java.io.Serializable {


     private Integer tlPhiSelectionId;
     private String tlPhiSelectionDetails;
     private Integer tlPhiSelectionSubnatureId;

    public TlPhiSelection() {
    }

    public TlPhiSelection(String tlPhiSelectionDetails, Integer tlPhiSelectionSubnatureId) {
       this.tlPhiSelectionDetails = tlPhiSelectionDetails;
       this.tlPhiSelectionSubnatureId = tlPhiSelectionSubnatureId;
    }
   
    public Integer getTlPhiSelectionId() {
        return this.tlPhiSelectionId;
    }
    
    public void setTlPhiSelectionId(Integer tlPhiSelectionId) {
        this.tlPhiSelectionId = tlPhiSelectionId;
    }
    public String getTlPhiSelectionDetails() {
        return this.tlPhiSelectionDetails;
    }
    
    public void setTlPhiSelectionDetails(String tlPhiSelectionDetails) {
        this.tlPhiSelectionDetails = tlPhiSelectionDetails;
    }
    public Integer getTlPhiSelectionSubnatureId() {
        return this.tlPhiSelectionSubnatureId;
    }
    
    public void setTlPhiSelectionSubnatureId(Integer tlPhiSelectionSubnatureId) {
        this.tlPhiSelectionSubnatureId = tlPhiSelectionSubnatureId;
    }




}


