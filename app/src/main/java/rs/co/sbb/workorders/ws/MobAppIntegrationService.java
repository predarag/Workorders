package rs.co.sbb.workorders.ws;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rs.co.sbb.workorders.entity.Response;
import rs.co.sbb.workorders.entity.product_package.PackageOptionResponse;
import rs.co.sbb.workorders.entity.product_package.ProductPackageResponse;
import rs.co.sbb.workorders.entity.totaltv.TotalTvActivationHolder;
import rs.co.sbb.workorders.entity.totaltv.TotalTvActivationRequest;

/**
 * Created by Predrag.Tasic on 8/31/2017.
 */

public interface MobAppIntegrationService {

    @POST("activation")
    public Call<Response> activateTotalTv(@Body TotalTvActivationRequest request);

    @GET("product_package")
    public Call<ProductPackageResponse> getServiceProductPackages(@Query("serviceName") String serviceName,
                                                                  @Query("country") String country);

    @GET("product_options")
    public Call<PackageOptionResponse> getProductPackageOptions(@Query("productPackage") String productPackage,
                                                                @Query("country") String country);

}
