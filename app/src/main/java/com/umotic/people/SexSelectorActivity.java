package com.umotic.people;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rd.PageIndicatorView;

import java.util.Timer;
import java.util.TimerTask;



public class SexSelectorActivity extends AppCompatActivity {

    //Variable definition
    ViewPager viewPager;
    ImageAdapter imageAdapter;
    FloatingActionButton goToAgeButton;
    Intent goToAgeSelectorActivity;
    TextView sexMessage;
    PageIndicatorView pageIndicatorView;
    Timer T;
    String name, surname, email, password;
    Button skipSex;
    public static Activity sex;


    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex_selector);
        sex = this;

        //Variable references
        goToAgeSelectorActivity = new Intent(this, AgeSelectorActivity.class);
        viewPager = (ViewPager)findViewById(R.id.sexPagerSelector);
        imageAdapter = new ImageAdapter(this);
        viewPager.setAdapter(imageAdapter);
        goToAgeButton = findViewById(R.id.gotoage);
        sexMessage = (TextView)findViewById(R.id.sexSelectorMessage);
        pageIndicatorView = (PageIndicatorView)findViewById(R.id.sexIndicatorView);
        skipSex = (Button)findViewById(R.id.btnSkipSex);


        //getting data from RegistrationActivity
        name = getIntent().getStringExtra("userName");
        surname = getIntent().getStringExtra("userSurname");
        email = getIntent().getStringExtra("userMail");
        password = getIntent().getStringExtra("userPassword");


        skipSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.cancel();
                //passing data to AgeActivity
                goToAgeSelectorActivity.putExtra("userMail", email)
                        .putExtra("userPassword", password.toString())
                        .putExtra("userName", name.toString())
                        .putExtra("userSurname", surname.toString())
                        .putExtra("userSex", 2);
                startActivity(goToAgeSelectorActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        goToAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.cancel();
                //passing data to AgeActivity
                goToAgeSelectorActivity.putExtra("userMail", email)
                        .putExtra("userPassword", password.toString())
                        .putExtra("userName", name.toString())
                        .putExtra("userSurname", surname.toString())
                        .putExtra("userSex", pageIndicatorView.getSelection());
                startActivity(goToAgeSelectorActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }

            ;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        T = new Timer();

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void run() {
                        if(pageIndicatorView.getSelection() == 0){
                            sexMessage.setText(R.string.sex_selector_message_guy);
                        } else {
                            sexMessage.setText(R.string.sex_selector_message_girl);
                        }
                        Log.i("from registration: ", name + surname + email + password);
                    }
                });
            }
        }, 1, 1);
    }


    /**
     * ###################################################################################### PERMISSION METHODS #############################################################################################################################
     *
     */






    /**
     * ####################################################################################### CUSTOM METHODS #################################################################################################################
     *
     */





}
