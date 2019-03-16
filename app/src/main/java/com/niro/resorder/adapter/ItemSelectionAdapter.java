package com.niro.resorder.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.niro.resorder.R;
import com.niro.resorder.pojo.Item;

import java.text.DecimalFormat;
import java.util.List;

public class ItemSelectionAdapter extends RecyclerView.Adapter<ItemSelectionAdapter.ItemSelectViewHolder> {

    public interface SelectionDelegate{
        void selectedItems(Item item);
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

        final Item selectItem = new Item();
        selectItem.setItemNumber(item.getItemNumber());
        selectItem.setItemDesc(item.getItemDesc());
        selectItem.setItemPrice(item.getItemPrice());
        selectItem.setItemQty(1.0);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.selectedItems(selectItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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
