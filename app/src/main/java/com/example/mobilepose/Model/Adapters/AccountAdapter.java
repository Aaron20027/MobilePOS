package com.example.mobilepose.Model.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilepose.Model.Listeners.SelectUserListener;
import com.example.mobilepose.Model.User;
import com.example.mobilepose.R;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder>{

    private List<User> users;
    SelectUserListener listener;

    public AccountAdapter(List<User> users, SelectUserListener listener) {
        this.users = users;
        this.listener=listener;
    }

    public void setFilteredList(List<User> filteredList){
        this.users=filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = users.get(position);

        holder.usernameTxt.setText(user.getUsername());
        holder.nameTxt.setText(user.getFname()+" "+user.getLname());
        holder.typeTxt.setText(String.valueOf(user.getType(Integer.valueOf(user.getType())).charAt(0)));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(users.get(position),users);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView usernameTxt,nameTxt,typeTxt;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameTxt=itemView.findViewById(R.id.usernameTxt);
            nameTxt=itemView.findViewById(R.id.nameTxt);
            typeTxt=itemView.findViewById(R.id.typeTxt);
            cardView=itemView.findViewById(R.id.userCard);
        }
    }
}
