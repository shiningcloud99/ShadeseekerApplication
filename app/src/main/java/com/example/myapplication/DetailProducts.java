package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;

public class DetailProducts extends AppCompatActivity {

    TextView detailBrand, detailProductName, detailShade, detailDesc;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);

        detailBrand = findViewById(R.id.detailBrand);
        detailProductName = findViewById(R.id.detailProductName);
        detailShade = findViewById(R.id.detailShade);
        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String productCode = bundle.getString("productCode");
            Product product = getProductByCode(productCode);
            if (product != null) {
                detailBrand.setText(product.getBrand());
                detailProductName.setText(product.getName());
                detailDesc.setText(product.getDescription());
                detailShade.setText(product.getShade());
                // For loading image using Picasso or any other library, use product.getImageUrl() instead
                detailImage.setImageResource(product.getImageResource());
            }
        }
    }

    private Product getProductByCode(String productCode) {
        // Implement your logic to retrieve product details based on the product code
        // Example: Here you can retrieve the product from a list or database
        return null; // Return the corresponding product object
    }
}
