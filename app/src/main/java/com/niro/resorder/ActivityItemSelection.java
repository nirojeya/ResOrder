package com.niro.resorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.niro.resorder.adapter.ItemSelectionAdapter;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.pojo.Order;
import com.niro.resorder.pojo.OrderDetail;
import com.niro.resorder.popup.ConfirmationPopup;
import com.niro.resorder.service.VolleyGetService;

import java.util.ArrayList;
import java.util.List;


public class ActivityItemSelection extends AppCompatActivity implements ItemSelectionAdapter.SelectionDelegate {

    private ItemSelectionAdapter selectionAdapter;
    private Order order;
    private List<OrderDetail> selectedItemList;
    private TextView totalCount;

    private String baseUrl = "http://54.200.81.66:3000/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String category;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                category= null;
            } else {
                category= extras.getString("CATEGORY_NAME");
            }
        } else {
            category= (String) savedInstanceState.getSerializable("CATEGORY_NAME");
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        */
                ConfirmationPopup.orderDetailsView(ActivityItemSelection.this, selectedItemList,order, new ConfirmationPopup.OrderConfirmDelegate() {
                    @Override
                    public void processOrderConfirm() {

                    }
                });
            }
        });



        assignViews(category);
    }

    private void assignViews(String category){
        RecyclerView orderListRV = findViewById(R.id.orderList);
        totalCount = findViewById(R.id.total_count);
        selectedItemList = new ArrayList<>();
        order = new Order();
        List<Item> itemList = new ArrayList<>();

        /*for(int i = 0; i<20; i++){
            Item item = new Item();
            item.setItemNumber(String.valueOf(i));
            item.setItemDesc("Item "+i+1);
            item.setItemQty(1.0);
            item.setItemPrice(i*10);

            itemList.add(item);
        }*/


        selectionAdapter = new ItemSelectionAdapter(itemList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderListRV.setLayoutManager(layoutManager);
        orderListRV.setHasFixedSize(true);
        orderListRV.setAdapter(selectionAdapter);

        String url = baseUrl + "api/inv/item/inventories?category="+category+"&&&";


        VolleyGetService.syncAllInventory(this, url, new VolleyGetService.ItemDelegate() {
            @Override
            public void syncItemDetails(List<Item> itemList) {

            }
        });


    }

    @Override
    public void selectedItems(OrderDetail orderDetail) {
       // Log.e("DDDDDDDFq","item pojo "+orderDetail.getItemNumber()+" "+orderDetail.getItemQty()+" "+orderDetail.getItemDesc());
       // Log.e("DDDDDDDFq","selectedItemList "+selectedItemList.size());

        if (selectedItemList.size()>0){
            //Log.e("FIND_IS","IF 1");
            int itemCount = 0;
            int index = -1;

            for (OrderDetail orderDetails:selectedItemList){

                if (orderDetails.getItemNumber().equalsIgnoreCase(orderDetail.getItemNumber())){
                    //Log.e("FIND_IS","equal num");

                    index = itemCount;
                }
                itemCount++;
            }

            if (index == -1){
                //Log.e("FIND_IS","IF -1");

                orderDetail.setSellingPrice(orderDetail.getItemPrice()*orderDetail.getItemQty());

                //Toast.makeText(getContext(), "please Select item" ,Toast.LENGTH_SHORT).show();
                selectedItemList.add(orderDetail);
                //setOrderTotal();
            }else {
                //Log.e("FIND_IS","ELSE "+index);

                //Log.e("DDDDDDDF","before "+item.getItemQty());

                selectedItemList.get(index).setItemQty(selectedItemList.get(index).getItemQty()+orderDetail.getItemQty());
                selectedItemList.get(index).setSellingPrice(selectedItemList.get(index).getSellingPrice()+orderDetail.getItemPrice());
                //od.setOrderDetailsItemSellingPrice(od.getOrderDetailsItemPrice());

                //Log.e("FIND_IS1","ELSE "+selectedItemList.get(index).getSellingPrice());

                setOrderTotal();
            }

        }else {
            //Log.e("FIND_IS","ELSE 1");

            orderDetail.setSellingPrice(orderDetail.getItemPrice()*orderDetail.getItemQty());

            //Log.e("FIND_IS1","ELSE "+orderDetail.getSellingPrice());

            //od.setOrderDetailsItemSellingPrice(od.getOrderDetailsItemPrice());
            selectedItemList.add(orderDetail);
            setOrderTotal();
        }
       // noOfItem.setText(calNoOfItem(selectIdlist));
        selectionAdapter.notifyDataSetChanged();
        totalCount.setText(String.valueOf(calOrderQty()));
    }

    private void setOrderTotal(){
        double orderTotal = 0.0;
        //Double discount = 0.0  ;
        for (OrderDetail orderDetail:selectedItemList){


            orderTotal= orderTotal + orderDetail.getSellingPrice();
        }
        order.setOrderTotal(orderTotal);
        //Log.e("Totoal",""+orderTotal);
    }

    private Double calOrderQty(){
        double orderQty = 0.0;
        for (OrderDetail orderDetail:selectedItemList){
            orderQty= orderQty + orderDetail.getItemQty();

            //Log.e("DDDDDDDF","orderQty "+orderQty+" item.getItemQty() "+item.getItemQty());
        }
        return orderQty;
    }
}
