package com.example.mobilepose.Model.Adapters;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mobilepose.Model.Product;
import com.example.mobilepose.Model.Listeners.SelectItemListener;
import com.example.mobilepose.R;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    private List<Product> ChildItemList;
    private SelectItemListener listener;
    private int parentPosition;

    ChildItemAdapter(List<Product> childItemList,SelectItemListener listener, int parentPosition)
    {
        this.ChildItemList = childItemList;
        this.listener=listener;
        this.parentPosition=parentPosition;
    }

    public void setFilteredList(List<Product> filteredList){
        this.ChildItemList=filteredList;
        notifyDataSetChanged();
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
        Product childItem = ChildItemList.get(position);
        childViewHolder.ChildItemTitle.setText(childItem.getProductName());
        childViewHolder.imageView.setImageBitmap(childItem.decodeImage(childItem.getProductImage()));
        childViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(ChildItemList.get(position),ChildItemList);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return ChildItemList.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView ChildItemTitle;
        CardView cardView;
        ImageView imageView;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            ChildItemTitle = itemView.findViewById(R.id.child_item_title);
            cardView= itemView.findViewById(R.id.cardView);
            imageView= itemView.findViewById(R.id.img_child_item);
        }
    }
} 