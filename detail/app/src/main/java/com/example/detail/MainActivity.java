package com.example.detail;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    FragmentManager fm;
    FrameLayout fragmentBorC;
    Fragment fragmentA;
    Fragment fragmentB;
    Fragment fragmentC;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentBorC = (FrameLayout)findViewById(R.id.fragmentBorC);

        fm  = (FragmentManager)getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentBorC, fragmentB);
        fragmentTransaction.commit();

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.button:
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentBorC, fragmentC, null);
                    fragmentTransaction.commit();


                    break;
            }
        }
        });


    }

    }

