package rs.co.sbb.workorders.ws;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Predrag.Tasic on 8/14/2017.
 */

public interface SerbianAddressService {

    @GET("getCitySap")
    Call<HashMap<String, String>> getAllCities();


    @GET("getRegionSap")
    Call<HashMap<String, String>> getRegionByCityCode(@Query("cityCode") String cityCode);

    @GET("getStreetSap")
    Call<HashMap<String, String>> getStreetsByRegionCode(@Query("regionCode") String settlementCode);



}
