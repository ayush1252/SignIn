package com.example.ayush.signingoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookActvity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_actvity);
        AppEventsLogger.activateApp(this);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        callbackManager=CallbackManager.Factory.create();
        if(Profile.getCurrentProfile()!=null)
        {
            Toast.makeText(this, "Tu already Registered hai chomu ", Toast.LENGTH_SHORT).show();
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("lalalla" , String.valueOf(loginResult.getRecentlyDeniedPermissions()));
                Log.d("lalala2", String.valueOf(loginResult.getAccessToken().getUserId()));
                Profile profile=Profile.getCurrentProfile();
                Log.d("1234",profile.getFirstName());
                Log.d("1234",String.valueOf(profile.getProfilePictureUri(Integer.valueOf(100),Integer.valueOf(100))));

            }

            @Override
            public void onCancel() {
                Toast.makeText(FacebookActvity.this, "UserCanceeled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FacebookActvity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
