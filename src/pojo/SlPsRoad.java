package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1



/**
 * SlPsRoad generated by hbm2java
 */
public class SlPsRoad  implements java.io.Serializable {


     private int slPsRoadId;
     private String slPsRoadName;

    public SlPsRoad() {
    }

	
    public SlPsRoad(int slPsRoadId) {
        this.slPsRoadId = slPsRoadId;
    }
    public SlPsRoad(int slPsRoadId, String slPsRoadName) {
       this.slPsRoadId = slPsRoadId;
       this.slPsRoadName = slPsRoadName;
    }
   
    public int getSlPsRoadId() {
        return this.slPsRoadId;
    }
    
    public void setSlPsRoadId(int slPsRoadId) {
        this.slPsRoadId = slPsRoadId;
    }
    public String getSlPsRoadName() {
        return this.slPsRoadName;
    }
    
    public void setSlPsRoadName(String slPsRoadName) {
        this.slPsRoadName = slPsRoadName;
    }




}


