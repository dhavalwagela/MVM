package com.example.mvm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewVehicleInventoryManager extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        MenuItem cartItem = menu.getItem(4);
        cartItem.setIcon(0);
        cartItem.setTitle("");
        cartItem.setEnabled(false);
        return true;
    }
    AlertDialog.Builder alertBuilder;
    public void onLogoutClick(final Context context) {
        alertBuilder = new AlertDialog.Builder(ViewVehicleInventoryManager.this);
        alertBuilder.setTitle("Confirm Logout");
        alertBuilder.setMessage("Are you sure you want to logout ?");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(context,MainActivity.class));
                dialogInterface.dismiss();
            }
        });
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                onLogoutClick(getApplicationContext());
                return true;
            case R.id.home:
                startActivity(new Intent(this,ManagerHomeScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle_inventory_manager);
        Intent receiverIntent = getIntent();
        OperatorDAO optDb = new OperatorDAO(this);
        OrderDAO orderDb = new OrderDAO(this);
        String location = receiverIntent.getStringExtra("selectedLocationId");
        String vehicle = receiverIntent.getStringExtra("selectedVehicleId");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date today = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(today);
        Cursor vehicleInventory = optDb.getVehicleInventory(vehicle);
        while (vehicleInventory.moveToNext()) {
            String itemId = vehicleInventory.getString(vehicleInventory.getColumnIndex("itemId"));
            if (itemId.equals("DRINKS"))
                ((TextView) findViewById(R.id.drinksQuantity)).setText(vehicleInventory.getString(vehicleInventory.getColumnIndex("quantity")));
            else if (itemId.equals("SNACKS"))
                ((TextView) findViewById(R.id.snacksQuantity)).setText(vehicleInventory.getString(vehicleInventory.getColumnIndex("quantity")));
            else
                ((TextView) findViewById(R.id.sandwitchQuantity)).setText(vehicleInventory.getString(vehicleInventory.getColumnIndex("quantity")));
        }
        ((TextView) findViewById(R.id.location_id)).setText(optDb.getDescription("location", location));
        int duration = Integer.parseInt(receiverIntent.getStringExtra("selectedEndTime")) - (Integer.parseInt(receiverIntent.getStringExtra("selectedStartTime")));
        ((TextView) findViewById(R.id.duration)).setText(String.valueOf(duration));
        ((TextView) findViewById(R.id.operator)).setText(receiverIntent.getStringExtra("selectedOperator"));
        String revenue = orderDb.getOrderRevenue(vehicle, location, date);
        ((TextView) findViewById(R.id.revenue)).setText("$ "+revenue);
        vehicleInventory.close();
    }
}
