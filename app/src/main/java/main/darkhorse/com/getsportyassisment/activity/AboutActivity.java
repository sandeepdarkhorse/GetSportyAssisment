package main.darkhorse.com.getsportyassisment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomDialogNoInternet;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AboutActivity extends AppCompatActivity implements CustomDialogNoInternet.myOnClickListener{

    Toolbar toolbar;
    private TextView headingAbout;
    private TextView bodyAbout;
    private ImageView backButton;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("About Us");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        headingAbout = findViewById(R.id.about_app);
        bodyAbout =  findViewById(R.id.body_about);
        textView1 = findViewById(R.id.text_contact_us_1);
        textView2 =  findViewById(R.id.text_contact_us_2);
        textView3 = findViewById(R.id.text_contact_us_3);
        textView4 =  findViewById(R.id.text_contact_us_4);
        textView5 =  findViewById(R.id.text_contact_us_5);
        textView6 =  findViewById(R.id.text_contact_us_6);
        textView7 =  findViewById(R.id.text_contact_us_7);
        textView8 =  findViewById(R.id.text_contact_us_8);


    }


    @Override
    public void onBackPressed() {
//        Intent i = new Intent(AboutActivity.this, MainActivity.class);
//        startActivity(i);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
    public void onButtonClick() {

    }
}
