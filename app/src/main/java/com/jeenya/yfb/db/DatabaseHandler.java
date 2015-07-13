package com.jeenya.yfb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hp on 7/7/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


   // private Context context;
    private static final String DATABASE_NAME = "BloodDonar";
    private static final String TABLE_NAME = "Contacts";
    private static final int DATABASE_VERSION = 2;

    // Tables Columns in the Databse
    private static final String UID = "_id";
    private static final String NAME = "NAME";
    private static final String NUMBER = "NUMBER";
    private static final String BLOOD = "BLOOD";


    //Query to create Database
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME + " TEXT,"
            + NUMBER + " TEXT,"
            // + BLOOD + " TEXT,"
            // + PRICE + " TEXT,"
            //  + URL + " TEXT,"
            // + OPER + " TEXT,"
            // + PID + " TEXT,"
            + BLOOD + " TEXT);";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       //this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
            //Toast.makeText(context, "OnCreate in DatabaseCreater", Toast.LENGTH_SHORT).show();

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    //Saving Data To Database
    public void phoneToDb(String name, String no, String blood) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, name);
        values.put(NUMBER, no);
        values.put(BLOOD, blood);

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        Log.d("Inserting to database", "Successfull");
        db.close(); // Closing database connection
    }

    public Cursor getCursor() {

        Cursor cursor = null;
        try {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + UID + " DESC";
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }


    public void clearLogs() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_NAME);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<UserDataFromDb> retriveUserData() {

        ArrayList<UserDataFromDb> contactList = new ArrayList<UserDataFromDb>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + UID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                // looping through all rows and adding to list
                // Log.d("Checking Id in Receiving section", "before move to first");

                // Log.d("Checking Id in Receiving section", "after move to first");
                do {
                    UserDataFromDb sd = new UserDataFromDb();
                    sd.setId(cursor.getInt(0));
                    Log.d("Receiving section", cursor.getString(0));

                    sd.setName(cursor.getString(1));
                    Log.d("Receiving section", cursor.getString(1));

                    sd.setPhNumber(cursor.getString(2));
                    Log.d("Receiving section", cursor.getString(2));

                    sd.setBloodGroup(cursor.getString(3));
                    Log.d(" Receiving section", cursor.getString(3));


                    // Adding contact to list
                    contactList.add(sd);

                } while (cursor.moveToNext());
            }
        }

        // return contact list
        return contactList;
    }


    // Deleting single Location
    public void deleteItem(int iD) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, UID + " = ?",
                new String[]{String.valueOf(iD)});
        db.close();
    }


    public long getNoOfItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        Long numRows = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        Log.d("No of Rows", String.valueOf(numRows));
        return numRows;
    }


    public boolean CheckIsDataAlreadyInDBorNot(String fieldValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + NUMBER + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // Updating single contact
    public void updateContact(String n, String p, String b, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, n);
        values.put(NUMBER, p);
        values.put(BLOOD, b);

        // updating row
        db.update(TABLE_NAME, values, UID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

}
