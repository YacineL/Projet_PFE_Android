package com.example.projet_pfe_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;


public class SignIn extends AppCompatActivity {
    EditText edtuser,edtpass;
    Button btnSignIn;
    private TextView configuration;
    private ProgressDialog progress;
    private SharedPreferences ShredRef;
    private boolean success;
    private String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtuser=  findViewById(R.id.edtuser);
        edtpass=  findViewById(R.id.edtpass);
        configuration=  findViewById(R.id.configuration);
        btnSignIn= (Button) findViewById(R.id.btnSignIn);
        SharedPreferences ShredRef ;

        configuration.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent homeIntent = new Intent(SignIn.this, SignUp.class);
           startActivity(homeIntent);
            finish();

        }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences ShredRefsign = getSharedPreferences("Login", Context.MODE_PRIVATE);

                        String user = ShredRefsign.getString("UserName", StringUtils.EMPTY);
                        String mot_pass = ShredRefsign.getString("Password",StringUtils.EMPTY);
                        boolean session = ShredRefsign.getBoolean("session", Boolean.FALSE);

                           if (
                                   session
                                        ||
                                   (
                                       user != null
                                               &&
                                       mot_pass != null
                                               &&
                                       user.toLowerCase().trim().equals(edtuser.getText().toString().toLowerCase().trim())
                                               &&
                                       mot_pass.toLowerCase().trim().equals(edtpass.getText().toString().toLowerCase().trim())
                                   )
                                )
                            {
                               SharedPreferences.Editor editor = ShredRefsign.edit();
                               editor.putBoolean("session", Boolean.TRUE);
                               editor.apply();
                               Intent homeIntent = new Intent(SignIn.this, MainActivity.class);
                               Toast.makeText(SignIn.this, "Login succeffuly", Toast.LENGTH_LONG).show();
                               startActivity(homeIntent);
                               finish();
                            }



                     else {
                        Context context = getApplicationContext();
                        CharSequence text = ("User or password are incorrect !");
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    }




        });


    }



    }




