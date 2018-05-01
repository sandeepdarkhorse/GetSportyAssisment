package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class UserAdapter extends FragmentStatePagerAdapter implements ApiData {

    static TabFragment_education tab1;
    static TabFragment_experience tab2;
    static TabFragment_others tab3;
    private final int mNumOfTabs;


    public UserAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public static TabFragment_education getEducation()
    {
        return tab1;
    }

    public static TabFragment_experience getExperience() {
        return tab2;
    }

    public static TabFragment_others getOthers() {
        return tab3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {


            case 0:
                tab1 = new TabFragment_education();
                return tab1;
            case 1:
                tab2 = new TabFragment_experience();
                return tab2;
            case 2:
                tab3 = new TabFragment_others();
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
    public void getEducationData(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {

    }

    @Override
    public void getExperienceData(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid) {

    }

    @Override
    public void getOthersData(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {

    }


}
