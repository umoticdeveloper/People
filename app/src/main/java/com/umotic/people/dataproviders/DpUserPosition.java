package com.umotic.people.dataproviders;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * In this class is set the definition of methods for retrieving:
 * - position data from all the users connected to the app
 * - insert position in real-time of the user that is connected to the app (with a temporal update)
 */
public class DpUserPosition {
    private DatabaseReference databaseReference;

    /**
     * Write user
     * @param latitude
     * @param longitude
     * @param cityName
     * @param lastTimePositionUpdate
     */
    public void writeUserPosition(String latitude, String longitude, String cityName, String lastTimePositionUpdate) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserPosition");
    }
}
