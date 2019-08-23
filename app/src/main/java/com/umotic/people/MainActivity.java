package com.umotic.people;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    UserPositionManager userPositionManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void didTapButton(View view) {
        Button bounceButtonSearch = (Button) findViewById(R.id.bounceButtonSearch);

        startButtonAnim(bounceButtonSearch);

        getUserPosition();

        getWorldPosition();
    }

    private void getWorldPosition() {

        //TODO : select all position from DB
        //TODO : insert user position in DB
        //TODO : display the list of position in the map
    }

    //Get GPS user location
    //The position is refreshed only with user moves

    private void getUserPosition() {
        userPositionManager = new UserPositionManager(this);
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
