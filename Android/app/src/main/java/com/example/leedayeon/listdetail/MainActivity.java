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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.leedayeon.listdetail.R.id.fab;
import static com.example.leedayeon.listdetail.R.id.start;
import static java.sql.Types.NULL;

public  class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private FirebaseRecyclerAdapter<NewQuiz, PostViewHolder> recycleAdapter;
    private DatabaseReference ref;

    private FirebaseAuth mAuth;
    FirebaseUser user;

    SimpleDateFormat date;

    boolean is_over;
    boolean is_end;
    String end;

    private int is_obj=1;

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

            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, NewQuiz post, final int position) {
                date = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

                String owner =post.getOwner();
               // Log.e("SSUID",user.getUid());
                //Log.e("SSOWNER",owner);

                viewHolder.descView.setText(post.getDescription());
                viewHolder.dateView.setText(date.format(post.getEnd_time()));

                viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.sign));
                viewHolder.titleView.setText(post.getTitle());

                if(user.getUid().equals(owner)) { //방을 만든 주인일때
                    viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.mipmap.crown));
                    viewHolder.titleView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            games_id = recycleAdapter.getRef(position).getKey();
                           // Log.e("ee", recycleAdapter.getRef(position).getKey());

                            ref.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, String> map = (Map)dataSnapshot.getValue();
                                    is_obj = Integer.parseInt(String.valueOf(map.get("is_obj")));
                                    if(is_obj == 1) {
                                        Intent intent2 = new Intent(getApplicationContext(), DetailActivity2.class);
                                        intent2.putExtra("games_id", games_id);
                                        startActivity(intent2);
                                    } else if(is_obj == 0){
                                        Intent intent2 = new Intent(getApplicationContext(), DetailSubjectActivity2.class);
                                        intent2.putExtra("games_id", games_id);
                                        startActivity(intent2);
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                }
                else { //방장이 아니라 참여자 신분
                    viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_launcher));
                    viewHolder.titleView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            games_id = recycleAdapter.getRef(position).getKey();
                            //Log.e("ee", recycleAdapter.getRef(position).getKey());

                            ref.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, String> map = (Map)dataSnapshot.getValue();
                                    is_obj = Integer.parseInt(String.valueOf(map.get("is_obj")));

                                    /**시간 마감인지 아닌지 구분하기위해 메소드 호출**/
                                    long time = Long.parseLong(String.valueOf(map.get("end_time")));
                                    is_end = is_end_time(time);

                                    if(is_obj == 1 && is_end == false) {
                                        Intent intent2 = new Intent(getApplicationContext(), DetailActivity.class);
                                        intent2.putExtra("games_id", games_id);
                                        startActivity(intent2);
                                    } else if(is_obj == 0 && is_end == false){
                                        Intent intent2 = new Intent(getApplicationContext(), DetailSubjectActivity.class);
                                        intent2.putExtra("games_id", games_id);
                                        startActivity(intent2);
                                    } else {
                                        Toast.makeText(MainActivity.this, "시간이 마감되었습니다", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

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

    /**시간이 마감되었는지 아닌지 비교하는 메소드**/
    public boolean is_end_time(long long_end) {
            Calendar cal_today = Calendar.getInstance();
            long today_time = cal_today.getTimeInMillis();

            if(today_time - long_end > 0) { //마감
                return true;
            } else { //마감되지않음
                return false;
            }
    }
}
