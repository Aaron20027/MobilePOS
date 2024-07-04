package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mobilepose.CategoriesCallback;
import com.example.mobilepose.Category;
import com.example.mobilepose.Model.API.Entities.ProductCategory;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.ProductManagement;
import com.example.mobilepose.R;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ProductCreation extends Fragment {

    String[] productCategory={"Add New Category"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Button cancelButton,saveButton,createButton;
    EditText newCatEdit;
    TextView backBtn;
    EditText prodNameTxt,prodDescTxt,prodPriceTxt,prodCatTxt;
    RadioGroup proAvailGrp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_product_creation, container, false);


        prodNameTxt=view.findViewById(R.id.productNameEdit);
        prodDescTxt=view.findViewById(R.id.productDescEdit);
        prodPriceTxt=view.findViewById(R.id.productPriceEdit);
        //prodCatTxt=view.findViewById(R.id.productCategoryEdit);
        proAvailGrp=view.findViewById(R.id.availabilityRadio);



        backBtn=view.findViewById(R.id.backProductBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProductManagement.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        autoCompleteTextView=view.findViewById(R.id.autoCompleteTextView3);

        Product.getCategories(new CategoriesCallback() {
            @Override
            public void onProductsFetched(List<Category> categories) {
                List<String> categoriesList=new ArrayList<String>();
                for (Category cat: categories){
                    categoriesList.add(cat.getCategoryName());
                }
                arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,categoriesList);
                categoriesList.add("Add New Category");
                autoCompleteTextView.setAdapter(arrayAdapter);
            }

            @Override
            public void onError(Throwable error) {

            }
        });



        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item=adapterView.getItemAtPosition(position).toString();

                if (item.equals("Add New Category")){
                    ShowAddCategory();
                }else{

                }

            }
        });

        createButton=view.findViewById(R.id.createProductBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateProduct();
            }
        });

        return view;
    }

    public void CreateProduct(){
        Product product=new Product(1,
                prodNameTxt.getText().toString(),
                Float.valueOf(prodPriceTxt.getText().toString()),
                prodDescTxt.getText().toString(),
                ProductCategory.PIZZA,
                "111");
        Product.addProducts(product,getActivity());
    }

    public void ShowAddCategory(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.add_category_pop,
                        (ConstraintLayout) getActivity().findViewById(R.id.addCategory)
                );

        newCatEdit=bottomSheetView.findViewById(R.id.newCatEdit);
        cancelButton=bottomSheetView.findViewById(R.id.cancelCatBtn);
        saveButton=bottomSheetView.findViewById(R.id.saveCatBtn);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product.addCategories(newCatEdit.getText().toString(),getActivity());
                newCatEdit.setText("");
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }




}