package main.darkhorse.com.getsportyassisment.otheruserprofileview;


import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;


public interface OtherProfileInterface {

    void getEducationData(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid);
    void getExperienceData(ArrayList<WorkExperienceData> work, String userid);
    void getOthersData(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid);

}
