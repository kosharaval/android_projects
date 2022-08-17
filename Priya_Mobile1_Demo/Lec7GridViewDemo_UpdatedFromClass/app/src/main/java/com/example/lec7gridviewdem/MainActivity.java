package com.example.lec7gridviewdem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<GalleryImage> AnimalList = new ArrayList<>();
    Toast currToast;
    GridView gridViewImages; //remember cannot do findViewById() because content view is not set


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddData(); //to load the data into the list of animals
       // Toast.makeText(this, "whatever", Toast.LENGTH_SHORT).show();

        gridViewImages = findViewById(R.id.gridViewGallery);
        ImageView imgViewLarge = findViewById(R.id.imgViewLarge);

        ImageAdapter myAdapter = new ImageAdapter(AnimalList);

        //does changing the data in the adapter class, change the data in the main activity

        gridViewImages.setAdapter(myAdapter);

        gridViewImages.setNumColumns(3);
        gridViewImages.setHorizontalSpacing(8);
        gridViewImages.setVerticalSpacing(8);

        //GalleryImage myImage = myAdapter.getItem(2);

        gridViewImages.setOnItemClickListener
                ((AdapterView<?> adapterView, View view, int i, long l) -> {

            imgViewLarge.setImageResource(AnimalList.get(i).getImgPic());
            if (currToast != null){
                currToast.cancel();
            }
            currToast = Toast.makeText(MainActivity.this,
                    "Species: " + AnimalList.get(i).getImgId(),Toast.LENGTH_LONG);
            currToast.show(); //dont forget to show the toast
        });

        //Adapter object
        //has methods getItem or getItemId which can be used in the activity

        //Exercises to try
        //EX 1: Use external layout (linear layout and constraint layout) for the gridView
        //EX 2: Add an ImgPrice field to Gallery Image class (with getters, setters, and constructor changes)

        //EX 3:Create a method in the adapter to find the image with the max, min price and average price
        // Use this method in the activity to get the average price of the adapter data

        //EX 4: try changing the adapter data inside the adapter class (say add 10% to the price)
        //and see if that impacts the data in the main activity

        //EX 5: Look at how to change horizontal space, vertical spacing and num columns
        //for grid view from xml, and for how to change scale type for image view in xml.


    }
    private void AddData(){
        AnimalList.add(new GalleryImage(101,"Gorilla",R.drawable.gorilla));
        AnimalList.add(new GalleryImage(102,"Panda",R.drawable.panda));
        AnimalList.add(new GalleryImage(103,"Eagle",R.drawable.eagle));
        AnimalList.add(new GalleryImage(104,"Panther",R.drawable.panther));
        AnimalList.add(new GalleryImage(105,"Polar Bear",R.drawable.polar));
        AnimalList.add(new GalleryImage(106,"Elephant",R.drawable.elephant));
    }
}