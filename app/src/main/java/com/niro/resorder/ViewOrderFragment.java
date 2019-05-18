package com.niro.resorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niro.resorder.adapter.ViewOrderAdapter;
import com.niro.resorder.helper.DrawReciept;
import com.niro.resorder.pojo.Order;
import com.niro.resorder.pojo.OrderDetail;
import com.niro.resorder.popup.ConfirmationPopup;
import com.niro.resorder.service.VolleyGetService;
import com.niro.resorder.service.VolleyPostService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOrderFragment extends Fragment implements ViewOrderAdapter.ViewOrderDelegate{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String baseUrl = "http://prod.kalesystems.com:3000/";


    private ViewOrderAdapter viewOrderAdapter;

    private static String startDate = getTodayDate(new Date(System.currentTimeMillis()));
    private static String endDate = tomorrowDate(new Date(System.currentTimeMillis()));//getTomorrowDate


    public ViewOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewOrderFragment newInstance(String param1, String param2) {
        ViewOrderFragment fragment = new ViewOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_view_order, container, false);

        initializeView(view);

        return view;
    }

    private void initializeView(View view){

        RecyclerView recyclerView = view.findViewById(R.id.viewOrderRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        final List<Order> viewOrder = new ArrayList<>();

        viewOrderAdapter = new ViewOrderAdapter(viewOrder,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(viewOrderAdapter);



        if(ResOrderApp.getUserDesignation().equalsIgnoreCase("Admin")){

            VolleyGetService.syncOrderHistory(getActivity(), "http://prod.kalesystems.com:3000/api/acct/salesreceipts?end_date="+endDate+"&start_date="+startDate+"&", new VolleyGetService.ViewOrderDelegate() {
                @Override
                public void processSyncOrder(List<Order> orderList) {
                    viewOrder.clear();
                    viewOrder.addAll(orderList);
                    viewOrderAdapter.notifyDataSetChanged();
                }
            });

        }else {
            VolleyGetService.syncOrderUserHistory(getActivity(), "http://prod.kalesystems.com:3000/api/acct/salesreceipts?end_date="+endDate+"&start_date="+startDate+"&", new VolleyGetService.ViewOrderDelegate() {
                @Override
                public void processSyncOrder(List<Order> orderList) {
                    viewOrder.clear();
                    viewOrder.addAll(orderList);
                    viewOrderAdapter.notifyDataSetChanged();
                }
            });
        }

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
    public void viewDetailsButtonClick(String orderId) {
        VolleyGetService.syncOrdersDetails(getActivity(), "http://prod.kalesystems.com:3000/api/acct/salesreceipt/"+orderId, new VolleyGetService.ViewOrderDetailsDelrgate() {
            @Override
            public void processSyncOrderDetails(List<OrderDetail> list,Order order) {

                ConfirmationPopup.orderDetailsView(getActivity(), list,order,false,new ConfirmationPopup.OrderConfirmDelegate() {
                    @Override
                    public void processOrderConfirm() {
                        // nothing to do
                    }
                });
            }
        });
    }

    @Override
    public void orderConfirmButtonClick(final Order order) {
        VolleyGetService.syncOrdersDetails(getActivity(),
                "http://prod.kalesystems.com:3000/api/acct/salesreceipt/"+order.getOrderId(),
                new VolleyGetService.ViewOrderDetailsDelrgate() {
            @Override
            public void processSyncOrderDetails(List<OrderDetail> list,Order order1) {
                createShareImageInBackground(order,list);
            }
        });
    }

    private void createShareImageInBackground(final Order order, final List<OrderDetail> list){

        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                DrawReciept drawReciept = new DrawReciept(getActivity());
                Bitmap bitmap = drawReciept.salesPrintReceiptNormalImage(order,list,"Sales Receipt","normal");

                openImage(bitmap);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Log.e("CVCVCBBHGG",""+order.getOrderId());

                String updateURL = baseUrl+"api/acct/salesreceipt/update/order-status";

                VolleyPostService.updateOrderStatus(getActivity(),updateURL,Integer.parseInt(order.getOrderId()));
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void openImage(Bitmap bitmap){

        String root = Environment.getExternalStorageDirectory().toString();
        Bitmap icon = bitmap;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        //File f = new File(Environment.getExternalStorageDirectory().toString() + "POS_images/" + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(root + "/temporary_file.jpg"));
        startActivity(Intent.createChooser(share, "Share Image"));

    }

    public static String getTodayDate(Date date){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US); // Monday 01/01/2016, 11:00 am

        return df.format(date);
    }

    public static String tomorrowDate(Date todayDate){

        Calendar c = Calendar.getInstance();
        c.setTime(todayDate);
        c.add(Calendar.DATE, 1);
        String daySample = String.valueOf(c.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayTO = sdf.format(c.getTime());

        return dayTO;
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
