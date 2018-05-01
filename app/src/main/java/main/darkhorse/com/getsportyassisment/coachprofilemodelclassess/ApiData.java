package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

import java.util.ArrayList;


/**
 * Created by Nikhil on 6/2/17.
 */
public interface ApiData {
    public void getEducationData(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid);
    public void getExperienceData(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid);
    public void getOthersData(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid);

}
