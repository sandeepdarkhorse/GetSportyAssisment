package main.darkhorse.com.getsportyassisment.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.model_classes.PlacesSportsdetail;

@SuppressLint("NewApi")
public class ActivityDashboardDetail extends Activity implements Serializable {
    public static PlacesSportsdetail sportdataitem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_sportdetail);
        ImageView imageView = (ImageView) findViewById(R.id.imagezoom);
        TextView sports = (TextView) findViewById(R.id.sports_name);

//        TextView place_name = (TextView) findViewById(R.id.place_name);
//        TextView coartNo = (TextView) findViewById(R.id.courtdetail);
//        TextView timming = (TextView) findViewById(R.id.timming);
//        TextView fee = (TextView) findViewById(R.id.fee);
//        TextView avCoachin = (TextView) findViewById(R.id.av_coaching);


        Bundle userinfo = getIntent().getExtras();
        if (userinfo != null) {

            sportdataitem = (PlacesSportsdetail) userinfo.getSerializable("sportdetail");
            if (sportdataitem != null && sportdataitem != null)
            {

                imageView.setImageResource(sportdataitem.getDrawable_id());
                sports.setText(sportdataitem.getName());

//                place_name.setText("At  "+ sportdataitem.getPlacename());
//                coartNo.setText(sportdataitem.getCourt());
//                timming.setText(sportdataitem.getTimming());
//                fee.setText(sportdataitem.getFee());
//                avCoachin.setText(sportdataitem.getCoachingAv());

            } else {

            }


        }
        RelativeLayout mainlaout = (RelativeLayout) findViewById(R.id.main_layout);
        mainlaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });

        CardView  cardviewathlete=(CardView)findViewById(R.id.athletecard);

        cardviewathlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }
}
