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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTaskFragment extends Fragment {
    /***********************************************************************************/
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    /***********************************************************************************/

    ImageView imageHatchBack, imageSedan, imageSUV, imagePickUp;
    EditText edtDescription,edtPickUpLocation, edtDropOffLocation, etdPackageWeight,edtAmount;
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
        edtDescription = getView().findViewById(R.id.edtDescription);
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
        String pickUpLocation = edtPickUpLocation.getText().toString();
        String dropOffLocation = edtDropOffLocation.getText().toString();
        String amount = edtAmount.getText().toString();
        String weight = etdPackageWeight.getText().toString();
        String vehicleType = tvVehicleType.getText().toString();
        String description = edtDescription.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference = firebaseDatabase.getReference().child("orders").push();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //writing values
        databaseReference.child("uid").setValue(uid);
        databaseReference.child("category").setValue(description);
        databaseReference.child("date").setValue(date);
        databaseReference.child("pickuplocation").setValue(pickUpLocation);
        databaseReference.child("dropofflocation").setValue(dropOffLocation);
        databaseReference.child("amount").setValue(amount);
        databaseReference.child("weight").setValue(weight);
        databaseReference.child("vehicletype").setValue(vehicleType);
        databaseReference.child("status").setValue("Not Accepted");
        databaseReference.child("driver").setValue("driver");
        databaseReference.child("completedby").setValue("driveruid");
        if(vehicleType.equals("hatchback")){
            databaseReference.child("imageurl").setValue("https://www.readingroomco.com/wp-content/uploads/2020/08/hatchback1.png");
        }else if(vehicleType.equals("sedan")){
            databaseReference.child("imageurl").setValue("https://www.readingroomco.com/wp-content/uploads/2020/08/sedan.png");
        }if(vehicleType.equals("suv")){
            databaseReference.child("imageurl").setValue("https://www.readingroomco.com/wp-content/uploads/2020/08/suv1.png");
        }else if(vehicleType.equals("pickup")){
            databaseReference.child("imageurl").setValue("https://www.readingroomco.com/wp-content/uploads/2020/08/pickup1.png");
        }

        //open History fragment
        HistoryFragment nextFrag= new HistoryFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerParent, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
    private boolean checkValidation(){
        Boolean flag = true;
        if(edtPickUpLocation.getText().toString().isEmpty()){
            flag = false;
            edtPickUpLocation.setError("This Field is Necessary");
        }
        if(edtDescription.getText().toString().isEmpty()){
            flag = false;
            edtDescription.setError("This Field is Necessary");
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