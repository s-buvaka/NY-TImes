package com.example.indus.businesscard.network;

import com.example.indus.businesscard.utils.Const;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    private static final String API_KEY = "api-key";
    private static ApiKeyInterceptor sInstance;

    private final String apiKey;

    private ApiKeyInterceptor(String apiKey){
        this.apiKey = apiKey;
    }

    static Interceptor create(){
        if(sInstance == null){
            sInstance = new ApiKeyInterceptor(Const.API_KEY);
        }
        return sInstance;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request requestWithoutApiKey = chain.request();

        final HttpUrl url = requestWithoutApiKey.url()
                .newBuilder()
                .addQueryParameter(API_KEY, apiKey)
                .build();

        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
                .url(url)
                .build();

        return chain.proceed(requestWithAttachedApiKey);
    }
}
