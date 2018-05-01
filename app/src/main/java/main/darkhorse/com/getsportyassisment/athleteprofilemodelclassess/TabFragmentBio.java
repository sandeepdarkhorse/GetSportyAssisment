package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;


public class TabFragmentBio extends Fragment implements ApiAtheliteData {

    View rootView;
    TextView tv_academy_name, tv_style, tv_date_of_birth, tv_height, tv_weight, profession, sport, gender;
    ImageView edit_bio_detail;
    Toast myToast;
    Achivement achivement;
    Header header;
    Bio bio;
    private TextView tv_email;
    private TextView tv_location;
    private AtheliteDetail atheliteDetail;
    private AtheliteTennisDetail atheliteTennisDetail;
    private AthleteBadmintonDetail athleteBadmintonDetail;
    private AtheliteFootballDetail atheliteFootballDetail;

    public TabFragmentBio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tabfragment_athlete_bio, container, false);
        instantiation();
        return rootView;
    }

    private void instantiation() {
        bio = new Bio();
        tv_academy_name = rootView.findViewById(R.id.academy_name);
        tv_style = rootView.findViewById(R.id.style);
      //  tv_email = rootView.findViewById(R.id.email);
        tv_date_of_birth = rootView.findViewById(R.id.date_of_birth);
        tv_height = rootView.findViewById(R.id.height);
        tv_weight = rootView.findViewById(R.id.weight);
        tv_location = rootView.findViewById(R.id.location);
        profession = rootView.findViewById(R.id.profession);
        sport = rootView.findViewById(R.id.sport);
        gender = rootView.findViewById(R.id.gender);
        edit_bio_detail = rootView.findViewById(R.id.edit_bio_detail);
        edit_bio_detail.setVisibility(View.GONE);
        atheliteFootballDetail = new AtheliteFootballDetail();
        achivement = new Achivement();
        header = new Header();
        atheliteTennisDetail = new AtheliteTennisDetail();
        atheliteDetail = new AtheliteDetail();
        athleteBadmintonDetail = new AthleteBadmintonDetail();
    }

    @Override
    public void getAchievements(ArrayList<BestResult> bestResult, ArrayList<Award> awards) {
        achivement.setBestResult(bestResult);
        achivement.setAwards(awards);
    }

    @Override
    public void getLatestResultData(ArrayList<LatestResults> LatestResult) {
        atheliteDetail.setLatestResults(LatestResult);
    }

    @Override
    public void getLatestResultTennisData(ArrayList<LatestResultsTennis> LatestResults) {
        atheliteTennisDetail.setLatestResults(LatestResults);
    }

    @Override
    public void getLatestResultFootballData(ArrayList<LatestResultsFootball> LatestResults) {
        atheliteFootballDetail.setLatestResults(LatestResults);
    }

    @Override
    public void getOthersData(String description, String level, String location, String name, String rank, String clubOrAcademy, String coachName, String dob, String height, String styleOrTypeOfPlay, String weight) {
    }

    @Override
    public void getUserData(String name, String Gender, String dob, String prof_name, String sport) {
        try {
            profession.setText(prof_name);
            this.sport.setText(sport);
        } catch (Exception e) {
        }
    }

    @Override
    public void getUserBio(String coachName, String clubOrAcademy, String styleOrTypeOfPlay, String dob, String height, String weight, String gender, String location, String email) {
        bio = new Bio();
        bio.setCoachName(coachName);
        bio.setClubOrAcademy(clubOrAcademy);
        bio.setStyleOrTypeOfPlay(styleOrTypeOfPlay);
        bio.setDob(dob);
        bio.setHeight(height);
        bio.setWeight(weight);
        bio.setLocation(location);
        bio.setGender(gender);
        bio.setEmail(email);
        try {
            tv_academy_name.setText(clubOrAcademy);
            tv_style.setText(styleOrTypeOfPlay);
            if (dob.equals("")) {
                tv_date_of_birth.setText("");
            } else {
                int age = DateConversion.calculateAge(DateConversion.StringtoDate(dob));
                String athelite_age = String.valueOf(age);
                tv_date_of_birth.setText(athelite_age + " Year");
            }
            tv_height.setText(height);
            tv_weight.setText(weight);
          //  tv_email.setText(email);
            this.gender.setText(gender);
            tv_location.setText(location);
        } catch (Exception e) {
        }
    }

    @Override
    public void getUserHeader(String name, String level, String rank, String location, String description) {
        header.setName(name);
        header.setLevel(level);
        header.setRank(rank);
        header.setLocation(location);
        header.setDescription(description);
    }

    @Override
    public void getLatestResultOthersData(ArrayList<LatestResultBadminton> latestResultsOthers) {
        athleteBadmintonDetail.setLatestResults(latestResultsOthers);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (myToast != null) {
            myToast.cancel();
        }
    }
}


