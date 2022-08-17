package com.example.briskdelivery;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    List<String> list;
    List<Integer> pic;

    // Constructor
    public ImageAdapter(List<String> list, List<Integer> pic) {
        this.list = list;
        this.pic = pic;
    }
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater layoutInflater
                    = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.listview_layout,
                    viewGroup,false);
        }TextView txtViewSite = view.findViewById(R.id.hisprice);

        txtViewSite.setText("          "+list.get(i));

        Drawable img = viewGroup.getResources().getDrawable(pic.get(i));

       img.setBounds(0,0,300,300); //this means the top, left 0,0 to bottom right 80,80 is the size of the drawable image

        //this means that the left drawable is the image, and top, right, bottom all have no images
        //in the textview
        txtViewSite.setCompoundDrawables(img,null,null,null);
        txtViewSite.setTextSize(21);

        //this is the spacing around drawable imgage
        txtViewSite.setCompoundDrawablePadding(7); //spacing around the drawable image


     //   txtViewSite.setGravity(Gravity.); //centers the text in the TextView

        return view;
    }


}