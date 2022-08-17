package com.example.japneets_3175finalpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CartGridViewAdapter extends BaseAdapter {

    List<Concert> concertArrayList = new ArrayList<Concert>();


    public CartGridViewAdapter(List<Concert> concertArrayList) {
        this.concertArrayList = concertArrayList;
    }

    @Override
    public int getCount() {
        return concertArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return concertArrayList.get(position).getConcertId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_external_gird, viewGroup, false);
        }
        TextView textViewShowNameList = view.findViewById(R.id.textViewShowNameGrid);
        TextView textViewShowPriceList = view.findViewById(R.id.textViewShowPriceGrid);
        TextView textViewShowNumTickets= view.findViewById(R.id.textViewShowNumTickets);
        TextView textViewSubTotal = view.findViewById(R.id.textViewSubTotal);

        textViewShowNameList.setText("Concert Name: " + String.valueOf(concertArrayList.get(position).getConcertName()));
        textViewShowPriceList.setText("Price: $" + String.valueOf(concertArrayList.get(position).getPrice()));
        textViewShowNumTickets.setText("NumTix: " + String.valueOf(concertArrayList.get(position).getNumberOfTickets()));
        DecimalFormat df = new DecimalFormat("$#.##");
        textViewSubTotal.setText("Sub Total: " + df.format(concertArrayList.get(position).getSubTotal()));

        return view;
    }
}
