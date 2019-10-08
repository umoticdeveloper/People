package com.umotic.people;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class PermissionSplashActivity extends AppCompatActivity {

    //Variable definition
    private final int ACCESS_TO_POSITION_REQUEST = 1;
    Intent goToLoginActivity, goToSettingsIntent, restartSplash;
    Thread welcomeThread, checkPermissionThread;
    Button goToSettings;
    TextView noGpsPermissionMsg, guidePermissions;
    Uri uri;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;



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
        guidePermissions = (TextView)findViewById(R.id.guidePermissions);
        goToSettingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        uri = Uri.fromParts("package", getPackageName(), null);
        goToSettingsIntent.setData(uri);

        requestGpsPermission();

        checkPermissionThread = new Thread() {
            @Override
            public void run() {
                if(checkLocationPermission()) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(goToLoginActivity);
                    finish();
                }
            }
        };


    }

    @Override
    public void onRestart(){
        super.onRestart();
        goToSettings.setVisibility(View.INVISIBLE);
        noGpsPermissionMsg.setVisibility(View.INVISIBLE);
        guidePermissions.setVisibility(View.INVISIBLE);
        if(checkLocationPermission()){
            startActivity(goToLoginActivity);
        }else {
            requestGpsPermission();
        }
    }



    private void requestGpsPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(PermissionSplashActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Permissions: ", permissions[0]);
        //Checking the request code of our request
        if (requestCode == ACCESS_TO_POSITION_REQUEST) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                checkPermissionThread.start();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Needed To Run The App", Toast.LENGTH_LONG).show();
                goToSettings.setVisibility(View.VISIBLE);
                noGpsPermissionMsg.setVisibility(View.VISIBLE);
                guidePermissions.setVisibility(View.VISIBLE);
            }
        }
    }


    //check if the permission is still given to the app
    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    public void goToSettings(View view){
        startActivity(goToSettingsIntent);
    }
}
