package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1



/**
 * SlSabhaType generated by hbm2java
 */
public class SlSabhaType  implements java.io.Serializable {


     private Integer slSabhaTypeId;
     private ApplicationCatagory applicationCatagory;
     private SlLaType slLaType;

    public SlSabhaType() {
    }

    public SlSabhaType(ApplicationCatagory applicationCatagory, SlLaType slLaType) {
       this.applicationCatagory = applicationCatagory;
       this.slLaType = slLaType;
    }
   
    public Integer getSlSabhaTypeId() {
        return this.slSabhaTypeId;
    }
    
    public void setSlSabhaTypeId(Integer slSabhaTypeId) {
        this.slSabhaTypeId = slSabhaTypeId;
    }
    public ApplicationCatagory getApplicationCatagory() {
        return this.applicationCatagory;
    }
    
    public void setApplicationCatagory(ApplicationCatagory applicationCatagory) {
        this.applicationCatagory = applicationCatagory;
    }
    public SlLaType getSlLaType() {
        return this.slLaType;
    }
    
    public void setSlLaType(SlLaType slLaType) {
        this.slLaType = slLaType;
    }




}


