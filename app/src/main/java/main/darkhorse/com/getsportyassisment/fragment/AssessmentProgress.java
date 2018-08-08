package main.darkhorse.com.getsportyassisment.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.shapesview.CutCornerView;
import main.darkhorse.com.getsportyassisment.shapesview.TriangleView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssessmentProgress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssessmentProgress extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CutCornerView triangleView_test;
    CutCornerView tecticalCardView,technicalCardView,physicalCardView,psycologicalCardView;
    Context context;
    String decimalDob;
    String decimalAssessmentDate;


    public AssessmentProgress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssessmentProgress.
     */
    // TODO: Rename and change types and number of parameters
    public static AssessmentProgress newInstance(String param1, String param2) {
        AssessmentProgress fragment = new AssessmentProgress();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.fragment_assessment_progress, container, false);
        getActivity().setTitle("Assessment Progress ");

        triangleView_test=(CutCornerView)rootView.findViewById(R.id.test_cardview);
        tecticalCardView =(CutCornerView)rootView.findViewById(R.id.tectical_cardView);
        technicalCardView=(CutCornerView)rootView.findViewById(R.id.technical_cardView);
        physicalCardView=(CutCornerView)rootView.findViewById(R.id.physical_card);
        psycologicalCardView=(CutCornerView)rootView.findViewById(R.id.psycological_card);



         triangleView_test.setOnClickListener(this);
        tecticalCardView.setOnClickListener(this);
        psycologicalCardView.setOnClickListener(this);
        physicalCardView.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.test_cardview:
                openTestDialog();

                break;
            case R.id.tectical_cardView:
                 openQuestionnareForm();

                break;
            case R.id.technical_cardView:
                openQuestionnareForm();
                break;
            case R.id.physical_card:
                openQuestionnareForm();
                break;
            case R.id.psycological_card:
               // openQuestionnareForm();
                readjson("2006-01-05");
               decimal_ageCalculation("2018-03-10");
                break;




        }

    }
    @TargetApi(Build.VERSION_CODES.O)
    private void decimal_ageCalculation(String age) {
        // String age="2006-01-05";
        Log.e("Tag","date data::"+ DateConversion.getday(age));
       String  dateDecimalValue="";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
        LocalDate localDate2 = LocalDate.parse( age , formatter2 );

        int year = localDate2.getYear();
        int month = localDate2.getMonthValue();
        int dayOfMonth = localDate2.getDayOfMonth();
        Month nameOfMonth=localDate2.getMonth();
        String monthName=String.valueOf(nameOfMonth);
        Log.e("Tag"," year::"+ year +"-month::: "+nameOfMonth+"-day::  "+dayOfMonth);

        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("decimalage.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        try{
            JSONObject obj = new JSONObject(json);
            String strData = obj.getString("decimalAge");
            Log.e("TaG","decimal age data::" +strData);
            JSONArray jjArray=obj.getJSONArray("decimalAge");
            for(int i=0;i<jjArray.length();i++){
                JSONObject jjObject=jjArray.getJSONObject(i);
                JSONArray jsonArray1=jjObject.getJSONArray(monthName);
                Log.e("TaG","month data::" +jsonArray1.toString());
                Log.e("TaG","length of array::" +jsonArray1.length());
                for(int j=0;j<jsonArray1.length();j++){
                    JSONObject jmonth=jsonArray1.getJSONObject(j);



                    Log.e("TAG","month data value::" +jmonth.getString(Integer.toString(dayOfMonth)));
                    dateDecimalValue=jmonth.getString(Integer.toString(dayOfMonth));


                }
                decimalAssessmentDate=Integer.toString(year)+"."+dateDecimalValue;

            }
            double actualAge=Double.parseDouble(decimalAssessmentDate)-Double.parseDouble(decimalDob);
            Log.e("TAG","decimal age::" +actualAge);
            double height=157.0;
            double legLength=77.4;
            double sittingHeight=79.6;
            double weight=53.0;

            double weightByHeightRatio=(weight/height)*100;
            String gender="Male";
            switch(gender){
                case "Male":
                    double maturityOffsetMale=(-9.236)+(0.0002708*legLength*sittingHeight)+(-0.001663 * actualAge*legLength)+(0.007216*actualAge*sittingHeight)+(0.02292 *weightByHeightRatio);
                    Log.e("Tag","value of maturityOff set::"+maturityOffsetMale);
               break;
                case "Female":

                    double maturityOffsetFemale =(-9.376)+(0.0001882*legLength*sittingHeight)+(-0.0022 * actualAge*legLength)+(0.005841*actualAge*sittingHeight)-(0.002658*actualAge*weight)+(0.07693 *weightByHeightRatio);
                    Log.e("Tag","value of maturityOff set::"+maturityOffsetFemale);
                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void readjson(String age) {
       // String age="2006-01-05";
       Log.e("Tag","date data::"+ DateConversion.getday(age));
        String dateDecimalValue="";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
        LocalDate localDate = LocalDate.parse( age , formatter );

        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        Month nameOfMonth=localDate.getMonth();
        String monthName=String.valueOf(nameOfMonth);
        Log.e("Tag"," year::"+ year +"-month::: "+nameOfMonth+"-day::  "+dayOfMonth);

        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("decimalage.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }

try{
            JSONObject obj = new JSONObject(json);
            String strData = obj.getString("decimalAge");
    Log.e("TaG","decimal age data::" +strData);
    JSONArray jjArray=obj.getJSONArray("decimalAge");
    for(int i=0;i<jjArray.length();i++){
    JSONObject jjObject=jjArray.getJSONObject(i);
    JSONArray jsonArray1=jjObject.getJSONArray(monthName);
    Log.e("TaG","month data::" +jsonArray1.toString());
    Log.e("TaG","length of array::" +jsonArray1.length());
    for(int j=0;j<jsonArray1.length();j++){
        JSONObject jmonth=jsonArray1.getJSONObject(j);


        Log.e("TAG","month data::" +jmonth);
        Log.e("TAG","month data value::" +jmonth.getString(Integer.toString(dayOfMonth)));
         dateDecimalValue=jmonth.getString(Integer.toString(dayOfMonth));

    }
        decimalDob=Integer.toString(year)+"."+dateDecimalValue;

}

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openQuestionnareForm() {

        final View dialogView = View.inflate(getActivity(), R.layout.questionformdialog, null);

        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(dialogView);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        android.support.v7.widget.Toolbar toolbar_dissmiss = (android.support.v7.widget.Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(dialogView, false, dialog);
            }
        });

        ImageView tool_imageView=(ImageView)toolbar_dissmiss.findViewById(R.id.closeDialogImg);
//        tool_imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                revealShow(dialogView, false, dialog);
//            }
//        });
        tool_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        dialog.show();

    }

    private void openTestDialog() {


        final View dialogView = View.inflate(getActivity(), R.layout.testdata_dialog, null);

        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(dialogView);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        android.support.v7.widget.Toolbar toolbar_dissmiss = (android.support.v7.widget.Toolbar) dialog.findViewById(R.id.toolbar);
        toolbar_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(dialogView, false, dialog);
            }
        });

        ImageView tool_imageView=(ImageView)toolbar_dissmiss.findViewById(R.id.closeDialogImg);
//        tool_imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                revealShow(dialogView, false, dialog);
//            }
//        });
        tool_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final  EditText editText_speed=(EditText)dialogView.findViewById(R.id.speed);
        final  EditText editText_height=(EditText)dialogView.findViewById(R.id.height);
        final   EditText editText_jump=(EditText)dialogView.findViewById(R.id.jump);
        final   EditText editText_weight=(EditText)dialogView.findViewById(R.id.weight);
        final  EditText editText_balance=(EditText)dialogView.findViewById(R.id.balance);
        final  EditText edt_athleteweight=(EditText)dialogView.findViewById(R.id.athlete_weight);
        final  EditText edt_leglength=(EditText)dialogView.findViewById(R.id.athlete_leg_length);
        final  EditText edt_sittingHeight=(EditText)dialogView.findViewById(R.id.sitting_height);


        final Button buttonSubmit=(Button)dialogView.findViewById(R.id.submit);
        final ImageView imageView1=(ImageView)dialogView.findViewById(R.id.tick1);
        final ImageView imageView2=(ImageView)dialogView.findViewById(R.id.tick2);
        final ImageView imageView3=(ImageView)dialogView.findViewById(R.id.tick3);
        final ImageView imageView4=(ImageView)dialogView.findViewById(R.id.tick4);
        final ImageView imageView5=(ImageView)dialogView.findViewById(R.id.tick5);
        final ImageView imageView6=(ImageView)dialogView.findViewById(R.id.tick6);
        final ImageView imageView7=(ImageView)dialogView.findViewById(R.id.tick7);
        final ImageView imageView8=(ImageView)dialogView.findViewById(R.id.tick8);





        editText_speed.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
               if(! editText_speed.getText().toString().equals("")){
                   imageView1.setVisibility(View.VISIBLE);
               }else{
                   imageView1.setVisibility(View.INVISIBLE);
               }
            }
        });

        editText_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! editText_height.getText().toString().equals("")){
                    imageView2.setVisibility(View.VISIBLE);
                }else{
                    imageView2.setVisibility(View.INVISIBLE);
                }
            }
        });

        editText_jump.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! editText_jump.getText().toString().equals("")){
                    imageView5.setVisibility(View.VISIBLE);
                }else{
                    imageView5.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! editText_weight.getText().toString().equals("")){
                    imageView3.setVisibility(View.VISIBLE);
                }else{
                    imageView3.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText_balance.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! editText_balance.getText().toString().equals("")){
                    imageView4.setVisibility(View.VISIBLE);
                }else{
                    imageView4.setVisibility(View.INVISIBLE);
                }
            }
        });
        edt_athleteweight.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! edt_athleteweight.getText().toString().equals("")){
                    imageView6.setVisibility(View.VISIBLE);
                }else{
                    imageView6.setVisibility(View.INVISIBLE);
                }
            }
        });

        edt_athleteweight.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! edt_athleteweight.getText().toString().equals("")){
                    imageView6.setVisibility(View.VISIBLE);
                }else{
                    imageView6.setVisibility(View.INVISIBLE);
                }
            }
        });
        edt_leglength.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! edt_leglength.getText().toString().equals("")){
                    imageView7.setVisibility(View.VISIBLE);
                }else{
                    imageView7.setVisibility(View.INVISIBLE);
                }
            }
        });
        edt_sittingHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! edt_sittingHeight.getText().toString().equals("")){
                    imageView8.setVisibility(View.VISIBLE);
                }else{
                    imageView8.setVisibility(View.INVISIBLE);
                }
            }
        });

        dialog.show();

    }


    @SuppressLint("NewApi")
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (triangleView_test.getX() + (triangleView_test.getWidth() / 2));
        int cy = (int) (triangleView_test.getY()) + triangleView_test.getHeight() + 56;


        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }


//method to read json file
    public String loadJSONFromAsset() {

            String json = null;
            try {
                InputStream is = getActivity().getAssets().open("decimalage.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    }

