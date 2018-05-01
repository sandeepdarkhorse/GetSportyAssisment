package main.darkhorse.com.getsportyassisment.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;


import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteData;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.AthleteProfile;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.AthleteUserAdapter;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.Award;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.BestResult;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.LatestResultBadminton;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.LatestResults;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.LatestResultsFootball;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.LatestResultsTennis;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.TabFragmentAchievements;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.TabFragmentBio;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.TabFragmentLatestResults;
import main.darkhorse.com.getsportyassisment.brodcastreceiver.ConnectivityReceiver;
import main.darkhorse.com.getsportyassisment.brodcastreceiver.NetworkBrodcastLisner;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ApiData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachProfile;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.TabFragment_education;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.TabFragment_experience;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.TabFragment_others;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserAdapter;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomDialogNoInternet;
import main.darkhorse.com.getsportyassisment.custom_classes.MyApplication;
import main.darkhorse.com.getsportyassisment.otheruserprofileview.OtherProfile;
import main.darkhorse.com.getsportyassisment.otheruserprofileview.OtherProfileAdapter;
import main.darkhorse.com.getsportyassisment.otheruserprofileview.TabEducationOthersProfile;
import main.darkhorse.com.getsportyassisment.otheruserprofileview.TabExperienceOthersProfile;
import main.darkhorse.com.getsportyassisment.otheruserprofileview.TabOtherDataOthersProfile;
import main.darkhorse.com.getsportyassisment.profileotherid.ApiDataOtherdata;
import main.darkhorse.com.getsportyassisment.profileotherid.OtherProfileAdapterAll;
import main.darkhorse.com.getsportyassisment.profileotherid.OtherProfileAll;
import main.darkhorse.com.getsportyassisment.profileotherid.OtherTabFragment_education;
import main.darkhorse.com.getsportyassisment.profileotherid.OtherTabFragment_experience;
import main.darkhorse.com.getsportyassisment.profileotherid.OtherTabFragment_other;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserProfile extends AppCompatActivity implements ApiDataOtherdata, ApiData, ApiAtheliteData, OtherProfile.OnFragmentInteractionListener, ConnectivityReceiver.ConnectivityReceiverListener, CustomDialogNoInternet.myOnClickListener {

    ApiData apiData;
    TabFragment_education adapter;
    Bundle userinfo = new Bundle();
    Toolbar toolbar;
    String Connectionid;
    String liteuserid;
    String liteuserprofid;
    String lite_user_sport;
    String student_id;
    String indiacterforprofile;
    String student_sport;

    Bundle studentinfo = new Bundle();
    Boolean isInternetPresent = false;
    NetworkStatus network_status;
    Boolean netStatus = false;
    private FrameLayout container;
    private TabFragment_experience expAdapter;
    private TabFragment_others otherAdapter;
    private TabFragmentLatestResults adapterlatestresult;
    private TabFragmentAchievements adapterachivement;
    private TabFragmentBio adapterbio;
    private SharedPreferences sharedPreferences1;
    private String user_id;
    private String job_applicant_id;
    private String job_applicant_profid;
    private String job_applicant_sport;
    private String job_applicant_name;
    private String job_title;
    private String job_id;
    private String job_status;
    private ImageView viewLogs;
    private ConnectivityReceiver mNetworkReceiver;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar_profile));
        }
       // CheckAndroidPermission.checkAndRequestPermissions(getApplicationContext(), this);
     //   mNetworkReceiver = new ConnectivityReceiver();
        registerNetworkBroadcastForNougat();
//        netStatus = NetworkBrodcastLisner.checkConnection();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView logo_image = (ImageView) toolbar.findViewById(R.id.logs);
        logo_image.setVisibility(View.GONE);

        container = (FrameLayout) findViewById(R.id.user_profile);
        viewLogs = (ImageView) findViewById(R.id.logs);
        FragmentManager fm = getSupportFragmentManager();

        SharedPreferences sharedPreferences = getSharedPreferences("Dashboard_prefs", MODE_PRIVATE);
        String prof_id = sharedPreferences.getString("prof_id", "");
        String user_id = sharedPreferences.getString("user_id", "");


//        network_status = new NetworkStatus(UserProfile.this);
//        isInternetPresent = network_status.isConnectingToInternet();
//        if (isInternetPresent)
//        {

        try {

            Bundle userinfo = getIntent().getExtras();
            if (userinfo != null)
            {
                Connectionid = userinfo.getString("Connectionid");
                liteuserid = userinfo.getString("liteuserid");
                liteuserprofid = userinfo.getString("liteuserprofid");
                lite_user_sport = userinfo.getString("liteuser_sport");
                student_id = userinfo.getString("student_id");
                student_sport = userinfo.getString("student_sport");
                job_applicant_id = userinfo.getString("liteuserid");
                job_applicant_profid = userinfo.getString("liteuserprofid");
                job_applicant_sport = userinfo.getString("liteuser_sport");
                job_applicant_name = userinfo.getString("lite_user_name");
                job_title = userinfo.getString("job_title");
                job_status = userinfo.getString("job_status");
                Log.e("Tag", "job status" + job_status);
                job_id = userinfo.getString("job_id");
                indiacterforprofile = userinfo.getString("indiacterforprofile");
                if (indiacterforprofile != null)
                {

                    switch (indiacterforprofile)
                    {
                        case "1":

                            switch (liteuserprofid) {
                                case "1":

                                    fm.beginTransaction().replace(R.id.user_profile, new AthleteProfile().newInstance(Connectionid, liteuserid, liteuserprofid, lite_user_sport, "1")).commit();

                                    break;
                                case "2":
                                    fm.beginTransaction().replace(R.id.user_profile, new CoachProfile().newInstance(Connectionid, liteuserid, liteuserprofid)).commit();

                                    break;

                                case "3":



                                    break;

                                case "4":

                                    break;

                                case "13":

                                    fm.beginTransaction().replace(R.id.user_profile, new OtherProfile().newInstance(Connectionid, liteuserid, liteuserprofid, "1")).commit();

                                    break;

                            }


                            break;
                        case "2":
                            fm.beginTransaction().replace(R.id.user_profile, new AthleteProfile().newInstance(student_sport, student_id, "2")).commit();

                            break;

                        case "3":

                            switch (liteuserprofid) {
                                case "1":
                                    fm.beginTransaction().replace(R.id.user_profile, new AthleteProfile().newInstance(job_applicant_id, job_applicant_profid, job_applicant_sport, job_applicant_name, job_title, job_id, job_status, "4")).commit();

                                    break;
                                case "2":
                                    fm.beginTransaction().replace(R.id.user_profile, new CoachProfile().newInstance(Connectionid, liteuserid, liteuserprofid,job_status,indiacterforprofile,job_id)).commit();

                                    break;

                                case "3":

                                    break;

                                case "4":

                                    break;
                                case "5":
                                    fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll().newInstance(Connectionid, liteuserid, liteuserprofid)).commit();


                                    break;




                                case "13":

                                    fm.beginTransaction().replace(R.id.user_profile, new OtherProfile().newInstance(job_applicant_id, job_applicant_profid, job_applicant_sport, job_applicant_name, job_title, job_id, job_status, "3")).commit();


                                    break;

                            }


                            break;

                        case "4":

                            switch (liteuserprofid)
                            {
                                case "1":
                                    fm.beginTransaction().replace(R.id.user_profile, new AthleteProfile().newInstance(Connectionid, liteuserid, liteuserprofid, lite_user_sport, "1")).commit();

                                    break;
                                case "2":
                                    fm.beginTransaction().replace(R.id.user_profile, new CoachProfile().newInstance(Connectionid, liteuserid, liteuserprofid)).commit();

                                    break;

                                case "3":

                                    break;

                                case "4":

                                    break;
                                case "6":

                                 //   fm.beginTransaction().replace(R.id.user_profile, new ParentProfileFragment().newInstance(Connectionid, liteuserid, liteuserprofid)).commit();

                                    break;
                                case "13":

                                    fm.beginTransaction().replace(R.id.user_profile, new OtherProfile().newInstance(Connectionid, liteuserid, liteuserprofid, "4")).commit();

                                    break;

                            }
                            break;

                        case "5":

                            break;

                    }
                }
                else
                {

                    switch (prof_id) {
                        case "1":
                            fm.beginTransaction().replace(R.id.user_profile, new AthleteProfile()).commit();
                            break;
                        case "2":
                            fm.beginTransaction().replace(R.id.user_profile, new CoachProfile()).commit();

                            break;

                        case "3":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;

                        case "4":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;
                        case "5":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;

                        case "7":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;
                        case "8":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;
                        case "9":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;

                        case "10":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;
                        case "11":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;

                        case "12":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;
                        case "13":
                            fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                            break;

                    }

                }
            } else {

                switch (prof_id) {
                    case "1":
                        fm.beginTransaction().replace(R.id.user_profile, new AthleteProfile()).commit();
                        break;
                    case "2":
                        fm.beginTransaction().replace(R.id.user_profile, new CoachProfile()).commit();

                        break;

                    case "3":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;

                    case "4":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;
                    case "5":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;

                    case "7":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;
                    case "8":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;
                    case "9":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;

                    case "10":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;
                    case "11":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;

                    case "12":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;
                    case "13":
                        fm.beginTransaction().replace(R.id.user_profile, new OtherProfileAll()).commit();

                        break;

                }


//                    if (prof_id.equals("1")) {
//                        fm.beginTransaction().replace(R.id.user_profile, new AthleteProfile()).commit();
//
//
//                    } else if (prof_id.equals("2")) {
//                        fm.beginTransaction().replace(R.id.user_profile, new CoachProfile()).commit();
//
//                    }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

//        }
//        else
//        {
//
//            MyToast toast = new MyToast();
//            toast.show(UserProfile.this, "Internet is not connected", false);
//
//
//        }


    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {

        sharedPreferences1 = getSharedPreferences("Dashboard_prefs", 0);
        user_id = sharedPreferences1.getString("user_id", "");
        if (indiacterforprofile != null) {
            switch (indiacterforprofile) {
                case "1":
                    Intent base1 = new Intent(UserProfile.this, MainActivity.class);
                    startActivity(base1);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case "2":

                    break;
                case "3":
                    Intent base2 = new Intent(UserProfile.this, MainActivity.class);
                    startActivity(base2);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                    userinfo.putString("jobId", job_id);
//                    userinfo.putString("jobTitle", job_title);
//                    userinfo.putString("BackPress", "1");
//                    Intent mycreation = new Intent(UserProfile.this, ActivityMyCreationDashBoardType.class);
//                    mycreation.putExtras(userinfo);
//                    startActivity(mycreation);
//                    finish();
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case "4":
//                    final Intent i = new Intent(UserProfile.this, ActivityConnection.class);
//                    startActivity(i);
//                    finish();
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case "5":
                    break;
                case "6":

                default:

                    break;
            }
        } else {
            Intent base3 = new Intent(UserProfile.this, MainActivity.class);
            startActivity(base3);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }


//        if (liteuserid != null && !liteuserid.equals(""))
//        {
//
//            Intent i = new Intent(UserProfile.this, BaseActivity.class);
//            startActivity(i);
//            finish();
//
//
//        } else if (user_id != null && !user_id.equals("")) {
//
//            Intent i = new Intent(UserProfile.this, BaseActivity.class);
//            startActivity(i);
//            finish();
//
//
//        }


    }


    @Override
    public void getEducationData(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fEducation = fm.findFragmentById(R.id.myViewCoach);
//        apiData = (ApiData) fEducation;
//        apiData.getEducationData(sport,formal,other);

        adapter = (TabFragment_education) UserAdapter.getEducation();
        adapter.getEducationData(sport, formal, other, userid);

    }

    @Override
    public void getExperienceData(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid) {
        expAdapter = (TabFragment_experience) UserAdapter.getExperience();
        expAdapter.getExperienceData(work, player, userid);
    }


    @Override
    public void getEducationDataAll(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {
        OtherTabFragment_education adapter = (OtherTabFragment_education) OtherProfileAdapterAll.getEducation();
        adapter.getEducationDataAll(sport, formal, other, userid);

    }

    @Override
    public void getExperienceDataAll(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid) {
        OtherTabFragment_experience expAdapter = (OtherTabFragment_experience) OtherProfileAdapterAll.getExperience();
        expAdapter.getExperienceDataAll(work, player, userid);
    }

    @Override
    public void getOthersDataAll(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {
        OtherTabFragment_other otherAdapter = (OtherTabFragment_other) OtherProfileAdapterAll.getOthers();
        otherAdapter.getOthersDataAll(dob, gender, prof_name, sport, link, age_group_coached, languages_known, userid);
    }

    @Override
    public void getOthersData(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {
        otherAdapter = (TabFragment_others) UserAdapter.getOthers();
        otherAdapter.getOthersData(dob, gender, prof_name, sport, link, age_group_coached, languages_known, userid);
    }

    @Override
    public void getAchievements(ArrayList<BestResult> bestResult, ArrayList<Award> awards) {
        adapterachivement = (TabFragmentAchievements) AthleteUserAdapter.getAchievements();
        adapterachivement.getAchievements(bestResult, awards);
    }

    @Override
    public void getLatestResultData(ArrayList<LatestResults> LatestResults) {
        adapterlatestresult = (TabFragmentLatestResults) AthleteUserAdapter.getLatestResultData();
        adapterlatestresult.getLatestResultData(LatestResults);
    }

    @Override
    public void getLatestResultTennisData(ArrayList<LatestResultsTennis> LatestResults) {
        adapterlatestresult = (TabFragmentLatestResults) AthleteUserAdapter.getLatestResultDatatennis();
        adapterlatestresult.getLatestResultTennisData(LatestResults);
    }

    @Override
    public void getLatestResultFootballData(ArrayList<LatestResultsFootball> LatestResults) {
        adapterlatestresult = (TabFragmentLatestResults) AthleteUserAdapter.getLatestResultDatafootball();
        adapterlatestresult.getLatestResultFootballData(LatestResults);
    }

    @Override
    public void getOthersData(String description, String level, String location, String name, String rank, String clubOrAcademy, String coachName, String dob, String height, String styleOrTypeOfPlay, String weight) {

    }

    @Override
    public void getUserData(String name, String Gender, String dob, String prof_name, String sport) {
        adapterbio = (TabFragmentBio) AthleteUserAdapter.getOthers();
        adapterbio.getUserData(name, Gender, dob, prof_name, sport);
    }

    @Override
    public void getUserBio(String coachName, String clubOrAcademy, String styleOrTypeOfPlay, String dob, String height, String weight, String gender, String location, String email) {
        adapterbio = (TabFragmentBio) AthleteUserAdapter.getbioData();
        adapterbio.getUserBio(coachName, clubOrAcademy, styleOrTypeOfPlay, dob, height, weight, gender, location, email);
    }


    @Override
    public void getUserHeader(String name, String level, String rank, String location, String description) {

    }

    @Override
    public void getLatestResultOthersData(ArrayList<LatestResultBadminton> latestResultsOthers) {
        adapterlatestresult = (TabFragmentLatestResults) AthleteUserAdapter.getLatestResultDatafootball();
        adapterlatestresult.getLatestResultOthersData(latestResultsOthers);
    }

    @Override
    public void sendEducationOthersProfile(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {
        TabEducationOthersProfile tab = (TabEducationOthersProfile) OtherProfileAdapter.getEducationTab();
        tab.getEducationData(sport, formal, other, userid);

    }

    @Override
    public void sendExperienceOthersProfile(ArrayList<WorkExperienceData> work, String userid) {
        TabExperienceOthersProfile tab1 = (TabExperienceOthersProfile) OtherProfileAdapter.getExperienceTab();
        tab1.getExperienceData(work, userid);
    }

    @Override
    public void sendOthersDataOthersProfile(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {
        TabOtherDataOthersProfile tab2 = (TabOtherDataOthersProfile) OtherProfileAdapter.getOtherTab();
        tab2.getOthersData(dob, gender, prof_name, sport, link, age_group_coached, languages_known, userid);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onRestart() {
        super.onRestart();

    }

    @Override
    public void onResume() {
        super.onResume();
     //   MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (netStatus == isConnected) {

        } else {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);


        }
    }

    @Override
    public void onButtonClick() {

    }
}
