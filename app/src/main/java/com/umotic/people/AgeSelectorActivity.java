package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.umotic.people.helper.HttpJsonParser;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AgeSelectorActivity extends AppCompatActivity {

    //Variable definition
    IndicatorSeekBar indicatorAgeBar;
    ImageView imageView;
    Animation animFadeOut;
    Animation animFadeIn;
    FloatingActionButton goToMain;
    Intent goToMainActivity;
    SeekParams seekActualParams;
    String name, surname, email, password, ageRange = "[18-24]";
    int sex;

    //Insert user to DB Variables
    private static final String USER_NAME = "user_name";
    private static final String USER_SURNAME = "user_surname";
    private static final String USER_MAIL = "user_mail";
    private static final String USER_PASSWORD = "user_password";
    private static final String SUCCESS_KEY = "success";
    private static final String EMPTY_PARAM = "";
    private static final String BASE_URL = "http://peopleapp.altervista.org/DbPhpFiles/";
    private int success;
    private ProgressDialog pDialog;


    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_selector);

        //Variable references
        goToMainActivity = new Intent(this, MainActivity.class);
        imageView = (ImageView)findViewById(R.id.ageImage);
        indicatorAgeBar = (IndicatorSeekBar)findViewById(R.id.indicatorSeekBarAge);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right_quick);
        goToMain = (FloatingActionButton)findViewById(R.id.gotopermissionsplashscreen);

        //getting data from RegistrationActivity
        name = getIntent().getStringExtra("userName");
        surname = getIntent().getStringExtra("userSurname");
        email = getIntent().getStringExtra("userMail");
        password = getIntent().getStringExtra("userPassword");
        sex = getIntent().getIntExtra("userSex", -1);


        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("from registration sex: ", name + surname + email + password + ageRange);
                String[] values = {name, surname, email, password, sex + "", ageRange};
                new SharedManager(getApplicationContext()).writeInfoShared(values);

                new CreateNewUser().execute();
                //startActivity(goToMainActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });


        //Logic for seekbar and image transition
        indicatorAgeBar.setR2L(false);
        indicatorAgeBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if(seekParams.progress == -1){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_kid);
                    ageRange = "[15-18]";
                    Log.d("Età: ", "< 18");
                }else if(seekParams.progress == 24){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_young);
                    ageRange = "[18-24]";
                    Log.d("Età: ", "[18-24]");
                }else if(seekParams.progress == 50){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_adult);
                    ageRange = "[25-30]";
                    Log.d("Età: ", "[25-30]");
                }else if(seekParams.progress == 75){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_adult_old);
                    ageRange = "[31-40]";
                    Log.d("Età: ", "[31-40]");
                }else if(seekParams.progress == 100){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_old);
                    ageRange = "[40-60]";
                    Log.d("Età: ", "> 40");
                }

                seekActualParams=seekParams;
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }


    /**
     * ###################################################################################### PERMISSION METHODS #############################################################################################################################
     *
     */




    /**
     * ####################################################################################### CUSTOM METHODS #################################################################################################################
     *
     */


    private class CreateNewUser extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AgeSelectorActivity.this);
            pDialog.setMessage("Wait while your account is set up!");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //populating request params
            httpParams.put(USER_NAME, name);
            httpParams.put(USER_SURNAME, surname);
            httpParams.put(USER_MAIL, email);
            httpParams.put(USER_PASSWORD, password);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(BASE_URL + "InsertUSer.php", "POST", httpParams);

            try {
                success = jsonObject.getInt(SUCCESS_KEY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (success == 1) {
                        Toast.makeText(AgeSelectorActivity.this, "Your account has been registered correctly", Toast.LENGTH_SHORT).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about user insert
                        setResult(20, i);
                        //finish this activity and go to map activity
                        startActivity(goToMainActivity);
                        finish();
                    } else {
                        Toast.makeText(AgeSelectorActivity.this, "Some error occured during registration", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
