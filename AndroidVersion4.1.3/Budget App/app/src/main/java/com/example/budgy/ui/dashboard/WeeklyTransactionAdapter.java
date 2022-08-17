package com.example.budgy.ui.dashboard;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgy.R;

import java.util.ArrayList;

public class WeeklyTransactionAdapter extends RecyclerView.Adapter<WeeklyTransactionAdapter.WeeklyTransactionViewHolder>
{
    private ArrayList<DailyTransactionItem> dailyTransactionItemList;

    private WeeklyTransactionAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(WeeklyTransactionAdapter.OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class WeeklyTransactionViewHolder extends RecyclerView.ViewHolder{

        public TextView txtDate;
        public TextView txtTitle;
        public TextView txtAmount;
        public ImageView imgReceipt;
        public ImageView imageDelete;

        public WeeklyTransactionViewHolder(@NonNull View itemView, WeeklyTransactionAdapter.OnItemClickListener listener)
        {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            imgReceipt = itemView.findViewById(R.id.imgReceipt);
            imageDelete = itemView.findViewById(R.id.imgDelete);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();

                        if(position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }

                    }
                }
            });
            imageDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();

                        if(position !=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }

                    }
                }
            });
        }
    }

    public WeeklyTransactionAdapter(ArrayList<DailyTransactionItem> dailyTransactionItems)
    {
        dailyTransactionItemList = dailyTransactionItems;
    }

    @NonNull
    @Override
    public WeeklyTransactionAdapter.WeeklyTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_weeklyreport,parent,false);
        WeeklyTransactionAdapter.WeeklyTransactionViewHolder evh = new WeeklyTransactionAdapter.WeeklyTransactionViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyTransactionAdapter.WeeklyTransactionViewHolder holder, int position) {
        DailyTransactionItem currentItem = dailyTransactionItemList.get(position);

        Integer date = currentItem.getDate();
        holder.txtDate.setText(date.toString());
        holder.txtTitle.setText(currentItem.getTitle());
        Double cAmt = currentItem.getAmount();
        holder.txtAmount.setText(cAmt.toString());
        String x = currentItem.getReceipt();
        holder.imgReceipt.setImageURI(Uri.parse("content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F46/ORIGINAL/NONE/2087033575"));
    }

    @Override
    public int getItemCount() {
        return dailyTransactionItemList.size();
    }
}
