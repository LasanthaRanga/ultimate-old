package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Ward generated by hbm2java
 */
public class Ward  implements java.io.Serializable {


     private Integer idWard;
     private String wardNo;
     private String wardName;
     private Integer wardStatus;
     private Integer wardSyn;
     private String wardComment;
     private String wardCode;
     private Integer officeIdOffice;
     private Set<StreetCopy> streetCopies = new HashSet<StreetCopy>(0);
     private Set<Street> streets = new HashSet<Street>(0);
     private Set<Assessment> assessments = new HashSet<Assessment>(0);

    public Ward() {
    }

    public Ward(String wardNo, String wardName, Integer wardStatus, Integer wardSyn, String wardComment, String wardCode, Integer officeIdOffice, Set<StreetCopy> streetCopies, Set<Street> streets, Set<Assessment> assessments) {
       this.wardNo = wardNo;
       this.wardName = wardName;
       this.wardStatus = wardStatus;
       this.wardSyn = wardSyn;
       this.wardComment = wardComment;
       this.wardCode = wardCode;
       this.officeIdOffice = officeIdOffice;
       this.streetCopies = streetCopies;
       this.streets = streets;
       this.assessments = assessments;
    }
   
    public Integer getIdWard() {
        return this.idWard;
    }
    
    public void setIdWard(Integer idWard) {
        this.idWard = idWard;
    }
    public String getWardNo() {
        return this.wardNo;
    }
    
    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }
    public String getWardName() {
        return this.wardName;
    }
    
    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
    public Integer getWardStatus() {
        return this.wardStatus;
    }
    
    public void setWardStatus(Integer wardStatus) {
        this.wardStatus = wardStatus;
    }
    public Integer getWardSyn() {
        return this.wardSyn;
    }
    
    public void setWardSyn(Integer wardSyn) {
        this.wardSyn = wardSyn;
    }
    public String getWardComment() {
        return this.wardComment;
    }
    
    public void setWardComment(String wardComment) {
        this.wardComment = wardComment;
    }
    public String getWardCode() {
        return this.wardCode;
    }
    
    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }
    public Integer getOfficeIdOffice() {
        return this.officeIdOffice;
    }
    
    public void setOfficeIdOffice(Integer officeIdOffice) {
        this.officeIdOffice = officeIdOffice;
    }
    public Set<StreetCopy> getStreetCopies() {
        return this.streetCopies;
    }
    
    public void setStreetCopies(Set<StreetCopy> streetCopies) {
        this.streetCopies = streetCopies;
    }
    public Set<Street> getStreets() {
        return this.streets;
    }
    
    public void setStreets(Set<Street> streets) {
        this.streets = streets;
    }
    public Set<Assessment> getAssessments() {
        return this.assessments;
    }
    
    public void setAssessments(Set<Assessment> assessments) {
        this.assessments = assessments;
    }




}


