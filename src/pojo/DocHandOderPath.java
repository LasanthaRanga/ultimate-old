package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1



/**
 * DocHandOderPath generated by hbm2java
 */
public class DocHandOderPath  implements java.io.Serializable {


     private Integer oderPathId;
     private ApplicationCatagory applicationCatagory;
     private String oderPathForm;
     private Integer priority;
     private String oderPathName;

    public DocHandOderPath() {
    }

    public DocHandOderPath(ApplicationCatagory applicationCatagory, String oderPathForm, Integer priority, String oderPathName) {
       this.applicationCatagory = applicationCatagory;
       this.oderPathForm = oderPathForm;
       this.priority = priority;
       this.oderPathName = oderPathName;
    }
   
    public Integer getOderPathId() {
        return this.oderPathId;
    }
    
    public void setOderPathId(Integer oderPathId) {
        this.oderPathId = oderPathId;
    }
    public ApplicationCatagory getApplicationCatagory() {
        return this.applicationCatagory;
    }
    
    public void setApplicationCatagory(ApplicationCatagory applicationCatagory) {
        this.applicationCatagory = applicationCatagory;
    }
    public String getOderPathForm() {
        return this.oderPathForm;
    }
    
    public void setOderPathForm(String oderPathForm) {
        this.oderPathForm = oderPathForm;
    }
    public Integer getPriority() {
        return this.priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public String getOderPathName() {
        return this.oderPathName;
    }
    
    public void setOderPathName(String oderPathName) {
        this.oderPathName = oderPathName;
    }




}


