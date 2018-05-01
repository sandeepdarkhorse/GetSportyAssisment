package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 27/03/17.
 */

public class GetPerformanceModuleRequest {


    private String gender;
    private String sport;
    private String dob;
    private String coachid;
    private String athleteid;

    public GetPerformanceModuleRequest(String gender, String sport, String dob, String coachid, String athleteid) {
        this.gender = gender;
        this.sport = sport;
        this.dob = dob;
        this.coachid = coachid;
        this.athleteid = athleteid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCoachid() {
        return coachid;
    }

    public void setCoachid(String coachid) {
        this.coachid = coachid;
    }

    public String getAthleteid() {
        return athleteid;
    }

    public void setAthleteid(String athleteid) {
        this.athleteid = athleteid;
    }
}
