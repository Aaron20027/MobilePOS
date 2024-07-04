package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.CouponManagement;
import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.User;
import com.example.mobilepose.R;

public class CouponCreation extends Fragment {

    String[] couponStatus={"Percentage","Fixed Ammount"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    TextView discountValueText, backBtn;
    EditText discountValueEdit;

    TextView couponCodeTxt,couponDescTxt,couponStatTxt,couponAmtTxt,couponStartTxt,couponEndTxt;
    RadioGroup availabiltyGrp;

    int discountType=2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_coupon_creation, container, false);

        backBtn=view.findViewById(R.id.backCouponBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, CouponManagement.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        couponCodeTxt=view.findViewById(R.id.couponCodeEdit);
        couponDescTxt=view.findViewById(R.id.couponDescEdit);
        //autto complete view text
        //couponStatTxt=view.findViewById(R.id.couponStatusEdit);
        couponAmtTxt=view.findViewById(R.id.couponAmmntEdit);
        couponStartTxt=view.findViewById(R.id.couponStartEdit);
        couponEndTxt=view.findViewById(R.id.couponEndEdit);

        availabiltyGrp=view.findViewById(R.id.availabilityRadio);

        autoCompleteTextView=view.findViewById(R.id.autoCompleteTextView2);
        arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,couponStatus);
        autoCompleteTextView.setAdapter(arrayAdapter);

        discountValueText=view.findViewById(R.id.textView65);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();

                if (item.equals("Percentage")){
                    discountValueText.setText("Percentage");
                    couponAmtTxt.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                    discountType=0;

                }else{
                    discountValueText.setText("Ammount");
                    couponAmtTxt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    discountType=1;
                }

            }
        });

        Button createButton=view.findViewById(R.id.createCouponBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCoupon(view);
            }
        });


        return view;
    }

    public void CreateCoupon(View view){
        int typeid=availabiltyGrp.getCheckedRadioButtonId();
        RadioButton radioButton = view.findViewById(typeid);

        int avail;
        if (radioButton.getText().toString().equals("Available")) {
            avail=0;
        }else{
            avail=1;
        }


        Coupon.addCoupon(new Coupon(1,
                couponCodeTxt.getText().toString(),
                couponDescTxt.getText().toString(),
                discountType,
                Float.parseFloat(couponAmtTxt.getText().toString()),
                avail),getActivity());


        couponCodeTxt.setText("");
        couponDescTxt.setText("");
        autoCompleteTextView.setText("Select item");
        arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,couponStatus);
        autoCompleteTextView.setAdapter(arrayAdapter);
        discountValueText.setText("Percentage/Ammount");
        discountType=2;
        couponAmtTxt.setText("");
        availabiltyGrp.clearCheck();

        Toast.makeText(view.getContext(), "Coupon has been added!", Toast.LENGTH_SHORT).show();
    }

}