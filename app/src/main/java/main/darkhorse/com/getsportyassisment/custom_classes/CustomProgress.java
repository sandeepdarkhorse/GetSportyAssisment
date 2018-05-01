package main.darkhorse.com.getsportyassisment.custom_classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import main.darkhorse.com.getsportyassisment.R;


/**
 * Created by sandeepsingh on 20/4/18.
 */

public class CustomProgress extends AppCompatActivity {
    public static CustomProgress customProgress = null;
    CircularProgressView mProgressBar;
    private Dialog mDialog;

    public static CustomProgress getInstance() {
        if (customProgress == null) {
            customProgress = new CustomProgress();
        }
        return customProgress;
    }

    public void showProgress(Context context, boolean cancelable) {
        mDialog = new Dialog(context);
        // no tile for the dialog
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.setContentView(R.layout.progress_bar_layout);
        mDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AnimationDrawable anim = new AnimationDrawable();
        ImageView athelite = (ImageView) mDialog.findViewById(R.id.gs_logo);
        mProgressBar = (CircularProgressView) mDialog.findViewById(R.id.progress_bar);

        //  mProgressBar.getIndeterminateDrawable().setColorFilter(context.getResources()
        // .getColor(R.color.material_blue_gray_500), PorterDuff.Mode.SRC_IN);
//        TextView progressText = (TextView) mDialog.findViewById(R.id.progress_text);
//        progressText.setText("" + message);
//        progressText.setVisibility(View.VISIBLE);
        // mProgressBar.setVisibility(View.VISIBLE);
        // you can change or add this line according to your need
//        mProgressBar.setIndeterminate(true);

        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);

        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_0),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_1),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_2),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_3),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_4),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_5),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_6),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_7),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_8),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_9),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_10),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_11),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_12),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_13),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_14),
                40);
        anim.addFrame(
                context.getResources().getDrawable(R.drawable.frame_15),
                40);
        athelite.setImageDrawable(anim);

        //if you want the animation to loop, set false
        anim.setOneShot(false);
        anim.start();
        mDialog.show();
    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
