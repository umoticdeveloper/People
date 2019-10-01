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
    private String userAge;
    private boolean userIsSpecialGuest;
    private String latitude;
    private String longitude;
    private String cityName;
    private Date lastTimePositionUpdate;
    private String name;
    private String surname;
    private String mail;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isUserIsSpecialGuest() {
        return userIsSpecialGuest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



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

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
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
