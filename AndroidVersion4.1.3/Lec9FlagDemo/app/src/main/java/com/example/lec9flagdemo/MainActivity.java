package com.example.lec9flagdemo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lec9flagdemo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    ColorSpecViewModel colorSpecViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //have access to content_main.xml elements because it is an include in the activity_main.xml
        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        constraintLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) constraintLayout.getBackground();
                int colorId = colorDrawable.getColor();
                if(colorId != Color.LTGRAY){
                    constraintLayout.setBackgroundColor(Color.LTGRAY);
                    binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FAFAFA")));
                }
                else {
                    constraintLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
                    binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                }
                Snackbar.make(view, "Dont Like the background", Snackbar.LENGTH_LONG)
                        .setAction("Undo", (View v)->{
                            if(colorId != Color.LTGRAY){
                                constraintLayout.setBackgroundColor(Color.LTGRAY);
                                binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FAFAFA")));
                            }
                            else {
                                constraintLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
                                binding.fab.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                            }
                        }).show();
            }
        });

        List<ColorSpec> colorSpecList = new ArrayList<>();
        List<String> colorDesc = new ArrayList<>(Arrays.asList("BLACK","ORANGE","PURPLE"));
        List<Integer> colorVal = new ArrayList<>(Arrays.asList(R.color.black,
                Color.rgb(255,165,0),
                Color.parseColor("#800080")));

        for(int i = 0; i<colorSpecList.size(); i++){
            ColorSpec eachSpec = new ColorSpec(colorDesc.get(i),colorVal.get(i));
            colorSpecList.add(eachSpec);
        }

        //Load this color data into the ViewModel
        colorSpecViewModel = new ViewModelProvider(this).get(ColorSpecViewModel.class);
        colorSpecViewModel.loadColors(colorSpecList);//Loads the data into the view model
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}