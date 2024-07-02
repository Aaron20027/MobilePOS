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

import com.example.mobilepose.Controller.AccountCreation;
import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AccountManagement extends Fragment implements SelectUserListener {

    FloatingActionButton createUserBtn;

    SearchView searchView;
    List<User> itemList;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;

    AccountAdapter userItemAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_management, container, false);
        
        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        searchView=view.findViewById(R.id.searchbar);
        searchView.clearFocus();

        User.getUsers(new UserCallback() {
            @Override
            public void onProductsFetched(List<User> users) {
                userItemAdapter = new AccountAdapter(users, AccountManagement.this);
                ParentRecyclerViewItem.setAdapter(userItemAdapter);
                ParentRecyclerViewItem.setLayoutManager(layoutManager);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterList(newText,users);
                        return true;
                    }
                });
            }

            @Override
            public void onError(Throwable error) {

            }


        });

        createUserBtn=(view).findViewById(R.id.createAccountFab);
        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, AccountCreation.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });


        return view;
    }

    private void filterList(String newText, List<User> users) {
        List<User> filteredList=new ArrayList<>();
        for(User user: users){
            if(user.getUsername().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(user);
            }
        }

        userItemAdapter.setFilteredList(filteredList);
    }


    @Override
    public void onItemClick(User user) {
        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.user_details,
                        (ConstraintLayout) getActivity().findViewById(R.id.userDetails)
                );

        setTextview(bottomSheetView, user);

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


        SetText(user);



        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void SetText(User user){
        TextView userTxt=bottomSheetView.findViewById(R.id.usernameTxt);
        TextView fnameTxt=bottomSheetView.findViewById(R.id.firstnameTxt);
        TextView lnameTxt=bottomSheetView.findViewById(R.id.lastnameTxt);
        TextView passwordTxt=bottomSheetView.findViewById(R.id.passwordTxt);
        TextView typeTxt=bottomSheetView.findViewById(R.id.typeTxt);
        TextView statusTxt=bottomSheetView.findViewById(R.id.statusTxt);
        userTxt.setText(user.getUsername());
        fnameTxt.setText(user.getFname());
        lnameTxt.setText(user.getFname());
        passwordTxt.setText(user.getPasswordProtected());
        typeTxt.setText(user.getTypeConvert());
        statusTxt.setText(user.getStatus());
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
                updateUser();

            }
        });
    }

    private void updateUser() {
    }


    private void showDeletePopup(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.delete_pop);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView deleteTxt=dialog.findViewById(R.id.couponcode);
        deleteTxt.setText("Do you want to delete this user?");

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
                deleteUser();
            }
        });

    }

    private void deleteUser() {
    }

    private void setTextview(View view, User user){

    }

}