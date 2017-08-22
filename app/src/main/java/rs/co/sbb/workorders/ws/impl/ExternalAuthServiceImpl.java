package rs.co.sbb.workorders.ws.impl;


import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.co.sbb.workorders.entity.LoginRequest;
import rs.co.sbb.workorders.entity.LoginResponse;
import rs.co.sbb.workorders.entity.NotificationTokenRequest;
import rs.co.sbb.workorders.ws.ExternalAuthService;
import rs.co.sbb.workorders.ws.config.ExternalAuthConfig;

public class ExternalAuthServiceImpl {

    private Retrofit retrofit;

    private OkHttpClient clientAuth = new OkHttpClient().newBuilder().addInterceptor(
            new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request request = chain.request();

                    Request.Builder builder = request.newBuilder().header("Authorization", Credentials.basic(ExternalAuthConfig.USERNAME,ExternalAuthConfig.PASSWORD));

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }
    ).build();

    public ExternalAuthServiceImpl(){

        retrofit = new Retrofit.Builder()
                .client(clientAuth)
                .baseUrl(ExternalAuthConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public Call<LoginResponse> login(LoginRequest request){
        ExternalAuthService service = retrofit.create(ExternalAuthService.class);

        return  service.login(request);
    }

    public Call<rs.co.sbb.workorders.entity.Response> logout(LoginRequest request){

        ExternalAuthService service = retrofit.create(ExternalAuthService.class);

        return  service.logout(request);
    }

    public Call<rs.co.sbb.workorders.entity.Response> setNotificationToken(NotificationTokenRequest request){

        ExternalAuthService service = retrofit.create(ExternalAuthService.class);

        return  service.setNotificationToken(request);

    }

}
