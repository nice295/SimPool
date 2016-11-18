package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DetailResult extends AppCompatActivity {

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private FirebaseAuth mAuth;
    private String games_id;
    FirebaseUser user;

    TextView checkResult;
    TextView rightResult;
    private ImageView mIvWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_result);

        checkResult = (TextView) findViewById(R.id.checkResult);
        rightResult = (TextView) findViewById(R.id.rightResult);
        mIvWin = (ImageView) findViewById(R.id.ivWin);

        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();

                Log.e("right answer : ", "" + map.get("right_answer").toString());
                Log.e("user answer : ", "" + dataSnapshot.child("participant").child(user.getUid()).child("answer").getValue());
                rightResult.setText("정답은 " + map.get("right_answer") + "입니다.");
                if (map.get("right_answer").equals(dataSnapshot.child("participant").child(user.getUid()).child("answer").getValue())) {
                    checkResult.setText("정답을 맞추셨습니다!");
                    mIvWin.setVisibility(View.VISIBLE);
                } else if (dataSnapshot.child("participant").child(user.getUid()).child("answer").getValue() == null) {
                    checkResult.setText("이 게임에 참여하지 않으셨습니다.");
                } else {
                    checkResult.setText("아깝게 틀리셨네요.");
                    mIvWin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
