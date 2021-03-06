package rs.co.sbb.workorders.ws.impl;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Cache;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rs.co.sbb.workorders.ws.SerbianAddressService;
import rs.co.sbb.workorders.ws.config.SerbianAddresConfig;

/**
 * Created by Predrag.Tasic on 8/14/2017.
 */

public class SerbianAddressServiceImpl {

    private Retrofit retrofit;
    private Context context;

    private static final String TAG = "SerbianAddressService";

    int cacheSize = 25 * 1024 * 1024; // 10 MB
    //File httpCacheDirectory = new File(context.getCacheDir(), "cache_file");
    Cache cache = new Cache(getDirectory(), cacheSize);

    private OkHttpClient clientAuth = new OkHttpClient().newBuilder().addInterceptor(
            new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request request = chain.request();

                    Request.Builder builder = request.newBuilder().header("Authorization", Credentials.basic(SerbianAddresConfig.USERNAME,SerbianAddresConfig.PASSWORD));

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }
    ).cache(cache)
            .build();

    public SerbianAddressServiceImpl(final Context context){
        this.context = context;

        retrofit = new Retrofit.Builder()
                .client(clientAuth)
                .baseUrl(SerbianAddresConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public Call<HashMap<String,String>> getAllCitys(){

        SerbianAddressService service = retrofit.create(SerbianAddressService.class);

        return  service.getAllCities();

    }

    public Call<HashMap<String,String>> getRegionByCityCode(String cityCode){

        SerbianAddressService service = retrofit.create(SerbianAddressService.class);

        return  service.getRegionByCityCode(cityCode);

    }

    public Call<HashMap<String,String>> getStreetsByRegionCode(String regionCode){

        SerbianAddressService service = retrofit.create(SerbianAddressService.class);

        Call<HashMap<String,String>> call = service.getStreetsByRegionCode(regionCode);

        Log.i("streets",call.request().url().toString());

        return call;

    }


    public Call<HashMap<String,String>> getPostCode(String cityCode,String regionCode, String streetCode){

        Log.i(TAG,cityCode+" "+regionCode+" "+streetCode);

        SerbianAddressService service = retrofit.create(SerbianAddressService.class);

        Call<HashMap<String,String>> call = service.getPostCode(cityCode,regionCode,streetCode);

        Log.i("streets",call.request().url().toString());

        return call;

    }


    private File getDirectory() {
        return new File("address_data");
    }
}
