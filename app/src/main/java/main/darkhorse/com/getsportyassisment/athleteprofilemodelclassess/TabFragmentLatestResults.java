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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonParser;


public class TabFragmentLatestResults extends Fragment implements ApiAtheliteData {

    View rootView;
    Achivement achivement;
    Header header;
    Bio bio;
    LatestResult_adapter latestadapter;
    LatestResult_football_adapter football_adapter;
    LatestResult_tennis_adapter tennis_adapter;
    JSONObject footBallDetail;
    private RecyclerView recyclerView;
    private ArrayList<LatestResults> latestList;
    private ArrayList<LatestResultsTennis> latestList_tennis;
    private ArrayList<LatestResultsFootball> latestList_footbal;
    private ArrayList<LatestResultBadminton> latestResultOther;
    private int MY_PERMISSIONS_REQUEST_CAMERA_STORAGE = 9000;

    public TabFragmentLatestResults() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tabfragment_latestresults, container, false);
        achivement = new Achivement();
        bio = new Bio();
        header = new Header();

        latestList = new ArrayList<>();
        latestList_tennis = new ArrayList<>();
        latestList_footbal = new ArrayList<>();
        latestResultOther = new ArrayList<>();
        footBallDetail = new JSONObject();

        recyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager bestresultdLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(bestresultdLayoutManager);

        return rootView;
    }

    @Override
    public void getAchievements(ArrayList<BestResult> bestResults, ArrayList<Award> awards) {
        achivement.setAwards(awards);
        achivement.setBestResult(bestResults);
    }

    @Override
    public void getLatestResultData(ArrayList<LatestResults> LatestResults) {
        latestList = LatestResults;
        latestadapter = new LatestResult_adapter(latestList);
        recyclerView.setAdapter(latestadapter);
    }

    @Override
    public void getLatestResultTennisData(ArrayList<LatestResultsTennis> LatestResults) {
        latestList_tennis = LatestResults;
        tennis_adapter = new LatestResult_tennis_adapter(latestList_tennis);
        recyclerView.setAdapter(tennis_adapter);
    }

    @Override
    public void getLatestResultFootballData(ArrayList<LatestResultsFootball> LatestResults) {
        latestList_footbal = LatestResults;
        football_adapter = new LatestResult_football_adapter(latestList_footbal);
        recyclerView.setAdapter(football_adapter);
    }

    @Override
    public void getLatestResultOthersData(ArrayList<LatestResultBadminton> latestResultsOthers) {
        latestResultOther = latestResultsOthers;
        LatestResultOtherAdapter otherAdapter = new LatestResultOtherAdapter(latestResultOther);
        recyclerView.setAdapter(otherAdapter);
    }

    @Override
    public void getOthersData(String description, String level, String location, String name, String rank, String clubOrAcademy, String coachName, String dob, String height, String styleOrTypeOfPlay, String weight) {
    }

    @Override
    public void getUserData(String name, String Gender, String dob, String prof_name, String sport) {
    }

    @Override
    public void getUserBio(String coachName, String clubOrAcademy, String styleOrTypeOfPlay, String dob, String height, String weight, String gender, String location, String email) {
        bio.setCoachName(coachName);
        bio.setClubOrAcademy(clubOrAcademy);
        bio.setStyleOrTypeOfPlay(styleOrTypeOfPlay);
        bio.setDob(dob);
        bio.setHeight(height);
        bio.setWeight(weight);
        bio.setGender(gender);
        bio.setLocation(location);
        bio.setEmail(email);
    }

    @Override
    public void getUserHeader(String name, String level, String rank, String location, String description) {
        header.setDescription(description);
        header.setLevel(level);
        header.setLocation(location);
        header.setName(name);
        header.setRank(rank);
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

    public class LatestResult_adapter extends RecyclerView.Adapter<LatestResult_adapter.ViewHolder> {
        private View rootview;
        private ArrayList<LatestResults> LatestResultData;

        LatestResult_adapter(ArrayList<LatestResults> data) {
            this.LatestResultData = data;
        }

        @Override
        public LatestResult_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_result_list_items, parent, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(LatestResult_adapter.ViewHolder holder, int position) {
            if (LatestResultData.size() > 0) {
                holder.setItem(LatestResultData.get(position));
            }
        }

        @Override
        public int getItemCount() {
            if (LatestResultData.size() == 0)
                return 1;
            return LatestResultData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView edit_data, imageAdd;
            private TextView title, comp_name, comp_date, event, score;

            public ViewHolder(final View itemView) {
                super(itemView);
                comp_name = itemView.findViewById(R.id.comp_name);
                imageAdd = itemView.findViewById(R.id.add_result);
                comp_date = itemView.findViewById(R.id.result_date);
                event = itemView.findViewById(R.id.event);
                title = itemView.findViewById(R.id.result_title);
                edit_data = itemView.findViewById(R.id.edit_data);
                score = itemView.findViewById(R.id.score);

                edit_data.setVisibility(View.GONE);
                imageAdd.setVisibility(View.GONE);
            }

            void setItem(LatestResults s) {
                comp_name.setText(s.getNameOfCompetation());
                comp_date.setText(s.getDateOfCompetation());
                event.setText(s.getEvent());
                title.setText(s.getResult());
                score.setText(s.getRoundOrHeat() + " " + s.getTime());
            }
        }
    }

    public class LatestResult_tennis_adapter extends RecyclerView.Adapter<LatestResult_tennis_adapter.ViewHolder> {

        private View rootview;
        private ArrayList<LatestResultsTennis> LatestResultData;

        LatestResult_tennis_adapter(ArrayList<LatestResultsTennis> data) {
            this.LatestResultData = data;
        }

        @Override
        public LatestResult_tennis_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.atelite_latestresult_listitem, parent, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(LatestResult_tennis_adapter.ViewHolder holder, int position) {
            if (LatestResultData.size() > 0) {
                holder.setItem(LatestResultData.get(position));
            }
        }

        @Override
        public int getItemCount() {
            if (LatestResultData.size() == 0)
                return 1;
            return LatestResultData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView edit_data, imageAdd;
            private TextView title, comp_name, comp_date, opponent, score;

            public ViewHolder(final View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.result_title);
                comp_name = itemView.findViewById(R.id.comp_name);
                comp_date = itemView.findViewById(R.id.result_date);
                edit_data = itemView.findViewById(R.id.edit_data);
                opponent = itemView.findViewById(R.id.opponent);
                score = itemView.findViewById(R.id.score);
                imageAdd = itemView.findViewById(R.id.add_result);

                edit_data.setVisibility(View.GONE);
                imageAdd.setVisibility(View.GONE);
            }

            void setItem(LatestResultsTennis s) {
                comp_name.setText(s.getNameOfCompetation());
                comp_date.setText(s.getDateOfCompetation());
                opponent.setText(s.getOpponent());
                title.setText(s.getResult());
                JSONObject jsonObject = s.getScore();
                String strScore = JsonParser.getString(jsonObject, JsonKeys.FIRST_SET) + ", " +
                        JsonParser.getString(jsonObject, JsonKeys.SECOND_SET) + ", " +
                        JsonParser.getString(jsonObject, JsonKeys.THIRD_SET) + ", " +
                        JsonParser.getString(jsonObject, JsonKeys.FOURTH_SET) + ", " +
                        JsonParser.getString(jsonObject, JsonKeys.FIFTH_SET);
                strScore = strScore.replaceAll(", , , , ", ", ");
                strScore = strScore.replaceAll(", , , ", ", ");
                strScore = strScore.replaceAll(", , ", ", ");
                if (strScore.endsWith(", ")) {
                    strScore = strScore.substring(0, strScore.lastIndexOf(",")).trim();
                }
                if (strScore.startsWith(", ")) {
                    strScore = strScore.replace(", ", "");
                }
                score.setText(strScore);
            }
        }
    }

    public class LatestResult_football_adapter extends RecyclerView.Adapter<LatestResult_football_adapter.ViewHolder> {

        private View rootview;
        private ArrayList<LatestResultsFootball> LatestResultData;

        LatestResult_football_adapter(ArrayList<LatestResultsFootball> data) {
            this.LatestResultData = data;
        }

        @Override
        public LatestResult_football_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.atelite_latestresult_listitem, parent, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(LatestResult_football_adapter.ViewHolder holder, int position) {
            if (LatestResultData.size() > 0) {
                holder.setItem(LatestResultData.get(position));
            }
        }

        @Override
        public int getItemCount() {
            if (LatestResultData.size() == 0)
                return 1;
            return LatestResultData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageAdd, edit_data;
            private TextView title, comp_name, comp_date, opponent, score;

            public ViewHolder(final View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.result_title);
                comp_name = itemView.findViewById(R.id.comp_name);
                comp_date = itemView.findViewById(R.id.result_date);
                edit_data = itemView.findViewById(R.id.edit_data);
                opponent = itemView.findViewById(R.id.opponent);
                score = itemView.findViewById(R.id.score);
                imageAdd = itemView.findViewById(R.id.add_result);

                edit_data.setVisibility(View.GONE);
                imageAdd.setVisibility(View.GONE);
            }

            void setItem(LatestResultsFootball s) {
                comp_name.setText(s.getNameOfCompetation());
                comp_date.setText(s.getDateOfCompetation());
                opponent.setText(s.getOpponent());
                title.setText(s.getResult());
                score.setText(s.getScore());
            }
        }
    }

    public class LatestResultOtherAdapter extends RecyclerView.Adapter<LatestResultOtherAdapter.ViewHolder> {

        private View rootview;
        private ArrayList<LatestResultBadminton> LatestResultData;

        LatestResultOtherAdapter(ArrayList<LatestResultBadminton> sportsEducationData) {
            this.LatestResultData = sportsEducationData;
        }

        @Override
        public LatestResultOtherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            rootview = LayoutInflater.from(parent.getContext()).inflate(R.layout.atelite_latestresult_listitem, parent, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(LatestResultOtherAdapter.ViewHolder holder, int position) {
            if (LatestResultData.size() > 0) {
                holder.setItem(LatestResultData.get(position));
            }
        }

        @Override
        public int getItemCount() {
            if (LatestResultData.size() == 0)
                return 1;
            return LatestResultData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageAdd, edit_data;
            private TextView title, comp_name, comp_date, opponent, score;

            public ViewHolder(final View itemView) {
                super(itemView);
                edit_data = itemView.findViewById(R.id.edit_data);
                comp_name = itemView.findViewById(R.id.comp_name);
                comp_date = itemView.findViewById(R.id.result_date);
                opponent = itemView.findViewById(R.id.opponent);
                title = itemView.findViewById(R.id.result_title);
                imageAdd = itemView.findViewById(R.id.add_result);
                score = itemView.findViewById(R.id.score);
                edit_data.setVisibility(View.GONE);
                imageAdd.setVisibility(View.GONE);
            }

            void setItem(LatestResultBadminton s) {
                comp_name.setText(s.getNameOfCompetation());
                comp_date.setText(s.getDateOfCompetation());
                opponent.setText(s.getOpponent());
                title.setText(s.getResult() + " In " + s.getRound());
                score.setText(s.getScore());
            }
        }
    }
}

