package com.example.deliverydeliverycustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewOrderDetails extends AppCompatActivity {
    EditText editTextCategory, editTextFrom, editTextTo, editTextDate, editTextWeight, editTextAmount;
    Button updateButton;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_details);
        init();
    }

    private void init() {
        String key = null;

        if (getIntent().hasExtra("key")) {
            key = getIntent().getStringExtra("key");
            Log.d("key",""+key);
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "key");
        }
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextTo = findViewById(R.id.editTextTo);
        editTextDate = findViewById(R.id.editTextDate);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextAmount = findViewById(R.id.editTextAmount);
        updateButton = findViewById(R.id.updateButton);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference;
        databaseReference = firebaseDatabase.getReference().child("orders").child(key);
        final DatabaseReference finalDatabaseReference = databaseReference;
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()){
                    finalDatabaseReference.child("category").setValue(editTextCategory.getText().toString());
                    finalDatabaseReference.child("pickuplocation").setValue(editTextFrom.getText().toString());
                    finalDatabaseReference.child("dropofflocation").setValue(editTextTo.getText().toString());
                    finalDatabaseReference.child("date").setValue(editTextDate.getText().toString());
                    finalDatabaseReference.child("weight").setValue(editTextWeight.getText().toString());
                    finalDatabaseReference.child("amount").setValue(editTextAmount.getText().toString());
                }
            }
        });

    }
    private boolean checkValidation(){
        Boolean flag = true;
        if(editTextFrom.getText().toString().isEmpty()){
            flag = false;
            editTextFrom.setError("This Field is Necessary");
        }
        if(editTextDate.getText().toString().isEmpty()){
            flag = false;
            editTextDate.setError("This Field is Necessary");
        }
        if(editTextCategory.getText().toString().isEmpty()){
            flag = false;
            editTextCategory.setError("This Field is Necessary");
        }
        if(editTextTo.getText().toString().isEmpty()){
            flag = false;
            editTextTo.setError("This Field is Necessary");
        }
        if(editTextWeight.getText().toString().isEmpty()){
            flag = false;
            editTextWeight.setError("This Field is Necessary");
        }
        else if(Double.parseDouble(editTextWeight.getText().toString()) <= 0){
            flag = false;
            editTextWeight.setError("Enter Weight In Pounds");
        }
        if(editTextAmount.getText().toString().isEmpty()){
            flag = false;
            editTextAmount.setError("This Field is Necessary");
        }
        else if(Double.parseDouble(editTextAmount.getText().toString()) < 10){
            flag = false;
            editTextAmount.setError("Minimum Amount You Can Offer Is $10");
        }
        return flag;
    }
}