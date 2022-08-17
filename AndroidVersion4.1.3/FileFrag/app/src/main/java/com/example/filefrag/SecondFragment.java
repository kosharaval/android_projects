package com.example.filefrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    TextView textViewDogName,textViewDogBreed,textViewDOB;
    ImageView imageViewDog;
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

        textViewDogName = view.findViewById(R.id.textViewDogName);
        textViewDogBreed = view.findViewById(R.id.textViewDogBreed);
        textViewDOB = view.findViewById(R.id.textViewDOB);
        imageViewDog = view.findViewById(R.id.imageViewDog);

        if(getArguments() != null)
        {
            imageViewDog.setImageResource(getArguments().getInt("DogImage"));
            textViewDogName.setText("Dog's Name is : " + getArguments().getString("DogName"));
            textViewDogBreed.setText("Dog's Breed is: " + getArguments().getString("BreedName"));
            textViewDOB.setText("Date of Birth: " + getArguments().getString("DOB"));
        }

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}