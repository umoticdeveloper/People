package com.umotic.people.Bean;

/**
 * This class is used to create a new Position object for updating Database.
 */
public class UserPosition {

    /**
     * Variable def
     */
    private String latitude;
    private String longitude;
    private String cityName;
    private String lastTimePositionUpdate;

    /**
     * Constructors definition
     */
    public UserPosition() {

    }


    /**
     * #############################################################################################
     * #                                   METHODS DEFINITION                                      #
     * #############################################################################################
     */

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

    public String getLastTimePositionUpdate() {
        return lastTimePositionUpdate;
    }

    public void setLastTimePositionUpdate(String lastTimePositionUpdate) {
        this.lastTimePositionUpdate = lastTimePositionUpdate;
    }

    public String toString() {
        return "Latitude: " + getLatitude() + ", Longitude: " + getLongitude() + ", City: " + getCityName() + ", Last time update: " + getLastTimePositionUpdate();
    }
}
