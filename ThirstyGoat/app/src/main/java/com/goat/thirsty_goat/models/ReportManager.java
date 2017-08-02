package com.goat.thirsty_goat.models;


import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Walker on 2/26/17.
 */

public class ReportManager {
    private List<Report> mReports;
    private Map<Marker, Report> mMarkers = new HashMap<>();

    public ReportManager() {
        mReports = new ArrayList<>();
        makeDummyReports();
    }

    private void makeDummyReports() {
        addReport(new Report(WaterType.BOTTLED, WaterCondition.POTABLE, new Location(33.749, -84.388)));
        addReport(new Report(WaterType.LAKE, WaterCondition.WASTE, new Location(33.8, -84.5)));
    }

    public List<Report> getReportList() {
        return mReports;
    }

    public void addReport(Report report) {
        mReports.add(report);
        Log.d("Report", "Added a water report!");
    }

//    public void addReportAndMarker(Report report, Marker marker) {
//        addReport(report);
//        mMarkers.put(marker, report);
//    }

    public Map<Marker, Report> getMarkers() {
        return mMarkers;
    }

    public String getLastReportString() {
        return mReports.get(mReports.size() - 1).toString();
    }

    public Report getLastReport() {
        return mReports.get(mReports.size() - 1);
    }
}
