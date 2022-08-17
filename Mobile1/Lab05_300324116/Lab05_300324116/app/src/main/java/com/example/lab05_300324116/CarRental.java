package com.example.lab05_300324116;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CarRental extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_car_rental);

        String [] carRentalLink = {"AVIS Canada","Enerprise","ABC Car Rental","Budget Car Rental","Expedia", "House of Car"};

        setListAdapter(new ArrayAdapter<String>(
                this,
                R.layout.activity_car_rental,
                R.id.list,
                carRentalLink
        ));
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        switch (position)
        {
            case 0:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.avis.ca/en/locations/ca/bc/surrey")));
                break;
            case 1:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.enterprise.com/en/home.html")));
                break;
            case 2:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://abccarrentals.ca/")));
                break;
            case 3:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.budget.ca/en/locations/ca/bc/surrey/bgns2")));
                break;
            case 4:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.expedia.ca/Cars")));
                break;
            case 5:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://themagnificentmile.com")));
                break;
        }

    }
}