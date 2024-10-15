package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapter.ItemAdapterJava;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SearchbrandActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Item> brandList;
    ItemAdapterJava itemAdapterJava;
    SearchView searchView;
    Toolbar toolbar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbrand);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchView = findViewById(R.id.searchBrand);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        brandList = new ArrayList<>();
        itemAdapterJava = new ItemAdapterJava(this, brandList, new ItemAdapterJava.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                Intent intent = new Intent(SearchbrandActivity.this, BrandsProduct.class);
                intent.putExtra("brandName", item.getHeading());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(itemAdapterJava);

        databaseReference = FirebaseDatabase.getInstance().getReference("brands");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                brandList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Brand brand = snapshot.getValue(Brand.class);
                    if (brand != null) {
                        brandList.add(new Item(brand.getName(), brand.getImageUrl()));
                    }
                }
                itemAdapterJava.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchbrandActivity.this, "Failed to read data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
