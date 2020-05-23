package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CreateNewAccount extends AppCompatActivity {

    //Views
    TextView firstCusName;
    TextView lastCusName;
    TextView cusSocial;
    TextView email;
    TextView phoneNum;

    //Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        //Grab Views
        firstCusName = (TextView) findViewById(R.id.firstNameEditText);
        lastCusName = (TextView) findViewById(R.id.lastNameEditText);
        cusSocial = (TextView) findViewById(R.id.socialEditText);
        email = (TextView) findViewById(R.id.emailEditText);
        phoneNum = (TextView) findViewById(R.id.phoneEditText);

        //Shared Preferences
        sharedPreferences = getSharedPreferences("AccountMgmtPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();


    }

    public void submitBtn(View view) {
        //Check information is in required format
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+.com";

        int ptnLength = phoneNum.getText().length();

        int socialLength = cusSocial.getText().length();

        //Checks any empty fields, makes sure email has @company.com pattern
        if(firstCusName.getText().toString().isEmpty()) {

            firstCusName.setError("Cannot Be Empty");
        }
        else if (!firstCusName.getText().toString().matches("[a-zA-Z]+")) {

            firstCusName.setError("Letters only");
        }
        if(lastCusName.getText().toString().isEmpty()) {

            lastCusName.setError("Cannot Be Empty");
        }
        else if (!lastCusName.getText().toString().matches("[a-zA-Z]+")) {

            lastCusName.setError("Letters only");
        }
        else if(email.getText().toString().isEmpty()) {

            email.setError("Cannot Be Empty");
        }
        else if(!email.getText().toString().trim().matches(emailPattern)) {
            email.setError("Invalid Email");
        }
        else if(phoneNum.getText().toString().isEmpty()) {

            phoneNum.setError("Cannot Be Empty");
        }
        else if(ptnLength != 10 || !phoneNum.getText().toString().matches("[0-9]+")) {
            phoneNum.setError("Format Error");
        }
        else if(socialLength != 9 || !cusSocial.getText().toString().matches("[0-9]+")) {
            cusSocial.setError("Format Error");
        }
        else if(Long.decode(cusSocial.getText().toString()) == 0) {
            cusSocial.setError("Cannot Be Zero");
        }
        else {
            //Redirect to address page
            Intent intent = new Intent(this, NewCustomerAddress.class);

            //Call method to save to shared preferences
            saveToSharedPref();

            //New activity
            startActivity(intent);
        }
    }

    private void saveToSharedPref() {

        String cusName = firstCusName.getText().toString() + " " + lastCusName.getText().toString();
        long custSocial =  Long.decode(cusSocial.getText().toString());
        long custPhoneNum =  Long.decode(phoneNum.getText().toString());

        editor.putString("custName", cusName.toUpperCase());
        editor.putString("custEmail", email.getText().toString().toUpperCase());
        editor.putLong("custSocial", custSocial);
        editor.putLong("custPhoneNum", custPhoneNum);

        //Commit Preferences writes
        editor.commit();

    }
}
