package com.example.leedayeon.listdetail;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by LeeDaYeon on 2016-07-20.
 */
public class DetailActivity extends AppCompatActivity {
    Intent intent;
    TextView tvTitle;
    TextView tvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();
        Post myPost = (Post)intent.getSerializableExtra("detail");

        tvTitle = (TextView)findViewById(R.id.textView);
        tvDesc = (TextView)findViewById(R.id.textView2);

        tvTitle.setText(myPost.getTitle());
        tvDesc.setText(myPost.getDescription());


        ActionBar actionBar = getActionBar();
        getSupportActionBar().setTitle(myPost.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return  true;
    }
}
