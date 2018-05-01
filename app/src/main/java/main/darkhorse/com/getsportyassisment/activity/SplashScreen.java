package main.darkhorse.com.getsportyassisment.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import main.darkhorse.com.getsportyassisment.R;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;
    boolean logged_in = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        showSplash();
    }

    private void showSplash() {

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run() {

                Thread background = new Thread()
                {
                    public void run() {
                        try {



                            sleep(2 * 1000);

                            SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
                            boolean login = sharedPreferences.getBoolean("login", false);
                            if (login == false) {

//                            Intent i = new Intent(ActivitySplashScreen.this, FirstScreen.class);

                                Intent i = new Intent(SplashScreen.this, FirstViewPagerActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }

                        } catch (Exception e) {
                        }
                    }
                };
                background.start();


            }
        }, SPLASH_TIME_OUT);
    }

    private void check_login() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                        //Log.d(LOG_TAG,"onCompleted jsonObject: "+jsonObject);
                        //Log.d(LOG_TAG,"onCompleted response: "+response);
                        // Application code
                        if (AccessToken.getCurrentAccessToken() != null) {
                            Log.d("token", AccessToken.getCurrentAccessToken().toString());
                            logged_in = true;
                        } else {
                            logged_in = false;
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,cover,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @Override
    protected void onResume() {
        super.onResume();


    }
}
