package com.niro.resorder.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.niro.resorder.R;
import com.niro.resorder.adapter.OrderConfirmAdapter;
import com.niro.resorder.pojo.OrderDetail;

import java.util.List;

public class ConfirmationPopup {



    public interface OrderConfirmDelegate{
        void processOrderConfirm();
    }

    private static OrderConfirmDelegate delegate;

    public static void orderDetailsView(final Context context, List<OrderDetail> list,OrderConfirmDelegate i) {
        delegate = i;


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.layout_order_confirm);

        final ListView inventorySelectorLV = dialog.findViewById(R.id.order_details_list);
        Button buttonClosePopup =  dialog.findViewById(R.id.order_confirm_btn);

        OrderConfirmAdapter adapter = new OrderConfirmAdapter(context,list);
        inventorySelectorLV.setAdapter(adapter);

        dialog.show();

        buttonClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.processOrderConfirm();
            }
        });


        }


}
