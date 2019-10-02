package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    //Variable definition
    Button btnSignIn, btnSignUp;
    EditText email, password, name, surname;
    Intent gotToSexActivity, goToLoginActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Variable references
        btnSignIn = (Button)findViewById(R.id.btnSignInRegistration);
        btnSignUp = (Button)findViewById(R.id.btnSignUpRegistration);
        email = (EditText)findViewById(R.id.emailRegistration);
        password = (EditText)findViewById(R.id.passwordRegistration);
        name = (EditText)findViewById(R.id.nameRegistration);
        surname = (EditText)findViewById(R.id.surnameRegistration);
        gotToSexActivity = new Intent(this, SexSelectorActivity.class);
        goToLoginActivity = new Intent(this, LoginActivity.class);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToLoginActivity);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            //add user info in shared preference to save them later in DB at the end of registration process.
            @Override
            public void onClick(View view) {
                /*
                if(name.getText().toString().isEmpty()) {
                    String[] values = {null,null,password.getText().toString(),null,null,email.getText().toString()};
                    new SharedManager(getApplicationContext()).writeInfoShared(values);
                }else {
                    String[] valuesDefault = {null,null,password.getText().toString(),name.getText().toString(),surname.getText().toString(),email.getText().toString()};
                    Log.d("Mail: ", email.getText().toString());
                    new SharedManager(getApplicationContext()).writeInfoShared(valuesDefault);
                }*/
                //passing data to SexActivity
                gotToSexActivity.putExtra("userMail", email.getText().toString())
                        .putExtra("userPassword", password.getText().toString())
                        .putExtra("userName", name.getText().toString())
                        .putExtra("userSurname", surname.getText().toString());
                startActivity(gotToSexActivity);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });




    }
}
