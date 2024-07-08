package com.example.mobilepose.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.Callbacks.CategoriesCallback;
import com.example.mobilepose.Model.Category;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.R;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProductCreation extends Fragment {

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Button cancelButton, saveButton, createButton;
    EditText newCatEdit;
    TextView backBtn;
    EditText prodNameTxt, prodDescTxt, prodPriceTxt;
    List<String> categoriesList = new ArrayList<String>();
    ImageButton productImg;

    String sImage;

    int category = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_creation, container, false);


        prodNameTxt = view.findViewById(R.id.productNameEdit);
        prodDescTxt = view.findViewById(R.id.productDescEdit);
        prodPriceTxt = view.findViewById(R.id.productPriceEdit);
        productImg = view.findViewById(R.id.imageButton3);

        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalley();
            }
        });


        backBtn = view.findViewById(R.id.backProductBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger = getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProductManagement.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView3);

        Product.getCategories(new CategoriesCallback() {
            @Override
            public void onProductsFetched(List<Category> categories) {
                categoriesList = new ArrayList<String>();
                for (Category cat : categories) {
                    categoriesList.add(cat.getCategoryName());
                }
                categoriesList.add("Add New Category");
                arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, categoriesList);
                autoCompleteTextView.setAdapter(arrayAdapter);

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        String item = adapterView.getItemAtPosition(position).toString();

                        if (item.equals("Add New Category")) {
                            ShowAddCategory(categories);
                        } else {
                            for (Category cat : categories) {
                                if (item.equals(cat.getCategoryName())) {
                                    category = cat.getCategoryId();
                                    ;
                                }
                            }

                        }

                    }
                });
            }

            @Override
            public void onError(Throwable error) {

            }
        });

        createButton = view.findViewById(R.id.createProductBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateProduct(view);
            }
        });

        return view;
    }

    public void CreateProduct(View view) {
        if (TextUtils.isEmpty(prodNameTxt.getText().toString()) || TextUtils.isEmpty(prodDescTxt.getText().toString()) ||
                TextUtils.isEmpty(prodPriceTxt.getText().toString()) || autoCompleteTextView.getText().toString().equals("Select item")) {
            Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (prodNameTxt.length() < 6 || prodNameTxt.length() > 30) {
            Toast.makeText(getActivity(), "Product name must be between 6 to 30 characters.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (prodDescTxt.length() < 6 || prodDescTxt.length() > 70) {
            Toast.makeText(getActivity(), "Product description must be between 6 to 70 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(prodPriceTxt.getText().toString());

            if (price <= 0) {
                Toast.makeText(getActivity(), "Price must be greater than zero.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Invalid price amount.", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(1,
                prodNameTxt.getText().toString(),
                Float.valueOf(prodPriceTxt.getText().toString()),
                prodDescTxt.getText().toString(),
                category,
                sImage, 0);
        Product.addProducts(product, getActivity());

        prodNameTxt.setText("");
        prodDescTxt.setText("");
        prodPriceTxt.setText("");
        productImg.setImageResource(R.color.gray);
        autoCompleteTextView.setText("Select item");
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, categoriesList);
        autoCompleteTextView.setAdapter(arrayAdapter);
        Toast.makeText(view.getContext(), "Product has been added!", Toast.LENGTH_SHORT).show();

    }

    public void ShowAddCategory(List<Category> categories) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.add_category_pop,
                        (ConstraintLayout) getActivity().findViewById(R.id.addCategory)
                );

        newCatEdit = bottomSheetView.findViewById(R.id.newCatEdit);
        cancelButton = bottomSheetView.findViewById(R.id.cancelCatBtn);
        saveButton = bottomSheetView.findViewById(R.id.saveCatBtn);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product.addCategories(newCatEdit.getText().toString(), getActivity());
                categoriesList.set(categoriesList.size() - 2, newCatEdit.getText().toString());
                arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, categoriesList);
                autoCompleteTextView.setAdapter(arrayAdapter);
                newCatEdit.setText("");
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public void openGalley() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGalley.launch(intent);
    }


    ActivityResultLauncher<Intent> openGalley = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Uri uriImage = o.getData().getData();
                    productImg.setImageURI(uriImage);

                    sImage = Product.encodeImage(getActivity(), uriImage);


                }
            });





}