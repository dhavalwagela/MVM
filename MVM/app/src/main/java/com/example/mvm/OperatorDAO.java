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
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public OperatorDAO( Context context) {
        super(context,dbname , null, 1);
    }

    public Cursor getAllVehiclesWithAssignedOperatorAndLocation(String vehicleId, String locationId, String date) {
        db = this.getWritableDatabase();

        String[] columns = new String[]{"vehicleId", "username", "locationId", "startTime", "endTime"};
        String condition = "";
        if (vehicleId != null)
            condition+= "vehicleId = '"+vehicleId+"'";
        if (locationId != null) {
            if (condition.length() > 0)
                condition+=" or ";
            condition+= "locationId = '"+locationId+"'";
        }
        Cursor cursor = db.query("VehicleOperatorAndLocation", columns, "date = '"+date+"'"+(condition.length() > 0 ? " and "+condition : "") , null, null, null, null);
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
        Cursor cursor = db.query("tbl_registerUser", columns, "userType = 'Operator'", null, null, null, null);
        return cursor;
    }
    public Cursor getVehicles() {
        db = this.getWritableDatabase();
        String[] columns = new String[]{"vehicleId", "description"};
        Cursor cursor = db.query("Vehicle", columns, null, null, null, null, null);
        return cursor;
    }
    public Cursor getLocations() {
        db = this.getWritableDatabase();
        String[] columns = new String[]{"locationId", "description"};
        Cursor cursor = db.query("Location", columns, null, null, null, null, null);
        return cursor;
    }
    public boolean canAssignOperator(String username, String vehicleId, String date) {
        db = this.getWritableDatabase();
        Cursor cursor = db.query("VehicleOperatorAndLocation", null, " username = '"+username+"' and date = '"+date+"' and vehicleId != '"+vehicleId+"'" , null, null, null, null);
        if (cursor != null && cursor.getCount() > 0)
            return false;
        return true;
    }
    public void assignOperator(String username, String vehicleId, String date) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query("VehicleOperatorAndLocation", null, " vehicleId = '"+vehicleId+"' and date = '"+date+"'" , null, null, null, null);
        cv.put("username", username);
        cv.put("date", date);
        cv.put("vehicleId", vehicleId);
        if (cursor.getCount() > 0)
            db.update("VehicleOperatorAndLocation", cv, "vehicleId = '"+vehicleId+"' and date = '"+date+"'" , null );
        else
            db.insert("VehicleOperatorAndLocation", null, cv);
    }
    public boolean canAssignLocation(String vehicleId) {
        db = this.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        Cursor cursor = db.query("VehicleOperatorAndLocation", null, " vehicleId = '"+vehicleId+"' and date = '"+simpleDateFormat.format(tomorrow)+"'" , null, null, null, null);
        if (cursor != null && cursor.getCount() > 0)
            return true;
        return false;
    }
    public void assignLocation(String locationId, String vehicleId, String startTime, String endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("locationId", locationId);
        cv.put("startTime", startTime);
        cv.put("endTime", endTime);
        cv.put("vehicleId", vehicleId);
        db.update("VehicleOperatorAndLocation", cv, "vehicleId = '"+vehicleId+"' and date = '"+simpleDateFormat.format(tomorrow)+"'" , null );
    }
    public Cursor getTimeSlot(String locationId) {
        db = this.getWritableDatabase();
        String[] columns = new String[]{"startTime", "endTime"};
        Cursor cursor = db.query("LocationDuration", columns, "locationId = '"+locationId+"'", null, null, null, null);
        return cursor;
    }
    public Cursor getVehicleInventory(String vehicleId) {
        db = this.getWritableDatabase();
        String condition = "vehicleId = '"+vehicleId+"'";
        Cursor cursor = db.query("Inventory", null, condition, null, null, null, null);
        return cursor;
    }
    public void updateInventory() {
        db = this.getWritableDatabase();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='Inventory'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() <= 0) {
            return;
        }
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
