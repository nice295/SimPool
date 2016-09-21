package com.example.leedayeon.listdetail;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/** khlee
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
*/

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.paperdb.Paper;

public class NewQuizActivity extends AppCompatActivity {

    // date and time
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    static final int TIME_12_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 2;


    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy MM dd aa hh:mm");
    private Button btTime;
    private Button btDate;
    private Button btNext;

    private EditText etAddTitle;
    private EditText etAddContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);

        setTitle("내기 추가하기");

        Paper.init(this);

        btDate = (Button) findViewById(R.id.pickDate);
        btTime = (Button) findViewById(R.id.pickTime12);
        btNext = (Button) findViewById(R.id.btNext);

        etAddTitle = (EditText) findViewById(R.id.etAddTitle);
        etAddContent = (EditText) findViewById(R.id.etAddContent);


        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("title", etAddTitle.getText().toString()); //String
                Paper.book().write("description", etAddContent.getText().toString()); //String
                Paper.book().write("end_time", btDate.getText().toString() + " " + btTime.getText().toString());
//                Paper.book().write("year", mYear);
//                Paper.book().write("month", mMonth);
//                Paper.book().write("day", mDay);
//                Paper.book().write("hour", mHour);
//                Paper.book().write("minute", mMinute);

                Intent intent = new Intent(NewQuizActivity.this, NewQuizActivity2.class);
                startActivity(intent);
            }
        });

        setDialogOnClickListener(R.id.pickDate, DATE_DIALOG_ID);
        setDialogOnClickListener(R.id.pickTime12, TIME_12_DIALOG_ID);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        updateDisplay();

    }

    private void setDialogOnClickListener(int buttonId, final int dialogId) {
        Button b = (Button) findViewById(buttonId);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(dialogId);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_12_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, id == TIME_12_DIALOG_ID);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case TIME_12_DIALOG_ID:
                ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
                break;
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }

    private void updateDisplay() {
        btDate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mYear).append("년 ")
                        .append(mMonth + 1).append("월 ")
                        .append(mDay).append("일"));

        btTime.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pad(mHour)).append("시")
                        .append(pad(mMinute)).append("분"));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay();
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
