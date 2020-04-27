package com.example.mvm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewVehicleInventoryUser extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private Button button;
    private String drinksTotalQuantity, snacksTotalQuantity, sandwitchesTotalQuantity;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        return true;
    }
    AlertDialog.Builder alertBuilder;
    public void onLogoutClick(final Context context) {
        alertBuilder = new AlertDialog.Builder(ViewVehicleInventoryUser.this);
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
            case R.id.cart:
                startActivity(new Intent(this,ViewCart.class));
                return true;
            case R.id.home:
                startActivity(new Intent(this,UserHomeScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle_inventory_user);
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
            if (itemId.equals("DRINKS")) {
                drinksTotalQuantity = vehicleInventory.getString(vehicleInventory.getColumnIndex("quantity"));
                ((TextView) findViewById(R.id.drinksQuantity)).setText(drinksTotalQuantity);
            } else if (itemId.equals("SNACKS")) {
                snacksTotalQuantity = vehicleInventory.getString(vehicleInventory.getColumnIndex("quantity"));
                ((TextView) findViewById(R.id.snacksQuantity)).setText(snacksTotalQuantity);
            } else {
                sandwitchesTotalQuantity = vehicleInventory.getString(vehicleInventory.getColumnIndex("quantity"));
                ((TextView) findViewById(R.id.sandwitchQuantity)).setText(sandwitchesTotalQuantity);
            }
        }
        ((TextView) findViewById(R.id.location_id)).setText(optDb.getDescription("location", location));
        int duration = Integer.parseInt(receiverIntent.getStringExtra("selectedEndTime")) - (Integer.parseInt(receiverIntent.getStringExtra("selectedStartTime")));
        ((TextView) findViewById(R.id.duration)).setText(String.valueOf(duration));

        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor session = sharedpreferences.edit();

                EditText sandwitchesText = findViewById(R.id.addSandwitchQuantity);
                EditText snacksText = findViewById(R.id.addSnacksQuantity);
                EditText drinksText = findViewById(R.id.addDrinkQuantity);

                int sandwitches = sandwitchesText.getText().length() <= 0 ? 0 : Integer.parseInt("" + sandwitchesText.getText());
                int drinks = drinksText.getText().length() <= 0 ? 0 : Integer.parseInt("" + drinksText.getText());
                int snacks = snacksText.getText().length() <= 0 ? 0 : Integer.parseInt("" + snacksText.getText());

                int totalSandwitches = Integer.parseInt(sandwitchesTotalQuantity);
                int totalDrinks = Integer.parseInt(drinksTotalQuantity);
                int totalSnacks = Integer.parseInt(snacksTotalQuantity);

                if (totalSandwitches < sandwitches || totalDrinks < drinks || totalSnacks < snacks) {
                    Toast.makeText(getApplicationContext(), "Invalid quantity of items", Toast.LENGTH_SHORT).show();
                }
                else {
                    session.putInt("sandwitches", sandwitches);
                    session.putInt("drinks", drinks);
                    session.putInt("snacks", snacks);
                    session.putInt("subtotal", sandwitches+drinks+snacks);
                    session.putFloat("grandTotal", (float) (8.75 + snacks + drinks + sandwitches));
                    session.putBoolean("cart", true);
                }

            }
        });

        vehicleInventory.close();

    }
}
