package main.darkhorse.com.getsportyassisment.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.custom_classes.Config;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomPlayerControlActivity;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.model_classes.AssistmentModle;
import main.darkhorse.com.getsportyassisment.model_classes.Video_link;

public class ActivityVideoLink extends Activity implements Serializable {
    public static AssistmentModle DataItem;

    RecyclerView recycleview_eventListing;
    RecyclerView.LayoutManager myLayoutManager;
    ArrayList<AssistmentModle> assistment_datalist;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolink);
        CircleImageView UserimageView = (CircleImageView) findViewById(R.id.profile_pic);
        TextView name = (TextView) findViewById(R.id.name);

        Bundle userinfo = getIntent().getExtras();
        if (userinfo != null) {

            DataItem = (AssistmentModle) userinfo.getSerializable("sportdetail");
            if (DataItem != null && DataItem != null) {
                String imageurl = DataItem.getUser_image();


                if (imageurl.isEmpty()) {
                    UserimageView.setImageDrawable(ContextCompat.getDrawable(ActivityVideoLink.this, R.drawable.resource_back));

                } else {
                    Picasso.with(ActivityVideoLink.this)
                            .load(DataItem.getUser_image())
                            .error(R.drawable.resource_back)
                            .into(UserimageView);
                }


                name.setText(DataItem.getName());

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

        ArrayList<Video_link> videolink = DataItem.getVideo_link();

        recycleview_eventListing = (RecyclerView) findViewById(R.id.recyclerview_videolink);
        myLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleview_eventListing.setLayoutManager(myLayoutManager);

        EventListingAdapter adapter = new EventListingAdapter(videolink);
        recycleview_eventListing.setAdapter(adapter);


    }


    @SuppressLint("NewApi")
    public class EventListingAdapter extends RecyclerView.Adapter<EventListingAdapter.ViewHolder> {
        private View rootview;
        ArrayList<Video_link> videolink;

        EventListingAdapter(ArrayList<Video_link> videolink) {
            this.videolink = videolink;

        }

        @Override
        public EventListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.videolink_item, parent, false);


            return new EventListingAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(EventListingAdapter.ViewHolder holder, int position) {

            holder.setItem(videolink.get(position));
//            holder.setItem(DietDataItems.get(position).getMy_diet_plan());
        }

        @Override
        public int getItemCount() {
            return videolink.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            private TextView videolink_tv;

            private YouTubeThumbnailView videoView;
            public ViewHolder(final View itemView) {
                super(itemView);

                videolink_tv = (TextView) itemView.findViewById(R.id.link);

                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {

                        int position = getAdapterPosition();
                        //   final String videoCode=    DateConversion.extractYTId(videolink.get(position).getVideolink());
//                        try{
//                            Intent intent = YouTubeStandalonePlayer.createVideoIntent(ActivityVideoLink.this, Config.KEY, videoCode, 100, true, true);
//
//
//                            startActivity(intent);
//
//
//
//                        }catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }

                        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
                        Pattern compiledPattern = Pattern.compile(pattern);
                        Matcher matcher = compiledPattern.matcher(videolink.get(position).getVideolink());
                        if(matcher.find()){
                            Intent intent = new Intent(ActivityVideoLink.this, CustomPlayerControlActivity.class);
                            intent.putExtra("videoCode", matcher.group());
                            startActivity(intent);

                        }


                    }
                });

            }

            public void setItem(Video_link DataItem) {

                videolink_tv.setText(DataItem.getVideolink());


            }

        }



    }


}
