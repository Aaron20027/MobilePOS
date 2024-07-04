package com.example.mobilepose;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mobilepose.Controller.ProductCreation;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.ProductCategory;
import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.Model.ProductCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManagement extends Fragment implements SelectItemListener{

    private FloatingActionButton createProductBtn;
    ParentItemAdapter parentItemAdapter;

    String[] productCategory={"Testing"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;
    SearchView searchView;
    private List<Category> categoriesList;
    private Map<Category, List<Product>> categoryProductsMap = new HashMap<>();
    private int categoriesFetchedCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_management, container, false);

        searchView = view.findViewById(R.id.searchbar);

        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);

        /*
        Product.getProducts(new ProductCallback() {
            @Override
            public void onProductsFetched(List<Product> products) {
                parentItemAdapter = new ParentItemAdapter(ParentItemList(products,categories), ProductManagement.this);
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
                        filterList(newText, products,ca);
                        return true;
                    }
                });
            }

            @Override
            public void onError(Throwable error) {
                // Handle error
            } parentItemAdapter = new ParentItemAdapter(ParentItemList(products,categories), ProductManagement.this);
                            ParentRecyclerViewItem.setAdapter(parentItemAdapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            ParentRecyclerViewItem.setLayoutManager(layoutManager);
        });

         */





        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        parentItemAdapter = new ParentItemAdapter(this);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

        fetchCategories();



        createProductBtn=(view).findViewById(R.id.createProductFab);
        createProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProductCreation.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });


        return view;
    }


    private void fetchCategories() {
        Product.getCategories(new CategoriesCallback() {
            @Override
            public void onProductsFetched(List<Category> categories) {
                categoriesList = categories;
                for (Category category : categoriesList) {
                    fetchProductsForCategory(category);
                }
            }

            @Override
            public void onError(Throwable error) {
                // Handle error
            }
        });
    }

    private void fetchProductsForCategory(Category category) {
        Product.getProducts(String.valueOf(category.getCategoryId()), new ProductCallback() {
            @Override
            public void onProductsFetched(List<Product> products) {
                categoryProductsMap.put(category, products);
                categoriesFetchedCount++;
                if (categoriesFetchedCount == categoriesList.size()) {
                    sortAndDisplayCategoriesWithProducts();
                }
            }

            @Override
            public void onError(Throwable error) {
                // Handle error
            }
        });
    }

    private void sortAndDisplayCategoriesWithProducts() {
        // Sort categories
        List<Category> sortedCategories = new ArrayList<>(categoriesList);
        sortCategories(sortedCategories);

        // Sort products within each category
        List<ParentItem> parentItemList = new ArrayList<>();
        for (Category category : sortedCategories) {
            List<Product> products = categoryProductsMap.get(category);
            sortProducts(products);
            parentItemList.add(new ParentItem(category.getCategoryName(), products));
        }

        // Update the adapter
        parentItemAdapter.setItemList(parentItemList);
        parentItemAdapter.notifyDataSetChanged();
    }
    private void sortCategories(List<Category> categories) {
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category c1, Category c2) {
                return c1.getCategoryName().compareTo(c2.getCategoryName());
            }
        });
    }

    private void sortProducts(List<Product> products) {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductName().compareTo(p2.getProductName());
            }
        });
    }



    private void filterList(String newText, List<Product> products,List<Category> categories) {
        List<ParentItem> filteredParentItemList = new ArrayList<>();

        for (Category category : categories) {
            List<ChildItem> filteredChildItemList = new ArrayList<>();
            for (Product product : products) {
                if (product.getProductName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredChildItemList.add(new ChildItem(product));
                }
            }
        }

        parentItemAdapter.setFilteredList(filteredParentItemList);

    }


    @Override
    public void onItemClick(Product childitem) {
        bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.product_details_pop,
                        (ConstraintLayout) getActivity().findViewById(R.id.productDetails)
                );


        TextView prodNameTxt=bottomSheetView.findViewById(R.id.prodNameTxt);
        prodNameTxt.setText(childitem.getProductName());

        TextView prodDescTxt=bottomSheetView.findViewById(R.id.prodDescTxt);
        prodDescTxt.setText(childitem.getProductDescription());

        TextView prodPriceTxt=bottomSheetView.findViewById(R.id.prodPriceTxt);
        prodPriceTxt.setText(String.valueOf(childitem.getProductPrice()));

        //TextView prodCatTxt=bottomSheetView.findViewById(R.id.prodCategoryTxt);
        //prodCatTxt.setText(childitem.getProduct().getProductCategory());

        //TextView prodAvailTxt=bottomSheetView.findViewById(R.id.prodAvailabiltyTxt);
        //prodPriceTxt.setText(childitem.getProduct().ge);

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

        EditText prodDesc=bottomSheetView.findViewById(R.id.productDescEdit);
        prodDesc.getText().clear();
        EditText prodPrice=bottomSheetView.findViewById(R.id.productPriceEdit);
        prodPrice.getText().clear();


        autoCompleteTextView=bottomSheetView.findViewById(R.id.autoCompleteTextView3);
        autoCompleteTextView.setText("Select Item");

        arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,productCategory);
        autoCompleteTextView.setAdapter(arrayAdapter);


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();
            }
        });

        //avialabilty

        Button cancelBtn=bottomSheetView.findViewById(R.id.cancelProductBtn);
        Button saveBtn=bottomSheetView.findViewById(R.id.saveProductBtn);
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
                updateProduct();
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
        deleteTxt.setText("Do you want to delete this product?");

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
                deleteProduct();
            }
        });

    }

    private void updateProduct(){
        //code to update product
    }

    private void deleteProduct(){
        //code to delete product
    }

    private void searchProduct(){
        //code to search product
    }

}