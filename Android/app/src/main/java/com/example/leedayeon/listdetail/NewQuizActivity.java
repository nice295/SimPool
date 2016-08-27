package com.example.leedayeon.listdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewQuizActivity extends AppCompatActivity {

    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy MMMM dd aa hh:mm");
    private Button btTime;
    private Button btNext;

    private EditText etAddTitle;
    private EditText etAddContent;

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            Toast.makeText(NewQuizActivity.this,
                    mFormatter.format(date), Toast.LENGTH_SHORT).show();

            btTime.setText(mFormatter.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(NewQuizActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);

        setTitle("내기 추가하기");

        btTime = (Button) findViewById(R.id.btTime);
        btNext = (Button) findViewById(R.id.btNext);

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


        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewQuizActivity.this, NewQuizActivity2.class);
                startActivity(intent);
            }
        });


//        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
//        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//
//        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
//                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
//
//                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
//                        datePicker.getMonth(),
//                        datePicker.getDayOfMonth(),
//                        timePicker.getCurrentHour(),
//                        timePicker.getCurrentMinute());
//
//                long time = calendar.getTimeInMillis();
//                alertDialog.dismiss();
//            }});
//        alertDialog.setView(dialogView);
//        alertDialog.show();
    }
}
