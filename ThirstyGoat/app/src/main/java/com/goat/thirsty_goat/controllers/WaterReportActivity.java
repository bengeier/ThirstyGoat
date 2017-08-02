//package com.goat.thirsty_goat.controllers;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.auth0.android.Auth0;
//import com.auth0.android.authentication.AuthenticationAPIClient;
//import com.auth0.android.authentication.AuthenticationException;
//import com.auth0.android.callback.BaseCallback;
//import com.auth0.android.management.ManagementException;
//import com.auth0.android.management.UsersAPIClient;
//import com.auth0.android.result.UserProfile;
//import com.goat.thirsty_goat.R;
//import com.goat.thirsty_goat.application.App;
//import com.goat.thirsty_goat.models.User;
//
//import java.util.HashMap;
//import java.util.Map;

package com.goat.thirsty_goat.controllers;

/**
 * Created by Walker on 2/27/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.application.App;
import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.User;
import com.goat.thirsty_goat.models.WaterCondition;
import com.goat.thirsty_goat.models.WaterType;

import java.util.HashMap;
import java.util.Map;

public class WaterReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    private EditText mUserNameField;
//    private EditText mEmailField;
//    private Spinner mAccounTypeSpinner;
//    private Button mSaveButton;
//    private Button mCancelEditButton;
//    private Button mMapButton;
//    private Auth0 mAuth0;
//    private AuthenticationAPIClient mClient;
//    private UserProfile mUserProfile;

    private EditText mLatitudeEditText;
    private EditText mLongitudeEditText;
    private Spinner mWaterConditionSpinner;
    private Spinner mWaterTypeSpinner;
    private Button mSubmitButton;
    private Button mCancelButton;


    // keeps up with instance data to create the report
    private WaterType mWaterType;
    private WaterCondition mWaterCondition;
    private double mLongitude;
    private double mLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_water_report);

//        mSaveButton = (Button) findViewById(R.id.save_button);
//        mCancelEditButton = (Button) findViewById(R.id.cancel_edit_button);
//        mMapButton = (Button) findViewById(R.id.map_button);

        mSubmitButton = (Button) findViewById(R.id.submit_report_button);
        mCancelButton = (Button) findViewById(R.id.cancel_report_button);
        mLatitudeEditText = (EditText) findViewById(R.id.latitude_edit_text);
        mLongitudeEditText = (EditText) findViewById(R.id.longitude_edit_text);
        mWaterConditionSpinner = (Spinner) findViewById(R.id.water_condition_spinner);
        mWaterTypeSpinner = (Spinner) findViewById(R.id.water_type_spinner);

//        /**
//         * Set up adapter to select account type
//         */
//        ArrayAdapter<User.AccountType> accountTypeAdapter =
//                new ArrayAdapter(this, android.R.layout.simple_spinner_item, User.AccountType.values());
//        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mAccounTypeSpinner.setAdapter(accountTypeAdapter);

        ArrayAdapter<WaterCondition> waterConditionAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterCondition.values());
        waterConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterConditionSpinner.setAdapter(waterConditionAdapter);

        ArrayAdapter<WaterType> waterTypeAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterType.values());
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterTypeSpinner.setAdapter(waterTypeAdapter);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mLongitude = extras.getDouble(MapsActivity.LONGITUDE_MESSAGE);
            mLatitude = extras.getDouble(MapsActivity.LATITUDE_MESSAGE);

            mLatitudeEditText.setText(String.valueOf(mLatitude), TextView.BufferType.EDITABLE);
            mLongitudeEditText.setText(String.valueOf(mLongitude), TextView.BufferType.EDITABLE);
        }
    }

    // for some reason, these aren't updating when clicked
    @ Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Report", "something selected");
        switch (parent.getId()){
            case R.id.water_type_spinner:
                mWaterType = (WaterType) parent.getItemAtPosition(position);
                break;
            case R.id.water_condition_spinner:
                mWaterCondition = (WaterCondition) parent.getItemAtPosition(position);
                break;
        }
    }

    // for some reason, this isn't doing anything
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("Report", "nothing selected");
        switch (parent.getId()){
            case R.id.water_type_spinner:
                mWaterType = WaterType.BOTTLED;
                break;
            case R.id.water_condition_spinner:
                mWaterCondition = WaterCondition.POTABLE;
                break;
        }
    }

    protected void onSubmitPressed(View view) {
        Log.d("Report", "pressed submit in water report");
        mLatitude = Double.parseDouble(mLatitudeEditText.getText().toString());
        mLongitude = Double.parseDouble(mLongitudeEditText.getText().toString());

        mWaterType = (WaterType) mWaterTypeSpinner.getSelectedItem();
        mWaterCondition = (WaterCondition) mWaterConditionSpinner.getSelectedItem();

        Log.d("Report", "long = " + mLongitude);
        Log.d("Report", "lat = " + mLatitude);
        Log.d("Report", "type = " + mWaterType.toString());
        Log.d("Report", "condition = " + mWaterCondition.toString());

        ModelFacade mFacade = ModelFacade.getInstance();
        mFacade.addReport(mWaterType, mWaterCondition, new Location(mLatitude, mLongitude));


        finish();
    }

    protected void onCancelPressed(View view) {
        Log.d("Report", "pressed cancel in water report");
        finish();
    }
}
