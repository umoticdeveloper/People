package com.umotic.people.Bean;
/**
 * This class is used to generate a new User
 */
public class User {

    /**
     * Variables definitions
     */
    private String userSex;
    private int userAge;
    private int userIsSpecialGuest;

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

    public int getUserIsSpecialGuest() {
        return userIsSpecialGuest;
    }

    public void setUserIsSpecialGuest(int userIsSpecialGuest) {
        this.userIsSpecialGuest = userIsSpecialGuest;
    }

    public String toString() {
        return "User is " + getUserAge() + " years old, is " + getUserSex() + ". VIP check: " + getUserIsSpecialGuest();
    }
}
