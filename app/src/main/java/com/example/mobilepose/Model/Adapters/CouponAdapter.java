package com.example.mobilepose.Model.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Listeners.SelectCouponListener;
import com.example.mobilepose.R;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder>{

    private List<Coupon> coupons;

    SelectCouponListener listener;

    public void setFilteredList(List<Coupon> filteredList){
        this.coupons=filteredList;
        notifyDataSetChanged();
    }

    public CouponAdapter(List<Coupon> coupons,SelectCouponListener listener) {
        this.coupons = coupons;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coupon_item, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Coupon coupon = coupons.get(position);

        if(coupon.getCouponType()==0){
            holder.ammountTxt.setText(String.valueOf((int)coupon.getCouponAmmnt())+"% OFF");
        }else{
            holder.ammountTxt.setText(coupon.getCouponAmmnt()+ " OFF");
        }

        holder.codeTxt.setText(coupon.getCouponCode());
        //holder.dateTxt.setText(coupon.getCouponStart()+"-"+coupon.getCouponEnd());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(coupons.get(position),coupons);
            }
        });
    }


    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView ammountTxt,codeTxt;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ammountTxt=itemView.findViewById(R.id.ammountTxt);
            codeTxt=itemView.findViewById(R.id.codeTxt);

            cardView=itemView.findViewById(R.id.couponCard);
        }
    }
}
