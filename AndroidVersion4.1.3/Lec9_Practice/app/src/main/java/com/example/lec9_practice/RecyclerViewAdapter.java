package com.example.lec9_practice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] titles = {"Chapter One","Chapter Two","Chapter Three","Chapter Four","Chapter Five","Chapter Six","Chapter Seven","Chapter Eight"};
    private String [] details = {"Item one details","Item two details","Item three details","Item four details","Item five details","Item six details",
            "Item seven details","Item eight details"};
    private int [] images = {R.drawable.android_image_1,R.drawable.android_image_2,R.drawable.android_image_3,R.drawable.android_image_4,
            R.drawable.android_image_5,R.drawable.android_image_6,R.drawable.android_image_7,R.drawable.android_image_7};

    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(  RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.itemImage.setImageResource(images[position]);
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position]);

    }

    @Override
    public int getItemCount() {

        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_detail);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Snackbar.make(v,"Click deletcted on item" + position,Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    FirstFragment firstFragment = new FirstFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.SecondFragment,firstFragment).addToBackStack(null).commit();
                    //Intent intent = new Intent(context,SecondFragment.class);
                    //.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //context.startActivity(intent);
                }
            });
        }
    }
}
