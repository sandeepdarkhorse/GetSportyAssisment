package main.darkhorse.com.getsportyassisment.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.model_classes.AssessmentDataPojo;
import main.darkhorse.com.getsportyassisment.model_classes.AssessmentListDataPojo;
import main.darkhorse.com.getsportyassisment.model_classes.AssessmentListResponse;
import main.darkhorse.com.getsportyassisment.model_classes.InstituteDataPojoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


@SuppressLint("NewApi")
public class ActivityInstituteDetail extends AppCompatActivity implements Serializable {
    public static InstituteDataPojoApi institutedataitem;
    private String[] typeArray = {"Basic", "Intermediate", "Advance"};
    private NetworkStatus network_status;
    CustomProgress customProgress;
    Button add_assisment;
    Button button_submit;
    ScrollView scrollview;
    MaterialSpinner assessment_type;
    TextInputLayout tl_assessment_name;
    TextInputLayout tl_assessment_date;
    TextInputLayout tl_assessment_vanue;
    TextInputEditText assessment_name;
    TextInputEditText assessment_vanue;
    TextInputEditText assessment_date;
    private String dateToServer = "";
    String instituteId = "";
    String Admin_id;
    RecyclerView recycleView_Listing;
    RecyclerView.LayoutManager myLayoutManager;
    AutoCompleteTextView Auto_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_institute_detail);
        SharedPreferences sharedPref = getSharedPreferences("Dashboard_prefs", Context.MODE_PRIVATE);
        Admin_id = sharedPref.getString("user_id", "");

        customProgress = CustomProgress.getInstance();
        network_status = new NetworkStatus(ActivityInstituteDetail.this);
        ImageView imageView = (ImageView) findViewById(R.id.institute_image);
        TextView instname = (TextView) findViewById(R.id.inst_name);

        TextView instlocation = (TextView) findViewById(R.id.inst_location);

        Bundle userinfo = getIntent().getExtras();
        if (userinfo != null) {

            institutedataitem = (InstituteDataPojoApi) userinfo.getSerializable("institutedetail");
            if (institutedataitem != null && institutedataitem != null) {
                instituteId = institutedataitem.getId();
                instname.setText(institutedataitem.getCollege_name());
                instlocation.setText(institutedataitem.getLocation());

            } else {

            }


        }


//        LinearLayout mainlaout = (LinearLayout) findViewById(R.id.ll_back_press);
//        mainlaout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finishAfterTransition();
//                onBackPressed();
//            }
//        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        add_assisment = (Button) findViewById(R.id.add_assisment);
        add_assisment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDiag();

            }
        });

        recycleView_Listing = (RecyclerView) findViewById(R.id.assessmentlist);

        myLayoutManager = new LinearLayoutManager(ActivityInstituteDetail.this, LinearLayoutManager.VERTICAL, false);
        recycleView_Listing.setLayoutManager(myLayoutManager);

        Retrofit_listdata();

        Auto_search = (AutoCompleteTextView) findViewById(R.id.search_assessment);
        ImageView im_search = (ImageView) findViewById(R.id.im_search);
        im_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Auto_search.getVisibility() == View.VISIBLE) {

                    Auto_search.setVisibility(View.GONE);
                    Auto_search.startAnimation(AnimationUtils.loadAnimation(ActivityInstituteDetail.this, R.anim.slide_out_right));


                } else {

                    Auto_search.setVisibility(View.VISIBLE);
                    Auto_search.startAnimation(AnimationUtils.loadAnimation(ActivityInstituteDetail.this, R.anim.slide_in_left));


                }


            }
        });


        Auto_search.setThreshold(0);


        Auto_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
//                if (s.toString().length() <= 3)
//                {
//
//
//
//                }
                if (s.length() > 0) {
                    updateList(s.charAt(0));
                } else {
                    InstituteListingAdapter adapter = new InstituteListingAdapter(arrylistinstitute);
                    adapter.notifyDataSetChanged();
                    recycleView_Listing.setAdapter(adapter);
                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finishAfterTransition();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public boolean hexChecker(char c, String assesstmentname) {

        char[] charArray = assesstmentname.toCharArray();

        for (char ch : charArray) {
            if (c == ch) {
                System.out.println("It worked!");
                return true;
            } else {
                System.out.println("It did not work!");
                return false;
            }
        }

        return false;
    }


    public void updateList(char assesstmentname) {

        ArrayList<AssessmentListDataPojo> arrylist = new ArrayList<>();
        arrylist.clear();

        for (AssessmentListDataPojo string : arrylistinstitute) {

            if (hexChecker(assesstmentname, string.getAssessment_name())) {
                arrylist.add(string);

            }
        }

        if (arrylist.size() > 0) {

            InstituteListingAdapter adapter = new InstituteListingAdapter(arrylist);
            adapter.notifyDataSetChanged();
            recycleView_Listing.setAdapter(adapter);

        } else {
            InstituteListingAdapter adapter = new InstituteListingAdapter(arrylistinstitute);
            adapter.notifyDataSetChanged();
            recycleView_Listing.setAdapter(adapter);
        }


    }

    ArrayList<AssessmentListDataPojo> arrylistinstitute;

    public void Retrofit_listdata() {
        arrylistinstitute = new ArrayList<>();
        arrylistinstitute.clear();


        if (network_status.isConnectingToInternet()) {
            try {
                String performanceUrl = "http://testingapp.getsporty.in/assessment_admin_controller.php?";


                String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("assessment_list", "UTF-8")
                        + "&" + URLEncoder.encode("institute_id", "UTF-8") + "=" + URLEncoder.encode(instituteId, "UTF-8");

                Retrofit retrofit = ApiClient.getClient();
                ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
                Call<AssessmentListResponse> checklogin = apiCall.getAssessmentlist(performanceUrl + data);
                Log.d("Resourse listing url:::", checklogin.request().url().toString());
                customProgress.showProgress(ActivityInstituteDetail.this, false);

                checklogin.enqueue(new Callback<AssessmentListResponse>() {
                    @Override
                    public void onResponse(Call<AssessmentListResponse> call, Response<AssessmentListResponse> response) {
                        customProgress.hideProgress();
                        arrylistinstitute = response.body().getData();
                        InstituteListingAdapter adapter = new InstituteListingAdapter(arrylistinstitute);
                        recycleView_Listing.setAdapter(adapter);


                    }

                    @Override
                    public void onFailure(Call<AssessmentListResponse> call, Throwable t) {
                        customProgress.hideProgress();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(ActivityInstituteDetail.this, R.string.no_internet_connection_error, Toast.LENGTH_LONG).show();

        }


    }

    @SuppressLint("NewApi")
    public class InstituteListingAdapter extends RecyclerView.Adapter<InstituteListingAdapter.ViewHolder> {


        private ArrayList<AssessmentListDataPojo> DataItems = new ArrayList<AssessmentListDataPojo>();
        private View rootview;

        InstituteListingAdapter(ArrayList<AssessmentListDataPojo> DietDataItems) {
            this.DataItems = DietDataItems;

        }

        @Override
        public InstituteListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessementlist_item, parent, false);


            return new InstituteListingAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(InstituteListingAdapter.ViewHolder holder, int position) {

            holder.setItem(DataItems.get(position));
            if (position % 2 == 0)
            {
                holder.background.setBackground(getDrawable(R.color.db_institute_color));
            }
            else
                {
                    holder.background.setBackground(getDrawable(R.color.db_selfEvent_color));


            }
//            holder.setItem(DietDataItems.get(position).getMy_diet_plan());
        }

        @Override
        public int getItemCount() {
            return DataItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            private TextView name;

            private TextView date, assignmaster;
            RelativeLayout background;

            public ViewHolder(final View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.name);

                date = (TextView) itemView.findViewById(R.id.date);

                assignmaster = (TextView) itemView.findViewById(R.id.assign_master);


                background = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);


                assignmaster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActivityInstituteDetail.this, "Waiting for Api implementation", Toast.LENGTH_SHORT).show();
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();

//                        InstituteDataPojoApi institutedata = DataItems.get(position);
//                        Bundle userinfo = new Bundle();
//                        userinfo.putSerializable("institutedetail", (Serializable) institutedata);
//                        Intent i = new Intent(new Intent(getActivity(), ActivityInstituteDetail.class));
//                        i.putExtras(userinfo);
//
//                        ActivityOptions options = ActivityOptions
//                                .makeSceneTransitionAnimation(getActivity(),
//                                        Pair.create(itemView.findViewById(R.id.athlete_image), "image_transition"),
//                                        Pair.create(itemView.findViewById(R.id.name), "name_transition"),
//                                        Pair.create(itemView.findViewById(R.id.location), "location_transition"));
//
//                        startActivity(i, options.toBundle());


                    }
                });

            }

            public void setItem(AssessmentListDataPojo DataItem) {
                name.setText(DataItem.getAssessment_name());
                date.setText(DateConversion.dateconversion(DataItem.getAssigned_date()));


            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private int[] validation = new int[4];

    private void showDiag() {

        final View dialogView = View.inflate(ActivityInstituteDetail.this, R.layout.create_assistment_dialog, null);
        final Dialog dialog = new Dialog(ActivityInstituteDetail.this, R.style.CustomDialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(dialogView);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Toolbar toolbar_dissmiss = (Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(dialogView, false, dialog);
            }
        });


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        scrollview = (ScrollView) dialog.findViewById(R.id.scrollview);

        assessment_type = (MaterialSpinner) dialog.findViewById(R.id.assessment_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityInstituteDetail.this, android.R.layout.simple_spinner_item, typeArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        assessment_type.setAdapter(adapter);


        tl_assessment_name = (TextInputLayout) dialog.findViewById(R.id.tl_assessment_name);
        assessment_name = (TextInputEditText) dialog.findViewById(R.id.assessment_name);


        tl_assessment_date = (TextInputLayout) dialog.findViewById(R.id.tl_assessment_date);
        assessment_date = (TextInputEditText) dialog.findViewById(R.id.assessment_date);


        tl_assessment_vanue = (TextInputLayout) dialog.findViewById(R.id.tl_assessment_vanue);
        assessment_vanue = (TextInputEditText) dialog.findViewById(R.id.assessment_vanue);


        button_submit = (Button) dialog.findViewById(R.id.submit_details);


        assessment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(ActivityInstituteDetail.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(android.widget.DatePicker datePicker, int y, int m, int d)

                    {
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

                        dateToServer = y + "-" + modMon + "-" + modDay;
                        String birthDate = d + " " + mon + " " + y;
                        assessment_date.setText(birthDate);
                        // editText_date_of_birth.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, day);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());


                mDatePicker.show();
            }


        });


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {


                String name = assessment_name.getText().toString();
//                String assessmentdate = assessment_date.getText().toString();
                String assessmentvanue = assessment_vanue.getText().toString();
                String assessmenttype = assessment_type.getSelectedItem().toString();


                if (name.equals("")) {
                    validation[0] = 1;
                    tl_assessment_name.setErrorEnabled(true);
                    tl_assessment_name.setError("You need to enter assessment date");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[0] = 0;

                    tl_assessment_name.setError(null);
                }

                if (dateToServer.equals("")) {
                    validation[1] = 1;
                    tl_assessment_date.setErrorEnabled(true);
                    tl_assessment_date.setError("You need to enter assessment date");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[1] = 0;

                    tl_assessment_date.setError(null);
                }
                if (assessmentvanue.equals("")) {
                    validation[2] = 1;
                    tl_assessment_vanue.setErrorEnabled(true);
                    tl_assessment_vanue.setError("You need to enter assessment vanue");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[2] = 0;
                    tl_assessment_vanue.setError(null);
                }

                if (assessmenttype.equals("Type of assessment")) {
                    validation[3] = 1;
                    assessment_type.setError("You need to enter assessment type");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else if (assessmenttype.equals("")) {
                    validation[3] = 1;
                    assessment_type.setError("You need to enter assessment type");
                    scrollview.fullScroll(ScrollView.FOCUS_UP);


                } else {
                    validation[3] = 0;

                    assessment_type.setError(null);
                }
                int sum = validation[0] + validation[1] + validation[2] + validation[3];
                if (sum == 0) {


                    try {

                        if (network_status.isConnectingToInternet()) {

                            try {
                                Retrofit retrofit = ApiClient.getClient();
                                ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);

                                AssessmentDataPojo datapojo = new AssessmentDataPojo(Admin_id, name, assessmentvanue, "0", assessmenttype, dateToServer, instituteId);
                                Call<JsonElement> checklogin = apiCall.addassessment("add_assessment", datapojo);
                                Log.e("institute listing url:", checklogin.request().url().toString());
                                customProgress.showProgress(ActivityInstituteDetail.this, false);
                                checklogin.enqueue(new Callback<JsonElement>() {
                                    @Override
                                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                        customProgress.hideProgress();
                                        JsonElement jsonElement = response.body();
                                        Log.e("JSON RESPONSE", jsonElement.toString());
                                        try {
                                            JSONObject jsonobj = new JSONObject(jsonElement.toString());
                                            String status = jsonobj.getString("status");
                                            if (status.equals("1")) {

                                                revealShow(dialogView, false, dialog);
                                                Retrofit_listdata();

                                            } else {

                                                Toast.makeText(ActivityInstituteDetail.this, (getString(R.string.server_error_text)), Toast.LENGTH_LONG).show();

                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(ActivityInstituteDetail.this, "Please try again", Toast.LENGTH_SHORT).show();

                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<JsonElement> call, Throwable t) {
                                        customProgress.hideProgress();
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                                customProgress.hideProgress();
                            }


                        } else {
                            Toast.makeText(ActivityInstituteDetail.this, "No internet connection", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(ActivityInstituteDetail.this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
                }


            }


        });

        dialog.show();
    }


    @SuppressLint("NewApi")
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);
        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (add_assisment.getX() + (add_assisment.getWidth() / 2));
        int cy = (int) (add_assisment.getY()) + add_assisment.getHeight() + 56;


        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }


}
