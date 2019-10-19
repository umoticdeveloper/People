package com.umotic.people;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.umotic.people.Bean.User;



public class UserPositionManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    //Variable definition
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    LatLng latLng;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private LocationManager locationManager;
    User user = new User();
    Context context;



    protected UserPositionManager(Context c) {
        context = c;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }



    //Controlla se sono presenti i permessi della posizione, se ottenuti avvia la startLocationUpdate()
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            //showAlert_2();

        } else {
            //Arready have permission
            startLocationUpdates();
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLocation == null) {

                startLocationUpdates();
            }
            if (mLocation != null) {
                //mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
                //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
            } else {
                //Toast.makeText(context, "Location not Detected", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i("GPS","Connection Suspended");
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("GPS", "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    protected void start() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    protected void stop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    //Get GPS location
    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("reques", "--->>>>>");
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }


    private void dbUserUpdater(Location location) {
        //TODO : write user position in DB
        /*
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("User");
        user.setLatitude(String.valueOf(location.getLatitude()));
        user.setLongitude(String.valueOf(location.getLongitude()));
        user.setCityName("Torremaggiore");
        user.setUserAge(22+"");
        user.setUserSex("m".toUpperCase());
        user.setUserIsSpecialGuest(false);
        user.setLastTimePositionUpdate(Calendar.getInstance().getTime());
        //dpUserInfo.insertUserInfo(user);
        databaseReference.setValue(user);
        */

    }


    //Quando avviene un cambiamento di posizione
    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());

        // now we can create a LatLng Object for use with maps
        // latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // Actually google maps use self gps info
        // this class has been made 4 fetch data or use custom maps elements like mooving arrow like in MapFragment, pins ecc

        sharedLastUserPosition(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));

        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        dbUserUpdater(location);

    }


    //ultima posizione nota condivisa nelle shared
    private void sharedLastUserPosition(String lat, String lon) {
        SharedPreferences sharedPref =  ((Activity) context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lon", lon);
        editor.putString("lat", lat);
        editor.commit();
    }
}
