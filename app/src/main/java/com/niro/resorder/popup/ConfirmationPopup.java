package com.niro.resorder.popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.niro.resorder.R;
import com.niro.resorder.adapter.OrderConfirmAdapter;
import com.niro.resorder.pojo.Order;
import com.niro.resorder.pojo.OrderDetail;

import java.util.List;

public class ConfirmationPopup {



    public interface OrderConfirmDelegate{
        void processOrderConfirm();
    }

    private static OrderConfirmDelegate delegate;

    public static void orderDetailsView(final Context context, List<OrderDetail> list, Order order,boolean isConfimBtnVisible, OrderConfirmDelegate i) {
        delegate = i;


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.layout_order_confirm);

        final ListView inventorySelectorLV = dialog.findViewById(R.id.order_details_list);
        Button buttonClosePopup =  dialog.findViewById(R.id.order_confirm_btn);

        if(isConfimBtnVisible){
            buttonClosePopup.setVisibility(View.VISIBLE);
        }else {
            buttonClosePopup.setVisibility(View.INVISIBLE);

        }

        TextView noOfOrder = dialog.findViewById(R.id.order_items);
        TextView totalOrder = dialog.findViewById(R.id.order_total);

        noOfOrder.setText("No of items: "+list.size());
        totalOrder.setText("Rs "+order.getOrderTotal());

        OrderConfirmAdapter adapter = new OrderConfirmAdapter(list);
        inventorySelectorLV.setAdapter(adapter);

        dialog.show();

        buttonClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                delegate.processOrderConfirm();
            }
        });


        }


}
