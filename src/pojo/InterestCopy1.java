package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * InterestCopy1 generated by hbm2java
 */
public class InterestCopy1  implements java.io.Serializable {


     private Integer idInterest;
     private String name;
     private Double rate;
     private Integer statues;

    public InterestCopy1() {
    }

    public InterestCopy1(String name, Double rate, Integer statues) {
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


