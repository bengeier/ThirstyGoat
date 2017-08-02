package com.goat.thirsty_goat.models;

import java.io.Serializable;

/**
 * Created by Walker on 2/26/17.
 */

public class Location {
    private double mLatitude;
    private double mLongitute;

    public Location(double lat, double lon) {
        mLatitude = lat;
        mLongitute = lon;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitute;
    }
}
