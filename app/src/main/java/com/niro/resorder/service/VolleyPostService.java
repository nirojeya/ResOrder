package com.niro.resorder.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

    private static OrderDelegate orderDelegate;
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
                //Log.e("respomce_order_err",error.toString().trim());

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

        String clientId = "tfuVDOVyAAI6R2aFMvXT4yVlsiXmalJDtMSVyVoX";
        String companyId = "e23197373bd3ce99";
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
            order.accumulate("temp_order_id", 0);
            order.accumulate("cashier_id","1");

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
