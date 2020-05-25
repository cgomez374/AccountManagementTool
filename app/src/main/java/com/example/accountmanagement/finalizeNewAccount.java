package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class finalizeNewAccount extends AppCompatActivity {

    //Edit Text
    EditText pin;
    EditText pinConfirm;
    EditText sqAnswer;

    //Spinner
    Spinner sqSpinner;
    String securityQuestionSelected = " ";
    ArrayAdapter<String> sqListArrayAdapter;

    //Customer
    Customers newCustomer = new Customers();

    //Firebase Helper
    FirebaseHelper firebaseHelper = new FirebaseHelper();

    //Shared Preferences
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_new_account);

        //Update Customer List
        firebaseHelper.retrieveAllCustomers();

        //Edit Texts
        pin = (EditText) findViewById(R.id.pinEditText);
        pinConfirm = (EditText) findViewById(R.id.confirmEditText);
        sqAnswer = (EditText) findViewById(R.id.answerEditText);

        //Spinner
        sqSpinner = findViewById(R.id.sqSpinner);
        sqListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.security_questions));
        sqListArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        sqSpinner.setAdapter(sqListArrayAdapter);

        //Shared Preferences
        sharedPreferences = getSharedPreferences("AccountMgmtPref", MODE_PRIVATE);


    }

    public void createAccount(View view) {

        //Grab selected security question
        securityQuestionSelected = sqSpinner.getSelectedItem().toString();

        if(pin.getText().toString().isEmpty())
            pin.setError("Cannot Be Empty");
        else if(pin.getText().toString().length() < 6)
            pin.setError("Too Short");
        else if(pin.getText().toString().length() > 10)
            pin.setError("Too Long");
        else if(pinConfirm.getText().toString().isEmpty())
            pinConfirm.setError("Cannot Be Empty");
        else if(!pin.getText().toString().equals(pinConfirm.getText().toString()))
            pinConfirm.setError("Pin Does Not Match");
        else if(securityQuestionSelected.isEmpty())
            Toast.makeText(this, "Must Choose Security Question", Toast.LENGTH_SHORT).show();
        else if(sqAnswer.getText().toString().isEmpty())
            sqAnswer.setError("Cannot Be Empty");
        else {
            //Find customer by shared preferences
            long custSocial = sharedPreferences.getLong("custSocial", 0);

            //Create customer object by finding new customer by using the shared pref saved info
            Customers customer = new Customers();

            try {
                customer.setCustomer(firebaseHelper.getCustomer(custSocial));
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            //Create New Account object
            Accounts newAccount = new Accounts(customer, securityQuestionSelected, sqAnswer.getText().toString(), Long.decode(pin.getText().toString()));

            //Upload to firebase
            if(firebaseHelper.addNewAccount(newAccount) == true){
                //redirect to shop activity
                Intent intent = new Intent(this, LinesToActivate.class);
                startActivity(intent);

            }


        }

    }
}
