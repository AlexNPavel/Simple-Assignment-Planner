package com.example.alex.contactstest;

import android.app.TimePickerDialog;
import android.content.Context;
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

    int startHour;
    int startMin;
    int endHour;
    int endMin;
    CourseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        dbHelper = new CourseDBHelper(this);
        final TextView startTime = (TextView) findViewById(R.id.course_start_time);
        final TextView endTime = (TextView) findViewById(R.id.course_end_time);
        final Context context = this;
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                startHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                startMin = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startHour = selectedHour;
                        startMin = selectedMinute;
                        if (!DateFormat.is24HourFormat(context)) {
                            if (startHour == 12) {
                                if (startMin < 10) {
                                    startTime.setText("12:0" + selectedMinute + " PM");
                                } else {
                                    startTime.setText("12:" + selectedMinute + " PM");
                                }
                            }
                            else if (startHour == 0) {
                                if (startMin < 10) {
                                    startTime.setText("12:0" + selectedMinute + " AM");
                                } else {
                                    startTime.setText("12:" + selectedMinute + " AM");
                                }
                            }
                            else if (startHour > 12) {
                                if (startMin < 10) {
                                    startTime.setText((selectedHour - 12) + ":0" + selectedMinute + " PM");
                                } else {
                                    startTime.setText((selectedHour - 12) + ":" + selectedMinute + " PM");
                                }
                            }
                            else {
                                if (startMin < 10) {
                                    startTime.setText(selectedHour + ":0" + selectedMinute + " AM");
                                } else {
                                    startTime.setText(selectedHour + ":" + selectedMinute + " AM");
                                }
                            }
                        }
                        else {
                            if (startMin < 10) {
                                startTime.setText(selectedHour + ":0" + selectedMinute);
                            } else {
                                startTime.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
                    }
                }, startHour, startMin, DateFormat.is24HourFormat(context));
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                endHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                endMin = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endHour = selectedHour;
                        endMin = selectedMinute;
                        if (!DateFormat.is24HourFormat(context)) {
                            if (endHour == 12) {
                                if (endMin < 10) {
                                    endTime.setText("12:0" + selectedMinute + " PM");
                                } else {
                                    endTime.setText("12:" + selectedMinute + " PM");
                                }
                            }
                            else if (endHour == 0) {
                                if (endMin < 10) {
                                    endTime.setText("12:0" + selectedMinute + " AM");
                                } else {
                                    endTime.setText("12:" + selectedMinute + " AM");
                                }
                            }
                            else if (endHour > 12) {
                                if (endMin < 10) {
                                    endTime.setText((selectedHour - 12) + ":0" + selectedMinute + " PM");
                                } else {
                                    endTime.setText((selectedHour - 12) + ":" + selectedMinute + " PM");
                                }
                            }
                            else {
                                if (endMin < 10) {
                                    endTime.setText(selectedHour + ":0" + selectedMinute + " AM");
                                } else {
                                    endTime.setText(selectedHour + ":" + selectedMinute + " AM");
                                }
                            }
                        }
                        else {
                            if (endMin < 10) {
                                endTime.setText(selectedHour + ":0" + selectedMinute);
                            } else {
                                endTime.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
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
        }
        else {
            String courseName = editText.getText().toString();
            editText = (EditText) findViewById(R.id.course_prof_name);
            String prof_name = editText.getText().toString();
            dbHelper.insertCourseData(courseName, startHour, startMin, endHour, endMin, prof_name);
            finish();
        }
    }
}
