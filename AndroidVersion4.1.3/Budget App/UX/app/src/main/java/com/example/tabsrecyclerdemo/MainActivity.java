package com.example.tabsrecyclerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> AllTuneNames = new ArrayList<>(Arrays.asList("Beauty and Beast","Lion King","Mary Poppins","Game of Thrones","Ozark"));
    List<Integer> AllTunePics = new ArrayList<>(Arrays.asList(R.drawable.beauty, R.drawable.lionking, R.drawable.marypoppins,R.drawable.gameofthrones, R.drawable.ozark, R.drawable.pause, R.drawable.play));

    List<Tune>AllTunesList = new ArrayList<>();
    List<Tune>MovieTunesList = new ArrayList<>();
    List<Tune>TVShowTunesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddData();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTunes);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);

       GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
       recyclerView.setLayoutManager(gridLayoutManager);

        TuneAdapter tuneAdapter = new TuneAdapter(AllTunesList, this);
        recyclerView.setAdapter(tuneAdapter);

        RecyclerView recyclerView2 = findViewById(R.id.recyclerViewTunes2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        TuneAdapter2 tuneAdapter2 = new TuneAdapter2(AllTunesList, this);
        recyclerView2.setAdapter(tuneAdapter2);

        TabLayout tuneTabs = findViewById(R.id.tabLayout);
        tuneTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tuneTabs.getSelectedTabPosition()){
                    case 0: // change the data of the adapter to all tunes
                        tuneAdapter.ChangeData(AllTunesList);
                        tuneAdapter2.ChangeData(AllTunesList);
                        break;
                    case 1: //change the data of the adapter to movie tunes
                        tuneAdapter.ChangeData(MovieTunesList);
                        tuneAdapter2.ChangeData(MovieTunesList);
                        break;
                    case 2: //change the data of the adapter to tv show tunes
                        tuneAdapter.ChangeData(TVShowTunesList);
                        tuneAdapter2.ChangeData(TVShowTunesList);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void AddData(){
        for (int i=0; i<AllTuneNames.size(); i++){
            Tune eachTune = new Tune(AllTuneNames.get(i),AllTunePics.get(i));
            AllTunesList.add(eachTune);
        }
        MovieTunesList = AllTunesList.subList(0,3);
        TVShowTunesList = AllTunesList.subList(3,5);
    }
}