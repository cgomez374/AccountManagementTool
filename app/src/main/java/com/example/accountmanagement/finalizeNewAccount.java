package com.example.accountmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class finalizeNewAccount extends AppCompatActivity {

    //Edit Text
    EditText pin;
    EditText pinConfirm;
    EditText sqAnswer;

    //Spinner
    Spinner sqSpinner;
    ArrayAdapter<String> sqListArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_new_account);

        //Edit Texts
        pin = (EditText) findViewById(R.id.pinEditText);
        pinConfirm = (EditText) findViewById(R.id.confirmEditText);
        sqAnswer = (EditText) findViewById(R.id.answerEditText);

        //Spinner
        sqSpinner = findViewById(R.id.sqSpinner);
        sqListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.security_questions));
        sqListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sqSpinner.setAdapter(sqListArrayAdapter);


    }
}
