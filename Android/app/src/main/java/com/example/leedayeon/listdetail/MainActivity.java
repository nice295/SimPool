package com.example.leedayeon.listdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    TextView etTitle,etDesc,etDate;
    Recycler_item recycler_item;
    int id;
    List<Recycler_item> items=new ArrayList<Recycler_item>();
    RecyclerView recyclerView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = (TextView) findViewById(R.id.textViewTitle);
        etDesc = (TextView) findViewById(R.id.textViewDesc);
        etDate = (TextView)findViewById(R.id.textViewDate);

//      DISPLAY
        mContext=getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);

        items.add(new Recycler_item(R.mipmap.ic_launcher, "first", "첫번째 게시물","~8/15,2015",R.mipmap.join));
        items.add(new Recycler_item(R.mipmap.ic_launcher, "second", "두 번째 게시물","~8/21,2016",R.mipmap.join));
        items.add(new Recycler_item(R.mipmap.crown, "user1", "내 게시물1","~9/12,2016",R.mipmap.sign));
        items.add(new Recycler_item(R.mipmap.ic_launcher, "third", "세 번째 게시물","~9/18,2016",R.mipmap.join));
        items.add(new Recycler_item(R.mipmap.crown, "user2", "내 게시물2","~9/20,2016",R.mipmap.sign));

        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewQuizActivity.class);
                startActivity(intent);
            }
        });
        Adapter = new RecyclerAdapter(getApplicationContext(),items,R.layout.item_message);
        recyclerView.setAdapter(Adapter);
    }
}