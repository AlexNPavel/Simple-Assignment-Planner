package com.example.alex.contactstest;

import android.app.DatePickerDialog;
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
    private int assignID;
    String assignName;
    boolean isNew;
    CourseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assignment);
        Intent currentIntent = getIntent();
        dbHelper = new CourseDBHelper(this);
        Cursor assignment= dbHelper.getAssignment(currentIntent.getIntExtra("assignID", 0));
        assignment.moveToFirst();
        isNew = currentIntent.getBooleanExtra("isNew", true);
        if (!isNew) {
            setTitle("Edit Assignment");
            assignName = assignment.getString(assignment.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_NAME));
            dueYear = assignment.getInt(assignment.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_YEAR));
            dueMonth = assignment.getInt(assignment.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_MONTH));
            dueDay = assignment.getInt(assignment.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_DAY));
            dueHour = assignment.getInt(assignment.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_HOUR));
            dueMin = assignment.getInt(assignment.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_MIN));
            assignID = assignment.getInt(assignment.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_ID));
        }
        else {
            assignID = 0;
        }
        courseID = currentIntent.getIntExtra("courseID", 1);
        dbHelper = new CourseDBHelper(this);
        final EditText name = (EditText) findViewById(R.id.assignment_name);
        final TextView dueDate = (TextView) findViewById(R.id.assignment_due_date);
        final TextView dueTime = (TextView) findViewById(R.id.assignment_due_time);
        final Context context = this;
        if (!isNew) {
            name.setText(assignName);
            dueTime.setText(Format24Hour.format(dueHour, dueMin, context));
            String yearStr = Integer.toString(dueYear);
            yearStr = yearStr.substring((yearStr.length() - 2), yearStr.length());
            dueDate.setText(dueMonth + "/" + dueDay + "/" + yearStr);
        }
        dueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                if (isNew) {
                    dueDay = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                    dueMonth = mcurrentTime.get(Calendar.MONTH);
                    dueYear = mcurrentTime.get(Calendar.YEAR);
                }
                DatePickerDialog mTimePicker;
                mTimePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dueDay = day;
                        dueMonth = month;
                        dueYear = year;
                        String yearStr = Integer.toString(dueYear);
                        yearStr = yearStr.substring((yearStr.length() - 2), yearStr.length());
                        dueDate.setText(dueMonth + "/" + dueDay + "/" + yearStr);
                    }
                }, dueYear, dueMonth, dueDay);
                mTimePicker.setTitle("Select Date");
                mTimePicker.show();
            }
        });

        dueTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                if (isNew) {
                    dueHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    dueMin = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        dueHour = selectedHour;
                        dueMin = selectedMinute;
                        dueTime.setText(Format24Hour.format(dueHour, dueMin, context));
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
            if (isNew) {
                dbHelper.insertAssignmentData(assignmentName, dueYear, dueMonth, dueDay, dueHour, dueMin, courseID);
            }
            else {
                dbHelper.updateAssignmentData(assignmentName, dueYear, dueMonth, dueDay, dueHour, dueMin, courseID, assignID);
            }
            finish();
        }
    }
}
