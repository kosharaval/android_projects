package com.example.japneets_3175final;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CourseGridViewAdapter extends BaseAdapter {

    List<Course> courseList = new ArrayList<Course>();


    public CourseGridViewAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return courseList.get(position).getCourseId();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ext_course_gridviewitem_layout, viewGroup, false);
        }
        TextView txtViewCourseNameAndDate = view.findViewById(R.id.txtViewCourseNameAndDate);
        TextView txtViewCoursePrice = view.findViewById(R.id.txtViewCoursePrice);

        ImageView imgViewDiscount = view.findViewById(R.id.imgViewDiscount);
        ImageView imgViewCourse = view.findViewById(R.id.imgViewCourse);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        txtViewCourseNameAndDate.setText(String.valueOf(courseList.get(position).getCourseName()) +
                "\n " + formatter.format(courseList.get(position).getCourseDate()));

        txtViewCoursePrice.setText(String.valueOf(courseList.get(position).getCoursePrice()));
        imgViewCourse.setImageResource(courseList.get(position).getCourseDrawable());

        if(courseList.get(position).getCourseDiscount() == 1){
            imgViewDiscount.setImageResource(R.drawable.discount);
                    }

        return view;
    }
}
