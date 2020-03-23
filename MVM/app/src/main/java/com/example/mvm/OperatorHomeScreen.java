package com.example.mvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class OperatorHomeScreen extends AppCompatActivity {
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        MenuItem cartItem = menu.getItem(4);
        cartItem.setIcon(0);
        cartItem.setTitle("");
        cartItem.setEnabled(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(this,MainActivity.class));
                return true;
            case R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operator_home_screen);
    }
    public void contactUs(View view) {
        startActivity(new Intent(this,ContactUsActivity.class));
    }
    public void viewCurrentOrders(View view) {
        startActivity(new Intent(this,CurrentOrders.class));
    }
    public void viewProfile(View view) {startActivity(new Intent(this,ViewProfile.class));}

}
