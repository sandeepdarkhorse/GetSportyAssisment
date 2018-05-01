package main.darkhorse.com.getsportyassisment.otheruserprofileview;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class OtherProfileAdapter extends FragmentStatePagerAdapter {

    private static TabEducationOthersProfile educationTab;
    private static TabExperienceOthersProfile experienceTab;
    private static TabOtherDataOthersProfile otherTab;
    private final int noOfTabs;


    public OtherProfileAdapter(FragmentManager fm , int noOfTabs){
        super(fm);
        this.noOfTabs = noOfTabs;
    }



    public static TabOtherDataOthersProfile getOtherTab(){
        return otherTab;
    }
    public static TabEducationOthersProfile getEducationTab (){
        return educationTab;
    }

    public static TabExperienceOthersProfile getExperienceTab(){
        return experienceTab;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                educationTab = new TabEducationOthersProfile();
                return educationTab;
            case 1:
                experienceTab = new TabExperienceOthersProfile();
                return experienceTab;
            case 2:
                otherTab = new TabOtherDataOthersProfile();
                return otherTab;
        }
        return null;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
