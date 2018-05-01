package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

public class Bio {
    private String coachName;
    private String clubOrAcademy;
    private String styleOrTypeOfPlay;
    private String dob;
    private String height;
    private String weight;
    private String gender;
    private String location;
    private String email;

    public Bio() {
    }

    public Bio(String coachName, String clubOrAcademy, String styleOrTypeOfPlay, String dob, String height, String weight, String gender, String location, String email) {

        this.coachName = coachName;
        this.clubOrAcademy = clubOrAcademy;
        this.styleOrTypeOfPlay = styleOrTypeOfPlay;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.location = location;
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getClubOrAcademy() {
        return clubOrAcademy;
    }

    public void setClubOrAcademy(String clubOrAcademy) {
        this.clubOrAcademy = clubOrAcademy;
    }

    public String getStyleOrTypeOfPlay() {
        return styleOrTypeOfPlay;
    }

    public void setStyleOrTypeOfPlay(String styleOrTypeOfPlay) {
        this.styleOrTypeOfPlay = styleOrTypeOfPlay;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
