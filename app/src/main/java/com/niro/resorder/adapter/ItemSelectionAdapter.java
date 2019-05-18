package com.niro.resorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.niro.resorder.R;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.pojo.OrderDetail;

import java.text.DecimalFormat;
import java.util.List;

public class ItemSelectionAdapter extends RecyclerView.Adapter<ItemSelectionAdapter.ItemSelectViewHolder> {

    public interface SelectionDelegate{
        void selectedItems(OrderDetail orderDetail);
    }

    private List<Item> itemList;
    private final DecimalFormat df = new DecimalFormat("0.00");
    private SelectionDelegate delegate;

    public ItemSelectionAdapter(List<Item> itemList,SelectionDelegate delegate){
        this.itemList = itemList;
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public ItemSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selection_single_view, parent, false);

        return new ItemSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSelectViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.itemDesc.setText(item.getItemDesc());
        holder.itemPrice.setText(df.format(item.getItemPrice()));

        /*switch (position){
            case 0:
                holder.itemImage.setImageResource(R.drawable.burger);
                break;
            case 1:
                holder.itemImage.setImageResource(R.drawable.french);
                break;
            case 2:
                holder.itemImage.setImageResource(R.drawable.noodles);
                break;
            case 3:
                holder.itemImage.setImageResource(R.drawable.salad);
                break;
            default:
                holder.itemImage.setImageResource(R.drawable.salad);
        }*/

        setCategoryImages(holder,item.getItemCategory(),position);

        //holder.itemImage.setImageResource(R.drawable.salad);

        final OrderDetail orderDetail = new OrderDetail();
        orderDetail.setItemNumber(item.getItemNumber());
        orderDetail.setItemDesc(item.getItemDesc());
        orderDetail.setItemPrice(item.getItemPrice());
        orderDetail.setBatchNo(item.getBid());
        orderDetail.setItemQty(1.0);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.selectedItems(orderDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void setCategoryImages(ItemSelectViewHolder holder,String category,int position){

        switch (category){
            case "Biriyani":
                if(position == 0){
                    holder.itemImage.setImageResource(R.drawable.muttonbiriyani); // wrong imgge
                }else if(position == 1){
                    holder.itemImage.setImageResource(R.drawable.muttonbiriyani);

                }else if(position == 2){
                    holder.itemImage.setImageResource(R.drawable.eggbiriyani);

                }else if(position == 3){
                    holder.itemImage.setImageResource(R.drawable.vegbiriyani);

                }
                break;

            case "Juice":
                if(position == 0){
                    holder.itemImage.setImageResource(R.drawable.orangejuice);
                }else if(position == 1){
                    holder.itemImage.setImageResource(R.drawable.applejuice);

                }else if(position == 2){
                    holder.itemImage.setImageResource(R.drawable.mangojuice);

                }else if(position == 3){
                    holder.itemImage.setImageResource(R.drawable.pineapplejuice);

                }
                break;

            case "Fride Rice":
                if(position == 0){
                    holder.itemImage.setImageResource(R.drawable.friderice);
                }else if(position == 1){
                    holder.itemImage.setImageResource(R.drawable.friderice);

                }else if(position == 2){
                    holder.itemImage.setImageResource(R.drawable.friderice);

                }else if(position == 3){
                    holder.itemImage.setImageResource(R.drawable.friderice);

                }
                break;

            case "Vegetarian":
                if(position == 0){
                    holder.itemImage.setImageResource(R.drawable.chickenbiriyani);
                }else if(position == 1){
                    holder.itemImage.setImageResource(R.drawable.muttonbiriyani);

                }else if(position == 2){
                    holder.itemImage.setImageResource(R.drawable.eggbiriyani);

                }else if(position == 3){
                    holder.itemImage.setImageResource(R.drawable.vegbiriyani);

                }
                break;

            case "Rice":
                if(position == 0){
                    holder.itemImage.setImageResource(R.drawable.chickenbiriyani);
                }else if(position == 1){
                    holder.itemImage.setImageResource(R.drawable.muttonbiriyani);

                }else if(position == 2){
                    holder.itemImage.setImageResource(R.drawable.eggbiriyani);

                }else if(position == 3){
                    holder.itemImage.setImageResource(R.drawable.vegbiriyani);

                }
                break;

            case "Ice Cream":
                if(position == 0){
                    holder.itemImage.setImageResource(R.drawable.chickenbiriyani);
                }else if(position == 1){
                    holder.itemImage.setImageResource(R.drawable.muttonbiriyani);

                }else if(position == 2){
                    holder.itemImage.setImageResource(R.drawable.eggbiriyani);

                }else if(position == 3){
                    holder.itemImage.setImageResource(R.drawable.vegbiriyani);

                }
                break;





        }
    }

    class ItemSelectViewHolder extends RecyclerView.ViewHolder{
        private android.support.v7.widget.CardView cardView;
        private ImageView itemImage;
        private TextView itemDesc, itemPrice;
        public ItemSelectViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.single_row_root);
            itemImage = itemView.findViewById(R.id.itemImg);
            itemDesc = itemView.findViewById(R.id.item_desc);
            itemPrice = itemView.findViewById(R.id.item_price);





        }





    }
}
