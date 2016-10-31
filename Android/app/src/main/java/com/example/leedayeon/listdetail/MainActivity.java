package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.leedayeon.listdetail.R.id.fab;
import static com.example.leedayeon.listdetail.R.id.start;

public  class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private FirebaseRecyclerAdapter<NewQuiz, PostViewHolder> recycleAdapter;
    private DatabaseReference ref;


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView descView;
        public TextView dateView;
        public ImageView imageView;
        public ImageView imageSitu;

        public PostViewHolder(View v) {
            super(v);
            titleView = (TextView) itemView.findViewById(R.id.textViewTitle);
            descView = (TextView) itemView.findViewById(R.id.textViewDesc);
            dateView = (TextView) itemView.findViewById(R.id.textViewDate);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageSitu = (ImageView) itemView.findViewById(R.id.imageViewSitu);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        layoutManager = new LinearLayoutManager(this);

        ref = FirebaseDatabase.getInstance().getReference();
        recycleAdapter = new FirebaseRecyclerAdapter<NewQuiz, PostViewHolder>(
                NewQuiz.class,
                R.layout.item_message,
                PostViewHolder.class,
                ref.child("games")) {

            public String games_id;
//            @Override
//            public void onBindViewHolder(PostViewHolder holder, int position, List<Object> payloads) {
//                super.onBindViewHolder(holder, position, payloads);
//                final int pos = position;
//                holder.titleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Log.e("ee", recycleAdapter.getRef(pos).getKey());
//
//                    }
//                });
//            }

            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, NewQuiz post, final int position) {
                final int is_obj=post.getIs_obj();
                final String obj_1=post.getObj_1();
                final String obj_2=post.getObj_2();

                viewHolder.descView.setText(post.getDescription());
                viewHolder.dateView.setText(Long.toString(post.getEnd_time()));

                viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.ic_launcher));
                viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.sign));

                viewHolder.titleView.setText(post.getTitle());
                viewHolder.titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this, ,Toast.LENGTH_SHORT).show();
                        games_id = recycleAdapter.getRef(position).getKey();
                        Log.e("ee", recycleAdapter.getRef(position).getKey());

                        Intent intent2 = new Intent(getApplicationContext(),DetailActivity2.class);
                        intent2.putExtra("games_id", games_id);
//                        intent2.putExtra("desc",viewHolder.descView.getText());
//                        intent2.putExtra("title",viewHolder.titleView.getText());
//                        intent2.putExtra("is_obj",is_obj);
//                        intent2.putExtra("obj_1",obj_1);
//                        intent2.putExtra("obj_2",obj_2);
                        startActivity(intent2);

//                        Intent intent3= getIntent();
//                        String result=intent3.getStringExtra("situ");
//
//                        if(result.equals("1"))
//                            viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.sign));
//                        else
//                            viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.join));
                    }
                });
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);

        FloatingActionButton fab ;
        fab= (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NewQuizActivity.class);
                startActivity(intent);
            }
        });
    }
}
