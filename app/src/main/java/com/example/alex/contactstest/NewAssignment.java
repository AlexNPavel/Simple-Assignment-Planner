package com.example.alex.contactstest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;


public class NewAssignment extends AppCompatActivity {

    int dueHour;
    int dueMin;
    int dueMonth;
    int dueDay;
    int dueYear;
    int courseID;
    CourseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent currentIntent = getIntent();
        courseID = currentIntent.getIntExtra(Assignments.EXTRA_COURSE, 1);
        setContentView(R.layout.activity_new_assignment);
        dbHelper = new CourseDBHelper(this);
        final TextView dueDate = (TextView) findViewById(R.id.assignment_due_date);
        final TextView dueTime = (TextView) findViewById(R.id.assignment_due_time);
        final Context context = this;
        dueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                dueDay = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                dueMonth = mcurrentTime.get(Calendar.MONTH);
                dueYear = mcurrentTime.get(Calendar.YEAR);
                DatePickerDialog mTimePicker;
                mTimePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dueDay = day;
                        dueMonth = month;
                        dueYear = year;
                        String yearStr = Integer.toString(year);
                        yearStr = yearStr.substring((yearStr.length() - 2), yearStr.length());
                        if (dueMin < 10) {
                            dueDate.setText(month + "/" + day + "/" + yearStr);
                        }
                    }
                }, dueYear, dueMonth, dueDay);
                mTimePicker.setTitle("Select Date");
                mTimePicker.show();
            }
        });

        dueTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                dueHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                dueMin = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        dueHour = selectedHour;
                        dueMin = selectedMinute;
                        if (!DateFormat.is24HourFormat(context)) {
                            if (dueHour == 12) {
                                if (dueMin < 10) {
                                    dueTime.setText("12:0" + selectedMinute + " PM");
                                } else {
                                    dueTime.setText("12:" + selectedMinute + " PM");
                                }
                            }
                            else if (dueHour == 0) {
                                if (dueMin < 10) {
                                    dueTime.setText("12:0" + selectedMinute + " AM");
                                } else {
                                    dueTime.setText("12:" + selectedMinute + " AM");
                                }
                            }
                            else if (dueHour > 12) {
                                if (dueMin < 10) {
                                    dueTime.setText((selectedHour - 12) + ":0" + selectedMinute + " PM");
                                } else {
                                    dueTime.setText((selectedHour - 12) + ":" + selectedMinute + " PM");
                                }
                            }
                            else {
                                if (dueMin < 10) {
                                    dueTime.setText(selectedHour + ":0" + selectedMinute + " AM");
                                } else {
                                    dueTime.setText(selectedHour + ":" + selectedMinute + " AM");
                                }
                            }
                        }
                        else {
                            if (dueMin < 10) {
                                dueTime.setText(selectedHour + ":0" + selectedMinute);
                            } else {
                                dueTime.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
                    }
                }, dueHour, dueMin, DateFormat.is24HourFormat(context));
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_assignment, menu);
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
        EditText editText = (EditText) findViewById(R.id.assignment_name);
        String assignmentName = editText.getText().toString();
        if (assignmentName.matches("")) {
            Toast.makeText(NewAssignment.this, "Assignment name cannot be blank", Toast.LENGTH_LONG).show();
        }
        else {
            dbHelper.insertAssignmentData(assignmentName, dueYear, dueMonth, dueDay, dueHour, dueMin, courseID);
            finish();
        }
    }
}
