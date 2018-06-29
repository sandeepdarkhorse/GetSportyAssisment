package main.darkhorse.com.getsportyassisment.performance;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import main.darkhorse.com.getsportyassisment.UtilsFile.FragmentTags;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonParser;
import main.darkhorse.com.getsportyassisment.UtilsFile.NetworkStatus;
import main.darkhorse.com.getsportyassisment.UtilsFile.SharedPrefs;
import main.darkhorse.com.getsportyassisment.custom_classes.CustomProgress;
import main.darkhorse.com.getsportyassisment.pieChart.charts.BarChart;


import main.darkhorse.com.getsportyassisment.pieChart.components.Legend;
import main.darkhorse.com.getsportyassisment.pieChart.components.XAxis;
import main.darkhorse.com.getsportyassisment.pieChart.components.YAxis;

import main.darkhorse.com.getsportyassisment.pieChart.data.BarData;
import main.darkhorse.com.getsportyassisment.pieChart.data.BarDataSet;
import main.darkhorse.com.getsportyassisment.pieChart.data.BarEntry;
import main.darkhorse.com.getsportyassisment.pieChart.data.Entry;
import main.darkhorse.com.getsportyassisment.pieChart.highlight.Highlight;
import main.darkhorse.com.getsportyassisment.pieChart.interfaces.datasets.IBarDataSet;

import main.darkhorse.com.getsportyassisment.pieChart.listener.OnChartValueSelectedListener;
import main.darkhorse.com.getsportyassisment.pieChart.utils.ColorTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerformanceAssessmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerformanceAssessmentFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //TextView textViewTitle;
    Dialog dialog;
    ArrayList<DisplayPerformance> arrayList;
    PerformanceAdapter performanceAdapter;
    TextView textViewDefault;
    CustomProgress customProgress;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabNew;
    //Toolbar toolbar;



    //private OnFragmentInteractionListener mListener;
//    private Button btNewAssessment;

    public PerformanceAssessmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerformanceAssessmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerformanceAssessmentFragment newInstance(String param1, String param2) {
        PerformanceAssessmentFragment fragment = new PerformanceAssessmentFragment();
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

    String dob,gender,studentId,sport;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_performance_assessment, container, false);
        customProgress = CustomProgress.getInstance();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.performance_list);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
//        btNewAssessment = (Button)rootView.findViewById(R.id.new_assessment);
        textViewDefault = (TextView)rootView.findViewById(R.id.default_text);
        fabNew = (FloatingActionButton) rootView.findViewById(R.id.new_assessment);
       // mParam1="53";//this value pass static to run code

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE,MODE_PRIVATE);
        mParam1=sharedPreferences.getString("studentid","");
       // mParam1="53";
        dob=sharedPreferences.getString("dob","");
        sport=sharedPreferences.getString("sport","");

        //toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        getActivity().setTitle(getResources().getString(R.string.performance));
        //getActivity().setTitle(getActivity().getResources().getString(R.string.performance));

        //textViewTitle = (TextView)toolbar.findViewById(R.id.title);

        RecyclerView.LayoutManager bestresultdLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(bestresultdLayoutManager);

        arrayList = new ArrayList<>();

        performanceAdapter = new PerformanceAdapter(arrayList);

        recyclerView.setAdapter(performanceAdapter);

//        btNewAssessment.setOnClickListener(this);
        fabNew.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("studentlist_classid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.clear();
        editor1.commit();

        loadData();

        return rootView;
    }

    private void loadData(){

        arrayList.clear();

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefs.PERFORMANCE, MODE_PRIVATE);

        //PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());

//        int rowId= performanceDbHelper.getRowId(Integer.parseInt(sharedPreferences.getString(SharedPrefs.STUDENTID,"0")));
//
//        if (rowId>0){
//
//            //hashMap.put(rowId, performanceDbHelper.getAllQuestions(Integer.parseInt(sharedPreferences.getString("studentid","0"))));
//        }


        NetworkStatus network_status = new NetworkStatus(getActivity());
        if (network_status.isConnectingToInternet()){

            customProgress.showProgress(getActivity(),  false);

            final PerformanceDbHelper performanceDbHelper = new PerformanceDbHelper(getActivity());
            int count = performanceDbHelper.getCount(Integer.parseInt(mParam1));


            Retrofit retrofit = ApiClient.getClient();
            PerformanceApiCall apiCall = retrofit.create(PerformanceApiCall.class);

            if (count > 0){
                count = 0;
            }
            else {
                count = 1;
            }

            //Call<GetPerformanceModulesResponse> getPerformanceModulesResponseCall = apiCall.getPerformanceModuleRequest("get_modules", new GetPerformanceModuleRequest(sharedPreferences.getString("gender",""),sharedPreferences.getString("sport",""),sharedPreferences.getString("dob",""),sharedPreferences1.getString("user_id",""),sharedPreferences.getString("studentid","")));
            Call<ViewPerformanceResponse> getPerformanceModulesResponseCall = apiCall.viewPerformanceRequest("view_performance", new ViewPerformanceRequest(mParam1,String.valueOf(count)));


            Log.e("publish url", getPerformanceModulesResponseCall.request().url().toString());
            getPerformanceModulesResponseCall.enqueue(new Callback<ViewPerformanceResponse>() {
                @Override
                public void onResponse(Call<ViewPerformanceResponse> call, Response<ViewPerformanceResponse> response) {

                    Log.e("Tag",response.message());
                    Log.e("Tag","status = "+response.body().getStatus());
                    Log.e("Tag", response.body().getMsg());
                    Log.e("Tag",response.body().getData().toString());
                    Log.e("Tag", "next  = "+response.body().getNext_assessment());

                    int count = performanceDbHelper.getCount(Integer.parseInt(mParam1));
                    Log.e("Tag","number of counts:: "+count);

                    int saveCount = 0;
                    if (response.body().getStatus() == 1){
                        try {

                            textViewDefault.setVisibility(View.GONE);
                            Log.e("Tag","data in perform assesment: "+response.body().getData().toString());

                            JSONArray jsonArray = new JSONArray(response.body().getData().toString());

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String name = JsonParser.getString(jsonObject, JsonKeys.NAME);
                                int id = JsonParser.getInt(jsonObject, JsonKeys.ID);
                                int coachId = JsonParser.getInt(jsonObject, JsonKeys.COACH_ID);
                                int athleteId = JsonParser.getInt(jsonObject, JsonKeys.ATHLETE_ID);
                                String data = JsonParser.getString(jsonObject, JsonKeys.DATA);
                                int status = JsonParser.getInt(jsonObject, JsonKeys.STATUS);
                                String dateCreated = JsonParser.getString(jsonObject, JsonKeys.DATE_CREATED);
                                String datePublished = JsonParser.getString(jsonObject, JsonKeys.DATE_PUBLISHED);
                                Log.e("Tag","name :"+name+"id :"+id+"coachId :"+coachId+"athlete id :"+athleteId+"data:: "+data);



                                if (JsonParser.getInt(jsonObject, JsonKeys.STATUS)==0){
                                    saveCount = 1;

                                    if (JsonParser.getInt(jsonObject, JsonKeys.COACH_ID) == Integer.parseInt(sharedPreferences.getString(SharedPrefs.USERID,"0"))&& count == 0){
                                        performanceDbHelper.saveAllQuestions(data, athleteId, status);
                                    }
                                }
//                                else {
                                DisplayPerformance displayPerformance = new DisplayPerformance();
                                displayPerformance.setCoachName(name);
                                displayPerformance.setId(id);
                                displayPerformance.setCoachId(coachId);
                                displayPerformance.setAthleteId(athleteId);
                                displayPerformance.setData(data);
                                displayPerformance.setStatus(status);
                                displayPerformance.setCreateDate(dateCreated);
                                displayPerformance.setPublishDate(datePublished);
                                displayPerformance.setAvg(JsonParser.getString(jsonObject, "avg"));
                                displayPerformance.setModules_avg(JsonParser.getString(jsonObject, "modules_avg"));
                                arrayList.add(displayPerformance);

                                //}

                            }

                            performanceAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        textViewDefault.setVisibility(View.VISIBLE);
                    }

                    if (count>0 || saveCount ==1){
                        //loadFromDb(studentId, 0);

                        //btNewAssessment.setVisibility(View.VISIBLE);
//                        btNewAssessment.setText("Edit Assessment");
                    }
                    else if (response.body().getNext_assessment()>0)
                    {
//                        btNewAssessment.setText("Next Assessment in "+response.body().getNext_assessment()+" days");
//                        btNewAssessment.setClickable(false);
                    }
                    else{

                        //btNewAssessment.setVisibility(View.VISIBLE);
//                        btNewAssessment.setText("New Assessment");
//                        btNewAssessment.setClickable(true);


                    }
                    customProgress.hideProgress();
                }

                @Override
                public void onFailure(Call<ViewPerformanceResponse> call, Throwable t) {
                    Log.e("Tag",t.toString());

                    customProgress.hideProgress();

//                    MyToast.show(getActivity(),"Some error  has occured. Try again later.",true);
                    textViewDefault.setVisibility(View.VISIBLE);

                }
            });
        }
        else{

//            MyToast.show(getActivity(),"No internet",true);
            textViewDefault.setVisibility(View.VISIBLE);


        }

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


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

        switch (view.getId()){
            case R.id.new_assessment:

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, NewAssessmentFragment.newInstance(mParam1,"")).addToBackStack("new_assessment").commit();

                break;
        }

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

    private class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.ViewHolder> {

        View rootview;
        ArrayList<DisplayPerformance> displayPerformances;

        public PerformanceAdapter(ArrayList<DisplayPerformance> displayPerformances) {
            this.displayPerformances = displayPerformances;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            rootview = LayoutInflater.from(getContext()).inflate(R.layout.performance_list_item, viewGroup, false);
            return new ViewHolder(rootview);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            DisplayPerformance displayPerformance = new DisplayPerformance();
            displayPerformance  = displayPerformances.get(i);
            viewHolder.textViewCoach.setText(displayPerformance.getCoachName());
            if (displayPerformance.getStatus() == 0){
                viewHolder.textViewDate.setText(displayPerformance.getCreateDate());
            }
            else {
                viewHolder.textViewDate.setText(displayPerformance.getPublishDate());
            }
            viewHolder.textViewAvg.setText(displayPerformance.getAvg());
            Log.e("Tag", displayPerformance.getModules_avg());
            viewHolder.barChart.setData(getPieData(viewHolder.barChart, displayPerformance.getModules_avg()));

//
//            viewHolder.textViewAvg.setText("Average rating - "+String.format("%.2f",calculateAverage(displayPerformance.getData())));

            //viewHolder.pieChart.setData(getPieData(displayPerformance.getData()));



        }

        @Override
        public int getItemCount() {
            //return arrayList.size();
            return displayPerformances.size();
        }

//        public float calculateAverage(String data){
//            float avg = 0.0f;
//
//            ArrayList<String> modules = new ArrayList<>();
//            ArrayList<GraphData> graphDataArrayList = new ArrayList<>();
//
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);
//
//                for (int i=0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    String module = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
//                    int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
//
//                    if (answer>0){
//
//                        if (modules.contains(module)){
//
//                            GraphData graphData = graphDataArrayList.get(modules.indexOf(module));
//                            graphData.setAnswer(graphData.getAnswer()+answer);
//                            graphData.setTotal(graphData.getTotal()+ 10);
//
//                            graphDataArrayList.set(modules.indexOf(module),graphData);
//
//
//                        }else {
//                            modules.add(module);
//                            GraphData graphData = new GraphData();
//                            graphData.setModule(module);
//                            graphData.setAnswer(answer);
//                            graphData.setTotal(10);
//
//                            graphDataArrayList.add(graphData);
//                        }
//                    }
//
//                }
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            int answer = 0;
//            int total = 0 ;
//
//            for (int i=0;i<graphDataArrayList.size();i++){
//
//                answer+=graphDataArrayList.get(i).getAnswer();
//                total+=graphDataArrayList.get(i).getTotal();
//            }
//
//            avg = (float) answer/ total*10;
//            return  avg;
//        }

//        public PieData getPieData(String data){
//
//            ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//            ArrayList<String> modules = new ArrayList<>();
//            ArrayList<GraphData> graphDataArrayList = new ArrayList<>();
//
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);
//
//                for (int i=0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    String module = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
//                    int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
//
//                    if (answer>0){
//
//                    if (modules.contains(module)){
//
//                        GraphData graphData = graphDataArrayList.get(modules.indexOf(module));
//                        graphData.setAnswer(graphData.getAnswer()+answer);
//                        graphData.setTotal(graphData.getTotal()+ 10);
//
//                        graphDataArrayList.set(modules.indexOf(module),graphData);
//
//
//                    }else {
//                        modules.add(module);
//                        GraphData graphData = new GraphData();
//                        graphData.setModule(module);
//                        graphData.setAnswer(answer);
//                        graphData.setTotal(10);
//
//                        graphDataArrayList.add(graphData);
//                    }
//                    }
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//            // the chart.
//            for (int i = 0; i < graphDataArrayList.size() ; i++) {
//                entries.add(new PieEntry(graphDataArrayList.get(i).getAnswer(),graphDataArrayList.get(i).getModule()));
//            }
//
//            PieDataSet dataSet = new PieDataSet(entries, "");
//
//            //dataSet.setDrawIcons(false);
//
//            //dataSet.setSliceSpace(3f);
//            //dataSet.setIconsOffset(new MPPointF(0, 40));
//            //dataSet.setSelectionShift(5f);
//
//            // add a lot of colors
//
//            ArrayList<Integer> colors = new ArrayList<Integer>();
//
//
//            for (int c : ColorTemplate.JOYFUL_COLORS)
//                colors.add(c);
//
//            for (int c : ColorTemplate.COLORFUL_COLORS)
//                colors.add(c);
//
//            for (int c : ColorTemplate.LIBERTY_COLORS)
//                colors.add(c);
//
//            for (int c : ColorTemplate.PASTEL_COLORS)
//                colors.add(c);
//
//            colors.add(ColorTemplate.getHoloBlue());
//
//            dataSet.setColors(colors);
//            //dataSet.setSelectionShift(0f);
//
//            PieData pieData = new PieData(dataSet);
//            pieData.setValueFormatter(new PercentFormatter());
//            pieData.setValueTextSize(11f);
//            pieData.setValueTextColor(Color.WHITE);
//
//
//            return pieData;
//
//        }

        public class ViewHolder extends RecyclerView.ViewHolder implements OnChartValueSelectedListener
        {

            private TextView textViewDate;
            private TextView textViewAvg;
            private TextView textViewCoach;
            private BarChart barChart;




            public ViewHolder(View itemView) {
                super(itemView);

                textViewDate = (TextView)itemView.findViewById(R.id.date);
                textViewAvg = (TextView)itemView.findViewById(R.id.avg);
                textViewCoach = (TextView)itemView.findViewById(R.id.coach_name);
                barChart = (BarChart) itemView.findViewById(R.id.piechart);

                barChart.setDrawBarShadow(false);

                barChart.setDrawValueAboveBar(true);

                barChart.getDescription().setEnabled(false);
                barChart.setMaxVisibleValueCount(60);
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(false);


                XAxis xl = barChart.getXAxis();
                xl.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
                xl.setDrawAxisLine(true);
                xl.setDrawGridLines(false);
                xl.setGranularity(1f);

                YAxis yl = barChart.getAxisLeft();
                yl.setDrawAxisLine(true);
                yl.setDrawGridLines(true);
                yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

                YAxis yr = barChart.getAxisRight();
                yr.setDrawAxisLine(true);
                yr.setDrawGridLines(true);
                yr.setAxisMinimum(0f);

                barChart.setFitBars(true);
                barChart.animateY(2000);

                //setData(5,50,pieChart);
                //pieChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

                Legend l = barChart.getLegend();
                l.setEnabled(true);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container,  ViewAssessmentFragment.newInstance(displayPerformances.get(getAdapterPosition()).getData(),displayPerformances.get(getAdapterPosition()).getModules_avg())).addToBackStack(FragmentTags.VIEW_ASSESSMENT_FRAGMENT).commit();
                    }
                });

            }



            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        }

        BarData getPieData(BarChart mChart, String modulesAvg){

            float barWidth = 9f;
            float spaceForBar = 10f;
            BarData pieData = null;

            try {
                JSONObject jsonObject = new JSONObject(modulesAvg);
                ArrayList<Integer> colors = new ArrayList<Integer>();
                ArrayList<BarEntry> entries = new ArrayList<BarEntry>();


                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);

                colors.add(ColorTemplate.getHoloBlue());

                final ArrayList<String> labels = new ArrayList<>();
                ArrayList<IBarDataSet> dataSets = new ArrayList<>();

                String description = "";

                description = JsonKeys.KEY_TECHNICAL;

                Log.e("Tag", "Json object "+jsonObject.toString());

                Log.e("Tag","X values  "+JsonParser.getString(jsonObject,JsonKeys.KEY_TECHNICAL));
                entries.add(new BarEntry(1*spaceForBar,Float.parseFloat(JsonParser.getString(jsonObject,JsonKeys.KEY_TECHNICAL))));
                labels.add(description);
//            BarDataSet dataSet = new BarDataSet(entries,description);
                BarDataSet dataSet = new BarDataSet(entries,"");
                dataSet.setColor(colors.get(1));
                dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                dataSets.add(dataSet);

                description = JsonKeys.KEY_TACTICAL;
                Log.e("Tag","X values  "+JsonParser.getString(jsonObject,JsonKeys.KEY_TACTICAL));
                entries.add(new BarEntry(2*spaceForBar,Float.parseFloat(JsonParser.getString(jsonObject,JsonKeys.KEY_TACTICAL))));
                labels.add(description);
//            BarDataSet dataSet = new BarDataSet(entries,description);
                dataSet = new BarDataSet(entries,"");
                dataSet.setColor(colors.get(2));
                dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataSets.add(dataSet);

                description = JsonKeys.KEY_PHYSICAL;
                Log.e("Tag","X values  "+JsonParser.getString(jsonObject,JsonKeys.KEY_PHYSICAL));
                entries.add(new BarEntry(3*spaceForBar,Float.parseFloat(JsonParser.getString(jsonObject,JsonKeys.KEY_PHYSICAL))));
                labels.add(description);
//            BarDataSet dataSet = new BarDataSet(entries,description);
                dataSet = new BarDataSet(entries,"");
                dataSet.setColor(colors.get(3));
                dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataSets.add(dataSet);

                description = JsonKeys.KEY_PSYCHOLOGICAL;
                Log.e("Tag","X values  "+JsonParser.getString(jsonObject,JsonKeys.KEY_PSYCHOLOGICAL));
                entries.add(new BarEntry(4*spaceForBar,Float.parseFloat(JsonParser.getString(jsonObject,JsonKeys.KEY_PSYCHOLOGICAL))));
                labels.add(description);
//            BarDataSet dataSet = new BarDataSet(entries,description);
                dataSet = new BarDataSet(entries,"");
                dataSet.setColor(colors.get(4));
                dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataSets.add(dataSet);

                description = JsonKeys.KEY_ATHLETE;
                Log.e("Tag","X values  "+JsonParser.getString(jsonObject,JsonKeys.KEY_ATHLETE));
                entries.add(new BarEntry(5*spaceForBar,Float.parseFloat(JsonParser.getString(jsonObject,JsonKeys.KEY_ATHLETE))));
                labels.add(description);
//            BarDataSet dataSet = new BarDataSet(entries,description);
                dataSet = new BarDataSet(entries,"");
                dataSet.setColor(colors.get(5));
                dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataSets.add(dataSet);


                description = JsonKeys.KEY_PARENT;
                Log.e("Tag","X values  "+JsonParser.getString(jsonObject,JsonKeys.KEY_PARENT));
                entries.add(new BarEntry(6*spaceForBar,Float.parseFloat(JsonParser.getString(jsonObject,JsonKeys.KEY_PARENT))));
                labels.add(description);
//            BarDataSet dataSet = new BarDataSet(entries,description);
                dataSet = new BarDataSet(entries,"");
                dataSet.setColor(colors.get(6));
                dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataSets.add(dataSet);







                //dataSet.setDrawIcons(false);

                //dataSet.setSliceSpace(3f);
                //dataSet.setIconsOffset(new MPPointF(0, 40));
                //dataSet.setSelectionShift(5f);

                // add a lot of colors



                //dataSet.setColors(colors);
                //dataSet.setSelectionShift(0f);

//        IAxisValueFormatter formatter = new IAxisValueFormatter() {
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                try {
//                    Log.e("Tag", "label "+labels.size()+" value "+value);
//                    return labels.get((int) value/10);
//                }catch (Exception e){
//                    return "";
//                }
//            }
//
//        };

                XAxis xAxis = mChart.getXAxis();
                xAxis.setAvoidFirstLastClipping(true);
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//        xAxis.setValueFormatter(formatter);

                pieData = new BarData(dataSets);
                //pieData.setValueFormatter(new PercentFormatter());
                pieData.setValueTextSize(11f);
                pieData.setValueTextColor(Color.WHITE);
                pieData.setBarWidth(barWidth);

            } catch (JSONException e) {
                e.printStackTrace();
            }



            return pieData;

        }

    }
}