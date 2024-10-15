package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText username,
            password;
    private Button login;

    private TextView signupText;
    Switch active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password_toggle);
        login = findViewById(R.id.loginButton);
        active = findViewById(R.id.active);
        signupText = findViewById(R.id.signupText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("login").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String input1 = username.getText().toString();
                        String input2 = password.getText().toString();

                        if (dataSnapshot.child(input1).exists()) {
                            if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                                if (active.isChecked()) {
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) {
                                        preferences.setDataLogin(LoginActivity.this, true);
                                        preferences.setDataAs(LoginActivity.this, "admin");
                                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")){
                                        preferences.setDataLogin(LoginActivity.this, true);
                                        preferences.setDataAs(LoginActivity.this, "user");
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    }
                                } else {
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) {
                                        preferences.setDataLogin(LoginActivity.this, false);
                                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));

                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")){
                                        preferences.setDataLogin(LoginActivity.this, false);
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    }
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Kata sandi salah", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Data belum terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("admin")) {
                startActivity(new Intent(this, AdminActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        }
    }
}