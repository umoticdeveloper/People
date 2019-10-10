package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    //Variable definition
    Button btnSignUp, btnSignIn;
    Intent goToRegistration, goToMain;
    EditText email, password;
    CheckBox rememberMe;
    String checkbox;
    public static Activity loginActivity;

    private static final String LOGIN_URL = "http://localhost/androidtest/GetUserData.php";
    public static final String KEY_MAIL = "user_mail";
    public static final String KEY_PASSWORD = "user_password";
    private String usermail, passwordStr;



    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;


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





    private void userLogin(){
        usermail = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "no success", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(KEY_MAIL, usermail);
                map.put(KEY_PASSWORD, passwordStr);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }







}
