package com.simpool.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView obj_1;
    TextView obj_2;
    TextView one;
    TextView two;
    private ImageView mIvWin;
    private boolean answer_nothing;

    String str_obj_1;
    String str_obj_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_result);

        checkResult = (TextView) findViewById(R.id.checkResult);
        rightResult = (TextView) findViewById(R.id.rightResult);
//        mIvWin = (ImageView) findViewById(R.id.ivWin);
        obj_1 = (TextView)findViewById(R.id.obj_1);
        obj_2 = (TextView)findViewById(R.id.obj_2);
        one = (TextView)findViewById(R.id.one);
        two = (TextView)findViewById(R.id.two);

        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();



        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();

//                Log.e("right answer : ", "" + map.get("right_answer").toString());
//                Log.e("user answer : ", "" + dataSnapshot.child("participant").child(user.getUid()).child("answer").getValue());

                str_obj_1 = map.get("obj_1");
                str_obj_2 = map.get("obj_2");

                if(map.get("right_answer") == null) {
                    rightResult.setText(" ");
                    checkResult.setText("방장이 시간내에 정답을 입력하지 않았습니다.");
                    answer_nothing = true;

                } else if(map.get("right_answer").toString().equals(dataSnapshot.child("participant").child(user.getUid()).child("answer").getValue())) {
                    rightResult.setText("정답은 " + map.get("right_answer") + "입니다.");
                    checkResult.setText("정답을 맞추셨습니다!");
                } else if(dataSnapshot.child("participant").child(user.getUid()).child("answer").getValue() == null){
                    rightResult.setText("정답은 " + map.get("right_answer") + "입니다.");
                    checkResult.setText("이 게임에 참여하지 않으셨습니다.");
                } else {
                    rightResult.setText("정답은 " + map.get("right_answer") + "입니다.");
                    checkResult.setText("아깝게 틀리셨네요.");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            myRef.child("games").child(games_id).child("num").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = (Map) dataSnapshot.getValue();

                    if(map == null) {
                        Toast.makeText(DetailResult.this, "아무도 참여하지 않았습니다", Toast.LENGTH_SHORT).show();
                        obj_1.setVisibility(View.INVISIBLE);
                        obj_2.setVisibility(View.INVISIBLE);
                    } else {
                        if( answer_nothing == true) {
                            obj_1.setVisibility(View.INVISIBLE);
                            obj_2.setVisibility(View.INVISIBLE);
                        } else {
                            if (map.get("obj_1") == null) {
                                Log.e("DETAIL RESULT : NULL" , "");
                                obj_1.setWidth(10);
                                one.setText("1번 : 0명");
                            } else {
                                one.setText( str_obj_1 + " : " + map.get("obj_1")+"명");
                                int i = Integer.parseInt(map.get("obj_1").toString());
                                obj_1.setWidth(i * 100);
                            }
                            if(map.get("obj_2") == null) {
                                two.setText("2번 : 0명");
                                obj_2.setWidth(10);
                            } else {
                                two.setText(str_obj_2 + " : " + map.get("obj_2")+"명");
                                int j = Integer.parseInt(map.get("obj_2").toString());
                                obj_2.setWidth(j * 100);
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
//EB:1B:0A:D3:4B:39:BA:0A:C4:26:4D:A0:2D:37:C3:E4:05:59:FC:95