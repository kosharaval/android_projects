package com.example.koshar_3375final;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomConcertAdapter extends RecyclerView.Adapter<CustomConcertAdapter.ViewHolder> {

    List<Concert> concertList = new ArrayList<>();
    Context context;

    int counter = 0;

    public CustomConcertAdapter(List<Concert> concertList) {
        this.concertList = concertList;
    }

    public void ChangeData(List<Concert> concertList){
        this.concertList = concertList;
        counter = 0; //reset current playing index to -1 every time data is changed
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_external, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.imgViewConcert.setImageResource(concertList.get(position).getConcertImage());
        holder.txtViewConcertDetails.setText(concertList.get(position).getConcertName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        holder.txtViewConcertDate.setText(String.valueOf(concertList.get(position).getConcertDate()));
        holder.txtViewConcertPrice.setText(String.valueOf(concertList.get(position).getPrice()));
        holder.txtViewNumTix.setText(String.valueOf(counter));

        //double price = concertList.get(position).getPrice();
        //NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        //holder.txtViewConcertPrice.setText(defaultFormat.format(price));
        holder.imgViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                //int couter=0;
                //concertList.set(position).setNumberOfTickets(couter);
                holder.txtViewNumTix.setText(String.valueOf(counter));
            }
        });

        holder.imgViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                holder.txtViewNumTix.setText(String.valueOf(counter));
            }
        });
    }

    @Override
    public int getItemCount() {
        return concertList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgViewConcert;
        TextView txtViewConcertDate;
        TextView txtViewConcertDetails;
        TextView txtViewConcertPrice;
        TextView txtViewNumTix;
        ImageView imgViewRemove;
        ImageView imgViewAdd;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewConcert =  itemView.findViewById(R.id.imgViewConcert);
            txtViewConcertDate = itemView.findViewById(R.id.txtViewConcertDate);
            txtViewConcertDetails = itemView.findViewById(R.id.txtViewConcertDetails);
            txtViewConcertPrice = itemView.findViewById(R.id.txtViewConcertPrice);
            imgViewAdd = itemView.findViewById(R.id.imgViewAdd);
            imgViewRemove = itemView.findViewById(R.id.imgViewRemove);
            txtViewNumTix = itemView.findViewById(R.id.txtViewNumTix);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

//
}
