package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

import java.util.ArrayList;

public class CoachExperience {
    private ArrayList<WorkExperienceData> workExperience;
    private ArrayList<PlayerExperienceData> experienceAsPlayer;

    public CoachExperience(ArrayList<WorkExperienceData> workExperience, ArrayList<PlayerExperienceData> experienceAsPlayer) {
        this.workExperience = workExperience;
        this.experienceAsPlayer = experienceAsPlayer;
    }

    public CoachExperience() {

    }

    public ArrayList<WorkExperienceData> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(ArrayList<WorkExperienceData> workExperience) {
        this.workExperience = workExperience;
    }

    public ArrayList<PlayerExperienceData> getExperienceAsPlayer() {
        return experienceAsPlayer;
    }

    public void setExperienceAsPlayer(ArrayList<PlayerExperienceData> experienceAsPlayer) {
        this.experienceAsPlayer = experienceAsPlayer;
    }
}
