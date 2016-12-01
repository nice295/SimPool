package com.example.leedayeon.listdetail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.leedayeon.listdetail.MainActivity.formatTimeString;
import static com.example.leedayeon.listdetail.R.color.room_timeover;

public class DetailActivity2 extends AppCompatActivity {


    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvMsg;
    private TextView Item1;
    private TextView Item2;
    private Button btEnter;
    private ImageView ivProfile;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private String games_id;

    private String title;
    private String desc;
    private long end_time;
    private String obj_1;
    private String obj_2;

    private int temp;

    NewQuiz post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvMsg = (TextView)findViewById(R.id.tvMsg);
        Item1 = (TextView)findViewById(R.id.Item1);
        Item2 = (TextView)findViewById(R.id.Item2);
        btEnter = (Button)findViewById(R.id.btEnter);
        ivProfile = (ImageView)findViewById(R.id.ivProfile);

        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");


      //  SimpleDateFormat dt = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
       // tvMsg.setText(dt.format(date));
  //      SimpleDateFormat dt = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        //tvMsg.setText(dt.format(date));

   //     Glide.with(DetailActivity2.this).load(post.getProfile()).into(ivProfile);

        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();

                title = map.get("title");
                Log.e("hhahahah", title);
                tvTitle.setText(title);

                //ivProfile.setImageDrawable(dataSnapshot.getValue());
                Glide.with(DetailActivity2.this).load(map.get("profile")).into(ivProfile);

               desc = map.get("description");
               tvDescription.setText(desc);

                end_time = Long.parseLong(String.valueOf(map.get("end_time")));
                tvMsg.setText(formatTimeString(end_time));

               //dt = map.get("end_time");


                obj_1 = map.get("obj_1");
                Item1.setText(obj_1);

                obj_2 = map.get("obj_2");
                Item2.setText(obj_2);

                if(map.get("right_answer") != null){
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
                final String str[] = {obj_1,obj_2};
                new AlertDialog.Builder(DetailActivity2.this)
                        .setTitle("정답을 입력해 주세요.")
                        .setPositiveButton("입력", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                myRef.child("games").child(games_id).child("right_answer").setValue(str[temp]);
                                Toast.makeText(getApplicationContext(), "정답이 입력되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                temp = which;
                            }
                        })
                        .show();

                        //btEnter.setEnabled(false);

        }});


    }


//
//    public void onClick(View v) {
//        final String str[] = {obj_1,obj_2};
//        new AlertDialog.Builder(this)
//                .setTitle("정답을 입력해 주세요.")
//                .setPositiveButton("입력", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                myRef.child("games").child(games_id).child("right_answer").setValue(str[temp]);
//                                Toast.makeText(getApplicationContext(), str[temp] + "선택", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                .setNegativeButton("취소", null)
//                .setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        temp = which;
//                                    }
//                                })
//                .show();
//
//    }
}
