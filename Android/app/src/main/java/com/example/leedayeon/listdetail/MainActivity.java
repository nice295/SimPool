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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.leedayeon.listdetail.R.id.fab;
import static com.example.leedayeon.listdetail.R.id.start;

public  class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private FirebaseRecyclerAdapter<NewQuiz, PostViewHolder> recycleAdapter;
    private DatabaseReference ref;

    private FirebaseAuth mAuth;
    FirebaseUser user;

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

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

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
                SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

                String owner =post.getOwner();
                Log.e("SSUID",user.getUid());
                Log.e("SSOWNER",owner);

                viewHolder.descView.setText(post.getDescription());
                viewHolder.dateView.setText(date.format(post.getEnd_time()));

                viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.sign));
                viewHolder.titleView.setText(post.getTitle());

                if(user.getUid().equals(owner)) {
                    viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.mipmap.crown));
                    viewHolder.titleView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            games_id = recycleAdapter.getRef(position).getKey();
                            Log.e("ee", recycleAdapter.getRef(position).getKey());

                            Intent intent2 = new Intent(getApplicationContext(), DetailActivity2.class);
                            intent2.putExtra("games_id", games_id);
                            startActivity(intent2);
                        }
                    });
                }
                else {
                    viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_launcher));
                    viewHolder.titleView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            games_id = recycleAdapter.getRef(position).getKey();
                            Log.e("ee", recycleAdapter.getRef(position).getKey());

                            Intent intent2 = new Intent(getApplicationContext(), DetailActivity.class);
                            intent2.putExtra("games_id", games_id);
                            startActivity(intent2);
                        }
                    });
                }
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
