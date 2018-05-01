package main.darkhorse.com.getsportyassisment.performance;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sandeepsingh on 20/4/18.
 */

public class GuidelinesDialog {
    public static void guideLinesPopup(Context context) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Holo_Light_NoActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.guidelines_layout);

//        final Typeface typeface_titillium_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Titillium-Regular.otf");

        TextView textViewHeading = (TextView)dialog.findViewById(R.id.heading);
        TextView textViewClose = (TextView)dialog.findViewById(R.id.close);
        final TextView textViewTitle = (TextView)dialog.findViewById(R.id.title_text);

//        textViewHeading.setTypeface(typeface_titillium_regular);
//        textViewTitle.setTypeface(typeface_titillium_regular);

        Retrofit retrofit = ApiClient.getClientLocal();
        PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefs.PERFORMANCE, Context.MODE_PRIVATE);
        Call<ViewGuidelinesResponse> viewGuidelinesResponseCall = apiCall.viewGuidelinesRequest("view_guidelines", new ViewGuidelinesRequest(sharedPreferences.getString(SharedPrefs.DOB,""),sharedPreferences.getString(SharedPrefs.SPORT,""),sharedPreferences.getString(SharedPrefs.GENDER,"")));
        Log.e("publish url", viewGuidelinesResponseCall.request().url().toString());
        viewGuidelinesResponseCall.enqueue(new Callback<ViewGuidelinesResponse>() {
            @Override
            public void onResponse(Call<ViewGuidelinesResponse> call, Response<ViewGuidelinesResponse> response) {
                Log.e("Tag",response.message());
                Log.e("Tag","status = "+response.body().getStatus());
                Log.e("Tag", response.body().getMsg());
                Log.e("Tag",response.body().getData().toString());
                if (response.body().getStatus()==1) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().getData().toString());
                        //JSONObject jsonObject1 = jsonObject.getJSONObject(JsonKeys.GUIDELINES);
                        JSONArray jsonArray = new JSONArray(jsonObject.getString(JsonKeys.GUIDELINES));
                        String guidelines = "\n";
                        for (int i = 0; i < jsonArray.length(); i++) {
                            guidelines = guidelines + jsonArray.getString(i) + "\n\n";
                        }
                        textViewTitle.setText(guidelines);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    textViewTitle.setText("No guidelines available");
                }
            }
            @Override
            public void onFailure(Call<ViewGuidelinesResponse> call, Throwable t) {
                Log.e("Tag",t.toString());
            }
        });
        textViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
