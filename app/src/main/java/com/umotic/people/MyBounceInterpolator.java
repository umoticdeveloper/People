package com.umotic.people;

public class MyBounceInterpolator implements android.view.animation.Interpolator {

    //Variable definition
    private float mAmplitude = 1;
    private double mFrequency = 10;

    //Constructors
    MyBounceInterpolator(float amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }


    /**
     * Function to animate the pulse view
     * @param time
     * @return
     */
    public float getInterpolation(float time) {
        return (float) Math.sin(mFrequency * time) + mAmplitude;
    }
}