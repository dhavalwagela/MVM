package com.example.mvm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AssignLocationScreen extends AppCompatActivity {

    private Button button;
    private Spinner operatorName;
    private Spinner vehicleName;
    private String selectedOperator, selectedVehicleId, operatorAssignedDate;
    AlertDialog.Builder alertBuilder;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        MenuItem cartItem = menu.getItem(4);
        cartItem.setIcon(0);
        cartItem.setTitle("");
        cartItem.setEnabled(false);
        return true;
    }
    public void onLogoutClick(final Context context) {
        alertBuilder = new AlertDialog.Builder(AssignLocationScreen.this);
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
        setContentView(R.layout.activity_assign_location_screen);
        OperatorDAO optDb = new OperatorDAO(this);
        UserDAO userDb = new UserDAO(this);

        Cursor cursorForLocations = optDb.getLocations();

        final List<String> listOfLocations = new ArrayList<>();
        List<String> locationOfLocationNames = new ArrayList<>();

        Spinner vehicleSpinner = findViewById(R.id.vehicles_spinner1);
        Spinner locationSpinner = findViewById(R.id.location_spinner);

        Cursor cursorForVehicles = optDb.getVehicles();

        final List<String> listOfVehicles = new ArrayList<>();
        List<String> listOfVehicleNames = new ArrayList<>();

        while (cursorForLocations.moveToNext()) {
            listOfLocations.add(cursorForLocations.getString(cursorForLocations.getColumnIndex("locationId")));
            locationOfLocationNames.add(userDb.getUserFullName(cursorForLocations.getString(cursorForLocations.getColumnIndex("description"))));
        }
        if (listOfLocations.size() > 0) {
            ArrayAdapter<String> locationSpinnerAdapter = new ArrayAdapter<String>(AssignLocationScreen.this,
                    android.R.layout.simple_spinner_item, locationOfLocationNames);

            locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectedOperator = listOfLocations.get(0);
            locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView,
                                           View selectedItemView, int position, long id) {
                    selectedOperator = listOfLocations.get(position);
                }

                public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                }

            });
            locationSpinner.setAdapter(locationSpinnerAdapter);
        }
        while (cursorForVehicles.moveToNext()) {
            listOfVehicles.add(cursorForVehicles.getString(cursorForVehicles.getColumnIndex("vehicleId")));
            listOfVehicleNames.add(cursorForVehicles.getString(cursorForVehicles.getColumnIndex("description")));
        }
        if (listOfVehicles.size() > 0) {
            ArrayAdapter<String> vehicleSpinnerAdapter = new ArrayAdapter<String>(AssignLocationScreen.this,
                    android.R.layout.simple_spinner_item, listOfVehicleNames);

            vehicleSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectedVehicleId = listOfVehicles.get(0);

            vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView,
                                           View selectedItemView, int position, long id) {
                    selectedVehicleId = listOfVehicles.get(position);
                }

                public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                }

            });
            vehicleSpinner.setAdapter(vehicleSpinnerAdapter);
        }
        TextView dateText = findViewById(R.id.tomrw_date);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        operatorAssignedDate = simpleDateFormat.format(tomorrow);
        dateText.setText(simpleDateFormat.format(tomorrow));

        button = (Button) findViewById(R.id.assignLocation);
        operatorName = findViewById(R.id.location_spinner);
        vehicleName = findViewById(R.id.vehicles_spinner1);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                Dialog dialog = new Dialog(AssignLocationScreen.this);
                dialog.setContentView(R.layout.activity_dialog);
                TextView textViewUser = (TextView) dialog.findViewById(R.id.textBrand);
                if (listOfLocations.size() > 0) {
                    textViewUser.setText("Vehicle: " + vehicleName.getSelectedItem().toString() + "\n" + "Assigned Operator: " + operatorName.getSelectedItem().toString());
                    Button okButton = dialog.findViewById(R.id.ok);
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            OperatorDAO optdb = new OperatorDAO(AssignLocationScreen.this);
                            optdb.assignOperator(selectedOperator, selectedVehicleId, operatorAssignedDate);
                            startActivity(getIntent());
                        }
                    });
                } else
                    textViewUser.setText("No operators available");
                dialog.show();
            }
        });
    }
}
