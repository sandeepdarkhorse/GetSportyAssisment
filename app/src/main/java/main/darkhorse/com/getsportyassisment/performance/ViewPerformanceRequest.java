package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 31/03/17.
 */

public class ViewPerformanceRequest {

    private String athleteid;
    private String assessment;

    public ViewPerformanceRequest(String athleteid, String assessment) {
        this.athleteid = athleteid;
        this.assessment = assessment;
    }

    public String getAthleteid() {
        return athleteid;
    }

    public void setAthleteid(String athleteid) {
        this.athleteid = athleteid;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
}
