package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    EditText etTitle;
    EditText etDesc;
    Button addBtn;
    ListView postsView;
    FirebaseListAdapter<Post> mAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = (EditText)findViewById(R.id.editText);
        etDesc = (EditText)findViewById(R.id.editText2);
        addBtn = (Button)findViewById(R.id.pickDate);

        postsView = (ListView)findViewById(R.id.listView);

//      DISPLAY
        mAdapter = new FirebaseListAdapter<Post>(this, Post.class, android.R.layout.two_line_list_item, ref) {
            @Override
            protected void populateView(View v, Post model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                ((TextView)v.findViewById(android.R.id.text1)).setTypeface(null, Typeface.NORMAL);
                ((TextView)v.findViewById(android.R.id.text1)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getTitle());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getDescription());
            }
        };

        postsView.setAdapter(mAdapter);

        postsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, ((TextView)view.findViewById(android.R.id.text1)).getText().toString(), Toast.LENGTH_SHORT).show();

                Post p = new Post(((TextView)view.findViewById(android.R.id.text1)).getText().toString(), ((TextView)view.findViewById(android.R.id.text2)).getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail", p);

                intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String desc = etDesc.getText().toString();

                Post p = new Post(title, desc);
                ref.push().setValue(p);

                etTitle.setText("");
                etDesc.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

}