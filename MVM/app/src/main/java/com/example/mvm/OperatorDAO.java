package com.example.mvm;

import android.content.ContentValues;
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
    public void updateInventory() {
        db = this.getWritableDatabase();
        String currentDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Cursor cursor = db.query("Inventory", null, "thruDate < '"+currentDate+"'", null, null, null, null);

        while (cursor.moveToNext()) {
            ContentValues cv = new ContentValues();
            String vehicleType = cursor.getString(cursor.getColumnIndex("vehicleType"));
            String itemId = cursor.getString(cursor.getColumnIndex("itemId"));
            String vehicleId = cursor.getString(cursor.getColumnIndex("vehicleId"));
            if (vehicleType.equals("Truck")) {
                if (itemId.equals("SNACKS")) {
                    cv.put("quantity", 40);
                } else if (itemId.equals("DRINKS")) {
                    cv.put("quantity", 50);
                } else
                    cv.put("quantity", 35);
            } else {
                if (itemId.equals("SNACKS")) {
                    cv.put("quantity", 30);
                } else if (itemId.equals("DRINKS")) {
                    cv.put("quantity", 30);
                } else
                    cv.put("quantity", 5);
            }
            cv.put("itemId", itemId);
            cv.put("vehicleType", vehicleType);
            cv.put("vehicleId", vehicleId);
            cv.put("placeQuantity",0);
            cv.put("thruDate", currentDate);
            String whereClause = "itemId = '"+itemId+"' and vehicleType = '"+vehicleType+"' and vehicleId = '"+vehicleId+"'";
            db.update("Inventory", cv, whereClause , null );
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
