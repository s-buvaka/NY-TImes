package com.example.indus.nytimes.network;

import com.example.indus.nytimes.modeldto.NewsResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INewsEndPoint {

    @GET("home.json")
    Single<NewsResponse> getNews();

    @GET("{section}.json")
    Single<NewsResponse> getNewsByCategory(@Path("section") String category);
}
