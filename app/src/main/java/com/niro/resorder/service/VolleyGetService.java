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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VolleyGetService {
    private static ItemDelegate itemDelegate;
    private static Context context;

    public interface ItemDelegate{
        void syncItemDetails(List<Item> itemList);
    }

    public static void syncItem(Context con, String url, ItemDelegate i) {
        //itemList.clear();
        final List<Item> searchList = new ArrayList<>();
        context = con;
        itemDelegate = i;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okfeffsdsdsgsgg123",response.toString());


                try {

                    JSONArray jsonArray = response.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Item item = new Item();

                        if(!object.isNull("category")){
                            item.setItemCategory(object.getString("category"));
                        }

                        if(!object.isNull("category")){
                            item.setItemCategory(object.getString("category"));
                        }


                        searchList.add(item);
                        // }
                    }

                    itemDelegate.syncItemDetails(searchList);
                    //storeItemsDB(clQueue,callingFrom);

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
                    //OrderHomeFragment.updateNetwork(context, "TimeOut");
                }
            }

        }){
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }*/
        };

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

}
