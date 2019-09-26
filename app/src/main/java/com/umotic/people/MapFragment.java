package com.umotic.people;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    //Variable definition
    static ArrayList<String> positions = new ArrayList<String>();
    static Bitmap gpsM;
    static Bitmap gpsF;
    static Bitmap gpsG;
    static Bitmap gpsCustom;
    static GoogleMap googleMap;
    SharedPreferences sharedPref;
    SupportMapFragment mapFragment;


    //Constructors
    public MapFragment() {}




    /**
     * ###################################################################################### CALLBACK METHODS #############################################################################################################################
     *
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment).commit();
        }

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        mapFragment.getMapAsync(this);

        //inizializza i marker del gps con gli sprite colorati
        prepareWordsIcons();
        return v;
    }


    //TODO : il ciclo funziona ma non riesco ad animare i marker
    public static void test() {

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    //MapFragment.displayWorldLocations();
                    //animateMarker(marker);
                    //Toast.makeText(MainActivity.class, "Ase", Toast.LENGTH_SHORT).show();
                }
            }
            }
        ).start();
    }


    //appena la mappa è pronta effettua operazioni preliminari
    //stile mappa, telecamera posizionata sull'utente,
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        String lon = sharedPref.getString("lon", "0");
        String lat = sharedPref.getString("lat", "0");
        this.googleMap=googleMap;

        googleMap.setMaxZoomPreference(16);
        googleMap.setMapType(1);
        googleMap.setMyLocationEnabled(true);

        //Toast.makeText(getContext(), "THREAD-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();
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

        //Toast.makeText(getContext(), "THREAD not custom-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Float zoom = googleMap.getCameraPosition().zoom;
                    //13 zoom = 20 px
                    //10 zoom = 14 px
                    //33 zoom = 12 px
                    //2  zoom =  0 px
                int dimensione = (int) (2*Math.pow(zoom.doubleValue(),2));
                modifyWordsIcons(dimensione,dimensione);
                //Log.d("camera",dimensione+" -> ZOOM: "+zoom.toString());
                displayWorldLocations();
            }
        });
    }

    //mostra sulla mappa gli sprite
    public static void displayWorldLocations() {
        googleMap.clear();
        //world positions
        // cicla tutte le pos e metti i marker come sprite rossi
        // inventa pos
        //Toast.makeText(getContext(), "THREAD-> Lat: " + lat + " Long: " + lon, Toast.LENGTH_SHORT).show();
        if (positions != null && positions.size() > 1) {
            for (String position : positions) {
                String[] worldposition = position.split(",");
                //Toast.makeText(, "mostrando pose", Toast.LENGTH_SHORT).show();

                LatLng wPos = new LatLng(Double.parseDouble(worldposition[0]), Double.parseDouble(worldposition[1]));

                if ("M".equals(worldposition[2])) {
                    //Log.d("MALE", "famale");
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(gpsM)).position(wPos).anchor(0.5f, 0.5f).alpha(1f).flat(true));
                }

                if ("F".equals(worldposition[2])) {
                   // Log.d("FEMALE", "female");

                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(gpsF)).position(wPos).anchor(0.5f, 0.5f).alpha(1f).flat(true));
                }

                if ("G".equals(worldposition[2])) {
                    //Log.d("Generic", "female");
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(gpsG)).position(wPos).anchor(0.5f, 0.5f).alpha(1f).flat(true));

               /*     Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(wPos)
                            .title("San Francisco")
                            .snippet("Population: 776733")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.fromResource(gpsCustom))));
*/
                }
            }
        } else {
            //Toast.makeText(getContext(), "nulla da mostrare", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    //inizializza i marker del gps con gli sprite colorati (M & F)
    private void prepareWordsIcons() {

        Bitmap bitmapM = BitmapFactory.decodeResource(getResources(), R.drawable.male);
        gpsM = Bitmap.createScaledBitmap(bitmapM, 400, 400, true);


        Bitmap bitmapF = BitmapFactory.decodeResource(getResources(), R.drawable.fm);
        gpsF = Bitmap.createScaledBitmap(bitmapF, 400, 400, true);


        Bitmap bitmapG = BitmapFactory.decodeResource(getResources(), R.drawable.generic);
        gpsG = Bitmap.createScaledBitmap(bitmapG, 400, 400, true);

        Bitmap bitmapCustom = BitmapFactory.decodeResource(getResources(), R.drawable.gps1);
        gpsCustom = Bitmap.createScaledBitmap(bitmapCustom, 200, 200, true);

    }


    private void modifyWordsIcons(int x,int y) {

        Bitmap bitmapM = BitmapFactory.decodeResource(getResources(), R.drawable.male);
        gpsM = Bitmap.createScaledBitmap(bitmapM, x, y, true);


        Bitmap bitmapF = BitmapFactory.decodeResource(getResources(), R.drawable.fm);
        gpsF = Bitmap.createScaledBitmap(bitmapF, x, y, true);


        Bitmap bitmapG = BitmapFactory.decodeResource(getResources(), R.drawable.generic);
        gpsG = Bitmap.createScaledBitmap(bitmapG, x, y, true);

        Bitmap bitmapCustom = BitmapFactory.decodeResource(getResources(), R.drawable.gps1);
        gpsCustom = Bitmap.createScaledBitmap(bitmapCustom, x, y, true);

    }


    //Definisce le posizioni da attribuire ai marker, chiamato dal main(sprite colorati)
    public static void fillWorldLPositions() {
        positions.clear();

        //Ferrarese
        for(int a =1;a<(new Random().nextInt(15 - 1) + 4);a++) {
            positions.add("41.12231423338676,16.86048149602925,G");
        }

        //"00.00010000000000,00.00010000000000"
        positions.add("41.12241423338676,16.86058149602925,M");
        positions.add("41.12241423338676,16.86058149602925,F");
        positions.add("41.12241423338676,16.86058149602925,M");

        //Politecnico
        for(int a =1;a<(new Random().nextInt(10 - 1) + 4);a++) {
            positions.add("41.10907370571775,16.878347396850586,G");
        }
        //postaccio
        for(int a =1;a<(new Random().nextInt(10 - 1) + 4);a++) {
            positions.add("41.11148,16.8554,G");
        }
    }
}
