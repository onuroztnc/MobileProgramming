package com.example.mobileprogramming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Course_DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "COURSE_database";

    private static final String TABLE_NAME = "courseInfo";
    private static String PERSON_ID  ="ID";
    private static String COURSE_NAME = "Course_Name";
    private static String TEACHER = "Teachner_Name";
    private static String HARFNOTU = "Harf_Notu";

    public Course_DB(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE  " + TABLE_NAME + "("
                + PERSON_ID + " TEXT PRIMARY KEY ,"
                + COURSE_NAME + " TEXT,"
                + TEACHER + " TEXT,"
                + HARFNOTU + " TEXT "
                + ");";
        db.execSQL(CREATE_TABLE);

    }

    public long addCourse(String personID, String coursename,String teacherName, String harfNotu){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PERSON_ID, personID);
        values.put(COURSE_NAME, coursename);
        values.put(TEACHER, teacherName);
        values.put(HARFNOTU, harfNotu);

        long r =  db.insert(TABLE_NAME, null, values);

        if(r>-1)
            Log.i("tag","İşlem Başarılı");
        else
            Log.e("tag","İşlem Başarısız");
        db.close();

        return r;

    }

    public ArrayList<HashMap<String, String>> getAllCourse(String personId){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id="+personId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                courseList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();

        return courseList;
    }

    public void deleteCourse(String personId){

        SQLiteDatabase db = this.getWritableDatabase();
        int flag = db.delete(TABLE_NAME, PERSON_ID + " =?",
                new String[] { personId });

        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion > 1){
            Log.i("Buraya gliyor5", "Onur");
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            this.onCreate(db);
        }

    }
}
