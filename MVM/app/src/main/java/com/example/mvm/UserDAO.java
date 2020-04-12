package com.example.mvm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDAO extends SQLiteOpenHelper {
    private static final String dbname = "VendingVehicleMachine.db";
    public UserDAO( Context context) {
        super(context,dbname , null, 1);
    }
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String[] columns = new String[]{"id", "firstname", "lastname", "username", "usertype", "email", "phone", "address", "city", "state", "zipcode", "secques", "secans"};
        Cursor cursor = db.query("tbl_registerUser", columns, "username = '"+username+"'", null, null, null, null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
