package com.example.lec9fragdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiate the txt view in the second fragment
        TextView txtView_Second = view.findViewById(R.id.textview_second);

        //get the color value from the bundle and set text color for text view
        if (getArguments() != null){
            Log.d("FRAGDEMO","color value passed is "
                    + getArguments().getInt("COLORVAL"));
            txtView_Second.setTextColor(getArguments().getInt("COLORVAL"));
        }

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.btn_thirdToListView).setOnClickListener((View v) ->{
            //need to set up navigation graph nodes and actions
            //before we can navigate from second fragment to list view fragment
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_listViewFragment);
        });
    }
}