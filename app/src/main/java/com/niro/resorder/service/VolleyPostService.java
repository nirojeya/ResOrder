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
import com.niro.resorder.helper.AppSettings;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.pojo.Order;
import com.niro.resorder.pojo.OrderDetail;
import com.niro.resorder.singleton.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VolleyPostService {

    public interface OrderDelegate{
        void processOrderFinished(String orderId);
    }

    public interface UserSignUpDelegate{
        void processRegisterFinished(String type);
    }

    private static OrderDelegate orderDelegate;

    public static void postUser(final Context ctx, String url, UserSignUpDelegate delegate){
        final UserSignUpDelegate signUpDelegate = delegate;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("respomce_item",response.toString());

                int currentUserId = AppSettings.getUniqueId(ctx);
                currentUserId = currentUserId + 1;
                AppSettings.setUniqueId(ctx,currentUserId);

                signUpDelegate.processRegisterFinished("ok");;


                // updateItemBatchId
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respomce_item_err",error.toString());
                signUpDelegate.processRegisterFinished("error");;

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setUserParams(ctx);
            }
        };

        // now volley retry policy is 20s
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] setUserParams(Context context){

        //String clientId = AppSettings.getClientId(context);
        //String companyId = AppSettings.getCompanyId(context);

        String clientId = "E7kctRDMnb5JRyoW5B4rRMH797uz5zNmQOFfVQLV";
        String companyId = "bf21636d3f29957e";


        JSONObject parent = new JSONObject();
        String body = null;
        try{
            //Here are your parameters:

            parent.put("client_id",clientId);
            //  Log.e("DATA","client_id "+clientId);

            parent.put("company_id",companyId);
            //  Log.e("DATA","company_id "+user.getUserUniqueId());

            parent.put("user_id",String.valueOf(AppSettings.getUniqueId(context)));
            //   Log.e("DATA","user_id "+String.valueOf(user.getUserID()));

            parent.put("user_unique_id","ae6ac1f1-b4ea-4b80-ac5c-121b9e3b384a");
            //  Log.e("DATA","user_unique_id "+user.getUserUniqueId());

            parent.put("user_auth","YBG0AWQJbWDSBgDY5WcwrJ3iGPd0eG74PNwVfFkgyGXk8g8ihUa4BTTp7IIrQ07vqQjXJXYuv3W");
            //  Log.e("DATA","user_auth "+user.getUserAuthId());

            parent.put("user_name", ResOrderApp.getUserName());
            //  Log.e("DATA","user_name "+user.getUserName());

            parent.put("user_type",ResOrderApp.getUserDesignation());
            //  Log.e("DATA","user_type "+user.getUserDesignation());

            parent.put("designation",ResOrderApp.getUserDesignation());
              //Log.e("DATA","designation "+ResOrderApp.getUserDesignation());

            parent.put("password",ResOrderApp.getPassword());
            //   Log.e("DATA","password "+user.getUserPassward());

            parent.put("name",ResOrderApp.getUserName());
            //   Log.e("DATA","name " +user.getUserName());

            parent.put("phone_number",ResOrderApp.getMobileNo());
            //   Log.e("DATA","phone_number "+user.getUserPNO());

            parent.put("email","n@g.com");

            parent.put("address",ResOrderApp.getUserAddress());
            //   Log.e("DATA","address "+user.getUserAddress());

            parent.put("status",0);










            body = parent.toString();
            Log.e("BODY",body);

        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            return body.getBytes("utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }


    public static void postItem(Context ctx,String url , final Item item){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("respomce_item",response.toString());


                // updateItemBatchId
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respomce_item_err",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setItemParams(item);
            }
        };

        // now volley retry policy is 20s
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] setItemParams(Item item){

        //String clientId = AppSettings.getClientId(context);
        //String companyId = AppSettings.getCompanyId(context);

        String clientId = "E7kctRDMnb5JRyoW5B4rRMH797uz5zNmQOFfVQLV";
        String companyId = "bf21636d3f29957e";

        //DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        JSONObject params = new JSONObject();
        String body = null;
        try{
            //Here are your parameters:

            //String date = ConversionClass.postServerDateFormat(item.getDate());

            /*String date = "n/l";
            if(!item.getBidExpDate().equalsIgnoreCase("n/l")){
                date = ConversionClass.postServerDateFormat(item.getBidExpDate());

            }else if(!item.getDate().equalsIgnoreCase("n/l")){
                date = ConversionClass.postServerDateFormat(item.getDate());
            }*/

            params.put("client_id", clientId);
            params.put("company_id", companyId);
            params.put("item_number", item.getItemNumber());
            params.put("temp_bid",item.getBid());
            params.put("bid", 0);
            params.put("track_inventory","Y");
            params.put("item_desc", item.getItemDesc());
            params.put("category", item.getItemCategory());
            params.put("subcategory", item.getItemSubCategory());
            params.put("uom", "no");
            params.put("vat_code", "1");
            params.put("selling_price", item.getItemPrice());
            params.put("max_discount", 0.0);
            params.put("max_discount_type",1);
            params.put("default_discount", 0.0);
            params.put("default_discount_type",1);
            //Log.e("ProductType",item.getProductType());
            /*if(item.getProductType().equalsIgnoreCase("P")) {
                params.put("type", 0); // this is product item
            }else if(item.getProductType().equalsIgnoreCase("M")) {
                params.put("type", 1); // this is manufacture item
            }else if(item.getProductType().equalsIgnoreCase("MI")){
                params.put("type", 2); // this is manufacture item
            }*/
            params.put("type", 1); // this is manufacture item
            params.put("purchase_price", item.getItemPrice());
            params.put("bid_exp_date", "n/l"); // this is convertion date
            params.put("qoh", item.getItemQty());
            params.put("qoh_count", item.getItemQty());
            params.put("reorder_qty", 0.0);
            params.put("location_id",1);
            params.put("bid_text", "n/l");
            params.put("bid_rcd_qty", item.getItemQty());
            params.put("supplier_id", "1");
            params.put("allow_sales","1");








            body = params.toString();
            Log.e("BODY",body);

        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            return body.getBytes("utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }


    /* POST Order and Order details */
    public static void postOrderAndOrderDetails(Context ctx, String url , final Order order, final List<OrderDetail> orderDetailsList, final OrderDelegate delegate){
        orderDelegate = delegate;
        //context = ctx;
        //Log.e("respomce","orderDetailsList "+orderDetailsList.size());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("respomce_order",response.toString());

                //Log.e("respomce_cus",response.toString());

                /*long endTime = System.currentTimeMillis();

                long startTime = AppSettings.getStartWebTime(context);

                long timeDif = endTime - startTime;

                AppSettings.setEndWebTime(context,timeDif);

                OrderHomeFragment.updateNetwork(context,"Other");


               // System.out.println("Total elapsed http request/response time in milliseconds: " + elapsedTime);

                Log.e("SERVER_LOG","dif "+timeDif);
*/

                try {

                    String temId = response.getJSONObject("salesreceipt").getString("temp_order_id");
                    String orderId = response.getJSONObject("salesreceipt").getString("order_id");

                    //Log.e("IDMIS","oId "+orderId+" Temp "+temId);
                    //Log.e("IDMIS","newId "+orderId+" newTemp "+temId);

                    //int newId = Integer.parseInt(orderId) + 1;

                   /* db.updateOrderId(orderId, temId);
                    db.updateOrderDetalisId(orderId, temId);
                    db.updateTxHeaderOrderId(orderId,temId);

                    db.updateOrderNumber(newId);


                    if (delegate != null) {
                        delegate.updateServerOrderId("response");
                    }
*/



                    //response.getJSONObject("salesreceipt").getString("temp_order_id");
                    //Log.e("IDs_INFO",response.getJSONObject("salesreceipt").getString("temp_order_id"));
                    //Log.e("IDs_INFO",response.getJSONObject("salesreceipt").getString("order_id"));

                    delegate.processOrderFinished(orderId);

                    //Log.e("IDs_INFO",response.getString("order_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respomce_order_err",error.toString().trim());

                delegate.processOrderFinished("Error");


               /* if (delegate != null) {
                    delegate.updateServerOrderId("error");
                }*/
                //progress.dismiss();
                /*if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError")) {
                   // Log.e("respomce_order_err","come error");

                    OrderHomeFragment.updateNetwork(context, "TimeOut");
                }*/

                /*if(CheckInternetCon.isConnectionTypeError(error.toString())) {
                    //Log.e("respomce_order_err","come error");
                    OrderHomeFragment.updateNetwork(context, "TimeOut");
                }*/
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setOrderAndOrderDetailsParams(order,orderDetailsList);
            }
        };
        // now volley retry policy is 20s
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private static byte[] setOrderAndOrderDetailsParams(Order orderData, List<OrderDetail> orderDetailsList){
        DecimalFormat df= new DecimalFormat("0.00");

        String clientId = "E7kctRDMnb5JRyoW5B4rRMH797uz5zNmQOFfVQLV";
        String companyId = "bf21636d3f29957e";
        int locationId = 1;
        //DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        JSONObject order = new JSONObject();
        JSONObject orderDetails;

        String body = null;
        try{
            //Here are your parameters:

//            String date = ConversionClass.postServerDateFormat(orderData.getDate());

            order.accumulate("client_id", clientId);
            order.accumulate("company_id", companyId);
            order.accumulate("order_id",0);
            order.accumulate("temp_order_id", 1);
            order.accumulate("cashier_id",ResOrderApp.getUserName());

            // Log.e("CRCRCRCR","cashier_id "+orderData.getCashierId()+" payment_method "+orderData.getPaymentMethod()+" payment_total "+orderData.getUserPayment());

            //order.accumulate("date", date);
            order.accumulate("order_status", String.valueOf(orderData.getOrderStatus()));

            order.accumulate("sub_total", Double.parseDouble(df.format(orderData.getOrderTotal())));

            order.accumulate("discount_total",0.0);
            order.accumulate("vat_total", 0.0);
            order.accumulate("total", Double.parseDouble(df.format(orderData.getOrderTotal())));
            order.accumulate("customer_id", String.valueOf(orderData.getCustomerId()));
            order.accumulate("payment_method", 1001);
            order.accumulate("payment_total",Double.parseDouble(df.format(orderData.getOrderTotal())));
            order.accumulate("service_charge",0.0);
            order.accumulate("location_id",locationId);


            Log.e("respomce",""+orderDetailsList.size());

            int row_id = 1;
            for(OrderDetail od : orderDetailsList){
                orderDetails = new JSONObject();

                orderDetails.put("row_id",row_id);
                orderDetails.put("item_number", od.getItemNumber());
                orderDetails.put("bid", od.getBatchNo());
                orderDetails.put("location_id",locationId);
                orderDetails.put("item_price", od.getSellingPrice());
                orderDetails.put("qty", od.getItemQty());
                orderDetails.put("item_discount",0.0);
                orderDetails.put("final_price",Double.parseDouble(df.format(od.getSellingPrice()))); //todo need check if value exit or not

                order.accumulate("details",orderDetails);


                row_id++;

            }

            body = order.toString();
            //dbHandler.addErrorLog("Sales_receipt_create",body);

            Log.e("respomce_",body);

        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            return body.getBytes("utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }
    /* End POST Order and Order details */


    public static void updateOrderStatus(Context ctx, String url , final int orderId){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("update_order_status",response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respomce_order_err",error.toString());
                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError")) {
                    // Log.e("respomce_order_err","come error");

                    //OrderHomeFragment.updateNetwork(context, "TimeOut");
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }

            @Override
            public byte[] getBody() {
                return setOrderStatusParams(orderId);
            }
        };
        // now volley retry policy is 20s
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    private static byte[] setOrderStatusParams(int orderId){
        DecimalFormat df= new DecimalFormat("0.00");

        String clientId = "E7kctRDMnb5JRyoW5B4rRMH797uz5zNmQOFfVQLV";
        String companyId = "bf21636d3f29957e";
        //int locationId = AppSettings.getLocationId(context);

       // DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        JSONObject order = new JSONObject();
        JSONObject orderDetails;

        String body = null;
        try{
            //Here are your parameters:

//            String date = ConversionClass.postServerDateFormat(orderData.getDate());

            order.put("client_id", clientId);
            order.put("company_id", companyId);
            order.put("order_id",orderId);
            order.put("old_order_status","1001");
            order.put("new_order_status","1002");


            /*order.accumulate("temp_order_id", 0);
            order.accumulate("cashier_id", String.valueOf(orderData.getCashierId()));

            // Log.e("CRCRCRCR","cashier_id "+orderData.getCashierId()+" payment_method "+orderData.getPaymentMethod()+" payment_total "+orderData.getUserPayment());

            //order.accumulate("date", date);
            order.accumulate("order_status", String.valueOf(orderData.getStatus()));

            order.accumulate("sub_total", Double.parseDouble(df.format(orderData.getSubTotal())));

            order.accumulate("discount_total", Double.parseDouble(df.format(orderData.getDiscountForTotal())));
            order.accumulate("vat_total", orderData.getVatTotal());
            order.accumulate("total", Double.parseDouble(df.format(orderData.getTotal())));
            if(orderData.getCustomerId() != null) {
                order.accumulate("customer_id", String.valueOf(orderData.getCustomerId()));
            }else {
                order.accumulate("customer_id","0");
            }
            order.accumulate("payment_method", orderData.getPaymentMethod());
            order.accumulate("payment_total",Double.parseDouble(df.format(orderData.getUserPayment())));
            order.accumulate("service_charge",Double.parseDouble(df.format(orderData.getResturantCharge())));
            order.accumulate("location_id",locationId);
*/



            //int row_id = orderDetailsList.size();


            /*for(OrderDetails od : orderDetailsList){
                //boolean isNotExits = dbHandler.isNotExistOrderDetails(String.valueOf(orderData.getId()),od.getItemNumber(),od.getBatchNo(),od.getLocationId());
                // Log.e("ERRRRR_UP","befor if "+isNotExits);

                // Log.e("ERRRRR_UP", "locationId 1 "+od.getLocationId()+" locationId 2 "+locationId);


                if(od.getLocationId() == locationId) {

                    // Log.e("ERRRRR_UP", "locationId "+od.getLocationId()+" num "+od.getItemNumber());

                    orderDetails = new JSONObject();

                    orderDetails.put("row_id", row_id);
                    orderDetails.put("item_number", od.getItemNumber());
                    orderDetails.put("bid", Integer.parseInt(od.getBatchNo()));
                    orderDetails.put("location_id", od.getLocationId());
                    orderDetails.put("item_price", od.getSales_price()); // discount minus
                    orderDetails.put("qty", od.getQty());
                    orderDetails.put("item_discount", od.getDiscount());
                    orderDetails.put("final_price", Double.parseDouble(df.format(od.getPrice()))); //todo need check if value exit or not

                    order.accumulate("details", orderDetails);

                    row_id++;
                }

                // index++;

            }
*/
            body = order.toString();

            Log.e("ERRRRR_UP",body);

        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            return body.getBytes("utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }


    private static Map<String, String> setHeaderData(){
        Map<String, String> headers = new HashMap<>();

        /*String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DbHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));
        String userId = AppSettings.getUniqueId(context);
        String authId = AppSettings.getAuthId(context);
*/
        headers.put("client_id","E7kctRDMnb5JRyoW5B4rRMH797uz5zNmQOFfVQLV");
        headers.put("company_id","bf21636d3f29957e");
        headers.put("user_id","626d596e-48a5-45ab-8477-448f9155e6f9");
        headers.put("authorization","IWRKJkdnF3ZzRtkqpFUp7F1VwQlw22zzxmeO4XpowO4tC70VxBObHYnjuggmy1j4lsQWwxwUyf1");

       /* headers.put("client_id",clientId);
        headers.put("company_id",companyId);
        headers.put("user_id",userId);
        headers.put("authorization",authId);
*/
        return headers;
    }


}
