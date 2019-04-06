package com.example.mobileprogramming;



import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Person_DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 23;
    private static final String DATABASE_NAME = "person_database";//database adı

    private static final String TABLE_NAME = "personalInfo";
    private static String PERSON_ID  ="ID";
    private static String PERSON_NAME = "Name";
    private static String PERSON_SURNAME = "Surname";
    private static String PHONE_NUMBER = "Phone_Num";
    private static String BIRTH_DATE = "BDate";
    private static String AGE = "Age";
    private static String ADDRESS = "Address";
    private static String IMAGE = "ImageSrc";

    public Person_DB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATE_TABLE = "CREATE TABLE  " + TABLE_NAME + "("
                + IMAGE + " TEXT,"
                + PERSON_ID + " TEXT PRIMARY KEY ,"
                + PERSON_NAME + " TEXT,"
                + PERSON_SURNAME + " TEXT,"
                + AGE + " INTEGER,"
                + BIRTH_DATE + " TEXT,"
                + PHONE_NUMBER + " TEXT,"
                + ADDRESS + " TEXT"
                + ");";
        db.execSQL(CREATE_TABLE);
    }


    public void deletePerson(String personId){

        SQLiteDatabase db = this.getWritableDatabase();
        int flag = db.delete(TABLE_NAME, PERSON_ID + " =?",
                new String[] { personId });

        db.close();
    }

    public long addPerson(String img,String personID, String name,String surname,int age, String bDate, String phoneNum, String address){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE, img);
        values.put(PERSON_ID, personID);
        values.put(PERSON_NAME, name);
        values.put(PERSON_SURNAME, surname);
        values.put(AGE, age);
        values.put(BIRTH_DATE, bDate);
        values.put(PHONE_NUMBER, phoneNum);
        values.put(ADDRESS, address);

        long r =  db.insert(TABLE_NAME, null, values);

        if(r>-1)
            Log.i("tag","İşlem Başarılı");
        else
            Log.e("tag","İşlem Başarısız");
        db.close();

        return r;

    }

    public HashMap<String, String> getPerson(String personID){

        HashMap<String,String> person = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+personID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            person.put(IMAGE, cursor.getString(0));
            person.put(PERSON_ID, cursor.getString(1));
            person.put(PERSON_NAME, cursor.getString(2));
            person.put(PERSON_SURNAME, cursor.getString(3));
            person.put(AGE, cursor.getString(4));
            person.put(BIRTH_DATE, cursor.getString(5));
            person.put(PHONE_NUMBER, cursor.getString(6));
            person.put(ADDRESS, cursor.getString(7));
        }
        cursor.close();
        db.close();

        return person;
    }

    public  ArrayList<HashMap<String, String>> getAllPerson(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<HashMap<String, String>> personList = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                /*
                byte[] byteArr = cursor.getBlob(0);
                String temp = byteArr.toString();
                map.put(cursor.getColumnName(0), temp);
                */
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                personList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();

        return personList;
    }

    public void updatePerson(String personID, String name,String surname,int age, String phoneNum, String address, String bDate, String img) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMAGE, img);
        values.put(PERSON_ID, personID);
        values.put(PERSON_NAME, name);
        values.put(PERSON_SURNAME, surname);
        values.put(AGE, age);
        values.put(BIRTH_DATE, bDate);
        values.put(PHONE_NUMBER, phoneNum);
        values.put(ADDRESS, address);


        db.update(TABLE_NAME, values, PERSON_ID + " = ?",
                new String[] { personID });
    }

    public int getPersonCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        return rowCount;
    }


    public void deleteDB(){


        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

        if(arg1 > 19){
            Log.i("Buraya gliyor5", "Onur");
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            this.onCreate(db);
        }



    }

}
