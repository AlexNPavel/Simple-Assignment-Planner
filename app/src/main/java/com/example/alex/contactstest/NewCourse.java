package com.example.alex.contactstest;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    //ArrayList<LinearLayout> layouts;
    int times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        //layouts = new ArrayList<>();
        times = 0;
        Intent intent = getIntent();
        dbHelper = new CourseDBHelper(this);
        Cursor course = dbHelper.getCourse(intent.getIntExtra("courseID", 0));
        course.moveToFirst();
        isNew = intent.getBooleanExtra("isNew", true);
        if (!isNew) {
            setTitle("Edit Course");
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
        ImageView addTimes= (ImageView) findViewById(R.id.add_course_times);
        addTimes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final LinearLayout fullSection = new LinearLayout(NewCourse.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                fullSection.setOrientation(LinearLayout.VERTICAL);
                fullSection.setLayoutParams(layoutParams);
                ((LinearLayout) findViewById(R.id.big_papa)).addView(fullSection);
                System.out.println(((LinearLayout) findViewById(R.id.big_papa)).getChildCount());
                if (((LinearLayout) findViewById(R.id.big_papa)).getChildCount() == 2) {
                    ((LinearLayout) findViewById(R.id.hr)).addView(new HorizontalRuleView(NewCourse.this));
                }
                final RelativeLayout sectionLayout = new RelativeLayout(NewCourse.this);
                fullSection.addView(sectionLayout);
                LinearLayout timesLayout = new LinearLayout(NewCourse.this);
                timesLayout.setLayoutParams(layoutParams);
                timesLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout theLayout = new LinearLayout(NewCourse.this);
                sectionLayout.addView(theLayout);
                theLayout.addView(timesLayout);
                LinearLayout doubleInnerLayout = new LinearLayout(NewCourse.this);
                doubleInnerLayout.setOrientation(LinearLayout.HORIZONTAL);
                doubleInnerLayout.setPadding(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()), 0, 0);
                doubleInnerLayout.setLayoutParams(layoutParams);
                final TextView txt1 = new TextView(NewCourse.this);
                txt1.setTextSize(18);
                txt1.setText("Start Time:");
                timesLayout.addView(doubleInnerLayout);
                doubleInnerLayout.addView(txt1);
                final TextView txt2 = new TextView(NewCourse.this);
                txt2.setTextSize(18);
                txt2.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()), 0, 0, 0);
                txt2.setText("Click to set");
                doubleInnerLayout.addView(txt2);
                txt2.setOnClickListener(new View.OnClickListener() {

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
                                txt2.setTextColor(Color.parseColor("#000000"));
                                txt2.setText(Format24Hour.format(selectedHour, selectedMinute, context));
                            }
                        }, endHour, endMin, DateFormat.is24HourFormat(context));
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });
                doubleInnerLayout = new LinearLayout(NewCourse.this);
                doubleInnerLayout.setOrientation(LinearLayout.HORIZONTAL);
                doubleInnerLayout.setLayoutParams(layoutParams);
                final TextView txt3 = new TextView(NewCourse.this);
                txt3.setTextSize(18);
                txt3.setText("End Time:");
                timesLayout.addView(doubleInnerLayout);
                doubleInnerLayout.addView(txt3);
                final TextView txt4 = new TextView(NewCourse.this);
                txt4.setTextSize(18);
                txt4.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics()),
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()), 0,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                txt4.setText("Click to set");
                doubleInnerLayout.addView(txt4);
                txt4.setOnClickListener(new View.OnClickListener() {

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
                                txt4.setTextColor(Color.parseColor("#000000"));
                                txt4.setText(Format24Hour.format(selectedHour, selectedMinute, context));
                            }
                        }, endHour, endMin, DateFormat.is24HourFormat(context));
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });
                RelativeLayout minusLayout = new RelativeLayout(NewCourse.this);
                RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                rLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                rLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                minusLayout.setLayoutParams(rLayoutParams);
                ImageView minus = new ImageView(NewCourse.this);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, getResources().getDisplayMetrics()),
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, getResources().getDisplayMetrics()));
                minus.setLayoutParams(parms);
                minus.setBackgroundResource(R.drawable.ic_remove_red_600_48dp);
                //     final int currentRun = layouts.size();
                minusLayout.addView(minus);
                sectionLayout.addView(minusLayout);
                fullSection.addView(new HorizontalRuleView(NewCourse.this));
                minus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) findViewById(R.id.big_papa)).removeView(fullSection);
                        if (((LinearLayout) findViewById(R.id.big_papa)).getChildCount() == 1) {
                            ((LinearLayout) findViewById(R.id.hr)).removeAllViews();
                        }
                        //layouts.remove(currentRun);
                    }
                });
                //layouts.add(fullSection);
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
