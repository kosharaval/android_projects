package com.example.filefrag;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    List<Dog> DogArrayList = new ArrayList();
    String dateString, resourceName, dogName;
    int dogImage;
    LocalDate dogDOB;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listViewItems = view.findViewById(R.id.listViewItems);
        DogViewModel dogViewModel = new ViewModelProvider(requireActivity()).get(DogViewModel.class);

        dogViewModel.getColors().observe(getViewLifecycleOwner(),
                (List<Dog> dogList) -> {
                    Log.d("FRAGDEMO",dogList.size() + " Total Colors in the list");
                    listViewItems.setAdapter(new DogAdapter(dogList));

                    //note this is reference data type
                    DogArrayList = dogList;
                });


        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dogName = DogArrayList.get(position).getDogName();
                dogImage = DogArrayList.get(position).getDogPicDrawable();
                resourceName = getResources().getResourceEntryName(DogArrayList.get(position).getDogPicDrawable());
                LocalDate dob = DogArrayList.get(position).getDob();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy");
                dateString = formatter.format(dob);

                Bundle bundle = new Bundle();
                bundle.putInt("DogImage",dogImage);
                bundle.putString("DogName",dogName);
                bundle.putString("BreedName",resourceName);
                bundle.putString("DOB",dateString);

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
            }
        });

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}