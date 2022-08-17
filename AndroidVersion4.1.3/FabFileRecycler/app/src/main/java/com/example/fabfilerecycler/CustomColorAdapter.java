package com.example.fabfilerecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomColorAdapter extends BaseAdapter {

    List<ColorSpec> colorSpecList = new ArrayList<>();

    public CustomColorAdapter(List<ColorSpec> colorSpecList) {
        this.colorSpecList = colorSpecList;
    }

    @Override
    public int getCount() {
        return colorSpecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_external_listview,parent,false);

        }
        TextView textViewItem = convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(colorSpecList.get(position).getColorDesc());
        textViewItem.setTextColor(colorSpecList.get(position).getColorValue());

        return convertView;
    }
}
