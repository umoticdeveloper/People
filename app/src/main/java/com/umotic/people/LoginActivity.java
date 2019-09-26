package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    //Variable definition
    FloatingActionButton goToSex;
    Intent goToGpsSplashScreen;
    EditText email, password;
    SharedPreferences registrationData;



    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Variable references
        goToGpsSplashScreen = new Intent(this, GpsSplashActivity.class);
        goToSex = (FloatingActionButton)findViewById(R.id.gotosex);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        goToSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToGpsSplashScreen);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }


}
