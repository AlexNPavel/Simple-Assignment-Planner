package com.example.alex.contactstest;

import android.database.Cursor;

/**
 * Created by alex on 6/25/15. Add new header later.
 */

public class Course {

    private final int ID;
    private final String name;
    private final int startHour;
    private final int startMin;
    private final int endHour;
    private final int endMin;
    private final String professor;

    public Course(int courseID, CourseDBHelper dbHelper) {

        Cursor res = dbHelper.getCourse(courseID);

        ID = courseID;

        res.moveToFirst();

        name = res.getString(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_NAME));
        startHour = res.getInt(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_START_HOUR));
        startMin = res.getInt(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_START_MIN));
        endHour = res.getInt(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_END_HOUR));
        endMin = res.getInt(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_END_MIN));
        professor = res.getString(res.getColumnIndex(CourseDBHelper.COURSES_COLUMN_PROF));
    }

    public String getStartTime() {
        if (startMin < 10) {
            return (startHour + ":0" + startMin);
        }
        else {
            return (startHour + ":" + startMin);
        }
    }

    public String getEndTime() {
        if (endMin < 10) {
            return (endHour + ":0" + endMin);
        }
        else {
            return (endHour + ":" + endMin);
        }
    }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }

    public int getID() { return ID; }

}
