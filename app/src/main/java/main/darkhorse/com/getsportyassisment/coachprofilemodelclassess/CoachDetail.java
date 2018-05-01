package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;


public class CoachDetail {
    private CoachEducation Education;
    private CoachExperience Experience;
    private CoachHeader HeaderDetails;

    public CoachDetail() {

    }

    private UserData user;
    private double profile;

    public CoachDetail(CoachEducation education, CoachExperience experience, CoachHeader headerDetails, UserData user) {
        Education = education;
        Experience = experience;
        HeaderDetails = headerDetails;
        this.user = user;
    }

    public CoachDetail(CoachEducation education, CoachExperience experience, CoachHeader headerDetails, UserData user, double profile) {
        Education = education;
        Experience = experience;
        HeaderDetails = headerDetails;
        this.user = user;
        this.profile = profile;
    }

    public double getProfile() {
        return profile;
    }

    public void setProfile(double profile) {
        this.profile = profile;
    }

    public CoachEducation getEducation() {
        return Education;
    }

    public void setEducation(CoachEducation education) {
        Education = education;
    }

    public CoachExperience getExperience() {
        return Experience;
    }

    public void setExperience(CoachExperience experience) {
        Experience = experience;
    }

    public CoachHeader getHeaderDetails() {
        return HeaderDetails;
    }

    public void setHeaderDetails(CoachHeader headerDetails) {
        HeaderDetails = headerDetails;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }
}
