package com.example.leedayeon.listdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leedayeon.listdetail.R;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBtJoin = (Button) findViewById(R.id.btJoin);
        mBtJoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtJoin) {
            mBtJoin.setText(getString(R.string.joining));
        }
    }
}

