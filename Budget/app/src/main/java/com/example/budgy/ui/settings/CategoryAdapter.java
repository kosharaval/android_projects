package com.example.budgy.ui.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgy.Models.CategoryItem;
import com.example.budgy.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryItem> categoryItemList;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCategoryItem;

        public CategoryViewHolder(@NonNull View itemView)
        {
            super(itemView);

            txtCategoryItem = itemView.findViewById(R.id.txtCategoryItem);
        }
    }

    public CategoryAdapter(ArrayList<CategoryItem> categoryItemList)
    {
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_categoryitem, parent,false);
        return new CategoryAdapter.CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryItem item = categoryItemList.get(position);
        holder.txtCategoryItem.setText(categoryItemList.get(position).categoryName);
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

}
