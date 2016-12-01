package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.leedayeon.listdetail.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.leedayeon.listdetail.MainActivity.formatTimeString;

public class DetailSubjectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtJoin;

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvQuiz;
    private TextView tvMsg;
    private ImageView ivProfile;

    private EditText answer;

    private String title;
    private String desc;
    private String quiz;
    private long end_time;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private String games_id;
    private String answer_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subject);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvQuiz = (TextView)findViewById(R.id.tvQuiz);
        tvMsg = (TextView)findViewById(R.id.tvMsg);
        answer = (EditText)findViewById(R.id.answer);
        ivProfile = (ImageView)findViewById(R.id.ivProfile);

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

                Glide.with(DetailSubjectActivity.this).load(map.get("profile")).into(ivProfile);

                desc = map.get("description");
                tvDescription.setText(desc);

                quiz = map.get("subj");
                tvQuiz.setText("힌트 : "+quiz);

                // date = map.get("end_time");
                // tvMsg.setText(date);

                end_time = Long.parseLong(String.valueOf(map.get("end_time")));
                tvMsg.setText(formatTimeString(end_time));

                Map<String, String> sbj_map = (Map)dataSnapshot.child("num").getValue();
                try {
                    answer_num = sbj_map.get("subj");
                }catch (NullPointerException e){
                    answer_num = String.valueOf(0);
                }

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
                        answer.setEnabled(false);
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mBtJoin = (Button) findViewById(R.id.btJoin);
        mBtJoin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == mBtJoin) {

                myRef.child("games").child(games_id).child("participant").child(user.getUid()).child("answer").setValue(answer.getText().toString());
                myRef.child("games").child(games_id).child("num").child("answer").setValue(String.valueOf(Integer.parseInt(answer_num) + 1));
                mBtJoin.setText(getString(R.string.joining));
                mBtJoin.setEnabled(false);
            Toast.makeText(this, "참여 성공하였습니다.", Toast.LENGTH_SHORT).show();

            mBtJoin.setEnabled(false);

//            Map<String, Object> childUpdates = new HashMap<>();
//            childUpdates.put("/games/" + games_id, result);
//
//            myRef.updateChildren(childUpdates);
//            myRef.child("games").child(games_id).child("owner").setValue("dongduk");


        }
    }
}
