package com.umotic.people;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
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
    private Toolbar toolbar;
    private Drawer drawerBuilder;
    private PrimaryDrawerItem homeButtonDrawer, profileButtonDrawer, settingsButtonDrawer, logoutButtonDrawer;
    AccountHeader headerResult;
    Intent logout;





    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginActivity.loginActivity.finish();



        //Variable references
        userPositionManager = new UserPositionManager(this);
        pulsatorLayout = (PulsatorLayout)findViewById(R.id.pulseView);
        pulsatorLayoutOver = (PulsatorLayout)findViewById(R.id.pulseViewOver);
        logout = new Intent(this, LoginActivity.class);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeightDp(250)
                .withHeaderBackground(R.drawable.gang)
                .addProfiles(new ProfileDrawerItem().withName("Federico").withEmail("mail@mail.com"))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                }).build();

        profileButtonDrawer = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.profile);
        settingsButtonDrawer = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.settings);
        logoutButtonDrawer = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.logout);
        homeButtonDrawer = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.home);

        drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(homeButtonDrawer, profileButtonDrawer, settingsButtonDrawer, new DividerDrawerItem(), logoutButtonDrawer)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Log.d("Item Drawer", position + "");
                        if(position == 1) { //HOME
                            //replace layout with fragment
                        }else if(position == 2) { //PROFILE
                            //replace layout with fragment
                        }else if(position == 3) { //SETTINGS
                            //replace layout with fragment
                        }else if(position == 5) { //LOGOUT
                            SharedPreferences checkBoxPref = getSharedPreferences("rememberMe", MODE_PRIVATE);
                            SharedPreferences.Editor editorCheckBox = checkBoxPref.edit();
                            editorCheckBox.clear().apply();
                            startActivity(logout);
                            MainActivity.this.finish();
                        }
                        return false;
                    }
                })
                .build();
        toolbar = (Toolbar)findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_drawer);


        if(!checkLocationPermission()){
            requestGpsPermission();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
       /* if(!checkLocationPermission()) {
            requestGpsPermission();
        }*/
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
    }



    /**
     * ###################################################################################### PERMISSION METHODS #############################################################################################################################
     *
     */

    //check if the permission is still given to the app
    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    private void requestGpsPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_TO_POSITION_REQUEST);
        }
    }




    /**
     * ####################################################################################### CUSTOM METHODS #################################################################################################################
     *
     */


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.exit_title)
                .setMessage(R.string.exit_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


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

        //Check if GPS is on or off
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerBuilder.openDrawer();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



