package com.example.wordsgame;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class WordListAdapter extends ListAdapter<Word, WordListAdapter.WordViewHolder> {
    WordListOpenHelper mDB;
    protected WordListAdapter(WordListOpenHelper db, @NonNull
            DiffUtil.ItemCallback<Word> diffCallback) {
        super(diffCallback);
        mDB = db;
    }
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word current = mDB.query(position);
        holder.bind(current);
    }
    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }
    static class WordDiff extends DiffUtil.ItemCallback<Word> {
        @Override
        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem)
        {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word
                newItem) {
            return oldItem.getmWord().equals(newItem.getmWord());
        }
    }
    class WordViewHolder extends RecyclerView.ViewHolder {
        private Word mWord;
        private final TextView wordItemView;
        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            wordItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("WordViewHolder", "Position: " + getAdapterPosition());
                    mDB.delete(mWord.getmId());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
        public void bind(Word word) {
            mWord = word;
            wordItemView.setText(mWord.getmWord());
        }
    }
}