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

public class DailyTransactionAdapter extends RecyclerView.Adapter<DailyTransactionAdapter.DailyTransactionViewHolder>
{
    private ArrayList<DailyTransactionItem> dailyTransactionItemList;

    private DailyTransactionAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class DailyTransactionViewHolder extends RecyclerView.ViewHolder{

        public TextView tvCName;
        public TextView tvCAmount;
        public TextView tvCType;
        public ImageView uploadedRecpit;
        public ImageView imageDelete;

        public DailyTransactionViewHolder(@NonNull View itemView, OnItemClickListener listener)
        {
            super(itemView);

            tvCName = itemView.findViewById(R.id.tvCategoryName);
            tvCAmount = itemView.findViewById(R.id.tvCategoryAmount);
            tvCType = itemView.findViewById(R.id.tvCategoryType);
            uploadedRecpit = itemView.findViewById(R.id.imgRecipt);
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

    public DailyTransactionAdapter(ArrayList<DailyTransactionItem> dailyTransactionItems)
    {
        dailyTransactionItemList = dailyTransactionItems;
    }

    @NonNull
    @Override
    public DailyTransactionAdapter.DailyTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_categorylist,parent,false);
        DailyTransactionAdapter.DailyTransactionViewHolder evh = new DailyTransactionAdapter.DailyTransactionViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyTransactionAdapter.DailyTransactionViewHolder holder, int position) {
        DailyTransactionItem currentItem = dailyTransactionItemList.get(position);

        holder.tvCType.setText(currentItem.getType());
        holder.tvCName.setText(currentItem.getTitle());
        Double cAmt = currentItem.getAmount();
        holder.tvCAmount.setText(cAmt.toString());
        String x = currentItem.getReceipt();
        holder.uploadedRecpit.setImageURI(Uri.parse("content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F46/ORIGINAL/NONE/2087033575"));
    }

    @Override
    public int getItemCount() {
        return dailyTransactionItemList.size();
    }
}