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

import com.example.leedayeon.listdetail.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtJoin;
    private RadioGroup radioButtons;
    private RadioButton Item1;
    private RadioButton Item2;
//    private RadioButton Item3;

    private TextView tvTitle;
    private TextView tvDescription;

    private String title;
    private String desc;
    private String obj_1;
    private String obj_2;

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    private String games_id;

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

                // date = map.get("end_time");
                // tvMsg.setText(date);

                obj_1 = map.get("obj_1");
                Item1.setText(obj_1);

                obj_2 = map.get("obj_2");
                Item2.setText(obj_2);

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
            mBtJoin.setText(getString(R.string.joining));
            mBtJoin.setEnabled(false);
            Item1.setEnabled(false);
            Item2.setEnabled(false);
            //Item3.setEnabled(false);
        }
    }
}

