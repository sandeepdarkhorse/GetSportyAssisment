package main.darkhorse.com.getsportyassisment.performance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;

public class PerformanceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_performance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CheckAndroidPermission.checkAndRequestPermissions(getApplicationContext(),this );
        //toolbar.setTitle(getResources().getString(R.string.performance));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();

        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.PERFORMANCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPrefs.DOB,bundle.getString("dob",""));
        editor.putString(SharedPrefs.GENDER,bundle.getString("gender",""));
        editor.putString(SharedPrefs.SPORT, bundle.getString("sport",""));
        editor.putString(SharedPrefs.STUDENTID, bundle.getString("studentid"));
        String dob=(bundle.getString("dob",""));
        Log.e("Tag","data in shared pref:: "+bundle.getString("dob","")+bundle.getString("sport","")+bundle.getString("studentid"));

        editor.commit();

        ImageView imageViewGuideLines = (ImageView)toolbar.findViewById(R.id.guidelines);
        imageViewGuideLines.setOnClickListener(PerformanceActivity.this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,new PerformanceAssessmentFragment()).addToBackStack("performance_listing").commit();

    }



    @Override
    public void onClick(View view) {


        switch (view.getId())
        {
            case R.id.back:

                onBackPressed();
                break;
            case R.id.guidelines:

                GuidelinesDialog.guideLinesPopup(PerformanceActivity.this);
                break;
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount()==1) {
            finish();
        }
        else
        {
            fragmentManager.popBackStackImmediate();
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



}


