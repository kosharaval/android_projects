package com.example.koshar_3375final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    List<Concert> concerArraytList = new ArrayList();
    RecyclerView recyclerView;
    CustomConcertAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


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

        ConcertViewModel concertViewModel = new ViewModelProvider(requireActivity()).get(ConcertViewModel.class);
        TextView textViewTitle = view.findViewById(R.id.textViewTitle);

        recyclerView = view.findViewById(R.id.recyclerViewConcert);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        concertViewModel.getConcertInfo().observe(getViewLifecycleOwner(),
                (List<Concert> concertList)->{

                    //adapter = new CustomConcertAdapter(concertList);
                    recyclerView.setAdapter(new CustomConcertAdapter(concertList));
                    concerArraytList = concertList;
                });


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = textViewTitle.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("Title", title);

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
            }
        });
    }
}