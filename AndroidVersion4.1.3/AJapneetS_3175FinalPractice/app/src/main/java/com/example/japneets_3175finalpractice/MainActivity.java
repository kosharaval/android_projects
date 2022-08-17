package com.example.japneets_3175finalpractice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    List<Concert> concertList = new ArrayList<>();
    SQLiteDatabase ConcertDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDB();
        createTables();

        concertList = ReadConcertInfo();

        Button buttonShowCart = findViewById(R.id.buttonShowCart);
        ListView listViewConerts = findViewById(R.id.listViewConcerts);
        listViewConerts.setAdapter(new ShowListViewAdapter(concertList));

        listViewConerts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            updateConcertTickets(concertList.get(position).getConcertId());

            }
        });

        buttonShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

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

                String[] col = csvLine.split(",");  //comma separated String[]
                int ConcertId = Integer.parseInt(col[0]);;

                String concertPic = col[1]; //this is the drawable resource name
                int ConcertDrawable = getResources().getIdentifier(concertPic,"drawable",getPackageName());

                String ConcertName = col[2];
                String ConcertDate = col[3];
                String ConcertTicketPrice = col[4];
                double price = Double.parseDouble(ConcertTicketPrice);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
                LocalDate date = LocalDate.parse(ConcertDate,formatter);

                Concert concert = new Concert(ConcertId,ConcertDrawable,date,ConcertName,price);
                ConcertList.add(concert);

                addConcertDetails(ConcertId,ConcertDrawable,ConcertName,ConcertDate,ConcertTicketPrice);
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

    private void createDB(){
        try{
            ConcertDb = openOrCreateDatabase("Concert.db",MODE_PRIVATE,null);
            Toast.makeText(this, "Database ready", Toast.LENGTH_SHORT).show();
        } catch (Exception ex){
            Log.e("DB DEMO", ex.getMessage());
        }
    }

    private void createTables(){
        try {
            //ConcertDrawable,ConcertName,ConcertDate,ConcertTicketPrice
            String setPRAGMAForeignKeysOn = "PRAGMA foreign_keys=ON;";
            String dropConcertTableCmd =  "DROP TABLE IF EXISTS " + "concerts;";
            String createConcertTableCmd = "CREATE TABLE concerts " +
                    "(concertId TEXT, concertDrawableName TEXT, concertName TEXT,concertDate TEXT,concertTicketPrice TEXT, concerttix INTEGER, PRIMARY KEY (concertId))" ;
            ConcertDb.execSQL(setPRAGMAForeignKeysOn);
            ConcertDb.execSQL(dropConcertTableCmd);
            ConcertDb.execSQL(createConcertTableCmd);

        } catch (Exception ex){
            Log.d("Database Error: ", ex.getMessage());
        }
    }

    private void addConcertDetails(int ID, int ConcertDrawable,String ConcertName,String ConcertDate,String ConcertTicketPrice){
        long result;
        ContentValues val = new ContentValues();
        val.put("concertId",ID); //just adding a D in front of the id before inserting into DB
        val.put("concertDrawableName",ConcertDrawable);
        val.put("concertName",ConcertName);
        val.put("concertDate",ConcertDate);
        val.put("concertTicketPrice",ConcertTicketPrice);
        val.put("concerttix",0);

        try{
            result = ConcertDb.insert("concerts",null,val);
            if (result != -1){
                Log.d("DB DEMO", "rowid = " + result + " inserted concert with id " + ID);
            } else{
                Log.d("DB DEMO","Error inserting concert with id " + ID);
            }
        } catch (Exception ex){
            Log.d("DB DEMO","Error adding concert for " + ID);
        }
    }

    private void updateConcertTickets(int ID) {
        int numRecs = 0;
        String concertID = String.valueOf(ID);
        String queryStr = "SELECT * " +
                "FROM concerts WHERE concertId = ?;";
        try {
            Cursor cursor = ConcertDb.rawQuery(queryStr, new String[]{concertID});

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String courseId = cursor.getString(0);
                    int numTix = cursor.getInt(5);
                    ContentValues val = new ContentValues();
                    val.put("concerttix", numTix + 1);
                    ConcertDb.update("concerts", val,
                            "concertId = ?", new String[]{concertID});
                    //numRecs++;
                    cursor.moveToNext();
                }
                //numRecs = cursor.getCount(); //you can also use getCount() of cursor to return the number of recs that match this stud id

            }
        } catch (Exception ex) {
            Log.d("DB DEMO", "Updating student grade records failed.." + ex.getMessage());
        }
        //return numRecs;
    }

}