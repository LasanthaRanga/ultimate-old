package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * Areacal generated by hbm2java
 */
public class Areacal  implements java.io.Serializable {


     private Integer idAreacal;
     private Double min;
     private Double max;
     private Double price;
     private Integer status;

    public Areacal() {
    }

    public Areacal(Double min, Double max, Double price, Integer status) {
       this.min = min;
       this.max = max;
       this.price = price;
       this.status = status;
    }
   
    public Integer getIdAreacal() {
        return this.idAreacal;
    }
    
    public void setIdAreacal(Integer idAreacal) {
        this.idAreacal = idAreacal;
    }
    public Double getMin() {
        return this.min;
    }
    
    public void setMin(Double min) {
        this.min = min;
    }
    public Double getMax() {
        return this.max;
    }
    
    public void setMax(Double max) {
        this.max = max;
    }
    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }




}


