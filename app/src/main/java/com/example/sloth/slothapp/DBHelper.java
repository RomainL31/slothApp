package com.example.sloth.slothapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = DBHelper.class.getSimpleName();

    public static final String DB_NAME = "sloth.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password"; // Change to encrypted password!

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  USER_TABLE);
        onCreate(db);
    }

    // Store users information in DB
    public void addUsers(String email, String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "user inserted " + id);
    }

    public boolean getUser(String email, String password) {
        String selectQuery = "select * from " + USER_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'"
                + " and " +
                COLUMN_PASSWORD + " = " + "'"+password+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }

        cursor.close();
        db.close();
        return false;
    }
}
