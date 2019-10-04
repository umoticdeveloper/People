package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;




public class LoginActivity extends AppCompatActivity {

    //Variable definition
    Button btnSignUp, btnSignIn;
    Intent goToRegistration, goToMain;
    EditText email, password;
    CheckBox rememberMe;
    String checkbox;

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
        rememberMe = (CheckBox)findViewById(R.id.rememberMeCheck);

        SharedPreferences checkPref = getSharedPreferences("rememberMe", MODE_PRIVATE);
        checkbox = checkPref.getString("remember", "");

        if(checkbox.equals("true")) {
            startActivity(goToMain);
            finish();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToRegistration);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    //check if remember me is already true
                    //if not:
                        //put boolean in shared pref and check for email and password to log in
                    //else:
                        //start main activity
                    SharedPreferences checkBoxPref = getSharedPreferences("rememberMe", MODE_PRIVATE);
                    SharedPreferences.Editor editorCheckBox = checkBoxPref.edit();
                    editorCheckBox.putString("remember", "true");
                    editorCheckBox.apply();

                }else {
                    SharedPreferences checkBoxPref = getSharedPreferences("rememberMe", MODE_PRIVATE);
                    SharedPreferences.Editor editorCheckBox = checkBoxPref.edit();
                    editorCheckBox.putString("remember", "false");
                    editorCheckBox.apply();
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check data for login from DB



                startActivity(goToMain);
                finish();
            }
        });

    }
}
