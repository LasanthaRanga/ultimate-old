package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1



/**
 * InterestCopy generated by hbm2java
 */
public class InterestCopy  implements java.io.Serializable {


     private Integer idInterest;
     private String name;
     private Double rate;
     private Integer statues;

    public InterestCopy() {
    }

    public InterestCopy(String name, Double rate, Integer statues) {
       this.name = name;
       this.rate = rate;
       this.statues = statues;
    }
   
    public Integer getIdInterest() {
        return this.idInterest;
    }
    
    public void setIdInterest(Integer idInterest) {
        this.idInterest = idInterest;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Double getRate() {
        return this.rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
    }
    public Integer getStatues() {
        return this.statues;
    }
    
    public void setStatues(Integer statues) {
        this.statues = statues;
    }




}


