package rs.co.sbb.workorders.service;


import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.co.sbb.workorders.entity.TokenResponse;
import rs.co.sbb.workorders.ws.impl.TokenServiceImpl;


public class FirebaseIdService extends FirebaseInstanceIdService {

    private final String TAG = "FirebaseIdService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        Log.i("#############","USAOOOOOOOOOOOOOOOO");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("TOKEN",refreshedToken);

        Toast.makeText(getApplicationContext(),refreshedToken, Toast.LENGTH_LONG).show();

        sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String token) {

        TokenServiceImpl tokenService = new TokenServiceImpl();

        Call<TokenResponse> call = tokenService.token("pedja",token);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                Log.i(TAG, response.code()+"");

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });

    }

}
