package com.example.leedayeon.listdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.leedayeon.listdetail.R;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtJoin;
    RadioGroup radioButtons;
    RadioButton Item1;
    RadioButton Item2;
    RadioButton Item3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        radioButtons = (RadioGroup) findViewById(R.id.radioButtons);
        Item1 = (RadioButton)findViewById(R.id.Item1);
        Item2 = (RadioButton)findViewById(R.id.Item2);
        Item3 = (RadioButton)findViewById(R.id.Item3);

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
            Item3.setEnabled(false);
        }
    }
}

