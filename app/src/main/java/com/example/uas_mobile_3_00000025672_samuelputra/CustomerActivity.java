package com.example.uas_mobile_3_00000025672_samuelputra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerActivity extends AppCompatActivity {
    Button btnLogoutC, btnNewThread, btnProfile;
    TextView TVWelcomeC;
    private DatabaseReference database;
    private ArrayList<Menu> ListMenu;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        TVWelcomeC = findViewById(R.id.TVWelcomeC);
        TVWelcomeC.setText("Welcome, "+ User.currentUsername);

        btnLogoutC = findViewById(R.id.btnLogoutC);
        btnNewThread= findViewById(R.id.btnAddThread);
        btnProfile= findViewById(R.id.btnProfile);

        rvView = (RecyclerView) findViewById(R.id.RVMenu);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("threads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListMenu = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    //Menu menu = noteDataSnapshot.getValue(Menu.class);
                    Menu menu = new Menu(noteDataSnapshot.child("title").getValue(String.class),noteDataSnapshot.child("deskripsi").getValue(String.class),
                            noteDataSnapshot.child("poster").getValue(String.class),noteDataSnapshot.child("comments").getValue(int.class));
                    ListMenu.add(menu);

                }


                adapter = new AdapterMenuRecyclerView(ListMenu, CustomerActivity.this);
                rvView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnLogoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("username", "");
                ed.putString("pass", "");
                ed.apply();

                Intent intent = new Intent(CustomerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnNewThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomerActivity.this, inputThreadActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomerActivity.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
