package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 10/2/17.
 */
public class UserData {
    private String name;
    private String gender;
    private String dob;
    private String prof_name;
    private String sport;
    private String link;
    private String age_group_coached;
    private String languages_known;
    private String user_image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    public UserData(String name, String Gender, String dob, String prof_name, String sport, String link, String age_group_coached, String languages_known, String user_image) {
        this.name = name;
        gender = Gender;
        this.dob = dob;
        this.prof_name = prof_name;
        this.sport = sport;
        this.link = link;
        this.age_group_coached = age_group_coached;
        this.languages_known = languages_known;
        this.user_image = user_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String Gender) {
        gender = Gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getProf_name() {
        return prof_name;
    }

    public void setProf_name(String prof_name) {
        this.prof_name = prof_name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAge_group_coached() {
        return age_group_coached;
    }

    public void setAge_group_coached(String age_group_coached) {
        this.age_group_coached = age_group_coached;
    }

    public String getLanguages_known() {
        return languages_known;
    }

    public void setLanguages_known(String languages_known) {
        this.languages_known = languages_known;
    }
}
