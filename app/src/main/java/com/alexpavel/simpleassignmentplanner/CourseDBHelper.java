package com.alexpavel.simpleassignmentplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 8/21/15. Add new header later.
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

    public static final String TIMES_TABLE_NAME = "link";
    public static final String TIMES_COLUMN_ID = "id";
    public static final String TIMES_COLUMN_COURSE_ID = "course_id";
    public static final String TIMES_COLUMN_DOW = "dow";
    public static final String TIMES_COLUMN_SH = "sH";
    public static final String TIMES_COLUMN_SM = "sM";
    public static final String TIMES_COLUMN_EH = "eH";
    public static final String TIMES_COLUMN_EM = "eM";


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
            LINK_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY," +
            LINK_COURSES_ID + INTEGER_TYPE + COMMA_SEP +
            LINK_ASSIGN_ID + INTEGER_TYPE + ");";

    public static final String CREATE_TIMES_ENTRIES = "CREATE TABLE IF NOT EXISTS " + TIMES_TABLE_NAME + "(" +
            TIMES_COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY," +
            TIMES_COLUMN_COURSE_ID + INTEGER_TYPE + COMMA_SEP +
            TIMES_COLUMN_DOW + INTEGER_TYPE + COMMA_SEP +
            TIMES_COLUMN_SH + INTEGER_TYPE + COMMA_SEP +
            TIMES_COLUMN_SM + INTEGER_TYPE + COMMA_SEP +
            TIMES_COLUMN_EH + INTEGER_TYPE + COMMA_SEP +
            TIMES_COLUMN_EM + INTEGER_TYPE +");";

    public CourseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COURSE_ENTRIES);
        sqLiteDatabase.execSQL(CREATE_ASSIGN_ENTRIES);
        sqLiteDatabase.execSQL(CREATE_LINK_ENTRIES);
        sqLiteDatabase.execSQL(CREATE_TIMES_ENTRIES);
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
        db.close();
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
        db.close();
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
        int assignID = getLatestAssignID();
        contentValues = new ContentValues();
        contentValues.put(LINK_COURSES_ID, courseID);
        contentValues.put(LINK_ASSIGN_ID, assignID);
        db.insert(LINK_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean updateAssignmentData(String name, int dueYear, int dueMonth, int dueDay, int dueHour, int dueMin, int courseID, int assignID) {
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
        db.close();
        return true;
    }

    public void insertTimeData(int courseID, int dow, int startHour, int startMin, int endHour, int endMin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMES_COLUMN_COURSE_ID, courseID);
        contentValues.put(TIMES_COLUMN_DOW, dow);
        contentValues.put(TIMES_COLUMN_SH, startHour);
        contentValues.put(TIMES_COLUMN_SM, startMin);
        contentValues.put(TIMES_COLUMN_EH, endHour);
        contentValues.put(TIMES_COLUMN_EM, endMin);
        db.insert(TIMES_TABLE_NAME, null, contentValues);
        db.close();
    }

    public Cursor getTimeData(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TIMES_TABLE_NAME + " where " +
                TIMES_COLUMN_ID + EQUALS + ID + ";", null );
    }

    public Cursor getCourseTimeData(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select " + TIMES_COLUMN_ID+ " from " +
                TIMES_TABLE_NAME + COMMA_SEP + COURSES_TABLE_NAME + " where " +
                COURSES_TABLE_NAME + DOT + COURSES_COLUMN_ID + EQUALS + Integer.toString(ID) + AND +
                COURSES_TABLE_NAME + DOT + COURSES_COLUMN_ID + EQUALS + TIMES_TABLE_NAME + DOT + TIMES_COLUMN_COURSE_ID +
                ";", null);
    }

    public void updateTimeData(int ID, int dow, int startHour, int startMin, int endHour, int endMin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMES_COLUMN_DOW, dow);
        contentValues.put(TIMES_COLUMN_SH, startHour);
        contentValues.put(TIMES_COLUMN_SM, startMin);
        contentValues.put(TIMES_COLUMN_EH, endHour);
        contentValues.put(TIMES_COLUMN_EM, endMin);
        db.update(TIMES_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(ID)});
        db.close();
    }

    public void dropTimeData(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = TIMES_COLUMN_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(ID) };
        db.delete(TIMES_TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public int getLatestCourseID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+COURSES_COLUMN_ID + " from " + COURSES_TABLE_NAME + ";", null);
        res.moveToLast();
        return res.getInt(res.getColumnIndex(COURSES_COLUMN_ID));
    }

    private int getLatestAssignID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+ASSIGN_COLUMN_ID + " from " + ASSIGN_TABLE_NAME + ";", null);
        res.moveToLast();
        return res.getInt(res.getColumnIndex(ASSIGN_COLUMN_ID));
    }

    private int getLinkID (int courseID, int assignID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + LINK_COLUMN_ID + " from " + LINK_TABLE_NAME +
                " where " + LINK_COURSES_ID + EQUALS + courseID + AND + LINK_ASSIGN_ID + EQUALS + assignID + ";", null);
        res.moveToFirst();
        return res.getInt(res.getColumnIndex(LINK_COLUMN_ID));
    }

    public Cursor getCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + COURSES_TABLE_NAME, null);
    }

    public Cursor getCourse(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + COURSES_TABLE_NAME + " where " +
                COURSES_COLUMN_ID + EQUALS + id + ";", null );
    }

    public Cursor getAssignment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + ASSIGN_TABLE_NAME + " where " +
                ASSIGN_COLUMN_ID + EQUALS + id + ";", null );
    }

    public Cursor getAssignments() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + ASSIGN_TABLE_NAME, null );
    }

    public boolean deleteAssignment(int assignID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = LINK_ASSIGN_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(assignID) };
        db.delete(LINK_TABLE_NAME, whereClause, whereArgs);
        whereClause = ASSIGN_COLUMN_ID + "=?";
        whereArgs = new String[] { String.valueOf(assignID) };
        db.delete(ASSIGN_TABLE_NAME, whereClause, whereArgs);
        db.close();
        return true;
    }

    private boolean deleteAssignment(int assignID, SQLiteDatabase db) {
        String whereClause = LINK_ASSIGN_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(assignID) };
        db.delete(LINK_TABLE_NAME, whereClause, whereArgs);
        whereClause = ASSIGN_COLUMN_ID + "=?";
        whereArgs = new String[] { String.valueOf(assignID) };
        db.delete(ASSIGN_TABLE_NAME, whereClause, whereArgs);
        db.close();
        return true;
    }

    public boolean deleteCourse(int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = getCourseAssignments(courseID);
        res.moveToFirst();
        for (int i = 0; i < res.getCount(); i++) {
            deleteAssignment(res.getInt(res.getColumnIndex(CourseDBHelper.LINK_ASSIGN_ID)), db);
            res.moveToNext();
        }
        String whereClause = COURSES_COLUMN_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(courseID) };
        db.delete(COURSES_TABLE_NAME, whereClause, whereArgs);
        db.close();
        return true;
    }

    public Cursor getCourseAssignments(Integer courseID) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select " + LINK_ASSIGN_ID + " from " +
                LINK_TABLE_NAME + COMMA_SEP + COURSES_TABLE_NAME + COMMA_SEP + ASSIGN_TABLE_NAME + " where " +
                COURSES_TABLE_NAME + DOT + COURSES_COLUMN_ID + EQUALS + Integer.toString(courseID) + AND +
                COURSES_TABLE_NAME + DOT + COURSES_COLUMN_ID + EQUALS + LINK_TABLE_NAME + DOT + LINK_COURSES_ID + AND +
                ASSIGN_TABLE_NAME + DOT + ASSIGN_COLUMN_ID + EQUALS + LINK_TABLE_NAME + DOT + LINK_ASSIGN_ID + ";", null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
