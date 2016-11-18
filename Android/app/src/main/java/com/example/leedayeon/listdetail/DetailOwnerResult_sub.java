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

public class DetailOwnerResult_sub extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private FirebaseAuth mAuth;
    private String games_id;
    FirebaseUser user;

     TextView rightanswer;
     TextView cntAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_owner_result_sub);

        rightanswer = (TextView)findViewById(R.id.rightanswer);
        cntAnswer = (TextView)findViewById(R.id.cntAnswer);

        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();

                rightanswer.setText("내가 입력한 정답 : "+map.get("right_answer"));
                cntAnswer.setText("정답을 고른 사람 : "+dataSnapshot.child("num").child("answer").getValue()+"명");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}