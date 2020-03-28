package com.example.mvm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ManagerHomeScreen extends AppCompatActivity {
    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        MenuItem cartItem = menu.getItem(4);
        cartItem.setIcon(0);
        cartItem.setTitle("");
        cartItem.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_home_screen);
    }
    public void viewAvailableVehicles(View view) {
        startActivity(new Intent(this,ViewAvailableVehiclesActivity.class));
    }

    public void viewOperators(View view) {
        startActivity(new Intent(this,ViewOperators.class));
    }
    public void contactUs(View view) {
        startActivity(new Intent(this,ContactUsActivity.class));
    }

    public void viewProfile(View view) {startActivity(new Intent(this,ViewProfile.class));}
    public void searchVendingVehicle(View view) {startActivity(new Intent(this,SearchVendingVehicleManager.class));}
    AlertDialog.Builder alertBuilder;
    public void onLogoutClick(final Context context) {
        alertBuilder = new AlertDialog.Builder(ManagerHomeScreen.this);
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
}