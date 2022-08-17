package com.example.fabfilerecycler;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ColorSpecViewModel colorSpecViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ConstraintLayout mainLayout = findViewById(R.id.mainLayout);

        mainLayout.setBackgroundColor(Color.parseColor("#FAFADADA"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ColorDrawable colorDrawable = (ColorDrawable) mainLayout.getBackground();
                int color = colorDrawable.getColor();

                if(color != Color.LTGRAY){
                    mainLayout.setBackgroundColor(Color.LTGRAY);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FAFADADA")));
                }else {
                    mainLayout.setBackgroundColor(Color.parseColor("#FAFADADA"));
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                }

                Snackbar.make(view, "Dont like the background ?", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", (View v)->{
                            if(color == Color.LTGRAY){
                                mainLayout.setBackgroundColor(Color.LTGRAY);
                                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FAFADADA")));
                            }else {
                                mainLayout.setBackgroundColor(Color.parseColor("#FAFADADA"));
                                fab.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            }

                        }).show();
            }
        });

        //Setting up the list of Color spec
        List<ColorSpec> colorSpecs = new ArrayList<>();
        List<String> ColorDesc = new ArrayList<>(Arrays.asList("BLACK","ORANGE","PURPLE"));
        List<Integer> ColorVals = new ArrayList<>(Arrays.asList(R.color.black,
                Color.rgb(255,165,0),
                Color.parseColor("#800080")));

        for(int i=0; i<ColorDesc.size(); i++){
            ColorSpec colorSpec = new ColorSpec(ColorDesc.get(i),ColorVals.get(i));
            colorSpecs.add(colorSpec);
        }

        //Load this color data into the View Model
        colorSpecViewModel = new ViewModelProvider(this).get(ColorSpecViewModel.class);

        //Loads the data into the view model
        colorSpecViewModel.loadColor(colorSpecs);
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
            Toast.makeText(this,"Clicked on Settings",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_search) {
            Toast.makeText(this,"Clicked on Search",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_profile) {
            Toast.makeText(this,"Clicked on Profile",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}