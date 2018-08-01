package main.darkhorse.com.getsportyassisment.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.activity.ActivityVideoLink;
import main.darkhorse.com.getsportyassisment.activity.UserProfile;
import main.darkhorse.com.getsportyassisment.custom_classes.DateConversion;
import main.darkhorse.com.getsportyassisment.model_classes.AssistmentModle;
import main.darkhorse.com.getsportyassisment.model_classes.DataModel;
import main.darkhorse.com.getsportyassisment.model_classes.MyData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAssessmentEventList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAssessmentEventList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAssessmentEventList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    LinearLayoutManager myLayoutManager;
    CustomAdapter adapter;

    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    private OnFragmentInteractionListener mListener;

    public FragmentAssessmentEventList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssessmentEventList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAssessmentEventList newInstance(String param1, String param2) {
        FragmentAssessmentEventList fragment = new FragmentAssessmentEventList();
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
       View rootView =inflater.inflate(R.layout.fragment_assessment_event_list, container, false);

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyler_view);

        myLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(myLayoutManager);


        recyclerView.setHasFixedSize(true);

        myLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.dateArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }

        removedItems = new ArrayList<Integer>();
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);


        return rootView;
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
////            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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


    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

        }


    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        return true;
    }

    private void addRemovedItemToList() {
        int addItemAtListPosition = 3;
        data.add(addItemAtListPosition, new DataModel(
                MyData.nameArray[removedItems.get(0)],
                MyData.dateArray[removedItems.get(0)],
                MyData.id_[removedItems.get(0)],
                MyData.drawableArray[removedItems.get(0)]
        ));
        adapter.notifyItemInserted(addItemAtListPosition);
        removedItems.remove(0);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private ArrayList<DataModel> dataSet;

        public  class MyViewHolder extends RecyclerView.ViewHolder {

            TextView textViewName;
            TextView textViewEvent;
            TextView dateOfAssessment;
            TextView location;
            Button addParticipant;


            public MyViewHolder(View itemView) {
                super(itemView);
                this.textViewName = (TextView) itemView.findViewById(R.id.institute_name);
                this.textViewEvent = (TextView) itemView.findViewById(R.id.event_name);
                this.dateOfAssessment = (TextView) itemView.findViewById(R.id.date_assess);
                this.location=(TextView)itemView.findViewById(R.id.location);
                this.addParticipant=(Button)itemView.findViewById(R.id.add_participant);

            }
        }

        public CustomAdapter(ArrayList<DataModel> data) {
            this.dataSet = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.am_institute_list_item, parent, false);


            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView instituteName = holder.textViewName;
            TextView textViewVersion = holder.dateOfAssessment;
            TextView AssessmentName=holder.textViewEvent;
            TextView location=holder.location;
            Button bttn_addParticipant= holder.addParticipant;

//            instituteName.setText(dataSet.get(listPosition).getSchool_name());
//            textViewVersion.setText(dataSet.get(listPosition).getDate());
//            AssessmentName.setText(dataSet.get(listPosition).getEvent_name());


            bttn_addParticipant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}


