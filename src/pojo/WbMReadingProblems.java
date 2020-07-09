package pojo;
// Generated Jul 9, 2020 11:48:20 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * WbMReadingProblems generated by hbm2java
 */
public class WbMReadingProblems  implements java.io.Serializable {


     private Integer wbMProblemsId;
     private WbMMeterStatus wbMMeterStatus;
     private String wbMProblem;
     private Integer wbMProblemStatus;
     private String wbMProbCode;
     private Set<WbMConnection> wbMConnections = new HashSet<WbMConnection>(0);
     private Set<WbMReadingProb> wbMReadingProbs = new HashSet<WbMReadingProb>(0);

    public WbMReadingProblems() {
    }

    public WbMReadingProblems(WbMMeterStatus wbMMeterStatus, String wbMProblem, Integer wbMProblemStatus, String wbMProbCode, Set<WbMConnection> wbMConnections, Set<WbMReadingProb> wbMReadingProbs) {
       this.wbMMeterStatus = wbMMeterStatus;
       this.wbMProblem = wbMProblem;
       this.wbMProblemStatus = wbMProblemStatus;
       this.wbMProbCode = wbMProbCode;
       this.wbMConnections = wbMConnections;
       this.wbMReadingProbs = wbMReadingProbs;
    }
   
    public Integer getWbMProblemsId() {
        return this.wbMProblemsId;
    }
    
    public void setWbMProblemsId(Integer wbMProblemsId) {
        this.wbMProblemsId = wbMProblemsId;
    }
    public WbMMeterStatus getWbMMeterStatus() {
        return this.wbMMeterStatus;
    }
    
    public void setWbMMeterStatus(WbMMeterStatus wbMMeterStatus) {
        this.wbMMeterStatus = wbMMeterStatus;
    }
    public String getWbMProblem() {
        return this.wbMProblem;
    }
    
    public void setWbMProblem(String wbMProblem) {
        this.wbMProblem = wbMProblem;
    }
    public Integer getWbMProblemStatus() {
        return this.wbMProblemStatus;
    }
    
    public void setWbMProblemStatus(Integer wbMProblemStatus) {
        this.wbMProblemStatus = wbMProblemStatus;
    }
    public String getWbMProbCode() {
        return this.wbMProbCode;
    }
    
    public void setWbMProbCode(String wbMProbCode) {
        this.wbMProbCode = wbMProbCode;
    }
    public Set<WbMConnection> getWbMConnections() {
        return this.wbMConnections;
    }
    
    public void setWbMConnections(Set<WbMConnection> wbMConnections) {
        this.wbMConnections = wbMConnections;
    }
    public Set<WbMReadingProb> getWbMReadingProbs() {
        return this.wbMReadingProbs;
    }
    
    public void setWbMReadingProbs(Set<WbMReadingProb> wbMReadingProbs) {
        this.wbMReadingProbs = wbMReadingProbs;
    }




}


