package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import java.util.ArrayList;

/**
 * Created by sandeep on 13/2/17.
 */
public interface ApiAtheliteData {

    public void getAchievements(ArrayList<BestResult> bestResult, ArrayList<Award> awards);

    public void getLatestResultData(ArrayList<LatestResults> LatestResults);

    public void getLatestResultTennisData(ArrayList<LatestResultsTennis> LatestResults);


    public void getLatestResultFootballData(ArrayList<LatestResultsFootball> LatestResults);

    public void getOthersData(String description, String level, String location, String name, String rank, String clubOrAcademy, String coachName, String dob, String height, String styleOrTypeOfPlay, String weight);

    public void getUserData(String name, String Gender, String dob, String prof_name, String sport);

    public void getUserBio(String coachName, String clubOrAcademy, String styleOrTypeOfPlay, String dob, String height, String weight, String gender, String location, String email);
    public void getUserHeader(String name, String level, String rank, String location, String description);


    void getLatestResultOthersData(ArrayList<LatestResultBadminton> latestResultsOthers);
}
