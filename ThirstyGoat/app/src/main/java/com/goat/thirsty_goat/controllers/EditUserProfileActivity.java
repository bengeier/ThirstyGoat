package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.goat.thirsty_goat.models.User;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText mUserNameField;
    private EditText mEmailField;
    private Spinner mAccounTypeSpinner;
    private Button mSaveButton;
    private Button mCancelEditButton;
    private Button mMapButton;
    private Button mReportButton;
    private Auth0 mAuth0;
    private AuthenticationAPIClient mClient;
    private UserProfile mUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        /**
         * Set up Auth0 main object and client
         */
        mAuth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        mClient = new AuthenticationAPIClient(mAuth0);
        mClient.tokenInfo(App.getInstance().getUserCredentials().getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(final UserProfile payload) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mUserProfile = payload;
                                User.updateUserSingleton(mClient);
                                updateFields();
                            }
                        });
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(EditUserProfileActivity.this,
                                        "Profile Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


        /**
         * Get dialog widgets
         */
        mUserNameField = (EditText) findViewById(R.id.user_name);
        mEmailField = (EditText) findViewById(R.id.email);
        mAccounTypeSpinner = (Spinner) findViewById(R.id.account_type_spinner);

        mSaveButton = (Button) findViewById(R.id.save_button);
        mCancelEditButton = (Button) findViewById(R.id.cancel_edit_button);
        mMapButton = (Button) findViewById(R.id.map_button);
        mReportButton = (Button) findViewById(R.id.add_report_button);

        /**
         * Set up adapter to select account type
         */
        ArrayAdapter<User.AccountType> accountTypeAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, User.AccountType.values());
        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccounTypeSpinner.setAdapter(accountTypeAdapter);

        /**
         * Set default field values
         */
        User.updateUserSingleton(mClient);
        updateFields();

        /**
         * Save and cancel button functionalities
         */
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        mCancelEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFields();
            }
        });

        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMapView();
            }
        });

        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToAddReportView();
            }
        });
    }

    private void updateFields() {
        mUserNameField.setText(User.getInstance().getUserName());
        mEmailField.setText(User.getInstance().getEmail());
        mAccounTypeSpinner.setSelection(User.findAccountTypePosition());
    }
    private void saveProfile() {
        UsersAPIClient userClient =
                new UsersAPIClient(mAuth0, App.getInstance().getUserCredentials().getIdToken());
        Map<String, Object> userMetadata = new HashMap<>();
        userMetadata.put("account_type", mAccounTypeSpinner.getSelectedItem().toString());
        userMetadata.put("email", mEmailField.getText().toString());
        userMetadata.put("name", mUserNameField.getText().toString());
        userClient.updateMetadata(mUserProfile.getId(), userMetadata)
                .start(new BaseCallback<UserProfile, ManagementException>() {
                    @Override
                    public void onSuccess(final UserProfile payload) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mUserProfile = payload;
                                User.updateUserSingleton(mClient);
                            }
                        });
                    }

                    @Override
                    public void onFailure(ManagementException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(EditUserProfileActivity.this, "Profile Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }

    public void switchToMapView() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void switchToAddReportView() {
        Intent intent = new Intent(this, WaterReportActivity.class);
        startActivity(intent);
    }
}
