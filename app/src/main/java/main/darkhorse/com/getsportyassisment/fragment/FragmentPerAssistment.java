package main.darkhorse.com.getsportyassisment.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.darkhorse.com.getsportyassisment.R;

/**
 * Created by shekhar on 4/7/18.
 */
public class FragmentPerAssistment extends Fragment
{


    public FragmentPerAssistment newInstance(String param1, String param2) {
        FragmentPerAssistment fragment = new FragmentPerAssistment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);


        fragment.setArguments(args);
        return fragment;
    }
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View rootView;
    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_per_assist, container, false);


        return rootView;

    }

    }
