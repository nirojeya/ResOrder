package com.niro.resorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.niro.resorder.adapter.CategoryAdapter;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.pojo.Order;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView orderListRV = findViewById(R.id.categoryList);
      //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        orderListRV.setLayoutManager(manager);
        orderListRV.setHasFixedSize(true);

        // totalCount = findViewById(R.id.total_count);
        //List<Item> selectedItemList = new ArrayList<>();
        //order = new Order();
        List<Item> itemList = new ArrayList<>();

        for(int i = 0; i<20; i++){
            Item item = new Item();
            item.setItemNumber(String.valueOf(i));
            item.setItemDesc("Item "+i+1);
            item.setItemQty(1.0);
            item.setItemPrice(i*10);

            itemList.add(item);
        }

        CategoryAdapter categoryAdapter = new CategoryAdapter(itemList);
        orderListRV.setAdapter(categoryAdapter);


    }
}
