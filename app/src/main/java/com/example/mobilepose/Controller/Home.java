package com.example.mobilepose.Controller;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Model.Callbacks.CategoriesCallback;
import com.example.mobilepose.Model.Category;
import com.example.mobilepose.Model.Adapters.MenuAdapter;
import com.example.mobilepose.Model.API.Entities.LoginResponse;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.Model.Callbacks.ProductCallback;
import com.example.mobilepose.Model.Order;
import com.example.mobilepose.Model.ParentItem;
import com.example.mobilepose.Model.Adapters.ParentItemAdapter;
import com.example.mobilepose.R;
import com.example.mobilepose.Model.Listeners.SelectItemListener;
import com.example.mobilepose.Model.ShoppingCart;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends Fragment implements SelectItemListener, MenuAdapter.OnOrderChangeListener {
    SearchView searchView;
    private ShoppingCart shoppingCart=new ShoppingCart();
    ParentItemAdapter parentItemAdapter;
    private List<Category> categoriesList;
    private Map<Category, List<Product>> categoryProductsMap = new HashMap<>();
    private int categoriesFetchedCount = 0;
    ConstraintLayout constraint;
    TextView totalTxt;



    String loginUserInfo;
    int passLength;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);
        searchView = view.findViewById(R.id.searchbar);
        FloatingActionButton buttonShowCart = view.findViewById(R.id.shopCartBtn);
        ConstraintLayout constraintLayout=view.findViewById(R.id.constraintLayout5);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        parentItemAdapter = new ParentItemAdapter(this);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);



        if (getArguments() != null) {
            loginUserInfo = getArguments().getString("loginUserInfo");
            passLength=getArguments().getInt("passCount");
            LoginResponse loginResponse = Utils.FromJson(loginUserInfo, LoginResponse.class);

            TextView nameTxt= view.findViewById(R.id.name);
            nameTxt.setText(loginResponse.firstName+" "+loginResponse.lastName);
            TextView typeTxt= view.findViewById(R.id.typeTxt);
            typeTxt.setText(String.valueOf(loginResponse.accountType.toString().charAt(0)));

            ShoppingCart updatedCart = getArguments().getParcelable("cart");
            if (updatedCart != null) {
                this.shoppingCart = updatedCart;
            }
        }


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

        parentItemAdapter.setItemList(filteredParentItemList);
        parentItemAdapter.notifyDataSetChanged();

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

        constraint=bottomSheetView.findViewById(R.id.ErrorLayout);
        if (shoppingCart.getOrders().isEmpty()) {
            constraint.setVisibility(View.VISIBLE);
        }else{
            constraint.setVisibility(View.GONE);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        MenuAdapter menuAdapter = new MenuAdapter(shoppingCart.getOrders(),constraint, this);
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.parentRecycle);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setLayoutManager(layoutManager);

        shoppingCart.setDiscountAmt(0);
        shoppingCart.calculateAll();

        totalTxt=bottomSheetView.findViewById(R.id.textView23);
        totalTxt.setText(String.format("₱%.2f",shoppingCart.getTotal()));

        Button CancelBtn=bottomSheetView.findViewById(R.id.cancelTxt);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelOrder(bottomSheetDialog);
            }
        });

        Button ProceedBtn=bottomSheetView.findViewById(R.id.proceedTxt);
        ProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(shoppingCart.getOrders().size() ==0)){
                    bottomSheetDialog.dismiss();
                    ShowPayment();
                }else{
                    Toast.makeText(getActivity(), "Shopping Cart is empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    public void ShowPayment(){
        Bundle bundle=new Bundle();
        bundle.putParcelable("cart",shoppingCart);
        bundle.putString("loginUserInfo", loginUserInfo);
        bundle.putInt("passCount", passLength);

        Cart cart = new Cart();
        cart.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, cart)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    private void CancelOrder(BottomSheetDialog bottomSheetDialog){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.delete_pop);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView deleteTxt=dialog.findViewById(R.id.couponcode);
        deleteTxt.setText("Do you want to cancel this order?");

        Button cancelDeleteBtn=dialog.findViewById(R.id.cancelDeleteBtn);
        cancelDeleteBtn.setText("No");
        cancelDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button deleteBtn=dialog.findViewById(R.id.deleteProductBtn);
        deleteBtn.setText("Yes");
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCart.clearOrder();
                dialog.dismiss();
                bottomSheetDialog.dismiss();
            }
        });
    }



    public void ShowMyAccount(){

        Bundle bundle = new Bundle();
        bundle.putString("loginUserInfo", loginUserInfo);
        bundle.putInt("passCount", passLength);

        MyAccount myAccount = new MyAccount();
        myAccount.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, myAccount)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    @Override
    public void onItemClick(Product childitem, List<Product> products) {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.order_pop);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView prodName=dialog.findViewById(R.id.prodName);
        prodName.setText(childitem.getProductName());
        TextView prodDesc=dialog.findViewById(R.id.prodDesc);
        prodDesc.setText(childitem.getProductDescription());
        TextView prodPrice=dialog.findViewById(R.id.prodPrice);
        prodPrice.setText("₱"+String.valueOf(childitem.getProductPrice()));
        TextView prodTotal=dialog.findViewById(R.id.prodTotal);
        ImageView imageView=dialog.findViewById(R.id.imageView3);
        //imageView.setImageBitmap(childitem.decodeImage(childitem.getProductImage()));

        TextView plusTxt,minusTxt,quantityTxt,prodTotalTxt;
        minusTxt=dialog.findViewById(R.id.textView11);
        plusTxt=dialog.findViewById(R.id.textView13);
        quantityTxt=dialog.findViewById(R.id.textView12);
        prodTotalTxt=dialog.findViewById(R.id.prodTotal);

        minusTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(quantityTxt.getText().toString()) > 1) {
                    quantityTxt.setText(String.valueOf(Integer.parseInt(quantityTxt.getText().toString())-1));
                    prodTotalTxt.setText("₱"+String.valueOf(Integer.parseInt(quantityTxt.getText().toString())*childitem.getProductPrice()));
                }
            }
        });

        plusTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(quantityTxt.getText().toString()) < 99) {
                    quantityTxt.setText(String.valueOf(Integer.parseInt(quantityTxt.getText().toString())+1));
                    prodTotalTxt.setText("₱"+String.valueOf(Integer.parseInt(quantityTxt.getText().toString())*childitem.getProductPrice()));
                }

            }
        });

        Button addToCart=dialog.findViewById(R.id.addToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(shoppingCart.orderExists(childitem)) {
                    Toast.makeText(getActivity(), "Order exist in shopping cart!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (Integer.parseInt(quantityTxt.getText().toString()) > 0){
                        Order order=new Order(childitem,Integer.parseInt(quantityTxt.getText().toString()));

                        shoppingCart.addOrder(order);
                        Toast.makeText(getActivity(), "Order has been added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }else{
                        Toast.makeText(getActivity(), "Quantity is 0!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public void onOrderChanged(List<Order> orders) {

        shoppingCart.setOrders(orders);
        shoppingCart.calculateAll();
        totalTxt.setText(String.format("₱%.2f",shoppingCart.getTotal()));

    }

}