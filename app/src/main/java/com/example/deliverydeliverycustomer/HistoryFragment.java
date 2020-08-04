package com.example.deliverydeliverycustomer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {


    /***********************************************************************************/
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    /***********************************************************************************/

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private ArrayList<ModelClass> arrayList;
    private RecyclerAdapter recyclerAdapter;
    private Context context;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    String uid = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        this.uid = firebaseUser.getUid();
        recyclerView = getView().findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<>();
        clearAll();
        getDataFromFirebase();
    }

    private void getDataFromFirebase() {
        Query query = databaseReference.child("orders");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("uid").getValue().toString().equals(uid)){
                        ModelClass modelClass = new ModelClass();
                        modelClass.setImageurl(snapshot.child("imageurl").getValue().toString());
                        modelClass.setDate(snapshot.child("date").getValue().toString());
                        modelClass.setOfferedAmount(snapshot.child("amount").getValue().toString());
                        modelClass.setStatus(snapshot.child("status").getValue().toString());
                        arrayList.add(modelClass);
                    }
                }
                recyclerAdapter = new RecyclerAdapter(getContext(),arrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void clearAll(){
        if(arrayList != null){
            arrayList.clear();
            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        arrayList = new ArrayList<>();
    }


    /***********************************************************************************/


    public HistoryFragment() {
        // Required empty public constructor
    }


    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}