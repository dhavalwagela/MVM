package com.example.mvm;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        TableLayout ll = findViewById(R.id.table_layout);

        List<List<String>> list = new ArrayList<>();
        List<String> child = new ArrayList<>();
        child.add("1");
        child.add("Drinks");
        child.add("2");
        child.add("$4.00");
        list.add(child);

        List<String> child1 = new ArrayList<>();
        child1.add("2");
        child1.add("Snacks");
        child1.add("3");
        child1.add("$9.00");
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
        }
    }
}
