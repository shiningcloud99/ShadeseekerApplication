package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchshadeActivity extends AppCompatActivity {

    private String selectedSkinTone = "";
    private String selectedUndertone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchshade);

        Button searchshadeButton = findViewById(R.id.searchshadeButton);
        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);

        List<String> items1 = new ArrayList<>();
        items1.add("Warm");
        items1.add("Neutral");
        items1.add("Cold");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedSkinTone = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        List<String> items2 = new ArrayList<>();
        items2.add("01");
        items2.add("02");
        items2.add("03");
        items2.add("04");
        items2.add("05");
        items2.add("06");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedUndertone = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        searchshadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedSkinTone.isEmpty() && !selectedUndertone.isEmpty()) {
                    String productCode = generateProductCode(selectedSkinTone, selectedUndertone);
                    searchProductByCode(productCode);
                } else {
                    Toast.makeText(SearchshadeActivity.this, "Please select skin tone and undertone first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String generateProductCode(String skinTone, String undertone) {
        return skinTone.substring(0, 1).toUpperCase() + undertone;
    }

    private void searchProductByCode(String productCode) {
        Intent intent = new Intent(SearchshadeActivity.this, BrandsProduct.class);
        intent.putExtra("productCode", productCode);
        startActivity(intent);
    }
}
