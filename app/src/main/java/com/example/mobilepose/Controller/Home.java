package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mobilepose.ChildItem;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.Model.ProductCallback;
import com.example.mobilepose.ParentItem;
import com.example.mobilepose.ParentItemAdapter;
import com.example.mobilepose.ProductManagement;
import com.example.mobilepose.R;
import com.example.mobilepose.SelectItemListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements SelectItemListener {
    private ChipGroup categoryChips;
    List<String> categories=new ArrayList<>();
    SearchView searchView;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;
    ParentItemAdapter parentItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);
        searchView = view.findViewById(R.id.searchbar);

        categories.add("Pizza");
        categories.add("Doughnouts");
        categories.add("Drinks");

        Product.getProducts(new ProductCallback() {
            @Override
            public void onProductsFetched(List<Product> products) {
                parentItemAdapter = new ParentItemAdapter(ParentItemList(products,categories), Home.this);
                ParentRecyclerViewItem.setAdapter(parentItemAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                ParentRecyclerViewItem.setLayoutManager(layoutManager);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterList(newText, products,categories);
                        return true;
                    }
                });
            }

            @Override
            public void onError(Throwable error) {
                // Handle error
            }
        });





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

    private void filterList(String newText, List<Product> products,List<String> categories) {
        List<ParentItem> filteredParentItemList = new ArrayList<>();

        for (String category : categories) {
            List<ChildItem> filteredChildItemList = new ArrayList<>();
            for (Product product : products) {
                if (product.getProductName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredChildItemList.add(new ChildItem(product));
                }
            }
        }

        parentItemAdapter.setFilteredList(filteredParentItemList);

    }



    private List<ParentItem> ParentItemList(List<Product> products,List<String> categories)
    {
        List<ParentItem> itemList = new ArrayList<>();

        for (String category : categories) {
            if (!products.isEmpty()) {
                ParentItem item = new ParentItem(category, ChildItemList(products));
                itemList.add(item);
            }
        }

        return itemList;
    }

    private List<ChildItem> ChildItemList(List<Product> products)
    {
        List<ChildItem> ChildItemList = new ArrayList<>();

        for (Product product : products) {
            ChildItem item = new ChildItem(product);
            ChildItemList.add(item);
        }

        return ChildItemList;
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

    @Override
    public void onItemClick(ChildItem childitem) {
        bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.order_pop,
                        (ConstraintLayout) getActivity().findViewById(R.id.orderDetails)
                );


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }
}