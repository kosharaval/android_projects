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
    //data, constructor, implement methods
    //Best to add data after the methods are implemented.


    List<Tune> tuneList;
    Context context;

    public void ChangeData(List<Tune> newTuneList){
        this.tuneList = newTuneList;
        notifyDataSetChanged();//this method notifies the adapter that data has changed,

    }
    //gen
    public TuneAdapter(List<Tune> tuneList, Context context) {
        this.tuneList = tuneList;
        this.context = context;
    }

    @NonNull
    @Override
    public TuneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        //inflate external layout capture it in View.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tuneitem,parent,false);

        //create custom view holder object
        TuneViewHolder viewHolder = new TuneViewHolder(itemView);

        //set the image view and text view from the holder object using findViewById
        viewHolder.tuneTextView = itemView.findViewById(R.id.textViewTune);
        viewHolder.tuneImageView = itemView.findViewById(R.id.imageViewTune);
        viewHolder.tuneItemView = itemView;
        viewHolder.tuneItemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

        viewHolder.tuneItemView.setOnClickListener((view)->{
            if(viewHolder.tuneItemView.getBackground() instanceof ColorDrawable
                    && ((ColorDrawable) viewHolder.tuneItemView.getBackground()).getColor() != Color.LTGRAY){
                viewHolder.tuneItemView.setBackgroundColor(Color.LTGRAY);
            }
            else {
                viewHolder.tuneItemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }
        });

        //return holder object
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TuneAdapter.TuneViewHolder holder, int position) {

        //position corresponds index, ith item of the adapter
        holder.tuneTextView.setText(tuneList.get(position).getTuneName());
        holder.tuneImageView.setImageResource(tuneList.get(position).getTunePic());

        //this could also be a place where you set up default UI elements
        //when the data is repopulated
        holder.tuneItemView.setBackgroundColor(Color.parseColor("#FAFAFA"));

    }

    @Override
    public int getItemCount() {
        //must return the size of your adapter data
        //which is the same as the list from the adapter data
        //The size of the data is determined by the list

        return tuneList.size();
        //if 0 It is logical error, no items will be shown.
        //return 0;
    }


    public class TuneViewHolder extends RecyclerView.ViewHolder{

        TextView tuneTextView;
        ImageView tuneImageView;
        View tuneItemView;

        public TuneViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
