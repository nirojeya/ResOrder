package com.niro.resorder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niro.resorder.R;
import com.niro.resorder.pojo.OrderDetail;

import java.util.List;

public class OrderConfirmAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<OrderDetail> orderDetails;

    public OrderConfirmAdapter(List<OrderDetail> orderDetails){
        this.orderDetails = orderDetails;
    }

    @Override
    public int getCount() {
        //Log.e("DDDDDDDFq1","selectedItemList "+orderDetails.size());

        return orderDetails.size();
    }

    @Override
    public OrderDetail getItem(int i) {
        return orderDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_confirm_single_row, viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        OrderDetail orderDetail = orderDetails.get(i);
      //  Log.e("mmmmmmm",orderDetail.getItemDesc());
        viewHolder.itemDesc.setText(orderDetail.getItemDesc());
        viewHolder.itemQty.setText(String.valueOf(orderDetail.getItemQty()));
        viewHolder.itemPrice.setText(String.valueOf(orderDetail.getSellingPrice()));

        return view;
    }

    class ViewHolder{
        TextView itemDesc,itemQty,itemPrice;
         ViewHolder(View view){
            itemDesc = view.findViewById(R.id.item_desc);
            itemQty = view.findViewById(R.id.item_qty);
            itemPrice = view.findViewById(R.id.item_pcs);
        }
    }
}
