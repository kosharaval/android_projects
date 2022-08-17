package com.example.japneets_3175final;

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

public class PurchaseListViewAdapter extends BaseAdapter {

    List<Course> courseArrayList = new ArrayList<Course>();

    public PurchaseListViewAdapter(List<Course> courseList) {
        this.courseArrayList = courseList;
    }

    @Override
    public int getCount() {
        return courseArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return courseArrayList.get(i).getCourseId();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ext_purchase_listviewitem_layout, viewGroup, false);
        }
        TextView txtViewPurchasedCourseName = view.findViewById(R.id.txtViewPurchasedCourseName);
        TextView txtViewPurchasedCoursePrice = view.findViewById(R.id.txtViewPurchasedCoursePrice);
        TextView txtviewPurchasedNumSessions= view.findViewById(R.id.txtviewPurchasedNumSessions);
        TextView txtViewPurchasedSubTotal = view.findViewById(R.id.txtViewPurchasedSubTotal);
        ImageView imgViewPurchasedDiscount = view.findViewById(R.id.imgViewPurchasedDiscount);

        txtViewPurchasedCourseName.setText(String.valueOf(courseArrayList.get(position).getCourseName()));
        double price = courseArrayList.get(position).getCoursePrice();
        txtViewPurchasedCoursePrice.setText(String.valueOf(price));

        int numSession = courseArrayList.get(position).getNumSession();
        txtviewPurchasedNumSessions.setText(String.valueOf(numSession));
        txtViewPurchasedSubTotal.setText(String.valueOf(courseArrayList.get(position).getSubTotal()));

        if(courseArrayList.get(position).getCourseDiscount() == 1){
            imgViewPurchasedDiscount.setImageResource(R.drawable.discount);
        }

        return view;

    }
}