package com.example.deliverydeliverycustomer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddTaskFragment extends Fragment {
    /***********************************************************************************/
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    /***********************************************************************************/

    ImageView imageHatchBack, imageSedan, imageSUV, imagePickUp;
    EditText edtPickUpLocation, edtDropOffLocation, etdPackageWeight,edtAmount;
    TextView tvVehicleType;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String uid = "";
    Button btnRequestDriver;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

    }
    private void init(){

        //Fetching UID
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        this.uid = firebaseUser.getUid();

        //Initializing
        imageHatchBack = getView().findViewById(R.id.imageHatchBack);
        imageSedan = getView().findViewById(R.id.imageSedan);
        imageSUV = getView().findViewById(R.id.imageSUV);
        imagePickUp = getView().findViewById(R.id.imagePickUp);

        //Fields
        edtPickUpLocation = getView().findViewById(R.id.edtPickUpLocation);
        edtDropOffLocation = getView().findViewById(R.id.edtDropOffLocation);
        etdPackageWeight = getView().findViewById(R.id.etdPackageWeight);
        edtAmount = getView().findViewById(R.id.edtAmount);
        tvVehicleType = getView().findViewById(R.id.tvVehicleType);
        btnRequestDriver = getView().findViewById(R.id.btnRequestDriver);

        //setting onclick
        imageHatchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVehicleType("hatchback");
            }
        });
        imageSedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVehicleType("sedan");
            }
        });
        imageSUV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVehicleType("suv");
            }
        });
        imagePickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVehicleType("pickup");
            }
        });
        btnRequestDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation())
                    requestDriver();
            }
        });
    }
    private void setVehicleType(String vehicleTypeName){
        this.tvVehicleType.setText(vehicleTypeName);
        tvVehicleType.setVisibility(View.VISIBLE);
    }
    private void requestDriver(){
        Toast.makeText(getContext(), "Request Driver Successful", Toast.LENGTH_SHORT).show();
    }
    private boolean checkValidation(){
        Boolean flag = true;
        if(edtPickUpLocation.getText().toString().isEmpty()){
            flag = false;
            edtPickUpLocation.setError("This Field is Necessary");
        }
        if(tvVehicleType.getText().toString().isEmpty()){
            flag = false;
            tvVehicleType.setVisibility(View.VISIBLE);
            tvVehicleType.setError("This Field is Necessary");
        }
        if(edtDropOffLocation.getText().toString().isEmpty()){
            flag = false;
            edtDropOffLocation.setError("This Field is Necessary");
        }
        if(etdPackageWeight.getText().toString().isEmpty()){
            flag = false;
            etdPackageWeight.setError("This Field is Necessary");
        }
        else if(Double.parseDouble(etdPackageWeight.getText().toString()) <= 0){
            flag = false;
            etdPackageWeight.setError("Enter Weight In Pounds");
        }
        if(edtAmount.getText().toString().isEmpty()){
            flag = false;
            edtAmount.setError("This Field is Necessary");
        }
        else if(Double.parseDouble(edtAmount.getText().toString()) < 10){
            flag = false;
            edtAmount.setError("Minimum Amount You Can Offer Is $10");
        }
        return flag;
    }





























    /***********************************************************************************/

    public AddTaskFragment() {
    }
    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }
}