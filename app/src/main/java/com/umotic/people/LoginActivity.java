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
    Button btnSignUp, btnSignIn;
    Intent goToRegistration, goToMain;
    EditText email, password;

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
        goToMain = new Intent(this, MainActivity.class);
        btnSignUp = (Button)findViewById(R.id.btnSignUpLogin);
        btnSignIn = (Button)findViewById(R.id.btnSignInLogin);
        email = (EditText)findViewById(R.id.emailLogin);
        password = (EditText)findViewById(R.id.passwordLogin);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToRegistration);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        //Check data for login
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToMain);
            }
        });

    }
}
