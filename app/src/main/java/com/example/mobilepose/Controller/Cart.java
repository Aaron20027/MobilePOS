package com.example.mobilepose.Controller;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Controller.AccountManagement;
import com.example.mobilepose.Controller.Home;
import com.example.mobilepose.Model.Adapters.MenuAdapter;
import com.example.mobilepose.Model.Callbacks.CouponCallback;
import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Order;
import com.example.mobilepose.Model.ShoppingCart;
import com.example.mobilepose.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;


public class Cart extends Fragment implements MenuAdapter.OnOrderChangeListener {
    ShoppingCart shoppingCart=new ShoppingCart();
    TextView discountTxt,subtotalTxt,taxTxt,totalTxt,backBtn, changeTxt;
    EditText couponCodeEdit, receivedEdit;
    RecyclerView recyclerView;
    ConstraintLayout constraint, paymentCon,cashCon;
    Button applyBtn ,cancelBtn, proceedBtn,cashBtn,creditBtn;
    private Coupon appliedCoupon;
    String loginUserInfo;
    int passLength;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            loginUserInfo = getArguments().getString("loginUserInfo");
            passLength= getArguments().getInt("passCount");
            shoppingCart = bundle.getParcelable("cart");
        }

        appliedCoupon=new Coupon();

        innitText(view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        MenuAdapter menuAdapter = new MenuAdapter(shoppingCart.getOrders(),constraint,this);
        recyclerView = view.findViewById(R.id.parentRecycle);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(layoutManager);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String couponCode = couponCodeEdit.getText().toString();
                if (TextUtils.isEmpty(couponCode)) {
                    Toast.makeText(getActivity(), "Please enter a coupon code!", Toast.LENGTH_SHORT).show();
                } else {
                    shoppingCart.validateCoupon(couponCode, getActivity(), new CouponCallback() {
                        @Override
                        public void onProductsFetched(List<Coupon> coupons) {
                            for (Coupon coupon : coupons) {
                                if (coupon.getCouponCode().equals(couponCode)) {
                                    appliedCoupon = coupon;
                                    if (appliedCoupon.getCouponAvail()==0){
                                        shoppingCart.applyDiscount(coupon);
                                        shoppingCart.calculateAll();
                                        SetText();
                                        Toast.makeText(getActivity(), "Coupon applied successfully!", Toast.LENGTH_SHORT).show();
                                        applyBtn.setEnabled(false);
                                        couponCodeEdit.setEnabled(false);
                                        return;
                                    }
                                }
                            }
                            Toast.makeText(getActivity(), "Invalid coupon code!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(getActivity(), "Error applying coupon: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyBtn.setEnabled(false);
                couponCodeEdit.setEnabled(false);
                recyclerView.setVisibility(View.GONE);
                paymentCon.setVisibility(View.VISIBLE);

            }
        });

        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cash Button Clicked", Toast.LENGTH_SHORT).show();
                // Check if this toast message appears
            }
        });

        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentCon.setVisibility(View.GONE);
                cashCon.setVisibility(View.VISIBLE);


                receivedEdit = view.findViewById(R.id.cashEdit);
                changeTxt = view.findViewById(R.id.textView20);


                receivedEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().isEmpty()) {
                            int receivedAmount = Integer.parseInt(s.toString());
                            changeTxt.setText(String.valueOf(shoppingCart.calculateChange(receivedAmount)));
                        } else {
                            changeTxt.setText("₱0");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelOrder();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCart.setOrders(menuAdapter.getOrders());

                Bundle bundle=new Bundle();
                bundle.putParcelable("cart",shoppingCart);
                bundle.putString("loginUserInfo", loginUserInfo);
                bundle.putInt("passCount", passLength);

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
        paymentCon=view.findViewById(R.id.paymentCon);
        cashCon=view.findViewById(R.id.cashCon);

        couponCodeEdit=view.findViewById(R.id.editTextText4);

        applyBtn=view.findViewById(R.id.applyBtn);
        backBtn=view.findViewById(R.id.backShopBtn);
        cancelBtn=view.findViewById(R.id.cancelTxt);
        proceedBtn=view.findViewById(R.id.proceedTxt);
        cashBtn=view.findViewById(R.id.cashBtn);
        creditBtn=view.findViewById(R.id.creditBtn);

    }

    public void SetText(){
        discountTxt.setText("-"+String.format("₱%.2f", shoppingCart.getDiscountAmt()));
        subtotalTxt.setText(String.format("₱%.2f",shoppingCart.getSubtotal()));
        taxTxt.setText(String.format("₱%.2f",shoppingCart.getVat()));
        totalTxt.setText(String.format("₱%.2f",shoppingCart.getTotal()));
    }

    private void CancelOrder(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.delete_pop);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView deleteTxt=dialog.findViewById(R.id.couponcode);
        deleteTxt.setText("Do you want to cancel this order?");

        Button cancelDeleteBtn=dialog.findViewById(R.id.cancelDeleteBtn);
        cancelDeleteBtn.setText("No");
        cancelDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button deleteBtn=dialog.findViewById(R.id.deleteProductBtn);
        deleteBtn.setText("Yes");
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCart.clearOrder();
                dialog.dismiss();

                Bundle bundle=new Bundle();
                bundle.putString("loginUserInfo", loginUserInfo);
                bundle.putInt("passCount", passLength);

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
    }


    @Override
    public void onOrderChanged(List<Order> orders) {
        if (appliedCoupon != null) {
            shoppingCart.applyDiscount(appliedCoupon);
        }
        shoppingCart.setOrders(orders);
        shoppingCart.calculateAll();
        SetText();


    }
}