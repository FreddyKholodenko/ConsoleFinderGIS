package com.example.consolefindergis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class SellersListActivity extends Activity {

    MyRecyclerViewAdapter adapter;
    ArrayList<SalesInfo> salesInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_list_layout);
        //TODO get data from firebase
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, salesInfo);
        recyclerView.setAdapter(adapter);

    }

}
