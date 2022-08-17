package com.example.beerapp.adapters;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerapp.R;
import com.example.beerapp.activities.DetailsActivity;
import com.example.beerapp.model.Beer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<com.example.beerapp.adapters.BeerAdapter.MyViewHolder> {

    private List<Beer> beerList;
    private Context context;

    public BeerAdapter(Context context, List<Beer> beerList)
    {
        this.context = context;
        this.beerList = beerList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beerId;
        public TextView beerName;
        public ImageView beerImg;
        public TextView beerDescription;
        public TextView beerBrew;
        public LinearLayout singleBeerLayout;

        public MyViewHolder(View v) {
            super(v);

            beerName = (TextView)v.findViewById(R.id.beerNameTextView);
            beerId = (TextView)v.findViewById(R.id.beerIdTextView);
            beerImg = (ImageView)v.findViewById(R.id.beerImgImageView);
            beerDescription = (TextView)v.findViewById(R.id.beerDescriptionTextView);
            beerBrew = (TextView)v.findViewById(R.id.beerFirstBrewTextView);
            singleBeerLayout = (LinearLayout)v.findViewById(R.id.singleBeerLayout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.beer_list, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final int index = i;

        String beerUrl = beerList.get(i).getImageLink();
        Picasso.get().load(beerUrl).into(myViewHolder.beerImg);

        myViewHolder.beerName.setText(beerList.get(i).getName());
        myViewHolder.beerId.setText(beerList.get(i).getId() + "");
        myViewHolder.beerDescription.setText(beerList.get(i).getDescription() + "");
        myViewHolder.beerBrew.setText("Brewed: " + beerList.get(i).getFirstbrew() + "");

        myViewHolder.singleBeerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beer currentBeer = beerList.get(index);
                Intent detailsIntent = new Intent(context, DetailsActivity.class);

                Gson gson = new Gson();
                detailsIntent.putExtra("beer",gson.toJson(currentBeer));

                context.startActivity(detailsIntent);
                Log.w("Clicked on beer", currentBeer.getId() + " , " + currentBeer.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return beerList.size();
    }
}
