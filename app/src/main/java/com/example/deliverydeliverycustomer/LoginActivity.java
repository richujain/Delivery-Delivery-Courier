package com.example.deliverydeliverycustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.deliverydeliverycustomer.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.ForkJoinPool;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Before setContentView
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Arkhip_font.ttf")
       .setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,CustomerHomePage.class));
            finish();
        }

        //Init Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("users");

        //Init View
        rootLayout = findViewById(R.id.rootLayout);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
    }

    private void showLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        dialog.setTitle("Sign In");


        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login,null);

        final MaterialEditText edtEmail = login_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = login_layout.findViewById(R.id.edtPassword);

        dialog.setView(login_layout);

        //set button
        dialog.setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        //validation
                        if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                            Snackbar.make(rootLayout, "Please Enter Email Address", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                            Snackbar.make(rootLayout, "Please Enter Password", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (edtPassword.getText().toString().trim().length() < 6) {
                            Snackbar.make(rootLayout, "Password Too Short!", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        //Login
                        firebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim())
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        startActivity(new Intent(LoginActivity.this,CustomerHomePage.class));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void showRegisterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        dialog.setTitle("Register ");


        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register,null);

        final MaterialEditText edtEmail = register_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = register_layout.findViewById(R.id.edtPassword);
        final MaterialEditText edtName = register_layout.findViewById(R.id.edtName);
        final MaterialEditText edtPhone = register_layout.findViewById(R.id.edtPhone);

        dialog.setView(register_layout);

        //set button
        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //validation
                if(TextUtils.isEmpty(edtEmail.getText().toString().trim())){
                    Snackbar.make(rootLayout,"Please Enter Email Address", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(edtPassword.getText().toString().trim())){
                    Snackbar.make(rootLayout,"Please Enter Password", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(edtName.getText().toString().trim())){
                    Snackbar.make(rootLayout,"Please Enter Name", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(edtPhone.getText().toString().trim())){
                    Snackbar.make(rootLayout,"Please Enter Phone", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(edtPassword.getText().toString().trim().length() < 6){
                    Snackbar.make(rootLayout,"Password Too Short!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Register new user
                firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //Save user to firebase database
                                User user = new User();
                                user.setEmail(edtEmail.getText().toString().trim());
                                user.setPassword(edtPassword.getText().toString().trim());
                                user.setName(edtName.getText().toString().trim());
                                user.setPhone(edtPhone.getText().toString().trim());

                                //user email as key
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(rootLayout,"Registeration Successful", Snackbar.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(rootLayout,"Failed : "+ e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout,"Failed : "+ e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
}