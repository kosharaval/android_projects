package com.example.lec9fragdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomColorAdapter extends BaseAdapter {
    List<ColorSpec> ColorList = new ArrayList<>();

    public CustomColorAdapter(List<ColorSpec> colorList) {
        ColorList = colorList;
    }

    @Override
    public int getCount() {
        return ColorList.size(); //size of the data
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
           view = LayoutInflater.from(viewGroup.getContext())
                   .inflate(R.layout.layout_extspinnerlistview,
                           viewGroup,false);
        }
        TextView txtViewItem = view.findViewById(R.id.txtViewItem);
        txtViewItem.setText(ColorList.get(i).getColorDesc());
        txtViewItem.setTextColor(ColorList.get(i).getColorVal());
        return view;
    }
}
