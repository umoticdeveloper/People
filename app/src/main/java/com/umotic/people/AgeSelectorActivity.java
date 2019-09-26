package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class AgeSelectorActivity extends AppCompatActivity {

    //Variable definition
    IndicatorSeekBar indicatorAgeBar;
    ImageView imageView;
    Animation animFadeOut;
    Animation animFadeIn;
    FloatingActionButton goToMain;
    Intent goToMainActivity;




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
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        goToMain = (FloatingActionButton)findViewById(R.id.gotopermissionsplashscreen);

        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goToMainActivity);
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
                }else if(seekParams.progress == 24){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_young);
                }else if(seekParams.progress == 50){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_adult);
                }else if(seekParams.progress == 75){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_adult_old);
                }else if(seekParams.progress == 100){
                    animFadeOut.reset();
                    imageView.clearAnimation();
                    imageView.startAnimation(animFadeIn);
                    imageView.setImageResource(R.drawable.ic_old);
                }
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






}
