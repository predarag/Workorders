package rs.co.sbb.workorders.ws;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rs.co.sbb.workorders.entity.LoginRequest;
import rs.co.sbb.workorders.entity.LoginResponse;
import rs.co.sbb.workorders.entity.NotificationTokenRequest;
import rs.co.sbb.workorders.entity.Response;
import rs.co.sbb.workorders.entity.UserRoles;

/**
 * Created by Predrag.Tasic on 8/15/2017.
 */

public interface ExternalAuthService {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("logout")
    Call<Response> logout(@Body LoginRequest request);

    @POST("notificationToken")
    Call<Response> setNotificationToken(@Body NotificationTokenRequest request);

    @GET("userRoles")
    Call<UserRoles> getUserRoles(@Query("username") String username);

}
