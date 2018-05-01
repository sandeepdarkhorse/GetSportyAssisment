package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;


public class CoachHeader {
    private String designation;
    private String acamedy;
    private String location;
    private String description;

    public CoachHeader(String designation, String acamedy, String location, String description) {
        this.designation = designation;
        this.acamedy = acamedy;
        this.location = location;
        this.description = description;
    }

    public CoachHeader() {

    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAcamedy() {
        return acamedy;
    }

    public void setAcamedy(String acamedy) {
        this.acamedy = acamedy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
