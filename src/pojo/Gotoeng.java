package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * Gotoeng generated by hbm2java
 */
public class Gotoeng  implements java.io.Serializable {


     private Integer idGotoeng;
     private Integer natureCat;

    public Gotoeng() {
    }

    public Gotoeng(Integer natureCat) {
       this.natureCat = natureCat;
    }
   
    public Integer getIdGotoeng() {
        return this.idGotoeng;
    }
    
    public void setIdGotoeng(Integer idGotoeng) {
        this.idGotoeng = idGotoeng;
    }
    public Integer getNatureCat() {
        return this.natureCat;
    }
    
    public void setNatureCat(Integer natureCat) {
        this.natureCat = natureCat;
    }




}


