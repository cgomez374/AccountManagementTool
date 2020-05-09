package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FirstTimeLogin extends AppCompatActivity {

    FirebaseHelper firebaseHelper = new FirebaseHelper();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_login);

        //Open up Shared Pref
        sharedPreferences = getSharedPreferences("AccountMgmtPref", MODE_PRIVATE);

        //Call update of user list
        firebaseHelper.retrieveAllUsers();
    }

    public void changePassBtn(View view) {
        //Views
        TextView oldPass = (TextView) findViewById(R.id.oldPassEditText);
        TextView newPass = (TextView) findViewById(R.id.newPassEditText);
        TextView checkNew = (TextView) findViewById(R.id.confirmNewEditText);
        String newPassPattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{10,15})";

        //Check format of each field
        if(oldPass.getText().toString().isEmpty()) {
            oldPass.setError("Cannot Be Empty");
        }
        else if(newPass.getText().toString().isEmpty()) {
            newPass.setError("Cannot Be Empty");
        }
        else if(checkNew.getText().toString().isEmpty()) {
            checkNew.setError("Cannot Be Empty");
        }
        else if(!newPass.getText().toString().equals(checkNew.getText().toString())) {
            checkNew.setError("Does Not Match");
        }
        else if(newPass.getText().toString().length() < 10 || newPass.getText().toString().length() > 15) {
            newPass.setError("Too Short");
        }
        else if(!newPass.getText().toString().matches(newPassPattern)) {
            newPass.setError("Does Not Meet Requirements");
        }
        else {
            //Retrieve user if from shared pref
            String loggedUserId = sharedPreferences.getString("UserId", "default" );

            //Find user from firebase; check to see if old password matches firebase; update new password
            Users loggedUser = firebaseHelper.findUser(loggedUserId, oldPass.getText().toString());

            if(loggedUser == null) {
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
            }
            else if(!loggedUser.password.equals(oldPass.getText().toString())) {
                oldPass.setError("Incorrect Password");
            }
            else {
                //Toast.makeText(this, "User Found", Toast.LENGTH_SHORT).show();

                //Change user password; save to firebase; give success notification
                loggedUser.password = checkNew.getText().toString();
                firebaseHelper.updateUser(loggedUser);
                Toast.makeText(this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();

                //redirect to main sales page
                Intent mainSalesPage = new Intent(this, SalesMainPage.class);
                mainSalesPage.putExtra(loggedUserId, loggedUserId);
                startActivity(mainSalesPage);

            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent returnToMainAct = new Intent(this, MainActivity.class);
        startActivity(returnToMainAct);
    }
}
