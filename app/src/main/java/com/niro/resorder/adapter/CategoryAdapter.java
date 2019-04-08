package com.niro.resorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niro.resorder.R;
import com.niro.resorder.pojo.Item;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Item> itemList;
    public CategoryAdapter(List<Item> itemList){
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_single_row, viewGroup, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
