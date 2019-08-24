package com.umotic.people.Bean;

import java.util.Date;

/**
 * This class is used to generate a new User
 */
public class User {

    /**
     * Variables definitions
     */
    private String userSex;
    private int userAge;
    private boolean userIsSpecialGuest;
    private String latitude;
    private String longitude;
    private String cityName;
    private Date lastTimePositionUpdate;

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

    public boolean getUserIsSpecialGuest() {
        return userIsSpecialGuest;
    }

    public void setUserIsSpecialGuest(boolean userIsSpecialGuest) {
        this.userIsSpecialGuest = userIsSpecialGuest;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getLastTimePositionUpdate() {
        return lastTimePositionUpdate;
    }

    public void setLastTimePositionUpdate(Date lastTimePositionUpdate) {
        this.lastTimePositionUpdate = lastTimePositionUpdate;
    }


    public String toString() {
        return "User: \nAge: " + getUserAge() + "\nSex: " + getUserSex() + "\nIs VIP: " + getUserIsSpecialGuest() + "\nLat: " + getLatitude() + "\nLon: " + getLongitude() + "\nCity: " + getCityName() + "\nLast time update: " + getLastTimePositionUpdate();
    }

}
