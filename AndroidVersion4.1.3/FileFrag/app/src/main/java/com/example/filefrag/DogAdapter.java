package com.example.filefrag;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DogAdapter extends BaseAdapter {
    //Here adapter data is List of dog objects
    //instead of List<String[]> from previous demos

    List<Dog> DogsList = new ArrayList<Dog>();

    public DogAdapter(List<Dog> DogsList) {
        this.DogsList = DogsList;
    }

    @Override
    public int getCount() {
        return DogsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return DogsList.get(i).getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_dogitem, viewGroup, false);
        }
        TextView txtViewId = view.findViewById(R.id.txtViewId);
        TextView txtViewDogName = view.findViewById(R.id.txtViewName);
        TextView txtViewDogBreed= view.findViewById(R.id.txtViewBreed);
        TextView txtViewDOB = view.findViewById(R.id.txtViewDOB);

        ImageView imgViewDogPic = view.findViewById(R.id.imgViewDogPic);
        imgViewDogPic.setImageResource(DogsList.get(i).getDogPicDrawable());
        txtViewId.setText(String.valueOf(DogsList.get(i).getId()));
        txtViewDogName.setText(DogsList.get(i).getDogName());
        txtViewDogBreed.setText(DogsList.get(i).getDogBreed());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        txtViewDOB.setText(formatter.format(DogsList.get(i).getDob()));

        return view;

    }
}
