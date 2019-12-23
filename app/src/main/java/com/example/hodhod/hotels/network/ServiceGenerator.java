package com.example.hodhod.hotels.network;

import com.example.hodhod.hotels.MyApplication;
import com.example.hodhod.hotels.util.Constants;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static long cacheSize = 5 * 1024 * 1024;
    private static Cache cache = new Cache(MyApplication.getInstance().getCacheDir(), cacheSize);
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    // Request customization: add request headers
                    if (MyApplication.hasNetwork()) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                }
            });
    private static OkHttpClient client = okHttp.build();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();
    private static HotelApi hotelApi = retrofit.create(HotelApi.class);
    public static HotelApi getHotelApi() {
        return hotelApi;
    }
}