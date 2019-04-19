package com.niro.resorder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niro.resorder.adapter.ItemSelectionAdapter;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.pojo.Order;
import com.niro.resorder.pojo.OrderDetail;
import com.niro.resorder.service.VolleyGetService;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemSelectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemSelectionFragment extends Fragment implements ItemSelectionAdapter.SelectionDelegate{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ItemSelectionAdapter selectionAdapter;
    public static Order order;
    public static List<OrderDetail> selectedItemList;
    private List<Item> itemList;
    //private TextView totalCount;

    private String baseUrl = "http://54.200.81.66:3000/";
    private String url = "";


    public ItemSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemSelectionFragment newInstance(String param1, String param2) {
        ItemSelectionFragment fragment = new ItemSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_item_selection, container, false);
        assignViews(view);
        return view;
    }




    private void assignViews(View view){
        RecyclerView orderListRV = view.findViewById(R.id.orderList);
       // totalCount = view.findViewById(R.id.total_count);
        if(selectedItemList == null) {
            selectedItemList = new ArrayList<>();
            order = new Order();

            //Log.e("BBBBBBBB","selectedItemList null");

        }
        itemList = new ArrayList<>();

        /*for(int i = 0; i<20; i++){
            Item item = new Item();
            item.setItemNumber(String.valueOf(i));
            item.setItemDesc("Item "+i+1);
            item.setItemQty(1.0);
            item.setItemPrice(i*10);

            itemList.add(item);
        }*/


        selectionAdapter = new ItemSelectionAdapter(itemList,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        orderListRV.setLayoutManager(layoutManager);
        orderListRV.setHasFixedSize(true);
        orderListRV.setAdapter(selectionAdapter);

        VolleyGetService.syncAllInventory(getActivity(), url, new VolleyGetService.ItemDelegate() {
            @Override
            public void syncItemDetails(List<Item> list) {
                itemList.clear();
                itemList.addAll(list);
                selectionAdapter.notifyDataSetChanged();
            }
        });




    }

    public void passingSelectedCategory(String category){
        url = baseUrl + "api/inv/item/inventories?category="+category+"&&&";



    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                setOrderTotal();

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
        mListener.updateShoppingChart(calOrderQty());
        //totalCount.setText(String.valueOf(calOrderQty()));
    }

    private void setOrderTotal(){
        double orderTotal = 0.0;
        //Double discount = 0.0  ;
        for (OrderDetail orderDetail:selectedItemList){

            Log.e("Totoal","indi "+orderDetail.getSellingPrice());


            orderTotal= orderTotal + orderDetail.getSellingPrice();
        }
        order.setOrderTotal(orderTotal);
        Log.e("Totoal",""+orderTotal);
    }

    private Double calOrderQty(){
        double orderQty = 0.0;
        for (OrderDetail orderDetail:selectedItemList){
            orderQty= orderQty + orderDetail.getItemQty();

            //Log.e("DDDDDDDF","orderQty "+orderQty+" item.getItemQty() "+item.getItemQty());
        }
        return orderQty;
    }

    public static void clearOrder(){
        selectedItemList.clear();
        selectedItemList = null;
        order = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void updateShoppingChart(double qty);
    }
}
