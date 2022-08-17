package com.example.koshar_3375final;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.widget.TextView;

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


    ConcertViewModel concertViewModel;
    List<Concert> concertList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtViewNumTix = findViewById(R.id.txtViewNumTix);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Empty Cart", Snackbar.LENGTH_LONG)
                        .setAction("Undo", (View v)->{
                            //txtViewNumTix.setText("0");
                        }).show();
            }
        });

        concertList = ReadConcertInfo();
        concertViewModel = new ViewModelProvider(this).get(ConcertViewModel.class);
        concertViewModel.loadConcertInfo(concertList);
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
    public List<Concert> ReadConcertInfo(){

        List<Concert> ConcertList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.concerts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            String csvLine;
            String Header = reader.readLine();

            //ConcertDrawable,ConcertName,ConcertDate,ConcertTicketPrice
            while((csvLine = reader.readLine())!=null){

                String[] row = csvLine.split(",");  //comma separated String[]

                String concertPic = row[0]; //this is the drawable resource name
                int ConcertDrawable = getResources().getIdentifier(concertPic,"drawable",getPackageName());

                String ConcertName = row[1];
                String ConcertDate = row[2];
                double ConcertTicketPrice = Double.parseDouble(row[3]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
                LocalDate date = LocalDate.parse(ConcertDate,formatter);

                Concert eachDog = new Concert(ConcertDrawable,date,ConcertName,ConcertTicketPrice);
                ConcertList.add(eachDog);
            }
        }catch (IOException e){
            Log.d("Concert",e.getMessage());
        }catch (NumberFormatException e){
            Log.d("Concert",e.getMessage());
        }catch (DateTimeException e){
            Log.d("Concert",e.getMessage());
        }finally {
            //close the input stream
            try {
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException("Error while closing stream");
            }
        }

        return ConcertList;
    }
}