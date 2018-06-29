package main.darkhorse.com.getsportyassisment.performance;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.MyToast;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewAssessmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAssessmentFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CardView cardViewTechnical;
    CardView cardViewTactical;
    CardView cardViewPhysical;
    CardView cardViewPsycho;
    //    CardView cardViewSelf;
//    CardView cardViewParent;
    TextView textViewPhyAvg;
    TextView textViewPsyAvg;
    TextView textViewTechAvg;
    TextView textViewTactAvg;
    //    TextView textViewSelfAvg;
//    TextView textViewParentAvg;
    //Dialog dialog;
    ArrayList<String> modulesArrayList;
    Button buttonPublish;
    TextView textViewDefault;
    CustomProgress customProgress;
    private float avg = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;


    //private OnFragmentInteractionListener mListener;
    private String mParam2;

    public NewAssessmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAssessmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewAssessmentFragment newInstance(String param1, String param2) {
        NewAssessmentFragment fragment = new NewAssessmentFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_assessment, container, false);
        customProgress = CustomProgress.getInstance();
        cardViewTechnical = (CardView) rootView.findViewById(R.id.technical_layout);
        cardViewTactical = (CardView)rootView.findViewById(R.id.tactical_layout);
        cardViewPhysical = (CardView)rootView.findViewById(R.id.physical_layout);
        cardViewPsycho = (CardView)rootView.findViewById(R.id.psycho_layout);
//        cardViewSelf = (CardView) rootView.findViewById(R.id.self_layout);
//        cardViewParent = (CardView) rootView.findViewById(R.id.parent_layout);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        buttonPublish = (Button)rootView.findViewById(R.id.publish);
        textViewDefault = (TextView)rootView.findViewById(R.id.default_text);
        textViewPhyAvg = (TextView) rootView.findViewById(R.id.physical_avg);
        textViewPsyAvg = (TextView) rootView.findViewById(R.id.psycho_avg);
        textViewTechAvg = (TextView) rootView.findViewById(R.id.technical_avg);
        textViewTactAvg = (TextView) rootView.findViewById(R.id.tactical_avg);
//        textViewSelfAvg = (TextView) rootView.findViewById(R.id.self_avg);
//        textViewParentAvg = (TextView) rootView.findViewById(R.id.parent_avg);

//        Button buttonRecomPhysio = (Button)rootView.findViewById(R.id.physio);
//        Button buttonRecomPsycho = (Button)rootView.findViewById(R.id.recommend_psycho);

        cardViewTechnical.setOnClickListener(this);
        cardViewTactical.setOnClickListener(this);
        cardViewPhysical.setOnClickListener(this);
        cardViewPsycho.setOnClickListener(this);
//        cardViewSelf.setOnClickListener(this);
//        cardViewParent.setOnClickListener(this);
        buttonPublish.setOnClickListener(this);
        buttonPublish.setVisibility(View.GONE);

//        buttonRecomPhysio.setOnClickListener(this);
//        buttonRecomPsycho.setOnClickListener(this);
//
//        buttonRecomPhysio.setVisibility(View.GONE);
//        buttonRecomPsycho.setVisibility(View.GONE);

        modulesArrayList = new ArrayList<>();

//mParam1="53";

        loadModules();

        return rootView;
    }

    public void loadModules() {



        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE,MODE_PRIVATE);

        if (performanceDbHelper.getCount(Integer.parseInt(mParam1))>0)
        {
            loadFromDb(Integer.parseInt(mParam1));
            return;
        }

        fetchDataFromServer(Integer.parseInt(mParam1));
    }

    private void loadFromDb(int studentId)
    {
        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());

        modulesArrayList = performanceDbHelper.getAllModules(studentId);

        for (int i =0;i<modulesArrayList.size();i++){
            displayView(modulesArrayList.get(i));
        }
    }

    private void fetchDataFromServer(final int studentId){
        NetworkStatus network_status = new NetworkStatus(getActivity());
        if (network_status.isConnectingToInternet()){

            customProgress.showProgress(getActivity(),  false);

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE,MODE_PRIVATE);


            Retrofit retrofit = ApiClient.getClient();
            PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);

            Call<GetPerformanceModulesResponse> getPerformanceModulesResponseCall = apiCall.getPerformanceModuleRequest("get_modules",
                    new GetPerformanceModuleRequest(sharedPreferences.getString(SharedPrefs.GENDER,""),
                            sharedPreferences.getString(SharedPrefs.SPORT,""),sharedPreferences.getString(SharedPrefs.DOB,""),
                            sharedPreferences.getString(SharedPrefs.USERID,""),mParam1));




            Log.e("publish url", getPerformanceModulesResponseCall.request().url().toString());
            getPerformanceModulesResponseCall.enqueue(new Callback<GetPerformanceModulesResponse>() {
                @Override
                public void onResponse(Call<GetPerformanceModulesResponse> call, Response<GetPerformanceModulesResponse> response) {

                    Log.e("Tag",response.message());
                    Log.e("Tag",response.body().getStatus());
                    Log.e("Tag", response.body().getMsg());
                    Log.e("Tag",response.body().getData().toString());

                    try {

                        if (response.body().getStatus().equals("1")) {

                            PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
                            performanceDbHelper.saveAllQuestions(response.body().getData().toString(), studentId, 0);
                            loadFromDb(studentId);

                        }
                        else
                        {
                            buttonPublish.setVisibility(View.GONE);
                            textViewDefault.setVisibility(View.VISIBLE);
                        }

                    }catch (Exception e){
                        e.printStackTrace();

                        buttonPublish.setVisibility(View.GONE);
                        textViewDefault.setVisibility(View.VISIBLE);
                    }

                    customProgress.hideProgress();


                }

                @Override
                public void onFailure(Call<GetPerformanceModulesResponse> call, Throwable t) {
                    Log.e("Tag",t.toString());

                    customProgress.hideProgress();

                    MyToast.show(getActivity(),"Some error  has occured. Try again later.",true);

                    buttonPublish.setVisibility(View.GONE);
                    textViewDefault.setVisibility(View.VISIBLE);
                }
            });
        }
        else{
            MyToast.show(getActivity(),"No internet",true);
            customProgress.hideProgress();

        }
    }

    private void displayView(String module){
        switch (module){
            case JsonKeys.KEY_PHYSICAL:
                cardViewPhysical.setVisibility(View.VISIBLE);
                break;
            case JsonKeys.KEY_TECHNICAL:
                cardViewTechnical.setVisibility(View.VISIBLE);
                break;
            case JsonKeys.KEY_TACTICAL:
                cardViewTactical.setVisibility(View.VISIBLE);
                break;
            case JsonKeys.KEY_PSYCHOLOGICAL:
                cardViewPsycho.setVisibility(View.VISIBLE);
                break;
//            case JsonKeys.KEY_ATHLETE:
//                cardViewPsycho.setVisibility(View.VISIBLE);
//                break;
//            case JsonKeys.KEY_PARENT:
//                cardViewPsycho.setVisibility(View.VISIBLE);
//                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(getResources().getString(R.string.new_assessment));
        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);

        if (performanceDbHelper.getCount(Integer.parseInt(sharedPreferences.getString(SharedPrefs.STUDENTID, "0")))>0){
            savePerformance("0");

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);

        if (performanceDbHelper.getCount(Integer.parseInt(sharedPreferences.getString(SharedPrefs.STUDENTID, "0")))>0){
            savePerformance("0");

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
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
        //mListener = null;
    }



    @Override
    public void onClick(View view) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (view.getId())
        {
            case R.id.technical_layout:
                fragmentManager.beginTransaction().replace(R.id.container, AnalysisFragment.newInstance("Technical","edit",JsonKeys.KEY_TECHNICAL,"")).addToBackStack("technicalAnalysis").commit();
                break;
            case R.id.tactical_layout:
                fragmentManager.beginTransaction().replace(R.id.container, AnalysisFragment.newInstance("Tactical","edit",JsonKeys.KEY_TACTICAL,"")).addToBackStack("tacticalAnalysis").commit();
                break;
            case R.id.physical_layout:
                fragmentManager.beginTransaction().replace(R.id.container, AnalysisFragment.newInstance("Physical","edit",JsonKeys.KEY_PHYSICAL,"")).addToBackStack("physicalAnalysis").commit();
                break;
            case R.id.psycho_layout:
                fragmentManager.beginTransaction().replace(R.id.container, PsychoAnalysisFragment.newInstance("Psychological","edit",JsonKeys.KEY_PSYCHOLOGICAL,"")).addToBackStack("psychologicalAnalysis").commit();
                break;
//            case R.id.self_layout:
//                fragmentManager.beginTransaction().replace(R.id.container, PsychoAnalysisFragment.newInstance("Psychological","edit",JsonKeys.KEY_ATHLETE,"")).addToBackStack("psychologicalAnalysis").commit();
//                break;
//            case R.id.parent_layout:
//                fragmentManager.beginTransaction().replace(R.id.container, PsychoAnalysisFragment.newInstance("Psychological","edit",JsonKeys.KEY_PARENT,"")).addToBackStack("psychologicalAnalysis").commit();
//                break;
            case R.id.publish:
                publishPerformance("1");
                break;
//            case R.id.physio:
//                fragmentManager.beginTransaction().replace(R.id.container,RecommendFragment.newInstance("Recommend to Physio","")).addToBackStack("RecommendPhysio").commit();
//                break;
//            case R.id.recommend_psycho:
//                fragmentManager.beginTransaction().replace(R.id.container,RecommendFragment.newInstance("Recommend to Psychologist","")).addToBackStack(FragmentTags.RECOMMEND_PSYCHO).commit();
//                break;
        }

    }



    private void publishPerformance(final String status){

        customProgress.showProgress(getActivity(),  false);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);

        final PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());

        Retrofit retrofit = ApiClient.getClient();
        PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);

        //Call<GetPerformanceModulesResponse> getPerformanceModulesResponseCall = apiCall.getPerformanceModuleRequest("get_modules", new GetPerformanceModuleRequest(sharedPreferences.getString(SharedPrefs.GENDER,""),sharedPreferences.getString(SharedPrefs.SPORT,""),sharedPreferences.getString(SharedPrefs.DOB,"")));
        Call<PublishPerformanceResponse> getPerformanceModulesResponseCall = apiCall.publishPerformanceRequest("publish_performance", new PublishPerformanceRequest(performanceDbHelper.getAllData(Integer.parseInt(mParam1)), status, String.valueOf(performanceDbHelper.getRowId(Integer.parseInt(mParam1)))));


        Log.e("publish url", getPerformanceModulesResponseCall.request().url().toString());
        getPerformanceModulesResponseCall.enqueue(new Callback<PublishPerformanceResponse>() {
            @Override
            public void onResponse(Call<PublishPerformanceResponse> call, Response<PublishPerformanceResponse> response) {

                Log.e("Tag", ""+response.body().getStatus());
                Log.e("Tag", response.body().getMsg());
                Log.e("Tag", ""+response.body().getData().toString());
                customProgress.hideProgress();

                if (response.body().getStatus() == 1){
                    performanceDbHelper.publish(Integer.parseInt(sharedPreferences.getString(SharedPrefs.STUDENTID, "0")));
                    MyToast.show(getActivity(),"Performance is published",true);

                }
                else {
                    MyToast.show(getActivity(),"Performance is not published",true);

                }

                customProgress.hideProgress();



            }

            @Override
            public void onFailure(Call<PublishPerformanceResponse> call, Throwable t) {
                Log.e("Tag", t.toString());
                MyToast.show(getActivity(),"Performance is not saved",true);

                customProgress.hideProgress();

            }
        });
    }

    private void savePerformance(String status){



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);

        PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());

        if (performanceDbHelper.getCount(Integer.parseInt(mParam1))>0) {

            String modulesAverage = modulesAvg(performanceDbHelper.getAllData(Integer.parseInt(mParam1)), performanceDbHelper.getAllModules(Integer.parseInt(mParam1)));

            Retrofit retrofit = ApiClient.getClient();
            PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);

            //Call<GetPerformanceModulesResponse> getPerformanceModulesResponseCall = apiCall.getPerformanceModuleRequest("get_modules", new GetPerformanceModuleRequest(sharedPreferences.getString(SharedPrefs.GENDER,""),sharedPreferences.getString(SharedPrefs.SPORT,""),sharedPreferences.getString(SharedPrefs.DOB,"")));

            Call<SavePerformanceResponse> getPerformanceModulesResponseCall = apiCall.savePerformanceRequest("save_performance", new SavePerformanceRequest(sharedPreferences.getString(SharedPrefs.USERID, "7"), mParam1, performanceDbHelper.getAllData(Integer.parseInt(mParam1)), status, String.valueOf(performanceDbHelper.getRowId(Integer.parseInt(mParam1))), modulesAverage, String.valueOf(avg)));

            Log.e("save url", getPerformanceModulesResponseCall.request().url().toString());
            getPerformanceModulesResponseCall.enqueue(new Callback<SavePerformanceResponse>() {
                @Override
                public void onResponse(Call<SavePerformanceResponse> call, Response<SavePerformanceResponse> response) {

                    //dialog.dismiss();

                    Log.e("Tag", response.message());
                    Log.e("Tag", "" + response.body().getStatus());
                    Log.e("Tag", response.body().getMsg());
                    Log.e("Tag", response.body().getData().toString());

                    if (response.body().getStatus() == 1) {

                        //MyToast.show(getActivity(),"Performance is saved",true);


                    } else {
                        //MyToast.show(getActivity(),"Performance is not saved",true);

                    }

                }

                @Override
                public void onFailure(Call<SavePerformanceResponse> call, Throwable t) {
                    Log.e("Tag", t.toString());

                    //dialog.dismiss();

                }
            });
        }


    }

    private String modulesAvg(String data, ArrayList<String> modulesArrayList){
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

            textViewTechAvg.setText(String.valueOf(techAvg));
            textViewTactAvg.setText(String.valueOf(tactAvg));
            textViewPhyAvg.setText(String.valueOf(phyAvg));
            textViewPsyAvg.setText(String.valueOf(psyAvg));
//            textViewParentAvg.setText(String.valueOf(pareAvg));
//            textViewSelfAvg.setText(String.valueOf(athlAvg));

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

        //publishPerformance("0");
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
}
