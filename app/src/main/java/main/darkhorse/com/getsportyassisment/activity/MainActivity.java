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






}

