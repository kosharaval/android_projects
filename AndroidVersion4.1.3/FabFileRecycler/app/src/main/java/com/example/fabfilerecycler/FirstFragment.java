package com.example.fabfilerecycler;

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

   List<ColorSpec> fragColorSpecList;

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

        Spinner spinnerColor = view.findViewById(R.id.spinnerColor);

//        String[] spinnerData = {"1","2","3"};
//        spinnerColor.setAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                spinnerData));


//        spinnerColor.setAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,
//                ));

        //Load this color data into the View Model
        ColorSpecViewModel colorSpecViewModel = new ViewModelProvider(requireActivity()).get(ColorSpecViewModel.class);
        colorSpecViewModel.getColors().observe(getViewLifecycleOwner(),
                (List<ColorSpec> colorSpecs) -> {
                Log.d("FRAGDEMO",colorSpecs.size() + " Total Colors in the list");
                spinnerColor.setAdapter(new CustomColorAdapter(colorSpecs));

                //note this is reference data type
                fragColorSpecList = colorSpecs;
        });

        //Loads the data into the view model
        //colorSpecViewModel.loadColor();

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int chossenColorVal = fragColorSpecList.get(spinnerColor.getSelectedItemPosition()).getColorValue();

                Bundle bundle = new Bundle();
                bundle.putInt("COLORVAL",chossenColorVal);

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
            }
        });
    }
}