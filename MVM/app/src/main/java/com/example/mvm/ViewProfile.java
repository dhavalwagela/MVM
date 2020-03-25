package com.example.mvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
    }
    public void logout(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
/*    public void change_password(View view) {
        startActivity(new Intent(this,ChangePassword.class));
    }*/
    public void update_profile(View view) {
        startActivity(new Intent(this,UpdateProfile.class));
    }
}
