package com.example.deliverydeliverycustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ViewOrderDetails extends AppCompatActivity {
    EditText editTextCategory, editTextFrom, editTextTo, editTextDate, editTextWeight, editTextAmount;
    Button updateButton;
    TextView textViewStatus;
    DatabaseReference databaseReferenceTwo;
    String key;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String uid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_details);
        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        this.uid = firebaseUser.getUid();
        key = null;

        if (getIntent().hasExtra("key")) {
            key = getIntent().getStringExtra("key");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "key");
        }
        editTextCategory = findViewById(R.id.editTextCategory);
        textViewStatus = findViewById(R.id.textViewStatus);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextTo = findViewById(R.id.editTextTo);
        editTextDate = findViewById(R.id.editTextDate);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextAmount = findViewById(R.id.editTextAmount);
        updateButton = findViewById(R.id.updateButton);
        //updating fields
        databaseReferenceTwo = FirebaseDatabase.getInstance().getReference().child("orders").child(key);
        setValuesForFields();

        //writing
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

    private void setValuesForFields() {
        databaseReferenceTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String flag = dataSnapshot.child("category").getValue().toString();
                editTextCategory.setText(flag);
                flag = dataSnapshot.child("pickuplocation").getValue().toString();
                editTextFrom.setText(flag);
                flag = dataSnapshot.child("dropofflocation").getValue().toString();
                editTextTo.setText(flag);
                flag = dataSnapshot.child("date").getValue().toString();
                editTextDate.setText(flag);
                flag = dataSnapshot.child("amount").getValue().toString();
                editTextAmount.setText(flag);
                flag = dataSnapshot.child("weight").getValue().toString();
                editTextWeight.setText(flag);
                flag = dataSnapshot.child("status").getValue().toString();
                textViewStatus.setText(flag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*Query query = databaseReferenceTwo.child(key).child("category");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String flag = snapshot.child("category").getValue().toString();
                        editTextCategory.setText(flag);
                        flag = snapshot.child("pickuplocation").getValue().toString();
                        editTextFrom.setText(flag);
                        flag = snapshot.child("dropofflocation").getValue().toString();
                        editTextTo.setText(flag);
                        flag = snapshot.child("date").getValue().toString();
                        editTextDate.setText(flag);
                        flag = snapshot.child("amount").getValue().toString();
                        editTextAmount.setText(flag);
                        flag = snapshot.child("weight").getValue().toString();
                        editTextWeight.setText(flag);
                        flag = snapshot.child("status").getValue().toString();
                        textViewStatus.setText(flag);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
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