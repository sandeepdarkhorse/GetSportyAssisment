package main.darkhorse.com.getsportyassisment.activity;

import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.shapesview.DiagonalView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



/**
 * Created by shekhar on 27/7/18.
 */
public class Activity_dashboard extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    static CustomProgress customProgress;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_assistmanager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //       setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        customProgress = CustomProgress.getInstance();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Activity_dashboard.this);

        initView();
    }

    private void initView() {

        DiagonalView institutioncardView = (DiagonalView) findViewById(R.id.institution_cardView);
        DiagonalView selfassessmentcardView = (DiagonalView) findViewById(R.id.selfassessment_cardView);
        DiagonalView eventrtrielcardView = (DiagonalView) findViewById(R.id.eventrtriel_cardView);
        DiagonalView assessmentmasterCardView = (DiagonalView) findViewById(R.id.assessmentmaster_cardView);

        institutioncardView.setOnClickListener(this);
        selfassessmentcardView.setOnClickListener(this);
        eventrtrielcardView.setOnClickListener(this);
        assessmentmasterCardView.setOnClickListener(this);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.institution_cardView:
                Intent activitylist = new Intent(Activity_dashboard.this, ActivityList.class);
                startActivity(activitylist);
                break;
            case R.id.selfassessment_cardView:
                break;
            case R.id.eventrtriel_cardView:
                break;
            case R.id.assessmentmaster_cardView:
                break;

        }
    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onRestart() {
        super.onRestart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
            Intent intentAbout = new Intent(Activity_dashboard.this, AboutActivity.class);
            startActivity(intentAbout);

        } else if (id == R.id.nav_contact) {
            Intent intentAbout = new Intent(Activity_dashboard.this, ActivityContactUs.class);
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

            Intent main = new Intent(Activity_dashboard.this, ActivityLoginAdmin.class);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(main);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_dashboard.this, R.style.MyDialogTheme);
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


}
