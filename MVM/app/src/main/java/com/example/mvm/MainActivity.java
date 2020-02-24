package com.example.mvm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewLink);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void checkValidUser(View view) {
        username = (EditText) findViewById(R.id.userText);
        password = (EditText) findViewById(R.id.pwdText);
        SQLiteDatabase sqldb = this.openOrCreateDatabase("VendingVehicleMachine.db", MODE_PRIVATE, null);
        Cursor cursor = sqldb.rawQuery("select name FROM sqlite_master WHERE type='table' AND name='tbl_registerUser'", null);
        if(cursor.getCount() > 0) {
            String query = "Select * from tbl_registerUser where username = '" + username.getText().toString().trim() + "' and password = '" + password.getText().toString().trim() + "'";
            cursor = sqldb.rawQuery(query, null);
            if (cursor.getCount() <= 0) {
                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                username.setText("");
                password.setText("");
                cursor.close();
            } else {
                String data = "User";
                if (cursor.moveToFirst()) {
                    data = cursor.getString(cursor.getColumnIndex("usertype"));
                }
                cursor.close();
                if (data.equals("Manager")) {
                    startActivity(new Intent(this, ManagerHomeScreen.class));
                } else if (data.equals("Operator")) {
                    startActivity(new Intent(this, OperatorHomeScreen.class));
                } else
                    startActivity(new Intent(this, UserHomeScreen.class));
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
            cursor.close();
        }
    }
    public void registerDetails(View view) {
        startActivity(new Intent(this,Register.class));
    }
    public void forgotPassword(View view) {
        startActivity(new Intent(this,ForgotPasswordScreen.class));
    }
}
