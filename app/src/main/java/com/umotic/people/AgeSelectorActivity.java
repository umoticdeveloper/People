package com.umotic.people;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.json.JSONException;


public class AgeSelectorActivity extends AppCompatActivity {

    //Variable definition
    IndicatorSeekBar indicatorAgeBar;
    ImageView imageView;
    Animation animFadeOut;
    Animation animFadeIn;
    FloatingActionButton goToMain;
    Intent goToMainActivity, goToLoginActivity;
    SeekParams seekActualParams;
    String name, surname, email, password, ageRange = "[18-24]";
    int sex;
    private static String URL_REGIST = "http://peopleapp.altervista.org/DbPhpFiles/InsertUser.php";




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
        goToLoginActivity = new Intent(this, LoginActivity.class);



        //getting data from RegistrationActivity
        name = getIntent().getStringExtra("userName");
        surname = getIntent().getStringExtra("userSurname");
        email = getIntent().getStringExtra("userMail");
        password = getIntent().getStringExtra("userPassword");
        sex = getIntent().getIntExtra("userSex", -1);
        Log.d("SEX", sex + "");

        if(sex == 0){
            imageView.setImageResource(R.drawable.ic_young_man);
        }else if(sex == 1){
            imageView.setImageResource(R.drawable.ic_young_girl);
        }else if(sex == 2){
            imageView.setImageResource(R.drawable.ic_young);
        }

        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("from registration sex: ", name + surname + email + password + ageRange);
                String[] values = {name, surname, email, password, sex + "", ageRange};
                new SharedManager(getApplicationContext()).writeInfoShared(values);

                try {
                    signUp();
                } catch (JSONException e) {
                    Toast.makeText(AgeSelectorActivity.this, "Error in Request", Toast.LENGTH_SHORT).show();
                    ;
                }
                startActivity(goToMainActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

                SexSelectorActivity.sex.finish();
                finish();
            }
        });


        //Logic for seekbar and image transition
        indicatorAgeBar.setR2L(false);
        indicatorAgeBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if(sex == 2) {
                    if (seekParams.progress == -1) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_kid);
                        ageRange = "[15-18]";
                        Log.d("Età: ", "< 18");
                    } else if (seekParams.progress == 24) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_young);
                        ageRange = "[18-24]";
                        Log.d("Età: ", "[18-24]");
                    } else if (seekParams.progress == 50) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_adult);
                        ageRange = "[25-30]";
                        Log.d("Età: ", "[25-30]");
                    } else if (seekParams.progress == 75) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_adult_old);
                        ageRange = "[31-40]";
                        Log.d("Età: ", "[31-40]");
                    } else if (seekParams.progress == 100) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_old);
                        ageRange = "[40-60]";
                        Log.d("Età: ", "> 40");
                    }
                }else if(sex == 0){
                    if (seekParams.progress == -1) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_kid_man);
                        ageRange = "[15-18]";
                        Log.d("Età: ", "< 18");
                    } else if (seekParams.progress == 24) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_young_man);
                        ageRange = "[18-24]";
                        Log.d("Età: ", "[18-24]");
                    } else if (seekParams.progress == 50) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_adult_man);
                        ageRange = "[25-30]";
                        Log.d("Età: ", "[25-30]");
                    } else if (seekParams.progress == 75) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_adult_old_man);
                        ageRange = "[31-40]";
                        Log.d("Età: ", "[31-40]");
                    } else if (seekParams.progress == 100) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_old_man);
                        ageRange = "[40-60]";
                        Log.d("Età: ", "> 40");
                    }
                }else if(sex == 1){
                    if (seekParams.progress == -1) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_kid_girl);
                        ageRange = "[15-18]";
                        Log.d("Età: ", "< 18");
                    } else if (seekParams.progress == 24) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_young_girl);
                        ageRange = "[18-24]";
                        Log.d("Età: ", "[18-24]");
                    } else if (seekParams.progress == 50) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_adult_girl);
                        ageRange = "[25-30]";
                        Log.d("Età: ", "[25-30]");
                    } else if (seekParams.progress == 75) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_adult_old_girl);
                        ageRange = "[31-40]";
                        Log.d("Età: ", "[31-40]");
                    } else if (seekParams.progress == 100) {
                        animFadeOut.reset();
                        imageView.clearAnimation();
                        imageView.startAnimation(animFadeIn);
                        imageView.setImageResource(R.drawable.ic_old_girl);
                        ageRange = "[40-60]";
                        Log.d("Età: ", "> 40");
                    }
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


    private void signUp() throws JSONException {

        final String name = this.name.trim();
        final String surname = this.surname.trim();
        final String email = this.email.trim();
        final String password = this.password.trim();
        final String ageRange = this.ageRange;
        final String sex = new SharedManager(getApplicationContext()).getUserInfoShared().getUserSex();

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

        new BKGWorker().doInBackground("http://peopleapp.altervista.org/DbPhpFiles/InsertUser.php");


    }

}
