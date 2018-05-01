package main.darkhorse.com.getsportyassisment.otheruserprofileview;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

//import darkhorsesports.getsporty.R;
//import darkhorsesports.getsporty.athleteprofilemodelclassess.ApiAtheliteCall;
//import darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachEducation;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachExperience;
//import darkhorsesports.getsporty.coachprofilemodelclassess.CoachHeader;
//import darkhorsesports.getsporty.coachprofilemodelclassess.FormalEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.OtherCertificationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.PlayerExperienceData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.SportEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.UserProfileCoachEditRequest;
//import darkhorsesports.getsporty.coachprofilemodelclassess.UserProfileCoachEditResponse;
//import darkhorsesports.getsporty.coachprofilemodelclassess.WorkExperienceData;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachEducation;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachExperience;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.CoachHeader;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.GlobalUserProfileData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditRequest;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabExperienceOthersProfile extends Fragment implements OtherProfileInterface , View.OnClickListener{


    private String otherUserId = "";
    private View view;
    private String user_id;
    private String prof_id;
    private ArrayList<WorkExperienceData> myWorkExperienceList;
    private ArrayList<PlayerExperienceData> myPlayerExperienceList;
    private TextView addWorkExperience;
    private RecyclerView workExperienceRecyclerView;
    private String str_to_date_work_ex;
    private String str_from_date_work_ex;
    private MyWorkAdapter myWorkAdapter;
    private CoachEducation coachEducation;
    private GlobalUserProfileData globalUserProfileData = GlobalUserProfileData.getInstance();
    private CoachExperience coachExperience;
    private CoachHeader coachHeader;
   // private TextView addPlayerExperience;
    private RecyclerView playerExperienceRecyclerView;
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
    public TabExperienceOthersProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_experience_others_profile, container, false);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("Dashboard_prefs", 0);
        user_id = sharedPreferences1.getString("user_id", "");
        prof_id = sharedPreferences1.getString("prof_id","");

        myWorkExperienceList = new ArrayList<WorkExperienceData>();
        myPlayerExperienceList = new ArrayList<PlayerExperienceData>();

        addWorkExperience = (TextView) view.findViewById(R.id.addWorkExperience);
        //addPlayerExperience = (TextView) view.findViewById(R.id.addPlayerExperience);

        workExperienceRecyclerView = (RecyclerView) view.findViewById(R.id.work_experience);
        RecyclerView.LayoutManager layoutManagerWork = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        workExperienceRecyclerView.setLayoutManager(layoutManagerWork);


        playerExperienceRecyclerView = (RecyclerView) view.findViewById(R.id.player_experience);
        RecyclerView.LayoutManager layoutManagerPlayer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        playerExperienceRecyclerView.setLayoutManager(layoutManagerPlayer);

        addWorkExperience.setOnClickListener(this);
       // addPlayerExperience.setOnClickListener(this);




        return view;
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


        final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designation);
        final TextInputEditText description1 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience1);
        final TextInputEditText description2 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience2);
        final TextInputEditText description = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience);
        final TextInputEditText orgName = (TextInputEditText) dialog.findViewById(R.id.organizationName);
        final TextView tv_from_date = (TextView) dialog.findViewById(R.id.tv_from_date);
        final TextView tv_to_date = (TextView) dialog.findViewById(R.id.tv_to_date);
        final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
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
                    image_view.setImageResource(R.drawable.whiteback_with_corner);
                    Rl_till_date.setVisibility(View.GONE);
                    to_date.setClickable(false);
                    TilldateParamenter = "1";
                    end_from_dateServer2 = "Till Date";
                    isOnePressed1 = true;
                }

            }
        });






        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 29) {
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
                if (charSequence.length() == 29) {
                    description2.requestFocus();
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

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                            Toast.makeText(getActivity(), getString(R.string.wrong_start_date), Toast.LENGTH_SHORT).show();
//                            str_from_date_work_ex = ("");
//                            tv_from_date.setText(str_from_date_work_ex);
//
//                        } else
//                        {
//
//                        }
                        String mon = "";
                        switch (m) {
                            case 0:
                                mon = "January";
                                break;
                            case 1:
                                mon = "February";
                                break;
                            case 2:
                                mon = "March";
                                break;
                            case 3:
                                mon = "April";
                                break;
                            case 4:
                                mon = "May";
                                break;
                            case 5:
                                mon = "June";
                                break;
                            case 6:
                                mon = "July";
                                break;
                            case 7:
                                mon = "August";
                                break;
                            case 8:
                                mon = "September";
                                break;
                            case 9:
                                mon = "October";
                                break;
                            case 10:
                                mon = "November";
                                break;
                            case 11:
                                mon = "December";
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
                        tv_from_date.setText(str_from_dateServer2);


                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle(getString(R.string.enter_start_date));
                mDatePicker.show();
            }


        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                        if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                            Toast.makeText(getActivity(), getString(R.string.wrong_end_date), Toast.LENGTH_SHORT).show();
//                            str_to_date_work_ex = ("");
//                            tv_to_date.setText(str_to_date_work_ex);
//                        } else
//                        {
//
//                        }
                        String mon = "";
                        switch (m) {
                            case 0:
                                mon = "January";
                                break;
                            case 1:
                                mon = "February";
                                break;
                            case 2:
                                mon = "March";
                                break;
                            case 3:
                                mon = "April";
                                break;
                            case 4:
                                mon = "May";
                                break;
                            case 5:
                                mon = "June";
                                break;
                            case 6:
                                mon = "July";
                                break;
                            case 7:
                                mon = "August";
                                break;
                            case 8:
                                mon = "September";
                                break;
                            case 9:
                                mon = "October";
                                break;
                            case 10:
                                mon = "November";
                                break;
                            case 11:
                                mon = "December";
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
                        tv_to_date.setText(end_from_dateServer2);
                        TilldateParamenter = "0";




                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                mDatePicker.setTitle(getString(R.string.enter_ending_date));
                mDatePicker.show();
            }


        });
        dialog.show();

        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (designation.getText().toString().length() > 0 && orgName.getText().toString().length() > 0 && description.getText().toString().length() > 0 && str_from_dateServer2.length() > 0 && end_from_dateServer2.length() > 0) {
                    WorkExperienceData workExperienceData = new WorkExperienceData(designation.getText().toString(), orgName.getText().toString(), description.getText().toString(), str_from_dateServer2, end_from_dateServer2,TilldateParamenter);
                    //globalUserProfileData.workExperienceGlobal.add(workExperienceData);
                    myWorkExperienceList.add(workExperienceData);
                    MyWorkAdapter workAdapter = new MyWorkAdapter(myWorkExperienceList);
                    workExperienceRecyclerView.setAdapter(workAdapter);
                    workAdapter.notifyDataSetChanged();
                    updateData();
                    dialog.dismiss();

                } else {
                    Toast.makeText(getActivity(), getString(R.string.fill_all_detail), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void getEducationData(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {

    }

    @Override
    public void getExperienceData(ArrayList<WorkExperienceData> work, String userid) {


        otherUserId = userid;
        myWorkExperienceList = work;

        myWorkAdapter = new MyWorkAdapter(myWorkExperienceList);
        workExperienceRecyclerView.setAdapter(myWorkAdapter);


//        myPlayerExperienceList = player;

//        MyPlayerAdapter  myPlayerAdapter = new MyPlayerAdapter(myPlayerExperienceList);
//        playerExperienceRecyclerView.setAdapter(myPlayerAdapter);

        if(!user_id.equals(otherUserId))
        {
//            addPlayerExperience.setVisibility(View.GONE);
            addWorkExperience.setVisibility(View.GONE);
        }


    }

    @Override
    public void getOthersData(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {

    }

    private void updateData() {

        Retrofit retrofit = ApiClient.getClient();
        ApiAtheliteCall call = retrofit.create(ApiAtheliteCall.class);

        coachEducation = new CoachEducation(globalUserProfileData.sportEducationGlobal, globalUserProfileData.formalEducationGlobal, globalUserProfileData.otherCertificationGlobal);
        coachExperience = new CoachExperience(globalUserProfileData.workExperienceGlobal, globalUserProfileData.experienceAsPlayerGlobal);
        coachHeader = new CoachHeader(globalUserProfileData.designation, globalUserProfileData.acamedy, globalUserProfileData.location, globalUserProfileData.description);
        Call<UserProfileCoachEditResponse> editUserData = call.editProfileData("editUserData", user_id, prof_id, new UserProfileCoachEditRequest(coachEducation, coachExperience, coachHeader));

        Log.d("EditProfileHitUrl", editUserData.request().url().toString());

        editUserData.enqueue(new Callback<UserProfileCoachEditResponse>() {
            @Override
            public void onResponse(Call<UserProfileCoachEditResponse> call, Response<UserProfileCoachEditResponse> response) {

                Log.d("Well", response.body().getData().toString());
            }

            @Override
            public void onFailure(Call<UserProfileCoachEditResponse> call, Throwable t) {
                Log.d("Error", "Error");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addWorkExperience:
                workExperienceDialog();
                break;
        }
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
            return new ViewHolder(rootview);
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


                            final TextInputEditText designation = (TextInputEditText) dialog.findViewById(R.id.designation);
                            final TextInputEditText eDescription = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience);
                            final TextInputEditText eDescription1 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience1);
                            final TextInputEditText eDescription2 = (TextInputEditText) dialog.findViewById(R.id.descriptionExperience2);
                            final TextInputEditText orgName = (TextInputEditText) dialog.findViewById(R.id.organizationName);
                            final TextView tv_from_date = (TextView) dialog.findViewById(R.id.tv_from_date);
                            final TextView tv_to_date = (TextView) dialog.findViewById(R.id.tv_to_date);

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
                                        tv_to_date.setClickable(true);

                                    } else {
                                        image_view.setImageResource(R.drawable.whiteback_with_corner);
                                        Rl_till_date.setVisibility(View.GONE);
                                        tv_to_date.setClickable(false);
                                        tv_to_date.setText("Till Date");
                                        end_from_dateServer2_adapter = "Till Date";
                                        isOnePressed1 = true;
                                        TilldateParamenter="1";
                                    }

                                }
                            });


                            final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);
                            final ImageView from_date = (ImageView) dialog.findViewById(R.id.from_date);
                            final ImageView to_date = (ImageView) dialog.findViewById(R.id.to_date);

                            final TextView close = (TextView) dialog.findViewById(R.id.close);

                            String total = description.getText().toString();
                            String substring = new String();
                            if (total.length() > 0 && total.length() <= 30) {
                                substring = total;
                                eDescription.setText(substring.trim());
                            } else if (total.length() > 30 && total.length() <= 60) {
                                substring = total.substring(0, 30);
                                String substring1 = total.substring(30, total.length());
                                eDescription.setText(substring.trim());
                                eDescription1.setText(substring1.trim());
                            } else if (total.length() > 60 && total.length() <= 90) {
                                substring = total;
                                String substring1 = total.substring(30, 60);
                                String substring2 = total.substring(60, total.length());
                                eDescription.setText(substring.trim());
                                eDescription1.setText(substring1.trim());
                                eDescription2.setText(substring2.trim());
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
                            }
                            else
                            {
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

                            from_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);


                                    eDescription.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            if (charSequence.length() == 29) {
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
                                            if (charSequence.length() == 29) {
                                                eDescription2.requestFocus();
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });

                                    DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                        public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                                            if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                                                Toast.makeText(getActivity(),getString(R.string.wrong_start_date), Toast.LENGTH_SHORT).show();
//                                                str_from_date_work_ex = ("");
//                                                tv_from_date.setText(str_from_date_work_ex);
//
//                                            } else {
//
//                                            }
                                            String mon = "";
                                            switch (m) {
                                                case 0:
                                                    mon = "January";
                                                    break;
                                                case 1:
                                                    mon = "February";
                                                    break;
                                                case 2:
                                                    mon = "March";
                                                    break;
                                                case 3:
                                                    mon = "April";
                                                    break;
                                                case 4:
                                                    mon = "May";
                                                    break;
                                                case 5:
                                                    mon = "June";
                                                    break;
                                                case 6:
                                                    mon = "July";
                                                    break;
                                                case 7:
                                                    mon = "August";
                                                    break;
                                                case 8:
                                                    mon = "September";
                                                    break;
                                                case 9:
                                                    mon = "October";
                                                    break;
                                                case 10:
                                                    mon = "November";
                                                    break;
                                                case 11:
                                                    mon = "December";
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
                                            tv_from_date.setText(str_from_dateServer2_adapter);

                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(getString(R.string.enter_start_date));
                                    mDatePicker.show();
                                }


                            });

                            to_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                        public void onDateSet(DatePicker datepicker, int y, int m, int d) {
//                                            if ((d > day && m > month && y > year) || y > year || m > month && y >= year || d > day && y >= year) {
//                                                Toast.makeText(getActivity(), getString(R.string.wrong_end_date), Toast.LENGTH_SHORT).show();
//                                                str_to_date_work_ex = ("");
//                                                tv_to_date.setText(str_to_date_work_ex);
//                                            } else {
//
//                                            }
                                            String mon = "";
                                            switch (m) {
                                                case 0:
                                                    mon = "January";
                                                    break;
                                                case 1:
                                                    mon = "February";
                                                    break;
                                                case 2:
                                                    mon = "March";
                                                    break;
                                                case 3:
                                                    mon = "April";
                                                    break;
                                                case 4:
                                                    mon = "May";
                                                    break;
                                                case 5:
                                                    mon = "June";
                                                    break;
                                                case 6:
                                                    mon = "July";
                                                    break;
                                                case 7:
                                                    mon = "August";
                                                    break;
                                                case 8:
                                                    mon = "September";
                                                    break;
                                                case 9:
                                                    mon = "October";
                                                    break;
                                                case 10:
                                                    mon = "November";
                                                    break;
                                                case 11:
                                                    mon = "December";
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
                                            tv_to_date.setText(end_from_dateServer2_adapter);
                                            TilldateParamenter = "0";

                                        }
                                    }, year, month, day);
                                    mDatePicker.getDatePicker().setCalendarViewShown(false);
                                    mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                                    mDatePicker.setTitle(getString(R.string.enter_ending_date));
                                    mDatePicker.show();
                                }


                            });
                            dialog.show();

                            submit_details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    if (designation.getText().toString().length() > 0 && orgName.getText().toString().length() > 0 && eDescription.getText().toString().length() > 0 && tv_from_date.getText().toString().length() > 0 && tv_to_date.getText().toString().length() > 0) {
                                        WorkExperienceData workExperienceData = myWorkExperienceList.get(position);
                                        workExperienceData.setDescription(eDescription.getText() + " " + eDescription1.getText() + " " + eDescription2.getText());
                                        workExperienceData.setDesignation(designation.getText().toString());
                                        workExperienceData.setOrganisationName(orgName.getText().toString());
                                        workExperienceData.setDateFrom(tv_from_date.getText().toString());
                                        workExperienceData.setDateTo(tv_to_date.getText().toString());
                                        workExperienceData.setTillDate(TilldateParamenter);
                                        MyWorkAdapter workAdapter = new MyWorkAdapter(myWorkExperienceList);
                                        workExperienceRecyclerView.setAdapter(workAdapter);
                                        workAdapter.notifyDataSetChanged();
                                        updateData();
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.fill_all_detail), Toast.LENGTH_SHORT).show();
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
                TilldateParamenter=s.getTillDate();
                String s_title = s.getDesignation().toLowerCase();
                String s1_title = s.getDateFrom().toLowerCase();
                String s4_title = s.getDateTo().toLowerCase();
                String s2_title = s.getOrganisationName().toLowerCase();
                String s3_title = s.getDescription().toLowerCase();

                if (!s_title.equals("")) {
                    s_title = Character.toString(s_title.charAt(0)).toUpperCase() + s_title.substring(1);
                    desination.setText(s_title);
                } else {

                }
                if (!s1_title.equals("")) {

//                    s1_title = Character.toString(s1_title.charAt(0)).toUpperCase() + s1_title.substring(1);
//                    s4_title= Character.toString(s4_title.charAt(0)).toUpperCase() + s4_title.substring(1);

                    duractionExp.setText(s1_title + " to " + s4_title);
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

}
