package com.goat.thirsty_goat.models;

/**
 * Created by Walker on 2/26/17.
 */

public class Report {
    private static int Next_ID = 0;
    private int mID;
    private String mName;
    private String mDescription;
    private Location mLocation;
    private WaterType mWaterType;
    private WaterCondition mWaterCondition;

//    public Report(String name, String desc, Location location) {
//        mName = name;
//        mDescription = desc;
//        mLocation = location;
//        mID = Next_ID++;
//    }

    // todo: add name, date and time, report number
    public Report(WaterType type, WaterCondition condition, Location location) {
        //mName = name;
        //mDescription = desc;
        mLocation = location;
        mID = Next_ID++;
        mWaterType = type;
        mWaterCondition = condition;
    }

//    @ Override
//    public String toString() {
//        return mName + "\n" + mDescription;
//    }

    public String getName() {
        return mName;
    }

    public WaterType getWaterType() {
        return mWaterType;
    }

    public WaterCondition getWaterCondition() {
        return mWaterCondition;
    }


//    public String getDescription() {
//        return mDescription;
//    }

    public double getLatitude() {
        return mLocation.getLatitude();
    }

    public double getLongitude() {
        return mLocation.getLongitude();
    }
}
