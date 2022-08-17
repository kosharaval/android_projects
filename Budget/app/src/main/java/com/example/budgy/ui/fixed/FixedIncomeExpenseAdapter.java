package com.example.budgy.ui.fixed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgy.R;

import java.util.ArrayList;

public class FixedIncomeExpenseAdapter extends RecyclerView.Adapter<FixedIncomeExpenseAdapter.FixedIncomeExpensViewHolder> {
    private ArrayList<FixedAmountItem> mFixedamoutList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class FixedIncomeExpensViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;

        public FixedIncomeExpensViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageViewFixedAmountItem);
            mTextView1 = itemView.findViewById(R.id.textviewFixedAmount);
            mTextView2 = itemView.findViewById(R.id.textviewFixedAmount2);
            mDeleteImage = itemView.findViewById(R.id.image_delete_fixedamount);

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

            mDeleteImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION)
                        {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public FixedIncomeExpenseAdapter(ArrayList<FixedAmountItem> fixedamoutList)
    {
        mFixedamoutList = fixedamoutList;
    }

    @NonNull
    @Override
    public FixedIncomeExpensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_fixed_amount,parent,false);
        FixedIncomeExpensViewHolder evh = new FixedIncomeExpensViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull FixedIncomeExpensViewHolder holder, int position) {
        FixedAmountItem currentItem = mFixedamoutList.get(position);

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mFixedamoutList.size();
    }
}
