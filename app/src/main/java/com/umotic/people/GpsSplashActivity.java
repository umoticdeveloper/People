package com.umotic.people;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * This activity will be started only if we don't have gps activated (NOT PERMISSIONS).
 */
public class GpsSplashActivity extends AppCompatActivity {

    //Variable definitions
    Intent goToMain;
    Button btnGoToGpsSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_splash);

        //Variable references
        goToMain = new Intent(this, MainActivity.class);
        btnGoToGpsSettings = (Button)findViewById(R.id.btnGoToGpsSettings);

        requestGpsActivation();

        //Go to settings of gps to activate it manually
        btnGoToGpsSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void requestGpsActivation() {
        Boolean activated = false;

        if(activated) {
            startActivity(goToMain);
        } else {
            btnGoToGpsSettings.setVisibility(View.VISIBLE);
        }
    }
}
