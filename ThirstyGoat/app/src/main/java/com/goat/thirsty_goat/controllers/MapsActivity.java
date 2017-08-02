package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.Report;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public final static String LATITUDE_MESSAGE = "com.goat.thirsty_goat.LATITUDE";
    public final static String LONGITUDE_MESSAGE = "com.goat.thirsty_goat.LONGITUDE";

    private GoogleMap mMap;
    private ModelFacade mFacade;

    private LatLng mCurrLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFacade = ModelFacade.getInstance();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        
        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                mCurrLatLong = latLng;

                Log.d("Report", "pre handle");
                // switches to Wa
                handleReport(latLng);
                Log.d("Report", "post handle");

                displayMarkers();
                Log.d("Report", "post display markers");


                // Creating a marker
                //MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                //markerOptions.position(latLng);



                // Clears the previously touched position
                // mMap.clear();
//                mFacade.addReport("newly added", "Bobs Place", new Location(latLng.latitude, latLng.longitude));

                // Setting the title for the marker.
                // This will be displayed on taping the marker
//                markerOptions.title(mFacade.getLastReport().getName());
//                markerOptions.snippet(mFacade.getLastReport().getDescription());

                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);



                // my code
//                Marker newMarker = mMap.addMarker(markerOptions);
//                mFacade.addReportAndMarker("newly added", "Bobs Place",
//                        new Location(latLng.latitude, latLng.longitude),
//                        newMarker);

            }
        });

        displayMarkers();
    }

    public void onResume() {
        Log.d("Report", "MapActivity's onResume");
        super.onResume();
        if (mMap != null) {
            displayMarkers();
        }
    }

    public void displayMarkers() {
        Log.d("Report", "displaying markers");
        List<Report> reportList = mFacade.getReports();
        for (Report r : reportList) {
            LatLng loc = new LatLng(r.getLatitude(), r.getLongitude());
            //mMap.addMarker(new MarkerOptions().position(loc).title(r.getName()).snippet(r.getDescription()));
            mMap.addMarker(new MarkerOptions().position(loc).title(r.getWaterCondition().toString()).snippet(r.getWaterType().toString()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }

    public void handleReport(LatLng latLng) {
        Intent intent = new Intent(this, WaterReportActivity.class);
        intent.putExtra(LATITUDE_MESSAGE, latLng.latitude);
        intent.putExtra(LONGITUDE_MESSAGE, latLng.longitude);
        startActivity(intent);
    }
}
