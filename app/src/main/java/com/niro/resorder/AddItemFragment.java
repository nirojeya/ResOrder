package com.niro.resorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.niro.resorder.helper.ToastMessageHelper;
import com.niro.resorder.helper.Utils;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.service.VolleyGetService;
import com.niro.resorder.service.VolleyPostService;
import com.niro.resorder.service.VolleyUpdateService;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment implements View.OnClickListener,VolleyGetService.SearchItemDelegate{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText itemNumber;
    private EditText itemName;
    private EditText itemQty;
    private EditText itemPrice;
    private EditText itemCategory;
    private EditText itemSubCategory;

    private Button buttonAddItem;

    private int bid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        assignViews(view);
        return view;
    }

    private void assignViews(View view){

        itemNumber = view.findViewById(R.id.addItemNumber);
        itemName = view.findViewById(R.id.addItemName);
        itemQty = view.findViewById(R.id.addItemQty);
        itemPrice = view.findViewById(R.id.addItemPrice);
        itemCategory = view.findViewById(R.id.addItemCategory);
        itemSubCategory = view.findViewById(R.id.addItemSubCategory);

        buttonAddItem = view.findViewById(R.id.addAItemButton);

        buttonAddItem.setOnClickListener(this);

        itemNumberTextWacther();

    }

    private void itemNumberTextWacther(){
        itemNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    searchItemInServer(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchItemInServer(final CharSequence s){
        @SuppressLint("StaticFieldLeak") AsyncTask search = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                String url =  "http://prod.kalesystems.com:3000/"+ "api/inv/item/inventory/" + s;

                VolleyGetService.syncItem(getActivity(),url,AddItemFragment.this);

                return null;
            }
        };

        search.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private void processAddItem(Item item){
        String  itemURL = "http://prod.kalesystems.com:3000/"+"api/item/item";

        VolleyPostService.postItem(Objects.requireNonNull(getActivity()),itemURL,item);
    }

    private void processUpdateItem(Item item){

        String itemURL = "http://prod.kalesystems.com:3000/" + "api/item/item";
        String inventoryURL = "http://prod.kalesystems.com:3000/" + "api/inv/item/inventory";

        VolleyUpdateService.updateItem(Objects.requireNonNull(getActivity()),itemURL,item);
        VolleyPostService.postInventoryItem(getActivity(),inventoryURL,item);

        ToastMessageHelper.customSuccToast(getActivity(),"Update success");

    }


    private void processClear(){
        itemNumber.setText("");
        itemName.setText("");
        itemQty.setText("");
        itemPrice.setText("");
        itemCategory.setText("");
        itemSubCategory.setText("");
    }

    private void processFillData(Item item){
        //itemNumber.setText(item.getItemNumber());
        itemName.setText(item.getItemDesc());
        itemQty.setText(String.valueOf(item.getItemQty()));
        itemPrice.setText(String.valueOf(item.getItemPrice()));
        itemCategory.setText(item.getItemCategory());
        itemSubCategory.setText(item.getItemSubCategory());
        bid = item.getBid();
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
    public void onClick(View view) {

        if(buttonAddItem.getText().toString().equalsIgnoreCase("Add item")){
            processGetInputForAddItem();
        }else {
            processGetInputForUpdateItem();
        }


    }

    private void processGetInputForAddItem(){
        if(Utils.checkNotNullEditText(itemNumber) && Utils.checkNotNullEditText(itemName)
                && Utils.checkNotNullEditText(itemQty) && Utils.checkNotNullEditText(itemPrice)){

            Item item;


            String itemNo,itemDesc,itemCat,itemSubCat;
            double itemQtatity,itemPcs;


            itemNo = Utils.getInput(itemNumber);
            itemDesc = Utils.getInput(itemName);
            itemQtatity = Double.parseDouble(Utils.getInput(itemQty));
            itemPcs = Double.parseDouble(Utils.getInput(itemPrice));

            if(Utils.checkNotNullEditText(itemCategory)){
                itemCat = Utils.getInput(itemCategory);
            }else {
                itemCat = "Others";
            }

            if(Utils.checkNotNullEditText(itemSubCategory)){
                itemSubCat = Utils.getInput(itemSubCategory);
            }else {
                itemSubCat = "Others";
            }

            item = new Item();
            item.setItemNumber(itemNo);
            item.setItemDesc(itemDesc);
            item.setItemQty(itemQtatity);
            item.setItemPrice(itemPcs);
            item.setItemCategory(itemCat);
            item.setItemSubCategory(itemSubCat);

            processAddItem(item);
            processClear();
        }

    }

    private void processGetInputForUpdateItem(){
        if(Utils.checkNotNullEditText(itemNumber) && Utils.checkNotNullEditText(itemName)
                && Utils.checkNotNullEditText(itemQty) && Utils.checkNotNullEditText(itemPrice)){

            Item item;


            String itemNo,itemDesc,itemCat,itemSubCat;
            double itemQtatity,itemPcs;


            itemNo = Utils.getInput(itemNumber);
            itemDesc = Utils.getInput(itemName);
            itemQtatity = Double.parseDouble(Utils.getInput(itemQty));
            itemPcs = Double.parseDouble(Utils.getInput(itemPrice));

            if(Utils.checkNotNullEditText(itemCategory)){
                itemCat = Utils.getInput(itemCategory);
            }else {
                itemCat = "Others";
            }

            if(Utils.checkNotNullEditText(itemSubCategory)){
                itemSubCat = Utils.getInput(itemSubCategory);
            }else {
                itemSubCat = "Others";
            }

            item = new Item();
            item.setItemNumber(itemNo);
            item.setItemDesc(itemDesc);
            item.setItemQty(itemQtatity);
            item.setItemPrice(itemPcs);
            item.setItemCategory(itemCat);
            item.setItemSubCategory(itemSubCat);
            item.setBid(bid);

            processUpdateItem(item);
            processClear();
        }

    }




    @Override
    public void itemFromServerResult(List<Item> itemList) {
        if(itemList.size() > 0){
            buttonAddItem.setText("Update item");
            for(Item item : itemList){
                processFillData(item);
            }
        }else {
            buttonAddItem.setText("Add item");

        }
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
    }


}
