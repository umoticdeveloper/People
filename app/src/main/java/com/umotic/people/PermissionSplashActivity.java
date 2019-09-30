package com.umotic.people;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class PermissionSplashActivity extends AppCompatActivity {

    //Variable definition
    private final int ACCESS_TO_POSITION_REQUEST = 1;
    Intent goToLoginActivity, goToSettingsIntent, restartSplash;
    Thread welcomeThread;
    Button goToSettings;
    TextView noGpsPermissionMsg;
    Uri uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_splash);

        //Variable references
        goToLoginActivity = new Intent(this, LoginActivity.class);
        restartSplash = new Intent(this, PermissionSplashActivity.class);
        goToSettingsIntent = new Intent();
        goToSettings = (Button)findViewById(R.id.btnGoToSettings);
        noGpsPermissionMsg = (TextView)findViewById(R.id.noGpsPermissionMsg);
        goToSettingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        uri = Uri.fromParts("package", getPackageName(), null);
        goToSettingsIntent.setData(uri);



        welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);
                } catch (Exception e) {

                } finally {
                    startActivity(goToLoginActivity);
                    finish();
                }
            }
        };


        if(checkLocationPermission()) {
            welcomeThread.start();
        }else {
            requestGpsPermission();
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        if(checkLocationPermission()){
            startActivity(goToLoginActivity);
        }
    }



    private void requestGpsPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            ActivityCompat.requestPermissions(PermissionSplashActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
                            ActivityCompat.requestPermissions(PermissionSplashActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
                            startActivity(goToLoginActivity);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            goToSettings.setVisibility(View.VISIBLE);
                            noGpsPermissionMsg.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.gps_permission_title).setMessage(R.string.gps_permission_message).setPositiveButton(R.string.ok, dialogClickListener)
                    .setNegativeButton(R.string.cancel, dialogClickListener).setCancelable(false).show();

            /*
            new AlertDialog.Builder(this).setTitle(R.string.gps_permission_title).setMessage(R.string.gps_permission_message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(PermissionSplashActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
                            ActivityCompat.requestPermissions(PermissionSplashActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setCancelable(false).create().show();*/
        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
        }
    }


    //check if the permission is still given to the app
    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    public void goToSettings(View view){
        startActivity(goToSettingsIntent);
    }
}
