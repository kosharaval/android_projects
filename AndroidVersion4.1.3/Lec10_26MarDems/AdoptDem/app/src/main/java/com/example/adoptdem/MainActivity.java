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
    List<Dog> DogList = new ArrayList();
    String dateStr, resourceName, dogName; //is to maintain the pick up date that has been picked by the user
    TextView txtViewAdoptSummary; //note, that you cannot do findViewById() until setContentView()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DogList = ReadDogData(); //reading the csv file

        ListView listViewItems = findViewById(R.id.listViewItems);
        listViewItems.setAdapter(new DogAdapter(DogList));

        txtViewAdoptSummary = findViewById(R.id.txtViewAdoptionSumary);

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dogName = DogList.get(i).getDogName();
                resourceName = getResources()
                                .getResourceEntryName(DogList.get(i).getDogPicDrawable()); //using the drawable identifier, it returns the resource name

                LocalDate dob = DogList.get(i).getDob();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy");
                dateStr = formatter.format(dob); //formatting the dob in a different format

                txtViewAdoptSummary.setText("Adopted Dog: " + dogName
                        + "\nResource Name: " + resourceName + "\nDob: " + dateStr);

                //For those that are curious as to how date picker dialog works, here is the code
                //suppose we want a date picker dialog, it works with calendar class
                //We need a onDateListener for that dialog - which says what method is to be executed one a date is selected
                //from the picker dialog

                DatePickerDialog.OnDateSetListener onDateSetListener
                            = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //here i - year, i3 - day of the month,
                        // i1 - month - calendar month goes from 0-11 but LocalDate month
                        //goes from 1-12, so we need to do i1+1 to get the correct month
                        //in LocalDate

                        LocalDate pickedDate = LocalDate.of(i, i1+1,i2);
                        String pickedDateStr = DateTimeFormatter.ofPattern("MMM-d-yyyy")
                                                            .format(pickedDate);

                        txtViewAdoptSummary.setText("Adopted Dog: " + dogName +
                                "\nResource Name: " + resourceName +
                                "\nDob: " + dateStr +
                                "\nPick up date: " + pickedDateStr);

                    }
                };

                //now that on date set listener is set up, we can create a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        onDateSetListener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); //disables all dates prior to today
                datePickerDialog.show(); //shows the date picker dialog object we created

                //when the date picker dialog object is shown, and the
                //user selects a date, then the onDateSetListener is triggered executing the
                //onDateSet method that we have defined above to get the pickedDate and display
                //it using DateTimeFormatter in the text view to include pick up date
                //formatted to String based on format pattern



            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Dog> ReadDogData(){
        List<Dog> DogsList = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.doginfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            //if you have header line,
            // you must read it first before any loop for parsing is set up

            while((csvLine = reader.readLine()) != null){
                String[] row = csvLine.split(","); //comma separated String[]
                int id = Integer.parseInt(row[0]);
                String dogPicName = row[1]; //this is the drawable resource name

                int dogDrawable = getResources().getIdentifier(dogPicName,
                                                    "drawable",getPackageName());
                String dogBreed = row[2];
                String dogName = row[3];
                String dogDobStr = row[4];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
                LocalDate dob = LocalDate.parse(dogDobStr,formatter);

                Dog eachDog = new Dog(id,dogBreed,dogName,dogDrawable,dob);
                DogsList.add(eachDog);

            }
        } catch (IOException ex){
            Log.d("ADOPT",ex.getMessage());
        } catch (NumberFormatException ex){
            Log.d("ADOPT",ex.getMessage());
        } catch(DateTimeException ex){
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