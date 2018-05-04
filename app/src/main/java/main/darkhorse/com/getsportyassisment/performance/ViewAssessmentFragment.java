package main.darkhorse.com.getsportyassisment.performance;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
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
import main.darkhorse.com.getsportyassisment.UtilsFile.FragmentTags;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonParser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewAssessmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAssessmentFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<String> modules;
    JSONArray psychologicalData;
    JSONArray parentData;
    //TextView textViewTitle;
    //Toolbar toolbar;
    // TODO: Rename and change types of parameters
    private String data;
    private String mParam2;

    CardView cardViewTechnical;
    CardView cardViewTactical;
    CardView cardViewPhysical;
    CardView cardViewPsycho;
    CardView cardViewSelf;
    CardView cardViewParent;
    TextView textViewPhyAvg;
    TextView textViewPsyAvg;
    TextView textViewTechAvg;
    TextView textViewTactAvg;
    TextView textViewSelfAvg;
    TextView textViewParentAvg;



    //private OnFragmentInteractionListener mListener;


    public ViewAssessmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAssessmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAssessmentFragment newInstance(String param1, String param2) {
        ViewAssessmentFragment fragment = new ViewAssessmentFragment();
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
            data = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_assessment, container, false);
        cardViewTechnical = (CardView) rootView.findViewById(R.id.technical_layout);
        cardViewTactical = (CardView)rootView.findViewById(R.id.tactical_layout);
        cardViewPhysical = (CardView)rootView.findViewById(R.id.physical_layout);
        cardViewPsycho = (CardView)rootView.findViewById(R.id.psycho_layout);
        cardViewSelf = (CardView) rootView.findViewById(R.id.self_layout);
        cardViewParent = (CardView) rootView.findViewById(R.id.parent_layout);
        textViewPhyAvg = (TextView) rootView.findViewById(R.id.physical_avg);
        textViewPsyAvg = (TextView) rootView.findViewById(R.id.psycho_avg);
        textViewTechAvg = (TextView) rootView.findViewById(R.id.technical_avg);
        textViewTactAvg = (TextView) rootView.findViewById(R.id.tactical_avg);
        textViewSelfAvg = (TextView) rootView.findViewById(R.id.self_avg);
        textViewParentAvg = (TextView) rootView.findViewById(R.id.parent_avg);

        cardViewTechnical.setOnClickListener(this);
        cardViewTactical.setOnClickListener(this);
        cardViewPhysical.setOnClickListener(this);
        cardViewPsycho.setOnClickListener(this);
        cardViewSelf.setOnClickListener(this);
        cardViewParent.setOnClickListener(this);

        //toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        //textViewTitle = (TextView)toolbar.findViewById(R.id.title);

        //Log.e("Tag","data =   "+data);

        modules = new ArrayList<>();
        psychologicalData = new JSONArray();
        parentData = new JSONArray();


        getAllModules();

        if (modules.contains(JsonKeys.KEY_TECHNICAL)){
            cardViewTechnical.setVisibility(View.VISIBLE);
        }
        if (modules.contains(JsonKeys.KEY_TACTICAL)){
            cardViewTactical.setVisibility(View.VISIBLE);
        }
        if (modules.contains(JsonKeys.KEY_PHYSICAL)){
            cardViewPhysical.setVisibility(View.VISIBLE);
        }
        if (modules.contains(JsonKeys.KEY_PSYCHOLOGICAL)){
            cardViewPsycho.setVisibility(View.VISIBLE);
        }
        if (modules.contains(JsonKeys.KEY_ATHLETE)){
            cardViewSelf.setVisibility(View.VISIBLE);
        }
        if (modules.contains(JsonKeys.KEY_PARENT)){
            cardViewParent.setVisibility(View.VISIBLE);
        }

        try {

            JSONObject jsonObjectAvg = new JSONObject(mParam2);
            Log.e("Tag", "mparam2 "+mParam2);
            textViewTechAvg.setText(jsonObjectAvg.getString(JsonKeys.KEY_TECHNICAL));
            textViewTactAvg.setText(jsonObjectAvg.getString(JsonKeys.KEY_TACTICAL));

            textViewPhyAvg.setText(jsonObjectAvg.getString(JsonKeys.KEY_PHYSICAL));

            textViewPsyAvg.setText(jsonObjectAvg.getString(JsonKeys.KEY_PSYCHOLOGICAL));

            textViewSelfAvg.setText(jsonObjectAvg.getString(JsonKeys.KEY_ATHLETE));

            textViewParentAvg.setText(jsonObjectAvg.getString(JsonKeys.KEY_PARENT));



            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);

            for (int j=0;j<modules.size();j++){

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String module1 = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
                    String submodule = JsonParser.getString(jsonObject1, JsonKeys.SUB_MODULE);
                    int answer = JsonParser.getInt(jsonObject1, JsonKeys.ANSWER);
                    String question = JsonParser.getString(jsonObject1, JsonKeys.KEY_QUESTION);

                    if (module1.equals(modules.get(j))) {
                        if (module1.equals(JsonKeys.KEY_PSYCHOLOGICAL)) {

                            psychologicalData.put(jsonObject1);

                        } else if (module1.equals(JsonKeys.KEY_PARENT)) {

                            parentData.put(jsonObject1);

                        } else {

                        }
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.view_performance));
        //toolbar.setTitle("Performance");
    }

    //    private void loadData(){
//
//        ArrayList<GraphData> graphDataArrayList = new ArrayList<>();
//
//
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);
//
//            for (int i=0; i<jsonArray.length(); i++){
//                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                String module = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
//                String submodule = JsonParser.getString(jsonObject1, JsonKeys.SUB_MODULE);
//                int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
//
//                if (answer>0){
//
//                    if (modules.contains(module)){
//
////                        GraphData graphData = graphDataArrayList.get(modules.indexOf(module));
////                        graphData.setAnswer(graphData.getAnswer()+answer);
////                        graphData.setTotal(graphData.getTotal()+ 10);
////
////                        graphDataArrayList.set(modules.indexOf(module),graphData);
//
//
//                    }else {
//                        modules.add(module);
////                        GraphData graphData = new GraphData();
////                        graphData.setModule(module);
////                        graphData.setAnswer(answer);
////                        graphData.setTotal(10);
////
////                        graphDataArrayList.add(graphData);
//                    }
//                }
//
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//        ArrayList<GraphData> graphDataArrayList = new ArrayList<>();
//
//
//        int answer = 0;
//        int total = 0 ;
//
//        for (int i=0;i<graphDataArrayList.size();i++){
//
//            answer+=graphDataArrayList.get(i).getAnswer();
//            total+=graphDataArrayList.get(i).getTotal();
//        }
//
//        avg = (float) answer/ total*10;
//
//
//    }

    private void getAllModules(){

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String module = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
                int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);

                if (answer>0){

                    if (!modules.contains(module)) {
                        modules.add(module);
                    }
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void showPieData(PieChart pieChart, String module){
//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        //pieChart.setExtraOffsets(5, 10, 5, 5);
//
//        pieChart.setDragDecelerationFrictionCoef(0.95f);
//
////                pieChart.setCenterTextTypeface(mTfLight);
////                pieChart.setCenterText(generateCenterSpannableText());
//
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleColor(Color.WHITE);
//
//        pieChart.setTransparentCircleColor(Color.WHITE);
//        pieChart.setTransparentCircleAlpha(110);
//
//        pieChart.setHoleRadius(40f);
//        pieChart.setTransparentCircleRadius(40);
//
//        pieChart.setExtraLeftOffset(0);
//        //pieChart.setExtraRightOffset(5f);
//        //pieChart.setDrawCenterText(true);
//
//        pieChart.setRotationAngle(0);
//        // enable rotation of the chart by touch
//        pieChart.setRotationEnabled(true);
//        pieChart.setHighlightPerTapEnabled(true);
//
//        // mChart.setUnit(" €");
//        // mChart.setDrawUnitsInChart(true);
//
//        // add a selection listener
//        //pieChart.setOnChartValueSelectedListener(this);
//
//
//        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//        // mChart.spin(2000, 0, 360);
//
//        Legend l = pieChart.getLegend();
////        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
////        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
////        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
//        l.setDrawInside(false);
//        l.setWordWrapEnabled(true);
//        l.setXEntrySpace(0f);
//        l.setXOffset(0f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//
//        // entry label styling
//        pieChart.setEntryLabelColor(Color.WHITE);
////                pieChart.setEntryLabelTypeface(mTfRegular);
//        pieChart.setEntryLabelTextSize(12f);
//
//                pieChart.setData(getPieData(module));
//        pieChart.setDrawSliceText(false);
//
//        // undo all highlights
//        pieChart.highlightValues(null);
//
//        pieChart.invalidate();
//    }

//    private void showPieData(HorizontalBarChart pieChart, String module){
////        pieChart.setUsePercentValues(true);
////        pieChart.getDescription().setEnabled(false);
////        //pieChart.setExtraOffsets(5, 10, 5, 5);
////
////        pieChart.setDragDecelerationFrictionCoef(0.95f);
////
//////                pieChart.setCenterTextTypeface(mTfLight);
//////                pieChart.setCenterText(generateCenterSpannableText());
////
////        pieChart.setDrawHoleEnabled(true);
////        pieChart.setHoleColor(Color.WHITE);
////
////        pieChart.setTransparentCircleColor(Color.WHITE);
////        pieChart.setTransparentCircleAlpha(110);
////
////        pieChart.setHoleRadius(40f);
////        pieChart.setTransparentCircleRadius(40);
////
////        pieChart.setExtraLeftOffset(0);
////        //pieChart.setExtraRightOffset(5f);
////        //pieChart.setDrawCenterText(true);
////
////        pieChart.setRotationAngle(0);
////        // enable rotation of the chart by touch
////        pieChart.setRotationEnabled(true);
////        pieChart.setHighlightPerTapEnabled(true);
////
////        // mChart.setUnit(" €");
////        // mChart.setDrawUnitsInChart(true);
////
////        // add a selection listener
////        //pieChart.setOnChartValueSelectedListener(this);
////
////
////        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
////        // mChart.spin(2000, 0, 360);
////
////        Legend l = pieChart.getLegend();
//////        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//////        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//////        l.setOrientation(Legend.LegendOrientation.VERTICAL);
////        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
////        l.setDrawInside(false);
////        l.setWordWrapEnabled(true);
////        l.setXEntrySpace(0f);
////        l.setXOffset(0f);
////        l.setYEntrySpace(0f);
////        l.setYOffset(0f);
////
////        // entry label styling
////        pieChart.setEntryLabelColor(Color.WHITE);
//////                pieChart.setEntryLabelTypeface(mTfRegular);
////        pieChart.setEntryLabelTextSize(12f);
//
////
//        pieChart.setDrawBarShadow(false);
//
//        pieChart.setDrawValueAboveBar(true);
//
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setMaxVisibleValueCount(60);
//        pieChart.setPinchZoom(false);
//        pieChart.setDrawGridBackground(false);
//
//
//        XAxis xl = pieChart.getXAxis();
//        xl.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
//        xl.setDrawAxisLine(true);
//        xl.setDrawGridLines(false);
//        xl.setGranularity(1f);
//
//        YAxis yl = pieChart.getAxisLeft();
//        yl.setDrawAxisLine(true);
//        yl.setDrawGridLines(true);
//        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
////        yl.setInverted(true);
//
//        YAxis yr = pieChart.getAxisRight();
//        yr.setDrawAxisLine(true);
//        yr.setDrawGridLines(true);
//        yr.setAxisMinimum(0f);
//
//        pieChart.setFitBars(true);
//        pieChart.animateY(2000);
//
//        pieChart.setData(getPieData(module,pieChart));
//        //setData(5,50,pieChart);
//        //pieChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
//
//        Legend l = pieChart.getLegend();
//        l.setEnabled(false);
////        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
////        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
////        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
////        l.setDrawInside(false);
////        l.setFormSize(8f);
////        l.setXEntrySpace(4f);
////        pieChart.setDrawSliceText(false);
////
////        // undo all highlights
////        pieChart.highlightValues(null);
////
////        pieChart.invalidate();
//    }

//    public PieData getPieData(String module){
//
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//        ArrayList<String> submodules = new ArrayList<>();
//        ArrayList<GraphData> graphDataArrayList = new ArrayList<>();
//
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);
//
//            for (int i=0; i<jsonArray.length(); i++){
//                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                String module1 = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
//                String submodule = JsonParser.getString(jsonObject1, JsonKeys.SUB_MODULE);
//                int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
//                String question = JsonParser.getString(jsonObject1, JsonKeys.KEY_QUESTION);
//
//                if (module1.equals(module)) {
//                    if ( module1.equals(JsonKeys.KEY_PSYCHOLOGICAL)){
//
//                        psychologicalData.put(jsonObject1);
//
//                        if (answer > 0) {
//
//                            if (submodules.contains(question)) {
//
//                                GraphData graphData = graphDataArrayList.get(submodules.indexOf(question));
//                                graphData.setAnswer(graphData.getAnswer() + answer);
//                                graphData.setTotal(graphData.getTotal() + 10);
//
//                                graphDataArrayList.set(submodules.indexOf(question), graphData);
//
//
//                            } else {
//                                submodules.add(question);
//                                GraphData graphData = new GraphData();
//                                graphData.setModule(module);
//                                graphData.setSubmodule(question);
//                                graphData.setAnswer(answer);
//                                graphData.setTotal(10);
//
//                                graphDataArrayList.add(graphData);
//                            }
//                        }
//                    }
//                    else if ( module1.equals(JsonKeys.KEY_PARENT)){
//
//                        parentData.put(jsonObject1);
//
//                        if (answer > 0) {
//
//                            if (submodules.contains(question)) {
//
//                                GraphData graphData = graphDataArrayList.get(submodules.indexOf(question));
//                                graphData.setAnswer(graphData.getAnswer() + answer);
//                                graphData.setTotal(graphData.getTotal() + 10);
//
//                                graphDataArrayList.set(submodules.indexOf(question), graphData);
//
//
//                            } else {
//                                submodules.add(question);
//                                GraphData graphData = new GraphData();
//                                graphData.setModule(module);
//                                graphData.setSubmodule(question);
//                                graphData.setAnswer(answer);
//                                graphData.setTotal(10);
//
//                                graphDataArrayList.add(graphData);
//                            }
//                        }
//                    }
//                    else {
//
//
//                        if (answer > 0) {
//
//                            if (submodules.contains(submodule)) {
//
//                                GraphData graphData = graphDataArrayList.get(submodules.indexOf(submodule));
//                                graphData.setAnswer(graphData.getAnswer() + answer);
//                                graphData.setTotal(graphData.getTotal() + 10);
//
//                                graphDataArrayList.set(submodules.indexOf(submodule), graphData);
//
//
//                            } else {
//                                submodules.add(submodule);
//                                GraphData graphData = new GraphData();
//                                graphData.setModule(module);
//                                graphData.setSubmodule(submodule);
//                                graphData.setAnswer(answer);
//                                graphData.setTotal(10);
//
//                                graphDataArrayList.add(graphData);
//                            }
//                        }
//                    }
//                }
//
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (int i = 0; i < graphDataArrayList.size() ; i++) {
//            String description = "";
//            if (graphDataArrayList.get(i).getSubmodule().length()>20){
//                description = graphDataArrayList.get(i).getSubmodule().substring(0,20);
//            }
//            else {
//
//                int a = 20-graphDataArrayList.get(i).getSubmodule().length()+1;
//                description = graphDataArrayList.get(i).getSubmodule();
//                for (int j = 0; j<=a;j++){
//                    description+=" ";
//                }
//
//            }
//            entries.add(new PieEntry(graphDataArrayList.get(i).getAnswer(),description));
//
//            Log.e("Tag", "answer = "+ graphDataArrayList.get(i).getAnswer());
//        }
//
//        PieDataSet dataSet = new PieDataSet(entries, "");
//
//        //dataSet.setDrawIcons(false);
//
//        //dataSet.setSliceSpace(3f);
//        //dataSet.setIconsOffset(new MPPointF(0, 40));
//        //dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
////        for (int c : ColorTemplate.VORDIPLOM_COLORS)
////            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//        //dataSet.setSelectionShift(0f);
//
//        PieData pieData = new PieData(dataSet);
//        pieData.setValueFormatter(new PercentFormatter());
//        pieData.setValueTextSize(11f);
//        pieData.setValueTextColor(Color.WHITE);
//
//
//        return pieData;
//
//    }

//    private void setData(int count, float range, HorizontalBarChart mChart) {
//
//        float barWidth = 9f;
//        float spaceForBar = 10f;
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//
//        for (int i = 0; i < count; i++) {
//            float val = (float) (Math.random() * range);
//            yVals1.add(new BarEntry(i * spaceForBar, val,
//                    getResources().getDrawable(R.drawable.ic_brain)));
//        }
//
//        BarDataSet set1;
//
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
//            set1.setValues(yVals1);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(yVals1, "DataSet 1");
//
//            //set1.setDrawIcons(false);
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            data.setBarWidth(barWidth);
//            mChart.setData(data);
//
//        }
//    }

//    public BarData getPieData(String module, HorizontalBarChart mChart){
//
//        float barWidth = 9f;
//        float spaceForBar = 10f;
//
//        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
//        ArrayList<String> submodules = new ArrayList<>();
//        ArrayList<GraphData> graphDataArrayList = new ArrayList<>();
//
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        final ArrayList<String> labels = new ArrayList<>();
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//
//        for (int i = 0; i < graphDataArrayList.size() ; i++) {
//            entries = new ArrayList<>();
//            String description = "";
//            if (graphDataArrayList.get(i).getSubmodule().length()>20){
//                description = graphDataArrayList.get(i).getSubmodule();
//            }
//            else {
//
//                int a = 20-graphDataArrayList.get(i).getSubmodule().length()+1;
//                description = graphDataArrayList.get(i).getSubmodule();
//                for (int j = 0; j<=a;j++){
//                    description+=" ";
//                }
//            }
//            Log.e("Tag","X values  "+(graphDataArrayList.get(i).getAnswer()*10)/graphDataArrayList.get(i).getTotal());
//            entries.add(new BarEntry(i*spaceForBar,(graphDataArrayList.get(i).getAnswer()*10)/graphDataArrayList.get(i).getTotal()));
//            labels.add(description);
////            BarDataSet dataSet = new BarDataSet(entries,description);
//            BarDataSet dataSet = new BarDataSet(entries,"");
//            dataSet.setColor(colors.get(i));
//            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//
//            dataSets.add(dataSet);
//
//            Log.e("Tag", "answer = "+ graphDataArrayList.get(i).getAnswer());
//        }
//
//
//
//
//
//
//        //dataSet.setDrawIcons(false);
//
//        //dataSet.setSliceSpace(3f);
//        //dataSet.setIconsOffset(new MPPointF(0, 40));
//        //dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
//
//
//        //dataSet.setColors(colors);
//        //dataSet.setSelectionShift(0f);
//
////        IAxisValueFormatter formatter = new IAxisValueFormatter() {
////
////            @Override
////            public String getFormattedValue(float value, AxisBase axis) {
////                try {
////                    Log.e("Tag", "label "+labels.size()+" value "+value);
////                    return labels.get((int) value/10);
////                }catch (Exception e){
////                    return "";
////                }
////            }
////
////        };
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
////        xAxis.setValueFormatter(formatter);
//
//        BarData pieData = new BarData(dataSets);
//        //pieData.setValueFormatter(new PercentFormatter());
//        pieData.setValueTextSize(11f);
//        pieData.setValueTextColor(Color.WHITE);
//        pieData.setBarWidth(barWidth);
//
//        return pieData;
//
//    }

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
        switch (view.getId()){
            case R.id.technical_layout:
                fragmentManager.beginTransaction().replace(R.id.container,AnalysisFragment.newInstance("technical","","technical",data)).addToBackStack(FragmentTags.VIEW_TECHNICAL_ANALYSIS).commit();
                break;
            case R.id.tactical_layout:
                fragmentManager.beginTransaction().replace(R.id.container,AnalysisFragment.newInstance("tactical","","tactical",data)).addToBackStack("viewTacticalAnalysis").commit();
                break;
            case R.id.physical_layout:
                fragmentManager.beginTransaction().replace(R.id.container,AnalysisFragment.newInstance("physical","","physical",data)).addToBackStack("viewPhysicalAnalysis").commit();
                break;
            case R.id.psycho_layout:
//                fragmentManager.beginTransaction().replace(R.id.container,AnalysisFragment.newInstance(modules.get(3),"",modules.get(3),data)).addToBackStack("viewPsycologicalAnalysis").commit();

                fragmentManager.beginTransaction().replace(R.id.container,PsychoAnalysisFragment.newInstance("psychological","","psychological",psychologicalData.toString())).addToBackStack("viewPsycologicalAnalysis").commit();
                break;
            case R.id.self_layout:
                fragmentManager.beginTransaction().replace(R.id.container,AthleteAsessmentFragment.newInstance(data,"")).addToBackStack("viewAthleteAnalysis").commit();
                break;
            case R.id.parent_layout:
                fragmentManager.beginTransaction().replace(R.id.container,PsychoAnalysisFragment.newInstance("parent","","parent",parentData.toString())).addToBackStack("viewParentAnalysis").commit();
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
}
