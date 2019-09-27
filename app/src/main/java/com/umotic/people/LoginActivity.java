package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    //Variable definition
    FloatingActionButton goToSex;
    Button btnSignUp;
    Intent goToRegistration, goToSexActivity;
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
        goToRegistration = new Intent(this, RegistrationActivity.class);
        goToSexActivity = new Intent(this, SexSelectorActivity.class);
        btnSignUp = (Button)findViewById(R.id.btnSignUpLogin);
        goToSex = (FloatingActionButton)findViewById(R.id.gotosex);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToRegistration);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        goToSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToSexActivity);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }


}
