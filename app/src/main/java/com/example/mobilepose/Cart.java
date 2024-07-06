package com.example.mobilepose;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Controller.AccountManagement;
import com.example.mobilepose.Controller.Home;
import com.example.mobilepose.Model.Adapters.MenuAdapter;
import com.example.mobilepose.Model.Order;
import com.example.mobilepose.Model.ShoppingCart;


public class Cart extends Fragment {
    ShoppingCart shoppingCart=new ShoppingCart();

    TextView discountTxt,subtotalTxt,taxTxt,totalTxt,backBtn;
    EditText couponCodeEdit;
    ConstraintLayout constraint;

    Button applyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            shoppingCart = bundle.getParcelable("cart");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        MenuAdapter menuAdapter = new MenuAdapter(shoppingCart.getOrders(),constraint);
        RecyclerView recyclerView = view.findViewById(R.id.parentRecycle);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(layoutManager);

        innitText(view);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(couponCodeEdit.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter a coupon code!", Toast.LENGTH_SHORT).show();
                }else{
                    shoppingCart.validateCoupon(couponCodeEdit.getText().toString(),getActivity());
                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shoppingCart.setOrders(menuAdapter.getOrders());

                Bundle bundle=new Bundle();
                bundle.putParcelable("cart",shoppingCart);

                Home home = new Home();
                home.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, home)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        shoppingCart.calculateAll();
        SetText();





        return view;
    }

    public void innitText(View view){
        discountTxt=view.findViewById(R.id.textView20);
        subtotalTxt=view.findViewById(R.id.textView21);
        taxTxt=view.findViewById(R.id.textView22);
        totalTxt=view.findViewById(R.id.textView23);

        constraint=view.findViewById(R.id.ErrorLayout);

        couponCodeEdit=view.findViewById(R.id.editTextText4);

        applyBtn=view.findViewById(R.id.applyBtn);
        backBtn=view.findViewById(R.id.backShopBtn);
    }

    public void SetText(){
        subtotalTxt.setText(String.format("₱%.2f",shoppingCart.getSubtotal()));
        taxTxt.setText(String.format("₱%.2f",shoppingCart.getVat()));
        totalTxt.setText(String.format("₱%.2f",shoppingCart.getTotal()));
    }



}