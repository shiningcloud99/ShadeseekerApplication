package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText signupUsername, signupEmail, newpassword, confirmpassword;
    TextView loginText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUsername = findViewById(R.id.signupUsername);
        signupEmail = findViewById(R.id.signupEmail);
        newpassword = findViewById(R.id.newpassword);
        confirmpassword = findViewById(R.id.confirmpassword);
        signupButton = findViewById(R.id.signupButton);
        loginText = findViewById(R.id.SignUpText);

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("login");

                String username = signupUsername.getText().toString();
                String email = signupEmail.getText().toString();
                String password = newpassword.getText().toString();
                String confirm = confirmpassword.getText().toString();

                if (password.equals(confirm)) {
                    String userType = "user";

                    HelperClass helperClass = new HelperClass(username, email, password, confirm);

                    reference.child(username).child("as").setValue(userType);
                    reference.child(username).child("password").setValue(password);
                    reference.child(username).child("email").setValue(email);

                    Toast.makeText(SignupActivity.this, "Signup Seccess!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignupActivity.this, "Password do not match!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
