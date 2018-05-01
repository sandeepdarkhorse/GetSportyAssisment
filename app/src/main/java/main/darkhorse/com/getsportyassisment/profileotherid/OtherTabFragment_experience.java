package main.darkhorse.com.getsportyassisment.profileotherid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//import darkhorsesports.getsporty.R;
//import darkhorsesports.getsporty.athleteprofilemodelclassess.ApiAtheliteCall;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachHeader;
//import darkhorsesports.getsporty.coachprofilemodelclassess.FormalEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.GlobalUserProfileData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.OtherCertificationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.PlayerExperienceData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.SportEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.UserProfileCoachEditResponse;
//import darkhorsesports.getsporty.coachprofilemodelclassess.WorkExperienceData;
//import darkhorsesports.getsporty.customclasses.DateConversion;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachHeader;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.GlobalUserProfileData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OtherTabFragment_experience extends Fragment implements ApiDataOtherdata, View.OnClickListener {

    private static String mdy;
    public ArrayList<HashMap<String, String>> list_data_work_ex = new ArrayList<HashMap<String, String>>();
    View rootView;
    String[] keys1 = {"designation",
            "organisationName",
            "description",
            "dateFrom",
            "dateTo"};
    OtherProfileEducation coachEducation;
    OtherExperience coachExperience;
    Boolean isOnePressed1 = false;
    String TilldateParamenter;
    String str_from_dateServer1;
    String end_from_dateServer1;
    String str_from_dateServer1_adapter;
    String end_from_dateServer1_adapter;
    String str_from_dateServer2;
    String end_from_dateServer2;
    String str_from_dateServer2_adapter;
    String end_from_dateServer2_adapter;
    CardView cardview_work_experience;
    CardView cardview_experience_as_player;
    private ListView playerExList;
    private ListView workExList;
    private ImageView addWorkExperience;
    private ImageView addPlayerExperience;
    private String str_from_date_work_ex;
    private String str_to_date_work_ex;
    private String str_from_date_player;
    private String str_to_date_player;
    private HashMap<String, String> experienceData;
    private GlobalUserProfileData globalUserProfileData = GlobalUserProfileData.getInstance();
    private CoachHeader coachHeader;
    private ArrayList<HashMap<String, String>> list_data_player_ex = new ArrayList<HashMap<String, String>>();
    private String user_id;
    private MyWorkAdapter myWorkAdapter;
    private RecyclerView workExperienceRecyclerView;
    private ArrayList<WorkExperienceData> myWorkExperienceList;
    private ArrayList<PlayerExperienceData> myPlayerExperienceList;
    private RecyclerView playerExperienceRecyclerView;
    private String prof_id;
    private String otherUserId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_experienceallprofile, container, false);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("Dashboard_prefs", 0);
        user_id = sharedPreferences1.getString("user_id", "");
        prof_id = sharedPreferences1.getString("prof_id", "");

        myWorkExperienceList = new ArrayList<WorkExperienceData>();
        myPlayerExperienceList = new ArrayList<PlayerExperienceData>();

        addWorkExperience = (ImageView) rootView.findViewById(R.id.addWorkExperience);
        addPlayerExperience = (ImageView) rootView.findViewById(R.id.addPlayerExperience);

        workExperienceRecyclerView = (RecyclerView) rootView.findViewById(R.id.work_experience);
        RecyclerView.LayoutManager layoutManagerWork = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        workExperienceRecyclerView.setLayoutManager(layoutManagerWork);

        playerExperienceRecyclerView = (RecyclerView) rootView.findViewById(R.id.player_experience);
        RecyclerView.LayoutManager layoutManagerPlayer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        playerExperienceRecyclerView.setLayoutManager(layoutManagerPlayer);
        addWorkExperience.setOnClickListener(this);
        addPlayerExperience.setOnClickListener(this);
        cardview_work_experience = (CardView) rootView.findViewById(R.id.cardview_work_experience);
        cardview_experience_as_player = (CardView) rootView.findViewById(R.id.cardview_experience_as_player);
        RelativeLayout relativeLayout = rootView.findViewById(R.id.experience_as_player_layout);
        relativeLayout.setVisibility(View.GONE);
        playerExperienceRecyclerView.setVisibility(View.GONE);
        cardview_experience_as_player.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void getEducationDataAll(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {
    }

    @Override
    public void getExperienceDataAll(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid) {
        otherUserId = userid;
        myWorkExperienceList = work;
        myPlayerExperienceList = player;
        if (myWorkExperienceList.size() > 0) {
            cardview_work_experience.setVisibility(View.GONE);
            myWorkAdapter = new MyWorkAdapter(myWorkExperienceList);
            workExperienceRecyclerView.setAdapter(myWorkAdapter);
        } else {
            cardview_work_experience.setVisibility(View.VISIBLE);
        }
//        if (myPlayerExperienceList!=null && myPlayerExperienceList.size() > 0)
//        {
//            cardview_experience_as_player.setVisibility(View.GONE);
//            MyPlayerAdapter  myPlayerAdapter = new MyPlayerAdapter(myPlayerExperienceList);
//            playerExperienceRecyclerView.setAdapter(myPlayerAdapter);
//        } else {
//            cardview_experience_as_player.setVisibility(View.VISIBLE);
//        }

        if (!user_id.equals(otherUserId)) {
            addPlayerExperience.setVisibility(View.GONE);
            addWorkExperience.setVisibility(View.GONE);
        }
    }

    @Override
    public void getOthersDataAll(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {

    }


    public void setData(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other) {
        Log.d("SportEducation", sport.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addWorkExperience:
                workExperienceDialog();
                break;
            case R.id.addPlayerExperience:
                playerExperienceDialog();
                break;
        }
    }

    private void playerExperienceDialog() {
        str_to_date_player = "";
        str_from_date_player = "";
        str_from_dateServer1 = "";
        end_from_dateServer1 = "";
        TilldateParamenter = "0";
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.coach_profile_playerexperience);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));

        final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
        submit_details.setText("Save experience");
        final TextView title_text = (TextView) dialog.findViewById(R.id.toolbar_title);
        title_text.setText("Player experience");
        Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designation);
        final TextInputEditText description = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience);
//        final TextInputEditText description1 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience1);
//        final TextInputEditText description2 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience2);
        final TextInputEditText orgName = (TextInputEditText) dialog.findViewById(R.id.organizationName);
        final TextInputEditText tv_from_date = (TextInputEditText) dialog.findViewById(R.id.tv_from_date);
        final TextInputEditText tv_to_date = (TextInputEditText) dialog.findViewById(R.id.tv_to_date);


        final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
        final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);

        final TextView close = (TextView) dialog.findViewById(R.id.close);
        final TextView tv_till_date = (TextView) dialog.findViewById(R.id.tv_till_date);
        final LinearLayout till_date = (LinearLayout) dialog.findViewById(R.id.till_date);
        final ImageView image_view = (ImageView) dialog.findViewById(R.id.im_till_date);
        final RelativeLayout Rl_till_date = (RelativeLayout) dialog.findViewById(R.id.relative_till_date);
        till_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnePressed1) {
                    Rl_till_date.setVisibility(View.VISIBLE);
                    image_view.setImageResource(R.drawable.whiteback_without_corner);
                    isOnePressed1 = false;
                    to_date.setClickable(true);

                    TilldateParamenter = "0";
                } else {
                    Rl_till_date.setVisibility(View.GONE);
                    image_view.setImageResource(R.drawable.whiteback_with_corner);

                    to_date.setClickable(false);
//                    tv_to_date.setText("Till Date");
                    end_from_dateServer1 = "Till Date";
                    isOnePressed1 = true;
                    TilldateParamenter = "1";
                }

            }
        });

        close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.dismiss();

                return true;
            }
        });

        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                description.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 29) {
//                            description1.requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                            Toast.makeText(getActivity(), "Wrong Starting Date", Toast.LENGTH_SHORT).show();
//                            str_from_date_player = ("");
//                            tv_from_date.setText(str_from_date_player);
//
//                        } else {
//
//                        }
                        String mon = "";
                        switch (m) {
                            case 0:
                                mon = "Jan";
                                break;
                            case 1:
                                mon = "Feb";
                                break;
                            case 2:
                                mon = "Mar";
                                break;
                            case 3:
                                mon = "Apr";
                                break;
                            case 4:
                                mon = "May";
                                break;
                            case 5:
                                mon = "Jun";
                                break;
                            case 6:
                                mon = "Jul";
                                break;
                            case 7:
                                mon = "Aug";
                                break;
                            case 8:
                                mon = "Sept";
                                break;
                            case 9:
                                mon = "Oct";
                                break;
                            case 10:
                                mon = "Nov";
                                break;
                            case 11:
                                mon = "Dec";
                                break;

                        }
                        String modDay, modMon;
                        if (m + 1 < 10) {

                            modMon = "0" + (m + 1);
                        } else {
                            modMon = String.valueOf(m + 1);
                        }
                        if (d < 10) {

                            modDay = "0" + d;
                        } else {
                            modDay = String.valueOf(d);
                        }

                        str_from_dateServer1 = y + "-" + modMon + "-" + modDay;
                        str_from_date_player = d + " " + mon + " " + y;
                        tv_from_date.setText(str_from_date_player);

//                        String da = d + " " + mon + " " + y;
//                        str_from_date_player = (da);
//                        tv_from_date.setText(str_from_date_player);

                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle("ENTER STARTING DATE");
                mDatePicker.show();
            }


        });

        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                            Toast.makeText(getActivity(), "Wrong ending Date", Toast.LENGTH_SHORT).show();
//                            str_to_date_player = ("");
//                            tv_to_date.setText(str_to_date_player);
//                        } else {
//
//                        }
                        String mon = "";
                        switch (m) {
                            case 0:
                                mon = "Jan";
                                break;
                            case 1:
                                mon = "Feb";
                                break;
                            case 2:
                                mon = "Mar";
                                break;
                            case 3:
                                mon = "Apr";
                                break;
                            case 4:
                                mon = "May";
                                break;
                            case 5:
                                mon = "Jun";
                                break;
                            case 6:
                                mon = "Jul";
                                break;
                            case 7:
                                mon = "Aug";
                                break;
                            case 8:
                                mon = "Sept";
                                break;
                            case 9:
                                mon = "Oct";
                                break;
                            case 10:
                                mon = "Nov";
                                break;
                            case 11:
                                mon = "Dec";
                                break;

                        }
                        String modDay, modMon;
                        if (m + 1 < 10) {

                            modMon = "0" + (m + 1);
                        } else {
                            modMon = String.valueOf(m + 1);
                        }
                        if (d < 10) {

                            modDay = "0" + d;
                        } else {
                            modDay = String.valueOf(d);
                        }

                        end_from_dateServer1 = y + "-" + modMon + "-" + modDay;
                        str_to_date_player = d + " " + mon + " " + y;
                        tv_to_date.setText(str_to_date_player);
                        TilldateParamenter = "0";


                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle("ENTER ENDING DATE");
                mDatePicker.show();
            }


        });
        dialog.show();

        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (designation.getText().toString().length() > 0 && orgName.getText().toString().length() > 0 && description.getText().toString().length() > 0 && str_from_dateServer1.length() > 0 && end_from_dateServer1.length() > 0) {
                    PlayerExperienceData playerExperienceData = new PlayerExperienceData(designation.getText().toString(), orgName.getText().toString(), description.getText().toString(), str_from_dateServer1, end_from_dateServer1, TilldateParamenter);
                    myPlayerExperienceList.add(playerExperienceData);
                    MyPlayerAdapter playerAdapter = new MyPlayerAdapter(myPlayerExperienceList);
                    playerExperienceRecyclerView.setAdapter(playerAdapter);
                    playerAdapter.notifyDataSetChanged();
                    updateData(2);
                    dialog.dismiss();

                } else {
                    Toast.makeText(getActivity(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void workExperienceDialog() {
        str_to_date_work_ex = "";
        str_from_date_work_ex = "";
        str_from_dateServer2 = "";
        end_from_dateServer2 = "";
        TilldateParamenter = "0";
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.coach_profile_experience_edit);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));

        final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designation);
        final TextInputEditText description1 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience1);
        final TextInputEditText description2 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience2);
        final TextInputEditText description = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience);
        final TextInputEditText orgName = (TextInputEditText) dialog.findViewById(R.id.organizationName);

        final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
        final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);

        final TextView close = (TextView) dialog.findViewById(R.id.close);
        final TextInputEditText tv_from_date = (TextInputEditText) dialog.findViewById(R.id.tv_from_date);
        final TextInputEditText tv_to_date = (TextInputEditText) dialog.findViewById(R.id.tv_to_date);
        final TextView tv_till_date = (TextView) dialog.findViewById(R.id.tv_till_date);
        final LinearLayout till_date = (LinearLayout) dialog.findViewById(R.id.till_date);
        final ImageView image_view = (ImageView) dialog.findViewById(R.id.im_till_date);
        final RelativeLayout Rl_till_date = (RelativeLayout) dialog.findViewById(R.id.relative_till_date);

        final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
        submit_details.setText("Save experience");
        final TextView title_text = (TextView) dialog.findViewById(R.id.toolbar_title);
        title_text.setText("Edit work experience");
        Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        till_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnePressed1) {
                    Rl_till_date.setVisibility(View.VISIBLE);
                    image_view.setImageResource(R.drawable.whiteback_without_corner);
                    isOnePressed1 = false;
                    to_date.setClickable(true);
                    TilldateParamenter = "0";
                } else {
                    image_view.setImageResource(R.drawable.whiteback_with_corner);
                    Rl_till_date.setVisibility(View.GONE);
                    to_date.setClickable(false);
                    TilldateParamenter = "1";
                    isOnePressed1 = true;
                    end_from_dateServer2 = "Till Date";

                }

            }
        });


        close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.dismiss();

                return true;
            }
        });


        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {

                        String mon = "";
                        switch (m) {
                            case 0:
                                mon = "Jan";
                                break;
                            case 1:
                                mon = "Feb";
                                break;
                            case 2:
                                mon = "Mar";
                                break;
                            case 3:
                                mon = "Apr";
                                break;
                            case 4:
                                mon = "May";
                                break;
                            case 5:
                                mon = "Jun";
                                break;
                            case 6:
                                mon = "Jul";
                                break;
                            case 7:
                                mon = "Aug";
                                break;
                            case 8:
                                mon = "Sept";
                                break;
                            case 9:
                                mon = "Oct";
                                break;
                            case 10:
                                mon = "Nov";
                                break;
                            case 11:
                                mon = "Dec";
                                break;

                        }
                        String modDay, modMon;
                        if (m + 1 < 10) {

                            modMon = "0" + (m + 1);
                        } else {
                            modMon = String.valueOf(m + 1);
                        }
                        if (d < 10) {

                            modDay = "0" + d;
                        } else {
                            modDay = String.valueOf(d);
                        }

                        str_from_dateServer2 = y + "-" + modMon + "-" + modDay;
                        str_from_date_work_ex = d + " " + mon + " " + y;
                        tv_from_date.setText(str_from_date_work_ex);

//                        String da = d + " " + mon + " " + y;
//                        str_from_date_work_ex = (da);
//                        tv_from_date.setText(str_from_date_work_ex);

                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle("ENTER STARTING DATE");
                mDatePicker.show();
            }


        });

        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {


                        String mon = "";
                        switch (m) {
                            case 0:
                                mon = "Jan";
                                break;
                            case 1:
                                mon = "Feb";
                                break;
                            case 2:
                                mon = "Mar";
                                break;
                            case 3:
                                mon = "Apr";
                                break;
                            case 4:
                                mon = "May";
                                break;
                            case 5:
                                mon = "Jun";
                                break;
                            case 6:
                                mon = "Jul";
                                break;
                            case 7:
                                mon = "Aug";
                                break;
                            case 8:
                                mon = "Sept";
                                break;
                            case 9:
                                mon = "Oct";
                                break;
                            case 10:
                                mon = "Nov";
                                break;
                            case 11:
                                mon = "Dec";
                                break;

                        }
                        String modDay, modMon;
                        if (m + 1 < 10) {

                            modMon = "0" + (m + 1);
                        } else {
                            modMon = String.valueOf(m + 1);
                        }
                        if (d < 10) {

                            modDay = "0" + d;
                        } else {
                            modDay = String.valueOf(d);
                        }

                        end_from_dateServer2 = y + "-" + modMon + "-" + modDay;
                        str_to_date_work_ex = d + " " + mon + " " + y;
                        tv_to_date.setText(str_to_date_work_ex);
                        TilldateParamenter = "0";


                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle("ENTER ENDING DATE");
                mDatePicker.show();
            }


        });
        final ScrollView scrollview = (ScrollView) dialog.findViewById(R.id.experiance_scroll_view);
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

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollview.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        dialog.show();

        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (designation.getText().toString().length() > 0 && orgName.getText().toString().length() > 0 && description.getText().toString().length() > 0 && str_from_dateServer2.length() > 0 && end_from_dateServer2.length() > 0) {
                    WorkExperienceData workExperienceData = new WorkExperienceData(designation.getText().toString(), orgName.getText().toString(), description.getText().toString() + " " + description1.getText().toString() + " " + description2.getText().toString()
                            , str_from_dateServer2, end_from_dateServer2, TilldateParamenter);
//                    description.getText().toString() + " " + description1.getText().toString() + " " + description2.getText().toString()


                    //globalUserProfileData.workExperienceGlobal.add(workExperienceData);
                    myWorkExperienceList.add(workExperienceData);
                    MyWorkAdapter workAdapter = new MyWorkAdapter(myWorkExperienceList);
                    workExperienceRecyclerView.setAdapter(workAdapter);
                    workAdapter.notifyDataSetChanged();
                    updateData(1);
                    dialog.dismiss();

                } else {
                    Toast.makeText(getActivity(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void updateData(int view) {

        switch (view) {
            case 1:

                cardview_work_experience.setVisibility(View.GONE);
                break;
            case 2:
                cardview_experience_as_player.setVisibility(View.GONE);
                break;


            default:
        }


        Retrofit retrofit = ApiClient.getClient();
        ApiAtheliteCall call = retrofit.create(ApiAtheliteCall.class);
        // coachEducation = new OtherProfileEducation(globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal);
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

    public String dateconversion(String input) {

        try {

            Date myDate = new SimpleDateFormat("yyyy-MM-dd").parse(input);
            SimpleDateFormat mdyFormat = new SimpleDateFormat("dd MMM yyyy");

            // Format the date to Strings
            mdy = mdyFormat.format(myDate);


        } catch (ParseException exp) {
            exp.printStackTrace();
        }

        return mdy;
    }

    public class MyWorkAdapter extends RecyclerView.Adapter<MyWorkAdapter.ViewHolder> {

        private View rootview;
        private ArrayList<WorkExperienceData> workExperienceData;

        MyWorkAdapter(ArrayList<WorkExperienceData> workExperienceData) {
            this.workExperienceData = workExperienceData;

        }

        @Override
        public MyWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_list_item, parent, false);
            return new MyWorkAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(MyWorkAdapter.ViewHolder holder, int position) {
            holder.setItem(workExperienceData.get(position));
//            holder.textview.setText("well");

        }

        @Override
        public int getItemCount() {
            return workExperienceData.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView desination;
            TextView org_name;
            TextView description;
            TextView duractionExp;
            ImageView editData;


            public ViewHolder(final View itemView) {
                super(itemView);
                desination = (TextView) itemView.findViewById(R.id.designation);
                org_name = (TextView) itemView.findViewById(R.id.org_name);
                description = (TextView) itemView.findViewById(R.id.descriptionExp);
                duractionExp = (TextView) itemView.findViewById(R.id.durationExp);
                editData = (ImageView) itemView.findViewById(R.id.edit_data);


                if (user_id.equals(otherUserId)) {
                    editData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final int position = workExperienceRecyclerView.getChildAdapterPosition(itemView);
//                            Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                            str_to_date_work_ex = "";
                            str_from_date_work_ex = "";
                            str_from_dateServer2_adapter = "";
                            end_from_dateServer2_adapter = "";
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                            dialog.setContentView(R.layout.coach_profile_experience_edit);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));

                            final TextView title_text = (TextView) dialog.findViewById(R.id.title);
//                            title_text.setText("Experience As Player");


                            final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designation);
                            final TextInputEditText eDescription = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience);
                            final TextInputEditText eDescription1 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience1);
                            final TextInputEditText eDescription2 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience2);
                            final TextInputEditText orgName = (TextInputEditText) dialog.findViewById(R.id.organizationName);
                            final TextInputEditText tv_from_date = (TextInputEditText) dialog.findViewById(R.id.tv_from_date);
                            final TextInputEditText tv_to_date = (TextInputEditText) dialog.findViewById(R.id.tv_to_date);
                            final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
                            final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
                            final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);

                            final TextView close = (TextView) dialog.findViewById(R.id.close);

                            final TextView tv_till_date = (TextView) dialog.findViewById(R.id.tv_till_date);
                            final LinearLayout till_date = (LinearLayout) dialog.findViewById(R.id.till_date);
                            final ImageView image_view = (ImageView) dialog.findViewById(R.id.im_till_date);
                            final RelativeLayout Rl_till_date = (RelativeLayout) dialog.findViewById(R.id.relative_till_date);

                            Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
                            toolbar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            String str_till_date = workExperienceData.get(position).getTillDate();
                            if (str_till_date.equals("1")) {
                                Rl_till_date.setVisibility(View.GONE);
                                image_view.setImageResource(R.drawable.whiteback_with_corner);
                                TilldateParamenter = "1";
                                isOnePressed1 = true;
                                end_from_dateServer2_adapter = "Till Date";
                                str_from_dateServer2_adapter = workExperienceData.get(position).getDateFrom();

                            } else {
                                Rl_till_date.setVisibility(View.VISIBLE);
                                image_view.setImageResource(R.drawable.whiteback_without_corner);
                                TilldateParamenter = "0";
                                isOnePressed1 = false;
                                end_from_dateServer2_adapter = workExperienceData.get(position).getDateTo();
                                str_from_dateServer2_adapter = workExperienceData.get(position).getDateFrom();
                            }


                            till_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (isOnePressed1) {
                                        Rl_till_date.setVisibility(View.VISIBLE);
                                        image_view.setImageResource(R.drawable.whiteback_without_corner);
                                        isOnePressed1 = false;
                                        tv_to_date.setClickable(true);

                                    } else {
                                        image_view.setImageResource(R.drawable.whiteback_with_corner);
                                        Rl_till_date.setVisibility(View.GONE);
                                        tv_to_date.setClickable(false);
                                        tv_to_date.setText("Till Date");
                                        end_from_dateServer2_adapter = "Till Date";
                                        isOnePressed1 = true;
                                        TilldateParamenter = "1";
                                    }

                                }
                            });

                            String total = description.getText().toString();

                            if (description.getText().toString() != null) {
                                String descriptionpart[] = DateConversion.splitInParts(description.getText().toString(), 40);
                                int length = descriptionpart.length;
                                switch (length) {
                                    case 1:
                                        eDescription.setText(descriptionpart[0]);
                                        break;
                                    case 2:
                                        eDescription.setText(descriptionpart[0]);
                                        eDescription1.setText(descriptionpart[1]);
                                        break;
                                    case 3:
                                        eDescription.setText(descriptionpart[0]);
                                        eDescription1.setText(descriptionpart[1]);
                                        eDescription2.setText(descriptionpart[2]);
                                        break;
                                    default:
                                        eDescription.setText(descriptionpart[0]);
                                        eDescription1.setText(descriptionpart[1]);
                                        eDescription2.setText(descriptionpart[2] + descriptionpart[3]);
                                }


                            }


                            designation.setText(desination.getText());
                            orgName.setText(org_name.getText());

                            String dura = duractionExp.getText().toString();

                            String[] split = dura.split("to");
                            if (split.length >= 2) {
                                tv_from_date.setText(split[0]);
                                tv_to_date.setText(split[1]);
                                str_to_date_work_ex = split[1];
                                str_from_date_work_ex = split[0];
                            } else {
                                designation.setText("");
                                orgName.setText("");
                                eDescription.setText("");
                                eDescription1.setText("");
                                eDescription2.setText("");
                                tv_from_date.setText("");
                                tv_to_date.setText("");
                            }


                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();

                                }
                            });

                            tv_from_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                        public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                                            if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                                                Toast.makeText(getActivity(), "Wrong Starting Date", Toast.LENGTH_SHORT).show();
//                                                str_from_date_work_ex = ("");
//                                                tv_from_date.setText(str_from_date_work_ex);
//
//                                            } else {
//
//                                            }
                                            String mon = "";
                                            switch (m) {
                                                case 0:
                                                    mon = "Jan";
                                                    break;
                                                case 1:
                                                    mon = "Feb";
                                                    break;
                                                case 2:
                                                    mon = "Mar";
                                                    break;
                                                case 3:
                                                    mon = "Apr";
                                                    break;
                                                case 4:
                                                    mon = "May";
                                                    break;
                                                case 5:
                                                    mon = "Jun";
                                                    break;
                                                case 6:
                                                    mon = "Jul";
                                                    break;
                                                case 7:
                                                    mon = "Aug";
                                                    break;
                                                case 8:
                                                    mon = "Sept";
                                                    break;
                                                case 9:
                                                    mon = "Oct";
                                                    break;
                                                case 10:
                                                    mon = "Nov";
                                                    break;
                                                case 11:
                                                    mon = "Dec";
                                                    break;

                                            }
                                            String modDay, modMon;
                                            if (m + 1 < 10) {

                                                modMon = "0" + (m + 1);
                                            } else {
                                                modMon = String.valueOf(m + 1);
                                            }
                                            if (d < 10) {

                                                modDay = "0" + d;
                                            } else {
                                                modDay = String.valueOf(d);
                                            }


                                            str_from_dateServer2_adapter = y + "-" + modMon + "-" + modDay;
                                            str_from_date_work_ex = d + " " + mon + " " + y;
                                            tv_from_date.setText(str_from_date_work_ex);


                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle("ENTER STARTING DATE");
                                    mDatePicker.show();
                                }


                            });

                            tv_to_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                        public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                                            if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                                                Toast.makeText(getActivity(), "Wrong ending Date", Toast.LENGTH_SHORT).show();
//                                                str_to_date_work_ex = ("");
//                                                tv_to_date.setText(str_to_date_work_ex);
//                                            } else {
//
//
//                                            }
                                            String mon = "";
                                            switch (m) {
                                                case 0:
                                                    mon = "Jan";
                                                    break;
                                                case 1:
                                                    mon = "Feb";
                                                    break;
                                                case 2:
                                                    mon = "Mar";
                                                    break;
                                                case 3:
                                                    mon = "Apr";
                                                    break;
                                                case 4:
                                                    mon = "May";
                                                    break;
                                                case 5:
                                                    mon = "Jun";
                                                    break;
                                                case 6:
                                                    mon = "Jul";
                                                    break;
                                                case 7:
                                                    mon = "Aug";
                                                    break;
                                                case 8:
                                                    mon = "Sept";
                                                    break;
                                                case 9:
                                                    mon = "Oct";
                                                    break;
                                                case 10:
                                                    mon = "Nov";
                                                    break;
                                                case 11:
                                                    mon = "Dec";
                                                    break;

                                            }
                                            String modDay, modMon;
                                            if (m + 1 < 10) {

                                                modMon = "0" + (m + 1);
                                            } else {
                                                modMon = String.valueOf(m + 1);
                                            }
                                            if (d < 10) {

                                                modDay = "0" + d;
                                            } else {
                                                modDay = String.valueOf(d);
                                            }

                                            end_from_dateServer2_adapter = y + "-" + modMon + "-" + modDay;
                                            str_to_date_work_ex = d + " " + mon + " " + y;
                                            tv_to_date.setText(str_to_date_work_ex);
                                            TilldateParamenter = "0";

//                                            String da = d + " " + mon + " " + y;
//                                            str_to_date_work_ex = (da);
//                                            tv_to_date.setText(str_to_date_work_ex);

                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle("ENTER ENDING DATE");
                                    mDatePicker.show();
                                }


                            });
                            final ScrollView scrollview = (ScrollView) dialog.findViewById(R.id.experiance_scroll_view);

                            eDescription.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if (charSequence.length() == 57) {
                                        scrollview.fullScroll(ScrollView.FOCUS_UP);
                                        eDescription1.requestFocus();

                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });

                            eDescription1.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if (charSequence.length() == 57) {
                                        scrollview.fullScroll(ScrollView.FOCUS_UP);
                                        eDescription2.requestFocus();

                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });

                            eDescription2.addTextChangedListener(new TextWatcher() {
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

                            eDescription.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    scrollview.fullScroll(ScrollView.FOCUS_UP);
                                }
                            });


                            dialog.show();

                            submit_details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    if (designation.getText().toString().length() > 0 && orgName.getText().toString().length() > 0
                                            && eDescription.getText().toString().length() > 0 && str_from_dateServer2_adapter.length()
                                            > 0 && end_from_dateServer2_adapter.length() > 0) {


                                        WorkExperienceData workExperienceData = myWorkExperienceList.get(position);
                                        workExperienceData.setDescription(eDescription.getText() + " " + eDescription1.getText() + " " + eDescription2.getText());
                                        workExperienceData.setDesignation(designation.getText().toString());
                                        workExperienceData.setOrganisationName(orgName.getText().toString());
                                        workExperienceData.setDateFrom(str_from_dateServer2_adapter);
                                        workExperienceData.setDateTo(end_from_dateServer2_adapter);
                                        workExperienceData.setTillDate(TilldateParamenter);
                                        MyWorkAdapter workAdapter = new MyWorkAdapter(myWorkExperienceList);
                                        workExperienceRecyclerView.setAdapter(workAdapter);
                                        workAdapter.notifyDataSetChanged();
                                        updateData(0);
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(getActivity(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });


                } else {
                    editData.setVisibility(View.GONE);
                }
            }

            void setItem(WorkExperienceData s) {
                TilldateParamenter = s.getTillDate();
                String s_title = s.getDesignation().toLowerCase();
                String s2_title = s.getOrganisationName().toLowerCase();
                String s3_title = s.getDescription().toLowerCase();


                String s3_datefrom = s.getDateFrom();
                String s3_dateto = s.getDateTo();
                String till_date = s.getTillDate();

                if (till_date.equals("1")) {
                    String date1 = dateconversion(s3_datefrom);
                    duractionExp.setText(date1 + " to " + s3_dateto);
                } else {
                    String date1 = dateconversion(s3_datefrom);
                    String date2 = dateconversion(s3_dateto);
                    duractionExp.setText(date1 + " to " + date2);
                }


                if (!s_title.equals("")) {
                    s_title = Character.toString(s_title.charAt(0)).toUpperCase() + s_title.substring(1);
                    desination.setText(s_title);
                } else {

                }

                if (!s2_title.equals("")) {
                    s2_title = Character.toString(s2_title.charAt(0)).toUpperCase() + s2_title.substring(1);
                    org_name.setText(s2_title);
                } else {

                }
                if (!s3_title.equals("")) {
                    s3_title = Character.toString(s3_title.charAt(0)).toUpperCase() + s3_title.substring(1);
                    description.setText(s3_title);
                } else {

                }

            }
        }
    }

    public class MyPlayerAdapter extends RecyclerView.Adapter<MyPlayerAdapter.ViewHolder> {

        private View rootview;
        private ArrayList<PlayerExperienceData> playerExperienceData;

        MyPlayerAdapter(ArrayList<PlayerExperienceData> playerExperienceData) {
            this.playerExperienceData = playerExperienceData;

        }

        @Override
        public MyPlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_list_item, parent, false);
            return new MyPlayerAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(MyPlayerAdapter.ViewHolder holder, int position) {
            holder.setItem(playerExperienceData.get(position));
//            holder.textview.setText("well");

        }

        @Override
        public int getItemCount() {
            return playerExperienceData.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView desination;
            TextView org_name;
            TextView description;
            TextView duractionExp;
            ImageView editData;

            public ViewHolder(final View itemView) {
                super(itemView);
                desination = (TextView) itemView.findViewById(R.id.designation);
                org_name = (TextView) itemView.findViewById(R.id.org_name);
                description = (TextView) itemView.findViewById(R.id.descriptionExp);
                duractionExp = (TextView) itemView.findViewById(R.id.durationExp);
                editData = (ImageView) itemView.findViewById(R.id.edit_data);


                if (user_id.equals(otherUserId)) {
                    editData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final int position = playerExperienceRecyclerView.getChildAdapterPosition(itemView);
//                            Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                            str_to_date_player = "";
                            str_from_date_player = "";
                            str_from_dateServer1_adapter = "";
                            end_from_dateServer1_adapter = "";
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                            dialog.setContentView(R.layout.coach_profile_playerexperience);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));
                            final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
                            submit_details.setText("Save experience");
                            final TextView title_text = (TextView) dialog.findViewById(R.id.toolbar_title);
//                            title_text.setText("Edit work experience");
                            Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
                            toolbar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });


                            final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designation);
                            final TextInputEditText eDescription = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience);
//                            final TextInputEditText eDescription1 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience1);
//                            final TextInputEditText eDescription2 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience2);
                            final TextInputEditText orgName = (TextInputEditText) dialog.findViewById(R.id.organizationName);
                            final TextInputEditText tv_from_date = (TextInputEditText) dialog.findViewById(R.id.tv_from_date);
                            final TextInputEditText tv_to_date = (TextInputEditText) dialog.findViewById(R.id.tv_to_date);
                            final TextView tv_till_date = (TextView) dialog.findViewById(R.id.tv_till_date);
                            final LinearLayout till_date = (LinearLayout) dialog.findViewById(R.id.till_date);
                            final ImageView image_view = (ImageView) dialog.findViewById(R.id.im_till_date);
                            final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
                            final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);


                            final TextView close = (TextView) dialog.findViewById(R.id.close);


                            final RelativeLayout Rl_till_date = (RelativeLayout) dialog.findViewById(R.id.relative_till_date);

                            String str_till_date = playerExperienceData.get(position).getTillDate();
                            if (str_till_date.equals("1")) {
                                Rl_till_date.setVisibility(View.GONE);
                                image_view.setImageResource(R.drawable.whiteback_with_corner);
                                TilldateParamenter = "1";
                                isOnePressed1 = true;
                                end_from_dateServer1_adapter = "Till Date";
                                str_from_dateServer1_adapter = playerExperienceData.get(position).getDateFrom();

                            } else {
                                Rl_till_date.setVisibility(View.VISIBLE);
                                image_view.setImageResource(R.drawable.whiteback_without_corner);
                                TilldateParamenter = "0";
                                isOnePressed1 = false;
                                end_from_dateServer1_adapter = playerExperienceData.get(position).getDateTo();
                                str_from_dateServer1_adapter = playerExperienceData.get(position).getDateFrom();
                            }


                            till_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (isOnePressed1) {
                                        Rl_till_date.setVisibility(View.VISIBLE);
                                        image_view.setImageResource(R.drawable.whiteback_without_corner);
                                        isOnePressed1 = false;
                                        to_date.setClickable(true);

                                    } else {
                                        Rl_till_date.setVisibility(View.GONE);
                                        image_view.setImageResource(R.drawable.whiteback_with_corner);

                                        to_date.setClickable(false);
                                        tv_to_date.setText("Till Date");
                                        end_from_dateServer1_adapter = "Till Date";
                                        isOnePressed1 = true;
                                    }

                                }
                            });

                            String total = description.getText().toString();

                            eDescription.setText(total);

//                            String substring = new String();
//                            if (total.length() > 0 && total.length() <= 30) {
//                                substring = total;
//                                eDescription.setText(substring.trim());
//                            } else if (total.length() > 30 && total.length() <= 60) {
//                                substring = total.substring(0, 30);
//                                String substring1 = total.substring(30, total.length());
//                                eDescription.setText(substring.trim());
//                                eDescription1.setText(substring1.trim());
//                            } else if (total.length() > 60 && total.length() <= 90) {
//                                substring = total;
//                                String substring1 = total.substring(30, 60);
//                                String substring2 = total.substring(60, total.length());
//                                eDescription.setText(substring.trim());
//                                eDescription1.setText(substring1.trim());
//                                eDescription2.setText(substring2.trim());
//                            }


                            designation.setText(desination.getText());
                            orgName.setText(org_name.getText());
                            // String[] split = duractionExp.getText().toString().split("-");

//                            if (!split[0].equals("FROM")) {
//                                tv_from_date.setText(split[0]);
//                                tv_to_date.setText(split[1]);
//                                str_to_date_player = "";//split[1];
//                                str_from_date_player = "";// split[0];
//                            } else {
//                                designation.setText("");
//                                orgName.setText("");
//                                eDescription.setText("");
//                                eDescription1.setText("");
//                                eDescription2.setText("");
//                                tv_from_date.setText("");
//                                tv_to_date.setText("");
//                            }
                            String dura = duractionExp.getText().toString();
                            String[] split = dura.split("to");
                            if (split.length >= 2) {
                                tv_from_date.setText(split[0]);
                                tv_to_date.setText(split[1]);
                                str_to_date_work_ex = split[1];
                                str_from_date_work_ex = split[0];
                            } else {
                                designation.setText("");
                                orgName.setText("");
                                eDescription.setText("");
//                                eDescription1.setText("");
//                                eDescription2.setText("");
                                tv_from_date.setText("");
                                tv_to_date.setText("");
                            }


                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();

                                }
                            });

                            tv_from_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                        public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                                            if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                                                Toast.makeText(getActivity(), "Wrong Starting Date", Toast.LENGTH_SHORT).show();
//                                                str_from_date_player = ("");
//                                                tv_from_date.setText(str_from_date_player);
//
//                                            } else
//                                            {
//                                            }
                                            String mon = "";
                                            switch (m) {
                                                case 0:
                                                    mon = "Jan";
                                                    break;
                                                case 1:
                                                    mon = "Feb";
                                                    break;
                                                case 2:
                                                    mon = "Mar";
                                                    break;
                                                case 3:
                                                    mon = "Apr";
                                                    break;
                                                case 4:
                                                    mon = "May";
                                                    break;
                                                case 5:
                                                    mon = "Jun";
                                                    break;
                                                case 6:
                                                    mon = "Jul";
                                                    break;
                                                case 7:
                                                    mon = "Aug";
                                                    break;
                                                case 8:
                                                    mon = "Sept";
                                                    break;
                                                case 9:
                                                    mon = "Oct";
                                                    break;
                                                case 10:
                                                    mon = "Nov";
                                                    break;
                                                case 11:
                                                    mon = "Dec";
                                                    break;

                                            }
                                            String modDay, modMon;
                                            if (m + 1 < 10) {

                                                modMon = "0" + (m + 1);
                                            } else {
                                                modMon = String.valueOf(m + 1);
                                            }
                                            if (d < 10) {

                                                modDay = "0" + d;
                                            } else {
                                                modDay = String.valueOf(d);
                                            }

                                            str_from_dateServer1_adapter = y + "-" + modMon + "-" + modDay;
                                            str_from_date_player = d + " " + mon + " " + y;
                                            tv_from_date.setText(str_from_date_player);

//                                            String da = d + " " + mon + " " + y;
//                                            str_from_date_player = (da);
//                                            tv_from_date.setText(str_from_date_player);

                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle("ENTER STARTING DATE");
                                    mDatePicker.show();
                                }


                            });

                            tv_to_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                        public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                                            if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                                                Toast.makeText(getActivity(), "Wrong ending Date", Toast.LENGTH_SHORT).show();
//                                                str_to_date_player = ("");
//                                                tv_to_date.setText(str_to_date_player);
//                                            } else {
//                                            }
                                            String mon = "";
                                            switch (m) {
                                                case 0:
                                                    mon = "Jan";
                                                    break;
                                                case 1:
                                                    mon = "Feb";
                                                    break;
                                                case 2:
                                                    mon = "Mar";
                                                    break;
                                                case 3:
                                                    mon = "Apr";
                                                    break;
                                                case 4:
                                                    mon = "May";
                                                    break;
                                                case 5:
                                                    mon = "Jun";
                                                    break;
                                                case 6:
                                                    mon = "Jul";
                                                    break;
                                                case 7:
                                                    mon = "Aug";
                                                    break;
                                                case 8:
                                                    mon = "Sept";
                                                    break;
                                                case 9:
                                                    mon = "Oct";
                                                    break;
                                                case 10:
                                                    mon = "Nov";
                                                    break;
                                                case 11:
                                                    mon = "Dec";
                                                    break;

                                            }
                                            String modDay, modMon;
                                            if (m + 1 < 10) {

                                                modMon = "0" + (m + 1);
                                            } else {
                                                modMon = String.valueOf(m + 1);
                                            }
                                            if (d < 10) {

                                                modDay = "0" + d;
                                            } else {
                                                modDay = String.valueOf(d);
                                            }

                                            end_from_dateServer1_adapter = y + "-" + modMon + "-" + modDay;
                                            str_to_date_player = d + " " + mon + " " + y;
                                            tv_to_date.setText(str_to_date_player);
                                            TilldateParamenter = "0";
//                                            String da = d + " " + mon + " " + y;
//                                            str_to_date_player = (da);
//                                            tv_to_date.setText(str_to_date_player);


                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle("ENTER ENDING DATE");
                                    mDatePicker.show();
                                }


                            });
                            dialog.show();

                            submit_details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    try {
                                        if (designation.getText().toString().length() > 0 && orgName.getText().toString().length() > 0 && eDescription.getText().toString().length() > 0
                                                && str_from_dateServer1_adapter.length() > 0 && end_from_dateServer1_adapter.length() > 0) {

                                            PlayerExperienceData playerExperienceData = myPlayerExperienceList.get(position);
                                            playerExperienceData.setDescription(eDescription.getText().toString());
                                            playerExperienceData.setDesignation(designation.getText().toString());
                                            playerExperienceData.setOrganisationName(orgName.getText().toString());
                                            playerExperienceData.setDateFrom(str_from_dateServer1_adapter);
                                            playerExperienceData.setDateTo(end_from_dateServer1_adapter);
                                            playerExperienceData.setTillDate(TilldateParamenter);

                                            MyPlayerAdapter playerAdapter = new MyPlayerAdapter(myPlayerExperienceList);
                                            playerExperienceRecyclerView.setAdapter(playerAdapter);
                                            playerAdapter.notifyDataSetChanged();
                                            updateData(0);
                                            dialog.dismiss();

                                        } else {
                                            Toast.makeText(getActivity(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Log.e("MYAPP", "exception", e);
                                    }

//

                                }
                            });


                        }

                    });

                } else {
                    editData.setVisibility(View.GONE);
                }
            }

            void setItem(PlayerExperienceData s) {

                TilldateParamenter = s.getTillDate();
                String s_title = s.getDesignation().toLowerCase();
                String s1_title = s.getOrganisationName().toLowerCase();
                String s2_title = s.getDescription().toLowerCase();

                String s3_datefrom = s.getDateFrom();
                String s3_dateto = s.getDateTo();

                String till_date = s.getTillDate();
                if (till_date.equals("1")) {
                    String date1 = dateconversion(s3_datefrom);
                    duractionExp.setText(date1 + " to " + s3_dateto);
                } else {
                    String date1 = dateconversion(s3_datefrom);
                    String date2 = dateconversion(s3_dateto);
                    duractionExp.setText(date1 + " to " + date2);
                }

                if (!s_title.equals("")) {
                    s_title = Character.toString(s_title.charAt(0)).toUpperCase() + s_title.substring(1);
                    desination.setText(s_title);
                } else {

                }
                if (!s1_title.equals("")) {
                    s1_title = Character.toString(s1_title.charAt(0)).toUpperCase() + s1_title.substring(1);
                    org_name.setText(s1_title);
                } else {

                }
                if (!s2_title.equals("")) {
                    s2_title = Character.toString(s2_title.charAt(0)).toUpperCase() + s2_title.substring(1);
                    description.setText(s2_title);
                } else {

                }

            }


        }
    }

}
