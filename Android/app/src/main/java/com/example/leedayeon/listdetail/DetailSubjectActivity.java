package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leedayeon.listdetail.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailSubjectActivity extends AppCompatActivity  {

    private Button mBtJoin;

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvQuiz;

    private EditText answer;

    private String title;
    private String desc;
    private String quiz;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private String games_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subject);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvQuiz = (TextView)findViewById(R.id.tvQuiz);
        answer = (EditText)findViewById(R.id.answer);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");

        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();
                title = map.get("title");
                tvTitle.setText(title);

                desc = map.get("description");
                tvDescription.setText(desc);

                quiz = map.get("subj");
                tvQuiz.setText(quiz);

                // date = map.get("end_time");
                // tvMsg.setText(date);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("games").child(games_id).child("participant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("Count " ,""+dataSnapshot);
//                Map<String, String> map = (Map)dataSnapshot.getValue();

                for (DataSnapshot friendSnapshot: dataSnapshot.getChildren()) {
                    Log.e("uid :", title +"" + friendSnapshot.getKey());
                    if(user.getUid().equals(friendSnapshot.getKey())){
                        mBtJoin.setText(getString(R.string.joining));
                        mBtJoin.setEnabled(false);

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mBtJoin = (Button) findViewById(R.id.btJoin);
        mBtJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == mBtJoin) {

                    EditText answer = (EditText)findViewById(R.id.answer);

                    myRef.child("games").child(games_id).child("participant").child(user.getUid()).child("answer").setValue(answer.getText().toString());
                    mBtJoin.setText(getString(R.string.joining));
                    mBtJoin.setEnabled(false);
                    answer.setEnabled(false);

                }
            }
        });
       // 여기 오류생기는 건 좀 찾아봐야할것 같아..;; 고대로 복붙했는데 오류나서
    }


//    //@Override
//    public void onClick(View view) {
//        if (view == mBtJoin) {
//
//            EditText answer = (EditText)findViewById(R.id.answer);
//
//            myRef.child("games").child(games_id).child("participant").child(user.getUid()).child("answer").setValue(answer.getText().toString());
//            mBtJoin.setText(getString(R.string.joining));
//            mBtJoin.setEnabled(false);
//            answer.setEnabled(false);
//
//        }
//    }
}
