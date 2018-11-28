package com.example.indus.businesscard.modeldto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("results")
    private List<NewsItem> results;

    public void setResults(List<NewsItem> results) {
        this.results = results;
    }

    public List<NewsItem> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return
                "NewsResponse{" +
                        "results = '" + results + '\'' +
                        "}";
    }
}