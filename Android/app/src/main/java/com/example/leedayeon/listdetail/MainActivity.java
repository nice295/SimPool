package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        public ImageView imageSitu;

        public PostViewHolder(View v) {
            super(v);
            titleView = (TextView) itemView.findViewById(R.id.textViewTitle);
            descView = (TextView) itemView.findViewById(R.id.textViewDesc);
            dateView = (TextView) itemView.findViewById(R.id.textViewDate);
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

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        ref = FirebaseDatabase.getInstance().getReference();


        recycleAdapter = new FirebaseRecyclerAdapter<NewQuiz, PostViewHolder>(
                NewQuiz.class,
                R.layout.item_message,
                PostViewHolder.class,
                ref.child("games")
        ) {

            public String games_id;


            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, NewQuiz post, final int position) {

                String owner =post.getOwner();
                long long_end_time = post.getEnd_time();
                is_end = is_end_time(long_end_time);

                /** 내가 참여한 방일때 이미지를 참여중으로 바꿈 -> 동작안함
                if(is_joining(user.getUid(), recycleAdapter.getRef(position).getKey()) == true) {
                    viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.join));
                }
                 **/

                viewHolder.descView.setText(post.getDescription());
                viewHolder.titleView.setText(post.getTitle());

                /** 마감된 방일때 이미지를 마감으로 바꿈 **/
                if(is_end_time(long_end_time) == true) {
                    viewHolder.dateView.setText("마감됨");
                    viewHolder.mView.setBackgroundColor(getResources().getColor(R.color.room_timeover));
                    if(user.getUid().equals(owner)) {
                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.owner1));
                    } else {
                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, android.R.color.transparent));
                    }
                } else {
                    viewHolder.dateView.setText(formatTimeString(post.getEnd_time()));
                    viewHolder.mView.setBackgroundColor(Color.WHITE);
                    if(user.getUid().equals(owner)) {
                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.owner1));
                    } else {
                        viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, android.R.color.transparent));
                    }
                }


                if(user.getUid().equals(owner)) { //방을 만든 주인일때
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
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
                                        //viewHolder.imageSitu.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.mipmap.over));
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
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리

        Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
        mAuth.getInstance().signOut();
        int id = item.getItemId();
        switch (id){
            case R.id.logout_button:
                mAuth.getInstance().signOut();
                Intent sign_intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(sign_intent);
                break;
            case R.id.introduce:
                Intent introduce_intent = new Intent(getApplicationContext(), IntroduceActivity.class);
                startActivity(introduce_intent);
                break;
        }

        return super.onOptionsItemSelected(item);
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

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    public static String formatTimeString(long tempDate) {
        Calendar cal_today = Calendar.getInstance();
        long curTime = cal_today.getTimeInMillis();
        long diffTime = (tempDate-curTime) / 1000;

        String msg = null;
        Log.e("MAinActivity : ", Long.toString(diffTime));
        if (diffTime < TIME_MAXIMUM.SEC) {
            // sec
            msg = "곧 마감";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            // min
            msg = diffTime + "분 후 마감";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            // hour
            msg = (diffTime) + "시간 후 마감";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            // day
            msg = (diffTime) + "일 후 마감";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            // day
            msg = (diffTime) + "달 후 마감";
        } else {
            msg = "1년 이상 남음";
        }

        return msg;
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
