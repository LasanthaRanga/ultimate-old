package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * SlYesNoDetails generated by hbm2java
 */
public class SlYesNoDetails  implements java.io.Serializable {


     private Integer yesNoStatusId;
     private String yesNoStatusName;

    public SlYesNoDetails() {
    }

    public SlYesNoDetails(String yesNoStatusName) {
       this.yesNoStatusName = yesNoStatusName;
    }
   
    public Integer getYesNoStatusId() {
        return this.yesNoStatusId;
    }
    
    public void setYesNoStatusId(Integer yesNoStatusId) {
        this.yesNoStatusId = yesNoStatusId;
    }
    public String getYesNoStatusName() {
        return this.yesNoStatusName;
    }
    
    public void setYesNoStatusName(String yesNoStatusName) {
        this.yesNoStatusName = yesNoStatusName;
    }




}


