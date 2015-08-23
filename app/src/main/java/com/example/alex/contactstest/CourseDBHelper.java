package com.example.alex.contactstest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 8/21/15.
 */
public class CourseDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "planner.db";
    public static final int DATABASE_VERSION = 1;
    public static final String COURSES_TABLE_NAME = "courses";
    public static final String COURSES_COLUMN_ID = "id";
    public static final String COURSES_COLUMN_NAME = "name";
    public static final String COURSES_COLUMN_START_HOUR = "start_hour";
    public static final String COURSES_COLUMN_START_MIN = "start_min";
    public static final String COURSES_COLUMN_END_HOUR = "end_hour";
    public static final String COURSES_COLUMN_END_MIN = "end_min";
    public static final String COURSES_COLUMN_PROF = "prof";

    public static final String ASSIGN_TABLE_NAME = "assignments";
    public static final String ASSIGN_COLUMN_ID = "id";
    public static final String ASSIGN_COLUMN_NAME = "name";
    public static final String ASSIGN_COLUMN_YEAR = "year";
    public static final String ASSIGN_COLUMN_MONTH = "month";
    public static final String ASSIGN_COLUMN_DAY = "day";
    public static final String ASSIGN_COLUMN_HOUR = "hour";
    public static final String ASSIGN_COLUMN_MIN = "min";

    public static final String LINK_TABLE_NAME = "link";
    public static final String LINK_COLUMN_ID = "id";
    public static final String LINK_COURSES_ID = "course_id";
    public static final String LINK_ASSIGN_ID = "assign_id";

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String DOT = ".";
    private static final String EQUALS = " = ";
    private static final String AND = " AND ";

    public static final String CREATE_COURSE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + COURSES_TABLE_NAME + "(" +
            COURSES_COLUMN_ID + INTEGER_TYPE+" PRIMARY KEY," +
            COURSES_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
            COURSES_COLUMN_START_HOUR + INTEGER_TYPE + COMMA_SEP +
            COURSES_COLUMN_START_MIN + INTEGER_TYPE + COMMA_SEP +
            COURSES_COLUMN_END_HOUR + INTEGER_TYPE + COMMA_SEP +
            COURSES_COLUMN_END_MIN + INTEGER_TYPE + COMMA_SEP +
            COURSES_COLUMN_PROF + TEXT_TYPE +");";

    public static final String CREATE_ASSIGN_ENTRIES = "CREATE TABLE IF NOT EXISTS " + ASSIGN_TABLE_NAME + "(" +
            ASSIGN_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY," +
            ASSIGN_COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
            ASSIGN_COLUMN_YEAR + INTEGER_TYPE + COMMA_SEP +
            ASSIGN_COLUMN_MONTH + INTEGER_TYPE + COMMA_SEP +
            ASSIGN_COLUMN_DAY + INTEGER_TYPE + COMMA_SEP +
            ASSIGN_COLUMN_HOUR + INTEGER_TYPE + COMMA_SEP +
            ASSIGN_COLUMN_MIN + INTEGER_TYPE +");";

    public static final String CREATE_LINK_ENTRIES = "CREATE TABLE IF NOT EXISTS " + LINK_TABLE_NAME + "(" +
            LINK_COURSES_ID + INTEGER_TYPE + COMMA_SEP +
            LINK_ASSIGN_ID + INTEGER_TYPE + ");";

    public CourseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COURSE_ENTRIES);
        sqLiteDatabase.execSQL(CREATE_ASSIGN_ENTRIES);
        sqLiteDatabase.execSQL(CREATE_LINK_ENTRIES);
    }

    public boolean insertCourseData(String name, int startHour, int startMin, int endHour, int endMin, String prof) {
        SQLiteDatabase db = this.getWritableDatabase();
        /**
        db.execSQL("INSERT INTO " + COURSES_TABLE_NAME + "(" + COURSES_COLUMN_NAME + COMMA_SEP +
                COURSES_COLUMN_START_HOUR + COMMA_SEP + COURSES_COLUMN_START_MIN + COMMA_SEP +
                COURSES_COLUMN_END_HOUR + COMMA_SEP + COURSES_COLUMN_END_MIN + COMMA_SEP +
                COURSES_COLUMN_PROF + ") VALUES (" + "\"" + name + "\"" + COMMA_SEP + startHour + COMMA_SEP + startMin +
                COMMA_SEP + endHour + COMMA_SEP + endMin + COMMA_SEP + "\"" + prof + "\"" +");");
         **/
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSES_COLUMN_NAME, name);
        contentValues.put(COURSES_COLUMN_START_HOUR, startHour);
        contentValues.put(COURSES_COLUMN_START_MIN, startMin);
        contentValues.put(COURSES_COLUMN_END_HOUR, endHour);
        contentValues.put(COURSES_COLUMN_END_MIN, endMin);
        contentValues.put(COURSES_COLUMN_PROF, prof);
        db.insert(COURSES_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateCourseData(Integer id, String name, int startHour, int startMin, int endHour, int endMin, String prof) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSES_COLUMN_NAME, name);
        contentValues.put(COURSES_COLUMN_START_HOUR, startHour);
        contentValues.put(COURSES_COLUMN_START_MIN, startMin);
        contentValues.put(COURSES_COLUMN_END_HOUR, endHour);
        contentValues.put(COURSES_COLUMN_END_MIN, endMin);
        contentValues.put(COURSES_COLUMN_PROF, prof);
        db.update(COURSES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean insertAssignmentData(String name, int dueYear, int dueMonth, int dueDay, int dueHour, int dueMin, int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        /**
        db.execSQL("INSERT INTO " + ASSIGN_TABLE_NAME + "(" + ASSIGN_COLUMN_NAME + COMMA_SEP +
                ASSIGN_COLUMN_YEAR + COMMA_SEP + ASSIGN_COLUMN_MONTH + COMMA_SEP +
                ASSIGN_COLUMN_DAY + COMMA_SEP + ASSIGN_COLUMN_HOUR + COMMA_SEP +
                ASSIGN_COLUMN_MIN + ") VALUES (" + "\"" + name + "\"" + COMMA_SEP + dueYear +
                COMMA_SEP + dueMonth + COMMA_SEP + dueDay + COMMA_SEP + dueHour + COMMA_SEP +
                dueMin + ");");
        db.execSQL("INSERT INTO " + LINK_TABLE_NAME + "(" + LINK_COURSES_ID + COMMA_SEP + LINK_ASSIGN_ID +
                    ") VALUES (" + courseID + COMMA_SEP + assignID + ");");
         **/
        ContentValues contentValues = new ContentValues();
        contentValues.put(ASSIGN_COLUMN_NAME, name);
        contentValues.put(ASSIGN_COLUMN_YEAR, dueYear);
        contentValues.put(ASSIGN_COLUMN_MONTH, dueMonth);
        contentValues.put(ASSIGN_COLUMN_DAY, dueDay);
        contentValues.put(ASSIGN_COLUMN_HOUR, dueHour);
        contentValues.put(ASSIGN_COLUMN_MIN, dueMin);
        db.insert(ASSIGN_TABLE_NAME, null, contentValues);
        //db.close();
        //db = this.getWritableDatabase();
        int assignID = getAssignID(name);
        contentValues = new ContentValues();
        contentValues.put(LINK_COURSES_ID, courseID);
        contentValues.put(LINK_ASSIGN_ID, assignID);
        db.insert(LINK_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateAssignmentData(String name, int dueYear, int dueMonth, int dueDay, int dueHour, int dueMin, int courseID) {
        int assignID = getAssignID(name);
        int linkID = getLinkID(courseID, assignID);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ASSIGN_COLUMN_NAME, name);
        contentValues.put(ASSIGN_COLUMN_YEAR, dueYear);
        contentValues.put(ASSIGN_COLUMN_MONTH, dueMonth);
        contentValues.put(ASSIGN_COLUMN_DAY, dueDay);
        contentValues.put(ASSIGN_COLUMN_HOUR, dueHour);
        contentValues.put(ASSIGN_COLUMN_MIN, dueMin);
        db.update(ASSIGN_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(assignID)});
        contentValues = new ContentValues();
        contentValues.put(LINK_COURSES_ID, courseID);
        contentValues.put(LINK_ASSIGN_ID, assignID);
        db.update(LINK_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(linkID)});
        return true;
    }

    private int getCourseID(String course) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+COURSES_COLUMN_ID + " from " + COURSES_TABLE_NAME +
                                    " where " + COURSES_COLUMN_NAME + EQUALS + course, null);
        res.moveToFirst();
        return res.getInt(res.getColumnIndex(COURSES_COLUMN_ID));
    }

    private int getAssignID(String assignment) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+ASSIGN_COLUMN_ID + " from " + ASSIGN_TABLE_NAME +
                " where " + ASSIGN_COLUMN_NAME + EQUALS + "\"" + assignment + "\"" + ";", null);
        res.moveToFirst();
        return res.getInt(res.getColumnIndex(ASSIGN_COLUMN_ID));
    }

    private int getLinkID (int courseID, int assignID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + LINK_COLUMN_ID + " from " + LINK_TABLE_NAME +
                " where " + LINK_COURSES_ID + EQUALS + courseID + AND + LINK_ASSIGN_ID + EQUALS + assignID, null);
        res.moveToFirst();
        return res.getInt(res.getColumnIndex(LINK_COLUMN_ID));
    }

    public Cursor getCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + COURSES_TABLE_NAME, null );
        return res;
    }

    public Cursor getCourse(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + COURSES_TABLE_NAME + " where " +
                COURSES_COLUMN_ID + EQUALS + id + ";", null );
        return res;
    }

    public Cursor getAssignment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + ASSIGN_TABLE_NAME + " where " +
                ASSIGN_COLUMN_ID + EQUALS + id + ";", null );
        return res;
    }

    public Cursor getAssignments() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + ASSIGN_TABLE_NAME, null );
        return res;
    }

    public Cursor getCourseAssignments(Integer courseID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + LINK_ASSIGN_ID + " from " +
                LINK_TABLE_NAME + COMMA_SEP + COURSES_TABLE_NAME + COMMA_SEP + ASSIGN_TABLE_NAME + " where " +
                COURSES_TABLE_NAME + DOT + COURSES_COLUMN_ID + EQUALS + Integer.toString(courseID) + AND +
                COURSES_TABLE_NAME + DOT + COURSES_COLUMN_ID + EQUALS + LINK_TABLE_NAME + DOT + LINK_COURSES_ID + AND +
                ASSIGN_TABLE_NAME + DOT + ASSIGN_COLUMN_ID + EQUALS + LINK_TABLE_NAME + DOT + LINK_ASSIGN_ID, null);
        return res;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
