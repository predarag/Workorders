package rs.co.sbb.workorders.ws;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rs.co.sbb.workorders.entity.LoginRequest;
import rs.co.sbb.workorders.entity.LoginResponse;

/**
 * Created by Predrag.Tasic on 8/15/2017.
 */

public interface ExternalAuthService {

    @POST("login")
    public Call<LoginResponse> login(@Body LoginRequest request);

}
