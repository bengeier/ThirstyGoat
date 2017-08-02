package com.goat.thirsty_goat.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.CustomField;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;
import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.application.App;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {

    private Lock mLock;
    private AuthenticationAPIClient mClient ;
    private Auth0 mAuth0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LoginActivity", "On Create Login Activity");

        List<CustomField> customFields = new ArrayList<>();
        CustomField accountType = new CustomField(R.drawable.com_auth0_lock_header_logo, CustomField.FieldType.TYPE_NAME, "account_type", R.string.account_type);
        customFields.add(accountType);

        mAuth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        mLock = Lock.newBuilder(mAuth0, mCallback)
                //Add parameters to the builder
                .withSignUpFields(customFields)
                .build(this);
        startActivity(mLock.newIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLock.onDestroy(this);
        mLock = null;
    }

    private final LockCallback mCallback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            Toast.makeText(getApplicationContext(), "Log In - Success", Toast.LENGTH_SHORT).show();
            App.getInstance().setUserCredentials(credentials);
            redirectUser();
            startActivity(new Intent(getApplicationContext(), EditUserProfileActivity.class));
            finish();
        }

        @Override
        public void onCanceled() {
            Toast.makeText(getApplicationContext(), "Log In - Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(LockException error) {
            Toast.makeText(getApplicationContext(), "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
        }
    };

    public void redirectUser() {
        mClient = new AuthenticationAPIClient(mAuth0);
        mClient.tokenInfo(App.getInstance().getUserCredentials().getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload){

//                        String account_type = payload.getUserMetadata().get("account_type").toString();
//                        System.out.println(account_type);
                    }

                    @Override
                    public void onFailure(AuthenticationException error){
                    }
                });
    }

}


