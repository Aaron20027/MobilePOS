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
import android.widget.TextView;

import com.example.mobilepose.ProductManagement;
import com.example.mobilepose.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductCreation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductCreation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] productCategory={"Add New Category"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Button cancelButton,saveButton;
    EditText newCatEdit;

    TextView backBtn;

    public ProductCreation() {
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
    public static ProductCreation newInstance(String param1, String param2) {
        ProductCreation fragment = new ProductCreation();
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
        View view= inflater.inflate(R.layout.fragment_product_creation, container, false);

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
        arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item,productCategory);
        autoCompleteTextView.setAdapter(arrayAdapter);


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

        return view;
    }

    public void CreateProduct(View view){
        //code to ADD Product to database
    }

    public void AddNewCategory(){
        //code to add category to database
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
                AddNewCategory();
            }
        });


        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();


    }
}