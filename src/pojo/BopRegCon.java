package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * BopRegCon generated by hbm2java
 */
public class BopRegCon  implements java.io.Serializable {


     private Integer id;
     private Bophascommettee bophascommettee;
     private String val;
     private String type;

    public BopRegCon() {
    }

    public BopRegCon(Bophascommettee bophascommettee, String val, String type) {
       this.bophascommettee = bophascommettee;
       this.val = val;
       this.type = type;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Bophascommettee getBophascommettee() {
        return this.bophascommettee;
    }
    
    public void setBophascommettee(Bophascommettee bophascommettee) {
        this.bophascommettee = bophascommettee;
    }
    public String getVal() {
        return this.val;
    }
    
    public void setVal(String val) {
        this.val = val;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }




}


