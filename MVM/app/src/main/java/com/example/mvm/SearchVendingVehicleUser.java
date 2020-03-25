package com.example.mvm;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SearchVendingVehicleUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(this,MainActivity.class));
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
        setContentView(R.layout.activity_search_vending_vehicle_user);
        TextView t1 = (TextView) findViewById(R.id.text1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner_next = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_name, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.vehicle_name, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner_next.setAdapter(adapter1);
        spinner_next.setOnItemSelectedListener(this);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void inventory_view(View view) {
        startActivity(new Intent(this,ViewVehicleInventoryUser.class));
    }
}
