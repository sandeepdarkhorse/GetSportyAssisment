package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

/**
 * Created by sandeep on 14/2/17.
 */
public class User {

    private String name;
    private String gender;
    private String dob;
    private String prof_name;
    private String sport;
    private String location;
    private String user_image;

    public User(String name, String Gender, String dob, String prof_name, String sport, String location) {
        this.name = name;
        gender = Gender;
        this.dob = dob;
        this.prof_name = prof_name;
        this.sport = sport;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




}
