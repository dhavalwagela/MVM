package com.example.mvm;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewOrders extends AppCompatActivity {

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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        TableLayout ll = findViewById(R.id.table_layout);

        List<List<String>> list = new ArrayList<>();
        List<String> child = new ArrayList<>();
        child.add("1234");
        child.add("Location 1");
        child.add("03/19/2020");
        child.add("9AM - 11AM");
        child.add("Completed");
        list.add(child);

        List<String> child1 = new ArrayList<>();
        child1.add("12345");
        child1.add("Location 2");
        child1.add("03/20/2020");
        child1.add("1PM - 5PM");
        child1.add("Pending");
        list.add(child1);
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
                textView.setWidth(70);
                if (in != 0) {
                    textView.setWidth(340);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                } else
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(textView);
                in++;
            }
            System.out.println(i);
            ll.addView(row, i);
            row.setClickable(true);

            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),OrderDetails.class);
                    startActivityForResult(intent,0);
                }
            });
        }
    }
}
