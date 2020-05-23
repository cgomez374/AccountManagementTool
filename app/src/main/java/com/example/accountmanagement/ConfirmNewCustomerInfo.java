package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
        cusName.setText(name);
        cusEmail.setText(email);
        cusSocial.setText("***-**-" + social.substring(social.length() - 4, social.length()));
        cusPhone.setText("(" + phoneNum.substring(0, 3) + ")" + phoneNum.substring(3, 6) + "-" + phoneNum.substring(phoneNum.length() - 4, phoneNum.length()));
        cusAddress.setText(address);


    }

    public void createAccount(View view) {
        //Customers(String name, String email, String customerId, int pin, String sqAnswer, String address, long phoneNum, long socialSecurity)
        try{
            Customers newCustomer = new Customers(name, email, address, Long.decode(phoneNum), Long.decode(social));

            if(firebaseHelper.addNewCustomer(newCustomer) == true)
                message.setText("Customer Profile Successfully Created");
            else
                message.setText("Customer Profile Not Created");

            message.setVisibility(View.VISIBLE);
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
