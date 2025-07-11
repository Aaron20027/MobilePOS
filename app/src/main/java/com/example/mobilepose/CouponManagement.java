package com.example.mobilepose;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponManagement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CouponManagement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] couponStatus={"Percentage","Fixed Ammount"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    TextView discountValueText;
    EditText discountValueEdit;

    public CouponManagement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CouponManagement.
     */
    // TODO: Rename and change types and number of parameters
    public static CouponManagement newInstance(String param1, String param2) {
        CouponManagement fragment = new CouponManagement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_coupon_management, container, false);

        autoCompleteTextView=view.findViewById(R.id.autoCompleteTextView2);
        arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,couponStatus);
        autoCompleteTextView.setAdapter(arrayAdapter);

        discountValueText=view.findViewById(R.id.textView65);
        discountValueEdit=view.findViewById(R.id.couponAmmntEdit);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();

                if (item.equals("Percentage")){
                    discountValueText.setText("Percentage");
                    discountValueEdit.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

                }else{
                    discountValueText.setText("Ammount");
                    discountValueEdit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }

            }
        });
        return view;
    }

    public void CreateCoupon(View view){
        //code to ADD Coupon to database
    }
    public void UpdateCoupon(View view){
        //code to UPDATE Coupon to database
    }
    public void SearchCoupon(View view){
        //code to SEARCH Coupon to database
    }
    public void DeleteCoupon(View view){
        //code to DELETE Coupon to database
    }
}