package rs.co.sbb.workorders.ws.impl;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.co.sbb.workorders.entity.TokenResponse;
import rs.co.sbb.workorders.ws.TokenService;


public class TokenServiceImpl  {

    private Context context;
    private Retrofit retrofit;

   public TokenServiceImpl(){

       retrofit = new Retrofit.Builder()
               .baseUrl("http://10.0.2.2:8080")
               .addConverterFactory(GsonConverterFactory.create())
               .build();

   }

    public Call<TokenResponse> token(String user, String token){
        Log.i("token", user+" "+token);
        TokenService service = retrofit.create(TokenService.class);

        return  service.token(user, token);


    }

}
