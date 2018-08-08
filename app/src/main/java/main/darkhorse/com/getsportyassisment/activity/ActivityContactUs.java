package main.darkhorse.com.getsportyassisment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityContactUs extends AppCompatActivity {
    private final String EMAIL = "Info@getSporty.in";
    Toolbar toolbar;
    ImageView imageView_back;
    TextView textView_title;
    private Button sendMail;
    private EditText bodyMail;
    private TextView textView_line1;
    private TextView textView_line2,textView_line3,textView_line4,textView_line5,textView_line6;
    private TextView contactUs;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        CheckAndroidPermission.checkAndRequestPermissions(getApplicationContext(),this );
        instantiation();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Contact Us");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bodyMail = findViewById(R.id.body_of_mail);
        contactUs = findViewById(R.id.contactUs);
        sendMail = findViewById(R.id.send_mail);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bodyMail.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), R.string.enter_message_for_contact, Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("vnd.android.cursor.dir/email");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                            EMAIL});
                    intent.putExtra(Intent.EXTRA_TEXT, bodyMail.getText().toString());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Query");
                    startActivity(intent);
                }
            }
        });


    }


    private void instantiation() {

        textView_line1 = findViewById(R.id.text_line_1);
        textView_line2 = findViewById(R.id.text_line_2);
        textView_line3 = findViewById(R.id.text_line_3);
        textView_line4 = findViewById(R.id.text_line_4);
        textView_line5 =  findViewById(R.id.text_line_5);
        textView_line6 =  findViewById(R.id.text_line_6);


    }

    @Override
    public void onBackPressed() {
//        Intent i = new Intent(ActivityContactUs.this, MainActivity.class);
//        startActivity(i);
//        finish();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

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
}
