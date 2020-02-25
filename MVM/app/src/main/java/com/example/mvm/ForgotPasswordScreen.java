package com.example.mvm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordScreen extends AppCompatActivity {
    String email, newPassword, confirmPassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_screen);
    }
    public void resetPassword(View view) {
        email = ((EditText) findViewById(R.id.confirmEmail)).getText().toString().trim();
        newPassword = ((EditText) findViewById(R.id.newPassword)).getText().toString().trim();
        confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString().trim();
        if (newPassword.equals(confirmPassword)) {
            SQLiteDatabase sqldb = this.openOrCreateDatabase("VendingVehicleMachine.db", MODE_PRIVATE, null);
            String queryForCheckingEmail = "Select * from tbl_registerUser where email = '" + email +"'";
            Cursor cursor = sqldb.rawQuery(queryForCheckingEmail, null);
            if(cursor.getCount() <= 0) {
                Toast.makeText(getApplicationContext(),"This emailid doesn't belong to any registered user",Toast.LENGTH_SHORT).show();
                ((EditText) findViewById(R.id.confirmEmail)).setText("");
                ((EditText) findViewById(R.id.newPassword)).setText("");
                ((EditText) findViewById(R.id.confirmPassword)).setText("");
            } else {
                DBManager db = new DBManager(this);
                ContentValues cv = new ContentValues();
                cv.put("password",newPassword);
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.update("tbl_registerUser", cv, "email='"+email+"'", null);
                Toast.makeText(getApplicationContext(),"Reset Password Successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
            }
        } else {
            Toast.makeText(getApplicationContext(),"Passwords' fields doesn't match",Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.confirmEmail)).setText("");
            ((EditText) findViewById(R.id.newPassword)).setText("");
            ((EditText) findViewById(R.id.confirmPassword)).setText("");
        }
    }
}
