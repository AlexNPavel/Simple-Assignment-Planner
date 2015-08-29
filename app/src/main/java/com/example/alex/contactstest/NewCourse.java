package com.example.alex.contactstest;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;


public class NewCourse extends AppCompatActivity {

    private boolean isNew;
    private int id;
    int startHour;
    int startMin;
    int endHour;
    int endMin;
    String courseName;
    String profName;
    CourseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        Intent intent = getIntent();
        dbHelper = new CourseDBHelper(this);
        Cursor course = dbHelper.getCourse(intent.getIntExtra("courseID", 0));
        course.moveToFirst();
        isNew = intent.getBooleanExtra("isNew", true);
        if (!isNew) {
            courseName = course.getString(course.getColumnIndex(CourseDBHelper.COURSES_COLUMN_NAME));
            startHour = course.getInt(course.getColumnIndex(CourseDBHelper.COURSES_COLUMN_START_HOUR));
            startMin = course.getInt(course.getColumnIndex(CourseDBHelper.COURSES_COLUMN_START_MIN));
            endHour = course.getInt(course.getColumnIndex(CourseDBHelper.COURSES_COLUMN_END_HOUR));
            endMin = course.getInt(course.getColumnIndex(CourseDBHelper.COURSES_COLUMN_END_MIN));
            profName = course.getString(course.getColumnIndex(CourseDBHelper.COURSES_COLUMN_PROF));
            id = course.getInt(course.getColumnIndex(CourseDBHelper.COURSES_COLUMN_ID));
        }
        else {
            id = 0;
        }
        dbHelper = new CourseDBHelper(this);
        final EditText name = (EditText) findViewById(R.id.course_name);
        final TextView startTime = (TextView) findViewById(R.id.course_start_time);
        final TextView endTime = (TextView) findViewById(R.id.course_end_time);
        final EditText profNameField = (EditText) findViewById(R.id.course_prof_name);
        final Context context = this;
        if (!isNew) {
            name.setText(courseName);
            startTime.setText(Format24Hour.format(startHour, startMin, context));
            endTime.setText(Format24Hour.format(endHour, endMin, context));
            if (profName != null) {
                profNameField.setText(profName);
            }
        }
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                if (isNew) {
                    startHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    startMin = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startHour = selectedHour;
                        startMin = selectedMinute;
                        startTime.setText(Format24Hour.format(startHour, startMin, context));
                    }
                }, startHour, startMin, DateFormat.is24HourFormat(context));
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                if (isNew) {
                    endHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    endMin = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endHour = selectedHour;
                        endMin = selectedMinute;
                        endTime.setText(Format24Hour.format(endHour, endMin, context));
                    }
                }, endHour, endMin, DateFormat.is24HourFormat(context));
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveCourse(View view) throws IOException {
        EditText editText = (EditText) findViewById(R.id.course_name);
        if (editText.getText().toString().matches("")) {
            Toast.makeText(NewCourse.this, "Course name cannot be blank", Toast.LENGTH_LONG).show();
        } else {
            courseName = editText.getText().toString();
            editText = (EditText) findViewById(R.id.course_prof_name);
            profName = editText.getText().toString();
            if (isNew) {
                dbHelper.insertCourseData(courseName, startHour, startMin, endHour, endMin, profName);
            }
            else {
                dbHelper.updateCourseData(id, courseName, startHour, startMin, endHour, endMin, profName);
            }
            finish();
        }
    }
}
