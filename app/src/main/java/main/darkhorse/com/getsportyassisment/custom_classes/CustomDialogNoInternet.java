package main.darkhorse.com.getsportyassisment.custom_classes;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import main.darkhorse.com.getsportyassisment.R;


public class CustomDialogNoInternet extends AppCompatActivity {
    public static CustomDialogNoInternet customProgress = null;
    CircularProgressView mProgressBar;
    private Dialog mDialog;
    Boolean connect = false;
    AnimationDrawable anim;

    public static CustomDialogNoInternet getInstance() {
        if (customProgress == null) {
            customProgress = new CustomDialogNoInternet();
        }
        return customProgress;
    }

    ImageView nointernet_icon;
    RelativeLayout mainlayout;
    Context mcontext;

    public myOnClickListener myListener;

    // This is my interface //
    public interface myOnClickListener {
        void onButtonClick();
    }

    public void showProgress(Context context, boolean cancelable) {
        mDialog = new Dialog(context);
        mcontext = context;
        myListener = (myOnClickListener) context;
        // no tile for the dialog
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.setContentView(R.layout.no_internet_connection_layout);
        mDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        anim = new AnimationDrawable();
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        nointernet_icon = (ImageView) mDialog.findViewById(R.id.noInternet);
        mainlayout = (RelativeLayout) mDialog.findViewById(R.id.RelativeLayout);

        mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                myListener.onButtonClick();


            }
        });


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    mDialog.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(receiver, new IntentFilter("noInternet"));

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mainlayout.performClick();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }


    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}

