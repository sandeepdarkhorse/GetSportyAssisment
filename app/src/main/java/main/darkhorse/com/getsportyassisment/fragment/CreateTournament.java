package main.darkhorse.com.getsportyassisment.fragment;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.UtilsFile.CommonUtils;
import main.darkhorse.com.getsportyassisment.UtilsFile.MainUrls;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.model_classes.CategoriesString;
import main.darkhorse.com.getsportyassisment.model_classes.EventGroups;
import main.darkhorse.com.getsportyassisment.model_classes.MyTournamentDataModel;
import main.darkhorse.com.getsportyassisment.model_classes.RefDate;
import main.darkhorse.com.getsportyassisment.model_classes.SelectedCategories;
import main.darkhorse.com.getsportyassisment.model_classes.SportsData;
import main.darkhorse.com.getsportyassisment.model_classes.TermsCoditionItemString;
import main.darkhorse.com.getsportyassisment.model_classes.TournamentCategoryData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTournament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTournament extends Fragment implements View.OnClickListener {
    public static final String MY_PREFS_NAME = "Dashboard_prefs";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String search_url = MainUrls.cityurl + "?";
    private static MyTournamentDataModel tournamentdata = null;
    //    private OnFragmentInteractionListener mListener;
    ImageView imageViewTournament;
    Boolean imageuploadgalary = false;
    Uri imagefromgalary;
    Uri uri;
    List<String> list_sports;

    String TournamentName = "";
    String StartDate = "";
    String st_referance_date = "";
    String EndDate = "";
    String EntryStart = "";
    String EntryEnd = "";
    String Sport = "Swimming";
    String Level = "";
    String imageEncoded = "";
    String address_line1 = "";
    String city = "";
    String pin = "";
    String tournament_description = "";
    String tournament_links = "";
    String tournamentFees = "";
    String eventFees = "";
    String maxParticipate = "";
    String gender = "";
    String orgName;
    String orgAbout;
    String orgAddress1;
    String ordAddress2;
    String orgCity;
    String orgState;
    String orgPin;
    String orgMobile;
    String orgEmail;
    String orgGstin;
    TextView textViewTitle;
    TextView textViewStartDate;
    TextView textViewEndDate;
    TextView textViewEntryStart;
    TextView textViewEntryEnd;
    TextView textViewSport;
    TextView textViewLevel;
    TextView textViewAddress;
    TextView textViewLocation;
    TextView textViewPin;
    TextView textViewDescription;
    TextView textViewWebsite;
    TextView textViewTourFees;
    TextView textViewEventFees;
    TextView textViewMax;
    TextView textViewCategory;
    TextView textview_referance_date;
    //    TextView textViewAgeGroup;
    TextView textViewTerms;
    TextView textViewOrgName;
    TextView textViewOrgAddress;
    TextView textViewOrgLocation;
    TextView textViewOrgPin;
    TextView textViewEmail;
    TextView textViewNumber;
    ImageView imageViewEditOrganizer;
    LinearLayout event_fees_layout;
    ArrayList<TermsCoditionItemString> Terms_condArrayList;
    RecyclerView recyclerViewCategories;
    RecyclerView recyclerViewTermsCondition;
    String browserKey = "AIzaSyDfdIdeA96qORreYWTCGto85nz0_ZSx_dc";
    ArrayList<String> names;
    String url;
    CustomProgress customProgress;
    Boolean d1 = false, d2 = false, d3 = false, d4 = false, d5 = false, d6 = false;
    String imageurl = "";
    RelativeLayout editHeader, editDetails, editCategory, addOrgData, editTerms, lltermscondition;

    ArrayList<EventGroups> eventGroups;
    String format;
    boolean isRange;
    String referenceDate;
    String startDate;
    String endDate;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private File compressedImage;
    private File actualImage;
    private NetworkStatus network_status;
    private String categoryId;

    private RefDate refDate;

    public CreateTournament() {
        // Required empty public constructor
    }


    public static CreateTournament newInstance(MyTournamentDataModel tournament_data) {
        CreateTournament fragment = new CreateTournament();
        tournamentdata = tournament_data;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    final int[] validation = new int[7];
    String value = "";
    ArrayList<CategoriesString> categoryString = null;

    ArrayList<SportsData> sportsData;

    //    ArrayList<TournamentCategory> categoryArrayList;
    ArrayList<SelectedCategories> selectedCategories;


    ArrayList<TournamentCategoryData> categoryArrayList2;


    RecyclerView editCategoryViewCategories;
    LinearLayout layoutcategory;
    final ArrayList<String> defaultarraylist = new ArrayList<>();
    CheckBox checkboxCondition;
    String categorytext = "";
    String org_id = "0";
    String teamsport = "";

    String team_sporttype = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_tournament, container, false);

        defaultarraylist.add("Male");
        defaultarraylist.add("Female");
        categoryArrayList2 = new ArrayList<TournamentCategoryData>();
        customProgress = CustomProgress.getInstance();
        layoutcategory = rootView.findViewById(R.id.layout_category);
        checkboxCondition = rootView.findViewById(R.id.terms_condition);
        editHeader = rootView.findViewById(R.id.relative_title_name);
        editDetails = rootView.findViewById(R.id.relative_edit_address);
        editCategory = rootView.findViewById(R.id.relative_edit_category);
        addOrgData = rootView.findViewById(R.id.relative_edit_organizer);
        editTerms = rootView.findViewById(R.id.relative_terms_and_condition);
        lltermscondition = rootView.findViewById(R.id.ll_termscondition);
        imageViewTournament = rootView.findViewById(R.id.tournament_image);
        ImageView imageViewEdit = rootView.findViewById(R.id.edit_header);
        textViewTitle = rootView.findViewById(R.id.tournament_title_name);
        ImageView imageViewEditDetails = rootView.findViewById(R.id.edit_details);
        textViewStartDate = rootView.findViewById(R.id.start_date);
        textViewEndDate = rootView.findViewById(R.id.end_date);
        textViewEntryStart = rootView.findViewById(R.id.entry_start_date);
        textViewEntryEnd = rootView.findViewById(R.id.entry_end_date);
        textViewSport = rootView.findViewById(R.id.sport);
        textViewLevel = rootView.findViewById(R.id.level);
        ImageView imageViewEditAddress = rootView.findViewById(R.id.edit_address);

        textViewAddress = rootView.findViewById(R.id.address);
        textViewLocation = rootView.findViewById(R.id.location);
        textViewPin = rootView.findViewById(R.id.pin);
        textViewDescription = rootView.findViewById(R.id.description);
        textViewWebsite = rootView.findViewById(R.id.website);
        textViewTourFees = rootView.findViewById(R.id.tour_fees);
        textViewEventFees = rootView.findViewById(R.id.event_fees);
        textViewMax = rootView.findViewById(R.id.max_participate);

        ImageView imageViewEditCategory = rootView.findViewById(R.id.edit_category);
        textViewCategory = rootView.findViewById(R.id.category);
        textview_referance_date = rootView.findViewById(R.id.view_referance_date);
//        textViewAgeGroup = rootView.findViewById(R.id.age_group_data);

        imageViewEditOrganizer = rootView.findViewById(R.id.edit_organizer);

        textViewOrgName = rootView.findViewById(R.id.organizer_name);
        textViewOrgAddress = rootView.findViewById(R.id.organizer_address);
        textViewOrgLocation = rootView.findViewById(R.id.organizer_location);
        textViewOrgPin = rootView.findViewById(R.id.organizer_pin);
        textViewEmail = rootView.findViewById(R.id.email_id);
        textViewNumber = rootView.findViewById(R.id.number);

        ImageView imageViewTerms = rootView.findViewById(R.id.edit_terms);
        textViewTerms = rootView.findViewById(R.id.terms_and_condition);
        Button buttonCreate = rootView.findViewById(R.id.create);

        event_fees_layout = (LinearLayout) rootView.findViewById(R.id.event_fees_layout);


        imageViewEdit.setOnClickListener(this);
        imageViewEditDetails.setOnClickListener(this);
        imageViewEditAddress.setOnClickListener(this);
        imageViewEditOrganizer.setOnClickListener(this);
        imageViewTerms.setOnClickListener(this);
        imageViewEditCategory.setOnClickListener(this);
        buttonCreate.setOnClickListener(this);

        Terms_condArrayList = new ArrayList<>();

        selectedCategories = new ArrayList<>();
        eventGroups = new ArrayList<>();

        if (tournamentdata == null) {
            getActivity().setTitle("Tournament Create");
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Fragments", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor1.putString("current_fragment", "edit_tournament");
            editor1.putString("previous_fragment", "mycreation");
            editor1.commit();

        } else {
            getActivity().setTitle("Tournament Update");
            customProgress.showProgress(getActivity(), false);

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    customProgress.hideProgress();
                }
            };
            new Thread(r).start();


            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Fragments", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("current_fragment", "edit_tournament");
            editor.putString("previous_fragment", "mycreation");
            editor.commit();
            categoryId = tournamentdata.getCategory().get(0).getEventId();
            TournamentName = tournamentdata.getName();
            address_line1 = tournamentdata.getAddress_1();
            city = tournamentdata.getLocation();
            pin = tournamentdata.getPin();
            tournament_links = tournamentdata.getTournaments_link();

            StartDate = tournamentdata.getStart_date();
            EndDate = tournamentdata.getEnd_date();
            EntryStart = tournamentdata.getEvent_entry_date();
            EntryEnd = tournamentdata.getEvent_end_date();

            tournament_description = tournamentdata.getDescription();
            Level = tournamentdata.getTournament_level();
            Sport = tournamentdata.getSport();
            Terms_condArrayList = new ArrayList<>();
            Terms_condArrayList.clear();
            if (tournamentdata.getCategory() != null) {
                categoryArrayList2 = tournamentdata.getCategory();
            }

            Terms_condArrayList = tournamentdata.getTerms_cond();
            st_referance_date = tournamentdata.getCategory().get(0).getReferanceDate();
            if (!st_referance_date.equals(null) && !st_referance_date.equals("")) {
                textview_referance_date.setText(DateConversion.dateconversionMonth(st_referance_date));

            } else {
                textview_referance_date.setText("");

            }


            tournamentFees = tournamentdata.getCategory().get(0).getTournament_fees();
            eventFees = tournamentdata.getCategory().get(0).getEvent_fees();
            maxParticipate = tournamentdata.getCategory().get(0).getMax_participate();
            teamsport = tournamentdata.getCategory().get(0).getTournament_feetype();

            textViewTitle.setText(tournamentdata.getName());
            textViewStartDate.setText(DateConversion.dateconversion(tournamentdata.getStart_date()));
            textViewEndDate.setText(DateConversion.dateconversion(tournamentdata.getEnd_date()));
            textViewEntryStart.setText(DateConversion.dateconversion(tournamentdata.getEvent_entry_date()));
            textViewEntryEnd.setText(DateConversion.dateconversion(tournamentdata.getEvent_end_date()));
            textViewSport.setText(tournamentdata.getSport());
            textViewLevel.setText(tournamentdata.getTournament_level());
            textViewAddress.setText(tournamentdata.getAddress_1());
            textViewLocation.setText(tournamentdata.getLocation());
            textViewPin.setText(tournamentdata.getPin());
            textViewDescription.setText(tournamentdata.getDescription());
            textViewWebsite.setText(tournamentdata.getTournaments_link());

            textViewEventFees.setText(tournamentdata.getCategory().get(0).getEvent_fees());
            textViewMax.setText(tournamentdata.getCategory().get(0).getMax_participate());


            //            textViewAgeGroup.setText("");
            textViewCategory.setText("");
            //  textViewGender.setText("");
            String agegroup = "";
            String gender = "";
            String agegrouptext = "Under";


            switch (Sport) {
                case "Cricket":
                    setCategorydata();
                    textViewTourFees.setText(tournamentFees + " / " + teamsport);
                    event_fees_layout.setVisibility(View.GONE);
                    break;
                case "Football":
                    textViewTourFees.setText(tournamentFees + " / " + teamsport);
                    event_fees_layout.setVisibility(View.GONE);
                    setCategorydata();
                    break;
                case "Basket-ball":
                    textViewTourFees.setText(tournamentFees + " / " + teamsport);
                    event_fees_layout.setVisibility(View.GONE);
                    setCategorydata();
                    break;

                default:
                    event_fees_layout.setVisibility(View.VISIBLE);
                    textViewTourFees.setText(tournamentFees);
                    textViewEventFees.setText(eventFees);
                    if (categoryArrayList2 != null && categoryArrayList2.size() > 0) {
                        for (int i = 0; i < categoryArrayList2.size(); i++) {


                            String ApplyGender = categoryArrayList2.get(i).getGender();
                            String applyAge = categoryArrayList2.get(i).getAge();


                            if (ApplyGender.equals("B")) {
                                value = "Both Male,Female";
                            } else if (ApplyGender.equals("M")) {

                                value = "Male";
                            } else if (ApplyGender.equals("F")) {

                                value = "Female";
                            } else {
                                ApplyGender = "";
                                value = "";
                            }

//                            if (categoryArrayList2.get(i).getEvent() != null && !categoryArrayList2.get(i).getEvent().isEmpty()) {
//                                for (int j = 0; j < categoryArrayList2.get(i).getEvent().size(); j++) {
//
//                                    categorytext = categoryArrayList2.get(i).getEvent().get(j).getEvent() + " Under " + applyAge + " " + value + "\n";
//                                    textViewCategory.append(categorytext);
//
//
//                                }
//                            }


                        }


                    }

            }


            textViewTerms.setText("");
            String termscondition = "";
            if (Terms_condArrayList != null && Terms_condArrayList.size() > 0) {
                for (int i = 0; i < Terms_condArrayList.size(); i++) {
                    textViewTerms.append(termscondition + " " + Terms_condArrayList.get(i).getTerm() + "\n");
                    d5 = true;
                }
            }
            try {
                String imageurl = MainUrls.tournament_image_url + tournamentdata.getImage();
                Bitmap thumbnail = DateConversion.getBitmapFromURL(imageurl);
                thumbnail = Bitmap.createScaledBitmap(thumbnail, 512, 512, false);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                byte[] b = bytes.toByteArray();
                imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            } catch (Exception e) {
                System.out.println(e);
            }

            String imageurl = MainUrls.tournament_image_url + tournamentdata.getImage();
            Picasso.with(getActivity())
                    .load(imageurl)
                    .error(R.drawable.edit_profile_back)      // optional
                    .placeholder(R.drawable.edit_profile_back)
                    .resize(1112, 640)                        // optional
                    .into(imageViewTournament);
            buttonCreate.setText("UPDATE TOURNAMENT");
            d1 = true;
            d2 = true;
            d3 = true;
            d4 = true;
            d5 = true;
            d6 = true;
            checkboxCondition.setChecked(true);
        }
       // checkorgdetail();
      //  getSports();


        checkboxCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    d6 = true;

                } else {
                    d6 = false;
                }

            }
        });
        return rootView;
    }


    private void setCategorydata() {
        if (categoryArrayList2 != null && categoryArrayList2.size() > 0) {
            for (int i = 0; i < categoryArrayList2.size(); i++) {
                String ApplyGender = categoryArrayList2.get(i).getGender();
                String applyAge = categoryArrayList2.get(i).getAge();
//                String TourFees = categoryArrayList2.get(i).getTournament_fees();
//                String Event_fees = categoryArrayList2.get(i).getEvent_fees();
//                String Max_participate = categoryArrayList2.get(i).getMax_participate();
//                textViewTourFees.setText(" Rs." + TourFees);
//                textViewEventFees.setText(" Rs." + Event_fees);
//                textViewMax.setText(Max_participate);


                if (ApplyGender.equals("B")) {
                    value = "Both Male,Female";
                } else if (ApplyGender.equals("M")) {

                    value = "Male";
                } else if (ApplyGender.equals("F")) {

                    value = "Female";
                } else {
                    ApplyGender = "";
                    value = "";
                }
                categorytext = " Under " + applyAge + " " + value + "\n";
                textViewCategory.append(categorytext);


            }


        }
    }


//    private void getSports() {
//        sportsData = new ArrayList<>();
//        list_sports = new ArrayList<>();
//        network_status = new NetworkStatus(getActivity());
//        if (network_status.isConnectingToInternet()) {
//            try {
//                Retrofit retrofit;
//                retrofit = ApiClient.getClient();
//                ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//                Call<TourSportsListResponse> sportsList = apiCall.getTourSports("tournament_sports");
//                sportsList.enqueue(new Callback<TourSportsListResponse>() {
//                    @Override
//                    public void onResponse(Call<TourSportsListResponse> call, Response<TourSportsListResponse> response) {
//                        if (response.body().getStatus().equals("1")) {
//                            list_sports.clear();
//                            sportsData.clear();
//                            sportsData = response.body().getData();
//                            for (SportsData sportsData : response.body().getData()) {
//                                list_sports.add(sportsData.getSports());
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<TourSportsListResponse> call, Throwable t) {
//                    }
//                });
//            } catch (Exception e) {
//            }
//        } else {
//            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_header:
                if (CheckAndroidPermission.checkAndRequestPermissions(getContext(), getActivity())) {
                  //  onSelectImageClick(v);
                } else {
                    CheckAndroidPermission.checkAndRequestPermissions(getContext(), getActivity());
                }
                break;
            case R.id.edit_details:
               // editHeader();
                break;
            case R.id.edit_address:
                if (textViewSport.getText().toString().equals("Add Sport") || textViewSport.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Select sports first", Toast.LENGTH_LONG).show();
                //    editHeader();
                    return;
                }
               // editDetails();
                break;
            case R.id.edit_organizer:
               // addOrgData();
                break;
            case R.id.edit_category:

                if (textViewSport.getText().toString().equals("Add Sport") || textViewSport.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Select sports first", Toast.LENGTH_LONG).show();
//                    editHeader();
//                    return;
                }

                if (textViewTourFees.getText().toString().equals("Tournament Fees in INR") || textViewTourFees.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter fee detail first", Toast.LENGTH_LONG).show();
                 //   editDetails();
                    return;
                }
               // editCategorydesign();
                break;
            case R.id.edit_terms:
             //   editTerms();
                break;
            case R.id.create:
                if (tournamentdata == null) {
//                    save_tournament();
                } else {
               //     update_tournament();
                }
                break;
        }
    }


//    public void onSelectImageClick(View view) {
//        CropImage.activity(null)
//                .setActivityTitle(this.getResources().getString(R.string.image_cropper))
//                .setGuidelines(CropImageView.Guidelines.OFF)
//                .setFixAspectRatio(true)
//                .setAspectRatio(1115, 640)
//                .setMinCropWindowSize(0, 0)
//                .setMaxZoom(0)
//                .setInitialCropWindowPaddingRatio(0)
//                .setRequestedSize(1115, 640)
//                .start(getContext(), this);
//
//    }

    //@Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // handle result of CropImageActivity
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                imageuploadgalary = true;
//                imageViewTournament.setImageURI(result.getUri());
//                imagefromgalary = result.getUri();
//                // Compress image in main thread using custom Compressor
//                try {
//                    actualImage = FileUtil.from(getContext(), imagefromgalary);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    compressedImage = new Compressor(getActivity())
//                            .setMaxWidth(640)
//                            .setMaxHeight(480)
//                            .setQuality(75)
//                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                            .compressToFile(actualImage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Bitmap bitmap = null;
//                try {
//                    bitmap = (BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    byte[] b = baos.toByteArray();
//                    imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    private void editHeader() {
//        final ArrayAdapter<String> levelAdapter;
//        final ArrayList<String> level_List = new ArrayList<>();
//        final ArrayList<String> tournamenttype_list = new ArrayList<>();
//        final int[] validation = new int[7];
//        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.tournament_header);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        ImageView imageViewClose = dialog.findViewById(R.id.close);
//        final TextInputEditText textInputEditTextTournament = dialog.findViewById(R.id.tournament_name);
//        final TextInputEditText textInputEditTextStartDate = dialog.findViewById(R.id.start_date);
//        final TextInputEditText referance_date = dialog.findViewById(R.id.referance_date);
//        final TextInputEditText textInputEditTextEntryEnd = dialog.findViewById(R.id.entry_end_date);
//        final MaterialSpinner materialSpinnerSport = dialog.findViewById(R.id.sport_spinner);
//
//
//        final MaterialSpinner sport_tournamenttype = dialog.findViewById(R.id.sport_tournamenttype);
//
//
//        final MaterialSpinner materialSpinnerLevel = dialog.findViewById(R.id.level_spinner);
//        final TextInputEditText textInputEditTextOther = dialog.findViewById(R.id.other);
//        ImageView imageViewStart = dialog.findViewById(R.id.im_start_date);
//        ImageView imageViewReferance_date = dialog.findViewById(R.id.im_referance_date);
//        final TextInputEditText textInputEditTextEndDate = dialog.findViewById(R.id.end_date);
//        ImageView imageViewEnd = dialog.findViewById(R.id.im_end_date);
//        final TextInputEditText textInputEditTextEntryStart = dialog.findViewById(R.id.entry_start_date);
//        ImageView imageViewEntryStart = dialog.findViewById(R.id.im_entry_start);
//        ImageView imageViewEntryEnd = dialog.findViewById(R.id.im_entry_end);
//        final TextInputLayout view_start = (TextInputLayout) dialog.findViewById(R.id.view_start);
//        final TextInputLayout view_end = (TextInputLayout) dialog.findViewById(R.id.view_end);
//        final TextInputLayout view_entry_start = (TextInputLayout) dialog.findViewById(R.id.view_entry_start);
//        final TextInputLayout view_entry_end = (TextInputLayout) dialog.findViewById(R.id.view_entry_end);
//        final TextInputLayout view_tournament_name = (TextInputLayout) dialog.findViewById(R.id.view_tournament_name);
//        final TextInputLayout view_others = (TextInputLayout) dialog.findViewById(R.id.view_others);
//        final ScrollView scrollview = (ScrollView) dialog.findViewById(R.id.scrollview);
//        Button buttonSubmit = dialog.findViewById(R.id.submit);
//
//
//        imageViewClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        tournamenttype_list.add("Poolbased");
//        tournamenttype_list.add("League");
//        tournamenttype_list.add("Knockout");
//        tournamenttype_list.add("Roundrobin");
//
//
//        final ArrayAdapter<String> tournamenttypeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tournamenttype_list);
//        tournamenttypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sport_tournamenttype.setAdapter(tournamenttypeAdapter);
//
//        sport_tournamenttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                team_sporttype = sport_tournamenttype.getSelectedItem().toString();
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//        materialSpinnerSport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                String sport = materialSpinnerSport.getSelectedItem().toString();
//
//                switch (sport) {
//                    case "Cricket":
//                        sport_tournamenttype.setVisibility(View.VISIBLE);
//                        break;
//                    case "Football":
//                        sport_tournamenttype.setVisibility(View.VISIBLE);
//                        break;
//                    case "Basket-ball":
//                        sport_tournamenttype.setVisibility(View.VISIBLE);
//                        break;
//                    default:
//                        sport_tournamenttype.setVisibility(View.GONE);
//                        sport_tournamenttype.setSelection(0);
//                }
//                for (SportsData sportsData1 : sportsData) {
//                    if (sportsData1.getSports().equals(materialSpinnerSport.getSelectedItem().toString())) {
//                        categoryId = sportsData1.getCode();
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        level_List.add("National");
//        level_List.add("State");
//        level_List.add("Zonal District");
//        level_List.add("Private");
//        level_List.add("Other");
//
//        levelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, level_List);
//        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        materialSpinnerLevel.setAdapter(levelAdapter);
//
//        ArrayAdapter<String> dataAdapterSports = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list_sports);
//        dataAdapterSports.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        materialSpinnerSport.setAdapter(dataAdapterSports);
//
//
//        materialSpinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                if (materialSpinnerLevel.getSelectedItem().toString().equals("Other")) {
//                    view_others.setVisibility(View.VISIBLE);
//                } else {
//                    view_others.setVisibility(View.GONE);
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });
//
//        textInputEditTextStartDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        ///Hers's what I used to get the day names (0-6 means monday - sunday):
//                        Log.e("Tag", "Day   " + d);
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//                        StartDate = y + "-" + modMon + "-" + modDay;
//                        textInputEditTextStartDate.setText(d + " " + mon + " " + y);
//
//                        EndDate = "";
//                        textInputEditTextEndDate.setText("");
//                        EntryStart = "";
//                        textInputEditTextEntryStart.setText("");
//                        EntryEnd = "";
//                        textInputEditTextEntryEnd.setText("");
//
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
//                calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
//                calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
//                mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                mDatePicker.setTitle("ENTER STARTING DATE");
//                mDatePicker.show();
//            }
//        });
//
//        referance_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                // final int year = calendar.get(Calendar.YEAR);
//                final int year = 1990;
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        ///Hers's what I used to get the day names (0-6 means monday - sunday):
//                        Log.e("Tag", "Day   " + d);
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//
//                        st_referance_date = y + "-" + modMon + "-" + modDay;
//
//                        referance_date.setText(d + " " + mon + " " + y);
//                        textview_referance_date.setText(d + " " + mon + " " + y);
//
//                        for (int i = 0; i < categoryArrayList2.size(); i++) {
//                            TournamentCategoryData t = new TournamentCategoryData
//                                    (categoryArrayList2.get(i).getEventId(),
//                                            categoryArrayList2.get(i).getAge(), categoryArrayList2.get(i).getGender()
//                                            , categoryArrayList2.get(i).getFee(), categoryArrayList2.get(i).getEvent(), st_referance_date,
//                                            categoryArrayList2.get(i).getTime(), categoryArrayList2.get(i).getGroupId()
//                                            , categoryArrayList2.get(i).getTournament_fees(), categoryArrayList2.get(i).getEvent_fees()
//                                            , categoryArrayList2.get(i).getMax_participate(), categoryArrayList2.get(i).getTournament_feetype(), categoryArrayList2.get(i).getTeam_sporttype());
//                            categoryArrayList2.set(i, t);
//
//                        }
//
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
//                mDatePicker.setTitle("ENTER AGE REFERANCE DATE");
//                mDatePicker.show();
//            }
//        });
//
//
//        imageViewReferance_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                referance_date.performClick();
//
//            }
//        });
//
//
//        imageViewStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textInputEditTextStartDate.performClick();
//
//            }
//        });
//
//        textInputEditTextEndDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//                        if (!StartDate.equals("") && !StartDate.equals(null)) {
//                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                            try {
//                                int compare = DateConversion.compareDatesByCalendarMethods(df, df.parse(StartDate), df.parse(y + "-" + modMon + "-" + modDay));
//                                switch (compare) {
//                                    case 1:
//                                        EndDate = y + "-" + modMon + "-" + modDay;
//                                        textInputEditTextEndDate.setText(d + " " + mon + " " + y);
//
//                                        EntryStart = "";
//                                        textInputEditTextEntryStart.setText("");
//                                        EntryEnd = "";
//                                        textInputEditTextEntryEnd.setText("");
//
//                                        break;
//
//                                    case 2:
//                                        Toast.makeText(getActivity(), "Please enter tournament end date comes after start date", Toast.LENGTH_SHORT).show();
//                                        EndDate = "";
//                                        textInputEditTextEndDate.setText("");
//                                        break;
//                                    case 3:
//                                        EndDate = y + "-" + modMon + "-" + modDay;
//                                        textInputEditTextEndDate.setText(d + " " + mon + " " + y);
//
//                                        EntryStart = "";
//                                        textInputEditTextEntryStart.setText("");
//                                        EntryEnd = "";
//                                        textInputEditTextEntryEnd.setText("");
//
//
//
//                                        break;
//                                }
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter tournament start date first", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
//                calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
//                calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
//                mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                mDatePicker.setTitle("ENTER STARTING DATE");
//                mDatePicker.show();
//            }
//        });
//
//        imageViewEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textInputEditTextEndDate.performClick();
//            }
//        });
//
//        textInputEditTextEntryStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        ///Hers's what I used to get the day names (0-6 means monday - sunday):
//
//                        Log.e("Tag", "Day   " + d);
//
//
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//
//                        if (!(StartDate.equals("") && !StartDate.equals(null)) && !(EndDate.equals("") && !EndDate.equals(null)))
//                        {
//                            Boolean compare1 = DateConversion.isDateInBetweenIncludingEndPoints(DateConversion.StringtoDate(StartDate), DateConversion.StringtoDate(EndDate), DateConversion.StringtoDate(y + "-" + modMon + "-" + modDay));
//                            if (compare1) {
//                                EntryStart = y + "-" + modMon + "-" + modDay;
//                                textInputEditTextEntryStart.setText(d + " " + mon + " " + y);
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter tournament entry date between  start date or end date", Toast.LENGTH_SHORT).show();
//                                EntryStart = "";
//                                textInputEditTextEntryStart.setText("");
//
//                            }
//
//
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter tournament start date or end date first", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
//                calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
//                calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
//                mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                mDatePicker.setTitle("ENTER STARTING DATE");
//                mDatePicker.show();
//            }
//        });
//
//        imageViewEntryStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textInputEditTextEntryStart.performClick();
//            }
//        });
//
//
//        textInputEditTextEntryEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        ///Hers's what I used to get the day names (0-6 means monday - sunday):
//
//                        Log.e("Tag", "Day   " + d);
//
//
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//
//                        if (!(StartDate.equals("") && !StartDate.equals(null)) && !(EndDate.equals("") && !EndDate.equals(null))&& !(EntryStart.equals("") && !EntryStart.equals(null)))
//                        {
//                            Boolean compare1 = DateConversion.isDateInBetweenIncludingEndPoints(DateConversion.StringtoDate(EntryStart), DateConversion.StringtoDate(EndDate), DateConversion.StringtoDate(y + "-" + modMon + "-" + modDay));
//                            if (compare1) {
//                                EntryEnd = y + "-" + modMon + "-" + modDay;
//                                textInputEditTextEntryEnd.setText(d + " " + mon + " " + y);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter tournament entryend date between  entrystart date or tournament end date", Toast.LENGTH_SHORT).show();
//                                EntryEnd = "";
//                                textInputEditTextEntryEnd.setText("");
//                            }
//
//
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter tournament start date or end date ,entrystart date first", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
//                calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
//                calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
//                mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                mDatePicker.setTitle("ENTER STARTING DATE");
//                mDatePicker.show();
//            }
//        });
//
//        imageViewEntryEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textInputEditTextEntryEnd.performClick();
//            }
//        });
//
//        if (tournamentdata == null) {
//
//
//        } else {
//
//
//            TournamentName = tournamentdata.getName();
//            Sport = tournamentdata.getSport();
//            Level = tournamentdata.getTournament_level();
//            StartDate = tournamentdata.getStart_date();
//            EndDate = tournamentdata.getEnd_date();
//            EntryStart = tournamentdata.getEvent_entry_date();
//            EntryEnd = tournamentdata.getEvent_end_date();
//
//
//            st_referance_date = tournamentdata.getCategory().get(0).getReferanceDate();
//
//            team_sporttype = tournamentdata.getCategory().get(0).getTeam_sporttype();
//
//            if (!st_referance_date.equals(null) && !st_referance_date.equals("")) {
//                referance_date.setText(DateConversion.dateconversionMonth(st_referance_date));
//
//            } else {
//
//                referance_date.setText("");
//
//            }
//            textInputEditTextTournament.setText(TournamentName);
//            textInputEditTextStartDate.setText(DateConversion.dateconversionMonth(StartDate));
//            textInputEditTextEndDate.setText(DateConversion.dateconversionMonth(EndDate));
//            textInputEditTextEntryStart.setText(DateConversion.dateconversionMonth(EntryStart));
//            textInputEditTextEntryEnd.setText(DateConversion.dateconversionMonth(EntryEnd));
//
//            sport_tournamenttype.setSelection(tournamenttypeAdapter.getPosition(team_sporttype) + 1);
//
//            materialSpinnerSport.setSelection(dataAdapterSports.getPosition(Sport) + 1);
//            materialSpinnerLevel.setSelection(levelAdapter.getPosition(Level) + 1);
//            if (level_List.contains(Level)) {
//                materialSpinnerLevel.setSelection(levelAdapter.getPosition(Level) + 1);
//                view_others.setVisibility(View.GONE);
//            } else {
//
//                materialSpinnerLevel.setSelection(levelAdapter.getPosition("Other") + 1);
//                view_others.setVisibility(View.VISIBLE);
//                textInputEditTextOther.setText(Level);
//
//            }
//
//
//            //textInputEditTextOther.setText();
//
//
//        }
//
//
//        buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                TournamentName = textInputEditTextTournament.getText().toString();
//                Sport = materialSpinnerSport.getSelectedItem().toString();
//
//                if (materialSpinnerLevel.getSelectedItem().toString().equals("Other")) {
//                    Level = textInputEditTextOther.getText().toString();
//                } else {
//                    Level = materialSpinnerLevel.getSelectedItem().toString().toString();
//                }
//
//
//                if (TournamentName.equals("")) {
//                    validation[0] = 1;
//                    view_tournament_name.setErrorEnabled(true);
//                    view_tournament_name.setError("You need to enter tournament name");
//                    scrollview.fullScroll(ScrollView.FOCUS_UP);
//
//                } else {
//                    validation[0] = 0;
//                    view_tournament_name.setError(null);
//                }
//                if (StartDate.equals("")) {
//                    validation[1] = 1;
//                    view_start.setErrorEnabled(true);
//                    view_start.setError("You need to enter tournament startdate");
//                    scrollview.fullScroll(ScrollView.FOCUS_UP);
//
//
//                } else {
//                    validation[1] = 0;
//                    view_start.setError(null);
//                }
//
//                if (EndDate.equals("")) {
//                    validation[2] = 1;
//                    view_end.setErrorEnabled(true);
//                    view_end.setError("You need to enter tournament enddate");
//                    scrollview.fullScroll(ScrollView.FOCUS_UP);
//
//
//                } else {
//                    validation[2] = 0;
//                    view_end.setError(null);
//                }
//
//                if (EntryStart.equals("")) {
//                    validation[3] = 1;
//                    view_entry_start.setErrorEnabled(true);
//                    view_entry_start.setError("You need to enter tournament entry startdate");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[3] = 0;
//                    view_entry_start.setError(null);
//                }
//
//                if (EntryEnd.equals("")) {
//                    validation[4] = 1;
//                    view_entry_end.setErrorEnabled(true);
//                    view_entry_end.setError("You need to enter tournament entry enddate");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[4] = 0;
//                    view_entry_end.setError(null);
//                }
//
//
//                if (Sport.equals("Sport")) {
//                    validation[5] = 1;
//                    materialSpinnerSport.setError("You need to enter a sport");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[5] = 0;
//                    materialSpinnerSport.setError(null);
//                }
//
//                if (Level.equals("")) {
//                    validation[6] = 1;
//                    view_others.setError("You need to enter a sport level");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//                    textInputEditTextOther.requestFocus();
//
//                } else if (Level.equals("Level")) {
//                    validation[6] = 1;
//                    materialSpinnerLevel.setError("You need to enter a sport level");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//                    materialSpinnerLevel.requestFocus();
//                } else {
//                    validation[6] = 0;
//                    materialSpinnerLevel.setError(null);
//                    view_others.setError(null);
//                }
//
//
//                int sum = validation[0] + validation[1] + validation[2] + validation[3] + validation[4] + validation[5] + validation[6];
//                if (sum == 0) {
//
//
//                    textViewTitle.setText(TournamentName);
//                    textViewStartDate.setText(textInputEditTextStartDate.getText().toString());
//                    textViewEndDate.setText(textInputEditTextEndDate.getText().toString());
//                    textViewEntryStart.setText(textInputEditTextEntryStart.getText().toString());
//                    textViewEntryEnd.setText(textInputEditTextEntryEnd.getText().toString());
//                    textViewSport.setText(Sport);
//                    textViewLevel.setText(Level);
//
//                    d1 = true;
//
//                    for (int i = 0; i < categoryArrayList2.size(); i++) {
//                        TournamentCategoryData t = new TournamentCategoryData
//                                (categoryArrayList2.get(i).getEventId(),
//                                        categoryArrayList2.get(i).getAge()
//                                        , categoryArrayList2.get(i).getGender(), categoryArrayList2.get(i).getFee()
//                                        , categoryArrayList2.get(i).getEvent(), categoryArrayList2.get(i).getReferanceDate()
//                                        , categoryArrayList2.get(i).getTime(),
//                                        categoryArrayList2.get(i).getGroupId()
//                                        , categoryArrayList2.get(i).getTournament_fees()
//                                        , categoryArrayList2.get(i).getEvent_fees()
//                                        , categoryArrayList2.get(i).getMax_participate()
//                                        , teamsport, team_sporttype);
//
//
//                        categoryArrayList2.set(i, t);
//
//                    }
//                    dialog.dismiss();
//
//
//                } else {
//                    Toast.makeText(getActivity(), "Please enter all fields.", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            }
//        });
//
//        dialog.show();
//
//    }

//    private void editDetails() {
//
//        final int[] validation = new int[8];
//        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.tournament_info);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        ImageView imageViewClose = dialog.findViewById(R.id.close);
//        final TextInputEditText textInputEditTextAddress = dialog.findViewById(R.id.address);
//        final AutoCompleteTextView autoCompleteTextViewLocation = dialog.findViewById(R.id.location);
//        final TextInputEditText textInputEditTextPin = dialog.findViewById(R.id.pin);
//        final TextInputEditText textInputEditTextDescription = dialog.findViewById(R.id.description);
//        final TextInputEditText textInputEditTextLink = dialog.findViewById(R.id.link);
//        final TextInputEditText textInputEditTextTourFees = dialog.findViewById(R.id.tour_fees);
//        final TextInputEditText textInputEditTextEventFees = dialog.findViewById(R.id.event_fees);
//        final TextInputEditText textInputEditTextMax = dialog.findViewById(R.id.max_participate);
//
//        final TextInputLayout view_location = (TextInputLayout) dialog.findViewById(R.id.view_location);
//        final TextInputLayout view_address = (TextInputLayout) dialog.findViewById(R.id.view_address);
//        final TextInputLayout view_pin = (TextInputLayout) dialog.findViewById(R.id.view_pin);
//        final TextInputLayout view_description = (TextInputLayout) dialog.findViewById(R.id.view_description);
//        final TextInputLayout view_tournament_link = (TextInputLayout) dialog.findViewById(R.id.view_tournament_link);
//        final TextInputLayout view_tournament_fees = dialog.findViewById(R.id.view_tournament_fees);
//        final TextInputLayout view_event_fees = (TextInputLayout) dialog.findViewById(R.id.view_event_fees);
//
//        final TextInputLayout view_view_max = (TextInputLayout) dialog.findViewById(R.id.view_max);
//
//
//        final LinearLayout linear_fees_layout = (LinearLayout) dialog.findViewById(R.id.linear_fees_layout);
//
//
//        Button buttonSubmit = dialog.findViewById(R.id.submit);
//        final ScrollView scrollview = dialog.findViewById(R.id.scrollview);
//
//
//        final CheckBox cbTeamfees = (CheckBox) dialog.findViewById(R.id.cb_team_fees);
//        final CheckBox cbPerheadfees = (CheckBox) dialog.findViewById(R.id.cb_perhead_fees);
//
//        cbTeamfees.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//
//                if (isChecked) {
//                    cbPerheadfees.setChecked(false);
//                    teamsport = "PerTeam";
//                }
//
//
//            }
//        });
//        cbPerheadfees.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//
//                if (isChecked) {
//                    cbTeamfees.setChecked(false);
//                    teamsport = "PerHead";
//                }
//
//
//            }
//        });
//
//
//        switch (Sport) {
//            case "Cricket":
//                linear_fees_layout.setVisibility(View.VISIBLE);
//                view_event_fees.setVisibility(View.GONE);
//                textInputEditTextEventFees.setText("0");
//                event_fees_layout.setVisibility(View.GONE);
//                break;
//            case "Football":
//                linear_fees_layout.setVisibility(View.VISIBLE);
//                view_event_fees.setVisibility(View.GONE);
//                textInputEditTextEventFees.setText("0");
//                event_fees_layout.setVisibility(View.GONE);
//                break;
//            case "Basket-ball":
//                linear_fees_layout.setVisibility(View.VISIBLE);
//                view_event_fees.setVisibility(View.GONE);
//                textInputEditTextEventFees.setText("0");
//                event_fees_layout.setVisibility(View.GONE);
//                break;
//
//            default:
//                linear_fees_layout.setVisibility(View.GONE);
//                view_event_fees.setVisibility(View.VISIBLE);
//                event_fees_layout.setVisibility(View.VISIBLE);
//        }
//        autoCompleteTextViewLocation.setThreshold(0);
//        names = new ArrayList<>();
//        autoCompleteTextViewLocation.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.toString().length() <= 3) {
//                    names = new ArrayList<String>();
//                    String input = "";
//                    try {
//                        input = "input=" + URLEncoder.encode(s.toString(), "utf-8");
//                    } catch (UnsupportedEncodingException e1) {
//                        e1.printStackTrace();
//                    }
//                    String output = "json";
//                    String parameter = input + "&types=geocode&sensor=true&key="
//                            + browserKey;
//                    url = output + "?" + parameter;
//                    Retrofit retrofit = darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient.getGooglePlaceClient();
//                    ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//                    Call<GooglePlaceApiResponse> jsonObjReq = apiCall.GoogleApiCall(url);
//                    Log.e("well", jsonObjReq.request().url().toString());
//                    jsonObjReq.enqueue(new Callback<GooglePlaceApiResponse>() {
//                        @Override
//                        public void onResponse(Call<GooglePlaceApiResponse> call, Response<GooglePlaceApiResponse> response) {
//
//                            ArrayList<Predictions> predictions = new ArrayList<Predictions>();
//                            predictions = response.body().getPredictions();
//
//                            try {
//                                for (int i = 0; i < predictions.size(); i++) {
//                                    String description = predictions.get(i).getDescription();
//                                    Log.d("description", description);
//                                    names.add(description);
//                                }
//                            } catch (Exception e) {
//                            }
//
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                                    getActivity(),
//                                    android.R.layout.simple_list_item_1, names) {
//                                @Override
//                                public View getView(int position,
//                                                    View convertView, ViewGroup parent) {
//                                    View view = super.getView(position,
//                                            convertView, parent);
//                                    TextView text = (TextView) view
//                                            .findViewById(android.R.id.text1);
//                                    text.setTextColor(Color.BLACK);
//                                    return view;
//                                }
//                            };
//                            autoCompleteTextViewLocation.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<GooglePlaceApiResponse> call, Throwable t) {
//
//
//                        }
//                    });
//
//
//                }
//
//            }
//        });
//
//        if (tournamentdata == null) {
//
//        } else
//
//        {
//            address_line1 = tournamentdata.getAddress_1();
//            city = tournamentdata.getLocation();
//            pin = tournamentdata.getPin();
//            tournament_description = tournamentdata.getDescription();
//            tournament_links = tournamentdata.getTournaments_link();
//
//
//
//            switch (Sport) {
//                case "Cricket":
//                    teamsport = tournamentdata.getCategory().get(0).getTournament_feetype();
//                    if (!teamsport.equals(null) && !teamsport.equals("")) {
//                        switch (teamsport) {
//                            case "PerTeam":
//                                cbTeamfees.setChecked(true);
//                                break;
//
//                            case "PerHead":
//                                cbPerheadfees.setChecked(true);
//                                break;
//                            default:
//                                cbTeamfees.setChecked(false);
//                                cbPerheadfees.setChecked(false);
//                        }
//                    }
//                    break;
//                case "Football":
//                    teamsport = tournamentdata.getCategory().get(0).getTournament_feetype();
//                    if (!teamsport.equals(null) && !teamsport.equals("")) {
//                        switch (teamsport) {
//                            case "PerTeam":
//                                cbTeamfees.setChecked(true);
//                                break;
//
//                            case "PerHead":
//                                cbPerheadfees.setChecked(true);
//                                break;
//                            default:
//                                cbTeamfees.setChecked(false);
//                                cbPerheadfees.setChecked(false);
//                        }
//                    }
//                    break;
//                case "Basket-ball":
//                    teamsport = tournamentdata.getCategory().get(0).getTournament_feetype();
//                    if (!teamsport.equals(null) && !teamsport.equals("")) {
//                        switch (teamsport) {
//                            case "PerTeam":
//                                cbTeamfees.setChecked(true);
//                                break;
//
//                            case "PerHead":
//                                cbPerheadfees.setChecked(true);
//                                break;
//                            default:
//                                cbTeamfees.setChecked(false);
//                                cbPerheadfees.setChecked(false);
//                        }
//                    }
//                    break;
//
//                default:
//
//            }
//            textInputEditTextAddress.setText(address_line1);
//            autoCompleteTextViewLocation.setText(city);
//            textInputEditTextPin.setText(pin);
//            textInputEditTextDescription.setText(tournament_description);
//            textInputEditTextLink.setText(tournament_links);
//            textInputEditTextTourFees.setText(tournamentdata.getCategory().get(0).getTournament_fees());
//            textInputEditTextEventFees.setText(tournamentdata.getCategory().get(0).getEvent_fees());
//            textInputEditTextMax.setText(tournamentdata.getCategory().get(0).getMax_participate());
//
//
//        }

//        buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if (cbTeamfees.isChecked()) {
//                    teamsport = "PerTeam";
//                } else if (cbPerheadfees.isChecked()) {
//                    teamsport = "PerHead";
//                }
//
//
//                address_line1 = textInputEditTextAddress.getText().toString();
//                city = autoCompleteTextViewLocation.getText().toString();
//                pin = textInputEditTextPin.getText().toString();
//                tournament_description = textInputEditTextDescription.getText().toString();
//                tournament_links = textInputEditTextLink.getText().toString();
//                tournamentFees = textInputEditTextTourFees.getText().toString();
//                eventFees = textInputEditTextEventFees.getText().toString();
//                maxParticipate = textInputEditTextMax.getText().toString();
//
//                if (address_line1.equals("")) {
//                    validation[0] = 1;
//                    view_address.setErrorEnabled(true);
//                    view_address.setError("You need to enter tournament address");
//                    scrollview.fullScroll(ScrollView.FOCUS_UP);
//
//                } else {
//                    validation[0] = 0;
//                    view_address.setError(null);
//                }
//                if (city.equals("")) {
//                    validation[1] = 1;
//
//                    view_location.setErrorEnabled(true);
//                    view_location.setError("You need to enter tournament city location");
//                    scrollview.fullScroll(ScrollView.FOCUS_UP);
//
//
//                } else {
//                    validation[1] = 0;
//                    view_location.setError(null);
//                }
//
//                if (pin.equals("")) {
//                    validation[2] = 1;
//                    view_pin.setErrorEnabled(true);
//                    view_pin.setError("You need to enter tournament pin");
//                    scrollview.fullScroll(ScrollView.FOCUS_UP);
//
//
//                } else {
//                    validation[2] = 0;
//                    view_pin.setError(null);
//                }
//
//                if (tournament_description.equals("")) {
//                    validation[3] = 1;
//                    view_description.setErrorEnabled(true);
//                    view_description.setError("You need to enter tournament description");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[3] = 0;
//                    view_description.setError(null);
//                }
//
//                if (tournament_links.equals("")) {
//                    validation[4] = 1;
//                    view_tournament_link.setErrorEnabled(true);
//                    view_tournament_link.setError("You need to enter tournament links");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[4] = 0;
//                    view_tournament_link.setError(null);
//                }
//                if (tournamentFees.equals("")) {
//                    validation[5] = 1;
//                    view_tournament_fees.setErrorEnabled(true);
//                    view_tournament_fees.setError("You need to enter tournament fees");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[5] = 0;
//                    view_tournament_fees.setError(null);
//                }
//                if (eventFees.equals("")) {
//                    validation[6] = 1;
//                    view_event_fees.setErrorEnabled(true);
//                    view_event_fees.setError("You need to enter per event fees");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[6] = 0;
//                    view_event_fees.setError(null);
//                }
//
//
//                if (maxParticipate.equals("")) {
//                    validation[7] = 1;
//                    view_view_max.setErrorEnabled(true);
//                    view_view_max.setError("You need to enter max event per candidate");
//                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
//
//
//                } else {
//                    validation[7] = 0;
//                    view_view_max.setError(null);
//                }
//
//
//                int sum = validation[0] + validation[1] + validation[2] + validation[3] + validation[4]
//                        + validation[5] + validation[6] + validation[7];
//                if (sum == 0) {
//
//                    textViewAddress.setText(address_line1);
//                    textViewLocation.setText(city);
//                    textViewPin.setText(pin);
//                    textViewDescription.setText(tournament_description);
//                    textViewWebsite.setText(tournament_links);
//
//                    switch (Sport) {
//                        case "Cricket":
//                            textViewTourFees.setText(tournamentFees + " / " + teamsport);
//
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//                                TournamentCategoryData t = new TournamentCategoryData
//                                        (categoryArrayList2.get(i).getEventId(),
//                                                categoryArrayList2.get(i).getAge()
//                                                , categoryArrayList2.get(i).getGender(), categoryArrayList2.get(i).getFee()
//                                                , categoryArrayList2.get(i).getEvent(), categoryArrayList2.get(i).getReferanceDate()
//                                                , categoryArrayList2.get(i).getTime(),
//                                                categoryArrayList2.get(i).getGroupId()
//                                                , tournamentFees
//                                                , categoryArrayList2.get(i).getEvent_fees()
//                                                , maxParticipate
//                                                , teamsport, categoryArrayList2.get(i).getTeam_sporttype());
//
//
//                                categoryArrayList2.set(i, t);
//                            }
//
//                            break;
//                        case "Football":
//                            textViewTourFees.setText(tournamentFees + " / " + teamsport);
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//                                TournamentCategoryData t = new TournamentCategoryData
//                                        (categoryArrayList2.get(i).getEventId(),
//                                                categoryArrayList2.get(i).getAge()
//                                                , categoryArrayList2.get(i).getGender(), categoryArrayList2.get(i).getFee()
//                                                , categoryArrayList2.get(i).getEvent(), categoryArrayList2.get(i).getReferanceDate()
//                                                , categoryArrayList2.get(i).getTime(),
//                                                categoryArrayList2.get(i).getGroupId()
//                                                , tournamentFees
//                                                , categoryArrayList2.get(i).getEvent_fees()
//                                                , maxParticipate
//                                                , teamsport, categoryArrayList2.get(i).getTeam_sporttype());
//
//
//                                categoryArrayList2.set(i, t);
//                            }
//                            break;
//                        case "Basket-ball":
//                            textViewTourFees.setText(tournamentFees + " / " + teamsport);
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//                                TournamentCategoryData t = new TournamentCategoryData
//                                        (categoryArrayList2.get(i).getEventId(),
//                                                categoryArrayList2.get(i).getAge()
//                                                , categoryArrayList2.get(i).getGender(), categoryArrayList2.get(i).getFee()
//                                                , categoryArrayList2.get(i).getEvent(), categoryArrayList2.get(i).getReferanceDate()
//                                                , categoryArrayList2.get(i).getTime(),
//                                                categoryArrayList2.get(i).getGroupId()
//                                                , tournamentFees
//                                                , categoryArrayList2.get(i).getEvent_fees()
//                                                , maxParticipate
//                                                , teamsport, categoryArrayList2.get(i).getTeam_sporttype());
//
//
//                                categoryArrayList2.set(i, t);
//                            }
//                            break;
//                        default:
//                            textViewTourFees.setText(tournamentFees);
//                            textViewEventFees.setText(eventFees);
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//                                TournamentCategoryData t = new TournamentCategoryData
//                                        (categoryArrayList2.get(i).getEventId(),
//                                                categoryArrayList2.get(i).getAge()
//                                                , categoryArrayList2.get(i).getGender(), categoryArrayList2.get(i).getFee()
//                                                , categoryArrayList2.get(i).getEvent(), categoryArrayList2.get(i).getReferanceDate()
//                                                , categoryArrayList2.get(i).getTime(),
//                                                categoryArrayList2.get(i).getGroupId()
//                                                , tournamentFees
//                                                , eventFees
//                                                , maxParticipate
//                                                , categoryArrayList2.get(i).getTournament_feetype(), categoryArrayList2.get(i).getTeam_sporttype());
//
//
//                                categoryArrayList2.set(i, t);
//                            }
//                    }
//
//
//                    textViewMax.setText(maxParticipate);
//                    d2 = true;
//
//
//                    dialog.dismiss();
//
//                } else {
//                    Toast.makeText(getActivity(), "Please enter all fields.", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            }
//        });
//
//        imageViewClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }


//    public void checkorgdetail() {
//        network_status = new NetworkStatus(getActivity());
//        if (network_status.isConnectingToInternet()) {
//            try {
//                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
//                String user_id = sharedPreferences1.getString("user_id", null);
//                String org_detail_url = "https://getsporty.in/testingapp/angularapi.php?";
//
//                String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("getorgdetails", "UTF-8")
//                        + "&" + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
//                Retrofit retrofit;
//                retrofit = ApiClient.getClient();
//                ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//                Call<JsonElement> checklogin = apiCall.getsport_List(org_detail_url + data);
//                checklogin.enqueue(new retrofit2.Callback<JsonElement>() {
//                    @Override
//                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                        if (response.body().toString() != null) {
//
//                            JsonElement jsonElement = response.body();
//                            Log.e("JSON RESPONSE", jsonElement.toString());
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(jsonElement.toString());
//                                String status = jsonObject.getString("status");
//                                if (status.equals("0")) {
//                                    imageViewEditOrganizer.setVisibility(View.VISIBLE);
//                                    d3 = false;
//                                    org_id = "0";
//                                } else {
//
//                                    JSONObject data = jsonObject.getJSONObject("data");
//                                    org_id = data.getString("id");
//                                    orgName = data.getString("org_name");
//                                    orgAbout = data.getString("about");
//                                    orgAddress1 = data.getString("address1");
//                                    ordAddress2 = data.getString("address2");
//                                    orgCity = data.getString("city");
//                                    orgState = data.getString("state");
//                                    orgPin = data.getString("pin");
//                                    orgMobile = data.getString("mobile");
//                                    orgEmail = data.getString("email");
//                                    orgGstin = data.getString("gstin");
//
//                                    textViewOrgName.setText(orgName);
//                                    textViewOrgAddress.setText(orgAddress1);
//                                    textViewOrgLocation.setText(orgCity);
//                                    textViewOrgPin.setText(orgPin);
//                                    textViewEmail.setText(orgEmail);
//                                    textViewNumber.setText(orgMobile);
//                                    imageViewEditOrganizer.setVisibility(View.GONE);
//                                    d3 = true;
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonElement> call, Throwable t) {
//
//                    }
//                });
//
//
//                Log.e("Tag", "hit URL:::::::;" + search_url + data);
//
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Toast.makeText(getActivity(), "Please check your internet settings", Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    public void addOrgData() {
//        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.tournament_addorg);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        final TextInputEditText tvOrgName = dialog.findViewById(R.id.org_name);
//        final TextInputEditText tvAbout = dialog.findViewById(R.id.about);
//        final AutoCompleteTextView autoCompleteTextViewstate = dialog.findViewById(R.id.state);
//        final AutoCompleteTextView autoCompleteTextViewcity = dialog.findViewById(R.id.city);
//        final TextInputEditText tvMobile = dialog.findViewById(R.id.mobile);
//        final TextInputEditText tvEmail = dialog.findViewById(R.id.email);
//        final TextInputEditText tvGst = dialog.findViewById(R.id.gst);
//        final TextInputEditText tvAddress = dialog.findViewById(R.id.address);
//        final TextInputEditText tvPin = dialog.findViewById(R.id.pin);
//
//        ImageView imageViewClose = dialog.findViewById(R.id.close);
//        Button buttonSubmit = dialog.findViewById(R.id.submit);
//
//        autoCompleteTextViewcity.setThreshold(0);
//        autoCompleteTextViewcity.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.toString().length() <= 3) {
//                    names = new ArrayList<String>();
//                    String input = "";
//                    try {
//                        input = "input=" + URLEncoder.encode(s.toString(), "utf-8");
//                    } catch (UnsupportedEncodingException e1) {
//                        e1.printStackTrace();
//                    }
//                    String output = "json";
//                    String parameter = input + "&types=geocode&sensor=true&key="
//                            + browserKey;
//                    url = output + "?" + parameter;
//                    Retrofit retrofit = darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient.getGooglePlaceClient();
//                    ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//                    Call<GooglePlaceApiResponse> jsonObjReq = apiCall.GoogleApiCall(url);
//                    Log.e("well", jsonObjReq.request().url().toString());
//                    jsonObjReq.enqueue(new Callback<GooglePlaceApiResponse>() {
//                        @Override
//                        public void onResponse(Call<GooglePlaceApiResponse> call, Response<GooglePlaceApiResponse> response) {
//
//                            ArrayList<Predictions> predictions = new ArrayList<Predictions>();
//                            predictions = response.body().getPredictions();
//
//                            try {
//                                for (int i = 0; i < predictions.size(); i++) {
//                                    String description = predictions.get(i).getDescription();
//                                    Log.d("description", description);
//                                    names.add(description);
//                                }
//                            } catch (Exception e) {
//                            }
//
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                                    getActivity(),
//                                    android.R.layout.simple_list_item_1, names) {
//                                @Override
//                                public View getView(int position,
//                                                    View convertView, ViewGroup parent) {
//                                    View view = super.getView(position,
//                                            convertView, parent);
//                                    TextView text = (TextView) view
//                                            .findViewById(android.R.id.text1);
//                                    text.setTextColor(Color.BLACK);
//                                    return view;
//                                }
//                            };
//                            autoCompleteTextViewcity.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<GooglePlaceApiResponse> call, Throwable t) {
//
//
//                        }
//                    });
//
//
//                }
//
//            }
//        });
//
//        autoCompleteTextViewstate.setThreshold(0);
//        autoCompleteTextViewstate.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                if (s.toString().length() <= 3) {
//                    names = new ArrayList<String>();
//                    String input = "";
//                    try {
//                        input = "input=" + URLEncoder.encode(s.toString(), "utf-8");
//                    } catch (UnsupportedEncodingException e1) {
//                        e1.printStackTrace();
//                    }
//                    String output = "json";
//                    String parameter = input + "&types=geocode&sensor=true&key="
//                            + browserKey;
//                    url = output + "?" + parameter;
//                    Retrofit retrofit = darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient.getGooglePlaceClient();
//                    ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//                    Call<GooglePlaceApiResponse> jsonObjReq = apiCall.GoogleApiCall(url);
//                    Log.e("well", jsonObjReq.request().url().toString());
//                    jsonObjReq.enqueue(new Callback<GooglePlaceApiResponse>() {
//                        @Override
//                        public void onResponse(Call<GooglePlaceApiResponse> call, Response<GooglePlaceApiResponse> response) {
//
//                            ArrayList<Predictions> predictions = new ArrayList<Predictions>();
//                            predictions = response.body().getPredictions();
//
//                            try {
//                                for (int i = 0; i < predictions.size(); i++) {
//                                    String description = predictions.get(i).getDescription();
//                                    Log.d("description", description);
//                                    names.add(description);
//                                }
//                            } catch (Exception e) {
//                            }
//
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                                    getActivity(),
//                                    android.R.layout.simple_list_item_1, names) {
//                                @Override
//                                public View getView(int position,
//                                                    View convertView, ViewGroup parent) {
//                                    View view = super.getView(position,
//                                            convertView, parent);
//                                    TextView text = (TextView) view
//                                            .findViewById(android.R.id.text1);
//                                    text.setTextColor(Color.BLACK);
//                                    return view;
//                                }
//                            };
//                            autoCompleteTextViewstate.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onFailure(Call<GooglePlaceApiResponse> call, Throwable t) {
//                        }
//                    });
//                }
//            }
//        });
//
//        imageViewClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (autoCompleteTextViewstate.getText().toString().equals("")
//                        || autoCompleteTextViewcity.getText().toString().equals("")
//                        || tvOrgName.getText().toString().equals("") || tvAbout.getText().toString().equals("")
//                        || tvMobile.getText().toString().equals("") || tvEmail.getText().toString().equals("")
//                        || tvAddress.getText().toString().equals("") || tvPin.getText().toString().equals("")) {
//                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
//                } else
//
//                {
//                    Retrofit retrofit = ApiClient.getClient();
//                    ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//                    SharedPreferences sharedPreferences_user = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
//                    String userId = sharedPreferences_user.getString("user_id", "");
//                    CreateOrg orgDetail = new CreateOrg(org_id, userId, tvOrgName.getText().toString(),
//                            tvAbout.getText().toString(), tvAddress.getText().toString(), "",
//                            autoCompleteTextViewcity.getText().toString()
//                            , autoCompleteTextViewstate.getText().toString(),
//                            tvPin.getText().toString(), tvMobile.getText().toString(),
//                            tvEmail.getText().toString(), tvGst.getText().toString(), "M");
//
//                    Call<CreateOrgResponse> createOrg = apiCall.createOrg("addOrg", orgDetail);
//
//                    createOrg.enqueue(new Callback<CreateOrgResponse>() {
//                        @Override
//                        public void onResponse(Call<CreateOrgResponse> call, Response<CreateOrgResponse> response) {
//
//                            orgName = tvOrgName.getText().toString();
//                            orgAbout = tvAbout.getText().toString();
//                            orgAddress1 = tvAddress.getText().toString();
//                            orgCity = autoCompleteTextViewcity.getText().toString();
//                            orgState = autoCompleteTextViewstate.getText().toString();
//                            orgPin = tvPin.getText().toString();
//                            orgMobile = tvMobile.getText().toString();
//                            orgEmail = tvEmail.getText().toString();
//
//                            textViewOrgName.setText(orgName);
//                            textViewOrgAddress.setText(orgAddress1);
//                            textViewOrgLocation.setText(orgCity);
//                            textViewOrgPin.setText(orgPin);
//                            textViewEmail.setText(orgEmail);
//                            textViewNumber.setText(orgMobile);
//
//                            imageViewEditOrganizer.setVisibility(View.GONE);
//                            d3 = true;
//                            dialog.dismiss();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<CreateOrgResponse> call, Throwable t) {
//
//                        }
//                    });
//                }
//
//            }
//        });
//
//        dialog.show();
//
//    }

//    private void save_tournament()
//
//    {
//        if (network_status.isConnectingToInternet()) {
//
//            if (d1) {
//                editHeader.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                editHeader.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//
//            if (d2) {
//                editDetails.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                editDetails.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//            if (d3) {
//                addOrgData.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                addOrgData.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//            if (d4) {
//                editCategory.setBackgroundColor(Color.parseColor("#ffffff"));
//
//
//            } else {
//                editCategory.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//            if (d5) {
//                editTerms.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                editTerms.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//            if (d6) {
//                lltermscondition.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                lltermscondition.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//
//
//            if (d1 && d2 && d3 && d4 && d5 && d6) {
//
//                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
//                String user_id = sharedPreferences1.getString("user_id", null);
//                CreateTournamentModel createtournament = new
//                        CreateTournamentModel(TournamentName, user_id, "0",
//                        address_line1,
//                        "", city, pin, "", orgName,
//                        orgMobile, "", orgEmail, orgAddress1,
//                        "", orgCity, orgPin,
//                        tournament_links, tournamentFees, eventFees, maxParticipate,
//                        StartDate, EndDate, EntryStart,
//                        EntryEnd, imageurl, tournament_description, Level,
//                        "", Sport, imageEncoded,
//                        categoryArrayList2,
//                        Terms_condArrayList, refDate);
//
//
//                Retrofit retrofit;
//                ApiAtheliteCall apiCall;
//                retrofit = darkhorsesports.getsporty.notification.ApiClient.getClient();
//                apiCall = retrofit.create(ApiAtheliteCall.class);
//                Call<StudentPaidListResponse> getStudentList = apiCall.createtournament("create_tournament", createtournament);
//                customProgress.showProgress(getActivity(), false);
//                Log.d("CreateClassHitUrl", getStudentList.request().url().toString());
//                getStudentList.enqueue(new Callback<StudentPaidListResponse>() {
//                    @Override
//                    public void onResponse(Call<StudentPaidListResponse> call, Response<StudentPaidListResponse> response) {
//
//                        if (response.body().getStatus().equals("1")) {
//
//                            Toast.makeText(getActivity(), "Tournament successfully created...", Toast.LENGTH_LONG).show();
//                            Intent i = new Intent(getActivity(), BaseActivity.class);
//                            startActivity(i);
//
//
//                        } else {
//                            customProgress.hideProgress();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StudentPaidListResponse> call, Throwable t) {
//                        customProgress.hideProgress();
//                    }
//                });
//
//
//            } else {
//                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//        } else {
//            Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_LONG).show();
//        }
//    }

//    private void update_tournament()
//
//    {
//        if (network_status.isConnectingToInternet()) {
//
//            if (d1) {
//                editHeader.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                editHeader.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//
//            if (d2) {
//                editDetails.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                editDetails.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//            if (d3) {
//                addOrgData.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                addOrgData.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//            if (d4) {
//                editCategory.setBackgroundColor(Color.parseColor("#ffffff"));
//
//
//            } else {
//                editCategory.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//            if (d5) {
//                editTerms.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                editTerms.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//
//            if (d6) {
//                lltermscondition.setBackgroundColor(Color.parseColor("#ffffff"));
//
//            } else {
//                lltermscondition.setBackgroundColor(Color.parseColor("#ff5964"));
//
//            }
//
//
//            if (d1 && d2 && d3 && d4 && d5 && d6) {
//
//                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(MY_PREFS_NAME, 0);
//                String user_id = sharedPreferences1.getString("user_id", null);
//                CreateTournamentModel createtournament = new
//                        CreateTournamentModel(TournamentName, user_id, tournamentdata.getId(),
//                        address_line1,
//                        "", city, pin, "", orgName,
//                        orgMobile, "", orgEmail, orgAddress1,
//                        "", orgCity, orgPin,
//                        tournament_links, tournamentFees, eventFees, maxParticipate, StartDate, EndDate, EntryStart,
//                        EntryEnd, imageurl, tournament_description, Level,
//                        "", Sport, imageEncoded,
//                        categoryArrayList2,
//                        Terms_condArrayList, refDate);
//
//                Retrofit retrofit;
//                ApiAtheliteCall apiCall;
//                retrofit = darkhorsesports.getsporty.notification.ApiClient.getClient();
//                apiCall = retrofit.create(ApiAtheliteCall.class);
//                Call<StudentPaidListResponse> getStudentList = apiCall.createtournament("create_tournament", createtournament);
//                customProgress.showProgress(getActivity(), false);
//                Log.d("CreateClassHitUrl", getStudentList.request().url().toString());
//                getStudentList.enqueue(new Callback<StudentPaidListResponse>() {
//                    @Override
//                    public void onResponse(Call<StudentPaidListResponse> call, Response<StudentPaidListResponse> response) {
//
//                        if (response.body().getStatus().equals("1")) {
//                            customProgress.hideProgress();
//                            Toast.makeText(getActivity(), "Tournament successfully update...", Toast.LENGTH_LONG).show();
//                            //getActivity().onBackPressed();
//                            Intent i = new Intent(getActivity(), BaseActivity.class);
//                            startActivity(i);
//                            //finish();
//
//
//                        } else {
//                            customProgress.hideProgress();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StudentPaidListResponse> call, Throwable t) {
//                        customProgress.hideProgress();
//                    }
//                });
//
//
//            } else {
//                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//        } else {
//            Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_LONG).show();
//        }
//    }


//    private void editCategorydesign() {
//
//        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.category_layout_design);
//        // category_layout_design
//
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        ImageView imageViewClose = dialog.findViewById(R.id.close);
//
//        final CheckBox checkBoxMale = dialog.findViewById(R.id.male);
//        final CheckBox checkBoxFemale = dialog.findViewById(R.id.female);
//        final TextInputEditText textInputEditTextAgeGroup = dialog.findViewById(R.id.age_group);
//
//        final TextView tv_sport = dialog.findViewById(R.id.sport_categories);
//        tv_sport.append(" " + Sport);
//        Button buttonSubmit = dialog.findViewById(R.id.submit);
//
//
//        recyclerViewCategories = dialog.findViewById(R.id.categories);
//        LinearLayout linearLayoutDate = dialog.findViewById(R.id.add_date);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerViewCategories.setLayoutManager(layoutManager);
//
//        if (tournamentdata != null) {
//            categoryArrayList2 = tournamentdata.getCategory();
//            CategoryAdapter categoriesAdapter = new CategoryAdapter(categoryArrayList2);
//            recyclerViewCategories.setAdapter(categoriesAdapter);
//            categoriesAdapter.notifyDataSetChanged();
//
//        } else {
//            categoryArrayList2.clear();
//
//
//        }
////        if (tournamentdata == null)
////        {
////
////        } else {
////            ArrayList<TournamentCategoryData> categorydata = tournamentdata.getCategory();
////            CategoryAdapter categoriesAdapter = new CategoryAdapter(categorydata);
////            recyclerViewCategories.setAdapter(categoriesAdapter);
////            categoriesAdapter.notifyDataSetChanged();
////        }
//
//        linearLayoutDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if (checkBoxMale.isChecked() && checkBoxFemale.isChecked()) {
//                    gender = "B";
//                    value = "Both Male,Female";
//                } else if (checkBoxMale.isChecked()) {
//                    gender = "M";
//                    value = "Male";
//                } else if (checkBoxFemale.isChecked()) {
//                    gender = "F";
//                    value = "Female";
//                } else {
//                    gender = "";
//                    value = "";
//                }
//                String ageGroup = textInputEditTextAgeGroup.getText().toString();
//
//
//                if (ageGroup.equals("")) {
//                    validation[0] = 1;
//                } else {
//                    validation[0] = 0;
//                }
//
//                if (gender.equals("")) {
//                    validation[1] = 1;
//                } else {
//                    validation[1] = 0;
//                }
//
//                int sum = validation[0] + validation[1];
////                        Toast.makeText(getActivity(), fees, Toast.LENGTH_SHORT).show();
//                if (sum == 0) {
//
//
//                    TournamentCategoryData t = new TournamentCategoryData
//                            (categoryId + (categoryArrayList2.size() + 1),
//                                    ageGroup, gender, "", categoryString, st_referance_date, "", "", tournamentFees, eventFees, maxParticipate, teamsport, team_sporttype);
//
//
//                    categoryArrayList2.add(t);
//                    CategoryAdapter categoriesAdapter = new CategoryAdapter(categoryArrayList2);
//                    recyclerViewCategories.setAdapter(categoriesAdapter);
//                    categoriesAdapter.notifyDataSetChanged();
//                    textInputEditTextAgeGroup.setText("");
//                    checkBoxMale.setChecked(false);
//                    checkBoxFemale.setChecked(false);
//
//                    Log.e("arraylist size", String.valueOf(categoryArrayList2.size()));
//
//                } else {
//                    Toast.makeText(getActivity(), "Please enter all fields.", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//        });
//
//
//        imageViewClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (categoryArrayList2.size() > 0) {
//                    dialog.dismiss();
//                    editCategoryApply();
//                }
//            }
//        });
//
//        dialog.show();
//    }


//    private void editCategoryApply() {
//
//        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.selectcategoryaddview);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        Button submit = (Button) dialog.findViewById(R.id.submit);
//
//        editCategoryViewCategories = (RecyclerView) dialog.findViewById(R.id.rv_event);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        editCategoryViewCategories.setLayoutManager(layoutManager);
//        CategoryAdapterApply categoriesAdapter = new CategoryAdapterApply(categoryArrayList2);
//        editCategoryViewCategories.setAdapter(categoriesAdapter);
//
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (Sport) {
//                    case "Cricket":
//
//                        if (categoryArrayList2.size() > 0) {
//                            dialog.dismiss();
//                            d4 = true;
//                            String categorytext = "";
//
//                            textViewCategory.setText("");
//
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//                                String gender = categoryArrayList2.get(i).getGender();
//                                String applyAge = categoryArrayList2.get(i).getAge();
//
//                                if (gender.equals("B")) {
//                                    value = "Both Male,Female";
//                                } else if (gender.equals("M")) {
//
//                                    value = "Male";
//                                } else if (gender.equals("F")) {
//
//                                    value = "Female";
//                                } else {
//
//                                    value = "";
//                                }
//                                categorytext = " Under " + applyAge + " " + value + "\n";
//                                textViewCategory.append(categorytext);
//
//
//                            }
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please click to add event.", Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                        break;
//                    case "Football":
//
//                        if (categoryArrayList2.size() > 0) {
//                            dialog.dismiss();
//                            d4 = true;
//                            String categorytext = "";
//
//                            textViewCategory.setText("");
//
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//                                String gender = categoryArrayList2.get(i).getGender();
//                                String applyAge = categoryArrayList2.get(i).getAge();
//
//                                if (gender.equals("B")) {
//                                    value = "Both Male,Female";
//                                } else if (gender.equals("M")) {
//
//                                    value = "Male";
//                                } else if (gender.equals("F")) {
//
//                                    value = "Female";
//                                } else {
//
//                                    value = "";
//                                }
//                                categorytext = " Under " + applyAge + " " + value + "\n";
//                                textViewCategory.append(categorytext);
//
//
//                            }
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please click to add event.", Toast.LENGTH_SHORT).show();
//
//                        }
//                        break;
//                    case "Basket-ball":
//
//                        if (categoryArrayList2.size() > 0) {
//                            dialog.dismiss();
//                            d4 = true;
//                            String categorytext = "";
//                            textViewCategory.setText("");
//
//
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//                                String gender = categoryArrayList2.get(i).getGender();
//                                String applyAge = categoryArrayList2.get(i).getAge();
//
//                                if (gender.equals("B")) {
//                                    value = "Both Male,Female";
//                                } else if (gender.equals("M")) {
//
//                                    value = "Male";
//                                } else if (gender.equals("F")) {
//
//                                    value = "Female";
//                                } else {
//
//                                    value = "";
//                                }
//                                categorytext = " Under " + applyAge + " " + value + "\n";
//                                textViewCategory.append(categorytext);
//
//
//                            }
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please click to add event.", Toast.LENGTH_SHORT).show();
//
//                        }
//                        break;
//
//                    default:
//                        if (categoryArrayList2.get(0).getEvent() != null && !categoryArrayList2.get(0).getEvent().isEmpty()) {
//                            dialog.dismiss();
//                            d4 = true;
//                            String categorytext = "";
//                            textViewCategory.setText("");
//                            for (int i = 0; i < categoryArrayList2.size(); i++) {
//
//
//                                String gender = categoryArrayList2.get(i).getGender();
//                                String applyAge = categoryArrayList2.get(i).getAge();
//
//                                if (gender.equals("B")) {
//                                    value = "Both Male,Female";
//                                } else if (gender.equals("M")) {
//
//                                    value = "Male";
//                                } else if (gender.equals("F")) {
//
//                                    value = "Female";
//                                } else {
//                                    gender = "";
//                                    value = "";
//                                }
//                                if (categoryArrayList2.get(i).getEvent() != null && !categoryArrayList2.get(i).getEvent().isEmpty()) {
//                                    for (int j = 0; j < categoryArrayList2.get(i).getEvent().size(); j++) {
//
//                                        categorytext = categoryArrayList2.get(i).getEvent().get(j).getEvent() + " Under " + applyAge + " " + value + "\n";
//                                        textViewCategory.append(categorytext);
//
//
//                                    }
//                                }
//
//                            }
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please click to add event.", Toast.LENGTH_SHORT).show();
//
//                        }
//                }
//
//
//            }
//        });
//
//        dialog.show();
//    }


//    private void editTerms() {
//        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.terms_layout);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        recyclerViewTermsCondition = dialog.findViewById(R.id.terms);
//        ImageView imageViewClose = dialog.findViewById(R.id.close);
//        Button buttonSubmit = dialog.findViewById(R.id.submit);
//        LinearLayout linearLayoutAdd = dialog.findViewById(R.id.add_terms);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerViewTermsCondition.setLayoutManager(layoutManager);
//        final TermsAdapter termsAdapter = new TermsAdapter();
//        recyclerViewTermsCondition.setAdapter(termsAdapter);
//
//        linearLayoutAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Terms_condArrayList.size() == 0) {
//                    TermsCoditionItemString termsCoditionItemString = new TermsCoditionItemString("");
//                    Terms_condArrayList.add(termsCoditionItemString);
//                    termsAdapter.notifyItemInserted(Terms_condArrayList.size() - 1);
//                } else {
//
//                    TermsAdapter.ItemViewHolder itemViewHolder = (TermsAdapter.ItemViewHolder) recyclerViewTermsCondition.findViewHolderForAdapterPosition(Terms_condArrayList.size() - 1);
//                    if (itemViewHolder.textInputEditTextTerms.getText().toString().equals("")) {
//                        Toast.makeText(getActivity(), "Fill all details", Toast.LENGTH_SHORT).show();
//                    } else {
//                        TermsCoditionItemString termsCoditionItemString = new TermsCoditionItemString("");
//                        Terms_condArrayList.add(termsCoditionItemString);
//                        termsAdapter.notifyItemInserted(Terms_condArrayList.size() - 1);
//                    }
//                }
//            }
//        });
//
//        imageViewClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textViewTerms.setText("");
//                String termscondition = "";
//                if (Terms_condArrayList != null && Terms_condArrayList.size() > 0 && !Terms_condArrayList.get(Terms_condArrayList.size() - 1).getTerm().equals("")) {
//                    Log.e("Tag", "Terms size" + Terms_condArrayList.size());
//                    for (int i = 0; i < Terms_condArrayList.size(); i++) {
//                        textViewTerms.append(termscondition + " " + Terms_condArrayList.get(i).getTerm() + "\n");
//                        d5 = true;
//                    }
//
//                    dialog.dismiss();
//                } else {
//                    Toast.makeText(getActivity(), "Please enter all fields.", Toast.LENGTH_SHORT).show();
//
//
//                }
//
//            }
//        });
//
//        dialog.show();
//
//    }

//    public void selReferenceDialog(View anchor) {
//
//        final PopupWindow tipWindow = new PopupWindow(getActivity());
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View contentView = inflater.inflate(R.layout.add_ref_date, null);
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) CommonUtils.dipToPixels(getActivity(), 200));
//        layoutParams.setMargins(10, 0, 10, 0);
//        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
//        contentView.setLayoutParams(layoutParams);
////        tipWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
//        tipWindow.setHeight((int) CommonUtils.dipToPixels(getActivity(), 320));
//        tipWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
//        tipWindow.setOutsideTouchable(true);
//        tipWindow.setTouchable(true);
//        tipWindow.setFocusable(true);
//        tipWindow.setBackgroundDrawable(new BitmapDrawable());
//        tipWindow.setContentView(contentView);
//
//        RadioGroup radioGroup = contentView.findViewById(R.id.group);
//        RadioButton radioButtonDate = contentView.findViewById(R.id.date);
//        final RelativeLayout relativeLayoutDate = contentView.findViewById(R.id.ref_date_layout);
//        final TextInputEditText textInputEditTextDate = contentView.findViewById(R.id.ref_date);
//        final TextInputLayout textInputLayoutDate = contentView.findViewById(R.id.view_date);
//        RadioButton radioButtonRange = contentView.findViewById(R.id.range);
//        final LinearLayout linearLayoutRange = contentView.findViewById(R.id.range_layout);
//        final TextInputEditText textInputEditTextStartDate = contentView.findViewById(R.id.start_date);
//        final TextInputLayout textInputLayoutStartDate = contentView.findViewById(R.id.view_start_date);
//        final TextInputEditText textInputEditTextEndDate = contentView.findViewById(R.id.end_date);
//        final TextInputLayout textInputLayoutEndDate = contentView.findViewById(R.id.view_end_date);
//
//        Button buttonSubmit = contentView.findViewById(R.id.submit);
//
//
//        if (refDate != null) {
//            if (refDate.isRange()) {
//                radioButtonRange.setChecked(true);
//                startDate = refDate.getStartYear();
//                endDate = refDate.getEndYear();
//                textInputEditTextStartDate.setText(DateConversion.dateconversionMonth(startDate));
//                textInputEditTextEndDate.setText(DateConversion.dateconversionMonth(endDate));
//            } else {
//                radioButtonDate.setChecked(true);
//                referenceDate = refDate.getRefDate();
//                textInputEditTextDate.setText(DateConversion.dateconversionMonth(referenceDate));
//            }
//
//        }
//
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.date:
//                        isRange = false;
//                        linearLayoutRange.setVisibility(View.GONE);
//                        relativeLayoutDate.setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.range:
//                        isRange = true;
//                        linearLayoutRange.setVisibility(View.VISIBLE);
//                        relativeLayoutDate.setVisibility(View.GONE);
//                        break;
//                }
//            }
//        });
//
//        textInputEditTextDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        ///Hers's what I used to get the day names (0-6 means monday - sunday):
//
//                        Log.e("Tag", "Day   " + d);
//
//
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//                        referenceDate = y + "-" + modMon + "-" + modDay;
//
//                        textInputEditTextDate.setText(d + " " + mon + " " + y);
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
//                calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
//                calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
//                mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                mDatePicker.setTitle("ENTER REFERENCE DATE");
//                mDatePicker.show();
//
//            }
//        });
//
//        textInputEditTextStartDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        ///Hers's what I used to get the day names (0-6 means monday - sunday):
//
//                        Log.e("Tag", "Day   " + d);
//
//
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//                        startDate = y + "-" + modMon + "-" + modDay;
//
//                        textInputEditTextStartDate.setText(d + " " + mon + " " + y);
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
//                calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
//                calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
//                mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                mDatePicker.setTitle("ENTER START DATE");
//                mDatePicker.show();
//
//            }
//        });
//
//
//        textInputEditTextEndDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Calendar calendar = Calendar.getInstance();
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        ///Hers's what I used to get the day names (0-6 means monday - sunday):
//
//                        Log.e("Tag", "Day   " + d);
//
//
//                        String mon = "";
//                        switch (m) {
//                            case 0:
//                                mon = "January";
//                                break;
//                            case 1:
//                                mon = "February";
//                                break;
//                            case 2:
//                                mon = "March";
//                                break;
//                            case 3:
//                                mon = "April";
//                                break;
//                            case 4:
//                                mon = "May";
//                                break;
//                            case 5:
//                                mon = "June";
//                                break;
//                            case 6:
//                                mon = "July";
//                                break;
//                            case 7:
//                                mon = "August";
//                                break;
//                            case 8:
//                                mon = "September";
//                                break;
//                            case 9:
//                                mon = "October";
//                                break;
//                            case 10:
//                                mon = "November";
//                                break;
//                            case 11:
//                                mon = "December";
//                                break;
//
//                        }
//
//                        String modDay, modMon;
//                        if (m + 1 < 10) {
//
//                            modMon = "0" + (m + 1);
//                        } else {
//                            modMon = String.valueOf(m + 1);
//                        }
//                        if (d < 10) {
//
//                            modDay = "0" + d;
//                        } else {
//                            modDay = String.valueOf(d);
//                        }
//
//                        endDate = y + "-" + modMon + "-" + modDay;
//
//                        textInputEditTextEndDate.setText(d + " " + mon + " " + y);
//
//                    }
//                }, year, month, day);
//                mDatePicker.getDatePicker().setCalendarViewShown(false);
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
//                calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
//                calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
//                mDatePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                mDatePicker.setTitle("ENTER END DATE");
//                mDatePicker.show();
//
//            }
//        });
//
//        buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isRange) {
//                    if (startDate.equals("")) {
//                        textInputLayoutStartDate.setErrorEnabled(true);
//                        textInputLayoutStartDate.setError("Enter start date");
//                    } else if (endDate.equals("")) {
//                        textInputLayoutEndDate.setErrorEnabled(true);
//                        textInputLayoutEndDate.setError("Enter end date");
//                    } else {
//                        textInputLayoutStartDate.setErrorEnabled(false);
//                        textInputLayoutEndDate.setErrorEnabled(false);
//                        refDate = new RefDate(false, referenceDate, true, startDate, endDate);
//                    }
//                } else {
//                    if (referenceDate.equals("")) {
//                        textInputLayoutDate.setErrorEnabled(true);
//                        textInputLayoutDate.setError("Enter reference date");
//                    } else {
//                        textInputLayoutDate.setErrorEnabled(false);
//                        refDate = new RefDate(true, referenceDate, false, startDate, endDate);
//                    }
//                }
//
//                if (refDate != null) {
//                    tipWindow.dismiss();
//                }
//
//
//            }
//        });
//
//
//        int screen_pos[] = new int[2];
//// Get location of anchor view on screen
//        anchor.getLocationOnScreen(screen_pos);
//// Get rect for anchor view
//        Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
//                + anchor.getWidth(), screen_pos[1] + anchor.getHeight());
//// Call view measure to calculate how big your view should be.
//        contentView.measure(ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.MATCH_PARENT);
//        int contentViewWidth = contentView.getMeasuredWidth();
//// In this case , i dont need much calculation for x and y position of
//// tooltip
//// For cases if anchor is near screen border, you need to take care of
//// direction as well
//// to show left, right, above or below of anchor view
//        int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
//        int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);
//
//        tipWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x, position_y);
//
//    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ItemViewHolder> {


        public TermsAdapter() {
        }

        @Override
        public TermsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.terms_list_items, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TermsAdapter.ItemViewHolder holder, final int position) {

            holder.textViewSNo.setText((position + 1) + ". ");
            holder.textInputEditTextTerms.setText(Terms_condArrayList.get(position).getTerm());
            holder.textInputEditTextTerms.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Terms_condArrayList.get(position).setTerm(holder.textInputEditTextTerms.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Terms_condArrayList.get(position).setTerm(holder.textInputEditTextTerms.getText().toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return Terms_condArrayList.size();
        }


        public class ItemViewHolder extends RecyclerView.ViewHolder {

            TextView textViewSNo;
            TextInputEditText textInputEditTextTerms;


            public ItemViewHolder(View itemView) {
                super(itemView);

                textViewSNo = itemView.findViewById(R.id.number);
                textInputEditTextTerms = itemView.findViewById(R.id.terms);


            }
        }


    }


    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


        ArrayList<TournamentCategoryData> category = new ArrayList<TournamentCategoryData>();
        private View rootview;

        CategoryAdapter(ArrayList<TournamentCategoryData> category) {
            this.category = category;

        }

        @Override
        public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
            return new CategoryAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
            holder.setItem(category.get(position));
        }

        @Override
        public int getItemCount() {
            return category.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView category_text;

            public ViewHolder(final View itemView) {
                super(itemView);
                category_text = (TextView) itemView.findViewById(R.id.category_text);


            }

            public void setItem(TournamentCategoryData professionalsDataItem) {

                if (professionalsDataItem.getGender().equals("B")) {

                    value = "Both Male,Female";
                } else if (professionalsDataItem.getGender().equals("M")) {

                    value = "Male";
                } else if (professionalsDataItem.getGender().equals("F")) {

                    value = "Female";
                } else {
                    gender = "";
                    value = "";
                }
                category_text.setText("Under " + professionalsDataItem.getAge() + " " + value);
            }

        }
    }


    public class CategoryAdapterApply extends RecyclerView.Adapter<CategoryAdapterApply.ViewHolder> {


        ArrayList<TournamentCategoryData> Tournamentcategory = new ArrayList<TournamentCategoryData>();
        private View rootview;

        CategoryAdapterApply(ArrayList<TournamentCategoryData> category) {
            this.Tournamentcategory = category;

        }

        @Override
        public CategoryAdapterApply.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_applylist_item, parent, false);


            return new CategoryAdapterApply.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(CategoryAdapterApply.ViewHolder holder, int position) {
            holder.setItem(Tournamentcategory.get(position));
        }

        @Override
        public int getItemCount() {
            return Tournamentcategory.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView category_text;
            RelativeLayout relative_text;

            RecyclerView recyclerView_event;

            public ViewHolder(final View itemView) {
                super(itemView);
                category_text = (TextView) itemView.findViewById(R.id.category_text);

                relative_text = (RelativeLayout) itemView.findViewById(R.id.relative_text);

                recyclerView_event = (RecyclerView) itemView.findViewById(R.id.rv_event);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView_event.setLayoutManager(layoutManager);
                // CategoryAdapterApply.ViewHolder itemViewHolder = (CategoryAdapterApply.ViewHolder) recyclerView_event.findViewHolderForAdapterPosition(category.size() - 1);


//                relative_text.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View anchor) {
//                        EventsCategory eventsCategory = new EventsCategory();
//                        for (SportsData sportsData1 : sportsData) {
//                            if (sportsData1.getSports().equals(Sport)) {
//                                eventsCategory = sportsData1.getEvents_category();
//                            }
//                        }
//
//                        final ArrayList<String> male = eventsCategory.getMale();
//
//
//                        switch (Sport) {
//                            case "Cricket":
//
//
////                                showTypeDialog2(position, recyclerView_event, view, defaultarraylist, EventId, Age
////                                        , Gender, Fee, Date, Time);
//
//                                break;
//                            case "Football":
//
////                                showTypeDialog2(position, recyclerView_event, view, defaultarraylist, EventId, Age
////                                        , Gender, Fee, Date, Time);
//                                break;
//                            case "Basket-ball":
//
////                                showTypeDialog2(position, recyclerView_event, view, defaultarraylist, EventId, Age
////                                        , Gender, Fee, Date, Time);
//
//                                break;
//
//                            default:
//
//                                PopupWindow tipWindow = new PopupWindow(getActivity());
//                                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                                View contentView = inflater.inflate(R.layout.swim_distance_layout, null);
//                                Display display = getActivity().getWindowManager().getDefaultDisplay();
//                                Point size = new Point();
//                                display.getSize(size);
//                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) CommonUtils.dipToPixels(getActivity(), 200));
//                                layoutParams.setMargins(10, 0, 10, 0);
//                                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
//                                contentView.setLayoutParams(layoutParams);
//                                tipWindow.setHeight((int) CommonUtils.dipToPixels(getActivity(), 200));
//                                tipWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
//                                tipWindow.setOutsideTouchable(true);
//                                tipWindow.setTouchable(true);
//                                tipWindow.setFocusable(true);
//                                tipWindow.setBackgroundDrawable(new BitmapDrawable());
//                                tipWindow.setContentView(contentView);
//
//                                final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
//                                LinearLayout linearLayout = contentView.findViewById(R.id.layout);
//                                for (int i = 0; i < male.size(); i++) {
//                                    CheckBox checkBox = new CheckBox(getActivity());
//                                    checkBox.setText(male.get(i));
//                                    checkBoxes.add(checkBox);
//                                    linearLayout.addView(checkBox);
//
//                                }
//
//                                int screen_pos[] = new int[2];
//
//                                anchor.getLocationOnScreen(screen_pos);
//
//                                Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
//                                        + anchor.getWidth(), screen_pos[1] + anchor.getHeight());
//
//                                contentView.measure(ActionBar.LayoutParams.MATCH_PARENT,
//                                        ActionBar.LayoutParams.MATCH_PARENT);
//                                int contentViewWidth = contentView.getMeasuredWidth();
//
//                                final int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
//                                int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);
//
//                                tipWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x, position_y);
//                                tipWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                                    @Override
//                                    public void onDismiss() {
//                                        categoryString = new ArrayList<CategoriesString>();
//
//                                        categoryString.clear();
//                                        final int position = editCategoryViewCategories.getChildAdapterPosition(itemView);
//                                        final TournamentCategoryData sportEducationData = Tournamentcategory.get(position);
//
//
//                                        for (int i = 0; i < checkBoxes.size(); i++) {
//                                            if (checkBoxes.get(i).isChecked()) {
//                                                CategoriesString objcategories = new CategoriesString(checkBoxes.get(i).getText().toString(), String.valueOf(position + i));
//                                                categoryString.add(objcategories);
//
//
//                                            }
//
//
//                                        }
//
//                                        sportEducationData.setEvent(categoryString);
//                                        EventCategoryAdapter categoriesAdapter = new EventCategoryAdapter(categoryString);
//                                        recyclerView_event.setAdapter(categoriesAdapter);
//                                        categoriesAdapter.notifyDataSetChanged();
//
//
//                                    }
//                                });
//
//
//                        }
//
//
//                    }
//                });

            }

            public void setItem(TournamentCategoryData professionalsDataItem)

            {
                if (professionalsDataItem.getGender().equals("B")) {

                    value = "Both Male,Female";
                } else if (professionalsDataItem.getGender().equals("M")) {

                    value = "Male";
                } else if (professionalsDataItem.getGender().equals("F")) {

                    value = "Female";
                } else {
                    gender = "";
                    value = "";
                }
                category_text.setText("Under " + professionalsDataItem.getAge() + " " + value);


            }

        }
    }


//    public class EventCategoryAdapter extends RecyclerView.Adapter<EventCategoryAdapter.ViewHolder> {
//
//
//        ArrayList<CategoriesString> category = new ArrayList<CategoriesString>();
//        private View rootview;
//
//        EventCategoryAdapter(ArrayList<CategoriesString> category) {
//            this.category = category;
//
//        }
//
//        @Override
//        public EventCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
//            return new EventCategoryAdapter.ViewHolder(rootview);
//        }
//
//        @Override
//        public void onBindViewHolder(EventCategoryAdapter.ViewHolder holder, int position) {
//            holder.setItem(category.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return category.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            private TextView category_text;
//
//            public ViewHolder(final View itemView) {
//                super(itemView);
//                category_text = (TextView) itemView.findViewById(R.id.category_text);
//
//
//            }
//
//            public void setItem(CategoriesString DataItem) {
//
//                category_text.setText(DataItem.getEvent());
//            }
//
//        }
//    }


}
