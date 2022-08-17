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
    int CurrentPlayingInd = -1; //keeps track of current song


    public TuneAdapter2(List<Tune> tuneList, Context context) {
        this.tuneList = tuneList;
        this.context = context;
    }

    public void ChangeData(List<Tune> newTuneList){
        this.tuneList = newTuneList;
        CurrentPlayingInd = -1; //reset current playing index to -1 every time data is changed
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TuneViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tuneitem2,
                        parent,false);
        TuneViewHolder2 myHolder = new TuneViewHolder2(itemView);
        myHolder.tuneTextView = itemView.findViewById(R.id.txtViewTune2);
        myHolder.tuneImageView = itemView.findViewById(R.id.imgViewTune2);
        myHolder.tunePlayPauseImageView = itemView.findViewById(R.id.imgViewPlayPause);


        //While in onBindViewHolder, I have access to position directly,
        //in the onCreateViewHolder, I can get the position
        //by calling holder object getAdapterPosition()
        //You can set up click listener either in the onCreateViewHolder
        //as shown below or in the onBindViewHolder (don't need in both places)

        //onCreateViewHolder click listener set up is more optimal in some cases

        /*
        myHolder.tunePlayPauseImageView.setOnClickListener((view) -> {
            if (CurrentPlayingInd == myHolder.getAdapterPosition()){
                CurrentPlayingInd = -1;
                notifyDataSetChanged();
            } else{
                CurrentPlayingInd = myHolder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });*/

        return myHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull TuneViewHolder2 holder, int position) {
        holder.tuneTextView.setText(tuneList.get(position).getTuneName());
        holder.tuneImageView.setImageResource(tuneList.get(position).getTunePic());
        //To determine whether play image or pause image should be shown,
        //you check the value of CurrentPlayingInd against position
        if (CurrentPlayingInd == position){
            holder.tunePlayPauseImageView.setImageResource(R.drawable.pause);
        } else{
            holder.tunePlayPauseImageView.setImageResource(R.drawable.play);
        }

        //click listeners can be set up in the CreateViewHolder or bind view holder method
        holder.tunePlayPauseImageView.setOnClickListener((view) -> {
            if (CurrentPlayingInd == position){
                //if currently playing item is clicked again
                //needs to NOT play
                CurrentPlayingInd = -1;
                notifyDataSetChanged();
            } else {
                CurrentPlayingInd = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        //return 0;
        return tuneList.size(); //returns the length of the data from the adapter
    }

    public class TuneViewHolder2 extends RecyclerView.ViewHolder{
        TextView tuneTextView;
        ImageView tuneImageView; //image on the left
        ImageView tunePlayPauseImageView; //used for populating the play/pause image on the right

        public TuneViewHolder2(@NonNull View itemView) {
            super(itemView);
        }
    }
}
