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

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mobilepose.Controller.CouponCreation;
import com.example.mobilepose.Controller.ProductCreation;
import com.example.mobilepose.Model.Coupon;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CouponManagement extends Fragment implements SelectCouponListener{
    FloatingActionButton createCouponBtn;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;
    SearchView searchView;
    List<Coupon> itemList;

    CouponAdapter couponItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_coupon_management, container, false);
        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        searchView=view.findViewById(R.id.searchbar);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        couponItemAdapter = new CouponAdapter(couponItemList(), this);
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

    private void filterList(String newText) {
        List<Coupon> filteredList=new ArrayList<>();
        for(Coupon coupon: itemList){
            if(coupon.getCouponCode().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(coupon);
            }
        }

        couponItemAdapter.setFilteredList(filteredList);
    }


    private List<Coupon> couponItemList()
    {
        itemList = new ArrayList<>();

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

        TextView couponAmtTxt=bottomSheetView.findViewById(R.id.textView4);


        String[] couponStatus={"Percentage","Fixed Ammount"};
        AutoCompleteTextView autoCompleteTextView=bottomSheetView.findViewById(R.id.coupStatusDrop);
        ArrayAdapter arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,couponStatus);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();

                if (item.equals("Percentage")){
                    couponAmtTxt.setText("Percentage");
                    couponAmtTxt.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

                }else{
                    couponAmtTxt.setText("Ammount");
                    couponAmtTxt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }

            }
        });



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