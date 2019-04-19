package com.niro.resorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niro.resorder.R;
import com.niro.resorder.pojo.Order;

import java.util.List;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewOrderHolder> {
    public interface ViewOrderDelegate{
        void viewDetailsButtonClick(String orderId);
    }

    private List<Order> orderList;
    private ViewOrderDelegate viewOrderDelegate;

    public ViewOrderAdapter(List<Order> orderList,ViewOrderDelegate viewOrderDelegate){
        this.orderList = orderList;
        this.viewOrderDelegate = viewOrderDelegate;
    }

    @NonNull
    @Override
    public ViewOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_order_history, viewGroup, false);

        return new ViewOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrderHolder viewOrderHolder, int i) {
         final Order order = orderList.get(i);

        viewOrderHolder.orderDate.setText(order.getDate());
        viewOrderHolder.orderStatus.setText(order.getOrderStatus());
        viewOrderHolder.orderId.setText(order.getOrderId());
        viewOrderHolder.orderTotal.setText(String.valueOf(order.getOrderTotal()));

        viewOrderHolder.orderDetailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOrderDelegate.viewDetailsButtonClick(order.getOrderId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


     class ViewOrderHolder extends RecyclerView.ViewHolder{
        TextView orderDate,orderStatus,orderId,orderTotal,orderDetailView;
         ViewOrderHolder(@NonNull View itemView) {
            super(itemView);

            orderDate = itemView.findViewById(R.id.orderDate);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderId = itemView.findViewById(R.id.orderId);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderDetailView = itemView.findViewById(R.id.orderDetailView);

        }
    }
}
