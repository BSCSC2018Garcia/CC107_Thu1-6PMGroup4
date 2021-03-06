package com.bscs.group4_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
              DB.execSQL("create Table  Userdetails(studno TEXT primary key, name TEXT, contact TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
                DB.execSQL("drop table if exists Userdetails");

    }
    public  Boolean insertuserdata(String studno,  String name, String contact, String dob) {
        SQLiteDatabase DB = this.getReadableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put("studno",studno);
        contentValues.put("name",name);
        contentValues.put("contact", contact);
        contentValues.put("dob",dob);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result==-1) {
            return  false;
        } else {
            return true;
        }
    }

    public Boolean updateuserdata(String studno, String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where studno = ?", new String[]{studno});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "studno=?", new String[]{studno});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}



    public Boolean deletedata (String studno)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where studno = ?", new String[]{studno});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "studno=?", new String[]{studno});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }



    public  Cursor getdata() {
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);

        return  cursor;

    }


}
