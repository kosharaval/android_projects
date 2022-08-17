package com.example.finalexam_kosharaval_300324116;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {

    TextView textViewOutputText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        textViewOutputText = view.findViewById(R.id.textViewOutputText);
        return view;
    }

    public void updateTextSize(int textSize, String textValue) {
        textViewOutputText.setText(textValue);
        textViewOutputText.setTextSize(textSize);
    }
}