package com.example.alex.contactstest;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by alex on 9/18/15.
 */
public class CourseTimeLayout {

    Context context;
    Activity activity;
    boolean isNew;
    CourseTimeStore timeStore;
    int startHour;
    int startMin;
    int endHour;
    int endMin;
    LinearLayout fullSection;
    ImageView minus;

    public CourseTimeLayout(Activity _activity, Context _context, boolean _isNew, CourseTimeStore _timeStore) {
        activity = _activity;
        context = _context;
        isNew = _isNew;
        timeStore = _timeStore;
    }

    public void setTimes(int sHour, int sMin, int eHour, int eMin) {
        startHour = sHour;
        startMin = sMin;
        endHour = eHour;
        endMin = eMin;
    }

    public ImageView getImageView() {
        return minus;
    }

    public void removeLayout() {
        if (fullSection != null) {
            ((LinearLayout) activity.findViewById(R.id.big_papa)).removeView(fullSection);
            if (((LinearLayout) activity.findViewById(R.id.big_papa)).getChildCount() == 1) {
                ((LinearLayout) activity.findViewById(R.id.hr)).removeAllViews();
            }
        }
    }

    public View createLayout() {
        fullSection = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fullSection.setOrientation(LinearLayout.VERTICAL);
        fullSection.setLayoutParams(layoutParams);
        ((LinearLayout) activity.findViewById(R.id.big_papa)).addView(fullSection);
        System.out.println(((LinearLayout) activity.findViewById(R.id.big_papa)).getChildCount());
        if (((LinearLayout) activity.findViewById(R.id.big_papa)).getChildCount() == 2) {
            ((LinearLayout) activity.findViewById(R.id.hr)).addView(new HorizontalRuleView(context));
        }
        final RelativeLayout sectionLayout = new RelativeLayout(context);
        fullSection.addView(sectionLayout);
        LinearLayout timesLayout = new LinearLayout(context);
        timesLayout.setLayoutParams(layoutParams);
        timesLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout theLayout = new LinearLayout(context);
        sectionLayout.addView(theLayout);
        theLayout.addView(timesLayout);
        LinearLayout doubleInnerLayout = new LinearLayout(context);
        doubleInnerLayout.setOrientation(LinearLayout.HORIZONTAL);
        doubleInnerLayout.setPadding(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, activity.getResources().getDisplayMetrics()), 0, 0);
        doubleInnerLayout.setLayoutParams(layoutParams);
        final TextView txt1 = new TextView(context);
        txt1.setTextSize(18);
        txt1.setText("Start Time:");
        timesLayout.addView(doubleInnerLayout);
        doubleInnerLayout.addView(txt1);
        final TextView txt2 = new TextView(context);
        txt2.setTextSize(18);
        txt2.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, activity.getResources().getDisplayMetrics()), 0, 0, 0);
        txt2.setText("Click to set");
        doubleInnerLayout.addView(txt2);
        txt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                if (isNew && (startHour == 0 && startMin == 0)) {
                    startHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    startMin = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startHour = selectedHour;
                        startMin = selectedMinute;
                        timeStore.setStartTime(startHour, startMin);
                        txt2.setTextColor(Color.parseColor("#000000"));
                        txt2.setText(Format24Hour.format(selectedHour, selectedMinute, context));
                    }
                }, startHour, startMin, DateFormat.is24HourFormat(context));
                mTimePicker.setTitle("Select Start Time");
                mTimePicker.show();
            }
        });
        doubleInnerLayout = new LinearLayout(context);
        doubleInnerLayout.setOrientation(LinearLayout.HORIZONTAL);
        doubleInnerLayout.setLayoutParams(layoutParams);
        final TextView txt3 = new TextView(context);
        txt3.setTextSize(18);
        txt3.setText("End Time:");
        timesLayout.addView(doubleInnerLayout);
        doubleInnerLayout.addView(txt3);
        final TextView txt4 = new TextView(context);
        txt4.setTextSize(18);
        txt4.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, activity.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, activity.getResources().getDisplayMetrics()), 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, activity.getResources().getDisplayMetrics()));
        txt4.setText("Click to set");
        doubleInnerLayout.addView(txt4);
        txt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                if (isNew && (endHour == 0 && endMin == 0)) {
                    endHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    endMin = mcurrentTime.get(Calendar.MINUTE);
                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endHour = selectedHour;
                        endMin = selectedMinute;
                        timeStore.setEndTime(endHour, endMin);
                        txt4.setTextColor(Color.parseColor("#000000"));
                        txt4.setText(Format24Hour.format(selectedHour, selectedMinute, context));
                    }
                }, endHour, endMin, DateFormat.is24HourFormat(context));
                mTimePicker.setTitle("Select End Time");
                mTimePicker.show();
            }
        });
        RelativeLayout minusLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        minusLayout.setLayoutParams(rLayoutParams);
        minus = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, activity.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, activity.getResources().getDisplayMetrics()));
        minus.setLayoutParams(params);
        minus.setBackgroundResource(R.drawable.ic_remove_red_600_48dp);
        minusLayout.addView(minus);
        sectionLayout.addView(minusLayout);
        fullSection.addView(new HorizontalRuleView(context));

        return fullSection;
    }
}
