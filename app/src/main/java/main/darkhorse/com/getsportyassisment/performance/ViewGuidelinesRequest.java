package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 07/04/17.
 */

public class ViewGuidelinesRequest {

    private String dob;
    private String sport;
    private String gender;

    public ViewGuidelinesRequest(String dob, String sport, String gender) {
        this.dob = dob;
        this.sport = sport;
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
