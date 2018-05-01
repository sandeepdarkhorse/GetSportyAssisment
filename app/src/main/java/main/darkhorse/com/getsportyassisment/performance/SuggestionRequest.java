package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 10/05/17.
 */

public class SuggestionRequest {

    private String coachid;
    private String title;
    private String description;
    private String module;
    private String gender;
    private String dob;
    private String sport;

    public SuggestionRequest(String coachid, String title, String description, String module, String gender, String dob, String sport) {
        this.coachid = coachid;
        this.title = title;
        this.description = description;
        this.module = module;
        this.gender = gender;
        this.dob = dob;
        this.sport = sport;
    }

    public String getCoachid() {
        return coachid;
    }

    public void setCoachid(String coachid) {
        this.coachid = coachid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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
}
