package com.example.consolefindergis;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SellersListActivity extends Activity {
    private DatabaseReference rootReference = FirebaseDatabase.getInstance("https://console-finder-gis-f12bd-default-rtdb.firebaseio.com/").getReference();
    static private String console;
    static private String city;
    ArrayList<String> indexes = new ArrayList();

    MyRecyclerViewAdapter adapter;
    ArrayList<SalesInfo> salesInfo;
    ArrayList<String> updatedIndexes = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_list_layout);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.city = extras.getString("city");
            this.console = extras.getString("console");
            this.indexes = extras.getStringArrayList("indexes");
        }
        salesInfo = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        //checks indexes so we can connect the chosen console to other related data
        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = 0;
                String word;
                System.out.println(indexes);
                for(DataSnapshot LocationCheck:snapshot.child("Location").getChildren())
                {
                    String locationName = LocationCheck.getValue().toString().trim();
                    if(locationName.equals("תל אביב")|| locationName.equals("תל אביב -יפו"))
                    {
                        locationName = "תל אביב-יפו";
                    }
                    if(locationName.equals(city))
                    {
                        word = counter + "";
                        for (String index:indexes) {
                            if(index.contentEquals(word))
                            {
                                updatedIndexes.add(counter+ "");
                            }
                        }
                    }
                    counter++;
                }

                for(String index:updatedIndexes)
                {
                    String title = (String) snapshot.child("Title").child(index).getValue();
                    String description = (String) snapshot.child("Description").child(index).getValue();
                    String sellerName = "שם מוכר/ת: " + (String) snapshot.child("Seller Name").child(index).getValue();
                    if(sellerName.equals("שם מוכר/ת: null"))
                    {
                        sellerName = "שם מוכר/ת: אנונימי";
                    }
                    String price ="מחיר: " + "₪" + (String) snapshot.child("Price").child(index).getValue();
                    String sellerPhone = "טלפון: " + (String) snapshot.child("Seller Phone").child(index).getValue();
                    String city = "עיר: " + (String) snapshot.child("Location").child(index).getValue();
                    String url = (String) snapshot.child("Image URL").child(index).getValue();
                    SalesInfo salesInfoObject = new SalesInfo(title,description,sellerName,price,sellerPhone,city,url);
                    salesInfo.add(salesInfoObject);
                }
                adapter = new MyRecyclerViewAdapter(getApplicationContext(), salesInfo);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}