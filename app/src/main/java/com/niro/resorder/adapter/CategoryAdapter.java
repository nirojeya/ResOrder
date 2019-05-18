package com.niro.resorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        switch (i){
            case 0:
                categoryHolder.imageView.setImageResource(R.drawable.biriyani);
                break;
            case 1:
                categoryHolder.imageView.setImageResource(R.drawable.juice);
                break;
            case 2:
                categoryHolder.imageView.setImageResource(R.drawable.friderice);
                break;
            case 3:
                categoryHolder.imageView.setImageResource(R.drawable.paneer);
                break;
            case 4:
                categoryHolder.imageView.setImageResource(R.drawable.fish);
                break;
            case 5:
                categoryHolder.imageView.setImageResource(R.drawable.vege);
                break;
                default:
                    categoryHolder.imageView.setImageResource(R.drawable.rice);

        }
        categoryHolder.categoryName.setText(categoryList.get(i));



        categoryHolder.rootLayout.setOnClickListener(new View.OnClickListener() {
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
        CardView rootLayout;
        ImageView imageView;
        TextView categoryName;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.category_root);
            categoryName = itemView.findViewById(R.id.category_name);
            imageView = itemView.findViewById(R.id.item_img);
        }
    }
}
