package main.darkhorse.com.getsportyassisment.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
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
import main.darkhorse.com.getsportyassisment.model_classes.PlacesSportsdetail;
import main.darkhorse.com.getsportyassisment.model_classes.TournamentListingResponse;
import main.darkhorse.com.getsportyassisment.model_classes.TournamentListingResponseItem;
import main.darkhorse.com.getsportyassisment.model_classes.sportspojo;
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


    ArrayList<sportspojo> sportfacilitylist;
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

    private void initView()
    {
        sportfacilitylist = new ArrayList<sportspojo>();
        sportspojo s0 = new sportspojo("Cricket", R.mipmap.cricket);
        sportspojo s1 = new sportspojo("Football", R.mipmap.football);
        sportspojo s2 = new sportspojo("Lawn tennis", R.mipmap.tennis);
        sportspojo s3 = new sportspojo("Swimming", R.mipmap.swimming);
        sportspojo s4 = new sportspojo("Baseball", R.mipmap.baseball);
        sportspojo s5 = new sportspojo("Badminton", R.mipmap.badminton);
        sportfacilitylist.add(0, s0);
        sportfacilitylist.add(1, s1);
        sportfacilitylist.add(2, s2);
        sportfacilitylist.add(3, s3);
        sportfacilitylist.add(4, s4);
        sportfacilitylist.add(5, s5);

//
//        ArrayList<Integer> imageresourse = new ArrayList<Integer>();
//        imageresourse.add(0, R.mipmap.cricket);
//        imageresourse.add(1, R.mipmap.football);
//        imageresourse.add(2, R.mipmap.hockey);
//        imageresourse.add(3, R.mipmap.table_tennis);
//        imageresourse.add(4, R.mipmap.tennis);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_sport);
        GridLayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(MainActivity.this, R.dimen.recycleview_space);
        mRecyclerView.addItemDecoration(itemDecoration);
        // Initialize a new instance of RecyclerView Adapter instance
        sportsfacilityadapter mAdapter = new sportsfacilityadapter(sportfacilitylist);
        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
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



    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {

            mItemOffset = itemOffset;

        }


        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {

            this(context.getResources().getDimensionPixelSize(itemOffsetId));

        }

        @Override

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,

                                   RecyclerView.State state) {

            super.getItemOffsets(outRect, view, parent, state);

            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);

        }

    }
    @SuppressLint("NewApi")
    public class sportsfacilityadapter extends RecyclerView.Adapter<sportsfacilityadapter.ViewHolder> {

        ImageView sport_image;
        TextView sport_name;
        View rootView;
        ArrayList<sportspojo> sportfacilitylist = new ArrayList<sportspojo>();
        private View rootview;
        sportsfacilityadapter(
                ArrayList<sportspojo> sportfacilitylist) {
            this.sportfacilitylist = sportfacilitylist;

        }

        @Override
        public sportsfacilityadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.academy_item_view, parent, false);
            return new sportsfacilityadapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(sportsfacilityadapter.ViewHolder holder, int position) {



            holder.setItem(sportfacilitylist.get(position));


        }

        @Override
        public int getItemCount() {
            return sportfacilitylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            public ViewHolder(final View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int position=getAdapterPosition();
                        PlacesSportsdetail places=new PlacesSportsdetail(sportfacilitylist.get(position).getName(),
                                sportfacilitylist.get(position).getDrawable_id(),"",
                                "4 Hard,2 Clay","5.00 AM -12 PM","Rs. 2000 Monthly","Yes");

                        Bundle userinfo = new Bundle();
                        userinfo.putSerializable("sportdetail", (Serializable) places);
                        Intent i = new Intent(new Intent(MainActivity.this, ActivityPlacesSportDetail.class));
                        i.putExtras(userinfo);

                        ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(MainActivity.this,
                                        Pair.create(view.findViewById(R.id.sport_image),"image_transition"),
                                        Pair.create(view.findViewById(R.id.sport_text), "sport_transition"));
                        startActivity(i, options.toBundle());



                    }
                });
                sport_image = (ImageView) itemView.findViewById(R.id.sport_image);
                sport_name = (TextView) itemView.findViewById(R.id.sport_text);

            }

            public void setItem(sportspojo s)

            {
                sport_name.setText(s.getName());
                sport_image.setImageResource(s.getDrawable_id());

            }

        }
    }




}

