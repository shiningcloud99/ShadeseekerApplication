package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivivity8();
            }
        });

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivivity9();
            }
        });

        Button favoriteButton = findViewById(R.id.favoriteButton);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivivity10();
            }
        });

        Button shadeButton = findViewById(R.id.shadeButton);
        shadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk menuju ke MainActivity5 di sini
                goToMainActivity5();
            }
        });

        Button brandButton = findViewById(R.id.brandButton);
        brandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity7();
            }
        });

        ImageView imageLuxcrime = findViewById(R.id.imageLuxcrime);
        imageLuxcrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailProducts();
            }
        });
    }
    private void goToMainActivivity8() {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void goToMainActivivity9() {
        Intent intent = new Intent(HomeActivity.this, SearchFinActivity.class);
        startActivity(intent);
    }

    private void goToMainActivivity10() {
        Intent intent = new Intent(HomeActivity.this, FavoriteActivity.class);
        startActivity(intent);
    }

    // Metode untuk menuju ke MainActivity5
    private void goToMainActivity5() {
        Intent intent = new Intent(HomeActivity.this, SearchshadeActivity.class);
        startActivity(intent);
    }

    private void goToMainActivity7() {
        Intent intent = new Intent(HomeActivity.this, SearchbrandActivity.class);
        startActivity(intent);
    }

    private void goToDetailProducts() {
        Intent intent = new Intent(HomeActivity.this, DetailProducts.class);
        startActivity(intent);
    }

}

