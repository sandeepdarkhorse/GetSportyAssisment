package main.darkhorse.com.getsportyassisment.performance;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonParser;
import main.darkhorse.com.getsportyassisment.UtilsFile.MyToast;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    //TextView textViewTitle;
    int studentid = 0;
    RecyclerView recyclerViewParent;
    Dialog dialog;
    CustomProgress customProgress;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String module;

    //ArrayList<String> submodules;

    //private OnFragmentInteractionListener mListener;
    private String data;
    private float avg = 0;

    public AnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalysisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalysisFragment newInstance(String param1, String param2, String param3, String param4 ){
        AnalysisFragment fragment = new AnalysisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            module = getArguments().getString(ARG_PARAM3);
            data = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);

        getActivity().setTitle(mParam1.substring(0, 1).toUpperCase() + mParam1.substring(1));

        customProgress = CustomProgress.getInstance();
        //submodules = new ArrayList<>();
        //Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        //textViewTitle = (TextView)toolbar.findViewById(R.id.title);
        Button buttonSuggestion = (Button)rootView.findViewById(R.id.suggestion);
        CardView cardViewSuggestion = (CardView)rootView.findViewById(R.id.suggestion_layout);

        recyclerViewParent = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        Button buttonSave = (Button)rootView.findViewById(R.id.save);

        buttonSuggestion.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        buttonSave.setVisibility(View.GONE);

        Log.e("Tag", module);

        RecyclerView.LayoutManager bestresultdLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewParent.setLayoutManager(bestresultdLayoutManager);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);
        studentid = Integer.parseInt(sharedPreferences.getString(SharedPrefs.STUDENTID,"0"));

        if (!mParam2.equals("edit")) {
            buttonSave.setVisibility(View.GONE);
            cardViewSuggestion.setVisibility(View.GONE);
            getSubmodules();
        }
        else
        {
            loadData();
        }

        return rootView;
    }

    private void getSubmodules()
    {

        ArrayList<String> submodules = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String module1 = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
                int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
                String submodule = JsonParser.getString(jsonObject1, JsonKeys.SUB_MODULE);

                if (module.equals(module1)){

                    if (!submodules.contains(submodule)) {
                        submodules.add(submodule);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerViewParent.setAdapter(new SubModulesAdapter(submodules));

    }

    private ArrayList<PerformanceAssessment> getQuestions(String submodule){

        ArrayList<PerformanceAssessment> performanceAssessments = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String module1 = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
                int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
                String question = JsonParser.getString(jsonObject1,JsonKeys.KEY_QUESTION);
                String submodule1 = JsonParser.getString(jsonObject1, JsonKeys.SUB_MODULE);

                if (module.equals(module1)&& submodule.equals(submodule1)){

                    PerformanceAssessment performanceAssessment = new PerformanceAssessment();
                    performanceAssessment.setQuestion(question);
                    performanceAssessment.setAnswer(answer);

                    performanceAssessments.add(performanceAssessment);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return performanceAssessments;
    }

    private void loadData(){

        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
        ArrayList<String> arrayList = performanceDbHelper.getAllSubModules(module,studentid);

        recyclerViewParent.setAdapter(new SubModulesAdapter(arrayList));

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(mParam1.substring(0, 1).toUpperCase() + mParam1.substring(1));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.suggestion:
                SuggestionDialog.suggestionPopup(getActivity(),mParam1);
                break;
            case R.id.save:
                savePerformance("0");
                break;
        }

    }

    private void savePerformance(String status){

        customProgress.showProgress(getActivity(),  false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);

        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
        String modulesAverage = modulesAvg(performanceDbHelper.getAllData(Integer.parseInt(mParam1)));


        Retrofit retrofit = ApiClient.getClient();
        PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);

        Call<SavePerformanceResponse> getPerformanceModulesResponseCall = apiCall.savePerformanceRequest("save_performance", new SavePerformanceRequest(sharedPreferences.getString(SharedPrefs.USERID,"7"), sharedPreferences.getString(SharedPrefs.STUDENTID,"0"), performanceDbHelper.getAllData(Integer.parseInt(sharedPreferences.getString(SharedPrefs.STUDENTID, "0"))), status,String.valueOf(performanceDbHelper.getRowId(Integer.parseInt(sharedPreferences.getString(SharedPrefs.STUDENTID, "0")))), modulesAverage, String.valueOf(avg)));

        Log.e("publish url", getPerformanceModulesResponseCall.request().url().toString());
        getPerformanceModulesResponseCall.enqueue(new Callback<SavePerformanceResponse>() {
            @Override
            public void onResponse(Call<SavePerformanceResponse> call, Response<SavePerformanceResponse> response) {

                customProgress.hideProgress();

                Log.e("Tag", response.message());
                Log.e("Tag", ""+response.body().getStatus());
                Log.e("Tag", response.body().getMsg());
                Log.e("Tag", response.body().getData().toString());

                if (response.body().getStatus() == 1){
                    MyToast.show(getActivity(),"Performance is saved",true);
                }else {
                    MyToast.show(getActivity(),"Performance is not saved",true);
                }
            }

            @Override
            public void onFailure(Call<SavePerformanceResponse> call, Throwable t) {
                Log.e("Tag", t.toString());
                customProgress.hideProgress();
            }
        });
    }

    private void saveAnswer(PerformanceAssessment performanceAssessment){

        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
        performanceDbHelper.saveAnswer(performanceAssessment);

    }

    private String modulesAvg(String data){
        JSONObject jsonObject = null;
        float techAvg = 0;
        float tactAvg = 0;
        float psyAvg = 0;
        float phyAvg = 0;
        float athlAvg = 0;
        float pareAvg = 0;
        float techCount = 0;
        float tactCount = 0;
        float psyCount = 0;
        float phyCount = 0;
        float athlCount = 0;
        float pareCount = 0;
        float avgCount = 0;
        try {


            jsonObject = new JSONObject();
            JSONObject jsonObject2 =  new JSONObject(data);
            JSONArray jsonArray = jsonObject2.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                switch (jsonObject1.getString("module")){

                    case "tactical":
                        if (!jsonObject1.getString("answer").equals("0")){
                            tactAvg+= Integer.parseInt(jsonObject1.getString("answer"));
                            tactCount++;
                        }
                        break;
                    case "technical":
                        if (!jsonObject1.getString("answer").equals("0")){
                            techAvg+= Integer.parseInt(jsonObject1.getString("answer"));
                            techCount++;
                        }
                        break;
                    case "physical":
                        if (!jsonObject1.getString("answer").equals("0")){
                            phyAvg+= Integer.parseInt(jsonObject1.getString("answer"));
                            phyCount++;
                        }
                        break;
                    case "psychological":
                        if (!jsonObject1.getString("answer").equals("0")){
                            psyAvg+= Integer.parseInt(jsonObject1.getString("answer"));
                            psyCount++;
                        }
                        break;
                    case "parent":
                        if (!jsonObject1.getString("answer").equals("0")){
                            pareAvg+= Integer.parseInt(jsonObject1.getString("answer"));
                            pareCount++;
                        }
                        break;
                    case "athlete":
                        if (!jsonObject1.getString("answer").equals("0")){
                            athlAvg+= Integer.parseInt(jsonObject1.getString("answer"));
                            athlCount++;
                        }
                        break;
                }
            }

            if (tactAvg>0){
                tactAvg = tactAvg/tactCount;
                avg+=tactAvg;
                avgCount++;
            }
            if (techAvg>0){
                techAvg = techAvg/techCount;
                avg+=techAvg;
                avgCount++;
            }
            if (phyAvg>0){
                phyAvg = phyAvg/phyCount;
                avg+=phyAvg;
                avgCount++;
            }
            if (psyAvg>0){
                psyAvg = psyAvg/psyCount;
                avg+=psyAvg;
                avgCount++;
            }
            if (pareAvg>0){
                pareAvg = pareAvg/pareCount;
                avg+=pareAvg;
                avgCount++;
            }
            if (athlAvg>0){
                athlAvg = athlAvg/athlCount;
                avg+=athlAvg;
                avgCount++;
            }

            if (avg>0){
                avg = avg/avgCount;
            }
            jsonObject.put("tactical", String.valueOf(tactAvg));
            jsonObject.put("technical", String.valueOf(techAvg));
            jsonObject.put("physical", String.valueOf(phyAvg));
            jsonObject.put("psychological", String.valueOf(psyAvg));
            jsonObject.put("parent", String.valueOf(pareAvg));
            jsonObject.put("athlete", String.valueOf(athlAvg));





        }catch (Exception e){
            e.printStackTrace();
        }


        return jsonObject.toString();
    }

    @Override
    public void onPause() {
        super.onPause();

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);
//
//        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
//
//        Retrofit retrofit = ApiClient.getClient();
//        PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);
//
//        //Call<GetPerformanceModulesResponse> getPerformanceModulesResponseCall = apiCall.getPerformanceModuleRequest("get_modules", new GetPerformanceModuleRequest(sharedPreferences.getString(SharedPrefs.GENDER,""),sharedPreferences.getString(SharedPrefs.SPORT,""),sharedPreferences.getString(SharedPrefs.DOB,"")));
//
//        Call<SavePerformanceResponse> getPerformanceModulesResponseCall = apiCall.savePerformanceRequest("save_performance", new SavePerformanceRequest(sharedPreferences.getString(SharedPrefs.USERID,""), sharedPreferences.getString(SharedPrefs.STUDENTID,"0"), performanceDbHelper.getAllData(Integer.parseInt(sharedPreferences.getString(SharedPrefs.USERID, "0"))), "0", String.valueOf(performanceDbHelper.getRowId(Integer.parseInt(sharedPreferences.getString(SharedPrefs.USERID, "0"))))));
//
//
//        Log.e("publish url", getPerformanceModulesResponseCall.request().url().toString());
//        getPerformanceModulesResponseCall.enqueue(new Callback<SavePerformanceResponse>() {
//            @Override
//            public void onResponse(Call<SavePerformanceResponse> call, Response<SavePerformanceResponse> response) {
//
//                Log.e("Tag", response.message());
//                Log.e("Tag", ""+response.body().getStatus());
//                Log.e("Tag", response.body().getMsg());
//                Log.e("Tag", response.body().getData().toString());
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<SavePerformanceResponse> call, Throwable t) {
//                Log.e("Tag", t.toString());
//
//            }
//        });

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class SubModulesAdapter extends RecyclerView.Adapter<SubModulesAdapter.ViewHolder> implements View.OnClickListener{

        int i =0;

        ArrayList<String> assessmentArrayList;
        int expanded = -1;

        public SubModulesAdapter(ArrayList<String> assessmentArrayList) {
            this.assessmentArrayList = assessmentArrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.analysis_list_items,viewGroup,false);

            return new ViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i)
        {

            viewHolder.textViewSubmodule.setText(assessmentArrayList.get(i));
            viewHolder.recyclerView.setAdapter(new QuestionsAdapter(assessmentArrayList.get(i)));

        }

        @Override
        public int getItemCount() {
            return assessmentArrayList.size();
        }

        @Override
        public void onClick(View view) {

        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView textViewSubmodule;
            ImageView imageViewArrow;
            RecyclerView recyclerView;



            public ViewHolder(final View itemView) {
                super(itemView);

                textViewSubmodule = (TextView)itemView.findViewById(R.id.submodule);
                imageViewArrow = (ImageView) itemView.findViewById(R.id.arrow);
                recyclerView = (RecyclerView)itemView.findViewById(R.id.recycler_view);

                RecyclerView.LayoutManager bestresultdLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(bestresultdLayoutManager);
                //recyclerViewParent.setAdapter(new QuestionsAdapter());

                recyclerView.setVisibility(View.GONE);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (recyclerView.getVisibility()==View.GONE)
                        {

                            recyclerView.setVisibility(View.VISIBLE);
                            if (expanded>-1){
                                recyclerViewParent.findViewHolderForAdapterPosition(expanded).itemView.performClick();
                            }
                            expanded = getAdapterPosition();
                            Log.e("Tag","Expanded  "+expanded);

                        }
                        else
                        {
                            recyclerView.setVisibility(View.GONE);
                            expanded = -1;
                        }
                    }
                });

            }


        }
    }

    class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder>{

        ArrayList<PerformanceAssessment> performanceAssessment;

        public QuestionsAdapter(String submodule) {

            if (mParam2.equals("edit")){
                PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
                performanceAssessment = performanceDbHelper.getQuestions(module,submodule,studentid);
            }
            else {

                performanceAssessment = getQuestions(submodule);
            }


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.submodule_list_items,viewGroup,false);

            return new ViewHolder(rootView);

        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            viewHolder.textViewQuestion.setText(performanceAssessment.get(i).getQuestion());
            int answer = performanceAssessment.get(i).getAnswer();

            for (int k=0;k<answer;k++)
            {
                viewHolder.imageViews[k].setBackground(getActivity().getResources().getDrawable(R.drawable.circle_backbround));
                viewHolder.textViews[k].setTextColor(getActivity().getResources().getColor(R.color.primary_white));

            }

            for (int j = answer;j<10;j++){
                viewHolder.imageViews[j].setBackground(getActivity().getResources().getDrawable(R.drawable.days_background));
                viewHolder.textViews[j].setTextColor(getActivity().getResources().getColor(R.color.text_color));
            }
        }

        @Override
        public int getItemCount() {
            return performanceAssessment.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView textViewQuestion;
            ImageView [] imageViews;
            TextView [] textViews;

            public ViewHolder(View itemView) {
                super(itemView);

                textViewQuestion = (TextView)itemView.findViewById(R.id.question);
                imageViews = new ImageView[10];
                textViews = new TextView[10];

                for (int j = 0;j<10;j++)
                {
                    String viewID = "image"+ (j+1);
                    Log.e("Tag","id "+viewID);

                    int resID = getResources().getIdentifier(viewID, "id", getActivity().getPackageName());
                    Log.e("Tag","res id "+resID);

                    imageViews[j] = (ImageView)itemView.findViewById(resID);

                    String viewID1 = "text"+ (j+1);
                    Log.e("Tag","id "+viewID);

                    int resID1 = getResources().getIdentifier(viewID1, "id", getActivity().getPackageName());
                    Log.e("Tag","res id "+resID);

                    textViews[j] = (TextView) itemView.findViewById(resID1);
                    if (mParam2.equals("edit")) {
                        imageViews[j].setOnClickListener(this);
                        textViews[j].setOnClickListener(this);
                    }

                }

            }

            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.image1:
                    case R.id.text1:
                        changeColor(1);
                        break;
                    case R.id.image2:
                    case R.id.text2:

                        changeColor(2);
                        break;
                    case R.id.image3:
                    case R.id.text3:

                        changeColor(3);
                        break;
                    case R.id.image4:
                    case R.id.text4:

                        changeColor(4);
                        break;
                    case R.id.image5:
                    case R.id.text5:

                        changeColor(5);
                        break;
                    case R.id.image6:
                    case R.id.text6:

                        changeColor(6);
                        break;
                    case R.id.image7:
                    case R.id.text7:

                        changeColor(7);
                        break;
                    case R.id.image8:
                    case R.id.text8:

                        changeColor(8);
                        break;
                    case R.id.image9:
                    case R.id.text9:

                        changeColor(9);
                        break;
                    case R.id.image10:
                    case R.id.text10:

                        changeColor(10);
                        break;


                }

            }

            private void changeColor(int value){
                for (int i=0;i<value;i++)
                {
                    imageViews[i].setBackground(getActivity().getResources().getDrawable(R.drawable.circle_backbround));
                    textViews[i].setTextColor(getActivity().getResources().getColor(R.color.primary_white));

                }

                for (int j = value;j<10;j++){
                    imageViews[j].setBackground(getActivity().getResources().getDrawable(R.drawable.days_background));
                    textViews[j].setTextColor(getActivity().getResources().getColor(R.color.text_color));


                }

                performanceAssessment.get(getAdapterPosition()).setAnswer(value);

                saveAnswer(performanceAssessment.get(getAdapterPosition()));
            }
        }
    }
}
