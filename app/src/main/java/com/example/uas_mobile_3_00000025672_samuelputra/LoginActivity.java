package com.example.uas_mobile_3_00000025672_samuelputra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameET , passET;
    private Button lgn;
    private TextView RegisterTV;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private Boolean loginIn;
    private int role;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        RegisterTV = findViewById(R.id.registerTV);
        lgn = findViewById(R.id.lgnBtn);

        // Initialize Shared Preferences and Auto Login
        sp = getSharedPreferences("Login", MODE_PRIVATE);

        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIn = false;
                usernameET = findViewById(R.id.userET);
                passET = findViewById(R.id.passET);

                String username = usernameET.getText().toString();
                String pass = passET.getText().toString();

                userLogin(username, pass);
            }
        });

        RegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void userLogin(final String username, final String pass) {
        if (!username.isEmpty()) {
            if (!pass.isEmpty()) {
                database.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            if ((noteDataSnapshot.child("username").getValue(String.class).equals(username)) &&
                                    (noteDataSnapshot.child("pass").getValue(String.class).equals(pass))) {
                                //^^cek repeat username dan password dari firebase
                                loginIn = true;

                                SharedPreferences.Editor ed = sp.edit();
                                ed.putString("username", username);
                                ed.putString("pass", pass);
                                ed.apply();

                                User.currentUsername = username;
                            }

                        }
                        if (loginIn) {

                            Toast.makeText(LoginActivity.this, "Logged in as " + username, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Username and Password Don't Match", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                    }
                });


            } else {
                Toast.makeText(LoginActivity.this, "Please Fill The Password", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Please Fill The Username", Toast.LENGTH_LONG).show();
        }
    }
}
