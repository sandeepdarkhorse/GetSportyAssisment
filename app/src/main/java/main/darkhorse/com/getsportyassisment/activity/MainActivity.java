package main.darkhorse.com.getsportyassisment.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.Serializable;
import java.util.ArrayList;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.model_classes.PlacesSportsdetail;
import main.darkhorse.com.getsportyassisment.model_classes.sportspojo;

import static main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress.customProgress;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    String user_id;
    ArrayList<sportspojo> sportfacilitylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckAndroidPermission.checkAndRequestPermissions(getApplicationContext(), this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //       setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        customProgress = CustomProgress.getInstance();


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

        CardView cricketcardView = (CardView) findViewById(R.id.cricket_cardView);
        CardView footballcardView = (CardView) findViewById(R.id.football_cardView);
        CardView tenniscardView = (CardView) findViewById(R.id.tennis_cardView);
        CardView swimmingcardView = (CardView) findViewById(R.id.swimming_cardView);
        CardView baseballcardView = (CardView) findViewById(R.id.baseball_cardView);
        CardView badmintoncardView = (CardView) findViewById(R.id.badminton_cardView);
        cricketcardView.setOnClickListener(this);
        footballcardView.setOnClickListener(this);
        tenniscardView.setOnClickListener(this);
        swimmingcardView.setOnClickListener(this);
        baseballcardView.setOnClickListener(this);
        badmintoncardView.setOnClickListener(this);
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
            Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intentAbout);

        } else if (id == R.id.nav_contact) {
            Intent intentAbout = new Intent(MainActivity.this, ActivityContactUs.class);
            startActivity(intentAbout);


        } else if (id == R.id.nav_logout) {
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

            Intent main = new Intent(MainActivity.this, ActivityLoginAdmin.class);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(main);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cricket_cardView:
                show_activity(R.id.cricket,R.id.cricket_text,sportfacilitylist.get(0).getName(), sportfacilitylist.get(0).getDrawable_id());
                break;
            case R.id.football_cardView:
                show_activity(R.id.footbal,R.id.footbal_text,sportfacilitylist.get(1).getName(), sportfacilitylist.get(1).getDrawable_id());

                break;
            case R.id.tennis_cardView:
                show_activity(R.id.tennis,R.id.tennis_text,sportfacilitylist.get(2).getName(), sportfacilitylist.get(2).getDrawable_id());

                break;
            case R.id.swimming_cardView:
                show_activity(R.id.swimming,R.id.swimming_text,sportfacilitylist.get(3).getName(), sportfacilitylist.get(3).getDrawable_id());

                break;
            case R.id.baseball_cardView:
                show_activity(R.id.baseball,R.id.baseball_text,sportfacilitylist.get(4).getName(), sportfacilitylist.get(4).getDrawable_id());

                break;
            case R.id.badminton_cardView:
                show_activity(R.id.badminton,R.id.badminton_text,sportfacilitylist.get(5).getName(), sportfacilitylist.get(5).getDrawable_id());

                break;

        }
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme);
            alertDialog.setTitle(Html.fromHtml("<font color='#ffffff'>Quit GetSporty Assisment ?</font>"));
            alertDialog.setMessage(Html.fromHtml("<font color='#ffffff'>Do you want to exit?</font>"));

            // Setting Icon to Dialog
            alertDialog.setIcon(R.mipmap.ic_launcher);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton(Html.fromHtml("<font color='#ffffff'>YES</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    Intent main = new Intent(Intent.ACTION_MAIN);
                    main.addCategory(Intent.CATEGORY_HOME);
                    main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(main);
                    finish();
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton(Html.fromHtml("<font color='#ffffff'>NO</font>"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }


    }
    @SuppressLint("NewApi")
 public  void show_activity(int imageview,int textview,String name,int drawable_id)
 {
     PlacesSportsdetail places = new PlacesSportsdetail(name,
             drawable_id, "",
             "", "", "", "");

     Bundle userinfo = new Bundle();
     userinfo.putSerializable("sportdetail", (Serializable) places);
     Intent i = new Intent(new Intent(MainActivity.this, ActivityDashboardDetail.class));
     i.putExtras(userinfo);

      ActivityOptions options = ActivityOptions
             .makeSceneTransitionAnimation(MainActivity.this,
                     Pair.create(findViewById(imageview), "image_transition"),
                     Pair.create(findViewById(textview), "sport_transition"));
     startActivity(i, options.toBundle());

     }
}

