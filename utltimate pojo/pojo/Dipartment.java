package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Dipartment generated by hbm2java
 */
public class Dipartment  implements java.io.Serializable {


     private Integer idDipartment;
     private String dipName;
     private String dipHead;
     private Integer dipStatus;
     private Integer dipSyn;
     private Set<UserHasDipartment> userHasDipartments = new HashSet<UserHasDipartment>(0);

    public Dipartment() {
    }

    public Dipartment(String dipName, String dipHead, Integer dipStatus, Integer dipSyn, Set<UserHasDipartment> userHasDipartments) {
       this.dipName = dipName;
       this.dipHead = dipHead;
       this.dipStatus = dipStatus;
       this.dipSyn = dipSyn;
       this.userHasDipartments = userHasDipartments;
    }
   
    public Integer getIdDipartment() {
        return this.idDipartment;
    }
    
    public void setIdDipartment(Integer idDipartment) {
        this.idDipartment = idDipartment;
    }
    public String getDipName() {
        return this.dipName;
    }
    
    public void setDipName(String dipName) {
        this.dipName = dipName;
    }
    public String getDipHead() {
        return this.dipHead;
    }
    
    public void setDipHead(String dipHead) {
        this.dipHead = dipHead;
    }
    public Integer getDipStatus() {
        return this.dipStatus;
    }
    
    public void setDipStatus(Integer dipStatus) {
        this.dipStatus = dipStatus;
    }
    public Integer getDipSyn() {
        return this.dipSyn;
    }
    
    public void setDipSyn(Integer dipSyn) {
        this.dipSyn = dipSyn;
    }
    public Set<UserHasDipartment> getUserHasDipartments() {
        return this.userHasDipartments;
    }
    
    public void setUserHasDipartments(Set<UserHasDipartment> userHasDipartments) {
        this.userHasDipartments = userHasDipartments;
    }




}


