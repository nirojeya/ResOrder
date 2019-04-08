package com.niro.resorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.niro.resorder.adapter.ItemSelectionAdapter;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.pojo.Order;
import com.niro.resorder.pojo.OrderDetail;
import com.niro.resorder.popup.ConfirmationPopup;

import java.util.ArrayList;
import java.util.List;

public class OrderSelection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ItemSelectionAdapter.SelectionDelegate{

    private ItemSelectionAdapter selectionAdapter;
    private Order order;
    private List<OrderDetail> selectedItemList;

    private RelativeLayout shopingChartRoot;
    private TextView totalCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_selection);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        */    ConfirmationPopup.orderDetailsView(OrderSelection.this, selectedItemList, new ConfirmationPopup.OrderConfirmDelegate() {
                    @Override
                    public void processOrderConfirm() {

                    }
                });


            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        assignViews();
    }

    private void assignViews(){
        RecyclerView orderListRV = findViewById(R.id.orderList);
        selectedItemList = new ArrayList<>();
        order = new Order();
        List<Item> itemList = new ArrayList<>();

        for(int i = 0; i<20; i++){
            Item item = new Item();
            item.setItemNumber(String.valueOf(i));
            item.setItemDesc("Item "+i+1);
            item.setItemQty(1.0);
            item.setItemPrice(i*10);

            itemList.add(item);
        }


        selectionAdapter = new ItemSelectionAdapter(itemList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderListRV.setLayoutManager(layoutManager);
        orderListRV.setHasFixedSize(true);
        orderListRV.setAdapter(selectionAdapter);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_selection, menu);
        View view =  menu.findItem(R.id.action_settings).getActionView();

        //View a = menu.findItem(R.id.action_settings).getActionView();

        if(view != null){
            shopingChartRoot = view.findViewById(R.id.shoping_chart_root);
            totalCount = view.findViewById(R.id.txtCount);
        }

        shopingChartRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConfirmationPopup.orderDetailsView(OrderSelection.this, selectedItemList, new ConfirmationPopup.OrderConfirmDelegate() {
                    @Override
                    public void processOrderConfirm() {

                    }
                });

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_category) {
            startActivity(new Intent(this,CategoryActivity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void selectedItems(OrderDetail orderDetail) {
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
