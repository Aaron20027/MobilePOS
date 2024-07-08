package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilepose.Model.ShoppingCart;
import com.example.mobilepose.R;

public class Payment extends Fragment {

    ShoppingCart shoppingCart;
    String loginUserInfo;
    int passLength;
    Button cashBtn,creditBtn;
    EditText receivedEdit;
    TextView changeTxt;
    ConstraintLayout paymentCon,cashCon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            loginUserInfo = bundle.getString("loginUserInfo");
            passLength = bundle.getInt("passCount");
            shoppingCart = bundle.getParcelable("cart");
        }

        paymentCon = view.findViewById(R.id.paymentCon);
        cashCon = view.findViewById(R.id.cashCon);

        cashBtn = view.findViewById(R.id.cashBtn);

        creditBtn = view.findViewById(R.id.creditBtn);


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

        creditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle credit/debit button click
            }
        });

        return view;
    }


}