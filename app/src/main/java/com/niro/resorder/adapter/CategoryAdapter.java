package com.niro.resorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niro.resorder.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    public interface CategoryClickDelegate{
        void categoryOnClick(int position);
    }

    private List<String> categoryList;
    private CategoryClickDelegate categoryClickDelegate;

    public CategoryAdapter(List<String> categoryList,CategoryClickDelegate categoryClickDelegate){
        this.categoryList = categoryList;
        this.categoryClickDelegate = categoryClickDelegate;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_single_row, viewGroup, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, final int i) {
        categoryHolder.categoryName.setText(categoryList.get(i));

        categoryHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickDelegate.categoryOnClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        TextView categoryName;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.category_root);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
