package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leedayeon.listdetail.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.leedayeon.listdetail.MainActivity.formatTimeString;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtJoin;
    private RadioGroup radioButtons;
    private RadioButton Item1;
    private RadioButton Item2;
//    private RadioButton Item3;

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvMsg;

    private String title;
    private String desc;
    private String obj_1;
    private String obj_2;
    private String obj1_num;
    private String obj2_num;
    private String answer;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private String games_id;
    private ArrayList<String> list;
    private long end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        radioButtons = (RadioGroup) findViewById(R.id.radioButtons);
        Item1 = (RadioButton)findViewById(R.id.Item1);
        Item2 = (RadioButton)findViewById(R.id.Item2);
        //Item3 = (RadioButton)findViewById(R.id.Item3);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvMsg = (TextView)findViewById(R.id.tvMsg);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Intent intent2 = getIntent();
        games_id = intent2.getStringExtra("games_id");

        list= new ArrayList<String>();

//        tvTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myRef.child("games").child(games_id).child("participant").addValueEventListener(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot friendSnapshot: dataSnapshot.getChildren()) {
//                            Log.e("XXY :", title +"" + friendSnapshot.getKey()+":"+friendSnapshot.child("answer").getValue());
//                            if(answer.equals(friendSnapshot.child("answer").getValue())){
//                                list.add(friendSnapshot.getKey());
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });

        myRef.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, String> map = (Map)dataSnapshot.getValue();
                title = map.get("title");
                tvTitle.setText(title);

                desc = map.get("description");
                tvDescription.setText(desc);

                // date = map.get("end_time");
                // tvMsg.setText(date);

                end_time = Long.parseLong(String.valueOf(map.get("end_time")));
                tvMsg.setText(formatTimeString(end_time));

//                answer=map.get("right_answer");

                obj_1 = map.get("obj_1");
                Item1.setText(obj_1);

                obj_2 = map.get("obj_2");
                Item2.setText(obj_2);

                Map<String, String> obj_map = (Map)dataSnapshot.child("num").getValue();
                try {
                    obj1_num = obj_map.get("obj_1");
                }catch (NullPointerException e){
                    obj1_num = String.valueOf(0);
                }
                try{
                    obj2_num = obj_map.get("obj_2");
                }catch (NullPointerException e){
                    obj2_num = String.valueOf(0);
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
                        Item1.setEnabled(false);
                        Item2.setEnabled(false);
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


            int id = radioButtons.getCheckedRadioButtonId();
            RadioButton rd = (RadioButton)findViewById(id);
            if(rd == null){
                Toast.makeText(this, "답안을 입력하세요", Toast.LENGTH_SHORT).show();
            }
            else {
                myRef.child("games").child(games_id).child("participant").child(user.getUid()).child("answer").setValue(rd.getText().toString());
                if (rd.getText().equals(obj_1))
                    myRef.child("games").child(games_id).child("num").child("obj_1").setValue(String.valueOf(Integer.parseInt(obj1_num) + 1));
                else
                    myRef.child("games").child(games_id).child("num").child("obj_2").setValue(String.valueOf(Integer.parseInt(obj2_num) + 1));


                mBtJoin.setText(getString(R.string.joining));
                mBtJoin.setEnabled(false);
                Item1.setEnabled(false);
                Item2.setEnabled(false);
                //Item3.setEnabled(false);

                Toast.makeText(this, "참여 성공하였습니다.", Toast.LENGTH_SHORT).show();
            }
//            Map<String, Object> childUpdates = new HashMap<>();
//            childUpdates.put("/games/" + games_id, result);
//
//            myRef.updateChildren(childUpdates);
//            myRef.child("games").child(games_id).child("owner").setValue("dongduk");



        }
    }
}
