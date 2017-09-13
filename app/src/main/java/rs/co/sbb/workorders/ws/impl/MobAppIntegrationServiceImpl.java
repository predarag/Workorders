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
import rs.co.sbb.workorders.entity.product_package.PackageOptionResponse;
import rs.co.sbb.workorders.entity.product_package.ProductPackageResponse;
import rs.co.sbb.workorders.entity.totaltv.TotalTvActivationHolder;
import rs.co.sbb.workorders.entity.totaltv.TotalTvActivationRequest;
import rs.co.sbb.workorders.ws.MobAppIntegrationService;
import rs.co.sbb.workorders.ws.config.MobAppIntegrationConfig;

/**
 * Created by Predrag.Tasic on 8/31/2017.
 */

public class MobAppIntegrationServiceImpl {

    private Retrofit retrofit;

    private OkHttpClient clientAuth = new OkHttpClient().newBuilder().addInterceptor(
            new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request request = chain.request();

                    Request.Builder builder = request.newBuilder().header("Authorization", Credentials.basic(MobAppIntegrationConfig.USERNAME,MobAppIntegrationConfig.PASSWORD));

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }
    ).build();

    public MobAppIntegrationServiceImpl(String basePath){

        retrofit = new Retrofit.Builder()
                .client(clientAuth)
                .baseUrl(MobAppIntegrationConfig.BASE_URL+basePath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public Call<ProductPackageResponse> getServiceProductPacakges(String serviceName, String country){

        MobAppIntegrationService service = retrofit.create(MobAppIntegrationService.class);

        return  service.getServiceProductPackages(serviceName,country);

    }

    public Call<rs.co.sbb.workorders.entity.Response> activateTotalTvEquipment(TotalTvActivationRequest request){
        MobAppIntegrationService service = retrofit.create(MobAppIntegrationService.class);

        return service.activateTotalTv(request);
    }

    public Call<PackageOptionResponse> getProductPackageOptions(String productPackage, String country){
        MobAppIntegrationService service = retrofit.create(MobAppIntegrationService.class);

        return service.getProductPackageOptions(productPackage,country);
    }


}
