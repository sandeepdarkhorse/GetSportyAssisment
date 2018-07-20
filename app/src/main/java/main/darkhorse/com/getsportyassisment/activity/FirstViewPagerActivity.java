package main.darkhorse.com.getsportyassisment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonElement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.UtilsFile.GoogleClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import main.darkhorse.com.getsportyassisment.fragment.FifthFragment;
import main.darkhorse.com.getsportyassisment.fragment.FourthFragment;
import main.darkhorse.com.getsportyassisment.fragment.OneFragment;
import main.darkhorse.com.getsportyassisment.fragment.ThreeFragment;
import main.darkhorse.com.getsportyassisment.fragment.TwoFragment;
import main.darkhorse.com.getsportyassisment.model_classes.FacebookDataItem;
import main.darkhorse.com.getsportyassisment.model_classes.FacebookDataPojo;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstViewPagerActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 9001;
    ViewPager viewpager;
    Custom_pager_Adapter custom_pager_adapter;
    private NetworkStatus network_status;
    GoogleClient googleclient = GoogleClient.getInstance();
    CallbackManager callbackManager;
    String name = "";
    String email = "";

    private String password;
    private String profilePic = "";
    private Uri profilePictureUri;
    private String dateToServer;
    ProgressDialog progressDialog;
    Context context;
    private String facebook_id;
    private String gender;
    String device_token;
    List<String> str_permission;

    RelativeLayout google_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckAndroidPermission.checkAndRequestPermissions(getApplicationContext(),this );

        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context=getApplicationContext();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.slider_view_pager);
        viewpager = (ViewPager) findViewById(R.id.pager);
        progressDialog = new ProgressDialog(FirstViewPagerActivity.this);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        RelativeLayout fbLogin = (RelativeLayout) findViewById(R.id.facebook_login);
        RelativeLayout gLogin = (RelativeLayout) findViewById(R.id.google_signin);

        custom_pager_adapter = new Custom_pager_Adapter(getSupportFragmentManager(), this);
        viewpager.setAdapter(custom_pager_adapter);
        viewpager.setCurrentItem(0);
        indicator.setViewPager(viewpager);

        fbLogin.setOnClickListener(this);
        gLogin.setOnClickListener(this);

        googleclient.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleclient.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleclient.gso)
                .build();


        if (!googleclient.mGoogleApiClient.isConnected())
            googleclient.mGoogleApiClient.connect();
        profilePictureUri = Uri.EMPTY;




//        Professionallistdata = new ArrayList<>();
//        professionalname = new ArrayList<>();
//        list_sports = new ArrayList<String>();
        network_status = new NetworkStatus(this);
        profilePictureUri = Uri.EMPTY;
        str_permission = new ArrayList<String>();
        str_permission.add("public_profile");
        str_permission.add("email");

       // ButterKnife.inject(this);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final String accessToken = loginResult.getAccessToken().getToken();


                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {


                                // Application code
                                if (network_status.isConnectingToInternet()) {
                                    try {
                                        facebook_id = jsonObject.getString("id");
                                        name = jsonObject.getString("name");
                                        if (jsonObject.has("email")) {
                                            email = jsonObject.getString("email");
                                        }
                                        if (jsonObject.has("gender")) {
                                            gender = jsonObject.getString("gender");
                                            gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);

                                        }



                                        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();

                                        if (Profile.getCurrentProfile() != null && Profile.getCurrentProfile().getProfilePictureUri(100, 100) != null) {
                                            profilePictureUri = Profile.getCurrentProfile().getProfilePictureUri(100, 100);
                                        } else {
                                            profilePictureUri = Uri.EMPTY;
                                        }
                                        if (profilePictureUri != null) {
                                            profilePic = profilePictureUri.toString();
                                        } else {
                                        }

                                        FacebookDataItem dataitem = new FacebookDataItem(facebook_id, email, name, profilePic);
                                        FacebookDataPojo datapojo = new FacebookDataPojo("M", email, dataitem, "", "1", "103", device_token);


                                        checklogin(datapojo, "1");
                                        // registration(2);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.no_internet_connection_error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,cover,email,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (googleclient.mGoogleApiClient.isConnected())
            googleclient.mGoogleApiClient.disconnect();
    }
    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.facebook_login:
//                Intent loginIntent=  new Intent(FirstViewPagerActivity.this,MainActivity.class);
//                startActivity(loginIntent);

                try {

                    AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();

                    Profile currentProfile = Profile.getCurrentProfile();

                    LoginManager.getInstance().logInWithReadPermissions(FirstViewPagerActivity.this, str_permission);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.google_signin:
                if (network_status.isConnectingToInternet())
                {

                    try {
                        signInGoogle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet_connection_error, Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess())
        {
            // Signed in successfully, show authenticated UI.
            try {
                GoogleSignInAccount acct = result.getSignInAccount();
                //firebaseAuthWithGoogle();
                assert acct != null;
                name = acct.getDisplayName();
                email = acct.getEmail();
                password = acct.getEmail();

                profilePictureUri = acct.getPhotoUrl();
                if (profilePictureUri != null) {
                    profilePic = profilePictureUri.toString();

                } else {
                    profilePic = "";
                }
                FacebookDataItem dataitem = new FacebookDataItem("facebook_id", email, name, profilePic);
                FacebookDataPojo datapojo = new FacebookDataPojo("M", email, dataitem, "", "2", "103", "device_token");

                checklogin(datapojo, "2");

            } catch (Exception e)
            { e.printStackTrace();
            Toast.makeText(this,"Exception:::::"+e.toString(),Toast.LENGTH_SHORT).show();
            }


        }

    }

    //google sign in
    private void signInGoogle() {
        googleclient.mGoogleApiClient.clearDefaultAccountAndReconnect();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleclient.mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void checklogin(FacebookDataPojo datapojo, final String login_type) {

    Retrofit retrofit;
    ApiAtheliteCall apiCall;
    retrofit = ApiClient.getClient();
    apiCall = retrofit.create(ApiAtheliteCall.class);
    Call<JsonElement> checklogin = apiCall.facebookLogin("gs_login", datapojo);
        Log.d("gs_login", checklogin.request().url().toString());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        checklogin.enqueue(new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            progressDialog.dismiss();
            JsonElement jsonElement = response.body();
            Log.e("JSON RESPONSE Gs_login", jsonElement.toString());
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonElement.toString());
                String status = jsonObject.getString("status");
                JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                String prof_id = jsonObject2.getString("prof_id");
                String userid = jsonObject2.getString("userid");
                String email_id = jsonObject2.getString("email");
                if (status.equals("1")) {

                    if (prof_id.equals("")) {
  //                      Signup_Dialog("1", login_type, userid);
                        Toast.makeText(FirstViewPagerActivity.this, "You are not a registered user.", Toast.LENGTH_LONG).show();

//
                    } else if (prof_id.equals("1")) {
                       // Toast.makeText(FirstViewPagerActivity.this, "in first condition.", Toast.LENGTH_LONG).show();
                        //  LoginToActovity(jsonElement.toString());
                    } else {
                        LoginToActovity(jsonElement.toString());
                    }

                } else if (status.equals("2")) {

                    if (email_id.equals("")) {
 //                       Signup_Dialog("2", login_type, userid);
                        Toast.makeText(FirstViewPagerActivity.this, "You are not a registered user.", Toast.LENGTH_LONG).show();


                    } else {
                        LoginToActovity(jsonElement.toString());
                    }

                } else if (status.equals("3")) {
                   // Signup_Dialog("3", login_type, userid);
                    Toast.makeText(FirstViewPagerActivity.this, "You are not a registered user.", Toast.LENGTH_LONG).show();

                } else {

                  //  Toast.makeText(FirstScreen.this, (getString(R.string.credential_notupdate)), Toast.LENGTH_LONG).show();
                    Toast.makeText(FirstViewPagerActivity.this, "You are not a registered user.", Toast.LENGTH_LONG).show();


                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
            progressDialog.dismiss();
        }
    });
}


    void LoginToActovity(String response) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonObject2 = jsonObject.getJSONObject("data");
            String user_id = jsonObject2.getString("userid");
            String name = jsonObject2.getString("name");
            String prof_id = jsonObject2.getString("prof_id");
            String image = jsonObject2.getString("user_image");
            String phone_number = jsonObject2.getString("contact_no");
            String location = jsonObject2.getString("location");
            String sport = jsonObject2.getString("sport");
            String email = jsonObject2.getString("email");
            String gender = jsonObject2.getString("gender");
            String dob = jsonObject2.getString("dob");
            String prof_name = jsonObject2.getString("prof_name");
            SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor editor_login = sharedPreferences.edit();
            editor_login.putBoolean("login", true);
            editor_login.commit();
            SharedPreferences.Editor editor = getSharedPreferences("Dashboard_prefs", MODE_PRIVATE).edit();
            editor.putString("Dashboard_type", prof_id);
            editor.putString("user_id", user_id);
            editor.putString("name", name);
            editor.putString("image", image);
            editor.putString("contact_no", phone_number);
            editor.putString("location", location);
            editor.putString("sport", sport);
            editor.putString("prof_id", prof_id);
            editor.putString("email", email);
            editor.putString("gender", gender);
            editor.putString("dob", dob);
            editor.putString("prof_name", prof_name);
            editor.commit();
            SharedPreferences sharedPreferences1 = getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            editor1.putString(SharedPrefs.USERID, user_id);
            editor1.commit();
            finish();
            Intent i = new Intent(FirstViewPagerActivity.this, MainActivity.class);
            startActivity(i);
            finish();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public class Custom_pager_Adapter extends FragmentPagerAdapter {
        private Context context;
        private int total = 5;

        public Custom_pager_Adapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();
            switch (position) {
                case 0:
                    f = new OneFragment();
                    break;
                case 1:
                    f = new TwoFragment();
                    break;
                case 2:
                    f = new ThreeFragment();
                    break;
                case 3:
                    f = new FourthFragment();
                    break;
                case 4:
                    f = new FifthFragment();
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            return total;
        }
    }
}
