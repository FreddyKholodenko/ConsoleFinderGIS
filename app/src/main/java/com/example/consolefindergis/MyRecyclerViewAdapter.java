package com.example.consolefindergis;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<SalesInfo> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<SalesInfo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.description.setText(mData.get(position).description);
        holder.title.setText(mData.get(position).title);
        holder.sellerName.setText(mData.get(position).sellerName);
        holder.sellerNumber.setText(mData.get(position).sellerNumber);
        holder.itemPrice.setText(mData.get(position).itemPrice);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,sellerName,sellerNumber,city,itemPrice;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_page);
            description = itemView.findViewById(R.id.description);
            sellerName = itemView.findViewById(R.id.sellerName);
            sellerNumber = itemView.findViewById(R.id.sellerNumber);
            city = itemView.findViewById(R.id.city);
        }

    }

}

