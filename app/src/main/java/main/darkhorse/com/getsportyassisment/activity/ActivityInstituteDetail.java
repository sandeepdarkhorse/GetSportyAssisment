package main.darkhorse.com.getsportyassisment.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.model_classes.InstituteDataPojo;
import main.darkhorse.com.getsportyassisment.model_classes.InstituteDataPojoApi;


@SuppressLint("NewApi")
public class ActivityInstituteDetail extends Activity implements Serializable {
    public static InstituteDataPojoApi sportdataitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_institute_detail);

        ImageView imageView = (ImageView) findViewById(R.id.institute_image);
        TextView instname = (TextView) findViewById(R.id.inst_name);

        TextView instlocation = (TextView) findViewById(R.id.inst_location);

        Bundle userinfo = getIntent().getExtras();
        if (userinfo != null) {

            sportdataitem = (InstituteDataPojoApi) userinfo.getSerializable("institutedetail");
            if (sportdataitem != null && sportdataitem != null)
            {

//                imageView.setImageResource(sportdataitem.getDrawable_id());
                instname.setText(sportdataitem.getCollege_name());

                instlocation.setText(sportdataitem.getLocation());

            } else {

            }


        }
        CoordinatorLayout mainlaout = (CoordinatorLayout) findViewById(R.id.main_layout);
        mainlaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });


        ImageView backpress=(ImageView) findViewById(R.id.back_press);

        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }


}
