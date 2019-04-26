package com.niro.resorder.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.niro.resorder.ResOrderApp;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.pojo.Order;
import com.niro.resorder.pojo.OrderDetail;
import com.niro.resorder.singleton.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VolleyGetService {
    private static ItemDelegate itemDelegate;
    private static CategoryDelegate categoryDelegate;
    private static ViewOrderDelegate viewOrderDelegate;
    private static ViewOrderDetailsDelrgate viewOrderDetailsDelrgate;

    private static Context context;
    private static List<String> categoryList;
    private static List<Item> itemInvList;


    public interface ItemDelegate{
        void syncItemDetails(List<Item> itemList);
    }

    public interface CategoryDelegate{
        void syncItemCategory(List<String> syncCategoryList);
    }

    public interface ViewOrderDelegate{
        void processSyncOrder(List<Order> orderList);
    }

    public interface ViewOrderDetailsDelrgate{
        void processSyncOrderDetails(List<OrderDetail> list);
    }

    public interface LoginUserDelegate{
        void checkValidUser(boolean isValid);
    }



    public static void syncAllInventory(Context con,String url, final ItemDelegate delegate) {
        //itemList.clear();
        context = con;
        itemDelegate= delegate;

        itemInvList = new ArrayList<>();

        //Log.e("okfhfhdfhdfhdf","response.toString()");
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okfhfhdfhdfhdf",response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("inventories");

                    if(itemInvList.size() > 0){
                        itemInvList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Item item = new Item();

                        if (!object.isNull("category")) {
                            item.setItemCategory(object.getString("category"));
                        }

                        if (!object.isNull("item_desc")) {
                            item.setItemDesc(object.getString("item_desc"));
                        }

                        if (!object.isNull("item_number")) {
                            item.setItemNumber(object.getString("item_number"));

                        }

                        if (!object.isNull("subcategory")) {
                            item.setItemSubCategory(object.getString("subcategory"));
                        }


                        if (!object.isNull("selling_price")) {
                            item.setItemPrice(object.getDouble("selling_price"));
                        }

                        if (!object.isNull("bid")) {
                            item.setBid(object.getInt("bid"));
                        }

                        itemInvList.add(item);
                    }
                    //storeInvenItemsDB(itemInvList);
                    itemDelegate.syncItemDetails(itemInvList);


                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                    // Log.e("respomce_order_err","come error");
                    //CustomDialog.imeOutExceptionDialog(context);
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncAllItemCategory(Context con,String url, final CategoryDelegate delegate) {
        context = con;
        categoryList = new ArrayList<>();
        categoryDelegate = delegate;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.e("33333333333"," erankirantaa "  );
                try {

                    JSONArray jsonArray = response.getJSONArray("categories");

                    if (categoryList.size() > 0) {
                        categoryList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (!object.isNull("category")) {
                            categoryList.add(object.getString("category"));
                        }
                    }

                    categoryDelegate.syncItemCategory(categoryList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("errrrrrrrrrrr "," " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                    // Log.e("respomce_order_err","come error");
                   // CustomDialog.imeOutExceptionDialog(context);
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncOrderHistory(Context con, String url,ViewOrderDelegate vod) {
        /*
         * Server tab (Sign_up) only need to sync all order.
         * This function will call by server tab.
         * So, no nee to check COMPANY_ID.
         * Before sync order need to check database by (order id and company id) unique
         * Join tab will show only join tab's sales.
         *
         * tk */

        context = con;
        viewOrderDelegate = vod;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okGood",response.toString());



                try {

                    JSONArray jsonArray = response.getJSONArray("salesreceipt");

                    List<Order> orderList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject odObj = jsonArray.getJSONObject(i);

                        Order order = new Order();

                        if (!odObj.isNull("order_status") && odObj.getString("order_status").equals("1001")) {

                            // Sync only 1005
                            if (!odObj.isNull("order_id")
                                    && !odObj.isNull("order_status") && odObj.getString("order_status").equals("1001")) {

                                order.setOrderId(String.valueOf(odObj.getInt("order_id")));
                            }

                            if (!odObj.isNull("created")) {
                                //order.setDate(ReadableDateFormat.UTCToLocalTime(odObj.getString("created")));
                                order.setDate(odObj.getString("created"));

                            }

                            /*if (!odObj.isNull("payment_method")) {
                                order.setPaymentMethod(odObj.getInt("payment_method"));
                            }*/

                            /*if (!odObj.isNull("discount_total")) {
                                order.setDiscountTotal(odObj.getDouble("discount_total"));
                            }*/

                            if (!odObj.isNull("total")) {
                                order.setOrderTotal(odObj.getDouble("total"));
                            }




                            //syncAllOrderAndOrderDetails(context,baseURL+"api/acct/salesreceipt/"+order.getId());

                        }

                        orderList.add(order);
                    }



                    viewOrderDelegate.processSyncOrder(orderList);


                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("RES_ERR",error.toString());
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncOrdersDetails(Context con,String url,ViewOrderDetailsDelrgate r) {
        viewOrderDetailsDelrgate = r;
        /*
         * Server tab (Sign_up) only need to sync all order.
         * This function will call by server tab.
         * So, no nee to check COMPANY_ID.
         * Before sync order need to check database by (order id, item number, bid and company id) unique
         * Join tab will show only join tab's sales.
         *
         * tk */

        context = con;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.e("okGood111",obj.toString());

                try {

                    List<OrderDetail> orderDetailsList = new ArrayList<>();

                    JSONObject object = obj.getJSONObject("salesreceipt");


                    // order details
                    JSONArray jsonArray = object.getJSONArray("details");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject odObj = jsonArray.getJSONObject(i);

                        OrderDetail od = new OrderDetail();
                        //Log.e("COUNTCCC","also come "+object.getString("item_number"));


                        if (!object.isNull("order_id")) {
                            od.setOederId(String.valueOf(object.getInt("order_id")));
                        }
                        if (!odObj.isNull("qty")) {
                            od.setItemQty(odObj.getDouble("qty"));
                        }
                        if (!odObj.isNull("item_price")) {
                            od.setSellingPrice(odObj.getDouble("item_price"));
                        }

                        od.setItemPrice(od.getSellingPrice() / od.getItemQty());

                        /*if (!odObj.isNull("bid")) {
                            od.setBatchNo(String.valueOf(odObj.getInt("bid")));
                        }
                        if (!odObj.isNull("item_number")) {
                            od.setItemNumber(odObj.getString("item_number"));
                        }
                        if (!odObj.isNull("item_discount")) {
                            od.setDiscount(odObj.getDouble("item_discount"));
                        }
                        if (!odObj.isNull("category")) {
                            od.setItemCategory(odObj.getString("category"));
                        }
                        if (!odObj.isNull("subcategory")) {
                            od.setItemSubCategory(odObj.getString("subcategory"));
                        }
                        Log.e("DESC",odObj.getString("item_desc"));*/
                        if (!odObj.isNull("item_desc")) {
                            od.setItemDesc(odObj.getString("item_desc"));
                        }

                        /*if (!object.isNull("company_id")) {
                            od.setCompanyId((object.getString("company_id")));
                        }

                        if (!odObj.isNull("purchase_price")) {
                            od.setPurchasePrice((odObj.getDouble("purchase_price")));
                        }

                        if(!odObj.isNull("location_id")){
                            od.setLocationId(odObj.getInt("location_id"));
                        }

                        od.setRegularPrice(od.getSales_price() + od.getDiscount());
*/
                        orderDetailsList.add(od);

                    }

                    viewOrderDetailsDelrgate.processSyncOrderDetails(orderDetailsList);




                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    public static void syncAllUsers(Context con, String url, final String un, final String pwd,LoginUserDelegate delegate) {
        final LoginUserDelegate userDelegate = delegate;
        //itemList.clear();
        context = con;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            boolean isValidUser = false;

            @Override
            public void onResponse(JSONObject response) {
                Log.e("okfeffsdsdsgsgg123",response.toString());


                try {

                    JSONArray jsonArray = response.getJSONArray("users");



                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);


                        //Log.e("COUNTCCC","come");

//                if(object.getInt("id") >= 0){
//
//                }


                        if (!object.isNull("user_name")
                                && object.getString("user_name").equalsIgnoreCase(un)
                                && !object.isNull("password")
                                && object.getString("password").equalsIgnoreCase(pwd)) {
                            //Log.e("COUNTCCC","also come "+object.getString("item_number"));

                            /*if (!object.isNull("email")) {
                                user.set(object.getString("category"));
                            }*/

                            //Log.e("okfeffsdsdsgsgg123","insert name "+object.getString("name"));

                            if (!object.isNull("phone_number")) {
                                ResOrderApp.setMobileNo(object.getString("phone_number"));
                                //Log.e("MAX_DIS",""+object.getDouble("max_discount"));
                            }
                            if (!object.isNull("password")) {
                                ResOrderApp.setPassword(object.getString("password"));
                            }

                            if (!object.isNull("name")) {
                                ResOrderApp.setFullName(object.getString("name"));
                            }
                            /*if (!object.isNull("user_unique_id")) {
                                user.setUserUniqueId(object.getString("user_unique_id"));
                            }
                            if (!object.isNull("user_auth")) {
                                user.setUserAuthId(object.getString("user_auth"));
                            }*/
                            if (!object.isNull("user_id")) {
                                ResOrderApp.setUserId(object.getString("user_id"));
                                //Log.e("MAX_DIS",""+object.getDouble("max_discount"));
                            }
                            if (!object.isNull("designation")) {
                                Log.e("MAX_DIS",""+object.getString("designation"));

                                ResOrderApp.setUserDesignation(object.getString("designation"));
                            }

                            if(!object.isNull("user_name")){
                                ResOrderApp.setUserName(object.getString("user_name"));
                            }
                            /*if (!object.isNull("user_type")) {
                                user.setUserDesignation(object.getString("user_type"));
                            }*/


                            isValidUser = true;

                            //storeUsersDB(userList);
                        }
                    }

                    userDelegate.checkValidUser(isValidUser);

                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }




    //set header
    private static Map<String, String> setHeaderData(){
        Map<String, String> headers = new HashMap<>();

        /*String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DbHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));
        String userId = AppSettings.getUniqueId(context);
        String authId = AppSettings.getAuthId(context);
*/
        headers.put("client_id","tfuVDOVyAAI6R2aFMvXT4yVlsiXmalJDtMSVyVoX");
        headers.put("company_id","e23197373bd3ce99");
        headers.put("user_id","62eb0889-7040-42ba-bd44-2aafc2667b0e");
        headers.put("authorization","KCkZm1t7j86OaTD0YRQWR1kSY7Fn3kcbLrIGp6FPYHRoXBenrNTAGTaXmiy6iHgS9oDuw2wfZE9");

       /* headers.put("client_id",clientId);
        headers.put("company_id",companyId);
        headers.put("user_id",userId);
        headers.put("authorization",authId);
*/
        return headers;
    }



}
