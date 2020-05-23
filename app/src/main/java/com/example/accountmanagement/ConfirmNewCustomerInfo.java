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

        //Retrieve from shared pref
        name = sharedPreferences.getString("custName", "default");
        email = sharedPreferences.getString("custEmail", "default");
        address = sharedPreferences.getString("custAddress", "default");
        social = String.valueOf(sharedPreferences.getLong("custSocial", 0));
        phoneNum = String.valueOf(sharedPreferences.getLong("custPhoneNum", 0));

        //Set Text Views
        cusName.setText("Name: \n\n" + name);
        cusEmail.setText("Email Address: \n\n" + email);
        cusSocial.setText("Social Security: \n\n***-**-" + social.substring(social.length() - 4, social.length()));
        cusPhone.setText("Phone Number: \n\n(" + phoneNum.substring(0, 3) + ")" + phoneNum.substring(3, 6) + "-" + phoneNum.substring(phoneNum.length() - 4, phoneNum.length()));
        cusAddress.setText("Address: \n\n" + address);


    }

//    public void createAccount(View view) {
//        //Customers(String name, String email, String customerId, int pin, String sqAnswer, String address, long phoneNum, long socialSecurity)
//        try{
//            Customers newCustomer = new Customers(name, email, address, phoneNum, social);
//
//            if(firebaseHelper.addNewCustomer(newCustomer) == true)
//                Toast.makeText(this, "Customer Successfully Created", Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(this, "Customer Already Exists", Toast.LENGTH_SHORT).show();
//        }
//        catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
}
