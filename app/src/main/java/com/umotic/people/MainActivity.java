package com.umotic.people;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.io.IOException;

import pl.droidsonroids.gif.GifDecoder;
import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity {

    UserPositionManager userPositionManager;

    private FragmentTransaction transaction;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPositionManager = new UserPositionManager(this);
        setContentView(R.layout.activity_main);



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void vibration(boolean active) {

        if(active){
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE));
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void didTapButton(View view) {
        Button bounceButtonSearch = findViewById(R.id.bounceButtonSearch);

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
    private void startButtonAnim( Button bounceButtonSearch){
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0, 30);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(5000);

        bounceButtonSearch.startAnimation(myAnim);
    }

    //The position tracking stops only when the app is closed
    @Override
    protected void onDestroy() {
        super.onDestroy();

        userPositionManager.stop();


    }




}
