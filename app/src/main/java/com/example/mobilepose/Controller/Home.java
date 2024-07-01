package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mobilepose.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends Fragment {
    private ChipGroup categoryChips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton buttonShowCart = view.findViewById(R.id.shopCartBtn);
        ConstraintLayout constraintLayout=view.findViewById(R.id.constraintLayout5);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMyAccount();
            }
        });
        buttonShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowShoppingCart();
            }
        });

        return view;


    }

    public void createChips(String[] chipTexts){
        //programatically create chips for home
        for (String text : chipTexts) {
            Chip chip = new Chip(getActivity());
            chip.setId(View.generateViewId());
            chip.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ));
            chip.setText(text);
            chip.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            chip.setChipBackgroundColorResource(R.color.backOrange);
            chip.setCheckedIconTintResource(R.color.black);
            chip.setCloseIconTintResource(R.color.black);
            chip.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.poppins));
            chip.setCloseIconVisible(true);
            categoryChips.addView(chip);
        }
    }



    public void getProductCategory(){
        //code that will read the category table in database and return it
    }

    public void ShowShoppingCart(){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.shopping_cart_popup,
                        (ConstraintLayout) getActivity().findViewById(R.id.shoppingcart)
                );

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    public void ShowMyAccount(){
        FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
        fragmentmanger.beginTransaction()
                .replace(R.id.fragmentContainerView, MyAccount.class,null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();


    }
}