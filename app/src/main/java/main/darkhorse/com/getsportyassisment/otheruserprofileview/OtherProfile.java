package main.darkhorse.com.getsportyassisment.otheruserprofileview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

//import darkhorsesports.getsporty.R;
//import darkhorsesports.getsporty.athleteprofilemodelclassess.ApiAtheliteCall;
//import darkhorsesports.getsporty.brodcastreceiver.NetworkBrodcastLisner;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachDetail;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachEducation;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachExperience;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachHeader;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ConnectToUserResponse;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ConnectedUserResponse;
//import darkhorsesports.getsporty.coachprofilemodelclassess.FormalEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.GenericResponse;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ImageUploadRequest;
//import darkhorsesports.getsporty.coachprofilemodelclassess.OtherCertificationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.PlayerExperienceData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.SportEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.UserProfileCoachEditRequest;
//import darkhorsesports.getsporty.coachprofilemodelclassess.UserProfileCoachEditResponse;
//import darkhorsesports.getsporty.coachprofilemodelclassess.UserProfileCoachResponse;
//import darkhorsesports.getsporty.coachprofilemodelclassess.WorkExperienceData;
//import darkhorsesports.getsporty.customclasses.CustomDialogNoInternet;
//import darkhorsesports.getsporty.customclasses.CustomProgress;
//import darkhorsesports.getsporty.customclasses.DateConversion;
//import darkhorsesports.getsporty.customclasses.MainUrls;
//import darkhorsesports.getsporty.customclasses.MyToast;
//import darkhorsesports.getsporty.fragments.Fragment_Share;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.MainUrls;
import main.darkhorse.com.getsportyassisment.UtilsFile.MyToast;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.brodcastreceiver.NetworkBrodcastLisner;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachDetail;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachEducation;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachExperience;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachHeader;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ConnectToUserResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ConnectedUserResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.GenericResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.GlobalUserProfileData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ImageUploadRequest;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditRequest;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomDialogNoInternet;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.fragment.Fragment_Share;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static main.darkhorse.com.getsportyassisment.custom_classes.DateConversion.context;


public class OtherProfile extends Fragment implements View.OnClickListener {
    public static final String MY_PREFS_NAME = "Dashboard_prefs";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "indiacater";
    private static final String signup_url = MainUrls.mainUrl + "?";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 9000;
    private static final int REQUEST_CAMERA = 9001;
    private static final int SELECT_FILE = 9002;
    //    TextView textView_title, textView_level, textText_rank;
    GlobalUserProfileData globalUserProfileData = GlobalUserProfileData.getInstance();
    String liteuserid, Connectionid, liteuserprofid, liteusersport, othersUserId, job_title, job_id, job_status, indiacater;

    View view1;
    LinearLayout snackbar_linear, create_job, layout_create_tournamnet, layout_create_event, snackbar_linear_layout;
    RelativeLayout item;
    TextView more, tv_create_job, tv_create_tournament, tv_create_event, tv_snackbar_more;
    LinearLayout layout_more;
    LinearLayout layout_profile;
    LinearLayout layout_profile_job;
    Button accept_offer, reject_offer;
    Button button_select;
    Button button_reject;
    ProgressDialog progressDialog;
    String applied_job_title;
    String salary;
    String joining_date;
    String other_deatil;
    SharedPreferences sharedPreferences1;
    String employerId;
    String employerName;
    Dialog dialogloader;
    CustomProgress customProgress;
    ProgressBar processbar;
    TextView tv_persentage;
    Toolbar toolbar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private SharedPreferences sharedPreferences;
    private String user_id, UserName, sport;
    private ImageView imageView_profile_pic;
    private ImageView editHeader;
    private TextView textView_name;
    private TextView textView_profession;
    private TextView textView_location;
    private TextView editText_about;
    private ImageView imageView_pic_blur;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Retrofit retrofit;
    private CoachEducation Education;
    private CoachExperience Experience;
    private CoachHeader Header;
    private ApiAtheliteCall apiCall;
    private String gender;
    private String dob;
    private String userChoosenTask;
    private int progressStatus = 0;
    private String encoded;

    public OtherProfile() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OtherProfile newInstance(String param1, String param2) {
        OtherProfile fragment = new OtherProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static OtherProfile newInstance(String Connectionid, String liteuserid, String liteuserprofid, String indicater) {
        OtherProfile athleteProfile = new OtherProfile();
        Bundle bundle = new Bundle();
        bundle.putString("Connectionid", Connectionid);
        bundle.putString("liteuserid", liteuserid);
        bundle.putString("liteuserprofid", liteuserprofid);
        bundle.putString(ARG_PARAM3, indicater);
        athleteProfile.setArguments(bundle);
        return athleteProfile;

    }

    public static OtherProfile newInstance(String job_applicant_id, String job_applicant_profid,
                                           String job_applicant_sport, String job_applicant_name, String job_title, String job_id, String job_status, String indicater) {
        OtherProfile athleteProfile = new OtherProfile();
        Bundle bundle = new Bundle();

        bundle.putString("liteuserid", job_applicant_id);
        bundle.putString("liteuserprofid", job_applicant_profid);
        bundle.putString("lite_user_sport", job_applicant_sport);
        bundle.putString("liteuser_name", job_applicant_name);
        bundle.putString("job_title", job_title);
        bundle.putString("job_id", job_id);
        bundle.putString("job_status", job_status);
        bundle.putString(ARG_PARAM3, indicater);
        athleteProfile.setArguments(bundle);
        return athleteProfile;

    }

    public static OtherProfile newInstance(String userid) {
        OtherProfile athleteProfile = new OtherProfile();

        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);
        athleteProfile.setArguments(bundle);
        return athleteProfile;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            othersUserId = getArguments().getString("userid");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            indiacater = getArguments().getString(ARG_PARAM3);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            liteuserid = getArguments().getString("liteuserid");
            Connectionid = getArguments().getString("Connectionid");
            liteuserprofid = getArguments().getString("liteuserprofid");
            job_title = getArguments().getString("job_title");
            job_id = getArguments().getString("job_id");
            job_status = getArguments().getString("job_status");
        }


    }
    ImageView   share_profile;
    private FragmentManager fm;
    CustomDialogNoInternet nointernet;
    String profileImage;
    Uri uri;
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_other_profile, container, false);
        nointernet = CustomDialogNoInternet.getInstance();
        fm = getFragmentManager();
        customProgress = CustomProgress.getInstance();
        processbar = (ProgressBar) rootView.findViewById(R.id.pb);
        tv_persentage = (TextView) rootView.findViewById(R.id.percentage);
        share_profile = (ImageView) rootView.findViewById(R.id.share_profile_pdf);
        customProgress = CustomProgress.getInstance();
        sharedPreferences1 = getActivity().getSharedPreferences("Dashboard_prefs", 0);
        UserName = sharedPreferences1.getString("name", "");
        user_id = sharedPreferences1.getString("user_id", null);
        sport = sharedPreferences1.getString("sport", null);

//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        ImageView logo_image = (ImageView) toolbar.findViewById(R.id.logs);
//        logo_image.setVisibility(View.GONE);




        instantiation();
        layout_profile.setVisibility(View.GONE);
        initView();
        check_cnnection();
        view1 = getLayoutInflater(savedInstanceState).inflate(R.layout.student_profile_my_snackbar, null);
        create_job = (LinearLayout) view1.findViewById(R.id.layout_share);
        layout_create_tournamnet = (LinearLayout) view1.findViewById(R.id.layout_for);
        layout_create_event = (LinearLayout) view1.findViewById(R.id.diet_log_view);
        snackbar_linear = (LinearLayout) view1.findViewById(R.id.layout_performance);
        snackbar_linear_layout = (LinearLayout) view1.findViewById(R.id.snackbar_linear);
        tv_create_job = (TextView) view1.findViewById(R.id.tv_create_job);
        tv_create_tournament = (TextView) view1.findViewById(R.id.tv_create_tournament);
        tv_create_event = (TextView) view1.findViewById(R.id.tv_create_event);
        tv_snackbar_more = (TextView) view1.findViewById(R.id.tv_snackbar_more);
        view1.setLayoutParams(new Snackbar.SnackbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();


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

//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.user_profile,new PerformanceAssessmentFragment()).addToBackStack("performance_listing").commit();

//                Intent i = new Intent(getActivity(), PerformanceActivity.class);
//                i.putExtra("gender",gender);
//                //i.putExtra("dob",bio.getDob());
//                i.putExtra("dob", dob);
//                i.putExtra("studentid",user_id);
//                i.putExtra("sport",sport);
//                startActivity(i);

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

        layout_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), ActivityCreateEvent.class);
//                startActivity(i);

            }
        });





        accept_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<ConnectToUserResponse> connectToUserResponseCall = apiCall.sendConnectionResponseRequest("request_response", Connectionid, "1", "M");
                Log.d("Connection URL", connectToUserResponseCall.request().url().toString());
                connectToUserResponseCall.enqueue(new Callback<ConnectToUserResponse>() {
                    @Override
                    public void onResponse(Call<ConnectToUserResponse> call, Response<ConnectToUserResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getContext(), "Connection Request Sent", Toast.LENGTH_SHORT).show();
                                accept_offer.setText("ACCEPT CONNECT");
                                accept_offer.setEnabled(false);
                                accept_offer.setClickable(false);
                                reject_offer.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getContext(), "Sorry for Inconvenience!! Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectToUserResponse> call, Throwable t) {
                        //  Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        reject_offer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();


            }
        });
        share_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String link = "http://getsporty.in/html_pdf/example_001.php?id="+ user_id;
                Bitmap bm = DateConversion.screenShot(rootView);
                File file = DateConversion.saveBitmap(bm, "mantis_image.png");

                if (Build.VERSION.SDK_INT >= 21) {
                    uri = FileProvider.getUriForFile(context, "darkhorsesports.getsporty", file);
                    showDialog(link, uri);
                } else {
                    uri = Uri.fromFile(file);
                    showDialog(link, uri);
                }


            }
        });

        Boolean isConnected = NetworkBrodcastLisner.checkConnection();
        connectNetwork(isConnected);

        return rootView;
    }

    private void connectNetwork(boolean isConnected) {

        nointernet.hideProgress();
        customProgress.hideProgress();
        if (isConnected) {
            initapicall();
        } else {

//            networkdialog = Alert.newInstance();
//            networkdialog.show(getSupportFragmentManager(), "NEWUSER");
            nointernet.showProgress(getActivity(), false);
//            professionDataArray.clear();
//            adapter = new MyConnectionsAdapter(professionDataArray);
//            connectionsList.setAdapter(adapter);


        }


    }

    public void initapicall()
    {
        if (indiacater != null)
        {
            switch (indiacater)
            {

                case "1":

                    item.setVisibility(View.GONE);
                    layout_profile_job.setVisibility(View.GONE);
                    editHeader.setVisibility(View.GONE);
                    imageView_profile_pic.setClickable(false);
                    OtherUserProfile(liteuserid);
                    break;
                case "2":

                    break;
                case "3":
                    if (job_status.equals("1")) {


                        item.setVisibility(View.GONE);
                        layout_profile_job.setVisibility(View.VISIBLE);
                        editHeader.setVisibility(View.GONE);
                        imageView_profile_pic.setClickable(false);

                        OtherUserProfile(liteuserid);
                    } else {

                        item.setVisibility(View.GONE);
                        layout_profile_job.setVisibility(View.GONE);
                        editHeader.setVisibility(View.GONE);
                        imageView_profile_pic.setClickable(false);

                        OtherUserProfile(liteuserid);
                    }

                    break;
                case "4":


                    item.setVisibility(View.GONE);
                    layout_profile_job.setVisibility(View.GONE);
                    editHeader.setVisibility(View.GONE);
                    imageView_profile_pic.setClickable(false);
                    OtherUserProfile(liteuserid);


                    break;
                case "5":
                    break;

                case "13":


                    item.setVisibility(View.GONE);
                    layout_profile_job.setVisibility(View.GONE);
                    editHeader.setVisibility(View.GONE);
                    imageView_profile_pic.setClickable(false);
                    OtherUserProfile(liteuserid);
                    break;
            }
        }
        else
        {
            OtherUserProfile(user_id);
        }
    }
    void showDialog(String lin,Uri uri) {

        DialogFragment newFragment = Fragment_Share.newInstance(lin,uri);
        newFragment.show(fm, "SHARE");
    }
    private void initView() {

        tabLayout.addTab(tabLayout.newTab().setText(R.string.education));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.experience));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.other));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final OtherProfileAdapter otherProfileAdapter = new OtherProfileAdapter(getChildFragmentManager(), 3);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(otherProfileAdapter);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(getContext());
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

                tabTextView.setText(tab.getText());

                // First tab is the selected tab, so if i==0 then set BOLD typeface
                if (i == 0) {
                    tabTextView.setTypeface(null, Typeface.BOLD);
                    tabTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.toolbar_color_home));
                }

            }

        }


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.toolbar_color_home));
        tabLayout.setTabTextColors(getResources().getColor(R.color.text_color), getResources().getColor(R.color.toolbar_color_home));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.toolbar_color_home));
                tabLayout.setTabTextColors(getResources().getColor(R.color.text_color), getResources().getColor(R.color.toolbar_color_home));
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();
                assert text != null;
                text.setTypeface(null, Typeface.BOLD);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                tabLayout.setTabTextColors(Color.parseColor("#2196f3"), Color.parseColor("#000000"));
                TextView text = (TextView) tab.getCustomView();

                assert text != null;
                text.setTypeface(null, Typeface.NORMAL);
                text.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color));


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        editHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenHeaderDialog();
            }
        });
    }

    private void OpenHeaderDialog() {


        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.coach_profile_header_details);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designationHeader);

        final TextInputEditText description = (TextInputEditText) dialog.findViewById(R.id.descriptionHeader);
        final TextInputEditText description1 = (TextInputEditText) dialog.findViewById(R.id.descriptionHeader1);
        final TextInputEditText description2 = (TextInputEditText) dialog.findViewById(R.id.descriptionHeader2);
        final AutoCompleteTextView location = (AutoCompleteTextView) dialog.findViewById(R.id.locationHeader);
        final TextInputEditText academyHeader = (TextInputEditText) dialog.findViewById(R.id.academyNameHeader);



        designation.setText(globalUserProfileData.designation);
        location.setText(globalUserProfileData.location);


        String total = globalUserProfileData.description;

        String substring = new String();
        if (total.length() > 0 && total.length() <= 50) {
            substring = total;
            description.setText(substring.trim());
        } else if (total.length() > 50 && total.length() <= 100) {
            substring = total.substring(0, 50);
            String substring1 = total.substring(50, total.length());
            description.setText(substring.trim());
            description1.setText(substring1.trim());
        } else if (total.length() > 100 && total.length() <= 160) {
            substring = total;
            String substring1 = total.substring(50, 100);
            String substring2 = total.substring(100, total.length());
            description.setText(substring.trim());
            description1.setText(substring1.trim());
            description2.setText(substring2.trim());
        }


        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 49) {
                    description1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        description1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 49) {
                    description2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
        final TextView close = (TextView) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog.show();

        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (designation.getText().toString().length() > 0 && description.getText().toString().length() > 0 && academyHeader.getText().toString().length() > 0 && location.getText().toString().length() > 0) {
                    CoachHeader coachHeader = new CoachHeader(designation.getText().toString(), academyHeader.getText().toString(), location.getText().toString(), description.getText().toString() + " " + description1.getText().toString() + " " + description2.getText().toString());
                    globalUserProfileData.designation = designation.getText().toString();
                    globalUserProfileData.description = description.getText().toString() + " " + description1.getText().toString() + " " + description2.getText().toString();
                    globalUserProfileData.acamedy = academyHeader.getText().toString();
                    globalUserProfileData.location = location.getText().toString();

                    textView_profession.setText(globalUserProfileData.designation);
                    textView_location.setText(globalUserProfileData.location);
                    editText_about.setText(globalUserProfileData.description);
                    updateData();
                    dialog.dismiss();
                } else {
                    MyToast toast = new MyToast();
                    toast.show(getContext(), "Fill All Fields", false);
                }
            }
        });

    }

    public void updateData() {

        Retrofit retrofit = ApiClient.getClient();
        ApiAtheliteCall call = retrofit.create(ApiAtheliteCall.class);

        Education = new CoachEducation(globalUserProfileData.sportEducationGlobal, globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal);
        Experience = new CoachExperience(globalUserProfileData.workExperienceGlobal, globalUserProfileData.experienceAsPlayerGlobal);//playerExperience is null as it  is "other" profile
        Header = new CoachHeader(globalUserProfileData.designation, globalUserProfileData.acamedy, globalUserProfileData.location, globalUserProfileData.description);
        Call<UserProfileCoachEditResponse> editUserData = call.editProfileData("editUserData", user_id, "2", new UserProfileCoachEditRequest(Education, Experience, Header));

        Log.d("EditProfileHitUrl", editUserData.request().url().toString());

        editUserData.enqueue(new Callback<UserProfileCoachEditResponse>() {
            @Override
            public void onResponse(Call<UserProfileCoachEditResponse> call, Response<UserProfileCoachEditResponse> response) {

                Log.d("Updated", response.body().getData().toString());
            }

            @Override
            public void onFailure(Call<UserProfileCoachEditResponse> call, Throwable t) {
                Log.d("Error", "Error");
            }
        });
    }

    private void instantiation() {

        imageView_profile_pic = (ImageView) rootView.findViewById(R.id.profile_pic);
        imageView_profile_pic.setOnClickListener(this);
        editHeader = (ImageView) rootView.findViewById(R.id.edit_header);

        textView_name = (TextView) rootView.findViewById(R.id.name);
        textView_profession = (TextView) rootView.findViewById(R.id.profession);
        textView_location = (TextView) rootView.findViewById(R.id.location);
        editText_about = (TextView) rootView.findViewById(R.id.about);
        imageView_pic_blur = (ImageView) rootView.findViewById(R.id.profile_pic_blur);
        viewPager = (android.support.v4.view.ViewPager) rootView.findViewById(R.id.myViewOther);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_other);
        item = (RelativeLayout) rootView.findViewById(R.id.view2);
        layout_profile = (LinearLayout) rootView.findViewById(R.id.footer_layout_profile);
        layout_profile_job = (LinearLayout) rootView.findViewById(R.id.footer_layout_profile_job);
        layout_more = (LinearLayout) rootView.findViewById(R.id.more_layout);
        button_select = (Button) rootView.findViewById(R.id.select);
        button_reject = (Button) rootView.findViewById(R.id.reject);
        button_select.setOnClickListener(this);
        button_reject.setOnClickListener(this);
        accept_offer = (Button) rootView.findViewById(R.id.accept_offer);
        reject_offer = (Button) rootView.findViewById(R.id.reject_offer);
        retrofit = ApiClient.getClient();
        apiCall = retrofit.create(ApiAtheliteCall.class);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           // case R.id.back_arrow:
//                Intent i = new Intent(getActivity(), BaseActivity.class);
//                startActivity(i);
             //   break;
          //  case R.id.edit_header_detail:
//                show_header_detail();
              //  break;
            case R.id.profile_pic:

                DateConversion.showImage(getContext(),profileImage);
                //openCameraDialog();
                break;
            case R.id.select:
                selectApplicant();


                break;
            case R.id.reject:
              //  rejectApplicant();
                break;


        }
    }

    private void openCameraDialog() {

        final CharSequence[] items = {"Take Photo", "Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                userChoosenTask = items[item].toString();
                boolean result1 = checkPermission1();
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result1 == true) {
                        cameraIntent();
                    } else {
                        Toast.makeText(getActivity(), "Please Grant the permission for Camera.", Toast.LENGTH_SHORT).show();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    boolean result2 = checkPermission2();

                    if (result2 == true) {
                        galleryIntent();
                    } else {
                        Toast.makeText(getActivity(), "Please Grant the permission for Gallery.", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
        builder.show();
    }

    public boolean checkPermission1() {
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

            return false;
        } else {
            return true;
        }

    }

    public boolean checkPermission2() {
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            return false;
        } else {
            return true;
        }

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE || requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (userChoosenTask.equals("Take Photo")) {
                    cameraIntent();
                }
                if (userChoosenTask.equals("Choose from Library")) {
                    galleryIntent();
                }
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_FILE) {
            Bitmap bm = null;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //imageView_profile_pic.setImageBitmap(bm);


            try {
                saveOnServer(bm);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Profile Pic Not Updated", Toast.LENGTH_LONG).show();
            } catch (OutOfMemoryError e) {
                Toast.makeText(getActivity(), "Profile Pic Not Updated", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_CANCELED)
                return;

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // imageView_profile_pic.setImageBitmap(thumbnail);

            try {
                saveOnServer(thumbnail);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Profile Pic Not Updated", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void saveOnServer(final Bitmap bm) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        try {
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Retrofit retrofit = ApiClient.getClient();
            ApiAtheliteCall call = retrofit.create(ApiAtheliteCall.class);
            Call<GenericResponse> imageupload = call.saveImage("imageupload", user_id, new ImageUploadRequest(encoded));
            Log.d("URLTAG", imageupload.request().url().toString());
            customProgress.showProgress(getActivity(), false);

            imageupload.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        imageView_profile_pic.setImageBitmap(bm);
                        imageView_pic_blur.setImageBitmap(bm);
                        SharedPreferences.Editor edit = sharedPreferences1.edit();
                        edit.putString("image", response.body().getData().getUser_image());
                        edit.commit();
                        customProgress.hideProgress();
                    } else {
                        customProgress.hideProgress();
                        Toast.makeText(getActivity(), "Profile Pic Not Updated", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Profile Pic Not Updated", Toast.LENGTH_LONG).show();
                    customProgress.hideProgress();
                }
            });
        } catch (OutOfMemoryError e) {

            System.gc();
            Toast.makeText(getActivity(), "in catch block", Toast.LENGTH_LONG).show();
            byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Retrofit retrofit = ApiClient.getClient();
            ApiAtheliteCall call = retrofit.create(ApiAtheliteCall.class);
            Call<GenericResponse> imageupload = call.saveImage("imageupload", user_id, new ImageUploadRequest(encoded));
            Log.d("URLTAG", imageupload.request().url().toString());
            customProgress.showProgress(getActivity(), false);

            imageupload.enqueue(new Callback<GenericResponse>() {
                @Override
                public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                    if (response.body().getStatus().equals("1")) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        imageView_profile_pic.setImageBitmap(bm);
                        imageView_pic_blur.setImageBitmap(bm);
                        SharedPreferences.Editor edit = sharedPreferences1.edit();
                        edit.putString("ProfileImage", response.body().getData().getUser_image());
                        edit.commit();
                        customProgress.hideProgress();
                    } else {
                        customProgress.hideProgress();
                        Toast.makeText(getActivity(), "Profile Pic Not Updated", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    // Toast.makeText(getActivity(), "Profile Pic Not Updated", Toast.LENGTH_LONG).show();
                    customProgress.hideProgress();
                }
            });

        }


    }

    public void OtherUserProfile(final String userid) {
        retrofit = ApiClient.getClient();
        ApiAtheliteCall call = retrofit.create(ApiAtheliteCall.class); //used coach profile response due to both being almost same except player
        // as experience being the only one not present in this profile

        final Call<UserProfileCoachResponse> getUserProfile = call.getCoachProfile("getUserProfile", userid, "13");
        customProgress.showProgress(getActivity(), false);

        Log.d("otherProfileUrl", getUserProfile.request().url().toString());
        getUserProfile.enqueue(new Callback<UserProfileCoachResponse>() {
            @Override
            public void onResponse(Call<UserProfileCoachResponse> call, Response<UserProfileCoachResponse> response) {
                customProgress.hideProgress();
                CoachDetail data = response.body().getData();

                OnFragmentInteractionListener sendToActivity = (OnFragmentInteractionListener) getActivity();
                globalUserProfileData.sportEducationGlobal = new ArrayList<SportEducationData>();
                globalUserProfileData.formalEducationGlobal = new ArrayList<FormalEducationData>();
                globalUserProfileData.otherCertificationGlobal = new ArrayList<OtherCertificationData>();
                globalUserProfileData.workExperienceGlobal = new ArrayList<WorkExperienceData>();
                globalUserProfileData.experienceAsPlayerGlobal = new ArrayList<PlayerExperienceData>();
                globalUserProfileData.designation = "";
                globalUserProfileData.description = "";
                globalUserProfileData.acamedy = "";
                globalUserProfileData.location = "";

                if (response.body().getData() != null) {


                    if (response.body().getData().getEducation() != null) {
                        globalUserProfileData.sportEducationGlobal = response.body().getData().getEducation().getSportEducation();
                        globalUserProfileData.formalEducationGlobal = response.body().getData().getEducation().getFormalEducation();
                        globalUserProfileData.otherCertificationGlobal = response.body().getData().getEducation().getOtherCertification();
                    }
                    if (response.body().getData().getExperience() != null) {
                        globalUserProfileData.workExperienceGlobal = response.body().getData().getExperience().getWorkExperience();
                        globalUserProfileData.experienceAsPlayerGlobal = response.body().getData().getExperience().getExperienceAsPlayer();
                    }
                    if (response.body().getData().getHeaderDetails() != null) {
                        globalUserProfileData.designation = response.body().getData().getHeaderDetails().getDesignation();
                        globalUserProfileData.description = response.body().getData().getHeaderDetails().getDescription();
                        globalUserProfileData.acamedy = response.body().getData().getHeaderDetails().getAcamedy();
                        globalUserProfileData.location = response.body().getData().getHeaderDetails().getLocation();

                        String designation = globalUserProfileData.designation;
                        if (!designation.equals("")) {
                            designation.toLowerCase();
                            designation = Character.toString(designation.charAt(0)).toUpperCase() + designation.substring(1);
                            textView_profession.setText(designation);

                        } else {

                        }
                        String location = globalUserProfileData.location;
                        if (!location.equals("")) {
                            location.toLowerCase();
                            location = Character.toString(location.charAt(0)).toUpperCase() + location.substring(1);
                            textView_location.setText(location);

                        } else {

                        }

                        String description = globalUserProfileData.description;
                        if (!description.equals("")) {
                            description.toLowerCase();
                            description = Character.toString(description.charAt(0)).toUpperCase() + description.substring(1);
                            editText_about.setText(description);

                        } else {

                        }


                    }
                    if (response.body().getData().getUser() != null && sendToActivity != null) {
                        gender = response.body().getData().getUser().getGender();
                        dob = response.body().getData().getUser().getDob();

                        sendToActivity.sendOthersDataOthersProfile(response.body().getData().getUser().getDob()
                                , response.body().getData().getUser().getGender()
                                , response.body().getData().getUser().getProf_name()
                                , response.body().getData().getUser().getSport()
                                , response.body().getData().getUser().getLink()
                                , response.body().getData().getUser().getAge_group_coached()
                                , response.body().getData().getUser().getLanguages_known()
                                , userid);


                        String profession = response.body().getData().getUser().getName() + " " + response.body().getData().getUser().getSport();//+ " " + response.body().getData().getUser().getProf_name();
                        String title = profession.toLowerCase();
                        title = Character.toString(title.charAt(0)).toUpperCase() + title.substring(1);
                        getActivity().setTitle(title);

                        String name = response.body().getData().getUser().getName();
                        if (!name.equals("")) {
                            name.toLowerCase();
                            name = Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
                            textView_name.setText(name);

                        } else {

                        }
                        String profession_name = response.body().getData().getUser().getProf_name();
                        if (!profession_name.equals("")) {
                            profession.toLowerCase();
                            profession_name = Character.toString(profession_name.charAt(0)).toUpperCase() + profession_name.substring(1);
                            textView_profession.setText(profession_name);

                        } else {

                        }


                         profileImage = response.body().getData().getUser().getUser_image();

                        if (profileImage != null && !profileImage.equals("")) {

                            Picasso.with(getActivity()).load(profileImage)
                                    .placeholder(R.drawable.account_blank).error(R.drawable.account_blank)
                                    .into(imageView_profile_pic);

                            Picasso.with(getActivity()).load(profileImage)
                                    .placeholder(R.drawable.account_blank).error(R.drawable.account_blank)
                                    .into(imageView_pic_blur);

                        }
                        String profile = String.valueOf(response.body().getData().getProfile());
                        progressStatus = (int) response.body().getData().getProfile();
                        tv_persentage.setText("Profile Completeness  " + profile + "%");
                        processbar.setProgress(progressStatus);
                    }
                }

                if (sendToActivity != null) {
                    sendToActivity.sendEducationOthersProfile(globalUserProfileData.sportEducationGlobal, globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal, userid);
                    sendToActivity.sendExperienceOthersProfile(globalUserProfileData.workExperienceGlobal, userid);
                }


            }

            @Override
            public void onFailure(Call<UserProfileCoachResponse> call, Throwable t) {
                customProgress.hideProgress();
            }
        });
    }

    public void check_cnnection() {

        if (liteuserid != null && !liteuserid.equals("")) {

            Retrofit retrofitNew = ApiClient.getClient();
            ApiAtheliteCall apicall = retrofitNew.create(ApiAtheliteCall.class);

            Call<ConnectedUserResponse> connectedUserResponseCall = apicall.getConnectionRequest("connection_status", liteuserid, sharedPreferences1.getString("user_id", ""));
            Log.d("Connection response", connectedUserResponseCall.request().url().toString());
            connectedUserResponseCall.enqueue(new Callback<ConnectedUserResponse>() {
                @Override
                public void onResponse(Call<ConnectedUserResponse> call, Response<ConnectedUserResponse> response) {
                    if (response.body().getStatus().equals("1")) {

                        if (response.body().getData().getReq_status().equals("0")) {
                            accept_offer.setVisibility(View.VISIBLE);
                            reject_offer.setVisibility(View.VISIBLE);
                        } else if (response.body().getData().getReq_status().equals("1")) {
                            layout_profile.setVisibility(View.GONE);
//                            accept_offer.setText("ACCEPT CONNECT");
//                            accept_offer.setEnabled(false);
//                            accept_offer.setClickable(false);
//                            reject_offer.setVisibility(View.GONE);

                        }

                    } else {


                    }
                }

                @Override
                public void onFailure(Call<ConnectedUserResponse> call, Throwable t) {
//                    progressDialog.dismiss();
                    //  Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });

        }


    }

    @SuppressLint("NewApi")
    private void selectApplicant() {


        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.job_confirm_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dialog_alert_background)));
//        final Button button_edit_now = (Button) dialog.findViewById(R.id.edit_now);
//        final Button button_edit_later = (Button) dialog.findViewById(R.id.edit_later);
        TextView textView_close = (TextView) dialog.findViewById(R.id.close);
        TextView textView_title_heading = (TextView) dialog.findViewById(R.id.title_heading);
        TextView textView_title = (TextView) dialog.findViewById(R.id.title);
        textView_title.setVisibility(View.GONE);
//        button_edit_now.setBackground(getActivity().getDrawable(R.drawable.simple_button_green));
//        button_edit_later.setBackground(getActivity().getDrawable(R.drawable.simple_button_perpel));
//        button_edit_now.setText("YES");
//        button_edit_later.setText("NO");
        String applicant_name = textView_name.getText().toString().toUpperCase();
        textView_title_heading.setText("DO YOU WANT TO SEND THIS \n OFFER OF EMPLOYMENT TO\n" + applicant_name + " ?");
        dialog.show();
//        button_edit_now.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                send_jobOffer();
//
//
//                dialog.dismiss();
//
//            }
//        });
//
//        button_edit_later.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                dialog.dismiss();
//            }
//        });


    }

//    private void send_jobOffer() {
//
//        final Dialog dialog = new Dialog(getActivity());
//
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//
//
//        dialog.setContentView(R.layout.job_offerdeatil_dialog);
//
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dialog_alert_background)));
//        final EditText editText_job_title = (EditText) dialog.findViewById(R.id.job_title);
//        final EditText textView_salary = (EditText) dialog.findViewById(R.id.salary);
//        final TextView textView_close = (TextView) dialog.findViewById(R.id.close);
//        final EditText textView_joining_date = (EditText) dialog.findViewById(R.id.joining_date1);
//        final EditText editText_other_detail = (EditText) dialog.findViewById(R.id.other_detail);
//        final Button button_submit = (Button) dialog.findViewById(R.id.submit);
//        editText_job_title.setText(job_title);
//        editText_job_title.setFocusable(false);
//        editText_job_title.setClickable(false);
//        textView_close.setVisibility(View.GONE);
//
//
//
//        dialog.show();
//
//
//        button_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                applied_job_title = editText_job_title.getText().toString();
//                salary = textView_salary.getText().toString();
//                joining_date = textView_joining_date.getText().toString();
//                other_deatil = editText_other_detail.getText().toString();
//                Log.e("Tag", "**here select@@ ");
//                sharedPreferences1 = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
//                employerId = sharedPreferences1.getString("user_id", "");
//                employerName = sharedPreferences1.getString("name", "");
//
//                try {
//
//                    String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("select_applicant", "UTF-8")
//                            + "&" + URLEncoder.encode("applicant_id", "UTF-8") + "=" + URLEncoder.encode(liteuserid, "UTF-8")
//                            + "&" + URLEncoder.encode("employer_id", "UTF-8") + "=" + URLEncoder.encode(employerId, "UTF-8")
//                            + "&" + URLEncoder.encode("job_title", "UTF-8") + "=" + URLEncoder.encode(job_title, "UTF-8")
//                            + "&" + URLEncoder.encode("job_id", "UTF-8") + "=" + URLEncoder.encode(job_id, "UTF-8")
//                            + "&" + URLEncoder.encode("employer_name", "UTF-8") + "=" + URLEncoder.encode(employerName, "UTF-8")
//                            + "&" + URLEncoder.encode("salary", "UTF-8") + "=" + URLEncoder.encode(salary, "UTF-8")
//                            + "&" + URLEncoder.encode("joining_date", "UTF-8") + "=" + URLEncoder.encode(joining_date, "UTF-8")
//                            + "&" + URLEncoder.encode("other_deatil", "UTF-8") + "=" + URLEncoder.encode(other_deatil, "UTF-8")
//                            + "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8");
//
//
//                    Log.v("Tag", " list url ::" + data);
//
//
//                    new SelectApplicant_webservice().execute(signup_url, data);
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//
//                dialog.dismiss();
//            }
//            // }
//
//        });
//
//
//        textView_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//
//
//    }

    void afterresponse3(String response)

    {
        progressDialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equals("Success")) {
//                offernotification();


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
//        textView_title_heading.setText("THE OFFER HAS BEEN SEND TO \n" + textView_name.getText().toString().toUpperCase());
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

//    public void rejectApplicant() {
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
//        button_edit_now.setText("YES");
//        button_edit_later.setText("NO");
//
//        textView_title_heading.setText("ARE YOU WANT TO REJECT  \n THIS APPLICANT ?");
//        dialog.show();
//        button_edit_now.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Log.e("Tag", "**here reject applicant @@ ");
//
//
//                sharedPreferences1 = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
//                employerId = sharedPreferences1.getString("user_id", "");
//                employerName = sharedPreferences1.getString("name", "");
//
//                try {
//
//                    String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("select_applicant", "UTF-8")
//                            + "&" + URLEncoder.encode("applicant_id", "UTF-8") + "=" + URLEncoder.encode(liteuserid, "UTF-8")
//                            + "&" + URLEncoder.encode("employer_id", "UTF-8") + "=" + URLEncoder.encode(employerId, "UTF-8")
//                            + "&" + URLEncoder.encode("job_title", "UTF-8") + "=" + URLEncoder.encode(job_title, "UTF-8")
//                            + "&" + URLEncoder.encode("job_id", "UTF-8") + "=" + URLEncoder.encode(job_id, "UTF-8")
//                            + "&" + URLEncoder.encode("employer_name", "UTF-8") + "=" + URLEncoder.encode(employerName, "UTF-8")
//                            + "&" + URLEncoder.encode("salary", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")
//                            + "&" + URLEncoder.encode("joining_date", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")
//                            + "&" + URLEncoder.encode("other_deatil", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")
//                            + "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
//
//
//                    Log.v("Tag", " list url ::" + data);
//
//                    new SelectApplicant_webservice().execute(signup_url, data);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//        textView_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                dialog.dismiss();
//            }
//        });
//        button_edit_later.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                dialog.dismiss();
//            }
//        });
//
//
//    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void sendEducationOthersProfile(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid);

        void sendExperienceOthersProfile(ArrayList<WorkExperienceData> work, String userid);

        void sendOthersDataOthersProfile(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid);

        void onFragmentInteraction(Uri uri);
    }

    private class SelectApplicant_webservice extends AsyncTask<String, Void, String> {

        JSONObject jsonObject_response = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading Profile ...");
            progressDialog.show();

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
            progressDialog.dismiss();
            afterresponse3(response);
        }
    }

}
