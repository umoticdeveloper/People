package com.umotic.people;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.umotic.people.Bean.User;
import com.umotic.people.dataproviders.DpUserInfo;

import java.util.Calendar;

public class UserPositionManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

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
    DpUserInfo dpUserInfo = new DpUserInfo(context);
    protected UserPositionManager(Context c) {

        context = c;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        //checkLocation(); //check whether location service is enable or not in your  phone

    }




    //Controlla se sono presenti i permessi della posizione, se ottenuti avvia la startLocationUpdate()
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            // Permission is not granted
            // Should we show an explanation?
           showAlert_2();

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("reques", "--->>>>>");
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");

    }

    /**
     * PRESA IN CARICO DA FEDERICO SCHIAVONE: GESTIONE DATI CON FIREBASE
     * @param location
     */
    private void dbUserUpdater(Location location) {
        //TODO : write user position in DB
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("User");
        user.setLatitude(String.valueOf(location.getLatitude()));
        user.setLongitude(String.valueOf(location.getLongitude()));
        //TODO: capire come si prende il nome della città
        user.setCityName("Torremaggiore");
        user.setUserAge(22);
        user.setUserSex("m".toUpperCase());
        user.setUserIsSpecialGuest(false);
        user.setLastTimePositionUpdate(Calendar.getInstance().getTime());

        //TODO: CAPIRE SE E' MEGLIO USARE UN DATAPROVIDER O LASCIARE COSI' COM'E' PERCHE' COL DP CREA A LOOP TANTI UTENTI OGNI VOLTA CHE CAMBIA LA POSIZIONE.
        //dpUserInfo.insertUserInfo(user);
        databaseReference.setValue(user);
    }


    //Quando avviene un cambiamento di posizione
    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());

        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        // You can now create a LatLng Object for use with maps
        //latLng = new LatLng(location.getLatitude(), location.getLongitude());
        sharedLastUserPosition(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
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


    private boolean checkLocation() {

        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    //mostra dialogo esteso richiesta permessi gps
    private void showAlert() {
        new AlertDialog.Builder(context)
                .setTitle("Necessario accesso alla Posizione ")
                .setMessage("E' necessario attivare la geolocalizzazione per quest' app")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    }
                })
                .setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    //mostra direttamente la richiesta permessi
    private void showAlert_2() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
