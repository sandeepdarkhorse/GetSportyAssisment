package main.darkhorse.com.getsportyassisment.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.Serializable;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.fragment.CreateTournament;
import main.darkhorse.com.getsportyassisment.model_classes.MyTournamentDataModel;

public class EventDetailActivity extends AppCompatActivity implements Serializable {
    public static MyTournamentDataModel tournamentdata_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Detail Tournament");

        Bundle userinfo = getIntent().getExtras();
        if (userinfo != null) {
            tournamentdata_item = (MyTournamentDataModel) getIntent().getSerializableExtra("tournamentdata");
            // tournamentdata_item = (MyTournamentDataModel) userinfo.getSerializable("tournamentdata");
            String name =userinfo.getString("name");
            getSupportActionBar().setTitle(name);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new CreateTournament().newInstance(tournamentdata_item)).addToBackStack("edit_tournament").commit();


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


//        Intent i = new Intent(ActivityEventEdit.this, BaseActivity.class);
////        i.putExtra("mycreation", "1");
//        startActivity(i);
        finish();


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
