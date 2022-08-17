package com.example.tabsrecyclerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> AllTuneNames = new ArrayList<>(Arrays.asList("Beauty and the Beast","Lion King",
                                            "Mary Poppins","Game of Thrones","Ozark"));
    List<Integer> AllTunePics = new ArrayList<>(Arrays.asList(R.drawable.beauty,R.drawable.lionking,
                                            R.drawable.marypoppins, R.drawable.gameofthrones, R.drawable.ozark ));

    //Creating three list of tune objects for each of the three lists
    List<Tune> AllTunesList = new ArrayList<>();
    List<Tune> MovieTunesList = new ArrayList<>();
    List<Tune> TVShowTunesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddData();

        RecyclerView recyclerViewTunes = findViewById(R.id.recyclerViewTunes);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerViewTunes.setLayoutManager(lm);

        //layout can be linear layout or grid layout for recycler view
        /*GridLayoutManager gm = new GridLayoutManager(this,2);
        recyclerViewTunes.setLayoutManager(gm);*/

        //grid layout manager is commented because
        //I want to use linear layout manager for tuneAdapter2 scenario
        //because there are three horizontal views, space is limited in grid

       // TuneAdapter tuneAdapter = new TuneAdapter(AllTunesList, this);

        //Creating tuneAdapter object belonging to the second adapter tuneAdapter2 class
        TuneAdapter2 tuneAdapter = new TuneAdapter2(AllTunesList,this);

        recyclerViewTunes.setAdapter(tuneAdapter);

        TabLayout tuneTabs = findViewById(R.id.tabLayout); //corresponds to the tab layout
        tuneTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tuneTabs.getSelectedTabPosition()) {
                    case 0: //change the data of the adapter to all tunes
                        tuneAdapter.ChangeData(AllTunesList);
                        break;
                    case 1: //change the data of the adapter to movie tunes
                        tuneAdapter.ChangeData(MovieTunesList);
                        //you can notify data set change from activity
                        //or inside the adapter class
                        //both are not necessary
                        tuneAdapter.notifyDataSetChanged();
                        break;
                    case 2: //change the data to tv show tunes
                        tuneAdapter.ChangeData((TVShowTunesList));
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
        for (int i = 0; i < AllTuneNames.size(); i++){
            Tune eachTune = new Tune(AllTuneNames.get(i),AllTunePics.get(i));
            AllTunesList.add(eachTune);
        }
        MovieTunesList = AllTunesList.subList(0,3);
        TVShowTunesList = AllTunesList.subList(3,5);
        //subList first param is starting index, second param is the exclusive upper bound index
    }
}