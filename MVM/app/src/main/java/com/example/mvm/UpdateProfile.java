package com.example.mvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
    }
    public void update(View view) {
        Toast.makeText(getApplicationContext(), "Updated Details Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,ViewProfile.class));
    }
}
