package com.niro.resorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.niro.resorder.adapter.CategoryAdapter;
import com.niro.resorder.service.VolleyGetService;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private List<String> categoryList;
    private String baseUrl = "http://prod.kalesystems.com:3000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initializeView();

    }

    private void initializeView(){
        RecyclerView orderListRV = findViewById(R.id.categoryList);
        //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        orderListRV.setLayoutManager(manager);
        orderListRV.setHasFixedSize(true);

        categoryList = new ArrayList<>();

        /*for(int i = 0; i<20; i++){
            Item item = new Item();
            item.setItemNumber(String.valueOf(i));
            item.setItemDesc("Item "+i+1);
            item.setItemQty(1.0);
            item.setItemPrice(i*10);

            itemList.add(item);
        }*/

        final CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, new CategoryAdapter.CategoryClickDelegate() {
            @Override
            public void categoryOnClick(int position) {

                String category = categoryList.get(position);
                category =   category.replaceAll(",","%2C");
                category = category.replaceAll(" ","%20");
                category = category.replaceAll("&","%26");

                Intent intent = new Intent(CategoryActivity.this,ActivityItemSelection.class);
                intent.putExtra("CATEGORY_NAME",category);
                startActivity(intent);
            }
        });

        orderListRV.setAdapter(categoryAdapter);

        VolleyGetService.syncAllItemCategory(this, baseUrl+"api/item/cat/categories", new VolleyGetService.CategoryDelegate() {
            @Override
            public void syncItemCategory(List<String> syncCategoryList) {
                categoryList.clear();
                categoryList.addAll(syncCategoryList);
                categoryAdapter.notifyDataSetChanged();
            }
        });

    }
}
