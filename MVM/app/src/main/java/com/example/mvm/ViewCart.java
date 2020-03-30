package com.example.mvm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewCart extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        return true;
    }
    AlertDialog.Builder alertBuilder;
    public void onLogoutClick(final Context context) {
        alertBuilder = new AlertDialog.Builder(ViewCart.this);
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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        TextView vehicleId = findViewById(R.id.vehicle_id);
        vehicleId.setText(vehicleId.getText()+"Truck 1");
        TextView locationId = findViewById(R.id.location_id);
        locationId.setText(locationId.getText()+"Spaniolo & W 1st");
        TableLayout ll = findViewById(R.id.table_layout);

        List<List<String>> list = new ArrayList<>();
        List<String> child = new ArrayList<>();
        child.add("1");
        child.add("Drinks");
        child.add("2");
        child.add("$3.00");
        list.add(child);

        List<String> child1 = new ArrayList<>();
        child1.add("2");
        child1.add("Snacks");
        child1.add("4");
        child1.add("$5.00");
        list.add(child1);

        List<String> child2 = new ArrayList<>();
        child2.add("3");
        child2.add("Sandwiches");
        child2.add("1");
        child2.add("$5.75");
        list.add(child2);

        for (int i = 2; i <= list.size() + 1; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            List<String> orders = list.get(i - 2);
            int in = 0;
            final String orderId = orders.get(0);
            for (String order : orders) {
                TextView textView = new TextView(this);
                textView.setText(order);
                textView.setWidth(310);
                textView.setTextSize(16);
                if (in == 3 || in == 2) {
                    textView.setWidth(340);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                }
                else
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(textView);
                in++;
            }
            ll.addView(row, i);
            row.setClickable(true);
        }

    }
    public void checkout(View view) {
        startActivity(new Intent(this,Checkout.class));
    }
}
