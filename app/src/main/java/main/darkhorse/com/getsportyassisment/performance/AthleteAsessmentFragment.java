package main.darkhorse.com.getsportyassisment.performance;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.FragmentTags;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonParser;
import main.darkhorse.com.getsportyassisment.pieChart.animation.Easing;
import main.darkhorse.com.getsportyassisment.pieChart.charts.PieChart;
import main.darkhorse.com.getsportyassisment.pieChart.components.Legend;
import main.darkhorse.com.getsportyassisment.pieChart.data.PieData;
import main.darkhorse.com.getsportyassisment.pieChart.data.PieDataSet;
import main.darkhorse.com.getsportyassisment.pieChart.data.PieEntry;
import main.darkhorse.com.getsportyassisment.pieChart.formatter.PercentFormatter;
import main.darkhorse.com.getsportyassisment.pieChart.utils.ColorTemplate;

/**
 * Created by sandeepsingh on 23/4/18.
 */

public class AthleteAsessmentFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    ArrayList<String> subModules;
    //TextView textViewTitle;
    // TODO: Rename and change types of parameters
    private String data;
    private String mParam2;
    private JSONArray technicalData;
    private JSONArray tacticalData;
    private JSONArray physicalData;
    private JSONArray psychologicalData;


    //private OnFragmentInteractionListener mListener;

    public AthleteAsessmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AthleteAsessmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AthleteAsessmentFragment newInstance(String param1, String param2) {
        AthleteAsessmentFragment fragment = new AthleteAsessmentFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_athlete_asessment, container, false);

        //Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        //textViewTitle = (TextView)toolbar.findViewById(R.id.title);

        //Log.e("Tag","data =   "+data);
        subModules = new ArrayList<>();

        CardView cardViewTechnical = (CardView)rootView.findViewById(R.id.technical);
        RelativeLayout relativeLayoutTechnical = (RelativeLayout)rootView.findViewById(R.id.technical_layout);
        TextView textViewTechnical = (TextView)rootView.findViewById(R.id.technical_text);
        PieChart pieChartTechnical = (PieChart)rootView.findViewById(R.id.technical_chart);


        CardView cardViewTactical = (CardView)rootView.findViewById(R.id.tactical);
        RelativeLayout relativeLayoutTactical = (RelativeLayout)rootView.findViewById(R.id.tactical_layout);
        TextView textViewTactical = (TextView)rootView.findViewById(R.id.tactical_text);
        PieChart pieChartTactical = (PieChart)rootView.findViewById(R.id.tactical_chart);

        CardView cardViewPhysical = (CardView)rootView.findViewById(R.id.physical);
        RelativeLayout relativeLayoutPhysical = (RelativeLayout)rootView.findViewById(R.id.physical_layout);
        TextView textViewPhysical = (TextView)rootView.findViewById(R.id.physical_text);
        PieChart pieChartPhysical = (PieChart)rootView.findViewById(R.id.physical_chart);

        CardView cardViewPsycho = (CardView)rootView.findViewById(R.id.psycho);
        RelativeLayout relativeLayoutPsycho = (RelativeLayout)rootView.findViewById(R.id.psycho_layout);
        TextView textViewPsycho = (TextView)rootView.findViewById(R.id.psycho_text);
        PieChart pieChartPsycho = (PieChart)rootView.findViewById(R.id.psycho_chart);

        relativeLayoutTechnical.setOnClickListener(this);
        relativeLayoutTactical.setOnClickListener(this);
        relativeLayoutPhysical.setOnClickListener(this);
        relativeLayoutPsycho.setOnClickListener(this);

        getAllSubmodules();

        for (int i=0; i<subModules.size();i++){
            //Log.e("Tag",subModules.get(i));
            switch (subModules.get(i)){
                case "Technical":
                    textViewTechnical.setText(subModules.get(i));
                    cardViewTechnical.setVisibility(View.VISIBLE);
                    technicalData = new JSONArray();
                    showPieData(pieChartTechnical, subModules.get(i));
                    break;
                case "Tactical":
                    textViewTactical.setText(subModules.get(i));
                    cardViewTactical.setVisibility(View.VISIBLE);
                    tacticalData = new JSONArray();
                    showPieData(pieChartTactical, subModules.get(i));

                    break;
                case "Physical":
                    textViewPhysical.setText(subModules.get(i));
                    cardViewPhysical.setVisibility(View.VISIBLE);
                    physicalData = new JSONArray();
                    showPieData(pieChartPhysical, subModules.get(i));

                    break;
                case "Psychological":
                    textViewPsycho.setText(subModules.get(i));
                    cardViewPsycho.setVisibility(View.VISIBLE);
                    psychologicalData = new JSONArray();
                    showPieData(pieChartPsycho, subModules.get(i));
                    break;

            }
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getAllSubmodules(){

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String module = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
                String subModule = JsonParser.getString(jsonObject1, JsonKeys.SUB_MODULE);
                int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
                if (module.equals("athlete")) {

                    if (answer > 0) {

                        if (!subModules.contains(subModule)) {
                            subModules.add(subModule);
                        }
                    }
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showPieData(PieChart pieChart, String subModule){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        //pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

//                pieChart.setCenterTextTypeface(mTfLight);
//                pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(40);

        pieChart.setExtraLeftOffset(0);
        //pieChart.setExtraRightOffset(5f);
        //pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);


        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(0f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
//                pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(12f);

        pieChart.setData(getPieData(subModule));
        pieChart.setDrawSliceText(false);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public PieData getPieData(String subModule){

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<String> submodules = new ArrayList<>();
        ArrayList<GraphData> graphDataArrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JsonKeys.DATA);

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String module1 = JsonParser.getString(jsonObject1, JsonKeys.MODULE);
                String submodule = JsonParser.getString(jsonObject1, JsonKeys.SUB_MODULE);
                int answer = JsonParser.getInt(jsonObject1,JsonKeys.ANSWER);
                String question = JsonParser.getString(jsonObject1, JsonKeys.KEY_QUESTION);
                if (module1.equals("athlete")) {

                    if (submodule.equals(subModule)) {


                        if (answer > 0) {

                            if (submodules.contains(question)) {

                                GraphData graphData = graphDataArrayList.get(submodules.indexOf(question));
                                graphData.setAnswer(graphData.getAnswer() + answer);
                                graphData.setTotal(graphData.getTotal() + 10);

                                graphDataArrayList.set(submodules.indexOf(question), graphData);


                            } else {
                                submodules.add(question);
                                GraphData graphData = new GraphData();
                                graphData.setModule(subModule);
                                graphData.setSubmodule(question);
                                graphData.setAnswer(answer);
                                graphData.setTotal(10);

                                graphDataArrayList.add(graphData);
                            }
                        }

                        switch (submodule){
                            case "Technical":

                                technicalData.put(jsonObject1);
                                break;
                            case "Tactical":

                                tacticalData.put(jsonObject1);

                                break;
                            case "Physical":

                                physicalData.put(jsonObject1);

                                break;
                            case "Psychological":
                                psychologicalData.put(jsonObject1);
                                break;

                        }

                    }
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < graphDataArrayList.size() ; i++) {
            entries.add(new PieEntry(graphDataArrayList.get(i).getAnswer(),graphDataArrayList.get(i).getSubmodule().length()>20?graphDataArrayList.get(i).getSubmodule().substring(0,20):graphDataArrayList.get(i).getSubmodule()));

            Log.e("Tag", "answer = "+ graphDataArrayList.get(i).getAnswer());
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        //dataSet.setDrawIcons(false);

        //dataSet.setSliceSpace(3f);
        //dataSet.setIconsOffset(new MPPointF(0, 40));
        //dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);


        return pieData;

    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
                fragmentManager.beginTransaction().replace(R.id.container,PsychoAnalysisFragment.newInstance("technical","","technical",technicalData.toString())).addToBackStack(FragmentTags.VIEW_TECHNICAL_ANALYSIS).commit();
                break;
            case R.id.tactical_layout:
                fragmentManager.beginTransaction().replace(R.id.container,PsychoAnalysisFragment.newInstance("tactical","","tactical",tacticalData.toString())).addToBackStack("viewTacticalAnalysis").commit();
                break;
            case R.id.physical_layout:
                fragmentManager.beginTransaction().replace(R.id.container,PsychoAnalysisFragment.newInstance("physical","","physical",physicalData.toString())).addToBackStack("viewPhysicalAnalysis").commit();
                break;
            case R.id.psycho_layout:
//                fragmentManager.beginTransaction().replace(R.id.container,AnalysisFragment.newInstance(modules.get(3),"",modules.get(3),data)).addToBackStack("viewPsycologicalAnalysis").commit();

                fragmentManager.beginTransaction().replace(R.id.container,PsychoAnalysisFragment.newInstance("psychological","","psychological",psychologicalData.toString())).addToBackStack("viewPsycologicalAnalysis").commit();
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
