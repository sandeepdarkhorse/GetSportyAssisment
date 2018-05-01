package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;


public class TabFragmentAchievements extends Fragment implements ApiAtheliteData, Animation.AnimationListener {

    View rootView;
    TextView sport_edit;
    TextView formal_edit;
    Achivement achivement;
    LinearLayout linearvisible, linearvisible_award;
    AwardResult_adapter award_result_Adapter;
    BestResult_adapter best_result_Adapter;
    private ArrayList<BestResult> listBestResult;
    private ArrayList<Award> AwardResult;
    private RecyclerView recycle_view_best;
    private RecyclerView recycle_view_award;
    private int MY_PERMISSIONS_REQUEST_CAMERA_STORAGE = 9000;

    public TabFragmentAchievements() {
        AwardResult = new ArrayList<>();
        listBestResult = new ArrayList<>();
        achivement = new Achivement();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tabfragment_achievements, container, false);
        instantiation();
        return rootView;
    }


    private void instantiation() {
        sport_edit = rootView.findViewById(R.id.sport_edit);
        formal_edit = rootView.findViewById(R.id.formal_edit);
        sport_edit.setVisibility(View.GONE);
        formal_edit.setVisibility(View.GONE);
        recycle_view_best = rootView.findViewById(R.id.recycle_view_best);
        RecyclerView.LayoutManager bestresultdLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycle_view_best.setLayoutManager(bestresultdLayoutManager);
        recycle_view_award = rootView.findViewById(R.id.recycle_award);
        RecyclerView.LayoutManager awardLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycle_view_award.setLayoutManager(awardLayoutManager);
        linearvisible = rootView.findViewById(R.id.linear_visible_bestresult);
        linearvisible_award = rootView.findViewById(R.id.linear_visible_award);
    }

    @Override
    public void getAchievements(ArrayList<BestResult> bestResult, ArrayList<Award> awards) {
        if (bestResult != null) {
            if (bestResult.size() > 0) {
                listBestResult = bestResult;
                best_result_Adapter = new BestResult_adapter(listBestResult);
                recycle_view_best.setAdapter(best_result_Adapter);
                try {
                    linearvisible.setVisibility(View.GONE);
                    recycle_view_best.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                }
                achivement.setBestResult(bestResult);
            } else {
                achivement.setBestResult(new ArrayList<BestResult>());
                try {
                    linearvisible.setVisibility(View.VISIBLE);
                    recycle_view_best.setVisibility(View.GONE);
                } catch (Exception e) {
                }
            }
        }
        if (awards != null) {
            if (awards.size() > 0) {
                AwardResult = awards;
                award_result_Adapter = new AwardResult_adapter(AwardResult);
                recycle_view_award.setAdapter(award_result_Adapter);
                try {
                    linearvisible_award.setVisibility(View.GONE);
                    recycle_view_award.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                }
                achivement.setAwards(awards);
            } else {
                achivement.setAwards(new ArrayList<Award>());
                try {
                    linearvisible_award.setVisibility(View.VISIBLE);
                    recycle_view_award.setVisibility(View.GONE);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void getLatestResultData(ArrayList<LatestResults> LatestResult) {
    }

    @Override
    public void getLatestResultTennisData(ArrayList<LatestResultsTennis> LatestResults) {
    }

    @Override
    public void getLatestResultFootballData(ArrayList<LatestResultsFootball> LatestResults) {
    }

    @Override
    public void getOthersData(String description, String level, String location, String name, String rank, String clubOrAcademy, String coachName, String dob, String height, String styleOrTypeOfPlay, String weight) {
    }

    @Override
    public void getUserData(String name, String Gender, String dob, String prof_name, String sport) {
    }

    @Override
    public void getUserBio(String coachName, String clubOrAcademy, String styleOrTypeOfPlay, String dob, String height, String weight, String gender, String location, String email) {
    }


    @Override
    public void getUserHeader(String name, String level, String rank, String location, String description) {
    }

    @Override
    public void getLatestResultOthersData(ArrayList<LatestResultBadminton> latestResultsOthers) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA_STORAGE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    public class AwardResult_adapter extends RecyclerView.Adapter<AwardResult_adapter.ViewHolder> {
        private View rootview;
        private ArrayList<Award> sportsEducationData;

        AwardResult_adapter(ArrayList<Award> sportsEducationData) {
            this.sportsEducationData = new ArrayList<>();
            this.sportsEducationData = sportsEducationData;
        }

        @Override
        public AwardResult_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.award_list_athlete_profile, parent, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(AwardResult_adapter.ViewHolder holder, int position) {
            holder.setItem(sportsEducationData.get(position), position);
        }

        @Override
        public int getItemCount() {
            return sportsEducationData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView awardName;
            private TextView heading;
            private TextView description;
            private TextView tvDate;
            private LinearLayout linearLayoutParent;

            public ViewHolder(final View itemView) {
                super(itemView);
                awardName = itemView.findViewById(R.id.awardName);
                heading = itemView.findViewById(R.id.heading);
                description = itemView.findViewById(R.id.description);
                tvDate = itemView.findViewById(R.id.date);
                linearLayoutParent = itemView.findViewById(R.id.parent_layout);
            }

            void setItem(Award s, int position) {
                String name = s.getNameOfAward();
                String date = s.getDate();
                String[] split = date.split("-");
                heading.setText("AWARD " + (position + 1));
                awardName.setText(name);
                description.setText(s.getDescription());
                tvDate.setText(s.getDate());
                if (position % 2 == 0) {
                  //  linearLayoutParent.setBackgroundResource(R.drawable.light_pink_background);
                } else {
                  //  linearLayoutParent.setBackgroundResource(R.drawable.light_blue_background);
                }
            }
        }
    }

    public class BestResult_adapter extends RecyclerView.Adapter<BestResult_adapter.ViewHolder> {
        private View rootview;
        private ArrayList<BestResult> BestResultData;

        BestResult_adapter(ArrayList<BestResult> sportsEducationData) {
            this.BestResultData = new ArrayList<>();
            this.BestResultData = sportsEducationData;
        }

        @Override
        public BestResult_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_list_item, parent, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(BestResult_adapter.ViewHolder holder, int position) {
            holder.setItem(BestResultData.get(position), position);
        }

        @Override
        public int getItemCount() {
            return BestResultData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView degree;
            private TextView duration;
            private TextView orgName;
            private TextView stream;
            private TextView textViewHeading;
            private LinearLayout linearLayoutParent;

            public ViewHolder(final View itemView) {
                super(itemView);
                degree = itemView.findViewById(R.id.degree);
                duration = itemView.findViewById(R.id.degree_duraction);
                orgName = itemView.findViewById(R.id.org_name);
                stream = itemView.findViewById(R.id.stream);
                textViewHeading = itemView.findViewById(R.id.heading);
                linearLayoutParent = itemView.findViewById(R.id.parent_layout);
            }

            void setItem(BestResult s, int position) {
                degree.setText("Result : " + s.getResult());
                duration.setText(s.getDate());
                orgName.setText(s.getRounds());
                stream.setText(s.getNameComptation());
                textViewHeading.setText("BEST RESULT " + (position + 1));
                if (position % 2 == 0) {
                 //   linearLayoutParent.setBackgroundResource(R.drawable.purple_background);
                } else {
                   // linearLayoutParent.setBackgroundResource(R.drawable.pink_background);
                }
            }
        }
    }
}

