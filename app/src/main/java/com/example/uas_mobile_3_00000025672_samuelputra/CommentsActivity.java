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
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Objects;

public class CommentsActivity extends AppCompatActivity {
    Button back, post;
    TextView ThreadTitle, ThreadDesc, ThreadPoster;
    EditText postText;
    private String title;
    private DatabaseReference database;
    private ArrayList<comment> ListComment;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        title = sp.getString("thread","");
        ThreadTitle = findViewById(R.id.ThreadTitle);
        ThreadDesc = findViewById(R.id.ThreadDesc);
        ThreadPoster = findViewById(R.id.ThreadPoster);
        DatabaseReference threadDetails = FirebaseDatabase.getInstance().getReference("threads");
        threadDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    if(noteDataSnapshot.child("title").getValue(String.class).equals(title))
                    {
                        ThreadTitle.setText(noteDataSnapshot.child("title").getValue(String.class));
                        ThreadDesc.setText(noteDataSnapshot.child("deskripsi").getValue(String.class));
                        ThreadPoster.setText(noteDataSnapshot.child("poster").getValue(String.class));
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        back = findViewById(R.id.btnBack);
        post = findViewById(R.id.btnPost);
        postText = findViewById(R.id.PostText);

        rvView = (RecyclerView) findViewById(R.id.Comments);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);



        DatabaseReference comments = FirebaseDatabase.getInstance().getReference("comments");

        comments.child(title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListComment = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    //Menu menu = noteDataSnapshot.getValue(Menu.class);
                    comment newComment = new comment(noteDataSnapshot.child("comment").getValue(String.class), noteDataSnapshot.child("commenter").getValue(String.class));
                    ListComment.add(newComment);

                }


                adapter = new AdapterComments(ListComment, CommentsActivity.this);
                rvView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference comments = FirebaseDatabase.getInstance().getReference("comments");
                comments.child(title).push().setValue(new comment(postText.getText().toString(),User.currentUsername));
                postText.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CommentsActivity.this, CustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }


}
