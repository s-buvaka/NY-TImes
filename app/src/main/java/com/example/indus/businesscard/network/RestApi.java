package com.example.indus.businesscard.network;

import com.example.indus.businesscard.utils.Const;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {
    private static final int TIMEOUT_IN_SECOND = 2;

    private static RestApi sInstance;
    private static INewsEndPoint sService;

    private RestApi() {
        OkHttpClient okHttpClient = buildOkHttpClient();
        Retrofit sRetrofit = buildRetrofit(okHttpClient);

        sService = sRetrofit.create(INewsEndPoint.class);
    }

    public static synchronized RestApi getInstance() {
        if (sInstance == null) {
            sInstance = new RestApi();
        }
        return sInstance;
    }

    public INewsEndPoint getEndPoint() {
        return sService;
    }

    private OkHttpClient buildOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(Const.API_KEY))
                .readTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
                .build();
    }

    private Retrofit buildRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
