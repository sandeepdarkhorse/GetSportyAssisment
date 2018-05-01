package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 7/2/17.
 */
public class UserProfileCoachEditRequest {
    private CoachEducation Education;
    private CoachExperience Experience;
    private CoachHeader HeaderDetails;


    public UserProfileCoachEditRequest(CoachEducation education, CoachExperience experience, CoachHeader headerDetails) {
        Education = education;
        Experience = experience;
        HeaderDetails = headerDetails;

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

}
