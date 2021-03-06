package com.simpool.leedayeon.listdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Map;

import static com.simpool.leedayeon.listdetail.MainActivity.formatTimeString;
import static com.simpool.leedayeon.listdetail.R.color.room_timeover;

public class DetailSubjectActivity2 extends AppCompatActivity  {
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvMsg;
    private TextView tvQuiz;
    private Button btEnter;
    private ImageView ivProfile;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private String games_id;

    private String title;
    private String desc;
    private String quiz;


    private int temp;

    private long end_time;



    //final Date date = new Date(post.getEnd_time());



    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subject2);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvMsg = (TextView)findViewById(R.id.tvMsg);
        tvQuiz = (TextView)findViewById(R.id.tvQuiz);
        btEnter = (Button)findViewById(R.id.btEnter);
        ivProfile = (ImageView)findViewById(R.id.ivProfile);


        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");


        //  SimpleDateFormat dt = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        // tvMsg.setText(dt.format(date));

        SimpleDateFormat dt = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        //tvMsg.setText(dt.format(date));

        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();
                title = map.get("title");
                Log.e("hhahahah", title);
                tvTitle.setText(title);

                Glide.with(DetailSubjectActivity2.this).load(map.get("profile")).into(ivProfile);

                desc = map.get("description");
                tvDescription.setText(desc);

                end_time = Long.parseLong(String.valueOf(map.get("end_time")));
                tvMsg.setText(formatTimeString(end_time));

                //dt = map.get("end_time");
                quiz = map.get("subj");
                tvQuiz.setText(quiz);

                if(map.get("right_answer")!=null){
                    btEnter.setEnabled(false);
                    btEnter.getResources().getColor(room_timeover);
                    btEnter.setText("입력 완료");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // tvTitle.setText(title);
        //tvDescription.setText(intent2.getStringExtra("desc"));

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final EditText answer = new EditText(DetailSubjectActivity2.this);
                new AlertDialog.Builder(DetailSubjectActivity2.this)
                        .setTitle("정답을 입력해 주세요.")
                        .setView(answer)
                        .setPositiveButton("입력", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String value = answer.getText().toString();
                                myRef.child("games").child(games_id).child("right_answer").setValue(value);
                                Toast.makeText(getApplicationContext(), "정답이 입력되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });


    }


}
