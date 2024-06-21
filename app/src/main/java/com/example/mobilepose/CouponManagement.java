package com.example.mobilepose;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilepose.Controller.CouponCreation;
import com.example.mobilepose.Controller.ProductCreation;
import com.example.mobilepose.Model.Coupon;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponManagement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CouponManagement extends Fragment implements SelectCouponListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FloatingActionButton createCouponBtn;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;

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
        View view= inflater.inflate(R.layout.fragment_coupon_management, container, false);
        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());


        CouponAdapter couponItemAdapter = new CouponAdapter(couponItemList(), this);
        ParentRecyclerViewItem.setAdapter(couponItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);


        createCouponBtn=(view).findViewById(R.id.createCouponFab);
        createCouponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, CouponCreation.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

        return view;
    }


    private List<Coupon> couponItemList()
    {
        List<Coupon> itemList = new ArrayList<>();

        Coupon item = new Coupon("111","Test","1","100","11/11/11","11/11/15","1");
        itemList.add(item);
        Coupon item1 = new Coupon("222","Test1","1","101","11/11/12","11/11/14","1");
        itemList.add(item1);
        Coupon item2 = new Coupon("333","Test2","1","102","11/11/13","11/11/13","1");
        itemList.add(item2);
        Coupon item3 = new Coupon("444","Test3","1","103","11/11/14","11/11/12","1");
        itemList.add(item3);
        Coupon item4= new Coupon("555","Test4","1","104","11/11/15","11/11/11","1");
        itemList.add(item4);
        Coupon item5= new Coupon("555","Test4","1","104","11/11/15","11/11/11","1");
        itemList.add(item5);
        Coupon item6= new Coupon("555","Test4","1","104","11/11/15","11/11/11","1");
        itemList.add(item6);

        return itemList;
    }
    @Override
    public void onItemClick(Coupon coupon) {
        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.coupon_details,
                        (ConstraintLayout) getActivity().findViewById(R.id.couponDetails)
                );

        setTextview(bottomSheetView, coupon);



        TextView updateBtn=bottomSheetView.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdatePopup();

            }
        });


        TextView deleteBtn=bottomSheetView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopup();
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    private void showUpdatePopup(){
        Group editGroup=bottomSheetView.findViewById(R.id.editgroup);
        Group textGroup=bottomSheetView.findViewById(R.id.textgroup);

        editGroup.setVisibility(View.VISIBLE);
        textGroup.setVisibility(View.GONE);



        Button cancelBtn=bottomSheetView.findViewById(R.id.cancelCouponBtn);
        Button saveBtn=bottomSheetView.findViewById(R.id.saveCouponBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGroup.setVisibility(View.GONE);
                textGroup.setVisibility(View.VISIBLE);

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update coupon
                updateCoupon();

            }
        });
    }



    private void showDeletePopup(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.delete_pop);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView deleteTxt=dialog.findViewById(R.id.couponcode);
        deleteTxt.setText("Do you want to delete this coupon?");

        Button cancelDeleteBtn=dialog.findViewById(R.id.cancelDeleteBtn);
        cancelDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button deleteBtn=dialog.findViewById(R.id.deleteProductBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCoupon();
            }
        });

    }

    private void setTextview(View view, Coupon coupon){
        TextView couponCodeTxt, couponDescTxt, couponStatTxt, couponAmmntTxt, couponStartTxt, couponEndTxt, couponAvailTxt;
        couponCodeTxt=view.findViewById(R.id.couponcode);
        couponCodeTxt.setText(coupon.getCouponCode());

        couponDescTxt=view.findViewById(R.id.coupDescTxt);
        couponDescTxt.setText(coupon.getCouponDesc());

        couponStatTxt=view.findViewById(R.id.coupStatusTxt);
        couponStatTxt.setText(coupon.getCouponStat());

        couponAmmntTxt=view.findViewById(R.id.coupAmmountTxt);
        couponAmmntTxt.setText(coupon.getCouponAmmnt());

        couponStartTxt=view.findViewById(R.id.coupStartTxt);
        couponStartTxt.setText(coupon.getCouponStart());

        couponEndTxt=view.findViewById(R.id.coupEndTxt);
        couponEndTxt.setText(coupon.getCouponEnd());

        couponAvailTxt=view.findViewById(R.id.coupAvailTxt);
        couponAvailTxt.setText(coupon.getCouponAvail());
    }

    private void updateCoupon(){

    }

    private void deleteCoupon(){

    }
}