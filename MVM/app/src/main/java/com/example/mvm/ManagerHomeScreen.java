package com.example.mvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ManagerHomeScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_home_screen);
    }
    public void logout(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}