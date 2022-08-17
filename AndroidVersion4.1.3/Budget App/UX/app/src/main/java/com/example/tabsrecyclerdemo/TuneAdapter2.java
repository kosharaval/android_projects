package com.example.tabsrecyclerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TuneAdapter2 extends RecyclerView.Adapter<TuneAdapter2.TuneViewHolder2> {

    List<Tune> tuneList;
    Context context;

    Integer CurrentPlayingIndex = -1; //keeps track of current song

    public void ChangeData(List<Tune> newTuneList){
        this.tuneList = newTuneList;
        CurrentPlayingIndex = -1;
        notifyDataSetChanged();
    }

    public TuneAdapter2(List<Tune> tuneList, Context context) {
        this.tuneList = tuneList;
        this.context = context;
    }

    @NonNull
    @Override
    public TuneViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tuneitem2,parent,false);

        TuneViewHolder2 viewHolder2 = new TuneViewHolder2(itemView);
        viewHolder2.tuneTextView = itemView.findViewById(R.id.textViewTune2);
        viewHolder2.tuneImageView = itemView.findViewById(R.id.imageViewTune2);
        viewHolder2.tunePlayPauseImageView = itemView.findViewById(R.id.imageViewplayPause);

        //While in OnBindViewHolder, I have access to position directly;
        //in the OnCreateViewHolder, I can get the position by calling holder object getAdapterPosition()
        //you can set up the onClickListener either in the onCreateViewHolder
        //as shown below or in the onBindViewHolder(dont need in both places)

        //onCreateViewHOlder click listener set up is more optimal in some cases
        
//        viewHolder2.tunePlayPauseImageView.setOnClickListener((view)->{
//            if(CurrentPlayingIndex==viewHolder2.getAdapterPosition()){
//                CurrentPlayingIndex = -1;
//                notifyDataSetChanged();
//            }else {
//                CurrentPlayingIndex = viewHolder2.getAdapterPosition();
//                notifyDataSetChanged();
//            }
//        });

        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull TuneAdapter2.TuneViewHolder2 holder, int position) {

        holder.tuneTextView.setText(tuneList.get(position).getTuneName());
        holder.tuneImageView.setImageResource(tuneList.get(position).getTunePic());
        //To determine whether play image or pause image should be shown;
        //you check the value of CurrentPlayingIndex against position;

        if(CurrentPlayingIndex == position){
            holder.tunePlayPauseImageView.setImageResource(R.drawable.pause);
        }else{
            holder.tunePlayPauseImageView.setImageResource(R.drawable.play);
        }

        //click listener can be set up in the CreateViewHolder orr bind view holder method
        holder.tunePlayPauseImageView.setOnClickListener((view)->{
            if(CurrentPlayingIndex == position){
                //if currently playing item is clicked again it needs to NOT play
                CurrentPlayingIndex = -1;
                notifyDataSetChanged();
            }else {
                CurrentPlayingIndex = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {

        return  tuneList.size(); //returns the length of the data from adapter
        //return 0;
    }

    public class TuneViewHolder2 extends RecyclerView.ViewHolder{

        TextView tuneTextView;
        ImageView tuneImageView;
        ImageView tunePlayPauseImageView;

        public TuneViewHolder2(@NonNull View itemView) {
            super(itemView);
        }
    }
}
