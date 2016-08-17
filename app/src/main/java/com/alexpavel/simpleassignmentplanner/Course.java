package com.alexpavel.simpleassignmentplanner;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;

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
    private final Context context;

    public Course(int courseID, CourseDBHelper dbHelper, Context context) {
        this.context = context;
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
        if (!DateFormat.is24HourFormat(context)) {
            if (startHour == 12) {
                if (startMin < 10) {
                    return "12:0" + startMin + " PM";
                } else {
                    return "12:" + startMin + " PM";
                }
            }
            else if (startHour == 0) {
                if (startMin < 10) {
                    return "12:0" + startMin + " AM";
                } else {
                    return "12:" + startMin + " AM";
                }
            }
            else if (startHour > 12) {
                if (startMin < 10) {
                    return (startHour - 12) + ":0" + startMin + " PM";
                } else {
                    return (startHour - 12) + ":" + startMin + " PM";
                }
            }
            else {
                if (startMin < 10) {
                    return startHour + ":0" + startMin + " AM";
                } else {
                    return startHour + ":" + startMin + " AM";
                }
            }
        }
        else {
            if (startMin < 10) {
                return startHour + ":0" + startMin;
            } else {
                return startHour + ":" + startMin;
            }
        }
    }

    public String getEndTime() {
        if (!DateFormat.is24HourFormat(context)) {
            if (endHour == 12) {
                if (startMin < 10) {
                    return "12:0" + endMin + " PM";
                } else {
                    return "12:" + endMin + " PM";
                }
            }
            else if (endHour == 0) {
                if (endMin < 10) {
                    return "12:0" + endMin + " AM";
                } else {
                    return "12:" + endMin + " AM";
                }
            }
            else if (endHour > 12) {
                if (endMin < 10) {
                    return (endHour - 12) + ":0" + endMin + " PM";
                } else {
                    return (endHour - 12) + ":" + endMin + " PM";
                }
            }
            else {
                if (endMin < 10) {
                    return endHour + ":0" + endMin + " AM";
                } else {
                    return endHour + ":" + endMin + " AM";
                }
            }
        }
        else {
            if (endMin < 10) {
                return endHour + ":0" + endMin;
            } else {
                return endHour + ":" + endMin;
            }
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
