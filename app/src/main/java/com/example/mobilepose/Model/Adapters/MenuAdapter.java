package com.example.mobilepose.Model.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilepose.Model.Order;
import com.example.mobilepose.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>{

    List<Order> orders;
    ConstraintLayout constraintLayout;

    private OnOrderChangeListener orderChangeListener;

    public interface OnOrderChangeListener {
        void onOrderChanged(List<Order> orders);
    }

    public MenuAdapter(List<Order> orders, ConstraintLayout constraintLayout, OnOrderChangeListener orderChangeListener) {
        this.constraintLayout=constraintLayout;
        this.orders = orders;
        this.orderChangeListener = orderChangeListener;
    }


    public List<Order> getOrders() {
        return orders;
    }

    @NonNull
    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, viewGroup, false);
        return new MenuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MyViewHolder holder, int position) {
        Order order=orders.get(position);
        holder.productTxt.setText(order.getProduct().getProductName());

        holder.qtyTxt.setText(String.valueOf(order.getQuantity()));

        holder.menuImage.setImageBitmap(order.getProduct().decodeImage(order.getProduct().getProductImage()));

        holder.minusTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getQuantity() > 1) {
                    order.setQuantity(order.getQuantity() - 1);
                    holder.qtyTxt.setText(String.valueOf(order.getQuantity()));
                    System.out.println("₱"+String.valueOf(order.getOrderTotal()));
                    holder.priceTxt.setText("₱"+String.valueOf(order.getOrderTotal()));
                    notifyItemChanged(position);
                    if (orderChangeListener != null) {
                        orderChangeListener.onOrderChanged(orders);
                    }
                }
            }
        });

        holder.plusTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.getQuantity() < 99) {
                    order.setQuantity(order.getQuantity() + 1);
                    holder.qtyTxt.setText(String.valueOf(order.getQuantity()));
                    holder.priceTxt.setText("₱"+String.valueOf(order.getOrderTotal()));
                    notifyItemChanged(position);
                    if (orderChangeListener != null) {
                        orderChangeListener.onOrderChanged(orders);
                    }
                }
            }
        });

        holder.priceTxt.setText("₱"+String.valueOf(order.getOrderTotal()));

        holder.deleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders.remove(order);

                if (orders.isEmpty()) {
                    constraintLayout.setVisibility(View.VISIBLE);
                }else{
                    constraintLayout.setVisibility(View.GONE);
                }
                notifyDataSetChanged();
                if (orderChangeListener != null) {
                    orderChangeListener.onOrderChanged(orders);
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView menuImage;
        TextView productTxt,minusTxt,plusTxt,qtyTxt,deleteTxt,priceTxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            menuImage=itemView.findViewById(R.id.imageView9);
            productTxt=itemView.findViewById(R.id.textView8);
            minusTxt=itemView.findViewById(R.id.textView11);
            plusTxt=itemView.findViewById(R.id.textView13);
            qtyTxt=itemView.findViewById(R.id.textView12);
            priceTxt=itemView.findViewById(R.id.textView9);
            deleteTxt=itemView.findViewById(R.id.textView10);

        }
    }
}
