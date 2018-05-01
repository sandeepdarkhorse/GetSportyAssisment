package main.darkhorse.com.getsportyassisment.profileotherid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;


/**
 * Created by sandeep on 7/11/17.
 */

public class OtherProfileAdapterAll extends FragmentStatePagerAdapter implements ApiDataOtherdata {

    static OtherTabFragment_education tab1;
    static OtherTabFragment_experience tab2;
    static OtherTabFragment_other tab3;
    private final int mNumOfTabs;


    public OtherProfileAdapterAll(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public static OtherTabFragment_education getEducation() {
        return tab1;
    }

    public static OtherTabFragment_experience getExperience() {
        return tab2;
    }

    public static OtherTabFragment_other getOthers() {
        return tab3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {


            case 0:
                tab1 = new OtherTabFragment_education();
                return tab1;
            case 1:
                tab2 = new OtherTabFragment_experience();
                return tab2;
            case 2:
                tab3 = new OtherTabFragment_other();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


    @Override
    public void getEducationDataAll(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {

    }

    @Override
    public void getExperienceDataAll(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid) {

    }


    @Override
    public void getOthersDataAll(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {

    }


}
