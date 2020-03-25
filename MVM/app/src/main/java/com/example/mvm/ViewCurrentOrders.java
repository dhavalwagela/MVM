package com.example.mvm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewCurrentOrders extends AppCompatActivity {

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
                startActivity(new Intent(this,OperatorHomeScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);
        TableLayout ll = findViewById(R.id.table_layout);

        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<String> child = new ArrayList<>();
            child.add("9932023");
            child.add("Bruce Wayne");
            child.add("Completed");
            child.add("03/09/2020");
            child.add("$12.00");
            list.add(child);
        }
        List<String> child = new ArrayList<>();
        child.add("9111");
        child.add("Bruce Wayne");
        child.add("Completed");
        child.add("03/09/2020");
        child.add("$12.00");
        list.add(child);

        for (int i = 2; i <= list.size()+1; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            List<String> orders = list.get(i-2);
            int in = 0;
            final String orderId = orders.get(0);
            for (String order : orders) {
                TextView textView = new TextView(this);
                textView.setText(order);
                if(in == 1)
                    textView.setWidth(300);
                else
                    textView.setWidth(265);
                if (in == 4) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                } else
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                row.addView(textView);
                in++;
            }
            ll.addView(row,i);
            row.setClickable(true);  //allows you to select a specific row

            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Dialog dialog = new Dialog(ViewCurrentOrders.this);
                    dialog.setContentView(R.layout.activity_dialog);
                    dialog.setTitle("Notification");
                    TextView textViewUser = (TextView) dialog.findViewById(R.id.textBrand);
                    Button hide = dialog.findViewById(R.id.assignLocation);
                    hide.setVisibility(View.GONE);
                    Button okButton = dialog.findViewById(R.id.ok);
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    textViewUser.setText(orderId);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
