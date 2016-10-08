package com.example.leedayeon.listdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity2 extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvMsg;

    private FirebaseListAdapter<NewQuiz> fListAdapter;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvDescription = (TextView)findViewById(R.id.tvDescription);

        myRef.child("games");
        Intent intent2 = getIntent();
        tvTitle.setText(intent2.getStringExtra("title"));
        tvDescription.setText(intent2.getStringExtra("desc"));



    }



    public void onClick(View v) {
        final LinearLayout linear = (LinearLayout)View.inflate(this, R.layout.custom_detail, null);


        new AlertDialog.Builder(this).setTitle("정답을 입력해 주세요.")
        .setView(linear).setPositiveButton("결과 확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // Toast.makeText(DetailActivity2.this, "입력되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivity2.this, DetailActivity3.class);
                startActivity(intent);


            }
        })
                .setNegativeButton("취소",null).show();

    }
}
