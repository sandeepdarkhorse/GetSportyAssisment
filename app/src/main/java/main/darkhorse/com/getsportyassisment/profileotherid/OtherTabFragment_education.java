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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

//import darkhorsesports.getsporty.R;
//import darkhorsesports.getsporty.athleteprofilemodelclassess.ApiAtheliteCall;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ApiData;
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
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ApiData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachHeader;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.GlobalUserProfileData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.TabFragment_education;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Nikhil on 17/9/16.
 */


public class OtherTabFragment_education extends Fragment implements View.OnClickListener, ApiDataOtherdata {

    private static String mdy;
    private static int year;
    private static int month;
    private static int day;

    private static GlobalUserProfileData globalUserProfileData = GlobalUserProfileData.getInstance();
    public ArrayList<HashMap<String, String>> list_data_sport = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> list_data_formal = new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> list_data_other = new ArrayList<HashMap<String, String>>();
    View rootView;
    String jsonData;
    ImageView edit_formal_education, edit_other, edit_sports_education_text;
    ImageView sport_edit, formal_edit, other_edit;
    HashMap<String, String> education_data = new HashMap<String, String>();
    String[] keys1 = {"degree",
            "stream",
            "organisation",
            "course_duration"};
    String str_from_date;

    String str_from_dateServer1;
    String end_from_dateServer1;
    String str_from_dateServer1_adapter;
    String end_from_dateServer1_adapter;

    String str_from_dateServer2;
    String end_from_dateServer2;
    String str_from_dateServer2_adapter;
    String end_from_dateServer2_adapter;

    String str_from_dateServer3;
    String end_from_dateServer3;
    String str_from_dateServer3_adapter;
    String end_from_dateServer3_adapter;

    String str_to_date;
    String TilldateParamenter;
    ApiData apiData;
    OtherProfileEducation coachEducation; //= new CoachEducation();
    OtherExperience coachExperience;// = new CoachExperience();
    CoachHeader coachHeader; // = new CoachHeader();
    boolean isChecked = false;
    Boolean isOnePressed1 = false;
    Boolean tilldate = false;
    CardView sport_education, formal_education, other_education;
    private ListView sportsEducation_list;
    private ListView formalEducation_list;
    private ListView other_certification;
    private String user_id;
    //    private RecyclerView sportsEducationRecyclerView;
    private TabFragment_education.MySportsAdapter mySportsAdapter;
    private ArrayList<SportEducationData> mySportsEducationList;
    private RecyclerView formalEducationRecyclerView;
    private ArrayList<FormalEducationData> myFormalEducationList;
    private MyFormalAdapter myFormalAdapter;
    private RecyclerView otherEducationRecyclerView;
    private MyOtherAdapter myOtherAdapter;
    private ArrayList<OtherCertificationData> myOtherEducationList;
    private String prof_id;
    private String otherUserId = "";
    private RecyclerView sportsEducationRecyclerView;

    public OtherTabFragment_education() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.tab_educationallprofile, container, false);
        myFormalEducationList = new ArrayList<FormalEducationData>();
        mySportsEducationList = new ArrayList<SportEducationData>();
        myOtherEducationList = new ArrayList<OtherCertificationData>();
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("Dashboard_prefs", 0);
        user_id = sharedPreferences1.getString("user_id", "");
        prof_id = sharedPreferences1.getString("prof_id", "");

        sportsEducationRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_sports_education);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sportsEducationRecyclerView.setLayoutManager(manager);


        formalEducationRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_formal_education);
        RecyclerView.LayoutManager managerformal = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        formalEducationRecyclerView.setLayoutManager(managerformal);

        otherEducationRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_other_education);
        RecyclerView.LayoutManager managerOther = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        otherEducationRecyclerView.setLayoutManager(managerOther);


        instantiation();
        click_listeners();
        return rootView;
    }

    private void instantiation() {

        edit_sports_education_text = (ImageView) rootView.findViewById(R.id.edit_sports_education);
        sport_edit = (ImageView) rootView.findViewById(R.id.sport_edit);

        edit_formal_education = (ImageView) rootView.findViewById(R.id.edit_formal_education);
        // formal_edit = (ImageView) rootView.findViewById(R.id.formal_edit);

        edit_other = (ImageView) rootView.findViewById(R.id.edit_other);
        // other_edit = (ImageView) rootView.findViewById(R.id.other_edit);
        formal_education = (CardView) rootView.findViewById(R.id.cardview_formal_education);

        other_education = (CardView) rootView.findViewById(R.id.cardview_other_education);
        sport_education = (CardView) rootView.findViewById(R.id.cardview_sport_education);
    }

    private void click_listeners() {

        edit_sports_education_text.setOnClickListener(this);


        edit_formal_education.setOnClickListener(this);


        edit_other.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edit_sports_education:
                education_dialog();
                break;

            case R.id.edit_formal_education:
                formal_dialog();
                break;


            case R.id.edit_other:
                other_dialog();
                break;
        }


    }


    private void updateData(int view) {
        switch (view) {
            case 1:
                sport_education.setVisibility(View.GONE);
                break;
            case 2:
                formal_education.setVisibility(View.GONE);
                break;
            case 3:
                other_education.setVisibility(View.GONE);
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


    @Override
    public void getEducationDataAll(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {
        otherUserId = userid;

        mySportsEducationList = sport;
        myFormalEducationList = formal;
        myOtherEducationList = other;

        if (mySportsEducationList.size() > 0) {
            sport_education.setVisibility(View.GONE);
            MySportsAdapter mySportsAdapter = new MySportsAdapter(mySportsEducationList);
            sportsEducationRecyclerView.setAdapter(mySportsAdapter);

        } else {
            sport_education.setVisibility(View.VISIBLE);
        }


        if (myFormalEducationList.size() > 0) {
            formal_education.setVisibility(View.GONE);
            myFormalAdapter = new MyFormalAdapter(myFormalEducationList);
            formalEducationRecyclerView.setAdapter(myFormalAdapter);

        } else {
            formal_education.setVisibility(View.VISIBLE);
        }

        if (myOtherEducationList.size() > 0) {
            other_education.setVisibility(View.GONE);
            myOtherAdapter = new MyOtherAdapter(myOtherEducationList);
            otherEducationRecyclerView.setAdapter(myOtherAdapter);
            otherEducationRecyclerView.getChildAdapterPosition(otherEducationRecyclerView.getFocusedChild());


        } else {
            other_education.setVisibility(View.VISIBLE);
        }


        if (!user_id.equals(otherUserId)) {
            edit_formal_education.setVisibility(View.GONE);
            edit_sports_education_text.setVisibility(View.GONE);
            edit_other.setVisibility(View.GONE);

        }
    }

    @Override
    public void getExperienceDataAll(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid) {

    }


    @Override
    public void getOthersDataAll(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {

    }


    public void setData(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other) {
        Log.d("SportEducation", sport.toString());
    }

    public void education_dialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.coach_addsport_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        str_from_date = "";
        str_to_date = "";
        str_from_dateServer1 = "";
        end_from_dateServer1 = "";
        TilldateParamenter = "0";
        final TextView title_text = (TextView) dialog.findViewById(R.id.title);

        final TextInputEditText degree = (TextInputEditText) dialog.findViewById(R.id.degree);

        final TextInputEditText org_name = (TextInputEditText) dialog.findViewById(R.id.org_name);
        final TextInputEditText stream = (TextInputEditText) dialog.findViewById(R.id.stream);

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
//        tilldate

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

                        str_from_dateServer1 = y + "-" + modMon + "-" + modDay;
                        str_from_date = d + " " + mon + " " + y;
                        tv_from_date.setText(str_from_date);


//                        String da = d + " " + mon + " " + y;
//                        str_from_date = (da);
//                        tv_from_date.setText(str_from_date);
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
//                            str_to_date = ("");
//                            tv_to_date.setText(str_to_date);
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
                        str_to_date = d + " " + mon + " " + y;
                        tv_to_date.setText(str_to_date);
                        TilldateParamenter = "0";
//                        String da = d + " " + mon + " " + y;
//                        str_to_date = (da);
//                        tv_to_date.setText(str_to_date);


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

                    if (degree.getText().toString().length() > 0 && stream.getText().toString().length() > 0 && org_name.getText().toString().length() > 0 && str_from_dateServer1.length() > 0 && end_from_dateServer1.length() > 0) {
                        SportEducationData sportEducationData = new SportEducationData(degree.getText().toString(), stream.getText().toString(), org_name.getText().toString(), str_from_dateServer1, end_from_dateServer1, TilldateParamenter);
                        mySportsEducationList.add(sportEducationData);
                        MySportsAdapter adapter = new MySportsAdapter(mySportsEducationList);
                        sportsEducationRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        updateData(1);
//                globalUserProfileData.sportEducationGlobal.add(sportEducationData);

                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("MYAPP", "exception", e);
                }


            }
        });


    }

    public void formal_dialog() {
        TilldateParamenter = "0";
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.coach_addformaleducation_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));

        final TextView title_text = (TextView) dialog.findViewById(R.id.title);

        final TextInputEditText degree = (TextInputEditText) dialog.findViewById(R.id.degree);

        final TextInputEditText org_name = (TextInputEditText) dialog.findViewById(R.id.org_name);
        final TextInputEditText stream = (TextInputEditText) dialog.findViewById(R.id.stream);

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

                    end_from_dateServer2 = "Till Date";
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
                        str_from_date = d + " " + mon + " " + y;
                        tv_from_date.setText(str_from_date);


//                        String da = d + " " + mon + " " + y;
//                        str_from_date = (da);
//                        tv_from_date.setText(str_from_date);

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
                        str_from_date = d + " " + mon + " " + y;
                        tv_to_date.setText(str_from_date);


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
                    if (degree.getText().toString().length() > 0 && stream.getText().toString().length() > 0 && org_name.getText().toString().length() > 0 && str_from_dateServer2.length() > 0 && end_from_dateServer2.length() > 0) {


                        FormalEducationData formalEducationData = new FormalEducationData(degree.getText().toString(), stream.getText().toString(), org_name.getText().toString(), str_from_dateServer2, end_from_dateServer2, TilldateParamenter);
//                globalUserProfileData.formalEducationGlobal.add(formalEducationData);
                        myFormalEducationList.add(formalEducationData);
                        MyFormalAdapter adapter = new MyFormalAdapter(myFormalEducationList);
                        formalEducationRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        updateData(2);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("MYAPP", "exception", e);
                }


            }
        });


    }

    public void other_dialog() {
        str_from_date = "";
        str_to_date = "";
        str_from_dateServer3 = "";
        end_from_dateServer3 = "";
        TilldateParamenter = "0";
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.coach_addcertificate_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));

        final TextView title_text = (TextView) dialog.findViewById(R.id.title);

        final TextInputEditText degree = (TextInputEditText) dialog.findViewById(R.id.degree);
        final TextInputEditText org_name = (TextInputEditText) dialog.findViewById(R.id.org_name);
        final TextInputEditText stream = (TextInputEditText) dialog.findViewById(R.id.stream);
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
                    end_from_dateServer3 = "Till Date";
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


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                            Toast.makeText(getActivity(), R.string.wrong_start_date, Toast.LENGTH_SHORT).show();
//                            str_from_date = ("");
//                            tv_from_date.setText(str_from_date);
//
//                        } else {
//
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

                        str_from_dateServer3 = y + "-" + modMon + "-" + modDay;
                        str_from_date = d + " " + mon + " " + y;
                        tv_from_date.setText(str_from_date);


                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle(R.string.enter_start_date);
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
//                            Toast.makeText(getActivity(), R.string.wrong_end_date, Toast.LENGTH_SHORT).show();
//                            str_to_date = ("");
//                            tv_to_date.setText(str_to_date);
//                        } else {
//
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

                        end_from_dateServer3 = y + "-" + modMon + "-" + modDay;
                        str_to_date = d + " " + mon + " " + y;
                        tv_to_date.setText(str_to_date);


                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle(R.string.enter_ending_date);
                mDatePicker.show();
            }


        });
        dialog.show();

        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (degree.getText().toString().length() > 0 && stream.getText().toString().length() > 0 && org_name.getText().toString().length() > 0 && str_from_dateServer3.length() > 0 && end_from_dateServer3.length() > 0) {

                    OtherCertificationData otherCertificationData = new OtherCertificationData(degree.getText().toString(), stream.getText().toString(), org_name.getText().toString(), str_from_dateServer3, end_from_dateServer3, TilldateParamenter);
//                globalUserProfileData.otherCertificationGlobal.add(otherCertificationData);
                    myOtherEducationList.add(otherCertificationData);
                    MyOtherAdapter adapter = new MyOtherAdapter(myOtherEducationList);
                    otherEducationRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    updateData(3);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), R.string.fill_all_detail, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public class MySportsAdapter extends RecyclerView.Adapter<MySportsAdapter.ViewHolder> {

        private View rootview;
        private ArrayList<SportEducationData> sportsEducationData;

        MySportsAdapter(ArrayList<SportEducationData> sportsEducationData) {
            this.sportsEducationData = new ArrayList<SportEducationData>();
            this.sportsEducationData = sportsEducationData;

        }

        @Override
        public MySportsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_list_item, parent, false);
            return new MySportsAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(MySportsAdapter.ViewHolder holder, int position) {
            holder.setItem(sportsEducationData.get(position));
//            holder.textview.setText("well");

        }

        @Override
        public int getItemCount() {
            return sportsEducationData.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image_view;
            RelativeLayout Rl_till_date;
            private TextView degree;
            private TextView duration;
            private TextView orgName;
            private TextView stream;
            private ImageView editData;

            public ViewHolder(final View itemView) {
                super(itemView);
                degree = (TextView) itemView.findViewById(R.id.degree);
                duration = (TextView) itemView.findViewById(R.id.degree_duraction);
                orgName = (TextView) itemView.findViewById(R.id.org_name);
                stream = (TextView) itemView.findViewById(R.id.stream);
                editData = (ImageView) itemView.findViewById(R.id.edit_data);


                if (user_id.equals(otherUserId)) {
                    editData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final int position = sportsEducationRecyclerView.getChildAdapterPosition(itemView);


//                            Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                            str_from_date = "";
                            str_to_date = "";
                            str_from_dateServer1_adapter = "";
                            end_from_dateServer1_adapter = "";
//                            TilldateParamenter = "0";
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                            dialog.setContentView(R.layout.coach_addsport_dialog);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            final TextView title_text = (TextView) dialog.findViewById(R.id.title);


                            final TextInputEditText dDegree = (TextInputEditText) dialog.findViewById(R.id.degree);
                            final TextInputEditText dOrgName = (TextInputEditText) dialog.findViewById(R.id.org_name);
                            final TextInputEditText dStream = (TextInputEditText) dialog.findViewById(R.id.stream);
                            final TextInputEditText tv_from_date = (TextInputEditText) dialog.findViewById(R.id.tv_from_date);
                            final TextInputEditText tv_to_date = (TextInputEditText) dialog.findViewById(R.id.tv_to_date);
                            final TextView tv_till_date = (TextView) dialog.findViewById(R.id.tv_till_date);
                            final LinearLayout till_date = (LinearLayout) dialog.findViewById(R.id.till_date);
                            image_view = (ImageView) dialog.findViewById(R.id.im_till_date);
                            Rl_till_date = (RelativeLayout) dialog.findViewById(R.id.relative_till_date);

                            final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
                            final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
                            final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);
                            final TextView close = (TextView) dialog.findViewById(R.id.close);
                            Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
                            toolbar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            String str_till_date = sportsEducationData.get(position).getTillDate();
                            if (str_till_date.equals("1")) {
                                Rl_till_date.setVisibility(View.GONE);
                                image_view.setImageResource(R.drawable.whiteback_with_corner);
                                TilldateParamenter = "1";
                                isOnePressed1 = true;
                                end_from_dateServer1_adapter = "Till Date";
                                str_from_dateServer1_adapter = sportsEducationData.get(position).getDateFrom();

                            } else {
                                Rl_till_date.setVisibility(View.VISIBLE);
                                image_view.setImageResource(R.drawable.whiteback_without_corner);
                                TilldateParamenter = "0";
                                isOnePressed1 = false;

                                end_from_dateServer1_adapter = sportsEducationData.get(position).getDateTo();
                                str_from_dateServer1_adapter = sportsEducationData.get(position).getDateFrom();
                            }


                            till_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (isOnePressed1) {
                                        Rl_till_date.setVisibility(View.VISIBLE);
                                        image_view.setImageResource(R.drawable.whiteback_without_corner);
                                        TilldateParamenter = "0";
                                        isOnePressed1 = false;
//
                                    } else {
                                        Rl_till_date.setVisibility(View.GONE);
                                        image_view.setImageResource(R.drawable.whiteback_with_corner);
//                                        to_date.setClickable(false);
                                        TilldateParamenter = "1";
                                        isOnePressed1 = true;
                                        end_from_dateServer1_adapter = "Till Date";
                                    }

                                }
                            });

                            dDegree.setText(degree.getText());
                            dOrgName.setText(orgName.getText());
                            dStream.setText(stream.getText());

                            String dura = duration.getText().toString();
                            String[] split = dura.split("to");
                            if (split.length >= 2) {
                                tv_from_date.setText(split[0]);
                                tv_to_date.setText(split[1]);
                            }
                            if (split.length >= 2) {
                                str_from_date = split[0];
                                str_to_date = split[1];
                            } else {
                                dDegree.setText("");
                                dOrgName.setText("");
                                dStream.setText("");
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
//                                                Toast.makeText(getActivity(), R.string.wrong_start_date, Toast.LENGTH_SHORT).show();
//                                                str_from_date = ("");
//                                                tv_from_date.setText(str_from_date);
//
//                                            } else
//                                            {
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

                                            str_from_dateServer1_adapter = y + "-" + modMon + "-" + modDay;
                                            str_from_date = d + " " + mon + " " + y;
                                            tv_from_date.setText(str_from_date);

                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(R.string.enter_start_date);
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
//                                                Toast.makeText(getActivity(), R.string.wrong_end_date, Toast.LENGTH_SHORT).show();
//                                                str_to_date = ("");
//                                                tv_to_date.setText(str_to_date);
//                                            } else
//                                            {
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

                                            end_from_dateServer1_adapter = y + "-" + modMon + "-" + modDay;
                                            str_to_date = d + " " + mon + " " + y;
                                            tv_to_date.setText(str_to_date);
                                            TilldateParamenter = "0";

//                                            String da = d + " " + mon + " " + y;
//                                            str_to_date = (da);
//                                            tv_to_date.setText(str_to_date);


                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(R.string.enter_ending_date);
                                    mDatePicker.show();
                                }


                            });
                            dialog.show();

                            submit_details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    try {


                                        if (dDegree.getText().toString().length() > 0 && dStream.getText().toString().length() > 0 && dOrgName.getText().toString().length() > 0 && str_from_dateServer1_adapter.length() > 0 && end_from_dateServer1_adapter.length() > 0) {

                                            SportEducationData sportEducationData = mySportsEducationList.get(position);
                                            sportEducationData.setDegree(dDegree.getText().toString());
                                            sportEducationData.setDateFrom(str_from_dateServer1_adapter);
                                            sportEducationData.setDateTo(end_from_dateServer1_adapter);
                                            sportEducationData.setTillDate(TilldateParamenter);

//                                        sportEducationData.setCourseDuration(str_from_dateServer1+" " + "to" + " "+end_from_dateServer1);
                                            sportEducationData.setOrganisation(dOrgName.getText().toString());
                                            sportEducationData.setStream(dStream.getText().toString());
                                            MySportsAdapter adapter = new MySportsAdapter(mySportsEducationList);
                                            sportsEducationRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                            updateData(0);
                                            dialog.dismiss();

                                        } else {
                                            Toast.makeText(getActivity(), R.string.fill_all_detail, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Log.e("MYAPP", "exception", e);
                                    }


                                }
                            });


                        }

                    });
                } else {
                    editData.setVisibility(View.GONE);
                }
            }

            void setItem(SportEducationData s) {
                TilldateParamenter = s.getTillDate();
                String s_title = s.getDegree().toLowerCase();
                String s1_title = s.getDateFrom().toLowerCase();
                String s4_title = s.getDateTo().toLowerCase();
                String s2_title = s.getOrganisation().toLowerCase();
                String s3_title = s.getStream().toLowerCase();

                String till_date = s.getTillDate();
                if (till_date.equals("1")) {
                    String date1 = DateConversion.dateconversionMonth(s1_title);
                    duration.setText(date1 + " to " + s4_title);


                } else {
                    String date1 = DateConversion.dateconversionMonth(s1_title);
                    String date2 = DateConversion.dateconversionMonth(s4_title);
                    duration.setText(date1 + " to " + date2);


                }


                if (!s_title.equals("")) {
                    s_title = Character.toString(s_title.charAt(0)).toUpperCase() + s_title.substring(1);
                    degree.setText(s_title);
                } else {

                }

                if (!s2_title.equals("")) {
                    s2_title = Character.toString(s2_title.charAt(0)).toUpperCase() + s2_title.substring(1);
                    orgName.setText(s2_title);
                } else {

                }
                if (!s3_title.equals("")) {
                    s3_title = Character.toString(s3_title.charAt(0)).toUpperCase() + s3_title.substring(1);
                    stream.setText(s3_title);
                } else {

                }

            }
        }
    }


    public class MyFormalAdapter extends RecyclerView.Adapter<MyFormalAdapter.ViewHolder> {

        private View rootview;
        private ArrayList<FormalEducationData> formalEducationData;

        MyFormalAdapter(ArrayList<FormalEducationData> formalEducationData) {
            this.formalEducationData = formalEducationData;

        }

        @Override
        public MyFormalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_list_item, parent, false);
            return new MyFormalAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(MyFormalAdapter.ViewHolder holder, int position) {
            holder.setItem(formalEducationData.get(position));
//            holder.textview.setText("well");

        }

        @Override
        public int getItemCount() {
            return formalEducationData.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView degree;
            private TextView duration;
            private TextView orgName;
            private TextView stream;
            private ImageView editData;


            public ViewHolder(final View itemView) {
                super(itemView);
                degree = (TextView) itemView.findViewById(R.id.degree);
                duration = (TextView) itemView.findViewById(R.id.degree_duraction);
                orgName = (TextView) itemView.findViewById(R.id.org_name);
                stream = (TextView) itemView.findViewById(R.id.stream);
                editData = (ImageView) itemView.findViewById(R.id.edit_data);


                if (user_id.equals(otherUserId)) {
                    editData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final int position = formalEducationRecyclerView.getChildAdapterPosition(itemView);
//                            Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                            str_from_date = "";
                            str_to_date = "";
                            str_from_dateServer2_adapter = "";
                            end_from_dateServer2_adapter = "";
//                            TilldateParamenter = "0";
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                            dialog.setContentView(R.layout.coach_addformaleducation_dialog);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));

                            final TextView title_text = (TextView) dialog.findViewById(R.id.title);

                            final TextInputEditText dDegree = (TextInputEditText) dialog.findViewById(R.id.degree);
                            final TextInputEditText dOrgName = (TextInputEditText) dialog.findViewById(R.id.org_name);
                            final TextInputEditText dStream = (TextInputEditText) dialog.findViewById(R.id.stream);
                            final TextView tv_from_date = (TextInputEditText) dialog.findViewById(R.id.tv_from_date);
                            final TextView tv_to_date = (TextInputEditText) dialog.findViewById(R.id.tv_to_date);

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
                            String str_till_date = formalEducationData.get(position).getTillDate();
                            if (str_till_date.equals("1")) {
                                Rl_till_date.setVisibility(View.GONE);
                                image_view.setImageResource(R.drawable.whiteback_with_corner);
                                TilldateParamenter = "1";
                                isOnePressed1 = true;
                                end_from_dateServer2_adapter = "Till Date";
                                str_from_dateServer2_adapter = formalEducationData.get(position).getDateFrom();

                            } else {
                                Rl_till_date.setVisibility(View.VISIBLE);
                                image_view.setImageResource(R.drawable.whiteback_without_corner);
                                TilldateParamenter = "0";
                                isOnePressed1 = false;

                                end_from_dateServer2_adapter = formalEducationData.get(position).getDateTo();
                                str_from_dateServer2_adapter = formalEducationData.get(position).getDateFrom();
                            }

                            till_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    if (isOnePressed1) {
                                        Rl_till_date.setVisibility(View.VISIBLE);
                                        image_view.setImageResource(R.drawable.whiteback_without_corner);
                                        isOnePressed1 = false;
                                        tv_to_date.setClickable(true);
                                        TilldateParamenter = "0";
                                    } else {
                                        Rl_till_date.setVisibility(View.GONE);
                                        image_view.setImageResource(R.drawable.whiteback_with_corner);
                                        tv_to_date.setClickable(false);
                                        end_from_dateServer2_adapter = "Till Date";
                                        isOnePressed1 = true;
                                        tv_to_date.setText("Till Date");
                                        TilldateParamenter = "1";
                                    }


                                }
                            });


                            final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
                            final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
                            final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);
                            final TextView close = (TextView) dialog.findViewById(R.id.close);

                            String dura = duration.getText().toString();
                            String[] split = dura.split("to");
                            if (split.length >= 2) {
                                tv_from_date.setText(split[0]);
                                tv_to_date.setText(split[1]);
                            }

                            dDegree.setText(degree.getText());
                            dOrgName.setText(orgName.getText());
                            dStream.setText(stream.getText());
                            if (split.length >= 2) {
                                str_from_date = split[0];
                                str_to_date = split[1];
                            } else {
                                dDegree.setText("");
                                dOrgName.setText("");
                                dStream.setText("");
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
//                                                Toast.makeText(getActivity(), R.string.wrong_start_date, Toast.LENGTH_SHORT).show();
//                                                str_from_date = ("");
//                                                tv_from_date.setText(str_from_date);
//
//                                            } else
//                                            {
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
                                            str_from_date = d + " " + mon + " " + y;
                                            tv_from_date.setText(str_from_date);


                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(R.string.enter_start_date);
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
//                                                Toast.makeText(getActivity(), R.string.wrong_end_date, Toast.LENGTH_SHORT).show();
//                                                str_to_date = ("");
//                                                tv_to_date.setText(str_to_date);
//                                            } else
//                                            {
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
                                            str_to_date = d + " " + mon + " " + y;
                                            tv_to_date.setText(str_to_date);
                                            TilldateParamenter = "0";
//                                            String da = d + " " + mon + " " + y;
//                                            str_to_date = (da);
//                                            tv_to_date.setText(str_to_date);


                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(R.string.enter_ending_date);
                                    mDatePicker.show();
                                }


                            });
                            dialog.show();

                            submit_details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    try {
                                        if (dDegree.getText().toString().length() > 0 && dStream.getText().toString().length() > 0 && dOrgName.getText().toString().length()
                                                > 0 && str_from_dateServer2_adapter.length() > 0 && end_from_dateServer2_adapter.length() > 0) {


                                            FormalEducationData formalEducationData = myFormalEducationList.get(position);
                                            formalEducationData.setDegree(dDegree.getText().toString());
                                            formalEducationData.setDateFrom(str_from_dateServer2_adapter);
                                            formalEducationData.setDateTo(end_from_dateServer2_adapter);
//                                        formalEducationData.setCourseDuration(str_from_dateServer2 + " " + "to" + " " + end_from_dateServer2);
                                            formalEducationData.setOrganisation(dOrgName.getText().toString());
                                            formalEducationData.setStream(dStream.getText().toString());
                                            formalEducationData.setTillDate(TilldateParamenter);


                                            MyFormalAdapter adapter = new MyFormalAdapter(myFormalEducationList);
                                            formalEducationRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                            updateData(0);
                                            dialog.dismiss();

                                        } else {
                                            Toast.makeText(getActivity(), R.string.fill_all_detail, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Log.e("MYAPP", "exception", e);
                                    }


                                }
                            });


                        }
                    });

                } else {
                    editData.setVisibility(View.GONE);
                }
            }

            void setItem(FormalEducationData s) {
//                TilldateParamenter = s.getTillDate();
                String s_title = s.getDegree().toLowerCase();
                String s1_title = s.getDateFrom().toLowerCase();
                String s4_title = s.getDateTo().toLowerCase();
//                String s1_title = s.getCourseDuration().toLowerCase();
                String s2_title = s.getOrganisation().toLowerCase();
                String s3_title = s.getStream().toLowerCase();

                String till_date = s.getTillDate();
                if (till_date.equals("1")) {
                    String date1 = DateConversion.dateconversionMonth(s1_title);
                    duration.setText(date1 + " to " + s4_title);
                } else {
                    String date1 = DateConversion.dateconversionMonth(s1_title);
                    String date2 = DateConversion.dateconversionMonth(s4_title);
                    duration.setText(date1 + " to " + date2);
                }


                if (!s_title.equals("")) {
                    s_title = Character.toString(s_title.charAt(0)).toUpperCase() + s_title.substring(1);
                    degree.setText(s_title);
                } else {

                }


                if (!s2_title.equals("")) {
                    s2_title = Character.toString(s2_title.charAt(0)).toUpperCase() + s2_title.substring(1);
                    orgName.setText(s2_title);
                } else {

                }
                if (!s3_title.equals("")) {
                    s3_title = Character.toString(s3_title.charAt(0)).toUpperCase() + s3_title.substring(1);
                    stream.setText(s3_title);
                } else {

                }


            }
        }
    }

    public class MyOtherAdapter extends RecyclerView.Adapter<MyOtherAdapter.ViewHolder> {

        private View rootview;
        private ArrayList<OtherCertificationData> otherCertificationData;

        MyOtherAdapter(ArrayList<OtherCertificationData> otherCertificationData) {
            this.otherCertificationData = otherCertificationData;

        }

        @Override
        public MyOtherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_list_item, parent, false);
            return new MyOtherAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(MyOtherAdapter.ViewHolder holder, int position) {
            holder.setItem(otherCertificationData.get(position));
//            holder.textview.setText("well");

        }

        @Override
        public int getItemCount() {
            return otherCertificationData.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView degree;
            private TextView duration;
            private TextView orgName;
            private TextView stream;
            private ImageView editData;

            public ViewHolder(final View itemView) {

                super(itemView);


                degree = (TextView) itemView.findViewById(R.id.degree);
                duration = (TextView) itemView.findViewById(R.id.degree_duraction);
                orgName = (TextView) itemView.findViewById(R.id.org_name);
                stream = (TextView) itemView.findViewById(R.id.stream);
                editData = (ImageView) itemView.findViewById(R.id.edit_data);


                if (user_id.equals(otherUserId)) {
                    editData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final int position = otherEducationRecyclerView.getChildAdapterPosition(itemView);
//                            Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                            str_from_date = "";
                            str_to_date = "";

                            str_from_dateServer3_adapter = "";
                            end_from_dateServer3_adapter = "";
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                            dialog.setContentView(R.layout.coach_addcertificate_dialog);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));
                            final TextView title_text = (TextView) dialog.findViewById(R.id.title);
                            final TextInputEditText dDegree = (TextInputEditText) dialog.findViewById(R.id.degree);
                            final TextInputEditText dOrgName = (TextInputEditText) dialog.findViewById(R.id.org_name);
                            final TextInputEditText dStream = (TextInputEditText) dialog.findViewById(R.id.stream);
                            final TextInputEditText tv_from_date = (TextInputEditText) dialog.findViewById(R.id.tv_from_date);
                            final TextInputEditText tv_to_date = (TextInputEditText) dialog.findViewById(R.id.tv_to_date);
                            final TextView tv_till_date = (TextView) dialog.findViewById(R.id.tv_till_date);
                            final LinearLayout till_date = (LinearLayout) dialog.findViewById(R.id.till_date);
                            final ImageView image_view = (ImageView) dialog.findViewById(R.id.im_till_date);
                            final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
                            final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
                            final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);
                            final TextView close = (TextView) dialog.findViewById(R.id.close);

                            final RelativeLayout Rl_till_date = (RelativeLayout) dialog.findViewById(R.id.relative_till_date);

                            Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
                            toolbar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            String str_till_date = otherCertificationData.get(position).getTillDate();
                            if (str_till_date.equals("1")) {
                                Rl_till_date.setVisibility(View.GONE);
                                image_view.setImageResource(R.drawable.whiteback_with_corner);
                                TilldateParamenter = "1";
                                isOnePressed1 = true;
                                end_from_dateServer3_adapter = "Till Date";
                                str_from_dateServer3_adapter = otherCertificationData.get(position).getDateFrom();

                            } else {
                                Rl_till_date.setVisibility(View.VISIBLE);
                                image_view.setImageResource(R.drawable.whiteback_without_corner);
                                TilldateParamenter = "0";
                                isOnePressed1 = false;
                                end_from_dateServer3_adapter = otherCertificationData.get(position).getDateTo();
                                str_from_dateServer3_adapter = otherCertificationData.get(position).getDateFrom();
                            }


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
                                        end_from_dateServer3_adapter = "Till Date";
                                        tv_to_date.setText("Till Date");
                                        isOnePressed1 = true;
                                        TilldateParamenter = "1";
                                    }

                                }
                            });


                            String dura = duration.getText().toString();
                            String[] split = dura.split("to");
                            if (split.length >= 2) {
                                tv_from_date.setText(split[0]);
                                tv_to_date.setText(split[1]);
                            }

                            dDegree.setText(degree.getText());
                            dOrgName.setText(orgName.getText());
                            dStream.setText(stream.getText());
                            if (split.length >= 2) {
                                str_from_date = split[0];
                                str_to_date = split[1];
                            } else {
                                dDegree.setText("");
                                dOrgName.setText("");
                                dStream.setText("");
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
//                                                Toast.makeText(getActivity(), R.string.wrong_start_date, Toast.LENGTH_SHORT).show();
//                                                str_from_date = ("");
//                                                tv_from_date.setText(str_from_date);
//
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

                                            str_from_dateServer3_adapter = y + "-" + modMon + "-" + modDay;
                                            str_from_date = d + " " + mon + " " + y;
                                            tv_from_date.setText(str_from_date);

//                                            String da = d + " " + mon + " " + y;
//                                            str_from_date = (da);
//                                            tv_from_date.setText(str_from_date);
                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(R.string.enter_start_date);
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
//                                                Toast.makeText(getActivity(), R.string.wrong_end_date, Toast.LENGTH_SHORT).show();
//                                                str_to_date = ("");
//                                                tv_to_date.setText(str_to_date);
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

                                            end_from_dateServer3_adapter = y + "-" + modMon + "-" + modDay;
                                            str_to_date = d + " " + mon + " " + y;
                                            tv_to_date.setText(str_to_date);
                                            TilldateParamenter = "0";
//                                            String da = d + " " + mon + " " + y;
//                                            str_to_date = (da);
//                                            tv_to_date.setText(str_to_date);
                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(R.string.enter_ending_date);
                                    mDatePicker.show();
                                }


                            });
                            dialog.show();

                            submit_details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (dDegree.getText().toString().length() > 0 && dStream.getText().toString().length() > 0 && dOrgName.getText().toString().length()
                                            > 0 && str_from_dateServer3_adapter.length() > 0 && end_from_dateServer3_adapter.length() > 0) {

                                        OtherCertificationData otherCertificationData = myOtherEducationList.get(position);
                                        otherCertificationData.setDegree(dDegree.getText().toString());
                                        otherCertificationData.setDateFrom(str_from_dateServer3_adapter);
                                        otherCertificationData.setDateTo(end_from_dateServer3_adapter);
                                        otherCertificationData.setTillDate(TilldateParamenter);
//                                        otherCertificationData.setCourseDuration(str_from_dateServer3 + " " + "to" + " " + end_from_dateServer3);
                                        otherCertificationData.setOrganisation(dOrgName.getText().toString());
                                        otherCertificationData.setStream(dStream.getText().toString());
                                        MyOtherAdapter adapter = new MyOtherAdapter(myOtherEducationList);
                                        otherEducationRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        updateData(0);
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(getActivity(), R.string.fill_all_detail, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    });


                } else {
                    editData.setVisibility(View.GONE);
                }
            }

            void setItem(OtherCertificationData s) {
                TilldateParamenter = s.getTillDate();
                String s_title = s.getDegree().toLowerCase();
                String s1_title = s.getDateFrom().toLowerCase();
                String s4_title = s.getDateTo().toLowerCase();
                // String s1_title = s.getCourseDuration().toLowerCase();
                String s2_title = s.getOrganisation().toLowerCase();
                String s3_title = s.getStream().toLowerCase();

                String till_date = s.getTillDate();
                if (till_date.equals("1")) {
                    String date1 = DateConversion.dateconversionMonth(s1_title);
                    duration.setText(date1 + " to " + s4_title);
                } else {
                    String date1 = DateConversion.dateconversionMonth(s1_title);
                    String date2 = DateConversion.dateconversionMonth(s4_title);
                    duration.setText(date1 + " to " + date2);
                }


                if (!s_title.equals(""))

                {
                    s_title = Character.toString(s_title.charAt(0)).toUpperCase() + s_title.substring(1);
                    degree.setText(s_title);
                } else {

                }


                if (!s2_title.equals("")) {
                    s2_title = Character.toString(s2_title.charAt(0)).toUpperCase() + s2_title.substring(1);
                    orgName.setText(s2_title);
                } else {

                }
                if (!s3_title.equals("")) {
                    s3_title = Character.toString(s3_title.charAt(0)).toUpperCase() + s3_title.substring(1);
                    stream.setText(s3_title);
                } else {

                }

            }


            @Override
            public void onClick(View view) {

            }
        }
    }


}