package com.example.mobilepose;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    private List<ChildItem> ChildItemList;
    private SelectItemListener listener;

    ChildItemAdapter(List<ChildItem> childItemList,SelectItemListener listener)
    {
        this.ChildItemList = childItemList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.child_item,
                        viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position)
    {
        ChildItem childItem = ChildItemList.get(position);
        childViewHolder.ChildItemTitle.setText(childItem.getProduct().getProductName());
        childViewHolder.ChildItemPrice.setText(String.valueOf(childItem.getProduct().getProductPrice()));
        childViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(ChildItemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return ChildItemList.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView ChildItemTitle, ChildItemPrice;
        CardView cardView;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            ChildItemTitle = itemView.findViewById(R.id.child_item_title);
            ChildItemPrice= itemView.findViewById(R.id.child_item_price);
            cardView= itemView.findViewById(R.id.cardView);
        }
    }
} 