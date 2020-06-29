package pojo;
// Generated Sep 6, 2019 4:30:16 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SlArrLoadLocation generated by hbm2java
 */
public class SlArrLoadLocation  implements java.io.Serializable {


     private int slArrLoadLocationId;
     private Double slArrLoadLocationX;
     private Double slArrLoadLocationY;
     private String slArrLoadLocationPlaceName;
     private Set<SlLoadArrearsPanels> slLoadArrearsPanelses = new HashSet<SlLoadArrearsPanels>(0);

    public SlArrLoadLocation() {
    }

	
    public SlArrLoadLocation(int slArrLoadLocationId) {
        this.slArrLoadLocationId = slArrLoadLocationId;
    }
    public SlArrLoadLocation(int slArrLoadLocationId, Double slArrLoadLocationX, Double slArrLoadLocationY, String slArrLoadLocationPlaceName, Set<SlLoadArrearsPanels> slLoadArrearsPanelses) {
       this.slArrLoadLocationId = slArrLoadLocationId;
       this.slArrLoadLocationX = slArrLoadLocationX;
       this.slArrLoadLocationY = slArrLoadLocationY;
       this.slArrLoadLocationPlaceName = slArrLoadLocationPlaceName;
       this.slLoadArrearsPanelses = slLoadArrearsPanelses;
    }
   
    public int getSlArrLoadLocationId() {
        return this.slArrLoadLocationId;
    }
    
    public void setSlArrLoadLocationId(int slArrLoadLocationId) {
        this.slArrLoadLocationId = slArrLoadLocationId;
    }
    public Double getSlArrLoadLocationX() {
        return this.slArrLoadLocationX;
    }
    
    public void setSlArrLoadLocationX(Double slArrLoadLocationX) {
        this.slArrLoadLocationX = slArrLoadLocationX;
    }
    public Double getSlArrLoadLocationY() {
        return this.slArrLoadLocationY;
    }
    
    public void setSlArrLoadLocationY(Double slArrLoadLocationY) {
        this.slArrLoadLocationY = slArrLoadLocationY;
    }
    public String getSlArrLoadLocationPlaceName() {
        return this.slArrLoadLocationPlaceName;
    }
    
    public void setSlArrLoadLocationPlaceName(String slArrLoadLocationPlaceName) {
        this.slArrLoadLocationPlaceName = slArrLoadLocationPlaceName;
    }
    public Set<SlLoadArrearsPanels> getSlLoadArrearsPanelses() {
        return this.slLoadArrearsPanelses;
    }
    
    public void setSlLoadArrearsPanelses(Set<SlLoadArrearsPanels> slLoadArrearsPanelses) {
        this.slLoadArrearsPanelses = slLoadArrearsPanelses;
    }




}


