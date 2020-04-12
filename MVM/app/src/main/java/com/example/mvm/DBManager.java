package com.example.mvm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBManager extends SQLiteOpenHelper
{

    private static final String dbname = "VendingVehicleMachine.db";
    public DBManager( Context context) {
        super(context,dbname , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table tbl_registerUser(id integer primary key autoincrement, firstname text, " +
                "lastname text, username text,password text,usertype text,email text,phone text,address text," +
                "city text,state text,zipcode text,secques text, secans text)";

        /*String qry = "create table tbl_registerUsers(id integer primary key autoincrement, firstname text, " +
                "lastname text, username text,password text,usertype text)";*/
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_registerUser");
        onCreate(db);
    }

    public String addRecord(String p1, String p2, String p3,String p4,String p5,String p6,
                            String p7,String p8,String p9, String p10, String p11,String p12, String p13)

    //public String addRecord(String p1, String p2, String p3,String p4,String p5)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstname",p1);
        cv.put("lastname",p2);
        cv.put("username",p3);
        cv.put("password",p4);
        cv.put("usertype",p5);
        cv.put("email",p6);
        cv.put("phone",p7);
        cv.put("address",p8);
        cv.put("city",p9);
        cv.put("state",p10);
        cv.put("zipcode",p11);
        cv.put("secques",p12);
        cv.put("secans",p13);

        long res = db.insert("tbl_registerUser", null,cv );
        if(res== -1)
            return "failed";
        else
            return "Account Created Successfully";

    }
}
