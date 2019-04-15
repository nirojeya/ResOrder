package com.niro.resorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.niro.resorder.pojo.Order;

import java.util.List;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewOrderHolder> {

    private List<Order> orderList;

    public ViewOrderAdapter(List<Order> orderList){
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrderHolder viewOrderHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class ViewOrderHolder extends RecyclerView.ViewHolder{

        public ViewOrderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
