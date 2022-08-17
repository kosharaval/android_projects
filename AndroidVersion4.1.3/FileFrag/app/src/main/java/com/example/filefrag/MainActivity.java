package com.example.filefrag;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    DogViewModel dogViewModel;
    List<Dog> dogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dogList = ReadDogData();
        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        dogViewModel.loadDogInfo(dogList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Dog> ReadDogData(){

        List<Dog> DogList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.doginfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            String csvLine;
            //if you have a header line, you must read it first before any loop fpr parsing is set up

            //remove the header from the file
            String Header = reader.readLine();

            while((csvLine = reader.readLine())!=null){

                String[] row = csvLine.split(",");  //comma separated String[]
                int id = Integer.parseInt(row[0]);

                String dogPicName = row[1]; //this is the drawable resource name
                int dogDrawable = getResources().getIdentifier(dogPicName,"drawable",getPackageName());

                String dogBread = row[2];
                String dogName = row[3];
                String dogDOB = row[4];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
                LocalDate dob = LocalDate.parse(dogDOB,formatter);

                Dog eachDog = new Dog(id,dogBread,dogName,dogDrawable,dob);
                DogList.add(eachDog);
            }
        }catch (IOException e){
            Log.d("Adopt",e.getMessage());
        }catch (NumberFormatException e){
            Log.d("Adopt",e.getMessage());
        }catch (DateTimeException e){
            Log.d("Adopt",e.getMessage());
        }finally {
            //close the input stream
            try {
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error while closing stream");
            }
        }

        return DogList;
    }
}