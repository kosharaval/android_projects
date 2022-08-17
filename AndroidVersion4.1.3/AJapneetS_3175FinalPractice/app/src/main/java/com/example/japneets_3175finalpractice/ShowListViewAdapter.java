package com.example.japneets_3175finalpractice;

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

public class ShowListViewAdapter extends BaseAdapter {
    //Here adapter data is List of dog objects
    //instead of List<String[]> from previous demos

    List<Concert> concertArrayList = new ArrayList<Concert>();

    public ShowListViewAdapter(List<Concert> concertList) {
        this.concertArrayList = concertList;
    }

    @Override
    public int getCount() {
        return concertArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return concertArrayList.get(i).getConcertId();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_external_list, viewGroup, false);
        }
        TextView textViewShowNameList = view.findViewById(R.id.textViewShowNameGrid);
        TextView textViewShowDateList = view.findViewById(R.id.textViewShowDateList);
        TextView textViewShowPriceList= view.findViewById(R.id.textViewShowPriceGrid);
        ImageView imageViewShowList = view.findViewById(R.id.imageViewShowList);

        imageViewShowList.setImageResource(concertArrayList.get(i).getConcertImage());
        textViewShowNameList.setText(String.valueOf(concertArrayList.get(i).getConcertName()));
        textViewShowPriceList.setText(String.valueOf(concertArrayList.get(i).getPrice()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        textViewShowDateList.setText(formatter.format(concertArrayList.get(i).getConcertDate()));

        return view;

    }
}