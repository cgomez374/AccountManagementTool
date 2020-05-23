package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NewCustomerAddress extends AppCompatActivity {

    //Edit Text Views
    EditText streetNumber;
    EditText streetName;
    EditText city;
    EditText state;
    EditText postalCode;

    //Connect to SharedPreferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_address);

        //Shared Preferences open file to read
        sharedPreferences = getSharedPreferences("AccountMgmtPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void submitBtnClicked(View view) {
        //Edit Text Views
        streetNumber = (EditText) findViewById(R.id.streetNumEditText);
        streetName = (EditText) findViewById(R.id.streetNameEditText);
        city = (EditText) findViewById(R.id.cityEditText);
        state = (EditText) findViewById(R.id.stateEditText);
        postalCode = (EditText) findViewById(R.id.zipCodeEditText);

        //Address
        String custAddress = " ";

        //Geocode in order to find address
        Geocoder geocoder = new Geocoder(this);

        //Save address
        List<Address> addressList = null;

        if(streetNumber.getText().toString().isEmpty()) {
            streetNumber.setError("Cannot Be Empty");
        }
        else if(streetName.getText().toString().isEmpty()) {
            streetName.setError("Cannot Be Empty");
        }
        else if(city.getText().toString().isEmpty()) {
            city.setError("Cannot Be Empty");
        }
        else if(state.getText().toString().isEmpty()) {
            state.setError("Cannot Be Empty");
        }
        else if(postalCode.getText().toString().isEmpty()) {
            postalCode.setError("Cannot Be Empty");
        }
        else {
            //Build into one string
            custAddress = streetNumber.getText().toString() + " " + streetName.getText().toString().toUpperCase()
                    + ", " + city.getText().toString().toUpperCase()
                    + ", " + state.getText().toString().toUpperCase()
                    + " " + postalCode.getText().toString().toUpperCase();
            try {
                addressList = geocoder.getFromLocationName(custAddress, 1);

                Address address = addressList.get(0);

            } catch (Exception e) {
                Toast.makeText(this, "Could Not Find Address", Toast.LENGTH_SHORT).show();
            }

        }

        //Write address to shared preferences
        editor.putString("custAddress", custAddress);
        editor.commit();

        //Start new activity to confirm customer information
        Intent intent = new Intent(this, ConfirmNewCustomerInfo.class);
        startActivity(intent);

    }
}
