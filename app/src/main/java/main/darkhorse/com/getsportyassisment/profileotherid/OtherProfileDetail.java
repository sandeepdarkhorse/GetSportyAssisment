package main.darkhorse.com.getsportyassisment.profileotherid;


import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachExperience;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachHeader;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserData;

/**
 * Created by sandeep on 7/11/17.
 */

public class OtherProfileDetail {
    private OtherProfileEducation Education;
    private CoachExperience Experience;
    private CoachHeader HeaderDetails;
    private UserData user;
    private double profile;

    public OtherProfileDetail() {

    }

    public OtherProfileDetail(OtherProfileEducation education, CoachExperience experience, CoachHeader headerDetails, UserData user) {
        Education = education;
        Experience = experience;
        HeaderDetails = headerDetails;
        this.user = user;
    }

    public OtherProfileDetail(OtherProfileEducation education, CoachExperience experience, CoachHeader headerDetails, UserData user, double profile) {
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

    public OtherProfileEducation getEducation() {
        return Education;
    }

    public void setEducation(OtherProfileEducation education) {
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
