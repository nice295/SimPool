package com.example.leedayeon.listdetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import io.paperdb.Paper;

public class NewQuizActivity2 extends AppCompatActivity {

    private String title;
    private String description;
    private long end_time;

    private int is_obj = 1; //객관식(obj)이면 1 주관식(subj)이면 0
    private String obj_1; //객관식 1번답지
    private String obj_2; //객관식 2번답지
    private String subj; //주관식 답지
    private String right_answer;
    private String owner;
    private String num;

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

        setTitle("내기 추가하기");

//        paper로 전의 액티비티에서 전달한 값 읽어오기
        title = Paper.book().read("title");
        description = Paper.book().read("description");
        end_time = Paper.book().read("end_time");


        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        radioObj = (RadioButton) findViewById(R.id.radioObj);
        radioSubj = (RadioButton) findViewById(R.id.radioSubj);


        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewQuiz nq = new NewQuiz();
                String owner = getUserId();

                if(is_obj == 1 && owner != null) {
                    obj_1 = ObjectFragment.obj1.getText().toString();
                    obj_2 = ObjectFragment.obj2.getText().toString();
                    nq = new NewQuiz(title, description, end_time, is_obj, obj_1, obj_2, owner, right_answer);
                } else if(is_obj == 0 && owner != null){
                    subj = SubjectFragment.subj.getText().toString();
                    nq = new NewQuiz(title, description, end_time, is_obj, subj, owner, right_answer);
                } else {
                    Toast.makeText(NewQuizActivity2.this, "입력값을 제대로 입력하세요", Toast.LENGTH_SHORT).show();
                }

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
                        is_obj = 1;
                        break;
                    case R.id.radioSubj:
                        fragment = new SubjectFragment();
                        is_obj = 0;
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            }
        });
    }
    public String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.e("user id is : ", user.getUid());
            return user.getUid();
        } else {
            // No user is signed in
            Log.e("user id is : ", user.getUid());
            return null;
        }
    }

}
