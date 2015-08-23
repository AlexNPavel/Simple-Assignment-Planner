package com.example.alex.contactstest;

import android.database.Cursor;

/**
 * Created by alex on 7/22/15.
 */
public class Assignment {

    private final String name;
    private final int dueYear;
    private final int dueMonth;
    private final int dueDay;
    private final int dueHour;
    private final int dueMin;
    private final int ID;

    public Assignment(int assignID, CourseDBHelper dbHelper) {

        Cursor res = dbHelper.getAssignment(assignID);

        ID = assignID;

        res.moveToFirst();

        name = res.getString(res.getColumnIndex(dbHelper.ASSIGN_COLUMN_NAME));
        dueYear = res.getInt(res.getColumnIndex(dbHelper.ASSIGN_COLUMN_YEAR));
        dueMonth = res.getInt(res.getColumnIndex(dbHelper.ASSIGN_COLUMN_MONTH));
        dueDay = res.getInt(res.getColumnIndex(dbHelper.ASSIGN_COLUMN_DAY));
        dueHour = res.getInt(res.getColumnIndex(dbHelper.ASSIGN_COLUMN_HOUR));
        dueMin = res.getInt(res.getColumnIndex(dbHelper.ASSIGN_COLUMN_MIN));

    }

    public String getDueTime() {
        if (dueMin < 10) {
            return (dueHour + ":0" + dueMin);
        }
        else {
            return (dueHour + ":" + dueMin);
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
