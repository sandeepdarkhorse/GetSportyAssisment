package main.darkhorse.com.getsportyassisment.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import main.darkhorse.com.getsportyassisment.R;
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
    TriangleView triangleView_test;
    TriangleView tecticalCardView,technicalCardView,physicalCardView,psycologicalCardView;


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

        triangleView_test=(TriangleView)rootView.findViewById(R.id.test_cardview);
        tecticalCardView =(TriangleView)rootView.findViewById(R.id.tectical_cardView);
        technicalCardView=(TriangleView)rootView.findViewById(R.id.technical_cardView);
        physicalCardView=(TriangleView)rootView.findViewById(R.id.physical_card);
        psycologicalCardView=(TriangleView)rootView.findViewById(R.id.psycological_card);



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
                readjson();
                break;




        }

    }

    private void readjson() {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("decimalage");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;
            Log.e("TaG","JSON LENGTH::" +m_jArry.length());

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("decimalage"));

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
        final Button buttonSubmit=(Button)dialogView.findViewById(R.id.submit);









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
