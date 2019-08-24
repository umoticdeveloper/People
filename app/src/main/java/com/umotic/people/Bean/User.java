package com.umotic.people.Bean;
/**
 * This class is used to generate a new User
 */
public class User {

    /**
     * Variables definitions
     */

    private int ID;
    private String userSex;
    private int userAge;
    private int userIsSpecialGuest;
    private UserPosition userPosition;


    /**
     * Constructors definitions
     */
    public User() {

    }

    /**
     * #############################################################################################
     * #                                   METHODS DEFINITION                                      #
     * #############################################################################################
     */

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserIsSpecialGuest() {
        return userIsSpecialGuest;
    }

    public void setUserIsSpecialGuest(int userIsSpecialGuest) {
        this.userIsSpecialGuest = userIsSpecialGuest;
    }

    public UserPosition getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(UserPosition userPosition) {
        this.userPosition = userPosition;
    }

    public String toString() {
        return "User is " + getUserAge() + " years old, is " + getUserSex() + ". VIP check: " + getUserIsSpecialGuest() + "User LonLat: " + userPosition.getLongitude() + " , " + userPosition.getLatitude();
    }
}
