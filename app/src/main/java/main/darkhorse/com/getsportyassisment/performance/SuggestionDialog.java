package main.darkhorse.com.getsportyassisment.performance;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.MyToast;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sandeepsingh on 23/4/18.
 */

public class SuggestionDialog {
    public static void suggestionPopup(final Context context, final String module) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.suggestion_layout);

        // final Typeface typeface_titillium_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Titillium-Regular.otf");

        TextView textViewHeading = (TextView)dialog.findViewById(R.id.heading);
        TextView textViewClose = (TextView)dialog.findViewById(R.id.close);
        final TextView textViewTitle = (TextView)dialog.findViewById(R.id.title_text);
        final TextView textViewDescription = (TextView)dialog.findViewById(R.id.description_text);

        final TextInputEditText textInputEditTextTitle = (TextInputEditText)dialog.findViewById(R.id.title);
        final TextInputEditText textInputEditTextDescription = (TextInputEditText)dialog.findViewById(R.id.description);

        Button buttonSubmit = (Button)dialog.findViewById(R.id.submit);


//        textViewHeading.setTypeface(typeface_titillium_regular);
//        textViewTitle.setTypeface(typeface_titillium_regular);
//        textViewDescription.setTypeface(typeface_titillium_regular);
//        textInputEditTextTitle.setTypeface(typeface_titillium_regular);
//        textInputEditTextDescription.setTypeface(typeface_titillium_regular);

        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = ApiClient.getClientLocal();
                PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);

                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefs.PERFORMANCE, Context.MODE_PRIVATE);
                SharedPreferences sharedPreferences1 = context.getSharedPreferences(SharedPrefs.DASHBOARD_PREFS, Context.MODE_PRIVATE);
                Call<SuggestionResponse> suggestionResponseCall = apiCall.suggestionRequest("save_suggestion", new SuggestionRequest(sharedPreferences1.getString("user_id",""),textInputEditTextTitle.getText().toString(),textInputEditTextDescription.getText().toString(),module,sharedPreferences.getString(SharedPrefs.GENDER,""),sharedPreferences.getString(SharedPrefs.DOB,""),sharedPreferences.getString(SharedPrefs.SPORT,"")));
                Log.e("publish url", suggestionResponseCall.request().url().toString());
                suggestionResponseCall.enqueue(new Callback<SuggestionResponse>() {
                    @Override
                    public void onResponse(Call<SuggestionResponse> call, Response<SuggestionResponse> response) {
                        Log.e("Tag",response.message());
                        Log.e("Tag","status = "+response.body().getStatus());
                        Log.e("Tag", response.body().getMsg());
                        Log.e("Tag","data = "+response.body().getData());

                        MyToast.show(context,response.body().getMsg(),true);

                    }
                    @Override
                    public void onFailure(Call<SuggestionResponse> call, Throwable t) {
                        Log.e("Tag",t.toString());
                    }
                });

                dialog.dismiss();
            }
        });



        dialog.show();






    }
}
