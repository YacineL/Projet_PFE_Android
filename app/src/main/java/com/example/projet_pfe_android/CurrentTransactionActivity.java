package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CurrentTransactionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_transaction);
        getSupportActionBar().setTitle("Transaction");
    }
}
