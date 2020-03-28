package com.example.mvm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AssignOperatorScreen extends AppCompatActivity {

    private Button button;
    public Spinner operatorName;
    public Spinner vehicleName;

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
        alertBuilder = new AlertDialog.Builder(AssignOperatorScreen.this);
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
        setContentView(R.layout.activity_assign_operator_screen);

        Spinner spinner = findViewById(R.id.vehicle_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssignOperatorScreen.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vehicle_name));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner1 = findViewById(R.id.operator_spinner);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AssignOperatorScreen.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.operator_name));

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner1.setAdapter(adapter1);
        TextView dateText = findViewById(R.id.tomrw_date);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateText.setText(simpleDateFormat.format(tomorrow));

        button = (Button) findViewById(R.id.assign_oprtr);
        operatorName = findViewById(R.id.operator_spinner);
        vehicleName = findViewById(R.id.vehicle_spinner);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                Dialog dialog = new Dialog(AssignOperatorScreen.this);
                dialog.setContentView(R.layout.activity_dialog);
                TextView textViewUser = (TextView) dialog.findViewById(R.id.textBrand);
                textViewUser.setText("Vehicle: "+vehicleName.getSelectedItem().toString() +"\n"+"Assigned Operator: "+operatorName.getSelectedItem().toString());
                Button okButton = dialog.findViewById(R.id.ok);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });
                dialog.show();
            }
        });
    }


}
