package com.example.deliverydeliverycustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerView";
    private Context context;
    private ArrayList<ModelClass> modelClassArrayList;

    public RecyclerAdapter(Context context, ArrayList<ModelClass> modelClassArrayList) {
        this.context = context;
        this.modelClassArrayList = modelClassArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.statusOfOrder.setText(modelClassArrayList.get(position).getStatus());
        holder.offeredAmount.setText(modelClassArrayList.get(position).getOfferedAmount());
        holder.orderDate.setText(modelClassArrayList.get(position).getDate());

        Glide.with(context).load(modelClassArrayList.get(position).getImageurl())
                .into(holder.vehicleTypeCircularImage);
    }

    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        ImageView vehicleTypeCircularImage;
        TextView statusOfOrder, orderDate, offeredAmount;

        public ViewHolder(View itemView){
            super(itemView);
            vehicleTypeCircularImage = itemView.findViewById(R.id.vehicleTypeCircularImage);
            statusOfOrder = itemView.findViewById(R.id.statusOfOrder);
            orderDate = itemView.findViewById(R.id.orderDate);
            offeredAmount = itemView.findViewById(R.id.offeredAmount);
        }


    }
}
