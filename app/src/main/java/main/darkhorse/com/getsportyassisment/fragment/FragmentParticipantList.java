package main.darkhorse.com.getsportyassisment.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import fr.ganfra.materialspinner.MaterialSpinner;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CheckAndroidPermission;
import main.darkhorse.com.getsportyassisment.activity.AssessmentEventList;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.model_classes.InstituteDataPojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentParticipantList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentParticipantList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Toolbar toolbar;
    FloatingActionButton floatingAdd;
    Button bttn_recordVideo,bttn_assessment,bttn_report;
    private static final int VIDEO_CAPTURE = 101;
    Uri videoUri;
    Context context;





    public FragmentParticipantList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentParticipantList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentParticipantList newInstance(String param1, String param2) {
        FragmentParticipantList fragment = new FragmentParticipantList();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_participant_list, container, false);

        getActivity().setTitle("Participant List");
//        toolbar.setVisibility(View.VISIBLE);
//        toolbar.setTitle("Participant List");
      //  setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bttn_recordVideo=(Button)rootView.findViewById(R.id.record_video);
        bttn_assessment=(Button)rootView.findViewById(R.id.assessment);
        bttn_report=(Button)rootView.findViewById(R.id.report);
   floatingAdd=(FloatingActionButton)rootView.findViewById(R.id.add_participant);
   floatingAdd.setVisibility(View.VISIBLE);
   floatingAdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialogAddParticipant();

    }
   });

        bttn_recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });

        bttn_assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().add(R.id.container,new AssessmentProgress().newInstance("","")).addToBackStack(null).commit();

            }
        });

        return rootView;
    }



    private void dialogAddParticipant() {

            final View dialogView = View.inflate(getActivity(), R.layout.add_athlete_dialog, null);

//        final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(dialogView);

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

    private void recordVideo() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());

        final View dialogView = View.inflate(getActivity(), R.layout.videoplay_layout, null);

//        final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(dialogView);

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
        Button buttonRecord=(Button)dialogView.findViewById(R.id.recordvideo);
        Button buttonPlay=(Button)dialogView.findViewById(R.id.play_video);
//
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
     final   VideoView mVideoView = (VideoView) dialogView.findViewById(R.id.video_view);



        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){}
                if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    File mediaFile = new File(
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");
                    videoUri = Uri.fromFile(mediaFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                    startActivityForResult(intent, VIDEO_CAPTURE);
                } else {
                    Toast.makeText(getActivity(), "No camera on device", Toast.LENGTH_LONG).show();
                }

            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.setVideoURI(videoUri);
                mVideoView.setMediaController(new MediaController(getActivity()));
                mVideoView.requestFocus();
                mVideoView.start();
            }
        });

        dialog.show();





    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "Video has been saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
              //  playbackRecordedVideo();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Video recording cancelled.",  Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Failed to record video",  Toast.LENGTH_LONG).show();
            }
        }
    }

//    public void playbackRecordedVideo() {
//        VideoView mVideoView = (VideoView) findViewById(R.id.video_view);
//        mVideoView.setVideoURI(videoUri);
//        mVideoView.setMediaController(new MediaController(getActivity()));
//        mVideoView.requestFocus();
//        mVideoView.start();
//    }



    @SuppressLint("NewApi")
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (floatingAdd.getX() + (floatingAdd.getWidth() / 2));
        int cy = (int) (floatingAdd.getY()) + floatingAdd.getHeight() + 56;


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

}
