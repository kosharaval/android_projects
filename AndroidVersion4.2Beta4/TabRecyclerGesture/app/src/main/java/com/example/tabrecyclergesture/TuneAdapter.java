package com.example.tabrecyclergesture;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TuneAdapter extends RecyclerView.Adapter<TuneAdapter.TuneViewHoler> {

    List<Tune> tuneList;
    Context context;

    Integer CurrentPlayingIndex = -1; //keeps track of current song

    public void ChangeData(List<Tune> newTuneList){
        this.tuneList = newTuneList;
        CurrentPlayingIndex = -1;
        notifyDataSetChanged();
    }

    public TuneAdapter(List<Tune> tuneList, Context context) {
        this.tuneList = tuneList;
        this.context = context;
    }
    @NonNull
    @Override
    public TuneViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tune,parent,false);
        TuneViewHoler viewHolder = new TuneViewHoler(itemView);
        viewHolder.tuneTextView = itemView.findViewById(R.id.textViewTune);
        viewHolder.tuneImageView = itemView.findViewById(R.id.imageViewTune);
        viewHolder.tunePlayPauseImageView = itemView.findViewById(R.id.imageViewplayPause);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TuneAdapter.TuneViewHoler holder, int position) {

        holder.tuneTextView.setText(tuneList.get(position).getTuneName());
        holder.tuneImageView.setImageResource(tuneList.get(position).getTunePic());

        if(CurrentPlayingIndex == position){
            holder.tunePlayPauseImageView.setImageResource(R.drawable.pause);
        }else{
            holder.tunePlayPauseImageView.setImageResource(R.drawable.play);
        }

        holder.tunePlayPauseImageView.setOnClickListener((view)->{
            if(CurrentPlayingIndex == position){
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
        //return 0;
        return tuneList.size();
    }

    public class TuneViewHoler extends RecyclerView.ViewHolder{
        TextView tuneTextView;
        ImageView tuneImageView;
        ImageView tunePlayPauseImageView;


        public TuneViewHoler(@NonNull View itemView) {
            super(itemView);
        }
    }
}
