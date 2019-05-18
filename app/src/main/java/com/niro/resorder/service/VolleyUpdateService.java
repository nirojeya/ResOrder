package com.niro.resorder.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.singleton.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyUpdateService {

    public static void updateItem(Context ctx, String url, final Item item){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("respomce_item_update",response.toString());
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
        VolleySingleton.getmInstance(ctx.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }


    private static byte[] setItemParams(Item item) {

        String clientId = "E7kctRDMnb5JRyoW5B4rRMH797uz5zNmQOFfVQLV";
        String companyId = "bf21636d3f29957e";
        //DBHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));

        String body = null;
        try{
            //Here are your parameters:

            //String date = ConversionClass.postServerDateFormat(item.getDate());

            // for(Item item : itemList) {

            JSONObject params = new JSONObject();


            params.put("client_id", clientId);
            params.put("company_id", companyId);

            params.put("item_number", item.getItemNumber());
            params.put("temp_bid",item.getBid());
            params.put("bid", item.getBid());
            params.put("track_inventory","N");
            params.put("item_desc", item.getItemDesc());
            params.put("category", item.getItemCategory());
            params.put("subcategory", item.getItemSubCategory());
            params.put("uom", "N");
            params.put("vat_code", "1");
            params.put("selling_price", item.getItemPrice());
            params.put("max_discount", 0.0);
            params.put("max_discount_type",1);
            params.put("default_discount", 0.0);
            params.put("default_discount_type",1);
            /*if(item.getProductType().equalsIgnoreCase("P")) {
                params.put("type", 0); // this is product item
            }else if(item.getProductType().equalsIgnoreCase("M")) {
                params.put("type", 1); // this is manufacture item
            }*/

            params.put("type", 1); // this is manufacture item

            params.put("allow_sales","1");



            body = params.toString();
            Log.e("update_item",body);

            return body.getBytes("utf-8");
            //  }

        } catch (JSONException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
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
