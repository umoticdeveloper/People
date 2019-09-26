package com.umotic.people;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

public class SexSelectorActivity extends AppCompatActivity {

    //Variable definition




    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex_selector);

        //Variable references
        final Intent goToAgeSelectorActivity = new Intent(this, AgeSelectorActivity.class);
        ViewPager viewPager = (ViewPager)findViewById(R.id.sexPagerSelector);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        viewPager.setAdapter(imageAdapter);
        FloatingActionButton goToAgeButton = findViewById(R.id.gotoage);

        goToAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToAgeSelectorActivity);
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


}
