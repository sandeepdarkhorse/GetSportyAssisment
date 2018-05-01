package main.darkhorse.com.getsportyassisment.profileotherid;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;


/**
 * Created by sandeep on 7/11/17.
 */

public class OtherExperience {
    private ArrayList<WorkExperienceData> workExperience;
    private ArrayList<PlayerExperienceData> experienceAsPlayer;

    public OtherExperience(ArrayList<WorkExperienceData> workExperience, ArrayList<PlayerExperienceData> experienceAsPlayer) {
        this.workExperience = workExperience;
        this.experienceAsPlayer = experienceAsPlayer;
    }

    public ArrayList<PlayerExperienceData> getExperienceAsPlayer() {
        return experienceAsPlayer;
    }

    public void setExperienceAsPlayer(ArrayList<PlayerExperienceData> experienceAsPlayer) {
        this.experienceAsPlayer = experienceAsPlayer;
    }

    public ArrayList<WorkExperienceData> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(ArrayList<WorkExperienceData> workExperience) {
        this.workExperience = workExperience;
    }

}
