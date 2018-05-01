package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class AthleteUserAdapter extends FragmentStatePagerAdapter implements ApiAtheliteData {

    static TabFragmentLatestResults tab1;
    static TabFragmentAchievements tab2;
    static TabFragmentBio tab3;
    private final int mNumOfTabs;


    public AthleteUserAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public static TabFragmentLatestResults getLatestResultData(){
        return tab1;
    }

    public static TabFragmentAchievements getAchievements()
    {
        return tab2;
    }

    public static TabFragmentBio getbioData()
    {
        return tab3;
    }

    public static TabFragmentBio getOthers() {
        return tab3;
    }

    public static TabFragmentLatestResults getLatestResultDatatennis()
    {
        return tab1;
    }

    public static TabFragmentLatestResults getLatestResultDatafootball()
    {
        return tab1;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {


            case 0:
               tab1 = new TabFragmentLatestResults();
                return tab1;
            case 1:
                tab2 = new TabFragmentAchievements();
                return tab2;
            case 2:
                tab3 = new TabFragmentBio();
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
    public void getAchievements(ArrayList<BestResult> bestResult, ArrayList<Award> awards) {
        tab2.getAchievements(bestResult, awards);

    }

    @Override
    public void getLatestResultData(ArrayList<LatestResults> LatestResults) {
        tab1.getLatestResultData(LatestResults);
    }

    @Override
    public void getLatestResultTennisData(ArrayList<LatestResultsTennis> LatestResults) {
        tab1.getLatestResultTennisData(LatestResults);
    }

    @Override
    public void getLatestResultFootballData(ArrayList<LatestResultsFootball> LatestResults) {
        tab1.getLatestResultFootballData(LatestResults);
    }

    @Override
    public void getOthersData(String description, String level, String location, String name, String rank, String clubOrAcademy, String coachName, String dob, String height, String styleOrTypeOfPlay, String weight) {
        tab3.getOthersData(description, level, location, name, rank, clubOrAcademy, coachName, dob, height, styleOrTypeOfPlay, weight);

    }

    @Override
    public void getUserData(String name, String Gender, String dob, String prof_name, String sport) {

    }

    @Override
    public void getUserBio(String coachName, String clubOrAcademy, String styleOrTypeOfPlay, String dob, String height, String weight, String gender, String location, String email) {

    }

    @Override
    public void getUserHeader(String name, String level, String rank, String location, String description) {

    }

    @Override
    public void getLatestResultOthersData(ArrayList<LatestResultBadminton> latestResultsOthers) {

    }
}