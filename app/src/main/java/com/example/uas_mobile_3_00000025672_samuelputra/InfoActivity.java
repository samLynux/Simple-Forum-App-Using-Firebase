package com.example.uas_mobile_3_00000025672_samuelputra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {
    Button back, logout;
    TextView UserId, Name, NIM, Email;
    EditText postText;
    private DatabaseReference database;
    private String userId, name, nim, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);



        UserId = findViewById(R.id.UserID);
        NIM = findViewById(R.id.NIM);
        Email = findViewById(R.id.Email);
        Name = findViewById(R.id.Name);

        DatabaseReference userDetails = FirebaseDatabase.getInstance().getReference("users");
        userDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    if(noteDataSnapshot.child("username").getValue(String.class).equals(User.currentUsername))
                    {

                        UserId.setText(noteDataSnapshot.child("username").getValue(String.class));
                        NIM.setText(noteDataSnapshot.child("nim").getValue(String.class));
                        Email.setText(noteDataSnapshot.child("email").getValue(String.class));
                        Name.setText(noteDataSnapshot.child("name").getValue(String.class));
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        back = findViewById(R.id.btnBack);
        logout = findViewById(R.id.btnLogoutC);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InfoActivity.this, CustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("username", "");
                ed.putString("pass", "");
                ed.apply();

                Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}