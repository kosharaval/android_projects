package com.example.reza.addfragmenttoanactivityusingxml;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Reza on 2018-02-18.
 */

public class HelloFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hello, container, false);

        //Here you can write your own code to initialize any views or widgets you might have in your fragment

        return view;
    }
}
