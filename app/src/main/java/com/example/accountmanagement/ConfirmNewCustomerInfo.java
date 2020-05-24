package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmNewCustomerInfo extends AppCompatActivity {

    //Firebase Helper
    FirebaseHelper firebaseHelper = new FirebaseHelper();

    //Shared Preferences
    SharedPreferences sharedPreferences;

    //Text Views
    TextView cusName;
    TextView cusEmail;
    TextView cusSocial;
    TextView cusPhone;
    TextView cusAddress;
    TextView message;

    //Attributes to create customer object
    String name;
    String email;
    String address;
    String social;
    String phoneNum;

    //Buttons active?
    Button creditBtn;
    Button saveBtn;
    Button shopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_new_customer_info);

        //Call firebase helper to update customer list
        firebaseHelper.retrieveAllCustomers();

        //Shared Preferences open file to read
        sharedPreferences = getSharedPreferences("AccountMgmtPref", MODE_PRIVATE);

        //Text Views
        cusName = (TextView) findViewById(R.id.nameTextView);
        cusEmail = (TextView) findViewById(R.id.emailTextView);
        cusSocial = (TextView) findViewById(R.id.socialTextView);
        cusPhone = (TextView) findViewById(R.id.phoneTextView);
        cusAddress = (TextView) findViewById(R.id.addressTextView);
        message = (TextView) findViewById(R.id.msgTextView);

        //Retrieve from shared pref
        name = sharedPreferences.getString("custName", "default");
        email = sharedPreferences.getString("custEmail", "default");
        address = sharedPreferences.getString("custAddress", "default");
        social = String.valueOf(sharedPreferences.getLong("custSocial", 0));
        phoneNum = String.valueOf(sharedPreferences.getLong("custPhoneNum", 0));

        //Set Text Views
        cusName.setText("Name:\n" + name);
        cusEmail.setText("Email:\n" + email);
        cusSocial.setText("Social Security:\n***-**-" + social.substring(social.length() - 4, social.length()));
        cusPhone.setText("Phone Number:\n(" + phoneNum.substring(0, 3) + ")" + phoneNum.substring(3, 6) + "-" + phoneNum.substring(phoneNum.length() - 4, phoneNum.length()));
        cusAddress.setText("Address:\n" + address);

        //Buttons active?
        creditBtn = findViewById(R.id.creditCheckButton);
        saveBtn = findViewById(R.id.saveAccountButton);
        shopBtn = findViewById(R.id.shopButton);

        saveBtn.setClickable(false);
        shopBtn.setClickable(false);


    }

    public void runCreditCheck(View view) {
        //Customers(String name, String email, String customerId, int pin, String sqAnswer, String address, long phoneNum, long socialSecurity)
        try{
            Customers newCustomer = new Customers(name, email, address, Long.decode(phoneNum), Long.decode(social));

            if(firebaseHelper.addNewCustomer(newCustomer) == true) {
                message.setText("Customer Profile Successfully Created");
                message.setTextColor(Color.parseColor("#00FF00"));

                //Set buttons to clickable in order to continue
                if(saveBtn.isClickable() == false && shopBtn.isClickable() == false) {
                    saveBtn.setClickable(true);
                    shopBtn.setClickable(true);
                }


            }
            else {
                message.setText("Customer Exists Already");
                message.setTextColor(Color.parseColor("#ff0000"));
            }

        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Method will assign the customer profile to a new account object
    public void saveButton(View view) {
        if(saveBtn.isClickable() == false) {
            Toast.makeText(this, "Complete Credit Check First", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, finalizeNewAccount.class);
            startActivity(intent);

        }
    }

    //Method will make sure account is saved first and then start shopping activity
    public void shopButton(View view) {
        if(shopBtn.isClickable() == false)
            Toast.makeText(this, "Save Account First", Toast.LENGTH_SHORT).show();
        else if(saveBtn.isClickable() == true) {
            Toast.makeText(this, "Start Shopping", Toast.LENGTH_SHORT).show();

        }
    }
}
