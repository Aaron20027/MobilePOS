package com.example.mobilepose.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.Callbacks.CategoriesCallback;
import com.example.mobilepose.Model.Category;
import com.example.mobilepose.Model.ChildItem;
import com.example.mobilepose.Model.ParentItem;
import com.example.mobilepose.Model.Adapters.ParentItemAdapter;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.Model.Callbacks.ProductCallback;
import com.example.mobilepose.R;
import com.example.mobilepose.Model.Listeners.SelectItemListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManagement extends Fragment implements SelectItemListener {

    private FloatingActionButton createProductBtn;
    RecyclerView ParentRecyclerViewItem;
    ParentItemAdapter parentItemAdapter;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    View bottomSheetView;
    BottomSheetDialog bottomSheetDialog;
    SearchView searchView;
    private List<Category> categoriesList;
    private Map<Category, List<Product>> categoryProductsMap = new HashMap<>();
    private int categoriesFetchedCount = 0;
    int category=-1;
    RadioGroup availGrp;
    ConstraintLayout constraint;
    EditText prodDesc,prodPrice;
    ImageButton prodImage;
    String sImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_management, container, false);

        searchView = view.findViewById(R.id.searchbar);

        int searchTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = searchView.findViewById(searchTextId);
        searchEditText.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        searchEditText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.black));


        ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);

        constraint= view.findViewById(R.id.ErrorLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        parentItemAdapter = new ParentItemAdapter(this);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);

        fetchCategories();

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
        categoriesFetchedCount = 0;
        categoryProductsMap.clear();
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

        List<Category> sortedCategories = new ArrayList<>(categoriesList);
        sortCategories(sortedCategories);


        List<ParentItem> parentItemList = new ArrayList<>();
        for (Category category : sortedCategories) {
            List<Product> products = categoryProductsMap.get(category);


            if (products.size()>0) {
                sortProducts(products);
                parentItemList.add(new ParentItem(category.getCategoryName(), products));
            }
        }

        if (parentItemList.isEmpty()) {
            constraint.setVisibility(View.VISIBLE);
        } else {
            constraint.setVisibility(View.GONE);
        }

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



    private void filterList(String newText) {
        List<ParentItem> filteredParentItemList = new ArrayList<>();


        List<Category> sortedCategories = new ArrayList<>(categoriesList);
        sortCategories(sortedCategories);


        for (Category category : sortedCategories) {
            List<Product> products = categoryProductsMap.get(category);


            List<Product> filteredProducts = new ArrayList<>();
            for (Product product : products) {
                if (product.getProductName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }


            if (!filteredProducts.isEmpty()) {
                filteredParentItemList.add(new ParentItem(category.getCategoryName(), filteredProducts));
            }
        }

        if (filteredParentItemList.isEmpty()) {
            constraint.setVisibility(View.VISIBLE);
        } else {
            constraint.setVisibility(View.GONE);
        }

        parentItemAdapter.setItemList(filteredParentItemList);
        parentItemAdapter.notifyDataSetChanged();




    }


    @Override
    public void onItemClick(Product childitem, List<Product> products) {
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

        TextView prodCatTxt=bottomSheetView.findViewById(R.id.prodCategoryTxt);

        ImageView prodImage=bottomSheetView.findViewById(R.id.imageView3);
        prodImage.setImageBitmap(childitem.decodeImage(childitem.getProductImage()));

        Product.getCategories(new CategoriesCallback() {
            @Override
            public void onProductsFetched(List<Category> categories) {
                for(Category cat:categories){
                    if(cat.getCategoryId()==childitem.getProductCategory()){
                        prodCatTxt.setText(cat.getCategoryName());
                    }
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });


        TextView prodAvailTxt=bottomSheetView.findViewById(R.id.prodAvailabiltyTxt);
        prodAvailTxt.setText(childitem.getProductAvailability(childitem.getProductAvailability()));



        TextView updateBtn=bottomSheetView.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdatePopup(childitem);

            }
        });


        TextView deleteBtn=bottomSheetView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopup(childitem,products);
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
    private void showUpdatePopup(Product childitem){
        Group editGroup=bottomSheetView.findViewById(R.id.editgroup);
        Group textGroup=bottomSheetView.findViewById(R.id.textgroup);

        editGroup.setVisibility(View.VISIBLE);
        textGroup.setVisibility(View.GONE);

        prodDesc=bottomSheetView.findViewById(R.id.productDescEdit);
        prodDesc.getText().clear();
        prodPrice=bottomSheetView.findViewById(R.id.productPriceEdit);
        prodPrice.getText().clear();

        autoCompleteTextView=bottomSheetView.findViewById(R.id.autoCompleteTextView3);

        prodImage=bottomSheetView.findViewById(R.id.imageView3);

        prodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalley();
            }
        });


        Product.getCategories(new CategoriesCallback() {
            @Override
            public void onProductsFetched(List<Category> categories) {
                List<String> categoriesList=new ArrayList<String>();
                for (Category cat: categories){
                    categoriesList.add(cat.getCategoryName());
                }
                arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,categoriesList);
                autoCompleteTextView.setAdapter(arrayAdapter);

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        String item=adapterView.getItemAtPosition(position).toString();
                            for (Category cat: categories){
                                if(item.equals(cat.getCategoryName())){
                                    category=cat.getCategoryId();;
                                }
                            }
                        }
                });
            }

            @Override
            public void onError(Throwable error) {

            }
        });


        availGrp=bottomSheetView.findViewById(R.id.availabilityRadio);


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
                updateProduct(childitem);
            }
        });
    }

    private void showDeletePopup(Product childitem, List<Product> products){
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
                bottomSheetDialog.dismiss();
                dialog.dismiss();
                Product.deleteProduct(childitem,getActivity());
                fetchCategories();
                Toast.makeText(bottomSheetView.getContext(), "Product has been deleted!", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void updateProduct(Product childitem){
        if (!prodDesc.getText().toString().trim().isEmpty()) {
            if (prodDesc.length() < 6 || prodDesc.length() > 70) {
                Toast.makeText(getActivity(), "Product description must be between 6 to 70 characters.", Toast.LENGTH_SHORT).show();
                return;
            }
            childitem.setProductDescription(prodDesc.getText().toString());
        }
        if (!prodPrice.getText().toString().trim().isEmpty()) {
            try {
                double price = Double.parseDouble(prodPrice.getText().toString());

                if (price <= 0) {
                    Toast.makeText(getActivity(), "Price must be greater than zero.", Toast.LENGTH_SHORT).show();
                    return;
                }
                childitem.setProductPrice(Integer.valueOf(prodPrice.getText().toString()));
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Invalid price amount.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(category!=-1){
            childitem.setProductCategory(category);
        }

        if(sImage!=null){
            childitem.setProductImage(sImage);
        }

        int selectedTypeId = availGrp.getCheckedRadioButtonId();

        if (selectedTypeId != -1) {
            RadioButton selectedTypeButton = bottomSheetView.findViewById(selectedTypeId);
            String userType = selectedTypeButton.getText().toString();
        }

        bottomSheetDialog.dismiss();
        Product.updateProduct(childitem,bottomSheetView.getContext());
        sImage=null;
        fetchCategories();
        Toast.makeText(bottomSheetView.getContext(), "Product has been updated!", Toast.LENGTH_SHORT).show();


    }

    public void openGalley() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGalley.launch(intent);
    }


    ActivityResultLauncher<Intent> openGalley = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {

                    try{
                        Uri uriImage = o.getData().getData();
                        prodImage.setImageURI(uriImage);
                        sImage = Product.encodeImage(getActivity(), uriImage);
                    }catch (Exception e){

                    }

                }
            });


}