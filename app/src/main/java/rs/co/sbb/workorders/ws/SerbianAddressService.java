package rs.co.sbb.workorders.ws;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rs.co.sbb.workorders.entity.TokenResponse;

/**
 * Created by Predrag.Tasic on 8/14/2017.
 */

public interface SerbianAddressService {

    @GET("getAllCommunitys")
    Call<HashMap<String, String>> getAllCommunitys();


    @GET("getSettlementByCommunityCode/{communityCode}")
    Call<HashMap<String, String>> getSettlementByCommunityCode(@Path("communityCode")String communityCode);

    @GET("getStreetsBySettlementCode/{settlementCode}")
    Call<HashMap<String, String>> getStreetsBySettlementCode(@Path("settlementCode")String settlementCode);



}
