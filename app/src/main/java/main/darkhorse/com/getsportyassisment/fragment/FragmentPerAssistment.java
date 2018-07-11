package main.darkhorse.com.getsportyassisment.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiCall;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.CommonUtils;
import main.darkhorse.com.getsportyassisment.UtilsFile.MainUrls;
import main.darkhorse.com.getsportyassisment.activity.ActivityDashboardDetail;
import main.darkhorse.com.getsportyassisment.activity.ActivityLoginAdmin;
import main.darkhorse.com.getsportyassisment.activity.ActivityVideoLink;
import main.darkhorse.com.getsportyassisment.activity.MainActivity;
import main.darkhorse.com.getsportyassisment.activity.UserProfile;
import main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess.ApiAtheliteCall;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.model_classes.AssistmentModle;
import main.darkhorse.com.getsportyassisment.model_classes.AssistmentResponse;
import main.darkhorse.com.getsportyassisment.model_classes.PlacesSportsdetail;
import main.darkhorse.com.getsportyassisment.model_classes.Signbody;
import main.darkhorse.com.getsportyassisment.model_classes.sportspojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by shekhar on 4/7/18.
 */
public class FragmentPerAssistment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public FragmentPerAssistment newInstance(String param1, String param2) {
        FragmentPerAssistment fragment = new FragmentPerAssistment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    View rootView;
    RecyclerView recycleview_eventListing;
    RecyclerView.LayoutManager myLayoutManager;
    ArrayList<AssistmentModle> assistment_datalist;
    CustomProgress customProgress;
    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_per_assist, container, false);

        recycleview_eventListing = (RecyclerView) rootView.findViewById(R.id.recyclerview_assist);
        myLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleview_eventListing.setLayoutManager(myLayoutManager);
        customProgress = CustomProgress.getInstance();
       if(!mParam1.equals(""))
       {
           Retrofit_listdata();
       }

       else{

       }

        return rootView;

    }

    public void Retrofit_listdata() {
        assistment_datalist = new ArrayList<AssistmentModle>();
        final int network = CommonUtils.getConnectivityStatus(getActivity());

        if (network == 0) {
            Toast.makeText(getContext(), R.string.no_internet_connection_error, Toast.LENGTH_LONG).show();


        } else {
            try
            {
                String performanceUrl = "http://testingapp.getsporty.in/performance.php?";
                SharedPreferences sharedPreferences_user = getActivity().getSharedPreferences("LoginCredentials", 0);
                String user_id = sharedPreferences_user.getString("userid", "");
                String data = URLEncoder.encode("act", "UTF-8") + "=" + URLEncoder.encode("get_assessement_list", "UTF-8")
                        + "&" + URLEncoder.encode("sport", "UTF-8") + "=" + URLEncoder.encode(mParam1, "UTF-8")
                        + "&" + URLEncoder.encode("usertype", "UTF-8") + "=" + URLEncoder.encode(mParam2, "UTF-8");

                Retrofit retrofit = ApiClient.getClient();
                ApiAtheliteCall apiCall = retrofit.create(ApiAtheliteCall.class);
                Call<AssistmentResponse> checklogin = apiCall.getassismentlist(performanceUrl + data);
                Log.d("Resourse listing url:::", checklogin.request().url().toString());
                customProgress.showProgress(getActivity(), false);

                checklogin.enqueue(new Callback<AssistmentResponse>() {
                    @Override
                    public void onResponse(Call<AssistmentResponse> call, Response<AssistmentResponse> response) {
                        customProgress.hideProgress();
                        assistment_datalist = response.body().getData();
                        EventListingAdapter adapter = new EventListingAdapter(assistment_datalist);
                        recycleview_eventListing.setAdapter(adapter);
//                        JsonElement jsonElement = response.body();
//                        Log.e("JSON RESPONSE", jsonElement.toString());
                        //afterresponse(jsonElement.toString());
                    }

                    @Override
                    public void onFailure(Call<AssistmentResponse> call, Throwable t) {
                        customProgress.hideProgress();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    @SuppressLint("NewApi")
    public class EventListingAdapter extends RecyclerView.Adapter<EventListingAdapter.ViewHolder> {


        private ArrayList<AssistmentModle> DataItems = new ArrayList<AssistmentModle>();
        private View rootview;

        EventListingAdapter(ArrayList<AssistmentModle> DietDataItems) {
            this.DataItems = DietDataItems;

        }

        @Override
        public EventListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.assisement_item, parent, false);
            return new EventListingAdapter.ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(EventListingAdapter.ViewHolder holder, int position) {

            holder.setItem(DataItems.get(position));
//            holder.setItem(DietDataItems.get(position).getMy_diet_plan());
        }

        @Override
        public int getItemCount() {
            return DataItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            private ImageView athleteimage;
            private TextView age;


            private Button assistment;

            private TextView name;
            private TextView gender;
            private TextView location;

            public ViewHolder(final View itemView) {
                super(itemView);
                athleteimage = (ImageView) itemView.findViewById(R.id.athlete_image);

                name = (TextView) itemView.findViewById(R.id.name);
                gender = (TextView) itemView.findViewById(R.id.gender);

                age = (TextView) itemView.findViewById(R.id.age);
                location = (TextView) itemView.findViewById(R.id.location);


                assistment = (Button) itemView.findViewById(R.id.assist);

                assistment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        AssistmentModle assistdataitem = DataItems.get(position);
                        Bundle userinfo = new Bundle();
                        userinfo.putSerializable("sportdetail", (Serializable) assistdataitem);
                        Intent i = new Intent(new Intent(getActivity(), ActivityVideoLink.class));
                        i.putExtras(userinfo);

                         ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(getActivity(),
                                        Pair.create(itemView.findViewById(R.id.athlete_image), "image_transition"),
                                        Pair.create(itemView.findViewById(R.id.name), "text_transition"));
                        startActivity(i, options.toBundle());



                    }
                });


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Bundle userinfo = new Bundle();
                        String userid = DataItems.get(position).getUserid();
                        userinfo.putString("indiacterforprofile", "2");
                        userinfo.putString("student_sport", mParam1);
                        userinfo.putString("student_id", userid);
                        Intent i = new Intent(new Intent(getActivity(), UserProfile.class));
                        i.putExtras(userinfo);
                        startActivity(i);

                    }
                });

            }

            public void setItem(AssistmentModle DataItem) {
                String imageurl = DataItem.getUser_image();
                name.setText(DataItem.getName());
                age.setText(DataItem.getDob());
                gender.setText(DataItem.getGender());
                location.setText(DataItem.getLocation());
                if (imageurl.isEmpty()) {
                    athleteimage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.resource_back));

                } else {
                    Picasso.with(getActivity())
                            .load(DataItem.getUser_image())
                            .error(R.drawable.resource_back)
                            .into(athleteimage);
                }


            }

        }
    }


}
