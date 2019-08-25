package com.umotic.people.dataproviders;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.umotic.people.Bean.User;


/**
 * In this class is set the definition of methods for retrieving:
 * - position data from all the users connected to the app
 * - insert position in real-time of the user that is connected to the app (with a temporal update)
 */
public class DpUserInfo {
    Context context;



    public DpUserInfo(Context context) {
        this.context = context;
    }

    /**
     * Insert the first occurence of a user if it doesn't exist. The user is identified by its ID.
     */
    public void insertUserInfo(User user) {
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        User userData = new User();
        databaseReference.child("User").push().setValue(userData);
    }
}
