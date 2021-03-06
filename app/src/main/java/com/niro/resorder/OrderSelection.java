package com.niro.resorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niro.resorder.adapter.ItemSelectionAdapter;
import com.niro.resorder.helper.AppSettings;
import com.niro.resorder.helper.Utils;
import com.niro.resorder.pojo.OrderDetail;
import com.niro.resorder.popup.ConfirmationPopup;
import com.niro.resorder.service.VolleyPostService;

public class OrderSelection extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemSelectionAdapter.SelectionDelegate,CategoryFragment.OnFragmentInteractionListener,
        ItemSelectionFragment.OnFragmentInteractionListener,
        ViewOrderFragment.OnFragmentInteractionListener,
        AddItemFragment.OnFragmentInteractionListener,WelComeFragment.OnFragmentInteractionListener{

    private static android.support.v4.app.FragmentManager fragmentManager;

    /*private ItemSelectionAdapter selectionAdapter;
    private Order order;
    private List<OrderDetail> selectedItemList;
*/
    private RelativeLayout shopingChartRoot;
    private TextView totalCount;
    private String baseUrl = "http://prod.kalesystems.com:3000/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_selection);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.orderFrameContainer, new WelComeFragment(),
                            Utils.WelComeFragment).commit();


        }


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        *//*    ConfirmationPopup.orderDetailsView(OrderSelection.this, selectedItemList, new ConfirmationPopup.OrderConfirmDelegate() {
                    @Override
                    public void processOrderConfirm() {

                    }
                });


            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //assignViews();
    }

    /*private void assignViews(){
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
        //orderListRV.setAdapter(selectionAdapter);

    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Fragment itemSelectionFragment = fragmentManager
                .findFragmentByTag(Utils.ItemSelectionFragment);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(itemSelectionFragment != null){
            //Log.e("nnnnmm","not null itemSelectionFragment");
            replaceCategoryFragment();
        }else {
            super.onBackPressed();

            //Log.e("nnnnmm","null itemSelectionFragment");



        }

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.order_selection, menu);

        //MenuItem menuItem =  menu.findItem(R.id.action_add_item);
        /*if(ResOrderApp.getUserDesignation().equalsIgnoreCase("Admin")) {
            menuItem.setVisible(true);
            invalidateOptionsMenu();
        }else {
            menuItem.setVisible(false);
            invalidateOptionsMenu();

        }*/


        View view =  menu.findItem(R.id.action_settings).getActionView();

        /*MenuItem menuItem =  menu.findItem(R.id.action_add_item);
        if(ResOrderApp.getUserDesignation().equalsIgnoreCase("Admin")) {
            menuItem.setVisible(true);
            invalidateOptionsMenu();
        }else {
            menuItem.setVisible(true);
            invalidateOptionsMenu();

        }*/


        //View a = menu.findItem(R.id.action_settings).getActionView();

        if(view != null){
            shopingChartRoot = view.findViewById(R.id.shoping_chart_root);
            totalCount = view.findViewById(R.id.txtCount);
        }

            shopingChartRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.checkNotNullTextView(totalCount)) {

                        ConfirmationPopup.orderDetailsView(OrderSelection.this, ItemSelectionFragment.selectedItemList,ItemSelectionFragment.order,true,new ConfirmationPopup.OrderConfirmDelegate() {
                            @Override
                            public void processOrderConfirm() {

                                ItemSelectionFragment.order.setOrderStatus(1001);
                                String url = baseUrl + "api/acct/salesreceipt";

                                //Log.e("respomce","OS "+ItemSelectionFragment.selectedItemList.size());

                                //createShareImageInBackground(ItemSelectionFragment.order, ItemSelectionFragment.selectedItemList);

                                VolleyPostService.postOrderAndOrderDetails(OrderSelection.this, url, ItemSelectionFragment.order, ItemSelectionFragment.selectedItemList, new VolleyPostService.OrderDelegate() {
                                    @Override
                                    public void processOrderFinished(String orderId) {
                                        totalCount.setText("0");
                                        ItemSelectionFragment.clearOrder();
                                        replaceCategoryFragment();
                                    }
                                });


                            }
                        });
                    }

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

        if (id == R.id.nav_home) {
            // Handle the camera action
            replaceHomeFragment();
        } else if (id == R.id.nav_category) {
            //startActivity(new Intent(this,CategoryActivity.class));

            replaceCategoryFragment();

        } else if (id == R.id.nav_view_order) {

            replaceViewOrderFragment();

        } else if (id == R.id.nav_log_out) {
            processLogout();
        } else if (id == R.id.nav_add_item) {

            if(ResOrderApp.getUserDesignation().equalsIgnoreCase("Admin")){
                replaceAddItemFragment();

            }else {
                Toast.makeText(this, "You have no permission", Toast.LENGTH_SHORT).show();
            }
        } /*else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void processLogout() {
        ResOrderApp.setUserId("");
        ResOrderApp.setFullName("");
        ResOrderApp.setPassword("");
        ResOrderApp.setMobileNo("");
        ResOrderApp.setUserDesignation("User");
        ResOrderApp.setUserAddress("address");
        AppSettings.setUserSession(this,0);
        finish();
    }

    @Override
    public void selectedItems(OrderDetail od){}


    protected void replaceCategoryFragment() {
        fragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.orderFrameContainer, new CategoryFragment(),
                        Utils.CategoryFragment).commit();
    }

    protected void replaceHomeFragment() {
        fragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.orderFrameContainer, new WelComeFragment(),
                        Utils.WelComeFragment).commit();
    }

    protected void replaceViewOrderFragment() {
        fragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.orderFrameContainer, new ViewOrderFragment(),
                        Utils.ViewOrderFragment).commit();
    }

    protected void replaceAddItemFragment() {
        fragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.orderFrameContainer, new AddItemFragment(),
                        Utils.AddItemFragment).commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void updateShoppingChart(int qty) {
        totalCount.setText(String.valueOf(qty));

    }




    private void sentSMS(String name , String phone){
        //Log.e("phoneVerify",name + "  "+phone);
       /* String phoneNumber = "0771751365";
        String message = "Hello Sujan! \n your bill amount 500.00 \n receive amount 1000.00 \n your balnce 500.00 \n Thank you \n KALE MOBILE POS \n Ha..Haa.Haa";
*/
        String phoneNumber = phone;//"775272030";
        String message = "Hello! " + name + " \n Your bill amount "+ ItemSelectionFragment.order +" \n " +
                " \n Thank you \n Send by Niro";

        /*try {
                // Get the default instance of the SmsManager
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber,
                        null,
                        message,
                        null,
                        null);
                Toast.makeText(getContext(), "Your sms has successfully sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(getContext(),"Your sms has failed...",
                        Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }*/


        // public void sendSmsBySIntent() {
        // add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber);

        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // add the message at the sms_body extra field
        smsSIntent.putExtra("sms_body", message);
        try{
            startActivity(smsSIntent);
        } catch (Exception ex) {
            Toast.makeText(OrderSelection.this, "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        // }

       /* public void sendSmsByVIntent() {

            Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
            // prompts only sms-mms clients
            smsVIntent.setType("vnd.android-dir/mms-sms");

            // extra fields for number and message respectively
            smsVIntent.putExtra("address", phoneNumber.getText().toString());
            smsVIntent.putExtra("sms_body", smsBody.getText().toString());
            try{
                startActivity(smsVIntent);
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, "Your sms has failed...",
                        Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }

        }*/

    }






    @Override
    public void selectedCategory(String category) {
        ItemSelectionFragment itemSelectionFragment = new ItemSelectionFragment();
        itemSelectionFragment.passingSelectedCategory(category);
        fragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.orderFrameContainer, itemSelectionFragment,
                        Utils.ItemSelectionFragment).commit();

    }
}
