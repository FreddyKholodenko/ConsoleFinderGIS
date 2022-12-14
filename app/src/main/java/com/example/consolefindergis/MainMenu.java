package com.example.consolefindergis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.lang.*;

public class MainMenu extends AppCompatActivity {

    final private DatabaseReference consoleReference = FirebaseDatabase.getInstance("https://console-finder-gis-f12bd-default-rtdb.firebaseio.com/").getReference("Console");
    final private DatabaseReference locationReference = FirebaseDatabase.getInstance("https://console-finder-gis-f12bd-default-rtdb.firebaseio.com/").getReference("Location");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button button = findViewById(R.id.search_btn_map);

// Values of string array from the database
        Spinner dropdown = findViewById(R.id.dropdown);
        String[] items = new String[]{"PS VITA", "PSP", "Wii", "XBox", "XBox 360", "XBox ONE", "נינטנדו", "פלייסטיישן", "פלייסטיישן 2", "פלייסטיישן 3", "פלייסטיישן 4" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        ArrayList<String> indexes = new ArrayList();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dropdownChoice = dropdown.getSelectedItem().toString();
                //checks indexes so we can connect the chosen console to other related data
                consoleReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int counter = 0;
                        for(DataSnapshot console:snapshot.getChildren())
                        {
                            String consoleName = console.getValue().toString().trim();
                            if(consoleName.equals(dropdownChoice))
                            {
                                indexes.add(counter+ "");
                            }
                            counter++;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                //setting HashSet to draw the cities
                HashSet<String> locationsToDraw = new HashSet<>();
                locationReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int counter = 0;
                        String word;
                        for(DataSnapshot location:snapshot.getChildren())
                        {
                            word = counter + "";
                            for (String index:indexes) {
                                if(index.contentEquals(word))
                                {
                                    String name = location.getValue(String.class);
                                    name.trim();
                                    if(name.equals("תל אביב")|| name.equals("תל אביב -יפו"))
                                    {
                                        name = "תל אביב-יפו";
                                    }
                                    locationsToDraw.add(name);
                                }
                            }
                            counter++;
                        }
                        Intent intent = new Intent(MainMenu.this,MapsActivity.class);
                        ArrayList<String> conversionToList = new ArrayList<>();
                        ArrayList<String> conversionIndexesKeyToList = new ArrayList<>();
                        conversionIndexesKeyToList.addAll(indexes);
                        conversionToList.addAll(locationsToDraw);
                        intent.putExtra("city",conversionToList.toString());
                        intent.putExtra("console",dropdown.getSelectedItem().toString());
                        intent.putExtra("indexes",indexes);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}