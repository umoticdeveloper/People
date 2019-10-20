package com.umotic.people;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


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

                try {
                    if (logIn()) {
                        startActivity(goToMain);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Mmm sembri nuovo, clicca su sign up!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        });

    }


    private boolean logIn() throws JSONException, ExecutionException, InterruptedException {

        boolean result;

        final String email = this.email.getText().toString();
        final String password = this.password.getText().toString();

        /*
	1 	ID (AI)
	2	user_sex
	3	user_age
	4	is_special_guest
	5	user_latitude
	6	user_longitude
	7	user_name
	8	user_surname
	9	user_password
	10	user_mail
*/
        JSONObject json = new JSONObject();

        json.put("user_password", "'" + password + "'");
        json.put("user_mail", "'" + email + "'");

        BKGWorker bkgw = new BKGWorker();

        String response = bkgw.execute("http://peopleapp.altervista.org/DbPhpFiles/GetUserData.php", json.toString()).get();
        JSONObject jresponse = new JSONObject(response);
        String loginResponse = jresponse.getString("response");

        Log.i("CONNECTION_INFO_LOGIN", response);

        if ("0".equals(loginResponse)) {

            String sg = jresponse.getString("is_special_guest");
            String name = jresponse.getString("user_name");
            String surname = jresponse.getString("user_surname");
            String sex = jresponse.getString("user_sex");
            String ageRange = jresponse.getString("user_age");
            String lon = jresponse.getString("user_longitude");
            String lat = jresponse.getString("user_latitude");


            String[] values = {name, surname, email, password, sex + "", ageRange};
            new SharedManager(this).writeInfoShared(values);
            new UserPositionManager(this).sharedLastUserPosition(lat, lon);

            result = true;
        } else {
            result = false;
            Log.e("CONNECTION_ERROR", response);
        }
        return result;
    }






}
