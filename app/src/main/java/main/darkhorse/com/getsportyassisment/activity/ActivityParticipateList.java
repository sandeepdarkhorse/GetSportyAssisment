package main.darkhorse.com.getsportyassisment.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.MainUrls;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.model_classes.AppliedTournamentApplicantResponse;
import main.darkhorse.com.getsportyassisment.model_classes.AppliedTournamentListResponse;
import main.darkhorse.com.getsportyassisment.model_classes.TournamentCategoryData;
import main.darkhorse.com.getsportyassisment.performance.PerformanceActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityParticipateList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    public static final String MY_PREFS_NAME = "Dashboard_prefs";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private static String tourId,tournamentsport;
    String value = "";
    private static ArrayList<TournamentCategoryData> tournamentCategories;
    Boolean isInternetPresent = false;
    NetworkStatus network_status;
    ImageView imageView_profile_pic;
    Bitmap bitmap;
    Bitmap profile_pic1;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    String profile_url = MainUrls.mainUrl;
    ArrayList<HashMap<String, String>> list_data;
    //MyToast toast = new MyToast();
    ArrayList<AppliedTournamentApplicantResponse> tournamentappliedDataArray = new ArrayList<AppliedTournamentApplicantResponse>();
    RecyclerView applicantjobView;
    TextView textView_nodata;
    //CustomProgress customProgress;

    HashMap<String, String> categoriesMap;
    HashMap<String, String> categoriesValuesMap;
    String tournamentId,sport;
    Toolbar toolbar;
    CustomProgress customProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_participate_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customProgress = CustomProgress.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Participant List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        network_status = new NetworkStatus(ActivityParticipateList.this);
        isInternetPresent = network_status.isConnectingToInternet();

        applicantjobView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        applicantjobView.setLayoutManager(myLayoutManager);
        textView_nodata = (TextView)findViewById(R.id.no_data);

        Intent intent = getIntent();
        tournamentId=intent.getStringExtra("tournamnet_id");
        sport=intent.getStringExtra("sport");



        isInternetPresent = network_status.isConnectingToInternet();
        if (isInternetPresent) {

            try {
                initView();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            Toast.makeText(getApplicationContext(), "Internet is not connected", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        SharedPreferences sharedPreferences1 = getSharedPreferences(MY_PREFS_NAME, 0);
        String user_id = sharedPreferences1.getString("user_id", "");

        Retrofit retrofit = ApiClient.getClient();
        ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
        Call<AppliedTournamentListResponse> applicantlist = apiCall.getappliedtournament("participant_list", tournamentId);
        customProgress.showProgress(ActivityParticipateList.this, false);
        Log.e("well", applicantlist.request().url().toString());
        applicantlist.enqueue(new Callback<AppliedTournamentListResponse>() {
            @Override
            public void onResponse(Call<AppliedTournamentListResponse> call, Response<AppliedTournamentListResponse> response) {

                if (response.body().getStatus().equals("1"))
                {

                    customProgress.hideProgress();
                    tournamentappliedDataArray = response.body().getData();

                    for (int i = 0; i < tournamentappliedDataArray.size(); i++) {
                        AppliedTournamentApplicantResponse applicantResponse = tournamentappliedDataArray.get(i);
                        for (int j = 0; j < i; j++) {
                            AppliedTournamentApplicantResponse applicantResponse1 = tournamentappliedDataArray.get(j);
                            if (applicantResponse.getApplicant_id().equals(applicantResponse1.getApplicant_id())) {
                                applicantResponse1.setCategory_code("," + applicantResponse.getCategory_code());
                                tournamentappliedDataArray.set(j, applicantResponse1);
                                tournamentappliedDataArray.remove(i);
                                i--;
                            }

                        }
                    }

                    JobAppliedAdapter adapter = new JobAppliedAdapter(tournamentappliedDataArray);
                    applicantjobView.setAdapter(adapter);
                    textView_nodata.setVisibility(View.GONE);


                } else {
                    customProgress.hideProgress();
                    textView_nodata.setVisibility(View.VISIBLE);

//                    MyToast toast = new MyToast();
//                    toast.show(getActivity(), "No Candidate Applied.", false);
//
                    tournamentappliedDataArray.clear();
                    JobAppliedAdapter adapter = new JobAppliedAdapter(tournamentappliedDataArray);
                    applicantjobView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<AppliedTournamentListResponse> call, Throwable t) {
                customProgress.hideProgress();
            }
        });

    }

    @Override
    public void onRefresh() {

    }

    public class JobAppliedAdapter extends RecyclerView.Adapter<JobAppliedAdapter.ViewHolder> {


        private final ArrayList<AppliedTournamentApplicantResponse> professionalsDataItems;
        private View rootview;

        JobAppliedAdapter(ArrayList<AppliedTournamentApplicantResponse> professionalsDataItems) {
            this.professionalsDataItems = professionalsDataItems;

        }

        @Override
        public JobAppliedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_participant_list_item, parent, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(JobAppliedAdapter.ViewHolder holder, final int position) {
            holder.setItem(professionalsDataItems.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String lite_user_id = professionalsDataItems.get(position).getApplicant_id();
                    String profId = professionalsDataItems.get(position).getProf_id();
                    String lite_user_name = professionalsDataItems.get(position).getName();
                    String liteSport = professionalsDataItems.get(position).getSport();
                    String doB=professionalsDataItems.get(position).getDob();
                   String applicantId= professionalsDataItems.get(position).getApplicant_id();

//                    String job_status = jobapplied.get(position).getStatus();
                    //  userinfo.putString("Connectionid", Connection_id);

                    Bundle userinfo = new Bundle();
                    userinfo.putString("liteuserid", lite_user_id);
                    userinfo.putString("liteuserprofid", profId);
                    userinfo.putString("liteuser_sport", liteSport);
                    userinfo.putString("lite_user_name", lite_user_name);
                    userinfo.putString("job_title", "");
                    userinfo.putString("job_id", "");
                    userinfo.putString("job_status", "");
                    userinfo.putString("indiacterforprofile", "1");
                    Intent i = new Intent(ActivityParticipateList.this, UserProfile.class);
                    i.putExtras(userinfo);
                    startActivity(i);


                }
            });

            holder.buttonCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String lite_user_id = professionalsDataItems.get(position).getApplicant_id();
                    String profId = professionalsDataItems.get(position).getProf_id();
                    String lite_user_name = professionalsDataItems.get(position).getName();
                    String liteSport = professionalsDataItems.get(position).getSport();
                    String doB=professionalsDataItems.get(position).getDob();
                    String applicantId= professionalsDataItems.get(position).getApplicant_id();

                    Intent i = new Intent(ActivityParticipateList.this, PerformanceActivity.class);
                    i.putExtra("gender", "Male");
                    i.putExtra("dob", doB);
                    i.putExtra("studentid",applicantId );
                    i.putExtra("sport", liteSport);
                    startActivity(i);

                }
            });


        }

        @Override
        public int getItemCount() {
            return professionalsDataItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textViewName;
            private TextView textViewSport;
            private TextView textViewGender;
            private ImageView share;
            private TextView textViewDob;
            private TextView textViewAge;
            private ImageView profilePic;
            private Button buttonCategories;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.name);
                textViewSport = itemView.findViewById(R.id.sport);
                textViewGender = itemView.findViewById(R.id.gender);
               // share = itemView.findViewById(R.id.share_imagebutton);
                profilePic = itemView.findViewById(R.id.profile_pic);
                textViewDob = itemView.findViewById(R.id.dob);
                textViewAge = itemView.findViewById(R.id.age);
                buttonCategories = itemView.findViewById(R.id.categories);
            }

            public void setItem(AppliedTournamentApplicantResponse professionalsDataItem) {

                textViewName.setText(professionalsDataItem.getName());
                textViewSport.setText(professionalsDataItem.getSport());
                textViewGender.setText(professionalsDataItem.getGender());


                textViewDob.setText("Dob - "+ DateConversion.dateconversionMonth(professionalsDataItem.getDob()));
                textViewAge.setText("Age - "+professionalsDataItem.getAge()+" Year");

//                if(professionalsDataItem.getStatus().equals("2"))
//                {
//                    status.setText("Attended");
//                }else{
//                    status.setText("Did Attend");
//                }

                if (professionalsDataItem.getUser_image() != null && !professionalsDataItem.getUser_image().equals(""))
                    Picasso.with(getApplicationContext()).load(professionalsDataItem.getUser_image())
                            .placeholder(R.drawable.account_blank)
                            .error(R.drawable.account_blank)
                            .into(profilePic);


            }
        }
    }

    @Override
    public void onBackPressed() {
//        Intent i = new Intent(ActivityJobDetail.this, BaseActivity.class);
//        startActivity(i);
        // super.onBackPressed();
        this.finish();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                //  super.onBackPressed();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
