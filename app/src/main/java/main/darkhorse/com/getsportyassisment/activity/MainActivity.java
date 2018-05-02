package main.darkhorse.com.getsportyassisment.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiCall;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CommonUtils;
import main.darkhorse.com.getsportyassisment.UtilsFile.MainUrls;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.fragment.Fragment_Share;
import main.darkhorse.com.getsportyassisment.model_classes.MyTournamentDataModel;
import main.darkhorse.com.getsportyassisment.model_classes.MyTournamentResponse;
import main.darkhorse.com.getsportyassisment.model_classes.TournamentListingResponse;
import main.darkhorse.com.getsportyassisment.model_classes.TournamentListingResponseItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress.customProgress;
import static main.darkhorse.com.getsportyassisment.custom_classes.DateConversion.context;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String search_url = MainUrls.mainUrl + "?";//"http://getsporty.in/create_database.php";
    private static final String image_url = MainUrls.TOURNAMENT_IMAGE_URL;


    RecyclerView recycleview_tournamentListing;
    LinearLayoutManager myLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView textViewDefault;
    RelativeLayout noInternetLayout;
    ProgressBar  noInternetProgressBar;
    NetworkStatus network_status;
    private boolean isDataLoaded;
    String user_id;
    private Retrofit retrofit;
    private ApiAtheliteCall apiCall;
    private ArrayList<MyTournamentDataModel> tournament_dataitem = new ArrayList<MyTournamentDataModel>();
    Uri uri;
    private FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
 //       setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        customProgress= CustomProgress.getInstance();
        fm = getSupportFragmentManager();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        sharedpref = getSharedPreferences("PROFILEDATA", Context.MODE_PRIVATE);
//        userId = sharedpref.getString("user_id", "");
//        profId = sharedpref.getString("prof_id", "");
        SharedPreferences sharedPreferences_user = getSharedPreferences("Dashboard_prefs", 0);
        user_id = sharedPreferences_user.getString("user_id", "");
        initView();
    }

    private void initView() {


        recycleview_tournamentListing = (RecyclerView) findViewById(R.id.tournament_list);
        myLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recycleview_tournamentListing.setLayoutManager(myLayoutManager);

//        ApplyTournamentArrayList = new ArrayList<ApplyTournamentModel>();
//        ApplyCategory = new ArrayList<Category>();

//        SharedPreferences sharedPreferences_user = getActivity().getSharedPreferences("LoginCredentials", 0);
//        user_id = sharedPreferences_user.getString("userid", "");


        textViewDefault = (TextView) findViewById(R.id.default_text);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(false);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.tournament_color, R.color.event_color);
        noInternetLayout = (RelativeLayout)findViewById(R.id.noInternetLayout);
        noInternetLayout.setVisibility(View.GONE);
        noInternetProgressBar = (ProgressBar) findViewById(R.id.noInternetProgress);
        noInternetProgressBar.setVisibility(View.GONE);
        //instantiation();
        SharedPreferences sharedPreferences = getSharedPreferences("ResourcePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_fragment", "listing");
        editor.putString("previous_fragment", "tournament_main");
        editor.commit();

        network_status = new NetworkStatus(MainActivity.this);
        if (network_status.isConnectingToInternet()) {
            Retrofit_listdata("");
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
           // isDataLoaded = false;


        }



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (network_status.isConnectingToInternet()) {
                    Retrofit_listdata("");
                } else {
                    noInternetLayout.setVisibility(View.VISIBLE);
                   // isDataLoaded = false;

                }
            }
        });

        noInternetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noInternetProgressBar.setVisibility(View.VISIBLE);
                Handler myNetworkHandler = new Handler();
                myNetworkHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (network_status.isConnectingToInternet()) {
                            noInternetLayout.setVisibility(View.GONE);
                            Retrofit_listdata("");
                        }
                        noInternetProgressBar.setVisibility(View.GONE);

                    }
                }, 500);

            }
        });


    }


    public void Retrofit_listdata(final String position) {

        retrofit = ApiClient.getClient();
        apiCall = retrofit.create(ApiAtheliteCall.class);
        Call<MyTournamentResponse> getStudentList = apiCall.getMyTournament("editcreation", user_id, "3", "");
        customProgress.showProgress(MainActivity.this,  false);
        Log.d("URLTAG", getStudentList.request().url().toString());
        getStudentList.enqueue(new Callback<MyTournamentResponse>() {
            @Override
            public void onResponse(Call<MyTournamentResponse> call, Response<MyTournamentResponse> response) {
                if (response.body().getStatus() == 1)
                {
                    customProgress.hideProgress();
                    swipeRefreshLayout.setRefreshing(false);

                    tournament_dataitem = response.body().getData();


                    ProfessionalClassesAdapter log_adapter = new ProfessionalClassesAdapter(tournament_dataitem);
                    recycleview_tournamentListing.setAdapter(log_adapter);
                    // myRelativeLayout.setVisibility(View.VISIBLE);
                    textViewDefault.setVisibility(View.GONE);


                } else {
                    customProgress.hideProgress();
                    swipeRefreshLayout.setRefreshing(false);


                    MyToast toast = new MyToast();
                    tournament_dataitem.clear();
                    ProfessionalClassesAdapter log_adapter = new ProfessionalClassesAdapter(tournament_dataitem);
                    recycleview_tournamentListing.setAdapter(log_adapter);
                    // myRelativeLayout.setVisibility(View.GONE);
                    textViewDefault.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<MyTournamentResponse> call, Throwable t) {
                 customProgress.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_about) {
            Intent intentAbout= new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intentAbout);

        } else if (id == R.id.nav_contact) {
            Intent intentAbout= new Intent(MainActivity.this,ActivityContactUs.class);
            startActivity(intentAbout);


        }  else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences_logout = getSharedPreferences("Login", 0);
            SharedPreferences.Editor editor = sharedPreferences_logout.edit();
            editor.clear();
            editor.commit();
            SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor1.clear();
            editor1.commit();

            SharedPreferences sharedPreferences1 = getSharedPreferences("Dashboard_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences1.edit();
            editor2.clear();
            editor2.commit();

            Intent main = new Intent(MainActivity.this, FirstViewPagerActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(main);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public class ProfessionalClassesAdapter extends RecyclerView.Adapter<ProfessionalClassesAdapter.ViewHolder> {


        private final ArrayList<MyTournamentDataModel> overallClasses;
        private View rootview;

        ProfessionalClassesAdapter(ArrayList<MyTournamentDataModel> overallClasses) {
            this.overallClasses = overallClasses;
        }

        @Override
        public ProfessionalClassesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tournament_listing_item, parent, false);

            return new ProfessionalClassesAdapter.ViewHolder(rootview);
        }

        @SuppressLint("NewApi")
        @Override
        public void onBindViewHolder(ProfessionalClassesAdapter.ViewHolder holder, int position) {
            holder.setItem(overallClasses.get(position));


        }

        @Override
        public int getItemCount() {
            return overallClasses.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_event_title;
            TextView tv_event_date;
            TextView tv_event_address;
            TextView tv_event_sport;
            TextView tv_event_type;
            ImageView imageView_event_image;
            ImageView imageView_event_share;
            TextView tv_event_applicant;
            TextView tv_event_publish,tour_liveupdate;
            ProgressBar progressBarImageLoad;

            public ViewHolder(final View itemView) {
                super(itemView);
                tv_event_title = (TextView) itemView.findViewById(R.id.event_title);
                tv_event_date = (TextView) itemView.findViewById(R.id.event_date);
                tv_event_address = (TextView) itemView.findViewById(R.id.event_location);
                tv_event_sport = (TextView) itemView.findViewById(R.id.sport_name);
                tv_event_type = (TextView) itemView.findViewById(R.id.event_type);
                imageView_event_image = (ImageView) itemView.findViewById(R.id.event_image);
                imageView_event_share = (ImageView) itemView.findViewById(R.id.share_event);
                tv_event_applicant = (TextView) itemView.findViewById(R.id.event_applicant);
                tv_event_publish = (TextView) itemView.findViewById(R.id.event_publish);

                tour_liveupdate = (TextView) itemView.findViewById(R.id.tour_liveupdate);
               // tour_liveupdate.setVisibility(View.VISIBLE);

                progressBarImageLoad = (ProgressBar) itemView.findViewById(R.id.progressBarImageLoad);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int position = recycleview_tournamentListing.getChildAdapterPosition(itemView);
                        String tournament_id = tournament_dataitem.get(position).getId();
                        MyTournamentDataModel tournamentdata_item = tournament_dataitem.get(position);

                        String imageName = tournament_dataitem.get(position).getImage();
                        String tournamentName=tournament_dataitem.get(position).getName();

                        Bundle tournamentInfo = new Bundle();
                        tournamentInfo.putSerializable("tournamentdata", (Serializable) tournamentdata_item);
                        Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                        intent.putExtras(tournamentInfo);
                        intent.putExtra("name",tournamentName);
                        startActivity(intent);
                    }
                });


                tv_event_publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final    int position = recycleview_tournamentListing.getChildAdapterPosition(itemView);
                        final    String job_status = tournament_dataitem.get(position).getPublish();
                        ;
                        String message="";
                        String buttonText="";
                        if (job_status.equals("0")) {
                            message="Do you want publish this ?";
                            buttonText="Publish";

                        } else {
                            message="Do you want unpublish this ?";
                            buttonText="UnPublish";
                        }


                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
// Add the buttons?
                        builder.setTitle(buttonText);
                        builder.setMessage(message);
                        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button

                                String tournament_id = tournament_dataitem.get(position).getId();
                           //     Un_publishretrofit("0", tournament_id);


                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        //set cancel false dialog
                        builder.setCancelable(true);
                        // Create the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

//
                    }
                });

                tv_event_applicant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

//                        int position = recyclerView.getChildAdapterPosition(itemView);
//                        MyTournamentDataModel tournamentdata_item = tournament_dataitem.get(position);
//
//                        Bundle userinfo = new Bundle();
//                        userinfo.putSerializable("tournamentdata", (Serializable) tournament_dataitem);
//                        Intent i = new Intent(getActivity(), ActivityTournamentApplicantListing.class);
//                        i.putExtras(userinfo);
//                        startActivity(i);

                        int position = recycleview_tournamentListing.getChildAdapterPosition(itemView);
                        MyTournamentDataModel tournamentdata_item = tournament_dataitem.get(position);


                        String tournamentid = tournament_dataitem.get(position).getId();
                        String sport = tournament_dataitem.get(position).getSport();
                        Intent myIntent =new Intent(MainActivity.this,ActivityParticipateList.class);
                        myIntent.putExtra("tournamnet_id",tournamentid);
                        myIntent.putExtra("sport",sport);
                        startActivity(myIntent);


//                        Bundle userinfo = new Bundle();
//                        userinfo.putSerializable("tournamentdata", (Serializable) tournamentdata_item);
//                        Intent i = new Intent(MainActivity.this, ActivityTournamentApplicantListing.class);
//                        i.putExtras(userinfo);
//                        startActivity(i);
//                        SharedPreferences sharedPreferences = getSharedPreferences("Fragments", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("current_tab", 0);
//                        editor.commit();

                        //
//                        String tournamentid = tournament_dataitem.get(position).getId();
//                        String sport = tournament_dataitem.get(position).getSport();
//                        FragmentTransaction ft = fm.beginTransaction();
//                        Fragment eventFragment = getFragmentManager().findFragmentByTag("myTestFragment");
//
//                        if (eventFragment != null) {
//                            ft.hide(eventFragment);
//                            ft.add(R.id.container, TournamentApplicantListing.newInstance(tournamentid, tournament_dataitem.get(position).getCategory(),sport), "tournament_applicant");
//                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                            ft.addToBackStack("mycreation");
//                            ft.commit();
//                        } else {
//                            FragmentManager fragmentManger = getFragmentManager();
//                            fragmentManger.beginTransaction().replace(R.id.container, TournamentApplicantListing.newInstance(tournamentid, tournament_dataitem.get(position).getCategory(),sport)).addToBackStack("tournament_applicant").commit();
//
//                        }




                    }
                });

                tour_liveupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = recycleview_tournamentListing.getChildAdapterPosition(itemView);
                        MyTournamentDataModel tournamentdata_item = tournament_dataitem.get(position);

                        String end_date = tournament_dataitem.get(position).getEnd_date();
                        String tournamentid = tournament_dataitem.get(position).getId();
                        String sport = tournament_dataitem.get(position).getSport();
                        String tournamentImage=tournament_dataitem.get(position).getImage();
                        String tournamentName=tournament_dataitem.get(position).getName();
//                        FragmentTransaction ft = fm.beginTransaction();
//                        Fragment eventFragment = getFragmentManager().findFragmentByTag("myTestFragment");

//                        if (eventFragment != null) {
//                            ft.hide(eventFragment);
//                            ft.add(R.id.container, TournamentLiveUpdateListing.newInstance(tournamentdata_item), "tournament_updates");
//                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                            ft.addToBackStack("mycreation");
//                            ft.commit();
//                        } else {
//                            FragmentManager fragmentManger = getFragmentManager();
//                            fragmentManger.beginTransaction().replace(R.id.container, TournamentLiveUpdateListing.newInstance(tournamentdata_item)).addToBackStack("tournament_applicant").commit();
//
//                        }
////
//                        Intent updateIntent=new Intent(MainActivity.this,ActivityTournamentUpdate.class);
//                        updateIntent.putExtra("tournament_id",tournamentid);
//                        updateIntent.putExtra("tournament_name",tournamentName);
//                        updateIntent.putExtra("image",tournamentImage);
//                        updateIntent.putExtra("sports",sport);
//                        updateIntent.putExtra("endDate",end_date);
//                        startActivity(updateIntent);

                    }
                });

                imageView_event_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        SharedPreferences sharedPreferences_login = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        boolean login = sharedPreferences_login.getBoolean("login", false);
                        if (login == true) {

                            int position = recycleview_tournamentListing.getChildAdapterPosition(itemView);

                            String jobid = tournament_dataitem.get(position).getId();
                            final String link = "https://www.getsporty.in/share.php/referrer=share&module=3&id=" + jobid;
                            Bitmap bm = DateConversion.screenShot(rootview);
                            File file = DateConversion.saveBitmap(bm, "mantis_image.png");
                            Log.i("chase", "filepath: " + file.getAbsolutePath());
                            if (Build.VERSION.SDK_INT >= 21) {
                                uri = FileProvider.getUriForFile(context, "main.darkhorse.com.getsportyassisment", file);
                                showDialog(link, uri);
                            } else {
                                uri = Uri.fromFile(file);
                                showDialog(link, uri);
                            }

                        } else {

                        }
                    }
                });


            }


            @SuppressLint("NewApi")
            public void setItem(MyTournamentDataModel organizedClassDataItem) {

                String event_name = organizedClassDataItem.getName().toString().toLowerCase();
                String Sport = organizedClassDataItem.getSport().toString().toLowerCase();
                String address_1 = organizedClassDataItem.getAddress_1().toString().toLowerCase();
                String address_2 = organizedClassDataItem.getAddress_2().toString().toLowerCase();
                String location = organizedClassDataItem.getLocation().toString().toLowerCase();

                if (!event_name.equals("")) {
                    event_name = Character.toString(event_name.charAt(0)).toUpperCase() + event_name.substring(1);
                    tv_event_title.setText(event_name);

                } else {

                }


                if (!Sport.equals("")) {
                    Sport = Character.toString(Sport.charAt(0)).toUpperCase() + Sport.substring(1);
                    tv_event_sport.setText(Sport);

                } else {

                }

                if (!address_1.equals("")) {
                    address_1 = Character.toString(address_1.charAt(0)).toUpperCase() + address_1.substring(1);


                } else {
                    address_1 = "";
                }
                if (!address_2.equals("")) {
                    address_2 = Character.toString(address_2.charAt(0)).toUpperCase() + address_2.substring(1);


                } else {
                    address_2 = "";
                }
                if (!location.equals("")) {
                    location = Character.toString(location.charAt(0)).toUpperCase() + location.substring(1);


                } else {
                    location = "";
                }
                tv_event_address.setText(address_1 + " " + address_2 + " " + location);

                tv_event_date.setText(DateConversion.dateconversion(organizedClassDataItem.getStart_date()) + " - " + DateConversion.dateconversion(organizedClassDataItem.getEnd_date()));


                final String jobstatus = organizedClassDataItem.getPublish();
                if (jobstatus.equals("1")) {

//                    tv_event_publish.setText(getResources().getString(R.string.unpublish));
//                    tv_event_publish.setTextColor(getResources().getColor(R.color.primary_white));
//                    tv_event_publish.setBackgroundDrawable(getResources().getDrawable(R.drawable.publishrounded));
                } else {

//                    tv_event_publish.setText(getResources().getString(R.string.publish));
//                    tv_event_publish.setTextColor(getResources().getColor(R.color.my_profile));
//                    tv_event_publish.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedcorner_transparent));

                }


                String user_image = organizedClassDataItem.getImage();
                if (user_image != null && !user_image.equals(""))
                {

                    String imageurl = image_url + user_image;
                    Log.e("Tag", "imageurl::::::::::" + imageurl);



                    progressBarImageLoad.setVisibility(View.VISIBLE);

                    Picasso.with(getApplicationContext())
                            .load(imageurl)
                            .error(R.drawable.job_detail_icon)      // optional
                            .resize(1112, 640)                                 // optional
                            .into(imageView_event_image, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBarImageLoad.setVisibility(View.GONE);

                                }

                                @Override
                                public void onError() {
                                    progressBarImageLoad.setVisibility(View.GONE);

                                }
                            });



                } else {
                    progressBarImageLoad.setVisibility(View.GONE);
                }


            }
        }

        void showDialog(String lin, Uri uri) {

            DialogFragment newFragment = Fragment_Share.newInstance(lin, uri);
            newFragment.show(fm, "SHARE");
        }
    }
}

