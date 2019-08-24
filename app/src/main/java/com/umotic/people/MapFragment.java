package com.umotic.people;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    static Bitmap gpsM;
    static Bitmap gpsF;

    static GoogleMap googleMap;
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
        prepareWordsIcons();
        return v;
    }


    //TODO : ciclo viene effettuato solo una volta
    @Override
    public void onResume () {
        super.onResume();
   }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.setMaxZoomPreference(16);
        googleMap.setMapType(1);
        googleMap.setMyLocationEnabled(true);

        String lon = sharedPref.getString("lon", "0");
        String lat = sharedPref.getString("lat", "0");

        Toast.makeText(getContext(), "THREAD-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();
        LatLng pos = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(pos, 15);
        googleMap.animateCamera(location);

        //if userLocationEnable is false, can use custom marker
        if (!googleMap.isMyLocationEnabled()) {

            Toast.makeText(getContext(), "THREAD CUSTOM-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gps3);
            Bitmap gps = Bitmap.createScaledBitmap(bitmap, 80, 80, true);

            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(gps)).position(pos));
        return;
        }
        Toast.makeText(getContext(), "THREAD not custom-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();

    }

    public static void displayWorldLocations() {

        googleMap.clear();
        //world positions
        ArrayList<String> al = new ArrayList<String>();
        //fill array with world positions
        al = fillWorldLPositions(al);

                //cicla tutte le pos e metti i marker come sprite rossi
                // inventa pos
                //Toast.makeText(getContext(), "THREAD-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();
                if(al != null && al.size()>1) {


                    for (String position : al) {
                        String[] latlon = position.split(",");
                        //Toast.makeText(, "mostrando pose", Toast.LENGTH_SHORT).show();

                        LatLng wPos = new LatLng(Double.parseDouble(latlon[0]), Double.parseDouble(latlon[1]));


                        googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(gpsM)).position(wPos).anchor(0.5f,0.5f));


                    }
                }else{
                    //Toast.makeText(getContext(), "nulla da mostrare", Toast.LENGTH_SHORT).show();
                }




    }

    private void prepareWordsIcons() {

        Bitmap bitmapM = BitmapFactory.decodeResource(getResources(), R.drawable.generic);
        gpsM = Bitmap.createScaledBitmap(bitmapM, 200, 200, true);


        Bitmap bitmapF = BitmapFactory.decodeResource(getResources(), R.drawable.fm);
        gpsF = Bitmap.createScaledBitmap(bitmapF, 200, 200, true);

    }

    private static ArrayList<String> fillWorldLPositions(ArrayList<String> al) {


        //Ferrarese
        for(int a =1;a<(new Random().nextInt(10 - 1) + 4);a++) {
            al.add("41.12231423338676,16.86048149602925");
        }
        //Politecnico
        for(int a =1;a<(new Random().nextInt(10 - 1) + 4);a++) {

            al.add("41.10907370571775,16.878347396850586");
        }
        //postaccio
        for(int a =1;a<(new Random().nextInt(10 - 1) + 4);a++) {

            al.add("41.11148,16.8554");
        }



        return al;
    }


}
