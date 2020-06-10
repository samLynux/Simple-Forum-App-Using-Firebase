package com.example.uas_mobile_3_00000025672_samuelputra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private TextView login;
    private Button Regis;
    private EditText username, pass, CoPass, nim, name, email;
    private DatabaseReference database;
    private static boolean duplicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //firebase
        database = FirebaseDatabase.getInstance().getReference();
        //button
        login = findViewById(R.id.TVLogin);
        Regis = findViewById(R.id.btnRegis);
        //input user
        username = findViewById(R.id.ETUserRegis);
        nim = findViewById(R.id.ETNIMRegis);
        name = findViewById(R.id.ETnamaRegis);
        email = findViewById(R.id.ETEmailRegis);
        pass = findViewById(R.id.ETPassRegis);
        CoPass = findViewById(R.id.ETCoPassRegis);

        Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duplicate = false;
                //cek username kosong atau tidak
                if(!username.getText().toString().isEmpty()) {//cek password kosong atau tidak
                    if(!pass.getText().toString().isEmpty()) {//cek confirm password kosong atau tidak
                        if(!CoPass.getText().toString().isEmpty()){//cek pass ama comfirm pass match atau tidak
                            if(pass.getText().toString().equals(CoPass.getText().toString())){
                                submitUserToFB(new User(username.getText().toString(),pass.getText().toString(),nim.getText().toString(),name.getText().toString(),email.getText().toString()),username.getText().toString());
                            }// jika pass tidak match
                            else {
                                Toast.makeText(RegisterActivity.this, "Password don't match", Toast.LENGTH_LONG).show();
                            }
                        }//jika confirm pass kosong
                        else {
                            Toast.makeText(RegisterActivity.this, "Please Fill The Confirm Password", Toast.LENGTH_LONG).show();
                        }
                    }//jika pass kosong
                    else {
                        Toast.makeText(RegisterActivity.this, "Please Fill The Password", Toast.LENGTH_LONG).show();
                    }
                }//jika username kosong
                else {
                    Toast.makeText(RegisterActivity.this, "Please Fill The Username", Toast.LENGTH_LONG).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void submitUserToFB(final User users, final String userN){
        //cek nama user udh ada apa belum
        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(noteDataSnapshot.child("username").getValue(String.class), userN)) {
                        duplicate = true;
                    }
                }
                if(!duplicate){
                    submit(users);
                }else {
                    Toast.makeText(RegisterActivity.this, "username has been used", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void submit(User user){
        database.child("users").push().setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("username", username.getText().toString());
                ed.putString("pass", pass.getText().toString());
                ed.apply();

                User.currentUsername = username.getText().toString();
                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, CustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
