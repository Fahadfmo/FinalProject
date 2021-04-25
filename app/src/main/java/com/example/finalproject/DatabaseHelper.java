package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "Users.db";
    public static final String TABLE_NAME = "users";

    public static final String COLUMN_ID = "userId";
    public static final String COLUMN_FNAME = "fName";
    public static final String COLUMN_LNAME = "lName";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";

    // create database once this class is called
    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    //once database is created, create the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE " + TABLE_NAME + "("
                                + COLUMN_ID + " TEXT PRIMARY KEY,"
                                + COLUMN_FNAME + " TEXT,"
                                + COLUMN_LNAME + " TEXT,"
                                + COLUMN_PHONE + " TEXT,"
                                + COLUMN_EMAIL + " TEXT"
                                + ");"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AddUser(String id, String fName, String lName, String phone, String email) {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();

        // insert values that user pass to the method into the table columns
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_FNAME, fName);
        values.put(COLUMN_LNAME, lName);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EMAIL, email);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor ViewList()
    {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor x = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return x;
    }

    public void DeleteUser(String id) {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME + " WHERE " + COLUMN_ID + " = "+id+"");
        db.close();
    }

    public void UpdateUser(String id, String fName, String lName, String phone, String email){
        DeleteUser(id);
        AddUser(id, fName, lName, phone, email);
    }
}