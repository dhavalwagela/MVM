package com.example.mvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import static androidx.core.content.ContextCompat.startActivity;
public class Register extends AppCompatActivity {

    EditText firstNameText,lastNameText,usernameText,passwordText,emailText,phoneText,addressText,cityText,
            stateText,zipcodeText,secquestionText,secanswerText;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        firstNameText= (EditText)findViewById(R.id.firstNameText);
        lastNameText= (EditText)findViewById(R.id.lastNameText);
        usernameText= (EditText)findViewById(R.id.usernameText);
        passwordText= (EditText)findViewById(R.id.passwordText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        emailText= (EditText)findViewById(R.id.emailText);
        phoneText= (EditText)findViewById(R.id.phoneText);
        addressText= (EditText)findViewById(R.id.addressText);
        cityText= (EditText)findViewById(R.id.cityText);
        stateText= (EditText)findViewById(R.id.stateText);
        zipcodeText= (EditText)findViewById(R.id.zipcodeText);
        secquestionText= (EditText)findViewById(R.id.secquestionText);
        secanswerText= (EditText)findViewById(R.id.secanswerText);

    }

    public void addRecord(View view)
    {
        DBManager db = new DBManager(this);
        int radioId = radioGroup.getCheckedRadioButtonId();
        String rdtext = null;
        if(radioId != -1)
        {
            radioButton= findViewById(radioId);
            rdtext= (String) radioButton.getText();
        }



        String result= db.addRecord(firstNameText.getText().toString(),lastNameText.getText().toString(),
                usernameText.getText().toString(), passwordText.getText().toString(),rdtext,
                emailText.getText().toString(), phoneText.getText().toString(),addressText.getText().toString(),
                cityText.getText().toString(), stateText.getText().toString(),zipcodeText.getText().toString(),
                secquestionText.getText().toString(), secanswerText.getText().toString());

        /*String result= db.addRecord(firstNameText.getText().toString(),lastNameText.getText().toString(),
                usernameText.getText().toString(), passwordText.getText().toString(),rdtext);*/
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        if(result.equals("Success"))
            startActivity(new Intent(this,MainActivity.class));
        
        firstNameText.setText("");
        lastNameText.setText("");
        usernameText.setText("");
        passwordText.setText("");
        //radioButton.setText("");
        emailText.setText("");
        phoneText.setText("");
        addressText.setText("");
        cityText.setText("");
        stateText.setText("");
        zipcodeText.setText("");
        secquestionText.setText("");
        secanswerText.setText("");


    }
}
