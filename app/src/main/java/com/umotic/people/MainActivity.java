package com.umotic.people;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.umotic.people.Bean.User;
import com.umotic.people.Utils.GpsUtils;

import java.util.Timer;
import java.util.TimerTask;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity {

    //Variable definition
    UserPositionManager userPositionManager;
    public boolean inSearch=false;
    private PulsatorLayout pulsatorLayout, pulsatorLayoutOver;
    private final int ACCESS_TO_POSITION_REQUEST = 1;
    private boolean isContinue = false;
    private boolean isGPS = false;





    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variable references
        userPositionManager = new UserPositionManager(this);
        pulsatorLayout = (PulsatorLayout)findViewById(R.id.pulseView);
        pulsatorLayoutOver = (PulsatorLayout)findViewById(R.id.pulseViewOver);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //requestGpsPermission();
    }

    //The position tracking stops only when the app is closed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPositionManager.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        User user = new SharedManager(getApplicationContext()).getUserInfoShared();

        Toast.makeText(this, user.getName()+" "+user.getMail(), Toast.LENGTH_SHORT).show();
    }


    /**
     * ###################################################################################### PERMISSION METHODS #############################################################################################################################
     *
     */

    /*
    //Check the user choise for the position permission
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ACCESS_TO_POSITION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG);
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/

    /*
    //check if the permission is still given to the app
    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }*/


    /**
     * ####################################################################################### CUSTOM METHODS #################################################################################################################
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void vibration(boolean active) {
        if(active){
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }



    //TODO : contiene un thread fatto a tromba per simulare la ricerca
    //ON CLICK CUSTOM METHOD
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void didTapButton(View view) {
        /*
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT);
            Log.d("Request permission", "Permission granted");
        }else {
            requestGpsPermission();
            Log.d("Request permission", "Permission not granted");
        }*/

        //TODO: richiedere l'attivazione del GPS quando clicco
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        Button bounceButtonSearch = findViewById(R.id.bounceButtonSearch);
        final int[] a = {0};
        final Timer T = new Timer();

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                inSearch=true;
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void run() {
                        if(inSearch) {
                            vibration(true);
                            a[0]++;
                            if (a[0] == 8) {
                                T.cancel();
                                inSearch = false;
                            }
                        }
                    }
                });
            }
        }, 1000, 1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibration(true);
        }
        startButtonAnim(bounceButtonSearch);
        getWorldPosition();
    }


    private void getWorldPosition() {
        //TODO : select all position from DB
        //TODO : display the list of position in the map
        MapFragment.fillWorldLPositions();
        MapFragment.displayWorldLocations();
    }


    //Get GPS user location
    //The position is refreshed only with user moves
    private void writeUserPosition() {
        userPositionManager.start();
    }


    //Start button animation
    private void startButtonAnim(Button bounceButtonSearch){
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0, 30);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(7000);

        bounceButtonSearch.startAnimation(myAnim);
        pulsatorLayout.start();
        pulsatorLayoutOver.start();
    }


    //When users have been found, the animation stops
    private void onUsersFound() {    }

}



