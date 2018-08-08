package main.darkhorse.com.getsportyassisment.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonElement;
import org.json.JSONException;
import org.json.JSONObject;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.model_classes.Signbody;
import main.darkhorse.com.getsportyassisment.morphingbutton.MorphingButton;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ActivityLoginAdmin extends AppCompatActivity {

    NetworkStatus network_status;
    EditText editText_email, editText_password;
    String contact_no;
    Bundle userdetail = new Bundle();
    String data;
    CustomProgress customProgress;
    private MorphingButton btnSignin;
    private int mMorphCounter1 = 1;
    private int mMorphCounter2 = 1;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues() {
        String s1 = editText_email.getText().toString();
        String s2 = editText_password.getText().toString();

        if (s1.equals("") && s2.equals("")) {
            btnSignin.setEnabled(false);
            btnSignin.setBackground(getResources().getDrawable(R.drawable.traning_log_rounded));
        } else if (s1.equals("")) {
            btnSignin.setEnabled(false);
            btnSignin.setBackground(getResources().getDrawable(R.drawable.traning_log_rounded));
        } else if (s2.equals("")) {
            btnSignin.setEnabled(false);
            btnSignin.setBackground(getResources().getDrawable(R.drawable.traning_log_rounded));
        } else {
            btnSignin.setEnabled(true);
            btnSignin.setBackground(getResources().getDrawable(R.drawable.rounded_corner_log));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CheckAndroidPermission.checkAndRequestPermissions(getApplicationContext(),this );

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        intilization();
        TextView tv_forget_password = (TextView) findViewById(R.id.forget_password);
        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // forgetPassword();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callwebservice();


            }
        });

        morphToSquare(btnSignin, 0);


    }

    private void onMorphButton1Clicked(final MorphingButton btnMorph) {
        if (mMorphCounter1 == 0) {
            mMorphCounter1++;
            morphToSquare(btnMorph, integer(R.integer.mb_animation));
        } else if (mMorphCounter1 == 1) {
            mMorphCounter1 = 0;

            morphToSuccess(btnMorph);
        }
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration);
        btnMorph.morph(square);
    }

    private void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .width(dimen(R.dimen.mb_width_120))
                .height(dimen(R.dimen.mb_height_56))
                .icon(R.mipmap.ic_done)
                .text("Login Success");
        btnMorph.morph(square);


    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }




    @Override
    public void onBackPressed() {
        Intent main = new Intent(Intent.ACTION_MAIN);
        main.addCategory(Intent.CATEGORY_HOME);
        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(main);
        finish();
    }


    public void intilization() {
        customProgress = CustomProgress.getInstance();
        btnSignin = (MorphingButton) findViewById(R.id.signin);
        network_status = new NetworkStatus(this);
        editText_email = (EditText) findViewById(R.id.ed_email_id);
        editText_password = (EditText) findViewById(R.id.ed_password);
        // set listeners
        editText_email.addTextChangedListener(mTextWatcher);
        editText_password.addTextChangedListener(mTextWatcher);
        // run once to disable if empty
        checkFieldsForEmptyValues();


    }


    public void callwebservice() {
        SharedPreferences sharedPreferences_user = getSharedPreferences("Token", 0);
        String tokin = sharedPreferences_user.getString("tokin", "");

//        InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//

        if (network_status.isConnectingToInternet())
        {
            Retrofit retrofit = ApiClient.getClient();
            ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
            Call<JsonElement> checklogin = apiCall.getsportysignin("admin_login", new Signbody(editText_email.getText().toString(), editText_password.getText().toString()
                    , tokin));
            Log.d("Resourse listing url:::", checklogin.request().url().toString());

            customProgress.showProgress(ActivityLoginAdmin.this, false);
            checklogin.enqueue(new retrofit2.Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if (response.body().toString() != null) {
                        customProgress.hideProgress();
                        JsonElement jsonElement = response.body();
                        Log.e("JSON RESPONSE", jsonElement.toString());
                           afterresponse(jsonElement.toString());

                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    customProgress.hideProgress();
                }
            });


        } else {
            Toast.makeText(ActivityLoginAdmin.this, "No internet connection", Toast.LENGTH_LONG).show();
        }


    }

    void afterresponse(String response)
    {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equals("1"))
            {
                JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                String user_id = jsonObject2.getString("adminid");
                String name = jsonObject2.getString("name");
                String email = jsonObject2.getString("email");
                contact_no = jsonObject2.getString("contact_no");
                String gender = jsonObject2.getString("gender");
                String forget_code = jsonObject2.getString("address1");
                String dob = jsonObject2.getString("dob");
                String image = jsonObject2.getString("user_image");
                String location = jsonObject2.getString("location");
                final String userType= jsonObject2.getString("userType");
                SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor_login = sharedPreferences.edit();
                editor_login.putBoolean("login", true);
                editor_login.putString("userType",userType);
                editor_login.commit();

                SharedPreferences.Editor editor = getSharedPreferences("Dashboard_prefs", MODE_PRIVATE).edit();
//                editor.putString("Dashboard_type", prof_id);
                editor.putString("user_id", user_id);
                editor.putString("name", name);
                editor.putString("image", image);
                editor.putString("contact_no", contact_no);
                editor.putString("location", location);
                editor.putString("email", email);
                editor.putString("gender", gender);
                editor.putString("dob", dob);
                editor.putString("userType",userType);


                editor.commit();
                SharedPreferences sharedPreferences1 = getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.putString(SharedPrefs.USERID, user_id);
                editor1.commit();
                onMorphButton1Clicked(btnSignin);

                Thread background = new Thread() {
                    public void run() {
                        try {

                            sleep(2 * 500);

                            if(userType.equals("102")) {
                                Intent i = new Intent(ActivityLoginAdmin.this, Activity_dashboard.class);
                                startActivity(i);
                                finish();

                            }else if(userType.equals("103")){
                                Intent i = new Intent(ActivityLoginAdmin.this, AmDashboardActivity.class);
                                startActivity(i);
                                finish();
                            }

                        } catch (Exception e) {
                        }
                    }
                };
                background.start();


            } else {

                Toast.makeText(this, (getString(R.string.login_validation)), Toast.LENGTH_LONG).show();
//                editText_email.setBackgroundResource(R.drawable.signuptextbox_error);
//                editText_password.setBackgroundResource(R.drawable.signuptextbox_error);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    public void forgetPassword() {
//        final Dialog dialog = new Dialog(ActivityLoginAdmin.this);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        dialog.setContentView(R.layout.forget_password_dialog);
//        final MyToast toast = new MyToast();
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dialog_alert_background)));
//        final EditText emial_id = (EditText) dialog.findViewById(R.id.enter_email);
//
//        final TextView textView_close = (TextView) dialog.findViewById(R.id.close);
//        final Button button_submit = (Button) dialog.findViewById(R.id.submit);
//        final ImageView back_button = (ImageView) dialog.findViewById(R.id.back_button);
//        dialog.show();
//
//        button_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emial_id.getText().toString();
//
//                if (email.equals("")) {
//                    Toast.makeText(ActivityLoginAdmin.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
//
//                } else {
//
//
//                    try {
//                        data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("forget_pass", "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    Retrofit retrofit = ApiClient.getClient();
//                    ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
//
//                    Call<JsonElement> forget_password = apiCall.ForgetPassword(MainUrls.login_url + "?" + data);
//
//                    Log.d("forget_password  url:::", forget_password.request().url().toString());
//
//
//                    customProgress.showProgress(ActivityLoginAdmin.this, false);
//                    Log.e("well", forget_password.request().url().toString());
//                    forget_password.enqueue(new Callback<JsonElement>() {
//                        @Override
//                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response)
//                        {
//                            customProgress.hideProgress();
////                            if (response.body().getStatus().equals("1"))
////                            {
////
////
////                                Toast.makeText(ActivityLoginAdmin.this, getString(R.string.check_emailid), Toast.LENGTH_SHORT).show();
////
////
////
////
////                            } else {
////
////
////                                Toast.makeText(ActivityLoginAdmin.this, getString(R.string.email_notexist), Toast.LENGTH_SHORT).show();
////
////
////                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<JsonElement> call, Throwable t) {
//                            customProgress.hideProgress();
//                        }
//                    });
//                    dialog.dismiss();
//                }
//
//            }
//        });
//        back_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog.dismiss();
//            }
//        });
//
//
//    }


}



