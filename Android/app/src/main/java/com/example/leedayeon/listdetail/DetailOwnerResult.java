package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DetailOwnerResult extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private FirebaseAuth mAuth;
    private String games_id;
    FirebaseUser user;

    TextView rightAnswer;
    TextView obj_1;
    TextView obj_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_owner_result);

        rightAnswer = (TextView)findViewById(R.id.msg);
        obj_1 = (TextView)findViewById(R.id.obj_2);
        obj_2 = (TextView)findViewById(R.id.obj_2);

        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();

                rightAnswer.setText("내가 입력한 정답 : "+map.get("right_answer"));
                obj_1.setText("1번을 고른 사람 : "+dataSnapshot.child("num").child("obj_1"));
                obj_2.setText("2번을 고른 사람 : "+dataSnapshot.child("num").child("obj_2"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
