package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    FirebaseHelper firebaseHelper = new FirebaseHelper();
    TextView userId;
    TextView userPass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            //Update Firebase user list
            firebaseHelper.retrieveAllUsers();

            //Open shared Pref file
            sharedPreferences = getSharedPreferences("AccountMgmtPref", MODE_PRIVATE);

            //Prepare to edit shared pref file
            editor = sharedPreferences.edit();

            //Grab Views
            userId = (TextView) findViewById(R.id.userIdEditText);

            userPass = (TextView) findViewById(R.id.passwordEditText);
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void loginBtn(View view) {

        Users autUser = firebaseHelper.findUser(userId.getText().toString(), userPass.getText().toString());

        if(autUser == null) {
            Toast.makeText(this, "User ID or Password Incorrect", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

            //SharedPre writing to file
            editor.putString("UserId", autUser.userId);
            editor.putString("UserName", autUser.name);

            //Firs time accessing app?
            if(autUser.numberOfLoginSessions == 0) {
                //redirect to first time login to reset pass
                Intent intent = new Intent(this, FirstTimeLogin.class);

                startActivity(intent);
            }
            else {
                //redirect to main sales page
                Intent intent = new Intent(this, SalesMainPage.class);

                startActivity(intent);
            }

            //Update the number of sessions the user has and then update firebase
            autUser.numberOfLoginSessions++;
            firebaseHelper.updateUser(autUser);

            //Clear Screen
            userId.setText("");
            userPass.setText("");

            //Commit Preferences writes
            editor.commit();
        }

    }

    public void redirectToSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);

        //Clear Screen
        userId.setText("");
        userPass.setText("");
    }

}
