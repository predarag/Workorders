package rs.co.sbb.workorders.ws;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rs.co.sbb.workorders.entity.TokenResponse;


public interface TokenService {

    @GET("api/token")
    Call<TokenResponse> token(@Query("user")String user, @Query("token")String token);
}
