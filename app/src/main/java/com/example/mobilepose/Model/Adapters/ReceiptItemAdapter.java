package com.example.mobilepose.Model.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilepose.Model.Order;
import com.example.mobilepose.R;

import java.util.List;

public class ReceiptItemAdapter extends RecyclerView.Adapter<ReceiptItemAdapter.ReceiptItemViewHolder> {

    private List<Order> orders;

    public ReceiptItemAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ReceiptItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_item, parent, false);
        return new ReceiptItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptItemViewHolder holder, int position) {
        Order item = orders.get(position);
        holder.tvItemName.setText(item.getProduct().getProductName());
        holder.tvItemQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvItemPrice.setText(String.valueOf(item.getOrderTotal()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ReceiptItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName, tvItemQuantity, tvItemPrice;

        public ReceiptItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.names);
            tvItemQuantity = itemView.findViewById(R.id.qty);
            tvItemPrice = itemView.findViewById(R.id.prices);
        }
    }
}