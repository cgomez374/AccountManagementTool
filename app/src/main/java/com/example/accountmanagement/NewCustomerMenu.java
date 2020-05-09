package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewCustomerMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_menu);

    }

    public void newAccount(View view) {
        Intent newAccount = new Intent(this, CreateNewAccount.class);

        startActivity(newAccount);
    }

    public void purchaseAccesories(View view) {
        Intent accessories = new Intent(this, PurchaseAccessories.class);
        startActivity(accessories);
    }
}
