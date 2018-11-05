package com.example.indus.businesscard.network;

import com.example.indus.businesscard.modeldto.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INewsEndPoint {

    @GET("home.json")
    Single<NewsResponse> getNews();

    @GET("{section}.json")
    Single<NewsResponse> getNewsByCategory(@Path("section") String category);
}
