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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import io.paperdb.Paper;

public  class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private FirebaseRecyclerAdapter<NewQuiz, PostViewHolder> recycleAdapter;
    private DatabaseReference ref;

    private FirebaseAuth mAuth;
    FirebaseUser user;

    SimpleDateFormat date;

    boolean is_end;
    private int is_obj=1;
    boolean is_join;

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        View mView;
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

            mView = v;
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
                long long_end_time = post.getEnd_time();

                /** 내가 참여한 방일때 이미지를 참여중으로 바꿈 -> 동작안함
                if(is_joining(user.getUid(), recycleAdapter.getRef(position).getKey()) == true) {
                    viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.join));
                }
                **/

                viewHolder.descView.setText(post.getDescription());
                viewHolder.dateView.setText(date.format(post.getEnd_time()));

                viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.sign));
                viewHolder.titleView.setText(post.getTitle());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "You clicked on "+position);
                    }
                });

                if(user.getUid().equals(owner)) { //방을 만든 주인일때
                    viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.mipmap.crown));

                    /** 마감된 방일때 이미지를 마감으로 바꿈 **/
                    if(is_end_time(long_end_time) == true) {
                        Log.e("is_end_time !!! ", Boolean.toString(is_end_time(post.getEnd_time())));
                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.over));
                    }



                    viewHolder.titleView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            games_id = recycleAdapter.getRef(position).getKey();

                            ref.child("games").child(games_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String, String> map = (Map)dataSnapshot.getValue();
                                    is_obj = Integer.parseInt(String.valueOf(map.get("is_obj")));

                                    long time = Long.parseLong(String.valueOf(map.get("end_time")));
                                    is_end = is_end_time(time);

                                    if(is_obj == 1 && is_end == false) {
                                        Intent intent2 = new Intent(getApplicationContext(), DetailActivity2.class);
                                        intent2.putExtra("games_id", games_id);
                                        startActivity(intent2);
                                    } else if(is_obj == 0 && is_end == false){
                                        Intent intent2 = new Intent(getApplicationContext(), DetailSubjectActivity2.class);
                                        intent2.putExtra("games_id", games_id);
                                        startActivity(intent2);
                                    } else if(is_end == true){
                                        if(is_obj ==1) {
                                            Intent intent2 = new Intent(getApplicationContext(), DetailOwnerResult.class);
                                            intent2.putExtra("games_id", games_id);
                                            startActivity(intent2);
                                            Toast.makeText(MainActivity.this, "시간이 마감되었습니다", Toast.LENGTH_SHORT).show();
                                        } else if(is_obj==0){
                                            Intent intent2 = new Intent(getApplicationContext(), DetailOwnerResult_sub.class);
                                            intent2.putExtra("games_id", games_id);
                                            startActivity(intent2);
                                            Toast.makeText(MainActivity.this, "시간이 마감되었습니다", Toast.LENGTH_SHORT).show();
                                        }
                                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.over));
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

                    /** 마감된 방일때 이미지를 마감으로 바꿈 **/
                    if(is_end_time(long_end_time) == true) {
                        Log.e("is_end_time !!! ", Boolean.toString(is_end_time(post.getEnd_time())));
                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.over));
                    }


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
                                    } else if(is_end == true){
                                        Intent intent2 = new Intent(getApplicationContext(), DetailResult.class);
                                        intent2.putExtra("games_id", games_id);
                                        startActivity(intent2);
                                        Toast.makeText(MainActivity.this, "시간이 마감되었습니다", Toast.LENGTH_SHORT).show();
//                                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.over));
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

    /**내가 참여했는지 아닌지 알아보는 메소드 -> 이미지뷰 변경**/
    public boolean is_joining(final String myId, String gameId) {
        ref.child("games").child(gameId).child("participant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("Count " ,""+dataSnapshot);
//                Map<String, String> map = (Map)dataSnapshot.getValue();

                for (DataSnapshot friendSnapshot: dataSnapshot.getChildren()) {
//                    Log.e("uid :", title +"" + friendSnapshot.getKey());
                    if(myId.equals(friendSnapshot.getKey())){
                        is_join = true;
                        break;
                    } else {
                        is_join = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return is_join;
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
