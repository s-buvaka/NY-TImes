package com.example.indus.businesscard.network;

import com.example.indus.businesscard.modeldto.NewsResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface INewsEndPoint {

    @GET("home.json")
    Observable<NewsResponse> getNews();

    @GET("{section}.json")
    Observable<NewsResponse> getNewsByCategory(@Path("section") String category);
}
