package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * Userhasdivition generated by hbm2java
 */
public class Userhasdivition  implements java.io.Serializable {


     private Integer idUserHasDivition;
     private Gnd gnd;
     private User user;
     private Integer userHasDivitionStatus;

    public Userhasdivition() {
    }

    public Userhasdivition(Gnd gnd, User user, Integer userHasDivitionStatus) {
       this.gnd = gnd;
       this.user = user;
       this.userHasDivitionStatus = userHasDivitionStatus;
    }
   
    public Integer getIdUserHasDivition() {
        return this.idUserHasDivition;
    }
    
    public void setIdUserHasDivition(Integer idUserHasDivition) {
        this.idUserHasDivition = idUserHasDivition;
    }
    public Gnd getGnd() {
        return this.gnd;
    }
    
    public void setGnd(Gnd gnd) {
        this.gnd = gnd;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Integer getUserHasDivitionStatus() {
        return this.userHasDivitionStatus;
    }
    
    public void setUserHasDivitionStatus(Integer userHasDivitionStatus) {
        this.userHasDivitionStatus = userHasDivitionStatus;
    }




}


