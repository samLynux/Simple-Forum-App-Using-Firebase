package com.example.uas_mobile_3_00000025672_samuelputra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

public class inputThreadActivity extends AppCompatActivity {

    private Button addThreadBTN, back;
    private EditText title, desc;
    private DatabaseReference database;
    private static boolean duplicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_thread);

        //firebase
        database = FirebaseDatabase.getInstance().getReference();


        addThreadBTN = findViewById(R.id.btnThreadAdd);
        back = findViewById(R.id.btnBack);

        //input user
        title = findViewById(R.id.titleBTN);
        desc = findViewById(R.id.descBTN);

        addThreadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                duplicate = false;
                if(!title.getText().toString().isEmpty()) {
                    submitUserToFB(new Menu(title.getText().toString(), desc.getText().toString(), User.currentUsername, 0),title.getText().toString());
                }
                else {
                    Toast.makeText(inputThreadActivity.this, "Please Fill The Username", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(inputThreadActivity.this, CustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void submitUserToFB(final Menu thread, final String title){
        //cek nama user udh ada apa belum
        database.child("threads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(noteDataSnapshot.child("title").getValue(String.class), title)) {
                        duplicate = true;
                    }
                }
                if(!duplicate){
                    submit(thread);
                }else {
                    Toast.makeText(inputThreadActivity.this, "title has been used", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void submit(Menu thread){
        DatabaseReference comments = FirebaseDatabase.getInstance().getReference("comments");
        comments.child(thread.getTitle()).push().setValue(new comment("",""));
        database.child("threads").child(thread.getTitle()).setValue(thread).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(inputThreadActivity.this, "THREAD POSTED", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(inputThreadActivity.this, CustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
