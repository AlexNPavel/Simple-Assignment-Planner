package com.alexpavel.simpleassignmentplanner;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;

/**
 * Created by alex on 7/22/15. Add new header later.
 */
public class Assignment {

    private final String name;
    private final int dueYear;
    private final int dueMonth;
    private final int dueDay;
    private final int dueHour;
    private final int dueMin;
    private final int ID;
    private final Context context;

    public Assignment(int assignID, CourseDBHelper dbHelper, Context context) {
        this.context = context;
        Cursor res = dbHelper.getAssignment(assignID);

        ID = assignID;

        res.moveToFirst();

        name = res.getString(res.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_NAME));
        dueYear = res.getInt(res.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_YEAR));
        dueMonth = res.getInt(res.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_MONTH));
        dueDay = res.getInt(res.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_DAY));
        dueHour = res.getInt(res.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_HOUR));
        dueMin = res.getInt(res.getColumnIndex(CourseDBHelper.ASSIGN_COLUMN_MIN));

    }

    public String getDueTime() {
        if (!DateFormat.is24HourFormat(context)) {
            if (dueHour == 12) {
                if (dueMin < 10) {
                    return "12:0" + dueMin + " PM";
                } else {
                    return "12:" + dueMin + " PM";
                }
            }
            else if (dueHour == 0) {
                if (dueMin < 10) {
                    return "12:0" + dueMin + " AM";
                } else {
                    return "12:" + dueMin + " AM";
                }
            }
            else if (dueHour > 12) {
                if (dueMin < 10) {
                    return (dueHour - 12) + ":0" + dueMin + " PM";
                } else {
                    return (dueHour - 12) + ":" + dueMin + " PM";
                }
            }
            else {
                if (dueMin < 10) {
                    return dueHour + ":0" + dueMin + " AM";
                } else {
                    return dueHour + ":" + dueMin + " AM";
                }
            }
        }
        else {
            if (dueMin < 10) {
                return dueHour + ":0" + dueMin;
            } else {
                return dueHour + ":" + dueMin;
            }
        }
    }

    public String getDueDate() {
        String yearStr = Integer.toString(dueYear);
        yearStr = yearStr.substring((yearStr.length() - 2), yearStr.length());
        return(dueMonth + "/" + dueDay + "/" + yearStr);
    }

    public String getName() {
        return name;
    }

    public int getID() { return ID; }

}
