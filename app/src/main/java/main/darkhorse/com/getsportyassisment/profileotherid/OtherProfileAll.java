package main.darkhorse.com.getsportyassisment.profileotherid;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.UtilsFile.MainUrls;
import main.darkhorse.com.getsportyassisment.UtilsFile.MyToast;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.brodcastreceiver.NetworkBrodcastLisner;
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
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;
import main.darkhorse.com.getsportyassisment.compressor.Compressor;
import main.darkhorse.com.getsportyassisment.compressor.FileUtil;
import main.darkhorse.com.getsportyassisment.cropper.CropImage;
import main.darkhorse.com.getsportyassisment.cropper.CropImageView;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomDialogNoInternet;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.fragment.Fragment_Share;
import main.darkhorse.com.getsportyassisment.model_classes.ApiRefresh;
import main.darkhorse.com.getsportyassisment.model_classes.GooglePlaceApiResponse;
import main.darkhorse.com.getsportyassisment.model_classes.Predictions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;
import static main.darkhorse.com.getsportyassisment.custom_classes.DateConversion.context;


public class OtherProfileAll extends Fragment implements View.OnClickListener, CustomDialogNoInternet.myOnClickListener, ApiDataOtherdata {
    private static final String signup_url = MainUrls.mainUrl + "?";

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 9000;
    private static final int REQUEST_CAMERA = 9001;
    private static final int SELECT_FILE = 9002;
    private static final String TAG_RESULT = "predictions";
    ImageView imageView_back, profile_view_image;
    CircleImageView imageView_profile_pic;
    // ImageView       imageView_pic_blur;
    TextView textView_name, textView_title;
    TextView textView_profession;
    // TextView editText_about;
    TextView textView_location;
    TextView org_name;
    TextView sport_name;
    String sport;
    String prof;
    Button submit;
    View rootView;
    ApiDataOtherdata apiData;
    ApiRefresh apiDataRefress;
    Dialog dialogloader;
    GlobalUserProfileData globalUserProfileData = GlobalUserProfileData.getInstance();

    //    String[] keys_info = {"userid", "name", "email", "contact_no", "sport", "gender", "address1", "address2", "dob",
//            "prof_id", "user_image", "profile_status", "location", "prof_language", "age_catered", "about_me"};
//
    HashMap<String, String> hashMap_info;
    String[] keys_info = {"name", "sport", "prof_id", "location", "user_image", "about_me"};
    Boolean isInternetPresent = false;
    NetworkStatus network_status;
    Bitmap profile_pic1;
    OtherProfileEducation coachEducation; //= new CoachEducation();
    OtherExperience coachExperience;// = new CoachExperience();
    CoachHeader coachHeader; // = new CoachHeader();
    Typeface font, fontbold;
    Button accept_offer, reject_offer;
    String liteuserid, Connectionid, liteuserprofid, profid;
    View view1;
    LinearLayout snackbar_linear, create_job, layout_create_tournamnet, layout_create_event, snackbar_linear_layout;
    RelativeLayout item;
    TextView more, tv_create_job, tv_create_tournament, tv_create_event, tv_snackbar_more;
    LinearLayout layout_more;
    LinearLayout layout_profile;
    LinearLayout layout_profile_job;
    //ProgressBar processbar;
    //TextView tv_persentage;
    CustomProgress customProgress;
    ImageView share_profile;
    //placeLocation api credintion
    String url;
    JSONObject json;
    AutoCompleteTextView location;
    ArrayList<String> names;
    ArrayAdapter<String> adapter;
    String browserKey = "AIzaSyDfdIdeA96qORreYWTCGto85nz0_ZSx_dc";
    //placeLocation api credintion
    CustomDialogNoInternet nointernet;
    String profileImage;
    CircleImageView profile_image;
    Boolean imageuploadgalary = false;
    Uri imagefromgalary;
    Uri uri;
    private View view;
    private OtherProfileAdapterAll mAdapter;
    private ProgressDialog progressDialog;
    private String user_id, prof_id;
    private ImageView editHeader;
    private String userChoosenTask;
    private SharedPreferences sharedPreferences1;
    private String encoded;
    //    private ProgressBar profileLoader;
    private String othersUserId;
    private Button connectButton;
    private boolean isNotification = false;
    private Button sendMessage;
    private Button joinClass;
    private Retrofit retrofit;
    private FragmentManager fm;
    private ApiAtheliteCall apiCall;
    private int conId = 0;
    private int progressStatus = 0;
    private File compressedImage;
    private File actualImage;

    public OtherProfileAll() {

    }

    public static OtherProfileAll newInstance(String userid) {
        OtherProfileAll OtherProfile = new OtherProfileAll();
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);

        OtherProfile.setArguments(bundle);
        return OtherProfile;

    }

    public static OtherProfileAll newInstance(String Connectionid, String liteuserid, String liteuserprofid) {
        OtherProfileAll coachProfile = new OtherProfileAll();
        Bundle bundle = new Bundle();
        bundle.putString("Connectionid", Connectionid);
        bundle.putString("liteuserid", liteuserid);
        bundle.putString("liteuserprofid", liteuserprofid);

        coachProfile.setArguments(bundle);
        return coachProfile;

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            othersUserId = getArguments().getString("userid");
            isNotification = getArguments().getBoolean("notification");
            conId = getArguments().getInt("con_id");
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {


            liteuserid = getArguments().getString("liteuserid");
            Connectionid = getArguments().getString("Connectionid");
            liteuserprofid = getArguments().getString("liteuserprofid");

            profid = getArguments().getString("profid");

        }


    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // fragment_coach_profileview
        rootView = inflater.inflate(R.layout.fragment_coach_profileview, container, false);
        nointernet = CustomDialogNoInternet.getInstance();
        fm = getFragmentManager();
        customProgress = CustomProgress.getInstance();
        item = (RelativeLayout) rootView.findViewById(R.id.view2);
        layout_profile = (LinearLayout) rootView.findViewById(R.id.footer_layout_profile);
        layout_profile_job = (LinearLayout) rootView.findViewById(R.id.footer_layout_profile_job);
        layout_more = (LinearLayout) rootView.findViewById(R.id.more_layout);
        //  processbar = (ProgressBar) rootView.findViewById(R.id.pb);
        //tv_persentage = (TextView) rootView.findViewById(R.id.percentage);
        share_profile = (ImageView) rootView.findViewById(R.id.share_profile_pdf);
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


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView textView_title = (TextView) getActivity().findViewById(R.id.title);

      //  imageView_back = (ImageView) rootView.findViewById(R.id.back_arrow);


        accept_offer = (Button) rootView.findViewById(R.id.accept_offer);
        reject_offer = (Button) rootView.findViewById(R.id.reject_offer);


        share_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String link = "http://getsporty.in/html_pdf/example_001.php?id=" + user_id;
                Bitmap bm = DateConversion.screenShot(rootView);
                File file = DateConversion.saveBitmap(bm, "mantis_image.png");
                Log.i("chase", "filepath: " + file.getAbsolutePath());
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
            initView();
            check_connection();

        } else {


            nointernet.showProgress(getActivity(), false);

        }


    }


    private void initView() {
        sharedPreferences1 = getActivity().getSharedPreferences("Dashboard_prefs", 0);
        user_id = sharedPreferences1.getString("user_id", "");
        prof_id = sharedPreferences1.getString("prof_id", "");

        imageView_profile_pic = (CircleImageView) rootView.findViewById(R.id.profile_pic);
        imageView_profile_pic.setOnClickListener(this);

        editHeader = (ImageView) rootView.findViewById(R.id.edit_header);
        textView_name = (TextView) rootView.findViewById(R.id.name);
        textView_profession = (TextView) rootView.findViewById(R.id.profession);
        textView_location = (TextView) rootView.findViewById(R.id.location);

        org_name = (TextView) rootView.findViewById(R.id.tv_org_name);
        sport_name = (TextView) rootView.findViewById(R.id.tv_sport_name);
        hashMap_info = new HashMap<>();

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.myViewCoach);
        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_coach);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.education));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.experience));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.other));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mAdapter = new OtherProfileAdapterAll(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(mAdapter);

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
        // tabLayout.setTabTextColors(getResources().getColor(R.color.text_color), getResources().getColor(R.color.toolbar_color_home));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.toolbar_color_home));
                //tabLayout.setTabTextColors(getResources().getColor(R.color.text_color), getResources().getColor(R.color.toolbar_color_home));
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();
                assert text != null;
                text.setTypeface(null, Typeface.BOLD);
                text.setTextColor(ContextCompat.getColor(getContext(), R.color.toolbar_color_home));

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


        network_status = new NetworkStatus(getActivity());
        isInternetPresent = network_status.isConnectingToInternet();

        if (isInternetPresent) {

            try {
                coachprofile_load(user_id, prof_id);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), getString(R.string.no_inetnet_connection), Toast.LENGTH_SHORT).show();
        }


        if (!user_id.equals(othersUserId)) {
            editHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenHeaderDialog();


                }
            });
//            imageView_profile_pic.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View view) {
//                    openCameraDialog();
//                }
//            });
        } else {
            editHeader.setVisibility(View.GONE);

            if (isNotification) {
                connectButton.setVisibility(View.GONE);
                sendMessage.setVisibility(View.VISIBLE);
                joinClass.setVisibility(View.VISIBLE);
            } else {
                connectButton.setVisibility(View.VISIBLE);
            }

        }


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
                                Toast.makeText(getContext(), R.string.connection_request, Toast.LENGTH_SHORT).show();

                                accept_offer.setText("Accepted");
                                accept_offer.setEnabled(false);
                                accept_offer.setClickable(false);
                                reject_offer.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getContext(), getString(R.string.sorry_inconven), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectToUserResponse> call, Throwable t) {
                        //    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        reject_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<ConnectToUserResponse> connectToUserResponseCall = apiCall.sendConnectionResponseRequest("request_response", Connectionid, "0", "M");
                Log.d("Connection URL", connectToUserResponseCall.request().url().toString());
                connectToUserResponseCall.enqueue(new Callback<ConnectToUserResponse>() {
                    @Override
                    public void onResponse(Call<ConnectToUserResponse> call, Response<ConnectToUserResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getContext(), "Connection Request Sent", Toast.LENGTH_SHORT).show();
                                reject_offer.setText("Reject connection");
                                reject_offer.setEnabled(false);
                                reject_offer.setClickable(false);
                                accept_offer.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getContext(), "Sorry for Inconvenience!! Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectToUserResponse> call, Throwable t) {
                        // Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));
        final ScrollView scrollview = (ScrollView) dialog.findViewById(R.id.headerscrollview);

        final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designationHeader);
        final TextInputEditText description = (TextInputEditText) dialog.findViewById(R.id.descriptionHeader);
        final TextInputEditText description1 = (TextInputEditText) dialog.findViewById(R.id.descriptionHeader1);
        final TextInputEditText description2 = (TextInputEditText) dialog.findViewById(R.id.descriptionHeader2);
        // final TextInputEditText location = (TextInputEditText) dialog.findViewById(R.id.locationHeader);
        final TextInputEditText academyHeader = (TextInputEditText) dialog.findViewById(R.id.academyNameHeader);

        profile_image = (CircleImageView) dialog.findViewById(R.id.profile_pic_dialog);
        final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
        final TextView close = (TextView) dialog.findViewById(R.id.close);
        location = (AutoCompleteTextView) dialog.findViewById(R.id.locationHeader);
        location.setThreshold(0);
        names = new ArrayList<String>();
        location.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (s.toString().length() <= 3) {
                    names = new ArrayList<String>();
                    updateList(s.toString());
                }

            }
        });


        academyHeader.setText(globalUserProfileData.acamedy);
        designation.setText(textView_profession.getText());
        location.setText(textView_location.getText());

        if (globalUserProfileData.description != null) {
            String descriptionpart[] = DateConversion.splitInParts(globalUserProfileData.description, 40);
            int length = descriptionpart.length;
            switch (length) {
                case 1:
                    description.setText(descriptionpart[0]);
                    break;
                case 2:
                    description.setText(descriptionpart[0]);
                    description1.setText(descriptionpart[1]);
                    break;
                case 3:
                    description.setText(descriptionpart[0]);
                    description1.setText(descriptionpart[1]);
                    description2.setText(descriptionpart[2]);
                    break;
                default:
                    description.setText(descriptionpart[0]);
                    description1.setText(descriptionpart[1]);
                    description2.setText(descriptionpart[2] + descriptionpart[3]);
            }


        }

        if (imageuploadgalary) {


            profile_image.setImageURI(imagefromgalary);


        } else {
            if (profileImage != null && !profileImage.equals("")) {

                Picasso.with(getActivity()).load(profileImage)
                        .placeholder(R.drawable.account_blank).error(R.drawable.account_blank)
                        .into(profile_image);


            } else {
                Log.e("Tag", "Image is not set");
            }

        }


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckAndroidPermission.checkAndRequestPermissions(getContext(), getActivity())) {
                    onSelectImageClick(v);

                } else {
                    CheckAndroidPermission.checkAndRequestPermissions(getContext(), getActivity());


                }
            }
        });


        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 57) {
                    scrollview.fullScroll(ScrollView.FOCUS_UP);
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
                if (charSequence.length() == 57) {
                    scrollview.fullScroll(ScrollView.FOCUS_UP);
                    description2.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        description2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 57) {
                    submit_details.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    String academy = academyHeader.getText().toString().toLowerCase();
                    if (!academy.equals("")) {
                        academy = Character.toString(academy.charAt(0)).toUpperCase() + academy.substring(1);
                        // org_name.setText(academy);

                    } else {

                    }


                    String des = designation.getText().toString().toLowerCase();

                    if (!des.equals("")) {
                        des = Character.toString(des.charAt(0)).toUpperCase() + des.substring(1);
                        textView_profession.setText(des);

                    } else {

                    }

                    String loc = location.getText().toString().toLowerCase();
                    if (!loc.equals("")) {
                        loc = Character.toString(loc.charAt(0)).toUpperCase() + loc.substring(1);
                        textView_location.setText(loc);

                    } else {

                    }

                    String set_descriptipn = (description.getText().toString() + " " + description1.getText().toString() + " " + description2.getText().toString());
                    if (!set_descriptipn.equals("")) {
//                        set_descriptipn = Character.toString(set_descriptipn.charAt(0)).toUpperCase() + set_descriptipn.substring(1);
                        //    editText_about.setText(set_descriptipn);

                    } else {

                    }

                    updateData();
                    dialog.dismiss();
                } else {
                    MyToast toast = new MyToast();
                    toast.show(getContext(), "Fill All Fields", false);
                }
            }
        });

    }

    public void onSelectImageClick(View view) {
        CropImage.activity(null)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getContext(), this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageuploadgalary = true;
                imageView_profile_pic.setImageURI(result.getUri());
                profile_image.setImageURI(result.getUri());
                imagefromgalary = result.getUri();
                // Compress image in main thread using custom Compressor
                try {
                    actualImage = FileUtil.from(getContext(), imagefromgalary);


                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    compressedImage = new Compressor(getActivity())
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(actualImage);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                Bitmap bm = null;

                try {
                    bm = (BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
                    // bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getUri());
                    saveOnServer(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updateList(String place) {
        String input = "";

        try {
            input = "input=" + URLEncoder.encode(place, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String output = "json";
        String parameter = input + "&types=geocode&sensor=true&key="
                + browserKey;
        url = output + "?" + parameter;
        Retrofit retrofit = ApiClient.getGooglePlaceClient();
        ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
        Call<GooglePlaceApiResponse> jsonObjReq = apiCall.GoogleApiCall(url);
        Log.e("well", jsonObjReq.request().url().toString());
        jsonObjReq.enqueue(new Callback<GooglePlaceApiResponse>() {
            @Override
            public void onResponse(Call<GooglePlaceApiResponse> call, Response<GooglePlaceApiResponse> response) {

                ArrayList<Predictions> predictions = new ArrayList<Predictions>();
                predictions = response.body().getPredictions();

                try {
                    for (int i = 0; i < predictions.size(); i++) {
                        String description = predictions.get(i).getDescription();
                        Log.d("description", description);
                        names.add(description);
                    }
                } catch (Exception e) {
                }

                adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1, names) {
                    @Override
                    public View getView(int position,
                                        View convertView, ViewGroup parent) {
                        View view = super.getView(position,
                                convertView, parent);
                        TextView text = (TextView) view
                                .findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                location.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<GooglePlaceApiResponse> call, Throwable t) {


            }
        });


    }

    private void coachprofile_load(final String userid, final String prof_id) {


        try {
            retrofit = ApiClient.getClient();
            apiCall = retrofit.create(ApiAtheliteCall.class);
            Call<OtherProfileResponse> call = apiCall.getCoachProfilOther("getUserProfile", userid, prof_id);
            customProgress.showProgress(getActivity(), false);
            Log.d("URLTAG", call.request().url().toString());
            call.enqueue(new Callback<OtherProfileResponse>() {
                @Override
                public void onResponse(Call<OtherProfileResponse> call, Response<OtherProfileResponse> response) {
                    customProgress.hideProgress();
                    apiData = (ApiDataOtherdata) getActivity();
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

                            String academy = response.body().getData().getHeaderDetails().getAcamedy();

                            if (!academy.equals("")) {
//                                academy = Character.toString(academy.charAt(0)).toUpperCase() + academy.substring(1);
                                //    org_name.setText(academy);

                            } else {

                            }


                            String profession = globalUserProfileData.designation.toLowerCase();
                            if (!profession.equals("")) {
                                profession = Character.toString(profession.charAt(0)).toUpperCase() + profession.substring(1);
                                textView_profession.setText(profession);

                            } else {

                            }
                            String location = globalUserProfileData.location.toLowerCase();
                            if (!location.equals("")) {

                                location = Character.toString(location.charAt(0)).toUpperCase() + location.substring(1);
                                textView_location.setText(location);
                            } else {

                            }


                            String about = globalUserProfileData.description;

                            if (!about.equals("")) {

//                                about = Character.toString(about.charAt(0)).toUpperCase() + about.substring(1);
                                //       editText_about.setText(about);
                            } else {

                            }


                        }
                        if (response.body().getData().getUser() != null) {
                            apiData.getOthersDataAll(response.body().getData().getUser().getDob()
                                    , response.body().getData().getUser().getGender()
                                    , response.body().getData().getUser().getProf_name()
                                    , response.body().getData().getUser().getSport()
                                    , response.body().getData().getUser().getLink()
                                    , response.body().getData().getUser().getAge_group_coached()
                                    , response.body().getData().getUser().getLanguages_known()
                                    , userid);

                            String toolbar_title = response.body().getData().getUser().getName();
                            //            + " " + response.body().getData().getUser().getSport() + " " + response.body().getData().getUser().getProf_name();
                            String title = toolbar_title.toLowerCase();
                            title = Character.toString(title.charAt(0)).toUpperCase() + title.substring(1);
                            getActivity().setTitle(title);


                            sport = response.body().getData().getUser().getSport().toLowerCase();
                            prof = response.body().getData().getUser().getProf_name().toLowerCase();

//                            if (!sport.equals("")) {
//                                sport = Character.toString(sport.charAt(0)).toUpperCase() + sport.substring(1);
////                                sport_name.setText(sport);
//
//                            } else {
//                                sport = "";
//                            }

                            if (!prof.equals("")) {
                                prof = Character.toString(prof.charAt(0)).toUpperCase() + prof.substring(1);
//                                textView_profession.setText(prof);

                            } else {
                                prof = "";
                            }
                            //   org_name.setText(prof + "" + "," + sport);

                            org_name.setText(prof);
                            String name = response.body().getData().getUser().getName().toLowerCase();
                            if (!name.equals("")) {
                                name = Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
                                textView_name.setText(name);

                            } else {

                            }


                            profileImage = response.body().getData().getUser().getUser_image();

                            if (profileImage != null && !profileImage.equals("")) {

                                Picasso.with(getActivity()).load(profileImage)
                                        .placeholder(R.drawable.account_blank).error(R.drawable.account_blank)
                                        .into(imageView_profile_pic);

//                                Picasso.with(getActivity()).load(profileImage)
//                                        .placeholder(R.drawable.account_blank).error(R.drawable.account_blank)
//                                        .into(imageView_pic_blur);

//                                Picasso.with(getActivity()).load(profileImage)
//                                        .placeholder(R.drawable.account_blank).error(R.drawable.account_blank)
//                                        .into(profile_view_image);

                            } else {
                                Log.e("Tag", "Image is not set");
                            }
                        }
                        String profile = String.valueOf(response.body().getData().getProfile());
                        progressStatus = (int) response.body().getData().getProfile();
                        //  tv_persentage.setText("Profile Completeness  " + profile + "%");
                        // processbar.setProgress(progressStatus);
                    }

                    //  apiData.getEducationData(globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal, userid);

                    apiData.getEducationDataAll(globalUserProfileData.sportEducationGlobal, globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal, userid);


                    apiData.getExperienceDataAll(globalUserProfileData.workExperienceGlobal, globalUserProfileData.experienceAsPlayerGlobal, userid);

                }

                @Override
                public void onFailure(Call<OtherProfileResponse> call, Throwable t) {
                    customProgress.hideProgress();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        sharedPreferences1 = getActivity().getSharedPreferences("Dashboard_prefs", 0);
        final String user_id = sharedPreferences1.getString("user_id", null);

        if (!user_id.equals(userid)) {

            layout_profile.setVisibility(View.VISIBLE);
            editHeader.setVisibility(View.GONE);
            imageView_profile_pic.setClickable(false);

        } else {
            layout_profile.setVisibility(View.GONE);
        }

    }


    public void updateData() {

        Retrofit retrofit = ApiClient.getClient();
        ApiAtheliteCall call = retrofit.create(ApiAtheliteCall.class);
        //  coachEducation = new  OtherProfileEducation( globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal);
        coachEducation = new OtherProfileEducation(globalUserProfileData.sportEducationGlobal, globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal);

        coachExperience = new OtherExperience(globalUserProfileData.workExperienceGlobal, globalUserProfileData.experienceAsPlayerGlobal);

        coachHeader = new CoachHeader(globalUserProfileData.designation, globalUserProfileData.acamedy, globalUserProfileData.location, globalUserProfileData.description);
        Call<UserProfileCoachEditResponse> editUserData = call.editOtherProfileData("editUserData", user_id, "2", new OtherProfileEditRequest(coachEducation, coachExperience, coachHeader));

        Log.d("EditProfileHitUrl", editUserData.request().url().toString());

        editUserData.enqueue(new Callback<UserProfileCoachEditResponse>() {
            @Override
            public void onResponse(Call<UserProfileCoachEditResponse> call, Response<UserProfileCoachEditResponse> response) {

                Log.d("Updated", response.body().getData().toString());

//                apiDataRefress = (ApiRefresh) getActivity();
//                apiDataRefress.getRefress(true);

            }

            @Override
            public void onFailure(Call<UserProfileCoachEditResponse> call, Throwable t) {
                Log.d("Error", "Error");
            }
        });
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
                        profile_image.setImageBitmap(bm);
                        SharedPreferences.Editor edit = sharedPreferences1.edit();
                        edit.putString("image", response.body().getData().getUser_image());
                        edit.commit();
                        customProgress.hideProgress();

//                        apiDataRefress = (ApiRefresh) getActivity();
//                        apiDataRefress.getRefress(true);

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
                        // imageView_pic_blur.setImageBitmap(bm);
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

    public void check_connection() {
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
//                            accept_offer.setText("ACCEPT CONNECT");
//                            accept_offer.setEnabled(false);
//                            accept_offer.setClickable(false);
//                            reject_offer.setVisibility(View.GONE);
                            layout_profile.setVisibility(View.GONE);

                        }


//
                    }
                }

                @Override
                public void onFailure(Call<ConnectedUserResponse> call, Throwable t) {
                    // Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.profile_pic:
                if (imageuploadgalary) {

                    DateConversion.showImageUri(getContext(), imagefromgalary);

                } else {
                    DateConversion.showImage(getContext(), profileImage);

                }


                break;


        }
    }


    void showDialog(String lin, Uri uri) {

        DialogFragment newFragment = Fragment_Share.newInstance(lin, uri);
        newFragment.show(fm, "SHARE");
    }


    @Override
    public void onButtonClick() {

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

