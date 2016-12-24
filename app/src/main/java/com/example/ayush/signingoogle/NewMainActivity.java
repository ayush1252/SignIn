package com.example.ayush.signingoogle;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

/**
 * Created by Ayush on 25-12-2016.
 */

public class NewMainActivity  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.setTheme(R.style.FullscreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            try {
                Log.d("Lalala2",FirebaseAuth.getInstance().getCurrentUser().getToken(true).toString());
                Log.d("Lalala3",FirebaseAuth.getInstance().getCurrentUser().getToken(false).toString());
                FirebaseAuth.getInstance().getCurrentUser().getToken(true)
                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                if (task.isSuccessful()) {
                                    String idToken = task.getResult().getToken();
                                    Log.d("IDTOKEn",idToken);
                                    Toast.makeText(NewMainActivity.this, "idToken", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Handle error -> task.getException();
                                }
                            }
                        });
                Log.d("Lalala1",FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
                Log.d("Lalala",FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
            }
            catch (Exception e)
            {
                Log.d("LALALALA","NAHI HUA");

            }
            Toast.makeText(this, "AlreadyLoggedIN", Toast.LENGTH_SHORT).show();
        } else {
            // not signed in

            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER
                    ).setIsSmartLockEnabled(false).build(), 1
            );
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // user is signed in!

                try {
                    Log.d("Lalala1",FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Log.d("Lalala2",FirebaseAuth.getInstance().getCurrentUser().getToken(true).toString());
                    Log.d("Lalala3",FirebaseAuth.getInstance().getCurrentUser().getToken(false).toString());

                    Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
                    FirebaseAuth.getInstance().getCurrentUser().getToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if (task.isSuccessful()) {
                                        String idToken = task.getResult().getToken();
                                        Log.d("IDTOKEn",idToken);
                                        Toast.makeText(NewMainActivity.this, "idToken", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle error -> task.getException();
                                    }
                                }
                });
                }
                catch (Exception e)
                {
                    Log.d("LALALALA","NAHI HUA");

                }
                startActivity(new Intent(this, MainActivity.class));
                IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
                finish();
                return;
            }

            // Sign in canceled
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Request Cancelled", Toast.LENGTH_SHORT).show();
                return;
            }

            // No network

            if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
                Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            // User is not signed in. Maybe just wait for the user to press
            // "sign in" again, or show a message.
        }
    }
}
