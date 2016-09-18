package com.example.leedayeon.listdetail;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.paperdb.Paper;

public class NewQuizActivity2 extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioObj;
    RadioButton radioSubj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz2);

        setTitle("내기 추가하기!!!");

        String sample = Paper.book().read("title");
        Toast.makeText(this, sample, Toast.LENGTH_SHORT).show();


        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        radioObj = (RadioButton) findViewById(R.id.radioObj);
        radioSubj = (RadioButton) findViewById(R.id.radioSubj);

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
