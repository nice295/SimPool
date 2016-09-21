package com.example.leedayeon.listdetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class NewQuizActivity2 extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioObj;
    RadioButton radioSubj;

    Button btnStart;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz2);

        setTitle("내기 추가하기!!!");

//        paper로 전의 액티비티에서 전달한 값 읽어오기
        final String title = Paper.book().read("title");
        final String description = Paper.book().read("description");
        final String end_time = Paper.book().read("end_time");
//        Toast.makeText(this, sample, Toast.LENGTH_SHORT).show();


        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        radioObj = (RadioButton) findViewById(R.id.radioObj);
        radioSubj = (RadioButton) findViewById(R.id.radioSubj);


        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewQuiz nq = new NewQuiz(title, description, end_time);

//                myRef.child("games").setValue(nq);
                myRef.child("games").push().setValue(nq);


                Intent intent = new Intent(NewQuizActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Fragment fragment = new ObjectFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, fragment);
        fragmentTransaction.commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            Fragment fragment;

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    default:
                    case R.id.radioObj:
                        fragment = new ObjectFragment();
                        break;
                    case R.id.radioSubj:
                        fragment = new SubjectFragment();
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            }
        });
    }
}
