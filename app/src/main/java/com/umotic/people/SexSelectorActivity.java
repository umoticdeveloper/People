package com.umotic.people;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rd.PageIndicatorView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SexSelectorActivity extends AppCompatActivity {

    //Variable definition
    ViewPager viewPager;
    ImageAdapter imageAdapter;
    FloatingActionButton goToAgeButton;
    Intent goToAgeSelectorActivity;
    TextView sexMessage;
    PageIndicatorView pageIndicatorView;



    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex_selector);

        //Variable references
        goToAgeSelectorActivity = new Intent(this, AgeSelectorActivity.class);
        viewPager = (ViewPager)findViewById(R.id.sexPagerSelector);
        imageAdapter = new ImageAdapter(this);
        viewPager.setAdapter(imageAdapter);
        goToAgeButton = findViewById(R.id.gotoage);
        sexMessage = (TextView)findViewById(R.id.sexSelectorMessage);
        pageIndicatorView = (PageIndicatorView)findViewById(R.id.sexIndicatorView);

        Log.d("Count: ", pageIndicatorView.getCount() + "");


        goToAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToAgeSelectorActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(imageAdapter.getItemPosition(viewPager) == -1){
            sexMessage.setText(R.string.sex_selector_message_guy);
        } else {
            sexMessage.setText(R.string.sex_selector_message_girl);
        }
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
