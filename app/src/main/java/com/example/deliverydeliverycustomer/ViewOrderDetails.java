package com.example.deliverydeliverycustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextTo = findViewById(R.id.editTextTo);
        editTextDate = findViewById(R.id.editTextDate);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextAmount = findViewById(R.id.editTextAmount);
        updateButton = findViewById(R.id.updateButton);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference = firebaseDatabase.getReference().child("orders").child("-MDXjTTqAgsFm0iyGhqm");
        final DatabaseReference finalDatabaseReference = databaseReference;
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDatabaseReference.child("amount").setValue("100");
            }
        });

    }
}