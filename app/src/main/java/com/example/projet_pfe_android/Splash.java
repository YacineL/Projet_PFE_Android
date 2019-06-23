package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000; //Splash Screen Will Display fro 15 Sec.
    private SharedPreferences ShredRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ShredRef= getSharedPreferences("Login", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                String user=ShredRef.getString("UserName","");
                String mot_pass=ShredRef.getString("Password","");
                if (user=="") {
                    Intent mainIntent = new Intent(Splash.this,SignIn.class);


                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
                else
                {
                    Intent mainIntent = new Intent(Splash.this,MainActivity.class);


                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}