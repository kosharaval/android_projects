package com.example.lab07_300324116;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ImageAdapter.ItemClickListener {

    Integer[] dogBread = {
            R.drawable.bulldog,
            R.drawable.corgi,
            R.drawable.husky,
            R.drawable.pug,
            R.drawable.retriver,
            R.drawable.shepeard
    };

    ImageView pic;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pic = (ImageView) findViewById(R.id.imgLarge);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new ImageAdapter(this,dogBread);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    public void onItemClick(View view, int position)
    {
        pic.setImageResource(adapter.getItem(position));
    }

}