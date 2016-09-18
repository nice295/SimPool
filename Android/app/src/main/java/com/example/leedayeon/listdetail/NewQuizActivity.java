package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** khlee
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
*/

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.paperdb.Paper;

public class NewQuizActivity extends AppCompatActivity {

    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy MM dd aa hh:mm");
    private Button btTime;
    private Button btNext;

    private EditText etAddTitle;
    private EditText etAddContent;

    /** khlee
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
//            Toast.makeText(NewQuizActivity.this,
//                    mFormatter.format(date), Toast.LENGTH_SHORT).show();

            btTime.setText(mFormatter.format(date));
            Paper.book().write("date", date);  //Date
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(NewQuizActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);

        setTitle("내기 추가하기");

        Paper.init(this);

        btTime = (Button) findViewById(R.id.btTime);
        btNext = (Button) findViewById(R.id.btNext);

        etAddTitle = (EditText) findViewById(R.id.etAddTitle);
        etAddContent = (EditText) findViewById(R.id.etAddContent);

        /** khlee
        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        //.setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });
         */

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("title", etAddTitle.getText().toString()); //String
                Paper.book().write("content", etAddContent.getText().toString()); //String

                Intent intent = new Intent(NewQuizActivity.this, NewQuizActivity2.class);
                startActivity(intent);
            }
        });

    }
}
