package com.example.lec9fragdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

public class FirstFragment extends Fragment {

    List<ColorSpec> colorSpecList;
    List<ColorSpec> FragColorSpecs;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiate the spinner in the first fragment
        Spinner spinnerColors = view.findViewById(R.id.spinnerColors);

        //just to show you can set up a spinner using String[]
        String[] spinnerData = {"1","2","3"};
        spinnerColors.setAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_dropdown_item_1line,
                            spinnerData));

        //you can also set the array of strings from string array in xml resource
        spinnerColors.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.spinnerData2)));

        ColorSpecViewModel colorSpecViewModel = new ViewModelProvider(requireActivity()).get(ColorSpecViewModel.class);

        colorSpecViewModel.getColors().observe(getViewLifecycleOwner(), (List<ColorSpec> colorSpecs)-> {

            Log.d("FRAG_DEMO",colorSpecs.size() + " Total colors in the list");
            spinnerColors.setAdapter(new CustomColorAdapter(colorSpecs));
            FragColorSpecs = colorSpecs;
        });

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the color list, getting the selected color then the value.
                int chosenColorVal = FragColorSpecs.get(spinnerColors.getSelectedItemPosition()).getColorVal();

                Bundle bundle = new Bundle();
                bundle.putInt("COLORVAL", chosenColorVal);

                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
            }
        });
    }
}