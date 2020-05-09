package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SalesMainPage extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_main_page);

        sharedPreferences = getSharedPreferences("AccountMgmtPref", MODE_PRIVATE);

        //Grab Views
        TextView title = (TextView) findViewById(R.id.titleTextView);

        //Set the text to welcome user
        String name = sharedPreferences.getString("UserName", "default" );
        String welcome = "Welcome " + name + "!";

        //Set the title to include the current users name
        title.setText(welcome.toUpperCase());
    }

    public void newCustomer (View view) {
        //Add New Intent; Will redirect to the new customer menu
        Intent newCustomerMenu = new Intent(this, NewCustomerMenu.class);

        //Start New Activity
        startActivity(newCustomerMenu);
    }

    public void existingCustomer (View view) {
        Toast.makeText(this, "Redirect to existing customer menu", Toast.LENGTH_SHORT).show();

        //Add new Intent
    }

    @Override
    public void onBackPressed() {

        Intent returnToMainAct = new Intent(this, MainActivity.class);

        startActivity(returnToMainAct);
    }
}
