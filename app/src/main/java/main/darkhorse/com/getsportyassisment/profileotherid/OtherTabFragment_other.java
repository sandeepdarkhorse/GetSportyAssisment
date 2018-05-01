package main.darkhorse.com.getsportyassisment.profileotherid;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

//import darkhorsesports.getsporty.R;
//import darkhorsesports.getsporty.coachprofilemodelclassess.FormalEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.OtherCertificationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.PlayerExperienceData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.SportEducationData;
//import darkhorsesports.getsporty.coachprofilemodelclassess.WorkExperienceData;
//import darkhorsesports.getsporty.customclasses.CustomProgress;
//import darkhorsesports.getsporty.customclasses.DateConversion;
//import darkhorsesports.getsporty.customclasses.MainUrls;
//import darkhorsesports.getsporty.customclasses.MyToast;
//import darkhorsesports.getsporty.customclasses.NetworkStatus;
import fr.ganfra.materialspinner.MaterialSpinner;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.MainUrls;
import main.darkhorse.com.getsportyassisment.UtilsFile.MyToast;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.FormalEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.OtherCertificationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.PlayerExperienceData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.SportEducationData;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.WorkExperienceData;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;

public class OtherTabFragment_other extends Fragment implements ApiDataOtherdata, View.OnClickListener {
    private static final String mainUrl = MainUrls.mainUrl + "?";
    View rootView;
    String user_id;
    CustomProgress customProgress;
    private TextView dob;
    private TextView profession, langKnown, ageGroup, sport, linkToPersonalWeb, gender;
    private ImageView edit;
    private NetworkStatus net;
    private String response;
    private ProgressDialog progressDialog;
    private String langString;
    private String genderString;
    private String linkString;
    private String ageGrpString;
    private String prof_id;
    private String otherUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.tab_others, container, false);
        customProgress = CustomProgress.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Dashboard_prefs", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        net = new NetworkStatus(getActivity());
        LinearLayout layout_sport = (LinearLayout) rootView.findViewById(R.id.layout_sport);
        layout_sport.setVisibility(View.GONE);
        profession = (TextView) rootView.findViewById(R.id.profession);
        dob = (TextView) rootView.findViewById(R.id.dateOfBirth);
        langKnown = (TextView) rootView.findViewById(R.id.languagesKnown);
        ageGroup = (TextView) rootView.findViewById(R.id.ageGroupCoached);
        sport = (TextView) rootView.findViewById(R.id.sport);
        linkToPersonalWeb = (TextView) rootView.findViewById(R.id.linkPersonal);
        gender = (TextView) rootView.findViewById(R.id.gender);
        edit = (ImageView) rootView.findViewById(R.id.other_edit);
        edit.setOnClickListener(this);


        return rootView;


    }


    @Override
    public void getEducationDataAll(ArrayList<SportEducationData> sport, ArrayList<FormalEducationData> formal, ArrayList<OtherCertificationData> other, String userid) {

    }

    @Override
    public void getExperienceDataAll(ArrayList<WorkExperienceData> work, ArrayList<PlayerExperienceData> player, String userid) {

    }


    @Override
    public void getOthersDataAll(String dob, String gender, String prof_name, String sport, String link, String age_group_coached, String languages_known, String userid) {
        if (prof_name != null)
            profession.setText(prof_name);
        else
            profession.setHint("Add profession");

        if ((dob != null)||(dob != "") ) {

            int age = DateConversion.calculateAge(DateConversion.StringtoDate(dob));
            String athelite_age = String.valueOf(age);
            this.dob.setText(athelite_age + " Year");

//            String date = DateConversion.dateconversion(dob);
//            this.dob.setText(date);
        } else {
            this.dob.setHint("Enter dob");
        }


        if (sport != null)
            this.sport.setText(sport);
        else
            this.sport.setHint("Add sport");

        if (link != null) {
            linkToPersonalWeb.setText(link);
            linkString = link;
        } else {
            linkToPersonalWeb.setHint("Add link");
            linkString = "";
        }
        if (age_group_coached != null) {
            ageGroup.setText(age_group_coached);
            ageGrpString = age_group_coached;
        } else {
            ageGroup.setHint("Add age group");
            ageGrpString = "";

        }
        if (languages_known != null) {
            langKnown.setText(languages_known);
            langString = languages_known;
        } else {
            langKnown.setHint("Add languages");
            langString = "";

        }
        if (gender != null) {
            this.gender.setText(gender);
            genderString = gender;
        } else {
            this.gender.setHint("");
            genderString = "";
        }

//        if (!userId.equals(otherUserId)) {
//
//            edit.setVisibility(View.GONE);
//
//        } else {
//            edit.setVisibility(View.VISIBLE);
//        }


        if (!userid.equals(user_id)) {

            edit.setVisibility(View.GONE);


        } else {
            edit.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.other_edit:
                editDetailsDialog();
        }
    }

    private void editDetailsDialog() {


        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.coach_profile_others_edit);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.back_color_serach_spinner)));
        final ArrayList<String> arrayList_gender = new ArrayList<String>();
        arrayList_gender.add("Male");
        arrayList_gender.add("Female");

        final MaterialSpinner genderSpinner = (MaterialSpinner) dialog.findViewById(R.id.genderSpinner);
        final TextInputEditText langKnownEdit = (TextInputEditText) dialog.findViewById(R.id.languagesKnownEdit);
        final TextInputEditText ageCoached = (TextInputEditText) dialog.findViewById(R.id.ageGroupCoachedEdit);
        final TextInputEditText linkPersonal = (TextInputEditText) dialog.findViewById(R.id.linkPersonalEdit);

        langKnownEdit.setText(langKnown.getText());
        ageCoached.setText(ageGroup.getText());
        linkPersonal.setText(linkToPersonalWeb.getText());

        genderSpinner.setSelection(arrayList_gender.indexOf(gender.getText().toString()) + 1);


        final Button submit_details = (Button) dialog.findViewById(R.id.submit_details);


        final TextView close = (TextView) dialog.findViewById(R.id.close);

        close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.dismiss();

                return true;
            }
        });
        Toolbar toolbar = (Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


//        String[] gender = {"MALE", "FEMALE"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayList_gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                langKnown.setText(langKnownEdit.getText().toString());
                ageGroup.setText(ageCoached.getText().toString());
                linkToPersonalWeb.setText(linkPersonal.getText().toString());
                gender.setText(genderSpinner.getSelectedItem().toString());

                final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Dashboard_prefs", Context.MODE_PRIVATE);
                final String user_id = sharedPreferences.getString("user_id", "");
                if (langKnownEdit.getText().toString().length() > 0 && ageCoached.getText().toString().length() > 0 && linkPersonal.getText().toString().length() > 0 && !genderSpinner.getSelectedItem().toString().equals("GENDER")) {
                    if (net.isConnectingToInternet()) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("link", linkPersonal.getText().toString());
                            jsonObject.put("ageGroupCoached", ageCoached.getText().toString());
                            jsonObject.put("languagesKnown", langKnownEdit.getText().toString());
                            jsonObject.put("userid", user_id);
                            jsonObject.put("gender", sharedPreferences.getString("gender", ""));
                            jsonObject.put("dob", sharedPreferences.getString("dob", ""));
                            jsonObject.put("email", sharedPreferences.getString("email", ""));
                            jsonObject.put("mobile_no", sharedPreferences.getString("contact_no", ""));
                            jsonObject.put("proffession", sharedPreferences.getString("prof_name", ""));
                            jsonObject.put("prof_id", sharedPreferences.getString("prof_id", ""));
                            jsonObject.put("sport", sharedPreferences.getString("sport", ""));
                            jsonObject.put("status", sharedPreferences.getInt("Verified", 0));

                            String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("editprofile", "UTF-8");
                            data += "&" + URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(jsonObject), "UTF-8");

                            new Webservice_submit().execute(mainUrl, data);

//                        new Webservice_submit().execute("http://testingapp.getsporty.in/create_database.php?act=editprofile", data);

                            dialog.dismiss();
                        } catch (Exception e) {
                            Log.d("Error", e.toString());
                        }

                    } else {
                        MyToast myToast = new MyToast();
                        myToast.show(getActivity(), getString(R.string.no_inetnet_connection), true);
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.fill_all_detail), Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    void afterresponse(String response) {
        customProgress.hideProgress();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
                Toast.makeText(getContext(), "Data Updated", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(getContext(), "Server Did not respond", Toast.LENGTH_LONG).show();


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private class Webservice_submit extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgress.showProgress(getActivity(), false);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("Tag", params[0] + params[1]);
            String string_url = params[0];
            String string_data = params[1];
            BufferedReader bufferedReader = null;
            String response = null;
            JSONObject jsonObject_response = null;
            try {

                //server connection
                URL url = new URL(string_url);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                // sending data to server
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(string_data);
                wr.flush();

                // Get the server response

                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = bufferedReader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line);
                }

                // Append Server Response To Content String
                response = sb.toString();
                Log.e("Tag", "response= " + response);
                jsonObject_response = new JSONObject(response);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("Tag", "response before return = " + response);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.e("Tag", response);
            customProgress.hideProgress();
            afterresponse(response);


        }
    }
}
