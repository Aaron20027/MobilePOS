package com.example.mobilepose;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilepose.Controller.ProductCreation;
import com.example.mobilepose.Controller.Utils;
import com.example.mobilepose.Model.API.APICallback;
import com.example.mobilepose.Model.API.Entities.FetchProductResponse;
import com.example.mobilepose.Model.API.Entities.ResponseBase;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductManagement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductManagement extends Fragment implements SelectItemListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton createProductBtn;

    String[] productCategory={"Testing"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    public ProductManagement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductManagement.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductManagement newInstance(String param1, String param2) {
        ProductManagement fragment = new ProductManagement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_management, container, false);


        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parentRecycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ParentItemAdapter parentItemAdapter = new ParentItemAdapter(ParentItemList(),this);
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);


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

    private List<ParentItem> ParentItemList()
    {
        List<ParentItem> itemList
                = new ArrayList<>();

        ParentItem item
                = new ParentItem(
                "Title 1",
                ChildItemList());
        itemList.add(item);
        ParentItem item1
                = new ParentItem(
                "Title 2",
                ChildItemList());
        itemList.add(item1);
        ParentItem item2
                = new ParentItem(
                "Title 3",
                ChildItemList());
        itemList.add(item2);
        ParentItem item3
                = new ParentItem(
                "Title 4",
                ChildItemList());
        itemList.add(item3);

        return itemList;
    }

    private List<ChildItem> ChildItemList()
    {
        List<ChildItem> ChildItemList
                = new ArrayList<>();

        ChildItemList.add(new ChildItem("Card 1","1"));
        ChildItemList.add(new ChildItem("Card 2","1"));
        ChildItemList.add(new ChildItem("Card 3","1"));
        ChildItemList.add(new ChildItem("Card 4","1"));

        return ChildItemList;
    }

    @Override
    public void onItemClick(ChildItem childitem) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(
                        R.layout.product_details_pop,
                        (ConstraintLayout) getActivity().findViewById(R.id.productDetails)
                );

        TextView updateBtn=bottomSheetView.findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                        if (item.equals("Testing")){

                        }else{

                        }

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
        });


        TextView deleteBtn=bottomSheetView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.delete_pop);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

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
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
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