package com.example.projet_pfe_android;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button BtnSignUp = findViewById(R.id.BtnSignUp);
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);
                EditText paswword2 = findViewById(R.id.password2);



                if(password.getText().toString().toLowerCase().trim().equals(paswword2.getText().toString().toLowerCase().trim()))
                {
                    SharedPreferences ShredRef = SignUp.this.getSharedPreferences("Login", Context.MODE_PRIVATE);


                    SharedPreferences.Editor editor = ShredRef.edit();
                    editor.putString("UserName", username.getText().toString());
                    editor.putString("Password", paswword2.getText().toString());
                    editor.putBoolean("session", Boolean.TRUE);
                    editor.apply();
                    Toast.makeText(SignUp.this,"Create compte avec succés ",Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(SignUp.this, SignIn.class);

                    startActivity(homeIntent);
                    finish();
                }
                else             Toast.makeText(SignUp.this,"verfier password ",Toast.LENGTH_LONG).show();

            }
        });


    }
}
