package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//import darkhorsesports.getsporty.R;
//import darkhorsesports.getsporty.activities.PerformanceActivity;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient;
//import darkhorsesports.getsporty.customclasses.CustomProgress;
//import darkhorsesports.getsporty.customclasses.DateConversion;
//import darkhorsesports.getsporty.customclasses.GraphicsUtil;
//import darkhorsesports.getsporty.customclasses.MyToast;
//import darkhorsesports.getsporty.customclasses.TooltipWindowDietLog;
//import darkhorsesports.getsporty.fragments.Fragment_Share;
//import darkhorsesports.getsporty.modelclasses.ApplicantJobOfferResponse;
//import darkhorsesports.getsporty.modelclasses.SendOfferToApplicant;
//import darkhorsesports.getsporty.modelclasses.ShortlistCandidate;
//import darkhorsesports.getsporty.property.utils.SharedPrefs;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.MyToast;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.custom_classes.GraphicsUtil;

import main.darkhorse.com.getsportyassisment.fragment.Fragment_Share;
import main.darkhorse.com.getsportyassisment.model_classes.ShortlistCandidate;
import main.darkhorse.com.getsportyassisment.performance.PerformanceActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static main.darkhorse.com.getsportyassisment.custom_classes.DateConversion.context;


public class AthleteProfile extends Fragment implements View.OnClickListener, ApiAtheliteData {

    public static final String MY_PREFS_NAME = "Dashboard_prefs";
    private static final String ARG_SPORT = "sport";
    private static final String ARG_USERID = "userid";
    private static final String ARG_INDICATOR = "indicator";
    private static final String ARG_CONNECT_ID = "connectionId";
    private static final String ARG_PROF_ID = "profId";
    private static final String ARG_NAME = "name";
    private static final String ARG_JOB_TITLE = "jobTitle";
    private static final String ARG_JOB_ID = "jobId";
    private static final String ARG_JOB_STATUS = "jobStatus";
    static String calender_Date;
    ImageView imageView_profile_pic;
    TextView textView_name;
    TextView textView_profession;
    TextView textView_location;
    //TextView editText_about;
    TextView textView_level, textText_rank;
    Button submit;
    View rootView;
    HashMap<String, String> hashMap_info;
    GraphicsUtil graphicUtil;

    LatestResults LatestResults;
    Achivement achivement;
    Header header;
    Bio bio;
    ApiAtheliteData apiAtheliteData;
    String UserSport;
    Button button_select;
    Button button_shortlist;
    LinearLayout layout_sendoffer;
    Button button_send_offer;

    String connectionid, jobTitle, jobId, jobStatus;
    String diet_log_id;
    String userId;
    View view1;
    LinearLayout snackbar_linear, create_job, layout_create_tournamnet, layout_diet_log, snackbar_linear_layout;
    RelativeLayout item;
    TextView tv_create_job, tv_create_tournament, tv_create_event, tv_snackbar_more;
    LinearLayout layout_more, footer_shortlist;
    LinearLayout layout_profile_job;
    String salary;
    String joining_date;
    SharedPreferences sharedPreferences1;
    String employerId;
    String employerName;
    CustomProgress customProgress;
  //  TooltipWindowDietLog tipWindow;
    String profileImage;
    ImageView share_profile;
    Uri uri;
    AtheliteDetail atheliteDetail;
    AtheliteTennisDetail atheliteTennisDetail;
    AtheliteFootballDetail atheliteFootballDetail;
    AthleteBadmintonDetail athleteBadmintonDetail;
    private AthleteUserAdapter mAdapter;
    private Retrofit retrofit;
    private ApiAtheliteCall apiCall;
    private String sport;
    private String indiacator;
    private FragmentManager fm;
    Context context;

    public AthleteProfile() {
    }

    public static AthleteProfile newInstance(String sport, String userId, String indicator) {
        AthleteProfile fragment = new AthleteProfile();
        Bundle args = new Bundle();
        args.putString(ARG_SPORT, sport);
        args.putString(ARG_USERID, userId);
        args.putString(ARG_INDICATOR, indicator);
        fragment.setArguments(args);
        return fragment;
    }

    public static AthleteProfile newInstance(String connectionid, String userId, String profId, String sport, String indicator) {
        AthleteProfile athleteProfile = new AthleteProfile();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CONNECT_ID, connectionid);
        bundle.putString(ARG_USERID, userId);
        bundle.putString(ARG_PROF_ID, profId);
        bundle.putString(ARG_SPORT, sport);
        bundle.putString(ARG_INDICATOR, indicator);
        athleteProfile.setArguments(bundle);
        return athleteProfile;

    }

    public static AthleteProfile newInstance(String applicantId, String profId, String sport, String name,
                                             String jobTitle, String jobId, String jobStatus, String indicator) {
        AthleteProfile athleteProfile = new AthleteProfile();
        Bundle bundle = new Bundle();

        bundle.putString(ARG_USERID, applicantId);
        bundle.putString(ARG_PROF_ID, profId);
        bundle.putString(ARG_SPORT, sport);
        bundle.putString(ARG_NAME, name);
        bundle.putString(ARG_JOB_TITLE, jobTitle);
        bundle.putString(ARG_JOB_ID, jobId);
        bundle.putString(ARG_JOB_STATUS, jobStatus);
        bundle.putString(ARG_INDICATOR, indicator);
        athleteProfile.setArguments(bundle);
        return athleteProfile;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USERID);
            sport = getArguments().getString(ARG_SPORT);
            indiacator = getArguments().getString(ARG_INDICATOR);
        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userId = getArguments().getString(ARG_USERID);
            connectionid = getArguments().getString(ARG_CONNECT_ID);
            sport = getArguments().getString(ARG_SPORT);
            jobTitle = getArguments().getString(ARG_JOB_TITLE);
            jobId = getArguments().getString(ARG_JOB_ID);
            jobStatus = getArguments().getString(ARG_JOB_STATUS);
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_athlete_profile, container, false);
        fm = getFragmentManager();
        share_profile = rootView.findViewById(R.id.share_profile_pdf);
        share_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String link = "http://getsporty.in/html_pdf/example_001.php?id=" + userId;
                Bitmap bm = DateConversion.screenShot(rootView);

                File file = DateConversion.saveBitmap(bm, "mantis_image.png");
                Log.i("chase", "filepath: " + file.getAbsolutePath());

                if (Build.VERSION.SDK_INT >= 21) {
                    uri = FileProvider.getUriForFile(getContext(), "main.darkhorse.com.getsportyassisment", file);
                    showDialog(link, uri);
                } else {
                    uri = Uri.fromFile(file);
                    showDialog(link, uri);
                }


            }
        });

        customProgress = CustomProgress.getInstance();

        retrofit = ApiClient.getClient();
        apiCall = retrofit.create(ApiAtheliteCall.class);
        initView();

        view1 = getLayoutInflater(savedInstanceState).inflate(R.layout.student_profile_my_snackbar, null);
        create_job = view1.findViewById(R.id.layout_share);
        layout_create_tournamnet = view1.findViewById(R.id.layout_for);
        layout_diet_log = view1.findViewById(R.id.diet_log_view);
        snackbar_linear = view1.findViewById(R.id.layout_performance);
        snackbar_linear_layout = view1.findViewById(R.id.snackbar_linear);
        tv_create_job = view1.findViewById(R.id.tv_create_job);
        tv_create_tournament = view1.findViewById(R.id.tv_create_tournament);
        tv_create_event = view1.findViewById(R.id.tv_create_event);
        tv_snackbar_more = view1.findViewById(R.id.tv_snackbar_more);

        view1.setLayoutParams(new Snackbar.SnackbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();


        click_listeners();
        if (indiacator != null) {
            switch (indiacator) {
                case "1":
                    // layout_profile.setVisibility(View.VISIBLE);
                    item.setVisibility(View.GONE);
                    layout_profile_job.setVisibility(View.GONE);
//                    imageView_profile_pic.setClickable(false);


                    apiCall();
                    break;
                case "2":
                    item.setVisibility(View.GONE);
                    layout_profile_job.setVisibility(View.GONE);
//                    imageView_profile_pic.setClickable(false);


                    apiCall();
                    break;

                case "3":
                    item.setVisibility(View.GONE);
                    layout_profile_job.setVisibility(View.GONE);
//                    imageView_profile_pic.setClickable(false);


                    apiCall();
                    break;
                case "4":
                    switch (jobStatus)

                    {
                        case "1":
                            item.setVisibility(View.GONE);
                            footer_shortlist.setVisibility(View.VISIBLE);
                            //  layout_profile_job.setVisibility(View.VISIBLE);
//                            imageView_profile_pic.setClickable(false);


                            apiCall();

                            break;
                        case "2":
                            item.setVisibility(View.GONE);
                            //layout_profile_job.setVisibility(View.GONE);
                            footer_shortlist.setVisibility(View.GONE);
//                            imageView_profile_pic.setClickable(false);

                            apiCall();
                            break;

                        case "3":
                            layout_sendoffer.setVisibility(View.GONE);
                            button_send_offer.setVisibility(View.GONE);
                            footer_shortlist.setVisibility(View.GONE);
//                            imageView_profile_pic.setClickable(false);

                            apiCall();

                            break;

                        case "4":

                            item.setVisibility(View.GONE);
                            //layout_profile_job.setVisibility(View.GONE);
                            footer_shortlist.setVisibility(View.GONE);
                            layout_sendoffer.setVisibility(View.GONE);
                            button_send_offer.setVisibility(View.GONE);
//                            imageView_profile_pic.setClickable(false);
                           // apiCall();


                            break;
                        case "5":
                            layout_sendoffer.setVisibility(View.GONE);
                            button_send_offer.setVisibility(View.GONE);
                            footer_shortlist.setVisibility(View.GONE);
//                            imageView_profile_pic.setClickable(false);

                            apiCall();
                            break;
                    }
                    break;
            }

        } else {
            layout_profile_job.setVisibility(View.GONE);
            item.setVisibility(View.GONE);
            imageView_profile_pic.setClickable(true);


            apiCall();
        }

        layout_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_more.setVisibility(View.GONE);
                item.addView(view1);
            }
        });

        snackbar_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.removeView(view1);
                layout_more.setVisibility(View.VISIBLE);
                Intent i = new Intent(getActivity(), PerformanceActivity.class);
//                i.putExtra("gender", gender);
                //i.putExtra("dob",bio.getDob());
//                i.putExtra("dob", dob);
                i.putExtra("studentid", userId);
                i.putExtra("sport", sport);
                startActivity(i);
            }
        });

        snackbar_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.removeView(view1);
                layout_more.setVisibility(View.VISIBLE);
            }
        });
        create_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), ActivityCreateJobs.class);
//                startActivity(i);

            }
        });
        layout_create_tournamnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), ActivityCreateTournament.class);
//                startActivity(i);

            }
        });
        if (userId != null) {
            diet_log_id = userId;
        }

 //       tipWindow = new TooltipWindowDietLog(getActivity(), diet_log_id);
//        layout_diet_log.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (!tipWindow.isTooltipShown())
//                    tipWindow.showToolTip(view);
//
//
//            }
//        });


        return rootView;
    }

    private void apiCall() {
        switch (sport) {
            case "Athletics":
                ApicallforSwimming();
                break;
            case "Swimming":
                ApicallforSwimming();
                break;

            case "Table-tennis":
                Apicallfortennis();
                break;

            case "Football":
                Apicallforfootball();
                break;

            case "Badminton":
                Apicallfortennis();
                break;
            default:
                ApiCallForOthers();

        }
    }


    private void click_listeners() {

        //imageView_back.setOnClickListener(this);
        imageView_profile_pic.setOnClickListener(this);
        button_send_offer.setOnClickListener(this);
    }

    private void initView()
    {
        //processbar = (ProgressBar) rootView.findViewById(R.id.pb);
        // tv_persentage = (TextView) rootView.findViewById(R.id.percentage);
        item = (RelativeLayout) rootView.findViewById(R.id.view2);
//        layout_profile = (LinearLayout) rootView.findViewById(R.id.footer_layout_profile);
        layout_profile_job = (LinearLayout) rootView.findViewById(R.id.footer_layout_profile_job);
        layout_more = (LinearLayout) rootView.findViewById(R.id.more_layout);
        footer_shortlist = (LinearLayout) rootView.findViewById(R.id.footer_layout_shortlist);
        layout_sendoffer = (LinearLayout) rootView.findViewById(R.id.foote_job_offer);
        button_send_offer = (Button) rootView.findViewById(R.id.send_offer);

        button_shortlist = (Button) rootView.findViewById(R.id.shortlist);
        button_shortlist.setOnClickListener(this);
        button_select = (Button) rootView.findViewById(R.id.select);
        //button_reject = (Button) rootView.findViewById(R.id.reject);
        button_select.setOnClickListener(this);
        sharedPreferences1 = getActivity().getSharedPreferences("Dashboard_prefs", 0);
        graphicUtil = new GraphicsUtil();
        submit = (Button) rootView.findViewById(R.id.submit);
        imageView_profile_pic = (ImageView) rootView.findViewById(R.id.profile_pic);
        //imageView_pic_blur = (ImageView) rootView.findViewById(R.id.profile_pic_blur);
        textView_name = (TextView) rootView.findViewById(R.id.name);
        textView_profession = (TextView) rootView.findViewById(R.id.profession);
        textView_location = (TextView) rootView.findViewById(R.id.location);
      //  editText_about = (TextView) rootView.findViewById(R.id.about);
        textView_level = (TextView) rootView.findViewById(R.id.level);
        textText_rank = (TextView) rootView.findViewById(R.id.rank);
//        textView_title.setTypeface(font);


        hashMap_info = new HashMap<>();

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.myViewCoach);
        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_coach);


        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_latestresult));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tad_achivement));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_bio));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mAdapter = new AthleteUserAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView tabTextView = new TextView(getContext());
                tab.setCustomView(tabTextView);
                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setText(tab.getText());
                if (i == 0) {
                    //  tabTextView.setTypeface(custom_font, Typeface.BOLD);
                    tabTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_athlete));
                }
            }
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_athlete));
                tabLayout.setTabTextColors(getResources().getColor(R.color.tab_athlete_text_color), getResources().getColor(R.color.tab_athlete));
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
                TextView text = (TextView) tab.getCustomView();
                assert text != null;
                // text.setTypeface(custom_font, Typeface.BOLD);
                text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_athlete));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(Color.parseColor("#2196f3"), Color.parseColor("#000000"));
                TextView text = (TextView) tab.getCustomView();
                assert text != null;
                //  text.setTypeface(custom_font, Typeface.NORMAL);
                text.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_athlete_text_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_pic:
                DateConversion.showImage(getContext(), profileImage);
                break;
            case R.id.select:
                //selectApplicant();
                break;
            case R.id.reject:
                // rejectApplicant();
                break;
            case R.id.shortlist:
              //  shortlist();
                break;
            case R.id.send_offer:
               // send_jobOffer();
                break;


        }
    }

    public void ApiCallForOthers() {
        Call<UserProfileAtheliteTennisResponse> call = apiCall.getAtheliteProfileTennis("getUserProfile", userId, "1");
        customProgress.showProgress(getActivity(), false);
        call.enqueue(new Callback<UserProfileAtheliteTennisResponse>() {
            @Override
            public void onResponse(Call<UserProfileAtheliteTennisResponse> call, Response<UserProfileAtheliteTennisResponse> response) {
                if (response != null && response.body() != null) {
                    customProgress.hideProgress();
                    apiAtheliteData = (ApiAtheliteData) getActivity();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PROFILE_DATA, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(userId, response.body().getData().toString());
                    editor.commit();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().getData().toString());
                        Gson gson = new Gson();
                        athleteBadmintonDetail = gson.fromJson(jsonObject.toString(), AthleteBadmintonDetail.class);
                        achivement = athleteBadmintonDetail.getAchivement();
                        header = athleteBadmintonDetail.getHeader();
                        bio = athleteBadmintonDetail.getBio();
                        if (response.body().getData() != null) {
                            if (athleteBadmintonDetail.getAchivement() != null) {
                                apiAtheliteData.getAchievements(athleteBadmintonDetail.getAchivement().getBestResult(), athleteBadmintonDetail.getAchivement().getAwards());
                            }
                            if (athleteBadmintonDetail.getLatestResults() != null) {
                                apiAtheliteData.getLatestResultOthersData(athleteBadmintonDetail.getLatestResults());
                            }
                            if (athleteBadmintonDetail.getBio() != null) {
                                textView_profession.setText(athleteBadmintonDetail.getBio().getClubOrAcademy());
                                apiAtheliteData.getUserBio(athleteBadmintonDetail.getBio().getCoachName(),
                                        athleteBadmintonDetail.getBio().getClubOrAcademy(), athleteBadmintonDetail.getBio().getStyleOrTypeOfPlay(),
                                        athleteBadmintonDetail.getBio().getDob(), athleteBadmintonDetail.getBio().getHeight(),
                                        athleteBadmintonDetail.getBio().getWeight(), athleteBadmintonDetail.getBio().getGender(),
                                        athleteBadmintonDetail.getBio().getLocation(), athleteBadmintonDetail.getBio().getEmail());
                            }
                            if (athleteBadmintonDetail.getHeader() != null) {
                                apiAtheliteData.getUserHeader(athleteBadmintonDetail.getHeader().getName()
                                        , athleteBadmintonDetail.getHeader().getLevel()
                                        , athleteBadmintonDetail.getHeader().getRank()
                                        , athleteBadmintonDetail.getHeader().getLocation()
                                        , athleteBadmintonDetail.getHeader().getDescription());
                                textView_level.setText(athleteBadmintonDetail.getHeader().getLevel());
                                textText_rank.setText(athleteBadmintonDetail.getHeader().getRank());
                              //  editText_about.setText(athleteBadmintonDetail.getHeader().getDescription());
                            }
                            if (athleteBadmintonDetail.getUser() != null) {
                                apiAtheliteData.getUserData(athleteBadmintonDetail.getUser().getName()
                                        , athleteBadmintonDetail.getUser().getGender()
                                        , athleteBadmintonDetail.getUser().getDob()
                                        , athleteBadmintonDetail.getUser().getProf_name()
                                        , athleteBadmintonDetail.getUser().getSport());
                                textView_name.setText(athleteBadmintonDetail.getUser().getName());
                                UserSport = athleteBadmintonDetail.getUser().getSport();
                                textView_profession.setText(athleteBadmintonDetail.getUser().getSport() + " Player");
                                profileImage = athleteBadmintonDetail.getUser().getUser_image();
                                if (profileImage != null && !profileImage.equals("")) {
                                    Picasso.with(getActivity()).load(profileImage)
                                            .placeholder(R.drawable.default_user).error(R.drawable.default_user)
                                            .into(imageView_profile_pic);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                customProgress.hideProgress();
            }

            @Override
            public void onFailure(Call<UserProfileAtheliteTennisResponse> call, Throwable t) {
                customProgress.hideProgress();
            }
        });


    }

    public void ApicallforSwimming() {

        Call<UserProfileAtheliteResponse> call = apiCall.getAtheliteProfile("getUserProfile", userId, "1");
        customProgress.showProgress(getActivity(), false);
        call.enqueue(new Callback<UserProfileAtheliteResponse>() {
            @Override
            public void onResponse(Call<UserProfileAtheliteResponse> call, Response<UserProfileAtheliteResponse> response) {
                if (getActivity() != null && response != null && response.body() != null) {
                    customProgress.hideProgress();
                    apiAtheliteData = (ApiAtheliteData) getActivity();
                    if (response.body().getData() != null) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PROFILE_DATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(userId, response.body().getData().toString());
                        editor.commit();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getData().toString());
                            Gson gson = new Gson();
                            atheliteDetail = gson.fromJson(jsonObject.toString(), AtheliteDetail.class);
                            achivement = atheliteDetail.getAchivement();
                            header = atheliteDetail.getHeader();
                            bio = atheliteDetail.getBio();
                            if (atheliteDetail.getAchivement() != null) {
                                apiAtheliteData.getAchievements(atheliteDetail.getAchivement().getBestResult(), atheliteDetail.getAchivement().getAwards());
                            }
                            if (atheliteDetail.getLatestResults() != null) {
                                apiAtheliteData.getLatestResultData(atheliteDetail.getLatestResults());
                            }
                            if (atheliteDetail.getBio() != null) {
                                textView_profession.setText(atheliteDetail.getBio().getClubOrAcademy());
                                apiAtheliteData.getUserBio(atheliteDetail.getBio().getCoachName(),
                                        atheliteDetail.getBio().getClubOrAcademy(), atheliteDetail.getBio().getStyleOrTypeOfPlay(),
                                        atheliteDetail.getBio().getDob(), atheliteDetail.getBio().getHeight(),
                                        atheliteDetail.getBio().getWeight(), atheliteDetail.getBio().getGender(),
                                        atheliteDetail.getBio().getLocation(), atheliteDetail.getBio().getEmail());
                            }
                            if (atheliteDetail.getHeader() != null) {
                                apiAtheliteData.getUserHeader(atheliteDetail.getHeader().getName()
                                        , atheliteDetail.getHeader().getLevel()
                                        , atheliteDetail.getHeader().getRank()
                                        , atheliteDetail.getHeader().getLocation()
                                        , atheliteDetail.getHeader().getDescription());
                                textView_level.setText(atheliteDetail.getHeader().getLevel());
                                textText_rank.setText(atheliteDetail.getHeader().getRank());
                             //   editText_about.setText(atheliteDetail.getHeader().getDescription());
                            }
                            if (atheliteDetail.getUserdata() != null) {
                                apiAtheliteData.getUserData(atheliteDetail.getUserdata().getName()
                                        , atheliteDetail.getUserdata().getGender()
                                        , atheliteDetail.getUserdata().getDob()
                                        , atheliteDetail.getUserdata().getProf_name()
                                        , atheliteDetail.getUserdata().getSport());
                                textView_name.setText(atheliteDetail.getUserdata().getName());
                                UserSport = atheliteDetail.getUserdata().getSport();
                                textView_profession.setText(atheliteDetail.getUserdata().getSport());
                                profileImage = atheliteDetail.getUserdata().getUser_image();
                                if (profileImage != null && !profileImage.equals("")) {
                                    Picasso.with(getActivity()).load(profileImage)
                                            .placeholder(R.drawable.default_user).error(R.drawable.default_user)
                                            .into(imageView_profile_pic);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                customProgress.hideProgress();
            }

            @Override
            public void onFailure(Call<UserProfileAtheliteResponse> call, Throwable t) {
                if (getActivity() != null)
                    customProgress.hideProgress();

            }
        });

    }


    public void Apicallfortennis() {

        Call<UserProfileAtheliteTennisResponse> call = apiCall.getAtheliteProfileTennis("getUserProfile", userId, "1");
        customProgress.showProgress(getActivity(), false);
        call.enqueue(new Callback<UserProfileAtheliteTennisResponse>() {
            @Override
            public void onResponse(Call<UserProfileAtheliteTennisResponse> call, Response<UserProfileAtheliteTennisResponse> response) {
                if (getActivity() != null && response != null && response.body() != null) {
                    customProgress.hideProgress();
                    apiAtheliteData = (ApiAtheliteData) getActivity();
                    if (response.body().getData() != null) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PROFILE_DATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(userId, response.body().getData().toString());
                        editor.commit();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getData().toString());
                            Gson gson = new Gson();
                            atheliteTennisDetail = gson.fromJson(jsonObject.toString(), AtheliteTennisDetail.class);
                            achivement = atheliteTennisDetail.getAchivement();
                            header = atheliteTennisDetail.getHeader();
                            bio = atheliteTennisDetail.getBio();
                            if (atheliteTennisDetail.getAchivement() != null) {
                                apiAtheliteData.getAchievements(atheliteTennisDetail.getAchivement().getBestResult(), atheliteTennisDetail.getAchivement().getAwards());
                            }
                            if (atheliteTennisDetail.getLatestResults() != null) {
                                apiAtheliteData.getLatestResultTennisData(atheliteTennisDetail.getLatestResults());
                            }
                            if (atheliteTennisDetail.getBio() != null) {
                                textView_profession.setText(atheliteTennisDetail.getBio().getClubOrAcademy());
                                apiAtheliteData.getUserBio(atheliteTennisDetail.getBio().getCoachName(),
                                        atheliteTennisDetail.getBio().getClubOrAcademy(), atheliteTennisDetail.getBio().getStyleOrTypeOfPlay(),
                                        atheliteTennisDetail.getBio().getDob(), atheliteTennisDetail.getBio().getHeight(),
                                        atheliteTennisDetail.getBio().getWeight(), atheliteTennisDetail.getBio().getGender(),
                                        atheliteTennisDetail.getBio().getLocation(), atheliteTennisDetail.getBio().getEmail());
                            }
                            if (atheliteTennisDetail.getHeader() != null) {
                                apiAtheliteData.getUserHeader(atheliteTennisDetail.getHeader().getName()
                                        , atheliteTennisDetail.getHeader().getLevel()
                                        , atheliteTennisDetail.getHeader().getRank()
                                        , atheliteTennisDetail.getHeader().getLocation()
                                        , atheliteTennisDetail.getHeader().getDescription());
                                textView_level.setText(atheliteTennisDetail.getHeader().getLevel());
                                textText_rank.setText(atheliteTennisDetail.getHeader().getRank());
                              //  editText_about.setText(atheliteTennisDetail.getHeader().getDescription());
                            }
                            if (atheliteTennisDetail.getUserdata() != null) {
                                apiAtheliteData.getUserData(atheliteTennisDetail.getUserdata().getName()
                                        , atheliteTennisDetail.getUserdata().getGender()
                                        , atheliteTennisDetail.getUserdata().getDob()
                                        , atheliteTennisDetail.getUserdata().getProf_name()
                                        , atheliteTennisDetail.getUserdata().getSport());
                                textView_name.setText(atheliteTennisDetail.getUserdata().getName());
                                UserSport = atheliteTennisDetail.getUserdata().getSport();
                                textView_profession.setText(atheliteTennisDetail.getUserdata().getSport() + " Player");
                                profileImage = atheliteTennisDetail.getUserdata().getUser_image();
                                if (profileImage != null && !profileImage.equals("")) {
                                    Picasso.with(getActivity()).load(profileImage)
                                            .placeholder(R.drawable.default_user).error(R.drawable.default_user)
                                            .into(imageView_profile_pic);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                customProgress.hideProgress();
            }

            @Override
            public void onFailure(Call<UserProfileAtheliteTennisResponse> call, Throwable t) {
                customProgress.hideProgress();

            }
        });

    }

    public void Apicallforfootball() {

        Call<UserProfileAtheliteFootballResponse> call = apiCall.getAtheliteProfileFootball("getUserProfile", userId, "1");
        customProgress.showProgress(getActivity(), false);
        call.enqueue(new Callback<UserProfileAtheliteFootballResponse>() {
            @Override
            public void onResponse(Call<UserProfileAtheliteFootballResponse> call, Response<UserProfileAtheliteFootballResponse> response) {
                if (getActivity() != null) {
                    customProgress.hideProgress();
                    if (response != null && response.body() != null) {
                        apiAtheliteData = (ApiAtheliteData) getActivity();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PROFILE_DATA, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(userId, response.body().getData().toString());
                        editor.commit();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getData().toString());
                            Gson gson = new Gson();
                            atheliteFootballDetail = gson.fromJson(jsonObject.toString(), AtheliteFootballDetail.class);
                            achivement = atheliteFootballDetail.getAchivement();
                            header = atheliteFootballDetail.getHeader();
                            bio = atheliteFootballDetail.getBio();
                            if (response.body().getData() != null) {
                                if (atheliteFootballDetail.getAchivement() != null) {
                                    apiAtheliteData.getAchievements(atheliteFootballDetail.getAchivement().getBestResult(), atheliteFootballDetail.getAchivement().getAwards());
                                }
                                if (atheliteFootballDetail.getLatestResults() != null) {
                                    apiAtheliteData.getLatestResultFootballData(atheliteFootballDetail.getLatestResults());
                                }
                                if (atheliteFootballDetail.getBio() != null) {
                                    textView_profession.setText(atheliteFootballDetail.getBio().getClubOrAcademy());
                                    apiAtheliteData.getUserBio(atheliteFootballDetail.getBio().getCoachName(),
                                            atheliteFootballDetail.getBio().getClubOrAcademy(), atheliteFootballDetail.getBio().getStyleOrTypeOfPlay(),
                                            atheliteFootballDetail.getBio().getDob(), atheliteFootballDetail.getBio().getHeight(),
                                            atheliteFootballDetail.getBio().getHeight(), atheliteFootballDetail.getBio().getGender(),
                                            atheliteFootballDetail.getBio().getLocation(), atheliteFootballDetail.getBio().getEmail());
                                }
                                if (atheliteFootballDetail.getHeader() != null) {
                                    apiAtheliteData.getUserHeader(atheliteFootballDetail.getHeader().getName()
                                            , atheliteFootballDetail.getHeader().getLevel()
                                            , atheliteFootballDetail.getHeader().getRank()
                                            , atheliteFootballDetail.getHeader().getLocation()
                                            , atheliteFootballDetail.getHeader().getDescription());
                                    textView_level.setText(atheliteFootballDetail.getHeader().getLevel());
                                    textText_rank.setText(atheliteFootballDetail.getHeader().getRank());
                               //     editText_about.setText(atheliteFootballDetail.getHeader().getDescription());
                                }
                                if (atheliteFootballDetail.getUserdata() != null) {
                                    apiAtheliteData.getUserData(atheliteFootballDetail.getUserdata().getName()
                                            , atheliteFootballDetail.getUserdata().getGender()
                                            , atheliteFootballDetail.getUserdata().getDob()
                                            , atheliteFootballDetail.getUserdata().getProf_name()
                                            , atheliteFootballDetail.getUserdata().getSport());
                                    textView_name.setText(atheliteFootballDetail.getUserdata().getName());
                                    UserSport = atheliteFootballDetail.getUserdata().getSport();
                                    textView_profession.setText(atheliteFootballDetail.getUserdata().getSport() + " Player");
                                    profileImage = atheliteFootballDetail.getUserdata().getUser_image();
                                    if (profileImage != null && !profileImage.equals("")) {
                                        Picasso.with(getActivity()).load(profileImage)
                                                .placeholder(R.drawable.default_user).error(R.drawable.default_user)
                                                .into(imageView_profile_pic);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                customProgress.hideProgress();
            }

            @Override
            public void onFailure(Call<UserProfileAtheliteFootballResponse> call, Throwable t) {
                t.printStackTrace();
                customProgress.hideProgress();
            }
        });

    }


//    public void check_cnnection() {
//
//        if (userId != null && !userId.equals("")) {
//
//            Retrofit retrofitNew = ApiClient.getClient();
//            ApiAtheliteCall apicall = retrofitNew.create(ApiAtheliteCall.class);
//
//            Call<ConnectedUserResponse> connectedUserResponseCall = apicall.getConnectionRequest("connection_status", userId, sharedPreferences1.getString("user_id", ""));
//            Log.d("Connection response", connectedUserResponseCall.request().url().toString());
//            connectedUserResponseCall.enqueue(new Callback<ConnectedUserResponse>() {
//                @Override
//                public void onResponse(Call<ConnectedUserResponse> call, Response<ConnectedUserResponse> response) {
//                    if (response.body().getStatus().equals("1")) {
//
//                        if (response.body().getData().getReq_status().equals("0")) {
//                            layout_profile.setVisibility(View.VISIBLE);
//                        } else if (response.body().getData().getReq_status().equals("1")) {
//                            layout_profile.setVisibility(View.GONE);
//                        }
//
//                    } else {
//
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ConnectedUserResponse> call, Throwable t) {
//                    //  Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
//                }
//            });
//
//        }
//
//
//    }

    //call for shortlist the candidate

    private void shortlist() {
        // here we shortlist the candidate

        sharedPreferences1 = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
        employerId = sharedPreferences1.getString("user_id", "");
        employerName = sharedPreferences1.getString("name", "");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Detail", 0);
        String jobId = sharedPref.getString("jobId", "");
        Retrofit retrofitNew = ApiClient.getClient();
        ApiAtheliteCall apicall = retrofitNew.create(ApiAtheliteCall.class);

        Call<ShortlistCandidate> connectedUserResponseCall = apicall.shortlist_candidate("shortlist", new ShortlistRequest(userId, jobId, "2", "1"));
        Log.d("Connection response", connectedUserResponseCall.request().url().toString());
        customProgress.showProgress(getActivity(), false);

        connectedUserResponseCall.enqueue(new Callback<ShortlistCandidate>() {
            @Override
            public void onResponse(Call<ShortlistCandidate> call, Response<ShortlistCandidate> response) {
                if (response.body().getStatus() == 1) {
                    customProgress.hideProgress();
                    MyToast toast = new MyToast();
                    toast.show(getActivity(), getString(R.string.candidate_shortlisted), false);
                    footer_shortlist.setVisibility(View.GONE);
                } else {
                    customProgress.hideProgress();
                    MyToast toast = new MyToast();
                    toast.show(getActivity(), getString(R.string.not_shortlisted), false);
                }

                Log.e("Tag", "Response in retrofit:::" + response);

            }


            @Override
            public void onFailure(Call<ShortlistCandidate> call, Throwable t) {
                customProgress.hideProgress();
            }
        });

    }


//    @SuppressLint("NewApi")
//    private void selectApplicant() {
//
//
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.job_confirm_dialog);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dialog_alert_background)));
////        final Button button_edit_now = (Button) dialog.findViewById(R.id.edit_now);
////        final Button button_edit_later = (Button) dialog.findViewById(R.id.edit_later);
//        TextView textView_close = (TextView) dialog.findViewById(R.id.close);
//        TextView textView_title_heading = (TextView) dialog.findViewById(R.id.title_heading);
//        TextView textView_title = (TextView) dialog.findViewById(R.id.title);
//        textView_title.setVisibility(View.GONE);
////        button_edit_now.setBackground(getActivity().getDrawable(R.drawable.simple_button_green));
////        button_edit_later.setBackground(getActivity().getDrawable(R.drawable.simple_button_perpel));
////        button_edit_now.setText("YES");
////        button_edit_later.setText("NO");
//        String applicant_name = textView_name.getText().toString().toUpperCase();
//
//        textView_title_heading.setText(getString(R.string.sent_offer) + applicant_name + " ?");
//        dialog.show();
//
//        textView_close.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                dialog.dismiss();
//                return true;
//            }
//        });
//
////        button_edit_now.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////               // send_jobOffer();
////
////
////                dialog.dismiss();
////
////            }
////        });
////
////        button_edit_later.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////
////                dialog.dismiss();
////            }
////        });
//
//
//    }

//    private void send_jobOffer() {
//
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.job_offerdeatil_dialog);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dialog_alert_background)));
//        //final EditText editText_job_title = (EditText) dialog.findViewById(R.id.job_title);
//        final EditText textView_salary = (EditText) dialog.findViewById(R.id.salary);
//        final TextView textView_close = (TextView) dialog.findViewById(R.id.close);
//        final TextView textView_joining_date = (TextView) dialog.findViewById(R.id.joining_date);
//        final ImageView imageView_date = (ImageView) dialog.findViewById(R.id.date_calender);
//        //final EditText editText_other_detail = (EditText) dialog.findViewById(R.id.other_detail);
//        final Button button_submit = (Button) dialog.findViewById(R.id.submit);
//        dialog.show();
//
//        imageView_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar mcurrentDate = Calendar.getInstance();
//                final int mYear = mcurrentDate.get(Calendar.YEAR);
//                final int mMonth = mcurrentDate.get(Calendar.MONTH);
//                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
//                        // TODO Auto-generated method stub
//                            /*      Your code   to get date and time    */
////
//                        calender_Date = String.valueOf(selectedday) + "-" + String.valueOf(selectedmonth + 1) + "-" + String.valueOf(selectedyear);
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                        Date myDate = null;
//                        try {
//                            myDate = dateFormat.parse(calender_Date);
//
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//
//                        String finalDate = dateFormat.format(myDate);
//
//
//                        textView_joining_date.setText(finalDate);
//
//
//                        //textView_startDate.setText(calender_endDate);
//                    }
//                }, mYear, mMonth, mDay);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                mDatePicker.setTitle("Select start date");
//                mDatePicker.show();
//
//            }
//        });
//
//
//        button_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                applied_job_title = editText_job_title.getText().toString();
////                other_deatil = editText_other_detail.getText().toString();
//                salary = textView_salary.getText().toString();
//                joining_date = textView_joining_date.getText().toString();
//
//                Log.e("Tag", "**here select@@ ");
//                sharedPreferences1 = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
//                employerId = sharedPreferences1.getString("user_id", "");
//                employerName = sharedPreferences1.getString("name", "");
//                Log.e("Tag", "job id::" + jobId);
//
////                try {
////
////                    String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("select_applicant", "UTF-8")
////                            + "&" + URLEncoder.encode("applicant_id", "UTF-8") + "=" + URLEncoder.encode(liteuserid, "UTF-8")
////                            + "&" + URLEncoder.encode("employer_id", "UTF-8") + "=" + URLEncoder.encode(employerId, "UTF-8")
////                           // + "&" + URLEncoder.encode("job_title", "UTF-8") + "=" + URLEncoder.encode(job_title, "UTF-8")
////                            + "&" + URLEncoder.encode("job_id", "UTF-8") + "=" + URLEncoder.encode(job_id, "UTF-8")
////                            + "&" + URLEncoder.encode("employer_name", "UTF-8") + "=" + URLEncoder.encode(employerName, "UTF-8")
////                            + "&" + URLEncoder.encode("salary", "UTF-8") + "=" + URLEncoder.encode(salary, "UTF-8")
////                            + "&" + URLEncoder.encode("joining_date", "UTF-8") + "=" + URLEncoder.encode(joining_date, "UTF-8")
////                           // + "&" + URLEncoder.encode("other_deatil", "UTF-8") + "=" + URLEncoder.encode(other_deatil, "UTF-8")
////                            + "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8");
////
////
////                    Log.v("Tag", " list url ::" + data);
////
////
////                    new SelectApplicant_webservice().execute(signup_url, data);
////
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                }
//
//                //api calling for send offer
//
//                Retrofit retrofit = ApiClient.getClient();
//                ApiAtheliteCall call3 = retrofit.create(ApiAtheliteCall.class);
//                Call<ApplicantJobOfferResponse> jobcall = call3.send_offer("send_offer", new SendOfferToApplicant(userId, jobId, salary, joining_date, employerId));
//
//                // Call<AlignInterViewResponse> connectedUserResponseCall = apicall.align_candidate_interView("interview_schedule", new AlignDataRequest(employerID, job_ID, "3", "1",arrayList_shortlisted_candidate,dateOfInterview,msgForInterView,venueDetail));
//                Log.d("Connection response", jobcall.request().url().toString());
//                customProgress.showProgress(getActivity(), false);
//                jobcall.enqueue(new Callback<ApplicantJobOfferResponse>() {
//                    @Override
//                    public void onResponse(Call<ApplicantJobOfferResponse> call, Response<ApplicantJobOfferResponse> response) {
//                        if (response.body().getStatus().equals("1")) {
//                            customProgress.hideProgress();
//                            MyToast toast = new MyToast();
//                            toast.show(getActivity(), "Sent Offer to candidate sucessfully.", false);
//                            layout_sendoffer.setVisibility(View.INVISIBLE);
//                            dialog.cancel();
//
//
//                        } else {
//                            customProgress.hideProgress();
//                            MyToast toast = new MyToast();
//                            toast.show(getActivity(), "Some Technical issue retry after sometime.", false);
//                        }
//
//                        Log.e("Tag", "Response in retrofit:::" + response);
//
//                    }
//
//
//                    @Override
//                    public void onFailure(Call<ApplicantJobOfferResponse> call, Throwable t) {
//
//                        customProgress.hideProgress();
//                    }
//                });
//
//
//                dialog.dismiss();
//            }
//            // }
//
//        });
//        textView_close.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                dialog.dismiss();
//                return true;
//            }
//        });
//
//
//    }

    void afterresponse3(String response)

    {

        customProgress.hideProgress();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equals("Success")) {
              //  offernotification();


//                button_apply.setBackgroundColor(Color.parseColor("#1400AA"));
//                button_apply.setText("ALREADY APPLIED");
//                button_apply.setEnabled(false);

            } else {
                getActivity().onBackPressed();


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

//    @SuppressLint("NewApi")
//    void offernotification() {
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.job_confirm_dialog);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dialog_alert_background)));
//        final Button button_edit_now = (Button) dialog.findViewById(R.id.edit_now);
//        final Button button_edit_later = (Button) dialog.findViewById(R.id.edit_later);
//        TextView textView_close = (TextView) dialog.findViewById(R.id.close);
//        TextView textView_title_heading = (TextView) dialog.findViewById(R.id.title_heading);
//        TextView textView_title = (TextView) dialog.findViewById(R.id.title);
//        textView_title.setVisibility(View.GONE);
//        button_edit_later.setVisibility(View.GONE);
//        button_edit_now.setBackground(getActivity().getDrawable(R.drawable.simple_button_green));
//        button_edit_later.setBackground(getActivity().getDrawable(R.drawable.simple_button_perpel));
//        button_edit_now.setText("OK");
//        //  String applicant_name = hashMap_info.get(keys_info[1]).toString().toUpperCase();
//        textView_title_heading.setText(getString(R.string.offer_send_to) + textView_name.getText().toString().toUpperCase());
//        dialog.show();
//
//        button_edit_now.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                getActivity().onBackPressed();
//
//            }
//        });
//
//
//    }

    void showDialog(String lin, Uri uri) {

        DialogFragment newFragment = Fragment_Share.newInstance(lin, uri);
        newFragment.show(fm, "SHARE");
    }

    @Override
    public void getAchievements(ArrayList<BestResult> bestResult, ArrayList<Award> awards) {

    }

    @Override
    public void getLatestResultData(ArrayList<LatestResults> LatestResults) {

    }

    @Override
    public void getLatestResultTennisData(ArrayList<LatestResultsTennis> LatestResults) {

    }

    @Override
    public void getLatestResultFootballData(ArrayList<LatestResultsFootball> LatestResults) {

    }

    @Override
    public void getOthersData(String description, String level, String location, String name, String rank, String clubOrAcademy, String coachName, String dob, String height, String styleOrTypeOfPlay, String weight) {

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

    private class SelectApplicant_webservice extends AsyncTask<String, Void, String> {

        JSONObject jsonObject_response = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            customProgress.showProgress(getActivity(), false);

        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("Tag", params[0] + params[1]);

            String string_url = params[0];
            String string_data = params[1];
            Log.e("Tag", "! string_url= " + string_url);
            Log.e("Tag", "! string_data= " + string_data);
            BufferedReader bufferedReader = null;
            String response = null;
            JSONObject jsonObject_response = null;
            try {

                //server connection
                URL url = new URL(string_url);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                // sending data to server
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(string_data);
                wr.flush();

                // Get the server response

                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = bufferedReader.readLine()) != null) {

                    // Append server response in string

                    sb.append(line + "\n");

                }

                // Append Server Response To Content String

                response = sb.toString();
                Log.e("Tag", "response= " + response);
//                jsonObject_response = new JSONObject(response);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }


            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.e("Tag", response);
            customProgress.hideProgress();
            afterresponse3(response);
        }
    }


}

