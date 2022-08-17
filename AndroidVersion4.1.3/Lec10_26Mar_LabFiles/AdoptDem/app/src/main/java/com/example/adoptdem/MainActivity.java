package com.example.adoptdem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

    List<Dog> DogList = new ArrayList();
    String dateString, resourceName, dogName; //
    TextView txtViewAdoptSumary; // that you cannot do findbyid until setContentView ()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DogList = ReadDogData(); //reading the CSV file

        ListView listViewItems = findViewById(R.id.listViewItems);
        listViewItems.setAdapter(new DogAdapter(DogList));
        txtViewAdoptSumary =findViewById(R.id.txtViewAdoptionSumary);

        listViewItems.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)-> {
            dogName = DogList.get(position).getDogName();
            resourceName = getResources().getResourceEntryName(DogList.get(position).getDogPicDrawable()); //using the drawable id it returns DogList.get(position)

            LocalDate dob = DogList.get(position).getDob();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy");
            dateString = formatter.format(dob);//formatting the dob in different format

            txtViewAdoptSumary.setText("Adopted Dog: " + dogName + "\nResource Name: " + resourceName + "\n DOB: " + dateString);

        });

    }

    //this is because app is created with api 16 but the ofPattern method is introduced after api 26.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Dog> ReadDogData(){

        List<Dog> DogList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.doginfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            String csvLine;
            //if you have a header line, you must read it first before any loop fpr parsing is set up
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