package com.example.tabsrecyclerdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TuneAdapter extends RecyclerView.Adapter<TuneAdapter.TuneViewHolder> {

    //Best to add data after methods are implemented (after creation of custom view holder inner class)

    List<Tune> tuneList;
    Context context;

    public void ChangeData(List<Tune> newTuneList){
        this.tuneList = newTuneList;
        notifyDataSetChanged(); //this method notifies the adapter that data has changed, so it calls onBindViewHolder to populate data again
    }

    //generate constructor after data is added


    public TuneAdapter(List<Tune> tuneList, Context context) {
        this.tuneList = tuneList;
        this.context = context;
    }

    @NonNull
    @Override
    public TuneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        //inflate external layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                                                R.layout.layout_tuneitem,
                                                parent,false);
        //create custom view holder object
        TuneViewHolder myHolder = new TuneViewHolder(itemView);

        //set the image view and textview from the holder object using findViewById
        myHolder.tuneTextView = itemView.findViewById(R.id.txtViewTune);
        myHolder.tuneImageView = itemView.findViewById(R.id.imgViewTune);
        myHolder.tuneItemView = itemView;

        myHolder.tuneItemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

        myHolder.tuneItemView.setOnClickListener((view) -> {
            if (myHolder.tuneItemView.getBackground() instanceof ColorDrawable
               && ((ColorDrawable) myHolder.tuneItemView.getBackground()).getColor()
                                                        != Color.LTGRAY){
                myHolder.tuneItemView.setBackgroundColor(Color.LTGRAY);
            } else{
                myHolder.tuneItemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }
        });

        //return holder object
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TuneViewHolder holder, int position) {
        //position corresponds index, ith item of the adapter
        holder.tuneTextView.setText(tuneList.get(position).getTuneName());
        holder.tuneImageView.setImageResource(tuneList.get(position).getTunePic());

        //this could also be a place where you set up default UI elements
        //when the data is repopulated
        holder.tuneItemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
    }

    @Override
    public int getItemCount() {
        //getItemCount() must return the size of your adapter data
        //which is the same as the list from the adapter data
        return tuneList.size();
        //return 0; //if getItenCount() returns 0, it is a logical error, no items will be shown on recyclerView
    }

    //data
    //constructor
    //implement methods
    //Only implement the adapter methods after creating the inner custom view holder class


    //first creating inner custom view holder
    public class TuneViewHolder extends RecyclerView.ViewHolder {
        //Three objects
        TextView tuneTextView;
        ImageView tuneImageView;
        View tuneItemView;

        public TuneViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
