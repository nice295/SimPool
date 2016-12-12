package com.simpool.leedayeon.listdetail;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

/** khlee
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
*/

import java.util.Calendar;

import io.paperdb.Paper;

public class NewQuizActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "NewQuizActivity";
    // date and time
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    static final int TIME_12_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 2;

    Calendar end_cal = Calendar.getInstance();

    private Button btTime;
    private Button btDate;
    private Button btNext;

    private EditText etAddTitle;
    private EditText etAddContent;
    private LinearLayout mLlBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);

        setTitle(getString(R.string.addQuiz));

        Paper.init(this);

        mLlBg = (LinearLayout) findViewById(R.id.llBg);
        mLlBg.setOnTouchListener(this);
        btDate = (Button) findViewById(R.id.pickDate);
        btTime = (Button) findViewById(R.id.pickTime12);
        btNext = (Button) findViewById(R.id.btNext);

        etAddTitle = (EditText) findViewById(R.id.etAddTitle);
        etAddContent = (EditText) findViewById(R.id.etAddContent);


        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                test();

                Paper.book().write("title", etAddTitle.getText().toString()); //String
                Paper.book().write("description", etAddContent.getText().toString()); //String
                Paper.book().write("end_time", end_cal.getTimeInMillis()); //Long

//                Toast.makeText(NewQuizActivity.this, Long.toString(end_cal.getTimeInMillis()), Toast.LENGTH_SHORT).show();

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
                    end_cal.set(Calendar.YEAR, mYear);
                    end_cal.set(Calendar.MONTH, mMonth);
                    end_cal.set(Calendar.DATE, mDay);
//                    Toast.makeText(NewQuizActivity.this, Integer.toString(mDay), Toast.LENGTH_SHORT).show();
                    updateDisplay();
                }
            };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    end_cal.set(Calendar.HOUR_OF_DAY, mHour);
                    end_cal.set(Calendar.MINUTE, mMinute);
                    updateDisplay();
                }
            };


    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == mLlBg) {
            Log.d(TAG, "Touched on BG");
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        return false;
    }


    /**     long형으로 시간이 잘 담겨지는지 확인하기 위해 추가한 클래스.Log창 확인(삭제예정)
    public void test() {
        long today = end_cal.getTimeInMillis(); // long 형의 현재시간
        System.out.println(today);

        DateFormat df = new SimpleDateFormat("HH:mm"); // HH=24h, hh=12h
        String str = df.format(today);
        System.out.println(str);

        Date date = new Date(today);
        System.out.println(date);
    }
     **/
}
