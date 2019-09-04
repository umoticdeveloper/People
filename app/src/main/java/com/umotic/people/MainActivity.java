package com.umotic.people;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity {

    UserPositionManager userPositionManager;
    public boolean inSearch=false;

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

    //TODO : contiene un thread fatto a tromba per simulare la ricerca
    //rivedere per qualità di codice

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void didTapButton(View view) {
        Button bounceButtonSearch = findViewById(R.id.bounceButtonSearch);

        final int[] a = {0};

        final Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                inSearch=true;
                runOnUiThread(new Runnable()
                {


                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void run()
                    {

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
