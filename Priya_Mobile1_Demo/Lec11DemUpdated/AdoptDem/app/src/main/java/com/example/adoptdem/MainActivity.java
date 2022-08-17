package com.example.adoptdem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    List<Dog> DogsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DogsList = ReadDogData();

        ListView listViewItems = findViewById(R.id.listViewItems);
        listViewItems.setAdapter(new DogAdapter(DogsList));

        TextView txtViewAdoptSummary = findViewById(R.id.txtViewAdoptionSumary);

        listViewItems.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            String dogName = DogsList.get(i).getDogName();
            String resourceName = getResources()
                                .getResourceEntryName(DogsList.get(i).getDogPicDrawable());
            LocalDate dob = DogsList.get(i).getDob();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy");

            String dobStr = formatter.format(dob); //formatted date of birth String

            txtViewAdoptSummary.setText("Adopted Dog: " + dogName
                                + "\nResource Name: " + resourceName
                                + "\nDob: " + dobStr);

            DatePickerDialog.OnDateSetListener onDateSetListener
                    = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    //this is the method that is executed once the date has been selected by the user
                    //when the date picker dialog is shown
                    //i - year the user selected, i1 - month (calendar) that the user selected,
                    // goes from 0-11
                    //i2 - day of the month that the user selected
                    LocalDate pickedDate = LocalDate.of(i,i1+1,i2); //i1+1 because LocalDate month goes from 1-12 but calendar month goes from 0-11
                    String pickedDateStr = DateTimeFormatter.ofPattern("MMM-d-yyyy").format(pickedDate);

                    txtViewAdoptSummary.setText("Adopted Dog: " + dogName
                            + "\nResource Name: " + resourceName
                            + "\nDob: " + dobStr
                            + "\nPicked Date: " + pickedDateStr);

                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                                                            onDateSetListener,
                                                            Calendar.getInstance().get(Calendar.YEAR),
                                                            Calendar.getInstance().get(Calendar.MONTH),
                                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); //the current time stamp is set as the min date for the date picker
            datePickerDialog.show(); //make sure you show the datePicker dialog

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Dog> ReadDogData(){
        List<Dog> DogsList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.doginfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;

            //note no header line, so we can set up while loop directly
            //that has parsing numeric data etc.
            while((csvLine = reader.readLine()) != null){
                String[] row = csvLine.split(","); //comma separated String[]
                int id = Integer.parseInt(row[0]);
                String dogPicName = row[1]; //this is the drawable resource name
                //get drawable identifier (int) from dogPicName (resource name)
                int dogDrawable = getResources().getIdentifier(dogPicName,
                                                "drawable",getPackageName());

                String dogBreed = row[2];
                String dogName = row[3];
                String dogDobStr = row[4];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
                //formatter is needed for parsing String into LocalDate and for formatting LocalDate as a String
                LocalDate dob = LocalDate.parse(dogDobStr,formatter);

                Dog eachDog = new Dog(id,dogBreed,dogName,dogDrawable,dob);
                DogsList.add(eachDog);

            }
        } catch (IOException ex){
            Log.d("ADOPT",ex.getMessage());
        } catch (NumberFormatException ex){
            Log.d("ADOPT",ex.getMessage());
        } catch (DateTimeException ex){
            Log.d("ADOPT",ex.getMessage());
        } finally{
            //close the input stream and throw an exception if there is an issue
            try{
                inputStream.close();
            } catch (IOException ex){
                throw new RuntimeException("Error while closing stream...");
            }
        }
        return DogsList;
    }

}