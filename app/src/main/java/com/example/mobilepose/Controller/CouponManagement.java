package com.example.mobilepose.Controller;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Adapters.CouponAdapter;
import com.example.mobilepose.Model.Callbacks.CouponCallback;
import com.example.mobilepose.R;
import com.example.mobilepose.Model.Listeners.SelectCouponListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CouponManagement extends Fragment implements SelectCouponListener {
    FloatingActionButton createCouponBtn;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;
    SearchView searchView;
    CouponAdapter couponItemAdapter;
    TextView couponAmtTxt;
    EditText couponDescEdit,couponAmtEdit;
    RadioGroup availGrp;
    AutoCompleteTextView autoCompleteTextView;

    int discountType=2;
    ConstraintLayout constraint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_coupon_management, container, false);
        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        searchView=view.findViewById(R.id.searchbar);
        searchView.clearFocus();

        constraint=view.findViewById(R.id.ErrorLayout);


        Coupon.getCoupons(new CouponCallback() {
            @Override
            public void onProductsFetched(List<Coupon> coupons) {
                couponItemAdapter = new CouponAdapter(coupons, CouponManagement.this);
                ParentRecyclerViewItem.setAdapter(couponItemAdapter);
                ParentRecyclerViewItem.setLayoutManager(layoutManager);

                if (coupons.isEmpty()) {
                    constraint.setVisibility(View.VISIBLE);
                }else{
                    constraint.setVisibility(View.GONE);
                }

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterList(newText,coupons);
                        return true;
                    }
                });

            }

            @Override
            public void onError(Throwable error) {

            }
        });

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

    private void filterList(String newText,List<Coupon> coupons) {
        List<Coupon> filteredList=new ArrayList<>();
        for(Coupon coupon: coupons){
            if(coupon.getCouponCode().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(coupon);
            }
        }

        if (filteredList.isEmpty()) {
            constraint.setVisibility(View.VISIBLE);
        }else{
            constraint.setVisibility(View.GONE);
        }

        couponItemAdapter.setFilteredList(filteredList);
    }


    @Override
    public void onItemClick(Coupon coupon, List<Coupon> coupons) {
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
                showUpdatePopup(coupon, coupons);

            }
        });


        TextView deleteBtn=bottomSheetView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopup(coupon, coupons);
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    private void showUpdatePopup(Coupon coupon, List<Coupon> coupons){
        Group editGroup=bottomSheetView.findViewById(R.id.editgroup);
        Group textGroup=bottomSheetView.findViewById(R.id.textgroup);

        editGroup.setVisibility(View.VISIBLE);
        textGroup.setVisibility(View.GONE);

        couponAmtTxt=bottomSheetView.findViewById(R.id.textView4);

        couponDescEdit=bottomSheetView.findViewById(R.id.coupDescEdit);
        couponAmtEdit=bottomSheetView.findViewById(R.id.coupAmmountEdit);
        availGrp=bottomSheetView.findViewById(R.id.availabilityRadio);

        String[] couponStatus={"Percentage","Fixed Ammount"};
        autoCompleteTextView=bottomSheetView.findViewById(R.id.coupStatusDrop);
        ArrayAdapter arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,couponStatus);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();

                if (item.equals("Percentage")){
                    couponAmtTxt.setText("Percentage");
                    couponAmtTxt.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                    discountType=0;

                }else{
                    couponAmtTxt.setText("Ammount");
                    couponAmtTxt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    discountType=1;
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
                updateCoupon(coupon,coupons);

            }
        });
    }

    private void updateCoupon(Coupon coupon, List<Coupon> coupons) {

        if (!couponDescEdit.getText().toString().trim().isEmpty()) {
            if (couponDescEdit.length() < 6 || couponDescEdit.length() > 70) {
                Toast.makeText(getActivity(), "Coupon Description must be between 6 to 70 characters.", Toast.LENGTH_SHORT).show();
                return;
            }
            coupon.setCouponDesc(couponDescEdit.getText().toString());
        }

        if (!(discountType==2)) {
            coupon.setCouponType(discountType);
        }

        if (!couponAmtEdit.getText().toString().trim().isEmpty()) {
            if (autoCompleteTextView.getText().toString().equals("Percentage")) {

                try {
                    double percentage = Double.parseDouble(couponAmtEdit.getText().toString());
                    if (percentage <= 0 || percentage > 100) {
                        Toast.makeText(getActivity(), "Percentage must be between 0 and 100.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    coupon.setCouponAmmnt(Float.parseFloat(couponAmtEdit.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Percentage must be a number!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            if (autoCompleteTextView.getText().toString().equals("Fixed Ammount")) {
                try {
                    double ammount = Double.parseDouble(couponAmtEdit.getText().toString());

                    if (ammount <= 0 ) {
                        Toast.makeText(getActivity(), "Ammount must be greater than 0!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    coupon.setCouponAmmnt(Float.parseFloat(couponAmtEdit.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Ammount must be a number!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        int selectedAvailId = availGrp.getCheckedRadioButtonId();

        if (selectedAvailId != -1) {
            RadioButton selectedTypeButton = bottomSheetView.findViewById(selectedAvailId);
            String coupAvail = selectedTypeButton.getText().toString();
            coupon.setCouponAvail(coupAvail);
            System.out.println(coupAvail);
        }

        for (Coupon coup : coupons) {
            if (coup.getCouponId()==coup.getCouponId()) {
                coup.setCouponDesc(coupon.getCouponDesc());
                coup.setCouponType(coupon.getCouponType());
                System.out.println(coupon.getCouponAmmnt());
                coup.setCouponAmmnt(coupon.getCouponAmmnt());
                coup.setCouponAvail(coupon.getCouponAvail());
                break;
            }
        }

        couponAmtTxt.setText("Percentage/Ammount");
        bottomSheetDialog.dismiss();
        couponItemAdapter.setFilteredList(coupons);
        Coupon.updateCoupon(coupon,bottomSheetView.getContext());
        Toast.makeText(bottomSheetView.getContext(), "Coupon has been updated!", Toast.LENGTH_SHORT).show();

    }



    private void showDeletePopup(Coupon coupon, List<Coupon> coupons){
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
                coupons.remove(coupon);
                bottomSheetDialog.dismiss();
                dialog.dismiss();
                couponItemAdapter.setFilteredList(coupons);
                Coupon.deleteCoupon(coupon,bottomSheetView.getContext());
                Toast.makeText(bottomSheetView.getContext(), "Coupon has been deleted!", Toast.LENGTH_SHORT).show();
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
        couponStatTxt.setText(coupon.getCouponType(coupon.getCouponType()));


        TextView ammountTxt=view.findViewById(R.id.textView4);
        couponAmmntTxt=view.findViewById(R.id.coupAmmountTxt);

        if(coupon.getCouponType()==0){
            ammountTxt.setText("Percentage");
            couponAmmntTxt.setText(String.valueOf((int)coupon.getCouponAmmnt())+"%");
        }else{
            ammountTxt.setText("Fixed Ammount");
            couponAmmntTxt.setText(String.valueOf(coupon.getCouponAmmnt()));
        }


        couponAvailTxt=view.findViewById(R.id.coupAvailTxt);
        couponAvailTxt.setText(coupon.getCouponAvail(coupon.getCouponAvail()));
    }



}