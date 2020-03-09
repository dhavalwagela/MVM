package com.example.mvm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckBox;

public class MainActivity extends AppCompatActivity {

    /*TODO Need to make fields Mandatory*/
    TextView textView;
    EditText username, password;

    EditText edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewLink);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        @SuppressLint("WrongViewCast") AppCompatCheckBox checkbox = (AppCompatCheckBox) findViewById(R.id.checkBox);
        edtPassword = findViewById(R.id.pwdText);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            });
    }
    public void checkValidUser(View view) {
        username = findViewById(R.id.userText);
        password = findViewById(R.id.pwdText);
        if (username.getText().toString().trim().length() != 0 && password.getText().toString().trim().length() != 0) {
            SQLiteDatabase sqldb = this.openOrCreateDatabase("VendingVehicleMachine.db", MODE_PRIVATE, null);
            Cursor cursor = sqldb.rawQuery("select name FROM sqlite_master WHERE type='table' AND name='tbl_registerUser'", null);
            if (cursor.getCount() > 0) {
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
        } else {
            Toast.makeText(getApplicationContext(), "Enter required fields", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
        }
    }
    public void registerDetails(View view) {
        startActivity(new Intent(this,Register.class));
    }
    public void forgotPassword(View view) {
        startActivity(new Intent(this,ForgotPasswordScreen.class));
    }
}
