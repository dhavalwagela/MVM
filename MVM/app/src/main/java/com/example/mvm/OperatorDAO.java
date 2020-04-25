package com.example.mvm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OperatorDAO extends SQLiteOpenHelper {
    private static final String dbname = "VendingVehicleMachine.db";
    private SQLiteDatabase db;
    public OperatorDAO( Context context) {
        super(context,dbname , null, 1);
    }

    public Cursor getAllVehiclesWithAssignedOperatorAndLocation() {
        db = this.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String[] columns = new String[]{"vehicleId", "username", "locationId", "startTime", "endTime"};
        Cursor cursor = db.query("VehicleOperatorAndLocation", columns, "date < '"+ simpleDateFormat.format(tomorrow)+"' OR date is null" , null, null, null, null);
        return cursor;
    }
    public String getDescription(String table, String id) {
        db = this.getWritableDatabase();
        String[] columns = new String[]{"description"};
        Cursor cursor = db.query(table, columns, table+"Id = '"+id+"'", null, null, null, null);
        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex("description"));
        }
        return new String();
    }
    public Cursor getOperators() {
        db = this.getWritableDatabase();
        String[] columns = new String[]{"username"};
        Cursor cursor = db.query("tbl_registerUser", columns, "role = 'Operator'", null, null, null, null);
        return cursor;
    }
    public Cursor getVehicles() {
        db = this.getWritableDatabase();
        String[] columns = new String[]{"vehicleId", "description"};
        Cursor cursor = db.query("Vehicle", columns, null, null, null, null, null);
        return cursor;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
