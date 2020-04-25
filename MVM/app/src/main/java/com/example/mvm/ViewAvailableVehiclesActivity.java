package com.example.mvm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ViewAvailableVehiclesActivity extends AppCompatActivity {

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
        alertBuilder = new AlertDialog.Builder(ViewAvailableVehiclesActivity.this);
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
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_available_vehicles);
        OperatorDAO optDb = new OperatorDAO(this);
        UserDAO userDb = new UserDAO(this);
        TableLayout ll = findViewById(R.id.table_layout);
        Cursor cursor = optDb.getAllVehiclesWithAssignedOperatorAndLocation();

        int i = 2;
        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

                TextView textView = new TextView(this);
                textView.setText(optDb.getDescription("vehicle", cursor.getString(cursor.getColumnIndex("vehicleId"))));
                textView.setWidth(250);
                row.addView(textView);

                textView = new TextView(this);
                String username = cursor.getString(cursor.getColumnIndex("username"));
                if (username != null && username.length() > 0) {
                    textView.setText(userDb.getUserFullName(username));
                } else
                    textView.setText("John Wright");
                textView.setWidth(250);
                row.addView(textView);

                textView = new TextView(this);
                textView.setText(optDb.getDescription("location", cursor.getString(cursor.getColumnIndex("locationId"))));
                textView.setWidth(250);
                row.addView(textView);

                textView = new TextView(this);
                textView.setText(cursor.getString(cursor.getColumnIndex("startTime")) + ":00  -  " + cursor.getString(cursor.getColumnIndex("endTime"))+":00");
                textView.setWidth(550);
                row.addView(textView);

            ll.addView(row, i);
            i++;
        }
    }
    public void assignOperatorScreen(View view) {
        startActivity(new Intent(this,AssignOperatorScreen.class));
    }
}
