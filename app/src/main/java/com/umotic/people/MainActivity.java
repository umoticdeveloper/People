package com.umotic.people;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_main);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void didTapButton(View view) {
        Button bounceButtonSearch = (Button) findViewById(R.id.bounceButtonSearch);

        startButtonAnim(bounceButtonSearch);

        getUserPosition();


    }

    //Get GPS user location
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getUserPosition() {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Permessi gestire
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            String lon = location.getLongitude() + "";
                            String len = location.getLatitude() + "";
                            Toast.makeText(MainActivity.this, lon + " " + len, Toast.LENGTH_SHORT).show();
                        }else{
                            //TODO: Richiedi di attivare la posizione
                        }
                    }
                });

    }

    //Start button animation
    private void startButtonAnim( Button bounceButtonSearch){
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setDuration(5000);

        bounceButtonSearch.startAnimation(myAnim);
    }

}
