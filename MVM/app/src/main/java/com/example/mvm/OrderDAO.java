package com.example.mvm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDAO extends SQLiteOpenHelper {
    private static final String dbname = "VendingVehicleMachine.db";
    public OrderDAO( Context context) {
        super(context,dbname , null, 1);
    }
    public String getOrderRevenue(String vehicleId, String locationId, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("Orders", null, "vehicleId = '"+vehicleId+"' and locationId = '"+locationId+"' and orderDate = '"+date+"'", null, null, null, null);
        while(cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("grandTotal"));
        }
        return "0.00";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
