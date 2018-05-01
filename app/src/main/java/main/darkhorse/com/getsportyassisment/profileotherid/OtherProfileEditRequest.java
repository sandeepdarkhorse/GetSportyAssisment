package main.darkhorse.com.getsportyassisment.profileotherid;


import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachHeader;

/**
 * Created by sandeep on 7/11/17.
 */

public class OtherProfileEditRequest {
    private OtherProfileEducation Education;
    private OtherExperience Experience;
    private CoachHeader HeaderDetails;

    public OtherProfileEditRequest(OtherProfileEducation education, OtherExperience experience, CoachHeader headerDetails) {
        Education = education;
        Experience = experience;
        HeaderDetails = headerDetails;

    }

    public OtherProfileEducation getEducation() {
        return Education;
    }

    public void setEducation(OtherProfileEducation education) {
        Education = education;
    }

    public OtherExperience getExperience() {
        return Experience;
    }

    public void setExperience(OtherExperience experience) {
        Experience = experience;
    }

    public CoachHeader getHeaderDetails() {
        return HeaderDetails;
    }

    public void setHeaderDetails(CoachHeader headerDetails) {
        HeaderDetails = headerDetails;
    }


}

