package rs.co.sbb.workorders.service;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import rs.co.sbb.workorders.utils.SaveSharedPreference;
import rs.co.sbb.workorders.utils.Utils;


public class FirebaseIdService extends FirebaseInstanceIdService {

    private final String TAG = "FirebaseIdService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        Log.i("#############","USAOOOOOOOOOOOOOOOO");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("TOKEN",refreshedToken);


        sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String token) {

        Utils.setNotificationToken(SaveSharedPreference.getUser(getApplicationContext()),token);

    }

}
