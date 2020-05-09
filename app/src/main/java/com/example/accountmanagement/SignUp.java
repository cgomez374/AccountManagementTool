package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    FirebaseHelper firebaseHelper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        try{
            firebaseHelper.retrieveAllUsers();
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void addUserButton (View view) {

        //Find the views: first name, last name, email, and phone number
        TextView userFirstName = (TextView) findViewById(R.id.firstnameEditText2);

        TextView userLastName = (TextView) findViewById(R.id.lastnameEditText);

        TextView userEmail = (TextView) findViewById(R.id.emailEditText);

        TextView userPhoneNum = (TextView) findViewById(R.id.ptnEditText);

        String emailPattern = "[a-zA-Z0-9._-]+@company.com";

        int ptnLength = userPhoneNum.getText().length();

        //Checks any empty fields, makes sure email has @company.com pattern
        if(userFirstName.getText().toString().isEmpty()) {

            userFirstName.setError("Cannot Be Empty");
        }
        else if (!userFirstName.getText().toString().matches("[a-zA-Z]+")) {

            userFirstName.setError("Cannot Contain Numbers");
        }
        else if(userLastName.getText().toString().isEmpty()) {

            userLastName.setError("Cannot Be Empty");
        }
        else if (!userLastName.getText().toString().matches("[a-zA-Z]+")) {

            userLastName.setError("Cannot Contain Numbers");
        }
        else if(userEmail.getText().toString().isEmpty()) {

            userEmail.setError("Cannot Be Empty");
        }
        else if(!userEmail.getText().toString().trim().matches(emailPattern)) {
            userEmail.setError("Invalid Email");
        }
        else if(userPhoneNum.getText().toString().isEmpty()) {

            userPhoneNum.setError("Cannot Be Empty");
        }
        else if(ptnLength != 10 || !userPhoneNum.getText().toString().matches("[0-9]+")) {
            userPhoneNum.setError("Format Error");
        }
        else {

            String userName = userFirstName.getText().toString() + " " + userLastName.getText().toString();

            String userId = randomUserId();

            String userPassword = randomPassword();

            long sessions = 0;

            //Create the User
            Users newUser = new Users(userName, userEmail.getText().toString(), userId, userPassword, Long.decode(userPhoneNum.getText().toString()), sessions);

            //Call firebase helper functions
            if(firebaseHelper.addNewUser(newUser) == true) {
                Toast.makeText(this, "User Successfully Added!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Information Exists Already", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String randomUserId() {
        //User id will have to minimum 8 characters made up of numbers and letter
        // index one and two will be letters and the rest will be numbers

        //empty string
        String userId = "";

        //Fill index 1 and 2 with two letters
        // define the range
        int max = 122;
        int min = 97;
        int range = max - min + 1;

        // generate random numbers between 97 and 122 (ASCII), convert to char, add to userId
        for (int j = 0; j < 2; j++) {
            int rand = (int) (Math.random() * range) + min;
            char c = (char) rand;
            userId += c;
        }

        //generate random numbers from 1 -9, add to user ID
        // define the range
        max = 9;
        min = 1;
        range = max - min + 1;

        for (int k = 0; k < 6; k++) {
            int rand = (int) (Math.random() * range) + min;
            userId += rand;
        }

        //return
        return userId;
    }

    public String randomPassword() {
        //User id will have to minimum 8 characters made up of numbers and letter
        // index one and two will be letters and the rest will be numbers

        //empty string
        String password = "";

        //Fill index 1 and 2 with two letters
        // define the range
        int max = 125;
        int min = 33;
        int range = max - min + 1;

        // generate random numbers between 97 and 122 (ASCII), convert to char, add to userId
        for (int j = 0; j < 13; j++) {
            int rand = (int) (Math.random() * range) + min;
            char c = (char) rand;
            password += c;
        }

        //return
        return password;
    }

    @Override
    public void onBackPressed() {

        Intent returnToMainAct = new Intent(this, MainActivity.class);

        startActivity(returnToMainAct);
    }



}
