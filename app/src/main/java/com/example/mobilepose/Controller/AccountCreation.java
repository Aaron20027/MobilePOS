package com.example.mobilepose.Controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilepose.AccountManagement;
import com.example.mobilepose.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountCreation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountCreation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView backBtn;

    public AccountCreation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountManagement.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountCreation newInstance(String param1, String param2) {
        AccountCreation fragment = new AccountCreation();
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

        View view=inflater.inflate(R.layout.fragment_account_creation, container, false);
        backBtn=view.findViewById(R.id.backAccountBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanger= getActivity().getSupportFragmentManager();
                fragmentmanger.beginTransaction()
                        .replace(R.id.fragmentContainerView, AccountManagement.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        return view;
    }

    public void CreateUser(View view){
        //code to ADD user to database
    }
    public void UpdateUser(View view){
        //code to UPDATE user to database
    }
    public void SearchUser(View view){
        //code to SEARCH user to database
    }
    public void DeleteUser(View view){
        //code to DELETE user to database
    }

}