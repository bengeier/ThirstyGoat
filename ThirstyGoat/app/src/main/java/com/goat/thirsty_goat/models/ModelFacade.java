package com.goat.thirsty_goat.models;

import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Map;

/**
 * Created by Walker on 2/26/17.
 */

public class ModelFacade {
    private static ModelFacade INSTANCE = new ModelFacade();
    public static ModelFacade getInstance() {
        return INSTANCE;
    }

    private ReportManager mReportManager;
    //private UserManager mUserManager;

    private ModelFacade() {
        mReportManager = new ReportManager();
        //mUserManager = new UserManager();
    }

//    public void addReport(String title, String desc, Location loc) {
//        mReportManager.addReport(new Report(title, desc, loc));
//    }

    public void addReport(WaterType type, WaterCondition condition, Location loc) {
        mReportManager.addReport(new Report(type, condition, loc));
    }

//    public void addReportAndMarker(String title, String desc, Location loc, Marker marker) {
//        mReportManager.addReportAndMarker(new Report(title, desc, loc), marker);
//    }

    public List<Report> getReports() { return mReportManager.getReportList(); }

    public Report getLastReport() {
        return mReportManager.getLastReport();
    }

    public Map<Marker, Report> getMarkers() {
        return mReportManager.getMarkers();
    }
}
