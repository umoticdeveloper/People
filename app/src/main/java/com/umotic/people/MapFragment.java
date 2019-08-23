package com.umotic.people;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    SharedPreferences sharedPref;
    SupportMapFragment mapFragment;
    public MapFragment()

    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment).commit();
        }
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        mapFragment.getMapAsync(this);
        return v;
    }


    //TODO : ciclo viene effettuato solo una volta
    @Override
    public void onResume () {

        super.onResume();

        Handler handler = new Handler();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                String lon = sharedPref.getString("lon", "0");
                String lat = sharedPref.getString("lat", "0");

                Toast.makeText(getContext(), "THREAD-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();
                LatLng pos = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                //googleMap.setMyLocationEnabled(true);
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(pos, 15);
                googleMap.animateCamera(location);

                if (!googleMap.isMyLocationEnabled()) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gps3);
                    Bitmap gps = Bitmap.createScaledBitmap(bitmap, 80, 80, true);

                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(gps)).position(pos));
                    googleMap.setMapType(1);

                }

            }
        };
        handler.postDelayed(r,4);



    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.setMaxZoomPreference(16);


    }
}
